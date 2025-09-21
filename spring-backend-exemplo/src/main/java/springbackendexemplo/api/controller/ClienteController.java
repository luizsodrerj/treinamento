package springbackendexemplo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springbackendexemplo.api.dto.ClienteTO;
import springbackendexemplo.core.entity.Cliente;
import springbackendexemplo.core.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @PutMapping("/update")
    public Cliente updateCliente(
            @RequestBody ClienteTO clienteTO
    ) {
        Long id = Long.valueOf(clienteTO.getId());
        Cliente cliente = clienteRepository.findById(id).get();
        cliente.setNomeRazaoSocial(clienteTO.getNomeRazaoSocial());
        cliente = clienteRepository.save(cliente);

        return cliente;
    }

    @PostMapping("/novo-cliente")
    public Cliente insertCliente(
        @RequestBody ClienteTO clienteTO
    ) {
        Cliente cliente = new Cliente();
        cliente.setNomeRazaoSocial(clienteTO.getNomeRazaoSocial());
        cliente.setDataCadastro(new Date());
        cliente = clienteRepository.save(cliente);

        return cliente;
    }

    @GetMapping("/get-by-razao")
    public List<ClienteTO> getByRazaoSocial(
                @RequestParam(name = "razao", required = false)
                String razao
        ) {
        List<ClienteTO> clientes = new ArrayList<>();
        List<Cliente>list = clienteRepository.findByNomeRazaoSocialContainingIgnoreCase(razao);

        for (Cliente cliente: list) {
            ClienteTO to = new ClienteTO();
            to.setId(cliente.getId());
            to.setNomeRazaoSocial(cliente.getNomeRazaoSocial());
            to.setDataCadastro(cliente.getDataCadastro().toString());
            clientes.add(to);
        }
        return clientes;
    }

    @GetMapping("/get-all")
    public List<ClienteTO> getAll() {
        List<ClienteTO> clientes = new ArrayList<>();
        List<Cliente>list = clienteRepository.findAll();

        for (Cliente cliente: list) {
            ClienteTO to = new ClienteTO();
            to.setId(cliente.getId());
            to.setNomeRazaoSocial(cliente.getNomeRazaoSocial());
            to.setDataCadastro(cliente.getDataCadastro().toString());
            clientes.add(to);
        }
        return clientes;
    }

    @GetMapping("/by-id")
    public ClienteTO getById(
        @RequestParam(name = "id", required = false)
        String id
    ) {
        Long idCliente = Long.valueOf(id);
        Cliente cliente = clienteRepository.findById(idCliente).get();

        ClienteTO to = new ClienteTO();
        to.setId(cliente.getId());
        to.setNomeRazaoSocial(cliente.getNomeRazaoSocial());
        to.setDataCadastro(cliente.getDataCadastro().toString());

        return to;
    }


}







