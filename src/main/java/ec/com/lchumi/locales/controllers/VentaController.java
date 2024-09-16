package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.entities.Cliente;
import ec.com.lchumi.locales.models.entities.DetalleVenta;
import ec.com.lchumi.locales.models.entities.Venta;
import ec.com.lchumi.locales.services.IVentaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/venta/")
@CrossOrigin("*")
@Slf4j
public class VentaController {

    @Autowired
    private IVentaService ventaService;

    @PostMapping("crear-venta")
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        try {
            Venta ventanueva = ventaService.save(venta);
            return ResponseEntity.ok(ventanueva);
        }catch (Exception e){
            log.error("Error al crear venta", e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PutMapping("modificar-venta/{id}")
    public ResponseEntity<Venta> modificarVenta(@RequestBody Venta venta, @PathVariable Long id) {
        try {
            Venta ventaEncontrada = ventaService.porId(id);
            if (ventaEncontrada == null) {
                return ResponseEntity.notFound().build();
            } else {
                ventaEncontrada.setCliente(venta.getCliente());
                Venta actualizada= ventaService.save(ventaEncontrada);
                return ResponseEntity.ok(actualizada);
            }
        }catch (Exception e){
            log.error("Error al modificar venta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("{ventaId}/{tipoPrecio}/detalles-add")
    public ResponseEntity<Venta> agregarDetalle(@PathVariable Long ventaId, @RequestBody DetalleVenta detalleVenta, @PathVariable int tipoPrecio) {
        try {
            Venta venta = ventaService.agregarDetalle(ventaId,detalleVenta,tipoPrecio);
            return ResponseEntity.status(HttpStatus.CREATED).body(venta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("{ventaId}/detalles-del/{detalleId}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long ventaId, @PathVariable Long detalleId) {
        try {
            ventaService.eliminarDetalle(ventaId,detalleId);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("{ventaId}/detalles-put/{detalleId}/{tipoPrecio}")
    public ResponseEntity<DetalleVenta> actualizarDetalle(@PathVariable Long ventaId, @PathVariable Long detalleId, @PathVariable int tipoPrecio, @RequestBody DetalleVenta detalleActualizado) {
        try {
            DetalleVenta detalle = ventaService.actualizarDetalle(ventaId, detalleId, detalleActualizado,tipoPrecio);
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("{ventaId}/procesar-pago")
    public ResponseEntity<Venta> procesarPago(@PathVariable Long ventaId,
                                              @RequestParam BigDecimal montoCredito,
                                              @RequestParam BigDecimal montoEfectivo,
                                              @RequestParam BigDecimal montoTarjeta) {
        try {
            Venta venta = ventaService.procesarPago(ventaId, montoCredito, montoEfectivo, montoTarjeta);
            return ResponseEntity.ok(venta);
        } catch (Exception e) {
            log.error("Error al procesar pago", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("{clienteId}/pagar-credito")
    public ResponseEntity<Cliente> pagarCredito(@PathVariable Long clienteId, @RequestParam BigDecimal monto) {
        try {
            Cliente cliente = ventaService.pagarCredito(clienteId, monto);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            log.error("Error al pagar crédito", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("listar")
    public  ResponseEntity<List<Venta>> listar(){
        try {
            List<Venta> ventas =ventaService.findByAll();
            return ResponseEntity.ok(ventas);
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("listar-pendientes/{usuarioId}")
    public  ResponseEntity<List<Venta>> listarPendientes(@PathVariable Long usuarioId){
        try {
            List<Venta> ventas =ventaService.buscarEstado(usuarioId);
            return ResponseEntity.ok(ventas);
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("totales")
    public BigDecimal totales(){
        try {
            return ventaService.obtenerTotales();
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}
