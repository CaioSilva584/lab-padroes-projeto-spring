package one.digitalinnovation.gof.service.impl;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.repository.ClienteRepository;
import one.digitalinnovation.gof.repository.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 *
 * @author Caião
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    // Singleton: Injetar os componentes do Spring com @Autowired.
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    // Strategy: Implementar os métodos definidos na interface.
    // Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

    @Override
    public Iterable<Cliente> buscarTodos() {

        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElse(null);
    }

    @Override
    public void inserir(Cliente cliente) {
        // Extrai o cep do cliente a ser inserido
        String cep = cliente.getEndereco().getCep();

        // Verifica se o endereço existe, caso contrário, chama a API do viaCep e cria um novo
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco enderecoNovo = viaCepService.consultarCep(cep);
            enderecoRepository.save(enderecoNovo);
            return enderecoNovo;
        });

        cliente.setEndereco(endereco);

        clienteRepository.save(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);

        if(clienteBd.isPresent()){
            // Será que é errado fazer isso?
            inserir(cliente);
        } else {
            throw new NullPointerException("Não foi possível encontrar o cliente");
        }

    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
