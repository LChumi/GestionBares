package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Categoria;
import ec.com.lchumi.locales.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl extends GenericServiceImpl<Categoria,Long> implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public CrudRepository<Categoria, Long> getDao() {
        return categoriaRepository;
    }
}
