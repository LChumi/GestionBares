package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario,Long> {
    List<MovimientoInventario> findByfecha(LocalDate fecha);
    List<MovimientoInventario> findByFechaBetween(LocalDate fechaInicial, LocalDate fechaFinal);
}
