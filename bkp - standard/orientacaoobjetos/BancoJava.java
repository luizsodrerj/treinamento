package orientacaoobjetos;

public class BancoJava {

	public static void main(String[] args) {
		Conta contaPrimo = new Conta();
		contaPrimo.nomeDoCliente = "Primo Rico";
		contaPrimo.deposita(150000);
		contaPrimo.saca(15000);
		
		contaPrimo.imprimeSaldo();
		
	}

}
