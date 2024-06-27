package ec.com.lchumi.locales.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "entrada_inventario")
public class EntradaInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "ent_inv_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ent_inv_producto", referencedColumnName = "pro_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "ent_inv_bodega", referencedColumnName = "bod_id")
    private Bodega bodega;

    @ManyToOne
    @JoinColumn(name = "ent_inv_proveedor", referencedColumnName = "prov_id")
    private Proveedor proveedor;

    @Column(name = "ent_inv_cantidad")
    private int cantidad;
    @Column(name = "ent_inv_fecha")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "ent_inv_usuario", referencedColumnName = "usr_id")
    private Usuario usuario;
}
