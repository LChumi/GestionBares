package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    Producto findByBarra(String barra);
    Producto findByNombre(String nombre);
}
