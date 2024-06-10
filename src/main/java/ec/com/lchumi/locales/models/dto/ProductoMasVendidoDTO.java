package ec.com.lchumi.locales.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductoMasVendidoDTO {
    private Long productoId;
    private String nombre;
    private Long totalVendido;
}
