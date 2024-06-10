package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.com.lchumi.locales.models.enums.RolEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_codigo")
    private RolEnum codigo;

    @Column(name = "rol_nombre")
    private String descripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "rol",cascade = CascadeType.ALL)
    private List<Usuario> usuarios;
}
