package ec.com.lchumi.locales.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "almacen_producto")
@Data
@NoArgsConstructor
public class AlmacenProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "alm_prod_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alm_prod_producto", referencedColumnName = "pro_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "alm_prod_bodega")
    private Bodega bodega;

    @Column(name = "stock")
    private int stock;

    public AlmacenProducto(Producto producto, Bodega bodega, int i) {
        this.producto = producto;
        this.bodega = bodega;
        this.stock = i;
    }
}
