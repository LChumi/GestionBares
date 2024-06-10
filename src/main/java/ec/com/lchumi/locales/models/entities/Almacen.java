package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "almacen")
@Data
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "alm_id")
    private Long id;

   @ManyToOne
   @JoinColumn(name = "alm_producto", referencedColumnName = "pro_id")
    private Producto producto;

   @ManyToOne
   @JoinColumn(name = "alm_bodega", referencedColumnName = "bod_id")
    private Bodega bodega;

   private int stock;

}
