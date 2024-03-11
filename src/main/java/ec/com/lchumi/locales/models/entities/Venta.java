package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.com.lchumi.locales.models.enums.EstadoVenta;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

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
    private Date date;

    @Column(name = "vent_total")
    private double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "vent_estado")
    private EstadoVenta estado;

    @JsonIgnore
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;

    @ManyToOne
    @JoinColumn(name = "vent_cliente", referencedColumnName = "cli_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name ="vent_pago", referencedColumnName = "formpag_id")
    private FormaPago formaPago;

    @ManyToOne
    @JoinColumn(name = "vent_almacen",referencedColumnName = "alm_id")
    private Almacen almacen;

    @ManyToOne
    @JoinColumn(name = "vent_usuario",referencedColumnName = "usr_id")
    private Usuario usuario;
}
