package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Rol;
import ec.com.lchumi.locales.models.enums.RolEnum;

public interface IRolService extends IGenericService<Rol,Long>{
    Rol findByCodigo(RolEnum codigo);
}
