package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Producto;

public interface IProductoService extends IGenericService<Producto,Long> {
    Producto findByNombre(String nombre);
    Producto findByBarra(String barra);
}
