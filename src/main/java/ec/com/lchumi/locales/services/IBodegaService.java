package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Bodega;

public interface IBodegaService extends IGenericService<Bodega,Long>{
    Bodega findByNombre(String nombre);
}
