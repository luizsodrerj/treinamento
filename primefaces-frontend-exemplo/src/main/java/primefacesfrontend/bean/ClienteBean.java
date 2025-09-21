package primefacesfrontend.bean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import primefacesfrontend.client.dto.ClienteTO;
import reactor.core.publisher.Mono;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ClienteBean {

    private List<ClienteTO>clientes = new ArrayList<>();
    private ClienteTO cliente = new ClienteTO();
    private String opcaoPesquisa;

    private static final String OP_TODOS = "todos";
    private static final String OP_RAZAO = "razao";
    private static final String OP_ID = "id";

    private String criterio;


    public String onClickBtNovoCliente() {
        ((HttpServletRequest)FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequest())
                .getSession().removeAttribute("cliente");

        return "NovoCliente.xhtml";
    }

    public String onClickBtSalvar() {
        WebClient webClient = WebClient.create("http://localhost:8080");
        webClient.post()
                .uri("/api/cliente/novo-cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(cliente))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    System.out.println("==== ERRO =====");
                    return Mono.error(new Exception("error"));
                })
                .bodyToMono(ClienteTO.class)
                .subscribe(
                    response -> System.out.println("Response received"),
                    error -> System.out.println("==== ERRO =====")
                );

        return "home.xhtml";
    }

    public String detalheCliente() {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        WebClient webClient = WebClient.create("http://localhost:8080");
        criterio = request.getParameter("id");

        byId(webClient);
        cliente = clientes.get(0);

        try {
            cliente.setDataCadastro(
                new SimpleDateFormat("dd/MM/yyyy")
                .format(
                  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
                      .parse(cliente.getDataCadastro())
                )
            );
        } catch (Exception e) {
        }
        request.getSession().setAttribute("cliente",cliente);

        return "NovoCliente.xhtml";
    }

    public void pesquisar() {
        try{
            WebClient webClient = WebClient.create("http://localhost:8080");

            switch (opcaoPesquisa) {
                case OP_TODOS: getAll(webClient);
                break;
                case OP_RAZAO: byRazao(webClient);
                break;
                case OP_ID: byId(webClient);
                break;
                default:
                    throw new IllegalArgumentException("OPCAO invalida!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void byId(WebClient webClient) {
        List<ClienteTO>data = webClient.get()
                .uri("/api/cliente/by-id?id=" + criterio)
                .retrieve()
                .bodyToFlux(ClienteTO.class)
                .collectList()
                .block();
        clientes.clear();
        clientes.addAll(data);
    }

    private void byRazao(WebClient webClient) {
        List<ClienteTO>data = webClient.get()
                .uri("/api/cliente/get-by-razao?razao=" + criterio)
                .retrieve()
                .bodyToFlux(ClienteTO.class)
                .collectList()
                .block();
        clientes.clear();
        clientes.addAll(data);
    }

    private void getAll(WebClient webClient) {
        List<ClienteTO>data = webClient.get()
                .uri("/api/cliente/get-all")
                .retrieve()
                .bodyToFlux(ClienteTO.class)
                .collectList()
                .block();
        clientes.clear();
        clientes.addAll(data);
    }

/*
    public static void main(String[] args) {
        try{
            WebClient webClient = WebClient.create("http://localhost:8080");
            List<ClienteTO>data = webClient.get()
                        .uri("/api/cliente/by-id?id=2")
                        .retrieve()
                        .bodyToFlux(ClienteTO.class)
                        .collectList()
                        .block();

            System.out.println(data.get(0).getNomeRazaoSocial());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    public String getOpcaoPesquisa() {
        return opcaoPesquisa;
    }

    public void setOpcaoPesquisa(String opcaoPesquisa) {
        this.opcaoPesquisa = opcaoPesquisa;
    }

    public List<ClienteTO> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteTO> clientes) {
        this.clientes = clientes;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public ClienteTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteTO cliente) {
        this.cliente = cliente;
    }
}
