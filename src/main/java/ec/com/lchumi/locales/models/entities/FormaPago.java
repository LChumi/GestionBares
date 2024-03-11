package ec.com.lchumi.locales.models.entities;

import ec.com.lchumi.locales.models.enums.EstadoPago;
import ec.com.lchumi.locales.models.enums.TipoPago;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "forma_pago")
@Data
public class FormaPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "formpag_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "formpag_nombre")
    private TipoPago nombre;

    @Column(name = "formpag_activo")
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "formpag_venta",referencedColumnName = "vent_id")
    private Venta venta;
}
