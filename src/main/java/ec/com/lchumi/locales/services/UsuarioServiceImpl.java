package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.auth.UserRequest;
import ec.com.lchumi.locales.models.entities.Almacen;
import ec.com.lchumi.locales.models.entities.Usuario;
import ec.com.lchumi.locales.repository.AlmacenRepository;
import ec.com.lchumi.locales.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario,Long> implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AlmacenRepository almacenRepository;

    @Override
    public CrudRepository<Usuario, Long> getDao() {
        return usuarioRepository;
    }

    @Override
    public UserRequest login(UserRequest userRequest) {
        try {
            Usuario usuario=usuarioRepository.findByNombreUsuarioAndPassword(userRequest.getUsername(), userRequest.getPassword());
            if (usuario == null ){
                log.error("Usuario no Encontrado");
                return null;
            }
            return UserRequest.builder().username(usuario.getNombre()).password(usuario.getPassword()).build();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void agregarAlmacen(Long usuarioId, Long almacenId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Almacen almacen = almacenRepository.findById(almacenId).orElseThrow(()-> new IllegalArgumentException("Almacen no enconrado"));

        if (usuario.getAlmacenes().contains(almacen)) {
            throw new IllegalArgumentException("El usuario ya tiene asignado el almacen");
        }

        usuario.addAlmacen(almacen);
        usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarAlmacen(Long usuarioId, Long almacenId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Almacen almacen = almacenRepository.findById(almacenId)
                .orElseThrow(() -> new IllegalArgumentException("Almacén no encontrado"));

        usuario.removeAlmacen(almacen);
        usuarioRepository.save(usuario);
    }
}
