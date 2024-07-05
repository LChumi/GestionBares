package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.MovimientoInventario;
import ec.com.lchumi.locales.repository.MovimientoInventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovimientoInventarioServiceImpl extends GenericServiceImpl<MovimientoInventario,Long> implements IMovimientoInventarioService {
    @Autowired
    private MovimientoInventarioRepository movimientoRepository;

    @Override
    public CrudRepository<MovimientoInventario, Long> getDao() {
        return movimientoRepository;
    }

    @Override
    public List<MovimientoInventario> listarPorFecha() {
        LocalDate fechaActual = LocalDate.now();
        return movimientoRepository.findByfecha(fechaActual);
    }

    @Override
    public List<MovimientoInventario> listarEntreFechas(LocalDate fechaInicial, LocalDate fechaFinal) {
        return movimientoRepository.findByFechaBetween(fechaInicial, fechaFinal);
    }
}
