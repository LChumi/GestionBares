package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.entities.Rol;
import ec.com.lchumi.locales.services.IRolService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rol/")
@CrossOrigin("*")
@Slf4j
public class RolController {

    @Autowired
    private IRolService rolService;

    @GetMapping("listar")
    public ResponseEntity<List<Rol>> listar(){
        try {
            return ResponseEntity.ok(rolService.findByAll());
        }catch (Exception e){
            log.error("Error interno en el servicio {} ",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("crear")
    public ResponseEntity<Rol> crear(@Valid @RequestBody Rol rol){
        try {
            Rol rol_nuevo= rolService.save(rol);
            return ResponseEntity.ok(rol_nuevo);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("porId/{id}")
    public ResponseEntity<Rol> porId(@PathVariable Long id){
        try {
            Rol encontrado = rolService.porId(id);
            if (encontrado == null ){
                log.info("Rol no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(encontrado);
        }catch(Exception e){
            log.error("Error en el servicio al buscar {} ",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Rol> actualizar(@PathVariable Long id, @Valid @RequestBody Rol rol){
        try {
            Rol encontrado = rolService.porId(id);
            if (encontrado == null ){
                log.info("Rol no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            encontrado.setCodigo(rol.getCodigo());
            encontrado.setDescripcion(rol.getDescripcion());
            Rol actualizado = rolService.save(encontrado);
            return ResponseEntity.ok(actualizado);
        }catch(Exception e){
            log.error("Error en el servicio al actualizar {} ",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        try {
            Rol encontrado = rolService.porId(id);
            if (encontrado == null ){
                log.info("Rol no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            rolService.delete(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            log.error("Error en el servicio al eliminar {} ",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
