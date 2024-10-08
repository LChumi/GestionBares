package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.AlmacenProducto;
import ec.com.lchumi.locales.models.entities.Producto;
import ec.com.lchumi.locales.repository.AlmacenProductoRepository;
import ec.com.lchumi.locales.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl extends GenericServiceImpl<Producto,Long> implements IProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private AlmacenProductoRepository almacenProductoRepository;

    @Override
    public CrudRepository<Producto, Long> getDao() {
        return productoRepository;
    }

    @Override
    public Producto findByNombre(String nombre) {
        return productoRepository.findByDescripcion(nombre);
    }

    @Override
    public Producto findByBarra(String barra) {
        return productoRepository.findByBarra(barra);
    }

    @Override
    public List<Producto> findByNombreOrBarra(String data) {
        return productoRepository.findByDescripcionOrBarra(data);
    }

    @Override
    public List<AlmacenProducto> listarProductos() {
        return almacenProductoRepository.findAll();
    }

    @Override
    public void elimarProductoAlmacen(Long id) {
        almacenProductoRepository.deleteById(id);
    }
}
