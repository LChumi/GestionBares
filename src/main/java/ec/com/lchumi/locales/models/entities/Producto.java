package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "producto")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "pro_id")
    private Long id;

    @Column(name = "pro_barra")
    private String barra;

    @NotBlank
    @Column(name = "pro_descripcion")
    private String desripcion;

    @Column(name = "pro_precio1")
    private double precio1;

    @Column(name = "pro_precio")
    private double precio;

    @NotNull
    @Column(name = "pro_stock")
    private int stock;

    @JsonIgnore
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<BodegaProducto> bodegas;

    @ManyToOne
    @JoinColumn(name = "prod_categoria",referencedColumnName = "cat_id")
    private Categoria categoria;

    @JsonIgnore
    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL)
    private List<ProductoProveedor> proveedores;
}
