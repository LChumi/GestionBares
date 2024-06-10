package ec.com.lchumi.locales.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ClienteMayorDeudaDTO {
    private Long clienteId;
    private String nombre;
    private BigDecimal credito;
}
