package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "usr_nombre")
    @NotBlank
    private String nombre;

    @NotBlank
    @Column(name = "usr_apellido")
    private String apellido;

    @Column(name = "usr_telefono")
    private String telefono;

    @NotBlank
    @Column(name = "usr_username")
    private String nombreUsuario;

    @NotBlank
    @Column(name = "usr_password")
    private String contraseña;

    @ManyToOne
    @JoinColumn(name = "usr_rol", referencedColumnName = "rol_id")
    private Rol rol;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Venta> ventas;
}
