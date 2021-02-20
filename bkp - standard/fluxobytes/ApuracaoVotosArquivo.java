package fluxobytes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ApuracaoVotosArquivo {

	List<Candidato>candidatos = null;
	
	
	public static void main(String[] args) {
		ApuracaoVotosArquivo apuracao = new ApuracaoVotosArquivo();
		
		apuracao.apurarVotos();
	}

	public void apurarVotos() {
		try {
			List<Candidato>listaCandidatos = new ArrayList<>(); 
					
			File arquivo = new File("C:\\Users\\beto\\Desktop\\Mentoria & Cursos\\treinamento\\java standard\\votacao.txt");
			BufferedReader buffer = new BufferedReader(new FileReader(arquivo));
			
			String linhaArquivo = buffer.readLine();
			
			while (linhaArquivo != null) {
				StringTokenizer tokenizer = new StringTokenizer(linhaArquivo, ",");
				
				if (tokenizer.hasMoreTokens()) {
					String nome  = tokenizer.nextToken();
					int votos    = Integer.parseInt(tokenizer.nextToken().trim());
					
					Candidato candidato = new Candidato(nome, votos);
					listaCandidatos.add(candidato);
				}
				linhaArquivo = buffer.readLine();	
			}
			buffer.close();
			
			for (Candidato candidato : listaCandidatos) {
				System.out.println(
					candidato.getNome() + " - " + 
					candidato.getTotalVotos() + " votos"
				);
			}
			candidatos = listaCandidatos;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Candidato> getCandidatos() {
		return candidatos;
	}
}











