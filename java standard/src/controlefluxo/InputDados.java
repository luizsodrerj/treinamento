package controlefluxo;

import javax.swing.JOptionPane;

public class InputDados {

	
	public static void main(String[] args) {
		String priNumero = JOptionPane.showInputDialog("Entre com o primeiro numero:");
		String segNumero = JOptionPane.showInputDialog("Entre com o segundo numero:");
		int numeroUm = Integer.parseInt(priNumero);
		int numeroDois = Integer.parseInt(segNumero);
		
		String print = "A soma eh "+ (numeroUm + numeroDois);
		
		JOptionPane.showMessageDialog(null, print);
	}
	
}
