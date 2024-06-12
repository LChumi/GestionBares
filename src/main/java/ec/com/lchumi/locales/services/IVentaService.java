package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.DetalleVenta;
import ec.com.lchumi.locales.models.entities.Venta;
import jakarta.transaction.Transactional;

public interface IVentaService extends IGenericService<Venta, Long>{

    @Transactional
    DetalleVenta agregarDetalle(Long ventaId, DetalleVenta detalleVenta) throws  Exception;
    @Transactional
    void eliminarDetalle(Long ventaId, Long detalleId) throws Exception;
    @Transactional
    DetalleVenta actualizarDetalle(Long ventaId, Long detalleId, DetalleVenta detalleVenta) throws Exception;
}
