package ec.com.lchumi.locales.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "detventas")
@Data
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "detven_id")
    private Long id;

    @Column(name = "detven_cantidad")
    private int cantidad;

    @Column(name = "detven_precio")
    private double precio;

    @ManyToOne
    @JoinColumn(name = "detven_venta", referencedColumnName = "vent_id")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "detven_producto", referencedColumnName = "pro_id")
    private Producto producto;

}
