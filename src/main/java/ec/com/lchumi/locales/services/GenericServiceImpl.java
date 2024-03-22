package ec.com.lchumi.locales.services;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class GenericServiceImpl<T,ID extends Serializable> implements IGenericService<T,ID> {

    public abstract CrudRepository<T,ID> getDao();

    @Override
    public T save(T entity) {
        return getDao().save(entity);
    }

    @Override
    public T porId(ID id) {
        Optional<T> obj= getDao().findById(id);
        return obj.orElse(null);
    }

    @Override
    public List<T> findByAll() {
        List<T> list= new ArrayList<>();
        getDao().findAll().forEach(list::add);
        return list;
    }

    @Override
    public void delete(ID id) {
        getDao().deleteById(id);
    }
}
