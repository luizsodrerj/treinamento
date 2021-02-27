import java.awt.Font;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Main {

	public Main() {
	}

	public static void main(String[] args) {
		Font font = new Font("Arial", Font.BOLD, 16);
		
		JLabel label = new JLabel("Informe os dados do Candidato:");
		label.setFont(font);
		
		int totalGeralVotos = 0;
		
		String input = JOptionPane.showInputDialog(label);
		StringTokenizer dadosInput1 = new StringTokenizer(input,",");
		String nome1 = dadosInput1.nextToken();
		int votos1 = Integer.parseInt(dadosInput1.nextToken());
		totalGeralVotos += votos1;
		
		input = JOptionPane.showInputDialog(label);
		dadosInput1 = new StringTokenizer(input,",");
		String nome2 = dadosInput1.nextToken();
		int votos2 = Integer.parseInt(dadosInput1.nextToken());		
		totalGeralVotos += votos2;
		
		input = JOptionPane.showInputDialog(label);
		dadosInput1 = new StringTokenizer(input,",");
		String nome3 = dadosInput1.nextToken();
		int votos3 = Integer.parseInt(dadosInput1.nextToken());		
		totalGeralVotos += votos3;

		System.out.println("Total geral de votos - "+totalGeralVotos);
		
		double percent = votos1 * 100 / totalGeralVotos;
		
		System.out.println("Candidato - "+nome1);
		System.out.println("Votos - " + votos1);
		System.out.println("Percentual - " + percent + "%");

		percent = votos2 * 100 / totalGeralVotos;
		
		System.out.println("Candidato - "+nome2);
		System.out.println("Votos - " + votos2);
		System.out.println("Percentual - " + percent + "%");

		percent = votos3 * 100 / totalGeralVotos;
		
		System.out.println("Candidato - "+nome3);
		System.out.println("Votos - " + votos3);
		System.out.println("Percentual - " + percent + "%");
		
	}

}
