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

    private final AlmacenProductoRepository almacenProductoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final ProductoRepository productoRepository;
    private final BodegaRepository bodegaRepository;
    private final EntradaInventarioRepositorio entradaInventarioRepositorio;

    @Override
    public void agregarStock(Long productoId, Long bodegaId, int cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("No existe el producto"));
        Bodega bodega = bodegaRepository.findById(bodegaId)
                .orElseThrow(() -> new RuntimeException("No existe la bodega"));

        AlmacenProducto almacenProducto = almacenProductoRepository
                .findByProductoAndBodega(producto, bodega)
                .orElse(new AlmacenProducto(producto, bodega, 0));

        almacenProducto.setStock(almacenProducto.getStock() + cantidad);
        almacenProductoRepository.save(almacenProducto);

        registrarMovimientoInventario(producto, bodega, cantidad, TipoMovimientoEnum.ENTRADA);
    }

    @Override
    public void reducirStock(Long productoId, Long bodegaId, int cantidad) throws Exception {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("No existe el producto"));
        Bodega bodega = bodegaRepository.findById(bodegaId)
                .orElseThrow(() -> new RuntimeException("No existe la bodega"));

        AlmacenProducto almacenProducto = almacenProductoRepository
                .findByProductoAndBodega(producto, bodega)
                .orElseThrow(() -> new Exception("Stock insuficiente"));

        if (almacenProducto.getStock() < cantidad) {
            throw new Exception("Stock insuficiente");
        }
        almacenProducto.setStock(almacenProducto.getStock() - cantidad);
        almacenProductoRepository.save(almacenProducto);

        registrarMovimientoInventario(producto, bodega, cantidad, TipoMovimientoEnum.SALIDA);
    }

    private void registrarMovimientoInventario(Producto producto, Bodega bodega, int cantidad, TipoMovimientoEnum tipo) {
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setBodega(bodega);
        movimiento.setCantidad(cantidad);
        movimiento.setTipo(tipo);
        movimiento.setFecha(LocalDate.now());
        movimientoInventarioRepository.save(movimiento);
    }

    @Override
    public void registrarEntradaInventaro(EntradaInventario entradaInventario) {
        // Actualizar stock en AlmacenProducto
        AlmacenProducto almacenProducto = almacenProductoRepository.findByProductoAndBodega(entradaInventario.getProducto(), entradaInventario.getBodega())
                .orElse(new AlmacenProducto());

        almacenProducto.setProducto(entradaInventario.getProducto());
        almacenProducto.setBodega(entradaInventario.getBodega());
        almacenProducto.setStock(almacenProducto.getStock() + entradaInventario.getCantidad());
        almacenProductoRepository.save(almacenProducto);

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
