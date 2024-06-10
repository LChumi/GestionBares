package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.EntradaInventario;
import jakarta.transaction.Transactional;

public interface IInventarioService {

    @Transactional
    void agregarStock(Long productoId, Long bodegaId, int cantidad);

    @Transactional
    void reducirStock(Long productoId, Long bodegaId, int cantidad) throws Exception;

    @Transactional
    void registrarEntradaInventario(EntradaInventario entradaInventario);
}
