package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.entities.EntradaInventario;
import ec.com.lchumi.locales.models.entities.MovimientoInventario;
import ec.com.lchumi.locales.services.IInventarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/")
@CrossOrigin("*")
@Slf4j
public class InventarioController {

    @Autowired
    private IInventarioService inventarioService;

    @PostMapping("/entrada")
    public ResponseEntity<String> registrarEntradaInventario(@RequestBody EntradaInventario entradaInventario) {
        try {
            inventarioService.registrarEntradaInventario(entradaInventario);
            return ResponseEntity.ok("Entrada de inventarii registrada exitosamente");
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarStock(@RequestBody MovimientoInventario request) {
        inventarioService.agregarStock(request.getProducto().getId(), request.getBodega().getId(), request.getCantidad());
        return ResponseEntity.ok("Stock agregado exitosamente");
    }

    @PostMapping("/reducir")
    public ResponseEntity<String> reducirStock(@RequestBody MovimientoInventario request) {
        try {
            inventarioService.reducirStock(request.getProducto().getId(), request.getBodega().getId(), request.getCantidad());
            return ResponseEntity.ok("Stock reducido exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
