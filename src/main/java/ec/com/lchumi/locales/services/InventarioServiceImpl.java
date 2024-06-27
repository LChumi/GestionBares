package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.*;
import ec.com.lchumi.locales.models.enums.TipoMovimientoEnum;
import ec.com.lchumi.locales.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InventarioServiceImpl implements IInventarioService{

    private static final Logger log = LoggerFactory.getLogger(InventarioServiceImpl.class);
    private final AlmacenProductoRepository almacenProductoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final ProductoRepository productoRepository;
    private final BodegaRepository bodegaRepository;
    private final EntradaInventarioRepositorio entradaInventarioRepositorio;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void agregarStock(Long productoId, Long bodegaId, int cantidad ,Long usuarioId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("No existe el producto"));
        Bodega bodega = bodegaRepository.findById(bodegaId)
                .orElseThrow(() -> new RuntimeException("No existe la bodega"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario"));

        AlmacenProducto almacenProducto = almacenProductoRepository
                .findByProductoAndBodega(producto, bodega)
                .orElseThrow(()-> new RuntimeException("No se encontro el producto en la bodega "));

        almacenProducto.setStock(almacenProducto.getStock() + cantidad);
        almacenProductoRepository.save(almacenProducto);

        registrarMovimientoInventario(producto, bodega, cantidad, TipoMovimientoEnum.ENTRADA,usuario);
    }

    @Override
    public void reducirStock(Long productoId, Long bodegaId, int cantidad,Long usuarioId) throws Exception {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("No existe el producto"));
        Bodega bodega = bodegaRepository.findById(bodegaId)
                .orElseThrow(() -> new RuntimeException("No existe la bodega"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario"));

        AlmacenProducto almacenProducto = almacenProductoRepository
                .findByProductoAndBodega(producto, bodega)
                .orElseThrow(() -> new Exception("Stock insuficiente"));

        if (almacenProducto.getStock() < cantidad) {
            throw new Exception("Stock insuficiente");
        }
        almacenProducto.setStock(almacenProducto.getStock() - cantidad);
        almacenProductoRepository.save(almacenProducto);

        registrarMovimientoInventario(producto, bodega, cantidad, TipoMovimientoEnum.SALIDA,usuario);
    }

    private void registrarMovimientoInventario(Producto producto, Bodega bodega, int cantidad, TipoMovimientoEnum tipo,Usuario usuario) {
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setBodega(bodega);
        movimiento.setCantidad(cantidad);
        movimiento.setTipo(tipo);
        movimiento.setFecha(LocalDate.now());
        movimiento.setUsuario(usuario);
        movimientoInventarioRepository.save(movimiento);
    }

    @Override
    public void registrarEntradaInventaro(EntradaInventario entradaInventario) {
        log.info(entradaInventario.toString());
        // Actualizar stock en AlmacenProducto
        AlmacenProducto almacenProducto = almacenProductoRepository.findByProductoAndBodega(entradaInventario.getProducto(), entradaInventario.getBodega())
                .orElse(new AlmacenProducto());

        almacenProducto.setProducto(entradaInventario.getProducto());
        almacenProducto.setBodega(entradaInventario.getBodega());
        almacenProducto.setStock(almacenProducto.getStock() + entradaInventario.getCantidad());
        almacenProductoRepository.save(almacenProducto);
        log.info(almacenProducto.toString());

        // Registrar movimiento de inventario
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(entradaInventario.getProducto());
        movimiento.setBodega(entradaInventario.getBodega());
        movimiento.setCantidad(entradaInventario.getCantidad());
        movimiento.setTipo(TipoMovimientoEnum.ENTRADA);
        movimiento.setFecha(LocalDate.now());
        movimiento.setUsuario(entradaInventario.getUsuario());
        movimientoInventarioRepository.save(movimiento);
        log.info(movimiento.toString());

        // Registrar entrada de inventario
        entradaInventarioRepositorio.save(entradaInventario);
    }

    @Override
    public void transferirProducto(Long productoId, Long bodegaOrigenId, Long bodegaDesitnoId, int cantidad,Long usuarioId) throws Exception {
        Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new RuntimeException("No existe el producto"));
        Bodega bodegaOrigen = bodegaRepository.findById(bodegaOrigenId).orElseThrow(() -> new RuntimeException("No existe la bodega de origen"));
        Bodega bodegaDestino = bodegaRepository.findById(bodegaDesitnoId).orElseThrow(() -> new RuntimeException("No existe la bodega de destino "));
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("No existe el usuario"));

        AlmacenProducto almacenProductoOrigen= almacenProductoRepository.findByProductoAndBodega(producto,bodegaOrigen).orElseThrow(() -> new RuntimeException(" Stock insuficiente en la bodega de origen "));

        if (almacenProductoOrigen.getStock() < cantidad){
            throw new Exception("Stock insuficiente en la bodega de origen");
        }

        almacenProductoOrigen.setStock(almacenProductoOrigen.getStock()-cantidad);
        almacenProductoRepository.save(almacenProductoOrigen);

        AlmacenProducto almacenProductoDesitno = almacenProductoRepository.findByProductoAndBodega(producto,bodegaDestino).orElse(new AlmacenProducto());
        almacenProductoDesitno.setProducto(producto);
        almacenProductoDesitno.setBodega(bodegaDestino);
        almacenProductoDesitno.setStock(almacenProductoDesitno.getStock() + cantidad);
        almacenProductoRepository.save(almacenProductoDesitno);

        registrarMovimientoInventario(producto,bodegaOrigen,cantidad,TipoMovimientoEnum.SALIDA,usuario);
        registrarMovimientoInventario(producto,bodegaDestino,cantidad,TipoMovimientoEnum.ENTRADA,usuario);

    }
}
