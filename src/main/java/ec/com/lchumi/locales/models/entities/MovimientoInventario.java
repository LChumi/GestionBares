package ec.com.lchumi.locales.models.entities;

import ec.com.lchumi.locales.models.enums.TipoMovimientoEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "movinventario",uniqueConstraints = @UniqueConstraint(columnNames = {"movinv_producto", "movin_bodega"}))
public class MovimientoInventario {

    @Id
    @Setter(AccessLevel.NONE)
    @Column(name = "movinv_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movinv_producto", referencedColumnName = "pro_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "movin_bodega", referencedColumnName = "bod_id")
    private Bodega bodega;

    @Column(name = "movinv_cantidad")
    private int cantidad;

    @Column(name = "movinv_tipo")
    @Enumerated(EnumType.STRING)
    private TipoMovimientoEnum tipo;

    @Column(name = "movinv_fecha")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "movinv_usuario", referencedColumnName = "usr_id")
    private Usuario usuario;
}
