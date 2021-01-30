package jdbc;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ECommerceJDBC {

	public static void main(String[] args) {

	}

	void comprarProdutos() {
		JLabel nomeCliente = new JLabel("Informe o nome do Cliente:");
		nomeCliente.setFont(new Font("Arial", Font.BOLD, 18));
		JLabel endereco = new JLabel("Informe o Endereço de Entrega:");
		endereco.setFont(new Font("Arial", Font.BOLD, 18));
		JLabel nomeProduto = new JLabel("Informe o nome do Produto para incluir no Carrinho:");
		nomeProduto.setFont(new Font("Arial", Font.BOLD, 18));
		
		Pedido pedido = new Pedido();
		pedido.setDataHoraCriacao(new Date());
		
		String produto = JOptionPane.showInputDialog(nomeProduto);
		Produto priProduto = new Produto();
		priProduto.setDescricao(produto);
		priProduto.setQtdPedida(1);
		priProduto.setPreco(500D);
		priProduto.setPedido(pedido);
				
		produto = JOptionPane.showInputDialog(nomeProduto);
		Produto segundoProduto = new Produto();
		segundoProduto.setDescricao(produto);
		segundoProduto.setQtdPedida(2);
		segundoProduto.setPreco(20D);
		segundoProduto.setPedido(pedido);
		
		produto = JOptionPane.showInputDialog(nomeProduto);
		Produto terceiroProduto = new Produto();
		terceiroProduto.setDescricao(produto);
		terceiroProduto.setQtdPedida(3);
		terceiroProduto.setPreco(25D);
		terceiroProduto.setPedido(pedido);
		
		List<Produto>carrinhoCompras = new ArrayList<Produto>();
		carrinhoCompras.add(priProduto);
		carrinhoCompras.add(segundoProduto);
		carrinhoCompras.add(terceiroProduto);
		
		pedido.setCarrinhoCompras(carrinhoCompras);
		
		String nome   = JOptionPane.showInputDialog(nomeCliente);
		String endCli = JOptionPane.showInputDialog(endereco);
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setEndereco(endCli);
		pedido.setCliente(cliente);
		
		gravarPedidoNoBanco(pedido);
	}
	
	
	private void gravarPedidoNoBanco(Pedido pedido) {
		
	}

	void criarBdECommerce() throws SQLException {
		Connection con = null;
		try {
			String tabCliente = "CREATE TABLE CLIENTE (     " +
								"	ID INTEGER IDENTITY PRIMARY KEY, " +
								"	NOME 	 VARCHAR(300),  " +
								"	ENDERECO VARCHAR(1000)  " +
								")"; 
			
			String tabPedido  = "CREATE TABLE PEDIDO (        " +
								"	ID	INTEGER IDENTITY PRIMARY KEY,  " +
								"	DATA_HORA_CRIACAO DATE,   " +
								"	ID_CLIENTE INTEGER        " +
								")"; 
			String fkPedido  = "ALTER TABLE PEDIDO            " +
							   "ADD FOREIGN KEY(ID_CLIENTE)   " +
							   "REFERENCES CLIENTE(ID)";

			String tabProduto = "CREATE TABLE PRODUTO (             " +
								"	ID		   INTEGER PRIMARY KEY, " +
								"	ID_PEDIDO  INTEGER,       " +
								"	DESCRICAO  VARCHAR(300),  " +
								"	PRECO	   DOUBLE,        " +
								"	QTD_PEDIDA INTEGER        " +
								")";
			String fkProduto =  "ALTER TABLE PRODUTO        " +
								"ADD FOREIGN KEY(ID_PEDIDO) " +
								"REFERENCES PEDIDO(ID)";
			con = getConexao();
			
			PreparedStatement statement = con.prepareStatement(tabCliente);
			statement.executeUpdate();
			statement.close();
			
			statement = con.prepareStatement(tabPedido);
			statement.executeUpdate();
			statement.close();
			
			statement = con.prepareStatement(fkPedido);
			statement.executeUpdate();
			statement.close();

			statement = con.prepareStatement(tabProduto);
			statement.executeUpdate();
			statement.close();

			statement = con.prepareStatement(fkProduto);
			statement.executeUpdate();
			statement.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			con.close();
		}
	}
	
	private Connection getConexao() {
		Connection con = null;
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:file:bd_curso_jdbc", "SA", "");
			
		} catch (Exception e) {
			System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
			e.printStackTrace();
			return null;
		}
		return con;
	}
	
}
