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

    @PostMapping("agregar-producto")
    public ResponseEntity<String> agregarProducto(@RequestBody EntradaInventario entradaInventario) {
        try {
            inventarioService.registrarEntradaInventaro(entradaInventario);
            return ResponseEntity.ok("Producto agregado y stock actualizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar producto: " + e.getMessage());
        }
    }

    @GetMapping("incrementar-stock/{productoId}/{bodegaId}/{cantidad}")
    public ResponseEntity<String> incrementarStock(@PathVariable Long productoId, @PathVariable Long bodegaId, @PathVariable int cantidad) {
        try {
            inventarioService.agregarStock(productoId, bodegaId, cantidad);
            return ResponseEntity.ok("Stock incrementado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error al incrementar stock: " + e.getMessage());
        }
    }

    @GetMapping("reducir-stock/{productoId}/{bodegaId}/{cantidad}")
    public ResponseEntity<String> reducirStock(@PathVariable Long productoId, @PathVariable Long bodegaId, @PathVariable int cantidad) {
        try {
            inventarioService.reducirStock(productoId, bodegaId, cantidad);
            return ResponseEntity.ok("Stock reducido correctamente.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error al reducir stock: " + e.getMessage());
        }
    }
}
