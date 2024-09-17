package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.dto.ClienteCompradorFrecuenteDTO;
import ec.com.lchumi.locales.models.dto.ClienteMayorDeudaDTO;
import ec.com.lchumi.locales.models.dto.ProductoMasVendidoDTO;
import ec.com.lchumi.locales.models.entities.Venta;
import ec.com.lchumi.locales.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReporteServiceImpl implements IReporteService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<Venta> obtenerVentasPorFecha(LocalDate fechaInicial, LocalDate fechaFinal) {
        return ventaRepository.findByFechaBetween(fechaInicial, fechaFinal);
    }

    @Override
    public List<Venta> obtenerVentasFechaHoy() {
        LocalDate fechaActual = LocalDate.now();
        return ventaRepository.findByFecha(fechaActual);
    }

    @Override
    public List<ProductoMasVendidoDTO> obtenerProductosMasVendidos() {
        return ventaRepository.findProductoMasVendido();
    }

    @Override
    public List<ClienteMayorDeudaDTO> obtenerClientesConMayorDeuda() {
        return ventaRepository.findClientesConMayorDeuda();
    }

    @Override
    public List<ClienteCompradorFrecuenteDTO> obtenerClineteMaFrecuentes() {
        return ventaRepository.findClientesMasFrecuentes();
    }
}
