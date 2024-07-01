package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.auth.UserRequest;
import ec.com.lchumi.locales.models.entities.Usuario;
import jakarta.transaction.Transactional;

public interface IUsuarioService extends IGenericService<Usuario,Long> {

    UserRequest login(UserRequest userRequest);
    Usuario porUsername(String username);
    @Transactional
    void agregarAlmacen(Long usuarioId, Long almacenId);
    @Transactional
    void eliminarAlmacen(Long usuarioId, Long almacenId);
}
