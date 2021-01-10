package orientacaoobjetos;

public class Conta {

	String nomeDoCliente;
	String numero;
	double saldo;
	
	
	void deposita(double valor) {
		saldo = saldo + valor;
	}
	
	void saca(double valor) {
		saldo = saldo - valor;
	}
	
	void transfere(Conta terceiro, double valor) {
		terceiro.deposita(valor);
		saca(valor);
	}
	
	void imprimeSaldo() {
		System.out.println("Saldo: " + saldo);
	}
}
