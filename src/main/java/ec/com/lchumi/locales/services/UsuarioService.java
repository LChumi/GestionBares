package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.auth.UserRequest;
import ec.com.lchumi.locales.models.entities.Usuario;

public interface UsuarioService extends IGenericService<Usuario,Long> {

    UserRequest login(UserRequest userRequest);
}
