package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.AlmacenProducto;
import ec.com.lchumi.locales.models.entities.Bodega;
import ec.com.lchumi.locales.models.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlmacenProductoRepository extends JpaRepository<AlmacenProducto,Long> {

    Optional<AlmacenProducto> findByProductoAndBodega(Producto producto, Bodega bodega);
}
