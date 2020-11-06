package controlefluxo;

public class ApuracaoVotosArray {

	
	public static void main(String[] args) {
		String maisVotado   = "";
		int votosMaisVotado = 0;
		String menosVotado   = "";
		int votosMenosVotado = 200000;
		int totalDeVotos = 0;

		Candidato[] urna = new Candidato[] {
			 new Candidato("Fulano", 300), 
			 new Candidato("Beutrano", 200),
			 new Candidato("Ciclano", 180),
			 new Candidato("Um Dois Tres Oliveira Quatro", 400),
			 new Candidato("X", 1000),
			 new Candidato("Y", 800),
			 new Candidato("Z", 1200)
		};
		
		for (int i = 0; i < urna.length; i++) {
			Candidato candidato = urna[i];
			
			int qtdVotos = candidato.totalVotos;
			String nome  = candidato.nome;
			
			if (qtdVotos > votosMaisVotado) {
				votosMaisVotado = qtdVotos;
				maisVotado = nome;
			}
			if (qtdVotos < votosMenosVotado) {
				votosMenosVotado = qtdVotos;
				menosVotado = nome;
			}
			totalDeVotos = totalDeVotos + qtdVotos;
		}
		
		System.out.println("Candidato mais votado: " + maisVotado + " com " + votosMaisVotado + " votos");
		System.out.println("Candidato menos votado: " + menosVotado + " com " + votosMenosVotado + " votos");
		System.out.println("Total de votos apurados: " + totalDeVotos);
		
		for (int i = 0; i < urna.length; i++) {
			Candidato candidato = urna[i];
			
			if (candidato.totalVotos > 500) {
				System.out.println("Candidato: "+candidato.nome);
				System.out.println("Votos: "+candidato.totalVotos);
			}
		}
	}	
}











