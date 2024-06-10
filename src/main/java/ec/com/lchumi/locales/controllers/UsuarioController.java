package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.auth.UserRequest;
import ec.com.lchumi.locales.models.entities.Usuario;
import ec.com.lchumi.locales.services.IUsuarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/")
@CrossOrigin("*")
@Slf4j
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("listar")
    public ResponseEntity<List<Usuario>> listar(){
        try{
            List<Usuario> usuarios=usuarioService.findByAll();
            if (usuarios.isEmpty()){
                log.info("Lista Vacia ");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(usuarios);
        }catch (Exception e){
            log.error("Error interno {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("crear")
    public ResponseEntity<Usuario> crear(@Valid @RequestBody Usuario usuario){
        try{
            Usuario nuevoUsuario = usuarioService.save(usuario);
            log.info("Usuario guardado {}",usuario);
            return ResponseEntity.ok(nuevoUsuario);
        }catch (Exception e){
            log.error("Erro al guardar: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("porId/{id}")
    public ResponseEntity<Usuario> porId(@PathVariable Long id){
        try{
            Usuario encontrado = usuarioService.porId(id);
            if (encontrado == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(encontrado);
        }catch(Exception e){
            log.info("Error al interno al buscar {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id , @Valid @RequestBody Usuario usuario){
        try{
            Usuario encontrado = usuarioService.porId(id);
            if (encontrado == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            encontrado.setApellido(usuario.getApellido());
            encontrado.setNombre(usuario.getNombre());
            encontrado.setContraseña(usuario.getContraseña());
            encontrado.setRol(usuario.getRol());
            encontrado.setTelefono(usuario.getTelefono());
            encontrado.setNombreUsuario(usuario.getNombreUsuario());
            Usuario actualizado = usuarioService.save(encontrado);
            return ResponseEntity.ok(actualizado);
        }catch(Exception e){
            log.info("Error al interno al actualizar {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("borrar/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id){
        Usuario encontrado = usuarioService.porId(id);
        if(encontrado == null){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("login")
    public ResponseEntity<UserRequest> login(@Valid @RequestBody UserRequest userRequest){
        try {
            UserRequest usuario= usuarioService.login(userRequest);
            if (usuario== null ){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
