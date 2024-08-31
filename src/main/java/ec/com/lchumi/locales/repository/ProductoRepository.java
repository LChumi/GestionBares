package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    Producto findByBarra(String barra);
    Producto findByDescripcion(String nombre);

    @Query("SELECT p FROM Producto p WHERE LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR p.barra = :searchTerm")
    Object findByDescripcionOrBarra(@Param("searchTerm") String searchTerm);

}
