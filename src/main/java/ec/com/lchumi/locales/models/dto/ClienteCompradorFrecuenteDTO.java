package ec.com.lchumi.locales.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ClienteCompradorFrecuenteDTO {
    private Long clineteId;
    private String nombre;
    private Long totalCompras;
}
