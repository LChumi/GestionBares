package ec.com.lchumi.locales.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "bodega")
@Data
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "bod_id")
    private Long bod_id;

    @Column(name = "bod_nombre")
    private String nombre;



}
