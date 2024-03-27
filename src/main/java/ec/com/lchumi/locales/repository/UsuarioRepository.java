package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Usuario findByNombreUsuarioAndContraseña(String nombre,String pasword);
}
