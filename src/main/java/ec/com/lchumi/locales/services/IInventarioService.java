package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.EntradaInventario;
import jakarta.transaction.Transactional;

public interface IInventarioService {

    @Transactional
    void agregarStock(Long productoId, Long bodegaId, int cantidad,Long usuarioId);

    @Transactional
    void reducirStock(Long productoId, Long bodegaId, int cantidad,Long usuarioId) throws Exception;

    @Transactional
    void registrarEntradaInventaro(EntradaInventario entradaInventario);

    @Transactional
    void transferirProducto(Long productoId, Long bodegaOrigenId, Long bodegaDesitnoId, int cantidad,Long usuarioId) throws Exception;

}
