package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Cliente;
import ec.com.lchumi.locales.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ClienteServiceImpl extends GenericServiceImpl<Cliente,Long> implements IClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public CrudRepository<Cliente, Long> getDao() {
        return clienteRepository;
    }

    @Override
    public void actualizarCredito(Long clienteId, BigDecimal monto) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setCredito(cliente.getCredito().add(monto));
        clienteRepository.save(cliente);
    }
}
