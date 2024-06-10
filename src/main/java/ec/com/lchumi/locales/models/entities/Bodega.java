package ec.com.lchumi.locales.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bodega")
@Data
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "bod_id")
    private Long id;

    @Column(name = "bod_nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "bod_almacen",referencedColumnName = "alm_id")
    private Almacen almacen;

    @OneToMany(mappedBy = "bodega", cascade = CascadeType.ALL)
    private List<AlmacenProducto> productos = new ArrayList<>();

}
