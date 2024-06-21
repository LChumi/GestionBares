package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

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

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "usuario_almacen",
            joinColumns = @JoinColumn(name = "ua_usuario", referencedColumnName = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "ua_almacen", referencedColumnName = "alm_id"))
    private List<Almacen> almacenes;

    public List<Bodega> getBodegas(){
        return this.almacenes.stream()
                .flatMap(a -> a.getBodegas().stream())
                .collect(Collectors.toList());
    }

    public void addAlmacen(Almacen almacen){
        this.almacenes.add(almacen);
    }

    public void removeAlmacen(Almacen almacen){
        this.almacenes.remove(almacen);
    }

}
