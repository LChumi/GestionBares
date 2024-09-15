package ec.com.lchumi.locales.context;

import ec.com.lchumi.locales.models.entities.Cliente;
import ec.com.lchumi.locales.models.entities.Rol;
import ec.com.lchumi.locales.models.entities.Usuario;
import ec.com.lchumi.locales.models.enums.RolEnum;
import ec.com.lchumi.locales.services.IClienteService;
import ec.com.lchumi.locales.services.IRolService;
import ec.com.lchumi.locales.services.IUsuarioService;
import ec.com.lchumi.locales.services.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IRolService rolService;
    private final IUsuarioService usuarioService;
    private final IClienteService clienteService;

    @Override
    public void run(String... args) throws Exception {

        for(RolEnum rolEnum: RolEnum.values()){
            guardarRolSiNoExiste(rolEnum);
        }
        guardarUsuarioSiNoExiste();
        guardarClienteSiNoExiste();
    }

    private void guardarUsuarioSiNoExiste(){
        if (usuarioService.porUsername("admin") == null){
            Rol rol = rolService.findByCodigo(RolEnum.ROLE_ADMIN);
            if (rol != null){
                Usuario usuario = new Usuario();
                usuario.setNombre("admin");
                usuario.setNombreUsuario("admin");
                usuario.setApellido("admin");
                usuario.setRol(rol);
                usuario.setPassword("data_admin");
                usuarioService.save(usuario);
            }
        }
    }

    private void guardarClienteSiNoExiste(){
        if (clienteService.findByCedula("9999999999") == null){
            Cliente cliente = new Cliente();
            cliente.setNombre("Consumidor");
            cliente.setCedula("9999999999");
            cliente.setApellido("Final");
            cliente.setDireccion("S/N");
            cliente.setTelefono("9999999999");
            cliente.setCupo(new BigDecimal("0.0"));
            clienteService.save(cliente);
        }
    }

    private void guardarRolSiNoExiste(RolEnum codigo){
        if (rolService.findByCodigo(codigo) == null){
            Rol rol = new Rol();
            rol.setCodigo(codigo);
            rol.setDescripcion(codigo.name());
            rolService.save(rol);
        }
    }
}
