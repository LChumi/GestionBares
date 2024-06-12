package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.entities.Almacen;
import ec.com.lchumi.locales.services.IAlmacenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/almacen/")
@CrossOrigin("*")
@Slf4j
public class AlmacenController {

    @Autowired
    private IAlmacenService almacenService;

    @PostMapping("crear")
    public ResponseEntity<Almacen> crear(@RequestBody Almacen almacen) {
        log.info("Creando almacen");
        try {
            Almacen nuevo = almacenService.save(almacen);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        }catch (Exception e){
            log.error("Error al crear almacen {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("listar")
    public ResponseEntity<List<Almacen>> listar() {
        log.info("Listando almacen");
        try {
            List<Almacen> almacenes = almacenService.listarAlmacenes();
            return ResponseEntity.ok(almacenes);
        }catch (Exception e){
            log.error("Error al listar almacenes {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("porId/{id}")
    public ResponseEntity<Almacen> porId(@PathVariable Long id) {
        log.info("PorId de almacen {}", id);
        try {
            Almacen encontrado = almacenService.porId(id);
            if (encontrado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(encontrado);
        }catch (Exception e){
            log.error("Error al buscar almacen {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Almacen> actualizar(@RequestBody Almacen almacen, @PathVariable Long id) {
        log.info("Actualizando almacen {}", id);
        try {
            Almacen encontrado = almacenService.porId(id);
            if (encontrado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            encontrado.setNombre(almacen.getNombre());
            Almacen actualizado = almacenService.save(encontrado);
            return ResponseEntity.ok(actualizado);
        }catch (Exception e){
            log.error("Error al actualizar almacen {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Almacen> eliminar(@PathVariable Long id) {
        log.info("Eliminando almacen {}", id);
        try {
            Almacen encontrado = almacenService.porId(id);
            if (encontrado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            almacenService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            log.error("Error al eliminar almacen {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
