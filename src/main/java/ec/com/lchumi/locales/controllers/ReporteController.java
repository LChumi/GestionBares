package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.dto.ClienteCompradorFrecuenteDTO;
import ec.com.lchumi.locales.models.dto.ClienteMayorDeudaDTO;
import ec.com.lchumi.locales.models.dto.ProductoMasVendidoDTO;
import ec.com.lchumi.locales.models.entities.MovimientoInventario;
import ec.com.lchumi.locales.models.entities.Venta;
import ec.com.lchumi.locales.services.IMovimientoInventarioService;
import ec.com.lchumi.locales.services.IReporteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes/")
@CrossOrigin("*")
@Slf4j
public class ReporteController {

    @Autowired
    private IReporteService reporteService;
    @Autowired
    private IMovimientoInventarioService movimientoInventarioService;

    @GetMapping("ventas")
    public ResponseEntity<List<Venta>> obtenerVentasPorFecha(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicial, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin){
        try {
            return ResponseEntity.ok(reporteService.obtenerVentasPorFecha(fechaInicial, fechaFin));
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("ventas-actuales")
    public ResponseEntity<List<Venta>> obtenerVentasActuales(){
        try {
            return ResponseEntity.ok(reporteService.obtenerVentasFechaHoy());
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("productos-mas-vendidos")
    public ResponseEntity<List<ProductoMasVendidoDTO>> obtenerProductoMasVendidos(){
        try {
            return ResponseEntity.ok(reporteService.obtenerProductosMasVendidos());
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("clientes-mayor-deuda")
    public ResponseEntity<List<ClienteMayorDeudaDTO>> obtenerClinetesConMayorDeuda(){
        try {
            return ResponseEntity.ok(reporteService.obtenerClientesConMayorDeuda());
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("clientes-mas-freceuntes")
    public ResponseEntity<List<ClienteCompradorFrecuenteDTO>> obtenerClineteMasFrecuente() {
        try {
            return ResponseEntity.ok(reporteService.obtenerClineteMaFrecuentes());
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("lista-movimientos")
    public ResponseEntity<List<MovimientoInventario>> listaMovimientos(){
        try {
            return ResponseEntity.ok(movimientoInventarioService.listarPorFecha());
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("lista-movimientos-fechas")
    public ResponseEntity<List<MovimientoInventario>> listarMovEntreFechas(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicial, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin){
        try {
            return ResponseEntity.ok(movimientoInventarioService.listarEntreFechas(fechaInicial,fechaFin));
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
