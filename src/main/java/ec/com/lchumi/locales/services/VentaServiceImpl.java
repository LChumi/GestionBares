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
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VentaServiceImpl extends GenericServiceImpl<Venta,Long> implements IVentaService {

    private final VentaRepository ventaRepository;
    private final AlmacenProductoRepository almacenProductoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ClienteRepository clienteRepository;
    private final CreditoPagoRepository pagoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Venta agregarDetalle(Long ventaId, DetalleVenta detalle, int tipoPrecio) throws Exception {
        Venta venta = ventaRepository.findById(ventaId).orElseThrow(() -> new Exception("Venta no encontrada"));

        //Validar stock
        AlmacenProducto almacenProducto = almacenProductoRepository.findByProductoAndBodega(detalle.getProducto(), detalle.getBodega()).orElseThrow(() -> new Exception("No existe el producto en la bodega"));

        int cantidadAgregar= detalle.getCantidad() > 0 ? detalle.getCantidad() : 1;

        if (almacenProducto.getStock() < cantidadAgregar) {
            log.error("Stock insuficiente para el producto {} " , detalle.getProducto().getDescripcion());
            throw new Exception("Stock insuficiente para el producto " + detalle.getProducto().getDescripcion());
        }

        //Set tipo de precio
        detalle.setTipoPrecio(tipoPrecio);

        // Agregar detalle a la venta
        venta.agregarDetalle(detalle);
        ventaRepository.save(venta);

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

        return venta;
    }

    @Override
    public void eliminarDetalle(Long ventaId, Long detalleId) throws Exception {
        Venta venta = ventaRepository.findById(ventaId).orElseThrow(() -> new Exception("Venta no encontrada"));
        DetalleVenta detalle = venta.getDetalleById(detalleId);

        //Aumentar el stock del producto correspondiente
        AlmacenProducto almacenProducto = almacenProductoRepository.findByProductoAndBodega(detalle.getProducto(),detalle.getBodega()).orElseThrow(() -> new Exception("No existe el producto en la bodega"));
        almacenProducto.setStock(almacenProducto.getStock()+ detalle.getCantidad());
        almacenProductoRepository.save(almacenProducto);

        //Registrar movimiento de inventario
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(detalle.getProducto());
        movimiento.setCantidad(detalle.getCantidad());
        movimiento.setBodega(detalle.getBodega());
        movimiento.setTipo(TipoMovimientoEnum.ENTRADA);
        movimiento.setFecha(LocalDate.now());
        movimientoInventarioRepository.save(movimiento);

        //Eliminar el detalle del repositorio
        venta.eliminarDetalle(detalleId);
        detalleVentaRepository.deleteById(detalleId);
        ventaRepository.save(venta);
    }

    @Override
    public DetalleVenta actualizarDetalle(Long ventaId, Long detalleId, DetalleVenta detalleVenta, int tipoPrecio) throws Exception {
        Venta venta = ventaRepository.findById(ventaId).orElseThrow(() -> new Exception("Venta no encontrada"));
        DetalleVenta detalleExistente = venta.getDetalleById(detalleId);

        // Ajustar el stock del producto correspondiente
        AlmacenProducto almacenProducto = almacenProductoRepository.findByProductoAndBodega(detalleExistente.getProducto(), detalleExistente.getBodega()).orElseThrow(() -> new Exception("No existe el producto en la bodega"));
        int cantidadOriginal = detalleExistente.getCantidad();
        int cantidadNueva = detalleVenta.getCantidad();

        int diferenciaCantidad = cantidadNueva - cantidadOriginal;
        if (almacenProducto.getStock() < -diferenciaCantidad) {
            throw new Exception("Stock insuficiente para actualizar la cantidad del producto " + detalleVenta.getProducto().getDescripcion());
        }

        almacenProducto.setStock(almacenProducto.getStock() - diferenciaCantidad);
        almacenProductoRepository.save(almacenProducto);

        // Registrar movimiento de inventario
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(detalleVenta.getProducto());
        movimiento.setCantidad(Math.abs(diferenciaCantidad));
        movimiento.setBodega(detalleVenta.getBodega());
        movimiento.setTipo(diferenciaCantidad > 0 ? TipoMovimientoEnum.SALIDA : TipoMovimientoEnum.ENTRADA);
        movimiento.setFecha(LocalDate.now());
        movimientoInventarioRepository.save(movimiento);

        // Actualizar el detalle de la venta
        detalleExistente.setCantidad(detalleVenta.getCantidad());
        detalleExistente.setTipoPrecio(tipoPrecio);
        detalleExistente.setProducto(detalleVenta.getProducto()); // Esto actualiza el precio unitario y el subtotal
        detalleExistente.actualizarSubtotal();
        detalleVentaRepository.save(detalleExistente);
        ventaRepository.save(venta);

        return detalleExistente;
    }


    @Override
    public Venta procesarPago(Long ventaId, BigDecimal montoCredito, BigDecimal montoEfectivo, BigDecimal montoTarjeta) throws Exception {
        Venta ventaEncontrada = ventaRepository.findById(ventaId).orElseThrow(() -> new Exception("Venta no encontrada"));
        Cliente cliente = ventaEncontrada.getCliente();

        if (cliente == null ){
            throw new Exception("Venta no tiene cliente asociado");
        }

        BigDecimal totalVenta = ventaEncontrada.getTotal();
        BigDecimal creditoDisponible = cliente.getCredito();

        BigDecimal montoTotalPago= montoCredito.add(montoEfectivo).add(montoTarjeta);

        if (montoTotalPago.compareTo(totalVenta) < 0) {
            throw new Exception("El monto total del pago es insuficiente para cubrir el total de la venta");
        }

        BigDecimal montoRestante = totalVenta;

        if (montoCredito.compareTo(creditoDisponible) > 0) {
            throw new Exception("Monto de crédito excede el crédito disponible del cliente");
        }

        if (montoCredito.compareTo(montoRestante) > 0) {
            montoCredito = montoRestante;
        }

        montoRestante = montoRestante.subtract(montoCredito);
        cliente.setCredito(creditoDisponible.subtract(montoCredito));

        if (montoRestante.compareTo(BigDecimal.ZERO) > 0) {
            if (montoEfectivo.compareTo(montoRestante) > 0) {
                montoEfectivo = montoRestante;
            }

            montoRestante = montoRestante.subtract(montoEfectivo);
        }

        if (montoRestante.compareTo(BigDecimal.ZERO) > 0) {
            if (montoTarjeta.compareTo(montoRestante) > 0) {
                montoTarjeta = montoRestante;
            }

            montoRestante = montoRestante.subtract(montoTarjeta);
        }

        ventaEncontrada.setFormaPago(String.format("credito: %s, efectivo: %s, tarjeta: %s", montoCredito, montoEfectivo, montoTarjeta));
        ventaEncontrada.setEstado(true);
        ventaRepository.save(ventaEncontrada);
        clienteRepository.save(cliente);

        return ventaEncontrada;
    }

    @Override
    public Cliente pagarCredito(Long clienteId, BigDecimal monto) throws Exception {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new Exception("Cliente no encontrado"));

        cliente.setCredito(cliente.getCredito().add(monto));

        CreditoPago pago = new CreditoPago();
        pago.setCliente(cliente);
        pago.setMonto(monto);
        pago.setFecha(LocalDate.now());

        pagoRepository.save(pago);
        clienteRepository.save(cliente);

        return cliente;
    }

    @Override
    public List<Venta> buscarEstado(Long usuarioId) throws Exception {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(()-> new Exception("Usuario no encontrado"));
        LocalDate fechaActual = LocalDate.now();
        return ventaRepository.findByFechaAndEstadoAndUsuario(fechaActual,false, usuario);
    }

    @Override
    public CrudRepository<Venta, Long> getDao() {
        return ventaRepository;
    }
}
