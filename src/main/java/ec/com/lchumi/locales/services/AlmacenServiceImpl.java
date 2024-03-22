package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Almacen;
import ec.com.lchumi.locales.repository.AlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlmacenServiceImpl extends GenericServiceImpl<Almacen,Long> implements AlmacenService {

    @Autowired
    private AlmacenRepository almacenRepository;

    @Override
    public List<Almacen> listarAlmacenes() {
        return almacenRepository.findAll();
    }

    @Override
    public CrudRepository<Almacen, Long> getDao() {
        return almacenRepository;
    }
}
