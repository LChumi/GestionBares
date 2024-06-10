package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "venta")
@Data
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "vent_id")
    private Long id;

    @Column(name = "vent_fecha")
    private LocalDate fecha;

    @ManyToOne
    private Cliente cliente;

    @Column(name = "vent_total")
    private BigDecimal total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "vent_usuario",referencedColumnName = "usr_id")
    private Usuario usuario;
}
