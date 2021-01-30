package jdbc;

public class Produto {

	private Pedido pedido;
	private String descricao;
	private Integer qtdPedida;
	private Double preco;
	
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getQtdPedida() {
		return qtdPedida;
	}
	public void setQtdPedida(Integer qtdPedida) {
		this.qtdPedida = qtdPedida;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
	
}
