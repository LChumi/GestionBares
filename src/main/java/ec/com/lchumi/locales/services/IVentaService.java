package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Venta;
import jakarta.transaction.Transactional;

public interface IVentaService{

    @Transactional
    Venta registrarVenta(Venta venta) throws Exception;
}
