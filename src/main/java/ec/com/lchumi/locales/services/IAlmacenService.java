package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Almacen;

import java.util.List;

public interface IAlmacenService extends IGenericService<Almacen,Long> {
    List<Almacen> listarAlmacenes();
    Almacen buscarAlmacenPorNombre(String nombre);
}
