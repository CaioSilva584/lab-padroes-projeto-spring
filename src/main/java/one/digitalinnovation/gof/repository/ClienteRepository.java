package one.digitalinnovation.gof.repository;

import one.digitalinnovation.gof.model.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// É adicionada a annotations de Repository por boas práticas
@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}
