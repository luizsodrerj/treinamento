package jdbc;

public class Cliente {

	private String nome;
	private String endereco;
	private Integer id;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

}
