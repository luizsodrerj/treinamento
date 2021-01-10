package controlefluxo;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class InputDados {

	
	public static void main(String[] args) {
		Font font = new Font("Arial", Font.BOLD, 18);
		
		JLabel labelNum1 = new JLabel("Entre com o primeiro numero:");
		labelNum1.setFont(font);
		JLabel labelNum2 = new JLabel("Entre com o segundo numero:");
		labelNum2.setFont(font);
		
		String priNumero = JOptionPane.showInputDialog(labelNum1);
		String segNumero = JOptionPane.showInputDialog(labelNum2);
		int numeroUm = Integer.parseInt(priNumero);
		int numeroDois = Integer.parseInt(segNumero);
		
		String print = "A soma eh "+ (numeroUm + numeroDois);
		JLabel labelPrint = new JLabel(print);
		labelPrint.setFont(font);
		
		JOptionPane.showMessageDialog(null, labelPrint);
	}
	
}
