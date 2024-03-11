package ec.com.lchumi.locales.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "bod_prod")
@Data
public class BodegaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "bp_id")
    private Long id;

    @Column(name = "bp_stock")
    private int stock;

    @ManyToOne
    @JoinColumn(name = "bp_bodega",referencedColumnName = "bod_id")
    private Bodega bodega;

    @ManyToOne
    @JoinColumn(name = "bp_producto",referencedColumnName = "pro_id")
    private Producto producto;
}
