package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.AlmacenProducto;
import ec.com.lchumi.locales.models.entities.Producto;

import java.util.List;

public interface IProductoService extends IGenericService<Producto,Long> {
    Producto findByNombre(String nombre);
    Producto findByBarra(String barra);
    List<Producto> findByNombreOrBarra(String data);
    List<AlmacenProducto> listarProductos();

    void elimarProductoAlmacen(Long id);
}
