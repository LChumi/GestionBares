package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Almacen;
import ec.com.lchumi.locales.models.entities.DetalleVenta;
import ec.com.lchumi.locales.models.entities.MovimientoInventario;
import ec.com.lchumi.locales.models.entities.Venta;
import ec.com.lchumi.locales.models.enums.TipoMovimientoEnum;
import ec.com.lchumi.locales.repository.AlmacenRepository;
import ec.com.lchumi.locales.repository.DetalleVentaRepository;
import ec.com.lchumi.locales.repository.MovimientoInventarioRepository;
import ec.com.lchumi.locales.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class VentaServiceImpl implements IVentaService {

    private final VentaRepository ventaRepository;
    private final AlmacenRepository almacenRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    @Override
    public Venta registrarVenta(Venta venta) throws Exception {
        //validar stock disponible
        for (DetalleVenta detalle : venta.getDetalles()){
            Almacen almacen = almacenRepository.findByProductoAndBodega(detalle.getProducto(),detalle.getBodega());
            if (almacen == null || almacen.getStock() < detalle.getCantidad()){
                log.error("Stock insuficiente para el producto "+ detalle.getProducto().getDesripcion());
                throw new Exception("Stock insuficiente para el producto "+ detalle.getProducto().getDesripcion());
            }

            // Reducir stock
            almacen.setStock(almacen.getStock() - detalle.getCantidad());
            almacenRepository.save(almacen);

            // Registrar movimiento de inventario
            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setProducto(detalle.getProducto());
            movimiento.setCantidad(detalle.getCantidad());
            movimiento.setBodega(detalle.getBodega());
            movimiento.setTipo(TipoMovimientoEnum.SALIDA);
            movimiento.setFecha(LocalDate.now());
            movimientoInventarioRepository.save(movimiento);
        }

        // Guardar venta y detalles
        venta = ventaRepository.save(venta);
        for (DetalleVenta detalle : venta.getDetalles()){
            detalle.setVenta(venta);
            detalleVentaRepository.save(detalle);
        }
        return venta;
    }
}
