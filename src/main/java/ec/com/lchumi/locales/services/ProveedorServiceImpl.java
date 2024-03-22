package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Proveedor;
import ec.com.lchumi.locales.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServiceImpl extends GenericServiceImpl<Proveedor,Long> implements ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public CrudRepository<Proveedor, Long> getDao() {
        return proveedorRepository;
    }
}
