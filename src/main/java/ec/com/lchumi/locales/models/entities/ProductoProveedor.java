package ec.com.lchumi.locales.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "producto_proveedor")
@Data
public class ProductoProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "proprov_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proprov_producto", referencedColumnName = "pro_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "proprov_proveedor", referencedColumnName = "prov_id")
    private Proveedor proveedor;
}
