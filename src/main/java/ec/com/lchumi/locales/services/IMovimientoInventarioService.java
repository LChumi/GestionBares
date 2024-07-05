package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.MovimientoInventario;

import java.time.LocalDate;
import java.util.List;

public interface IMovimientoInventarioService extends IGenericService<MovimientoInventario,Long> {

    List<MovimientoInventario> listarPorFecha();
    List<MovimientoInventario> listarEntreFechas(LocalDate fechaInicial, LocalDate fechaFinal);
}
