package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.entities.Cliente;
import ec.com.lchumi.locales.services.IClienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
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
            log.error("Error en el servicio listar {} ",e.getMessage());
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
            Cliente actualizado = clienteService.save(encontrado);

            return ResponseEntity.ok(actualizado);
        }catch (Exception e){
            log.error("Error en el servicio actualizar {}",e.getMessage());
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
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            log.error("Error en el servicio eliminar: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("{clienteId}/credito")
    public ResponseEntity<?> actualizarCredito(
            @PathVariable Long clienteId,
            @RequestBody @Valid @DecimalMin(value = "0.0", inclusive = false) BigDecimal monto) {

        try {
            clienteService.actualizarCredito(clienteId, monto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException e) {
            log.error("Monto inválido: {}", monto, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Monto inválido");
        } catch (Exception e) {
            log.error("Error en el servicio actualizarCredito", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @GetMapping("buscar-cliente/{data}")
    public ResponseEntity<Object> buscarCliente(@PathVariable String data) {
        try {
            Object o = clienteService.buscarCliente(data);
            return ResponseEntity.ok(o);
        } catch (Exception e) {
            log.error("Error en el servicio buscarCliente {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
