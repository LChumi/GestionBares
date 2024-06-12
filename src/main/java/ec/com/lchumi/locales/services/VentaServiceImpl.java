package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.*;
import ec.com.lchumi.locales.models.enums.TipoMovimientoEnum;
import ec.com.lchumi.locales.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class VentaServiceImpl extends GenericServiceImpl<Venta,Long> implements IVentaService {

    private final VentaRepository ventaRepository;
    private final AlmacenProductoRepository almacenProductoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public DetalleVenta agregarDetalle(Long ventaId, DetalleVenta detalle) throws Exception {
        Venta venta = ventaRepository.findById(ventaId).orElseThrow(() -> new Exception("Venta no encontrada"));

        //Validar stock
        AlmacenProducto almacenProducto = almacenProductoRepository.findByProductoAndBodega(detalle.getProducto(), detalle.getBodega()).orElseThrow(() -> new Exception("No existe el producto en la bodega"));

        int cantidadAgregar= detalle.getCantidad() > 0 ? detalle.getCantidad() : 1;

        if (almacenProducto.getStock() < cantidadAgregar) {
            log.error("Stock insuficiente para el producto {} " , detalle.getProducto().getDescripcion());
            throw new Exception("Stock insuficiente para el producto " + detalle.getProducto().getDescripcion());
        }

        //reducir stock
        almacenProducto.setStock(almacenProducto.getStock() - cantidadAgregar);
        almacenProductoRepository.save(almacenProducto);

        // Registrar movimiento de inventario
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(detalle.getProducto());
        movimiento.setCantidad(cantidadAgregar);
        movimiento.setBodega(detalle.getBodega());
        movimiento.setTipo(TipoMovimientoEnum.SALIDA);
        movimiento.setFecha(LocalDate.now());
        movimientoInventarioRepository.save(movimiento);

        // Agregar detalle a la venta
        venta.agregarDetalle(detalle);
        ventaRepository.save(venta);

        return detalle;
    }

    @Override
    public void eliminarDetalle(Long ventaId, Long detalleId) throws Exception {
        Venta venta = ventaRepository.findById(ventaId).orElseThrow(() -> new Exception("Venta no encontrada"));
        venta.eliminarDetalle(detalleId);
        ventaRepository.save(venta);
    }

    @Override
    public DetalleVenta actualizarDetalle(Long ventaId, Long detalleId, DetalleVenta detalleVenta) throws Exception {
        Venta venta = ventaRepository.findById(ventaId).orElseThrow(()-> new Exception("Venta no encontrada"));
        venta.actualizarDetalle(detalleId, detalleVenta);
        ventaRepository.save(venta);
        return detalleVenta;
    }


    @Override
    public CrudRepository<Venta, Long> getDao() {
        return ventaRepository;
    }
}
