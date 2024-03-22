package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
}
