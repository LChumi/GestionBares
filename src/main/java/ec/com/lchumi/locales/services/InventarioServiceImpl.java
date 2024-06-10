package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.*;
import ec.com.lchumi.locales.models.enums.TipoMovimientoEnum;
import ec.com.lchumi.locales.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InventarioServiceImpl implements IInventarioService{

    private final AlmacenRepository almacenRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final ProductoRepository productoRepository;
    private final BodegaRepository bodegaRepository;
    private final EntradaInventarioRepositorio entradaInventarioRepositorio;

    @Override
    public void agregarStock(Long productoId, Long bodegaId, int cantidad) {
        Producto producto = productoRepository.findById(productoId).orElseThrow(()-> new RuntimeException("No existe el producto"));
        Bodega bodega = bodegaRepository.findById(bodegaId).orElseThrow(() -> new RuntimeException("No existe el bodega"));

        Almacen almacen = almacenRepository.findByProductoAndBodega(producto,bodega);
        if (almacen == null) {
            almacen = new Almacen();
            almacen.setStock(cantidad);
            almacen.setProducto(producto);
            almacen.setBodega(bodega);
        }else {
            almacen.setStock(almacen.getStock()+cantidad);
        }

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setBodega(bodega);
        movimiento.setCantidad(cantidad);
        movimiento.setTipo(TipoMovimientoEnum.ENTRADA);
        movimiento.setFecha(LocalDate.now());
        movimientoInventarioRepository.save(movimiento);
    }

    @Override
    public void reducirStock(Long productoId, Long bodegaId, int cantidad) throws Exception {
        Producto producto = productoRepository.findById(productoId).orElseThrow(()-> new RuntimeException("No existe el producto"));
        Bodega bodega = bodegaRepository.findById(bodegaId).orElseThrow(() -> new RuntimeException("No existe el bodega"));

        Almacen almacen = almacenRepository.findByProductoAndBodega(producto,bodega);
        if (almacen == null || almacen.getStock() < cantidad){
            throw new Exception("Stock insuficiente");
        }
        almacen.setStock(almacen.getStock() - cantidad );
        almacenRepository.save(almacen);

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setBodega(bodega);
        movimiento.setCantidad(cantidad);
        movimiento.setTipo(TipoMovimientoEnum.SALIDA);
        movimiento.setFecha(LocalDate.now());
        movimientoInventarioRepository.save(movimiento);
    }

    @Override
    public void registrarEntradaInventario(EntradaInventario entradaInventario) {
        // Actualizar stock
        Almacen almacen = almacenRepository.findByProductoAndBodega(entradaInventario.getProducto(),entradaInventario.getBodega());
        if (almacen == null) {
            almacen = new Almacen();
            almacen.setStock(entradaInventario.getCantidad());
            almacen.setProducto(entradaInventario.getProducto());
            almacen.setBodega(entradaInventario.getBodega());
        } else {
            almacen.setStock(almacen.getStock() + entradaInventario.getCantidad());
        }
        almacenRepository.save(almacen);

        // Registrar movimiento de inventario
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(entradaInventario.getProducto());
        movimiento.setBodega(entradaInventario.getBodega());
        movimiento.setCantidad(entradaInventario.getCantidad());
        movimiento.setTipo(TipoMovimientoEnum.ENTRADA);
        movimiento.setFecha(LocalDate.now());
        movimientoInventarioRepository.save(movimiento);

        // Registrar entrada de inventario
        entradaInventarioRepositorio.save(entradaInventario);
    }
}
