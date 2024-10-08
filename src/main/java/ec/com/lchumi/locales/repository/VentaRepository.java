package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.dto.ClienteCompradorFrecuenteDTO;
import ec.com.lchumi.locales.models.dto.ClienteMayorDeudaDTO;
import ec.com.lchumi.locales.models.dto.ProductoMasVendidoDTO;
import ec.com.lchumi.locales.models.entities.Usuario;
import ec.com.lchumi.locales.models.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta,Long> {

    List<Venta> findByFechaBetween(LocalDate fechaInicial, LocalDate fechaFinal );
    List<Venta> findByFecha(LocalDate fecha);
    List<Venta> findByFechaAndEstadoAndUsuario(LocalDate fecha , Boolean estado, Usuario usuario);

    @Query("SELECT new ec.com.lchumi.locales.models.dto.ProductoMasVendidoDTO(d.producto.id, d.producto.descripcion, SUM(d.cantidad)) " +
            "FROM DetalleVenta d GROUP BY d.producto.id ORDER BY SUM(d.cantidad) DESC")
    List<ProductoMasVendidoDTO> findProductoMasVendido();

    @Query("SELECT new ec.com.lchumi.locales.models.dto.ClienteMayorDeudaDTO(c.id, c.nombre, c.credito) " +
            "FROM Cliente c WHERE c.credito > 0 ORDER BY c.credito DESC")
    List<ClienteMayorDeudaDTO> findClientesConMayorDeuda();

    @Query("SELECT new ec.com.lchumi.locales.models.dto.ClienteCompradorFrecuenteDTO(c.id, c.nombre, COUNT(v.id)) " +
            "FROM Venta v JOIN v.cliente c GROUP BY c.id ORDER BY COUNT(v.id) DESC")
    List<ClienteCompradorFrecuenteDTO> findClientesMasFrecuentes();

    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.fecha =:fecha")
    BigDecimal findTotalFecha(LocalDate fecha);



}
