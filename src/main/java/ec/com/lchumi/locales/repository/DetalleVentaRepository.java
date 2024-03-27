package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.DetalleVenta;
import ec.com.lchumi.locales.models.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta,Long> {

    List<DetalleVenta> findAllByVenta(Venta venta);
}
