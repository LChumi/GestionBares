package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venta")
@Data
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "vent_id")
    private Long id;

    @Column(name = "vent_fecha")
    private LocalDate fecha;

    @ManyToOne
    private Cliente cliente;

    @Column(name = "vent_total")
    private BigDecimal total;

    @JsonIgnore
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "vent_usuario",referencedColumnName = "usr_id")
    private Usuario usuario;

    public void agregarDetalle(DetalleVenta detalle) {
        boolean found = false;
        for (DetalleVenta existente : this.detalles) {
            if (existente.getProducto().equals(detalle.getProducto()) && existente.getBodega().equals(detalle.getBodega())){
                int nuevaCantidad = existente.getCantidad()+(detalle.getCantidad() > 0 ? detalle.getCantidad() :1);
                existente.setCantidad(nuevaCantidad);
                found = true;
                break;
            }
        }

        if (!found) {
            detalle.setCantidad(detalle.getCantidad() > 0 ? detalle.getCantidad() : 1);
            detalle.setVenta(this);
            this.detalles.add(detalle);
        }
    }

    public void actualizarDetalle(Long detalleId, DetalleVenta detalleActualizado) throws Exception {
        DetalleVenta detalleExistente = this.detalles.stream()
                .filter(d -> d.getId().equals(detalleId))
                .findFirst()
                .orElseThrow(() -> new Exception("Detalle de venta no encontrado"));

        detalleExistente.setProducto(detalleActualizado.getProducto());
        detalleExistente.setBodega(detalleActualizado.getBodega());
        detalleExistente.setCantidad(detalleActualizado.getCantidad());
        detalleExistente.setPrecioUnitario(detalleActualizado.getPrecioUnitario());
    }

    public void eliminarDetalle(Long detalleId) throws Exception {
        DetalleVenta detalle = this.detalles.stream()
                .filter(d -> d.getId().equals(detalleId))
                .findFirst()
                .orElseThrow(() -> new Exception("Detalle de venta no encontrado"));
        this.detalles.remove(detalle);
    }
}
