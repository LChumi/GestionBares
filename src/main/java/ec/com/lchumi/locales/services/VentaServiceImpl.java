package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.*;
import ec.com.lchumi.locales.models.enums.TipoMovimientoEnum;
import ec.com.lchumi.locales.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class VentaServiceImpl implements IVentaService {

    private final VentaRepository ventaRepository;
    private final AlmacenProductoRepository almacenProductoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public Venta registrarVenta(Venta venta) throws Exception {
        // Validar stock disponible y reducirlo
        for (DetalleVenta detalle : venta.getDetalles()) {
            AlmacenProducto almacenProducto = almacenProductoRepository.findByProductoAndBodega(detalle.getProducto(), detalle.getBodega())
                    .orElseThrow(() -> new Exception("No existe el producto en la bodega especificada"));

            if (almacenProducto.getStock() < detalle.getCantidad()) {
                log.error("Stock insuficiente para el producto " + detalle.getProducto().getDescripcion());
                throw new Exception("Stock insuficiente para el producto " + detalle.getProducto().getDescripcion());
            }

            // Reducir stock
            almacenProducto.setStock(almacenProducto.getStock() - detalle.getCantidad());
            almacenProductoRepository.save(almacenProducto);

            // Registrar movimiento de inventario
            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setProducto(detalle.getProducto());
            movimiento.setCantidad(detalle.getCantidad());
            movimiento.setBodega(detalle.getBodega());
            movimiento.setTipo(TipoMovimientoEnum.SALIDA);
            movimiento.setFecha(LocalDate.now());
            movimientoInventarioRepository.save(movimiento);
        }

        // Validar crédito del cliente
        Cliente cliente = venta.getCliente();
        BigDecimal creditoDisponible = cliente.getCredito();
        if (creditoDisponible.compareTo(venta.getTotal()) < 0) {
            throw new Exception("Crédito insuficiente para completar la compra");
        }

        // Guardar venta y detalles
        venta = ventaRepository.save(venta);
        for (DetalleVenta detalle : venta.getDetalles()) {
            detalle.setVenta(venta);
            detalleVentaRepository.save(detalle);
        }

        // Actualizar crédito del cliente
        cliente.setCredito(creditoDisponible.subtract(venta.getTotal()));
        clienteRepository.save(cliente);

        return venta;
    }
}
