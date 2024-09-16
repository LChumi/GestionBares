package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Proveedor;

public interface IProveedorService extends IGenericService<Proveedor,Long> {
    Proveedor findByCedRuc(String cedRuc);
}
