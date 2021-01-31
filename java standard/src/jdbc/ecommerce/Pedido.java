package jdbc.ecommerce;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {

	private Integer id;
	private Date dataHoraCriacao;
	private Cliente cliente;
	private List<Produto> carrinhoCompras = new ArrayList<Produto>();

	
	public Date getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public void setDataHoraCriacao(Date dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Produto> getCarrinhoCompras() {
		return carrinhoCompras;
	}

	public void setCarrinhoCompras(List<Produto> carrinhoCompras) {
		this.carrinhoCompras = carrinhoCompras;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
