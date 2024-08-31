package ec.com.lchumi.locales.repository;

import ec.com.lchumi.locales.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    Cliente findByCedula(String cedula);
    @Query("SELECT U FROM Cliente U WHERE LOWER(U.nombre) LIKE LOWER(CONCAT('%',:data, '%')) OR U.apellido LIKE(CONCAT('%',:data, '%')) OR U.cedula =:data")
    List<Cliente> findByData(String data);

}
