package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Bodega;
import ec.com.lchumi.locales.repository.BodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class BodegaServiceImpl extends GenericServiceImpl<Bodega,Long> implements BodegaService {

    @Autowired
    private BodegaRepository bodegaRepository;

    @Override
    public CrudRepository<Bodega, Long> getDao() {
        return bodegaRepository;
    }
}
