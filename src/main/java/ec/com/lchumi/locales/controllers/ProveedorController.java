package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.entities.Proveedor;
import ec.com.lchumi.locales.services.IProveedorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedor/")
@CrossOrigin("*")
@Slf4j
public class ProveedorController {

    @Autowired
    private IProveedorService proveedorService;

    @PostMapping("crear")
    public ResponseEntity<Proveedor> crear(@RequestBody Proveedor proveedor) {
        log.info("Creando proveedor");
        try {
            Proveedor nuevo = proveedorService.save(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        }catch (Exception e){
            log.error("Error al crear el proveedor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("listar")
    public ResponseEntity<List<Proveedor>> listar() {
        log.info("Listando proveedor");
        try {
            List<Proveedor> proveedores = proveedorService.findByAll();
            return ResponseEntity.status(HttpStatus.OK).body(proveedores);
        }catch (Exception e){
            log.error("Error al listar el proveedor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("porId/{id}")
    public ResponseEntity<Proveedor> buscarPorId(@PathVariable Long id) {
        log.info("Buscando proveedor por id: {}", id);
        try {
            Proveedor encontrado = proveedorService.porId(id);
            if (encontrado == null ){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(encontrado);
        }catch (Exception e){
            log.error("Error al buscar el proveedor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Proveedor> actualizar(@RequestBody Proveedor proveedor, @PathVariable Long id) {
        log.info("Actualizando proveedor: {}", id);
        try {
            Proveedor encontrado = proveedorService.porId(id);
            if (encontrado == null ){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            encontrado.setNombre(proveedor.getNombre());
            encontrado.setTelefono(proveedor.getTelefono());
            encontrado.setEmail(proveedor.getEmail());
            encontrado.setDireccion(proveedor.getDireccion());
            Proveedor actualizado = proveedorService.save(encontrado);
            return ResponseEntity.status(HttpStatus.OK).body(actualizado);
        }catch (Exception e){
            log.error("Error al actualizar el proveedor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
