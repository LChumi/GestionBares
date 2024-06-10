package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.entities.Venta;
import ec.com.lchumi.locales.services.IVentaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venta/")
@CrossOrigin("*")
@Slf4j
public class VentaController {

    @Autowired
    private IVentaService ventaService;

    @PostMapping("registrar-venta")
    public ResponseEntity<Venta> registrarVenta(@RequestBody Venta venta) {
        try {
            Venta nuevaVenta = ventaService.registrarVenta(venta);
            return ResponseEntity.ok(nuevaVenta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
