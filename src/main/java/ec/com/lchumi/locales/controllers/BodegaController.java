package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.entities.Bodega;
import ec.com.lchumi.locales.services.IBodegaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bodega/")
@CrossOrigin("*")
@Slf4j
public class BodegaController {
    @Autowired
    private IBodegaService bodegaService;

    @PostMapping("crear")
    public ResponseEntity<Bodega> crear(@Valid @RequestBody Bodega bodega){
        try {
            Bodega bodegaNueva = bodegaService.save(bodega);
            return ResponseEntity.status(HttpStatus.CREATED).body(bodegaNueva);
        }catch (Exception e){
            log.error("Error en el servicio crear bodega {}", bodega.getNombre());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("porId/{id}")
    public ResponseEntity<Bodega> porID(@PathVariable Long id){
        try {
            Bodega encontrada= bodegaService.porId(id);
            if (encontrada == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(encontrada);
        }catch (Exception e){
            log.error("Error en el servicio porId {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("listar")
    public ResponseEntity<List<Bodega>> listar(){
        try {
            List<Bodega> bodegas = bodegaService.findByAll();
            return ResponseEntity.ok(bodegas);
        }catch (Exception e){
            log.error("Error en el servicio {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Bodega> actualizar(@PathVariable Long id, @RequestBody Bodega bodega){
        try {
            Bodega encontrada = bodegaService.porId(id);
            if (encontrada == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            encontrada.setNombre(bodega.getNombre());
            if (bodega.getAlmacen() != null){
                encontrada.setAlmacen(bodega.getAlmacen());
            }
            Bodega actualizada = bodegaService.save(encontrada);
            return ResponseEntity.ok(actualizada);
        }catch (Exception e){
            log.error("Error en el servicio actualizar id:{}, nombre:{}",id,bodega.getNombre(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        try {
            Bodega encontrada = bodegaService.porId(id);
            if (encontrada == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            bodegaService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            log.error("Error en el servicio eliminar id: {}", id , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
