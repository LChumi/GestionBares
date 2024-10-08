package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.entities.AlmacenProducto;
import ec.com.lchumi.locales.models.entities.Producto;
import ec.com.lchumi.locales.services.IProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto/")
@CrossOrigin("*")
@Slf4j
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @PostMapping("crear")
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        try {
            Producto nuevo = productoService.save(producto);
            return ResponseEntity.ok(nuevo);
        }catch (Exception e){
            log.error("Error en el servicio crear producto {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("porId/{id}")
    public ResponseEntity<Producto> porID(@PathVariable Long id) {
        try {
            Producto encontrado = productoService.porId(id);
            if (encontrado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(encontrado);
        }catch (Exception e){
            log.error("Error en el servicio buscar producto {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("listar")
    public ResponseEntity<List<Producto>> listar(){
        try {
            List<Producto> productos = productoService.findByAll();
            return ResponseEntity.ok(productos);
        }catch (Exception e){
            log.error("Error en el servicio listar productos {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Producto> actualizar(@RequestBody Producto producto, @PathVariable Long id) {
        try {
            Producto encontrado = productoService.porId(id);
            if (encontrado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            encontrado.setDescripcion(producto.getDescripcion());
            encontrado.setBarra(producto.getBarra());
            encontrado.setCosto(producto.getCosto());
            encontrado.setPrecio1(producto.getPrecio1());
            encontrado.setPrecio2(producto.getPrecio2());
            encontrado.setPrecio3(producto.getPrecio3());

            Producto actualizado = productoService.save(encontrado);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e){
            log.error("Error en el servicio actualizar producto {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("elminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            Producto encontrado = productoService.porId(id);
            if (encontrado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            log.error("Error en el servicio eliminar producto {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("porNombre/{nombre}")
    public ResponseEntity<Producto> porNombre(@PathVariable String nombre) {
        try {
            Producto encontrado = productoService.findByNombre(nombre);
            if (encontrado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(encontrado);
        }catch (Exception e){
            log.error("Error en el servicio buscar producto por nombre {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("porBarra/{barra}")
    public ResponseEntity<Producto> porBarra(@PathVariable String barra) {
        try {
            Producto encontrado = productoService.findByBarra(barra);
            if (encontrado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(encontrado);
        }catch (Exception e){
            log.error("Error en el servicio buscar producto por barra {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("barra-desc/{data}")
    public ResponseEntity<List<Producto>> porBarraDesc(@PathVariable String data) {
        try {
            List<Producto> encontrado = productoService.findByNombreOrBarra(data);
            if (encontrado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(encontrado);
        }catch (Exception e){
            log.error("Error en el servicio buscar producto por barra o descripcion {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("productos-almacen")
    public ResponseEntity<List<AlmacenProducto>> findAllProducts(){
        try {
            List<AlmacenProducto> productos = productoService.listarProductos();
            return ResponseEntity.ok(productos);
        }catch (Exception e){
            log.error("Error en el servicio buscar productos- almacen{}",e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @DeleteMapping("eliminarInv/{id}")
    public ResponseEntity<Void> eliminarInv(@PathVariable Long id) {
        try{
            productoService.elimarProductoAlmacen(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            log.error("Error en el servicio eliminar producto- Almacen  {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
