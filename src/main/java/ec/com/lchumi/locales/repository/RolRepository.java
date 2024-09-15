package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.Rol;
import ec.com.lchumi.locales.models.enums.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol,Long> {
    Rol findByCodigo(RolEnum codigo);
}
