package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Venta;
import ec.com.lchumi.locales.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class VentaServiceImpl extends GenericServiceImpl<Venta,Long> implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public CrudRepository<Venta, Long> getDao() {
        return ventaRepository;
    }
}
