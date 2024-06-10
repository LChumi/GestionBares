package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "proveedor")
@Data
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "prov_id")
    private Long id;

    @NotBlank
    @Column(name = "prov_nombre")
    private String nombre;

    @Column(name = "prov_telefono")
    private String telefono;

    @Email
    @Column(name = "prov_email")
    private String email;

    @Column(name = "prov_direccion")
    private String direccion;

}
