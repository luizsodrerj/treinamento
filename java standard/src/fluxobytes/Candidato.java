package fluxobytes;

public class Candidato {

	int totalVotos;

	String nome;

	
	
	public Candidato(String nome, int totalVotos) {
		super();
		this.totalVotos = totalVotos;
		this.nome = nome;
	}


	public Candidato() {
	}


	public int getTotalVotos() {
		return totalVotos;
	}


	public void setTotalVotos(int totalVotos) {
		this.totalVotos = totalVotos;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
