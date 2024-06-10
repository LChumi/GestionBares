package ec.com.lchumi.locales.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "bodega")
@Data
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "bod_id")
    private Long id;

    @Column(name = "bod_nombre")
    private String nombre;



}
