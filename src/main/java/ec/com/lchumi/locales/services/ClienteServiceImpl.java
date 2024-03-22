package ec.com.lchumi.locales.services;

import ec.com.lchumi.locales.models.entities.Cliente;
import ec.com.lchumi.locales.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends GenericServiceImpl<Cliente,Long> implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public CrudRepository<Cliente, Long> getDao() {
        return clienteRepository;
    }
}
