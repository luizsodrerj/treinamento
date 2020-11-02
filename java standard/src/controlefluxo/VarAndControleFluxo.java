package controlefluxo;

import java.util.StringTokenizer;

public class VarAndControleFluxo {

	
	public static void main(String[] args) {
		String fulano = "Fulano,100";
		String beutrano = "Beutrano,200";
		String ciclano = "Ciclano,180";
		String umDoisTresOliv4 = "Um Dois Tres Oliveira Quatro,400";
		String x = "Candidato X,1000";
		String y = "Candidato Y,800";
		
		String maisVotado   = "";
		int votosMaisVotado = 0;
		String menosVotado   = "";
		int votosMenosVotado = 200000;
		int totalDeVotos = 0;
		
		StringTokenizer tokenizer = new StringTokenizer(fulano, ",");
		
		while (tokenizer.hasMoreElements()) {
			String nome  = tokenizer.nextToken();
			int votos = Integer.parseInt(tokenizer.nextToken());
			
			if (votos > votosMaisVotado) {
				votosMaisVotado = votos;
				maisVotado = nome;
			}
			if (votos < votosMenosVotado) {
				votosMenosVotado = votos;
				menosVotado = nome;
			}
			totalDeVotos = totalDeVotos + votos;
		}
		
		tokenizer = new StringTokenizer(beutrano, ",");
		
		while (tokenizer.hasMoreElements()) {
			String nome  = tokenizer.nextToken();
			int votos = Integer.parseInt(tokenizer.nextToken());
			
			if (votos > votosMaisVotado) {
				votosMaisVotado = votos;
				maisVotado = nome;
			}
			if (votos < votosMenosVotado) {
				votosMenosVotado = votos;
				menosVotado = nome;
			}
			totalDeVotos = totalDeVotos + votos;
		}
		
		tokenizer = new StringTokenizer(ciclano, ",");
		
		while (tokenizer.hasMoreElements()) {
			String nome  = tokenizer.nextToken();
			int votos = Integer.parseInt(tokenizer.nextToken());
			
			if (votos > votosMaisVotado) {
				votosMaisVotado = votos;
				maisVotado = nome;
			}
			if (votos < votosMenosVotado) {
				votosMenosVotado = votos;
				menosVotado = nome;
			}
			totalDeVotos = totalDeVotos + votos;
		}		

		tokenizer = new StringTokenizer(umDoisTresOliv4, ",");
		
		while (tokenizer.hasMoreElements()) {
			String nome  = tokenizer.nextToken();
			int votos = Integer.parseInt(tokenizer.nextToken());
			
			if (votos > votosMaisVotado) {
				votosMaisVotado = votos;
				maisVotado = nome;
			}
			if (votos < votosMenosVotado) {
				votosMenosVotado = votos;
				menosVotado = nome;
			}
			totalDeVotos = totalDeVotos + votos;
		}		
		tokenizer = new StringTokenizer(x, ",");
		
		while (tokenizer.hasMoreElements()) {
			String nome  = tokenizer.nextToken();
			int votos = Integer.parseInt(tokenizer.nextToken());
			
			if (votos > votosMaisVotado) {
				votosMaisVotado = votos;
				maisVotado = nome;
			}
			if (votos < votosMenosVotado) {
				votosMenosVotado = votos;
				menosVotado = nome;
			}
			totalDeVotos = totalDeVotos + votos;
		}		
		tokenizer = new StringTokenizer(y, ",");
		
		while (tokenizer.hasMoreElements()) {
			String nome  = tokenizer.nextToken();
			int votos = Integer.parseInt(tokenizer.nextToken());
			
			if (votos > votosMaisVotado) {
				votosMaisVotado = votos;
				maisVotado = nome;
			}
			if (votos < votosMenosVotado) {
				votosMenosVotado = votos;
				menosVotado = nome;
			}
			totalDeVotos = totalDeVotos + votos;
		}		

		System.out.println("Candidato mais votado: " + maisVotado + " com " + votosMaisVotado + " votos");
		System.out.println("Candidato menos votado: " + menosVotado + " com " + votosMenosVotado + " votos");
		System.out.println("Total de votos apurados: " + totalDeVotos);
	}

}








