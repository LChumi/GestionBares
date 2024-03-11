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

    @Column(name = "alm_nombre")
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL)
    private List<Bodega> bodegas;

    @JsonIgnore
    @OneToMany(mappedBy = "almacen",cascade = CascadeType.ALL)
    private List<Venta> ventas;

}
