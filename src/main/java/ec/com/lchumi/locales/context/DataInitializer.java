package ec.com.lchumi.locales.context;

import ec.com.lchumi.locales.models.entities.Usuario;
import ec.com.lchumi.locales.services.IRolService;
import ec.com.lchumi.locales.services.IUsuarioService;
import ec.com.lchumi.locales.services.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IRolService rolService;
    private final IUsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {

    }
}
