package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {

    // @RequestMapping(method = RequestMethod.GET, value = "/{cep}/json/") seria utilizado caso
    //o FeingClient não suportasse os comandos do Spring, ou seja, é possível fazer via @GetMapping
    //diretamente
    @GetMapping("/{cep}/json/")
    Endereco consultarCep(@PathVariable("cep") String cep);
}
