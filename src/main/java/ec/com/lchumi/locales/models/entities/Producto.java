package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
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
    private String descripcion;

    @Column(name = "pro_costo")
    private BigDecimal costo;

    @Column(name = "pro_precio1")
    private BigDecimal precio1;

    @Column(name = "pro_precio")
    private BigDecimal precio2;

    @Column(name = "pro_precio3")
    private BigDecimal precio3;
}
