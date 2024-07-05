package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Producto;
import ec.com.lchumi.locales.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl extends GenericServiceImpl<Producto,Long> implements IProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Override
    public CrudRepository<Producto, Long> getDao() {
        return productoRepository;
    }

    @Override
    public Producto findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    @Override
    public Producto findByBarra(String barra) {
        return productoRepository.findByBarra(barra);
    }
}
