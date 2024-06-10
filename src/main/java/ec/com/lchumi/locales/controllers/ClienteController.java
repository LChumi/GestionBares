package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.entities.Cliente;
import ec.com.lchumi.locales.services.IClienteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cliente/")
@CrossOrigin("*")
@Slf4j
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("listar")
    public ResponseEntity<List<Cliente>> lista(){
        try{
            List<Cliente> clientes =clienteService.findByAll();
            return ResponseEntity.ok(clientes);
        }catch (Exception e){
            log.error("Error en el servicio {} ",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("porId/{id}")
    public ResponseEntity<Cliente> porID(@PathVariable Long id){
        try {
            Cliente encontrado = clienteService.porId(id);
            if (encontrado == null ){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(encontrado);
        }catch (Exception e){
            log.error("Error en el servicio porId {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("crear")
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente){
        try {
            Cliente nuevo = clienteService.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        }catch (Exception e){
            log.error("Error en el servicio crear {} ",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id , @RequestBody Cliente cliente){
        try {
            Cliente encontrado = clienteService.porId(id);
            if (encontrado == null ){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            encontrado.setNombre(cliente.getNombre());
            encontrado.setApellido(cliente.getApellido());
            encontrado.setDireccion(cliente.getDireccion());
            encontrado.setEmail(cliente.getEmail());
            encontrado.setDireccion(cliente.getDireccion());
            return ResponseEntity.ok(encontrado);
        }catch (Exception e){
            log.error("Error en el servicio porId {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Void> elmiminar(@PathVariable Long id){
        try{
            Cliente encontrado=clienteService.porId(id);
            if (encontrado == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            clienteService.delete(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            log.error("Error en el servicio eliminar: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("{clienteId}/credito")
    public ResponseEntity<String> actualizarCredito(@PathVariable Long clienteId, @RequestBody BigDecimal monto){
        try {
            clienteService.actualizarCredito(clienteId, monto);
            return ResponseEntity.status(HttpStatus.OK).body("Credito actualizado exitosamente");
        }catch (Exception e){
            log.error("Error en el servicio actualizarCredito {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
