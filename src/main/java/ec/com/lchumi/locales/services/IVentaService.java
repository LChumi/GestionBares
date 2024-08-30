package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Cliente;
import ec.com.lchumi.locales.models.entities.DetalleVenta;
import ec.com.lchumi.locales.models.entities.Venta;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface IVentaService extends IGenericService<Venta, Long>{

    @Transactional
    DetalleVenta agregarDetalle(Long ventaId, DetalleVenta detalleVenta, int tipoPrecio) throws  Exception;
    @Transactional
    void eliminarDetalle(Long ventaId, Long detalleId) throws Exception;
    @Transactional
    DetalleVenta actualizarDetalle(Long ventaId, Long detalleId, DetalleVenta detalleVenta, int tipoPrecio) throws Exception;

    Venta procesarPago(Long ventaId, BigDecimal montoCredito, BigDecimal montoEfectivo, BigDecimal montoTarjeta) throws Exception;
    Cliente pagarCredito(Long clienteId, BigDecimal monto) throws Exception;

    List<Venta> buscarEstado(Long usuarioId) throws Exception;
}
