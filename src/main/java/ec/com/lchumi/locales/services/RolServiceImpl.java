package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Rol;
import ec.com.lchumi.locales.models.enums.RolEnum;
import ec.com.lchumi.locales.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl extends GenericServiceImpl<Rol,Long> implements IRolService {
    @Autowired
    private RolRepository rolRepository;

    @Override
    public CrudRepository<Rol, Long> getDao() {
        return rolRepository;
    }

    @Override
    public Rol findByCodigo(RolEnum codigo) {
        return rolRepository.findByCodigo(codigo);
    }
}
