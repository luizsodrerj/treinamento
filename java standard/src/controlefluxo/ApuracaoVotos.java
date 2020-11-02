package controlefluxo;

import java.util.StringTokenizer;

public class ApuracaoVotos {

	String maisVotado   = "";
	int votosMaisVotado = 0;
	String menosVotado   = "";
	int votosMenosVotado = 200000;
	int totalDeVotos = 0;

	
	public static void main(String[] args) {
		ApuracaoVotos apuracao = new ApuracaoVotos();
		
		String fulano = "Fulano,300";
		String beutrano = "Beutrano,200";
		String ciclano = "Ciclano,180";
		String umDoisTresOliv4 = "Um Dois Tres Oliveira Quatro,400";
		String x = "Candidato X,1000";
		String y = "Candidato Y,800";
		
		apuracao.apurarVoto(fulano);
		apuracao.apurarVoto(beutrano);
		apuracao.apurarVoto(ciclano);
		apuracao.apurarVoto(umDoisTresOliv4);
		apuracao.apurarVoto(x);
		apuracao.apurarVoto(y);
		
		apuracao.imprimirResultado();
		
	}

	void apurarVoto(String candidato) {
		StringTokenizer tokenizer = new StringTokenizer(candidato, ",");
		
		while (tokenizer.hasMoreElements()) {
			String nome = tokenizer.nextToken();
			String votos = tokenizer.nextToken();
			int qtdVotos = Integer.parseInt(votos);
			
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
	}

	void imprimirResultado() {
		System.out.println("Candidato mais votado: " + maisVotado + " com " + votosMaisVotado + " votos");
		System.out.println("Candidato menos votado: " + menosVotado + " com " + votosMenosVotado + " votos");
		System.out.println("Total de votos apurados: " + totalDeVotos);
	}
	
}








