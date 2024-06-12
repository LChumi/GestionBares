package ec.com.lchumi.locales.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "detventas")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "dventas_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dventas_venta", referencedColumnName = "vent_id")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "dventas_producto", referencedColumnName = "pro_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "dventas_bodega", referencedColumnName = "bod_id")
    private Bodega bodega;

    @Column(name = "dventas_cantidad")
    private int cantidad;
    @Column(name = "dventas_precio_unitario")
    private BigDecimal precioUnitario;

    @Setter(AccessLevel.NONE)
    @Column(name = "dventas_subtotal")
    private BigDecimal subtotal;

    public BigDecimal getSubtotal() {
        return this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
    }
}
