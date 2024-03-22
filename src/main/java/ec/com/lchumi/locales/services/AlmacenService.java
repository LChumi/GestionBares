package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Almacen;

import java.util.List;

public interface AlmacenService extends IGenericService<Almacen,Long> {
    List<Almacen> listarAlmacenes();
}
