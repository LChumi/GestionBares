package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.auth.UserRequest;
import ec.com.lchumi.locales.models.entities.Usuario;
import ec.com.lchumi.locales.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario,Long> implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public CrudRepository<Usuario, Long> getDao() {
        return usuarioRepository;
    }

    @Override
    public UserRequest login(UserRequest userRequest) {
        try {
            Usuario usuario=usuarioRepository.findByNombreUsuarioAndContraseña(userRequest.getUsername(), userRequest.getPassword());
            if (usuario == null ){
                log.error("Usuario no Encontrado");
                return null;
            }
            return UserRequest.builder().username(usuario.getNombre()).password(usuario.getContraseña()).build();
        }catch (Exception e){
            return null;
        }
    }
}
