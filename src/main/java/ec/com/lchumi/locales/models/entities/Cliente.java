package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "cli_id")
    private Long id;

    @Column(name = "cli_cedula", unique = true)
    private String cedula;

    @Column(name = "cli_nombre")
    private String nombre;

    @Column(name = "cli_apellido")
    private String apellido;

    @Email
    @Column(name = "cli_email")
    private String email;

    @Column(name = "cli_telefono")
    private String telefono;

    @Column(name = "cli_direccion")
    private String direccion;

    @Column(name = "cli_credito")
    private BigDecimal credito;

    @Column(name = "cli_cupo")
    private BigDecimal cupo;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditoPago> historialPagosCredito = new ArrayList<>();

}
