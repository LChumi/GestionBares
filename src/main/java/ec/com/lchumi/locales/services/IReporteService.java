package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.dto.ClienteCompradorFrecuenteDTO;
import ec.com.lchumi.locales.models.dto.ClienteMayorDeudaDTO;
import ec.com.lchumi.locales.models.dto.ProductoMasVendidoDTO;
import ec.com.lchumi.locales.models.entities.Venta;

import java.time.LocalDate;
import java.util.List;

public interface IReporteService {
    List<Venta> obtenerVentasPorFecha(LocalDate startDate, LocalDate endDate);
    List<Venta> obtenerVentasFechaHoy();

    List<ProductoMasVendidoDTO> obtenerProductosMasVendidos();
    List<ClienteMayorDeudaDTO> obtenerClientesConMayorDeuda();
    List<ClienteCompradorFrecuenteDTO> obtenerClineteMaFrecuentes();

}
