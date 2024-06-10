package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.Almacen;
import ec.com.lchumi.locales.models.entities.Bodega;
import ec.com.lchumi.locales.models.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlmacenRepository extends JpaRepository<Almacen,Long> {

}
