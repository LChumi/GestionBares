package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
