package ec.com.lchumi.locales.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "credito_pago")
@Data
public class CreditoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "cp_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cp_cliente", referencedColumnName = "cli_id")
    private Cliente cliente;

    @Column(name = "cp_fecha")
    private LocalDate fecha;

    @Column(name = "cp_monto")
    private BigDecimal monto;
}
