package jdbc;

public class Candidato {

	private Integer id; 
	private Integer totalVotos;
	private String nome;

	
	
	public Candidato(String nome, int totalVotos) {
		super();
		this.totalVotos = totalVotos;
		this.nome = nome;
	}


	public Candidato() {
	}


	public Integer getTotalVotos() {
		return totalVotos;
	}


	public void setTotalVotos(Integer totalVotos) {
		this.totalVotos = totalVotos;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
