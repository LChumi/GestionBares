package ec.com.lchumi.locales.models.entities;

import ec.com.lchumi.locales.models.enums.EstadoPago;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "credito")
@Data
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "cred_id")
    private Long id;

    @Column(name = "cred_monto")
    private double monto;

    @Column(name = "cred_fecha")
    private Date fecha;

    @Column(name = "cred_saldo")
    private double saldo;

    @Enumerated(EnumType.STRING)
    @Column(name = "cred_estado")
    private EstadoPago estado;

    @OneToOne
    @JoinColumn(name = "cred_cliente", referencedColumnName = "cli_id")
    private Cliente cliente;
}
