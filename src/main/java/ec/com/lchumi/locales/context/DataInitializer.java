package ec.com.lchumi.locales.context;

import ec.com.lchumi.locales.models.entities.*;
import ec.com.lchumi.locales.models.enums.RolEnum;
import ec.com.lchumi.locales.services.*;
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
    private final IAlmacenService almacenService;
    private final IBodegaService bodegaService;
    private final IProveedorService proveedorService;

    @Override
    public void run(String... args) throws Exception {

        for(RolEnum rolEnum: RolEnum.values()){
            guardarRolSiNoExiste(rolEnum);
        }
        guardarUsuarioSiNoExiste();
        guardarClienteSiNoExiste();
        guardarAlmacenSiNoExiste("Barmacia");
        guardarAlmacenSiNoExiste("Punto Cero");
        guardarBodegaSiNoExiste("Bodega Barmacia","Barmacia");
        guardarBodegaSiNoExiste("Bodega Punto Cero","Punto Cero");
        guardarProveedorSiNoExiste("9999999999");
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
            cliente.setCredito(new BigDecimal("0.0"));
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

    private void guardarAlmacenSiNoExiste(String almNombre){
        if (almacenService.buscarAlmacenPorNombre(almNombre) == null){
            Almacen almacen = new Almacen();
            almacen.setNombre(almNombre);
            almacenService.save(almacen);
        }
    }
    private void guardarBodegaSiNoExiste(String bodNombre, String almNombre){
        if (bodegaService.findByNombre(bodNombre) == null){
            Almacen almacen = almacenService.buscarAlmacenPorNombre(almNombre);
            Bodega bodega = new Bodega();
            bodega.setNombre(bodNombre);
            bodega.setAlmacen(almacen);
            bodegaService.save(bodega);
        }
    }
    private void guardarProveedorSiNoExiste(String cedRuc){
        if (proveedorService.findByCedRuc(cedRuc) == null){
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre("Proveedor ST");
            proveedor.setCedulaRuc(cedRuc);
            proveedor.setTelefono("9999999999");
            proveedor.setDireccion("S/N");
            proveedorService.save(proveedor);
        }
    }
}
