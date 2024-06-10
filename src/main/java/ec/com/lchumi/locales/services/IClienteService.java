package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Cliente;

import java.math.BigDecimal;

public interface IClienteService extends IGenericService<Cliente,Long> {

    void actualizarCredito(Long clienteId, BigDecimal monto);
}
