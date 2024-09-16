package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor,Long> {
    Proveedor findByCedulaRuc(String cedula);
}
