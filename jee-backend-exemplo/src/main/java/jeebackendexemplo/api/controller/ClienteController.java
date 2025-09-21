package jeebackendexemplo.api.controller;

import jeebackendexemplo.core.entity.Cliente;
import jeebackendexemplo.core.repository.ClienteRepository;
import springbackendexemplo.api.dto.ClienteTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/cliente")
public class ClienteController {
    @EJB
    private ClienteRepository clienteRepository;

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Cliente updateCliente(
           ClienteTO clienteTO
    ) {
        Long id = Long.valueOf(clienteTO.getId());
        Cliente cliente = clienteRepository.findById(id);
        cliente.setNomeRazaoSocial(clienteTO.getNomeRazaoSocial());
        cliente = clienteRepository.save(cliente);

        return cliente;
    }

    @POST
    @Path("/novo-cliente")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Cliente insertCliente(
        ClienteTO clienteTO
    ) {
        Cliente cliente = new Cliente();
        cliente.setNomeRazaoSocial(clienteTO.getNomeRazaoSocial());
        cliente.setDataCadastro(new Date());
        cliente = clienteRepository.save(cliente);

        return cliente;
    }

    @GET
    @Path("/get-by-razao")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClienteTO> getByRazaoSocial(
            @QueryParam("razao") String razao
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

    @GET
    @Path("/by-id")
    @Produces(MediaType.APPLICATION_JSON)
    public ClienteTO getById(
        @QueryParam("id") String id
    ) {
        Long idCliente = Long.valueOf(id);
        Cliente cliente = clienteRepository.findById(idCliente);

        ClienteTO to = new ClienteTO();
        to.setId(cliente.getId());
        to.setNomeRazaoSocial(cliente.getNomeRazaoSocial());
        to.setDataCadastro(cliente.getDataCadastro().toString());

        return to;
    }


}







