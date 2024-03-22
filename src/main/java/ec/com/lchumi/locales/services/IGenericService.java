package ec.com.lchumi.locales.services;

import java.io.Serializable;
import java.util.List;

public interface IGenericService<T,ID extends Serializable> {

    T save(T entity);
    T porId(ID id);
    List<T> findByAll();
    void delete(ID id);
}
