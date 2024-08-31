package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Cliente;
import ec.com.lchumi.locales.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

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
        cliente.setCupo(cliente.getCupo().add(monto));
        clienteRepository.save(cliente);
    }

    @Override
    public Object buscarCliente(String data) {
        List<Cliente> clientes = clienteRepository.findByData(data);
        if (clientes.isEmpty()) {
            return Collections.emptyList(); // No se encontraron clientes
        } else if (clientes.size() == 1) {
            return clientes.get(0); // Un solo cliente encontrado
        } else {
            return clientes; // Más de un cliente encontrado
        }
    }

}
