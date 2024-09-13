package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Setter(AccessLevel.NONE)
    @Column(name = "vent_total")
    private BigDecimal total;

    @Column(name = "vent_forma_pago")
    private String formaPago;

    @Column(name = "vent_estado")
    private Boolean estado=false;

    @JsonManagedReference
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "vent_usuario",referencedColumnName = "usr_id")
    private Usuario usuario;

    public void agregarDetalle(DetalleVenta detalle) {
        boolean found = false;
        for (DetalleVenta existente : this.detalles) {
            if (existente.getProducto().getId().equals(detalle.getProducto().getId()) && existente.getBodega().getId().equals(detalle.getBodega().getId())) {
                int nuevaCantidad = existente.getCantidad() + (detalle.getCantidad() > 0 ? detalle.getCantidad() : 1);
                existente.actualizarSubtotal();  // Actualizar el subtotal del detalle existente
                found = true;
                break;
            }
        }

        if (!found) {
            detalle.setCantidad(detalle.getCantidad() > 0 ? detalle.getCantidad() : 1);
            detalle.setVenta(this);
            detalle.actualizarSubtotal();  // Calcular el subtotal del nuevo detalle
            this.detalles.add(detalle);
        }
        calcularTotal();
    }

    public void actualizarDetalle(Long detalleId, DetalleVenta detalleActualizado) throws Exception {
        DetalleVenta detalleExistente = this.getDetalleById(detalleId);
        detalleExistente.setProducto(detalleActualizado.getProducto());
        detalleExistente.setBodega(detalleActualizado.getBodega());
        detalleExistente.setCantidad(detalleActualizado.getCantidad());
        calcularTotal();
    }

    public void eliminarDetalle(Long detalleId) throws Exception {
       DetalleVenta detalleVenta = this.getDetalleById(detalleId);
       this.detalles.remove(detalleVenta);
       calcularTotal();
    }

    public DetalleVenta getDetalleById(Long detalleId) throws Exception {
        return this.detalles.stream()
                .filter(d -> d.getId().equals(detalleId))
                .findFirst()
                .orElseThrow(() -> new Exception("Detalle de venta no encontrado"));
    }

    private void calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVenta detalle : this.detalles) {
            total = total.add(detalle.getSubtotal());
        }
        this.total = total;
    }
}
