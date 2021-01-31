package jdbc;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ECommerceJDBC {

	public static void main(String[] args) {
		try {
			ECommerceJDBC ecommerce = new ECommerceJDBC();

			ecommerce.criarBdECommerce();
			ecommerce.comprarProdutos();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void comprarProdutos() throws Exception {
		JLabel nomeCliente = new JLabel("Informe o nome do Cliente:");
		nomeCliente.setFont(new Font("Arial", Font.BOLD, 18));
		JLabel endereco = new JLabel("Informe o Endereço de Entrega:");
		endereco.setFont(new Font("Arial", Font.BOLD, 18));
		JLabel nomeProduto = new JLabel("Informe o nome do Produto para incluir no Carrinho:");
		nomeProduto.setFont(new Font("Arial", Font.BOLD, 18));
		JLabel qtdProduto = new JLabel("Informe a quantidade:");
		qtdProduto.setFont(new Font("Arial", Font.BOLD, 18));
		
		
		Pedido pedido = new Pedido();
		pedido.setDataHoraCriacao(new Date());
		
		String produto = JOptionPane.showInputDialog(nomeProduto);
		Integer quantidade = Integer.valueOf(JOptionPane.showInputDialog(qtdProduto));
		Produto priProduto = new Produto();
		priProduto.setDescricao(produto);
		priProduto.setQtdPedida(quantidade);
		priProduto.setPreco(500D);
		priProduto.setPedido(pedido);
				
//		produto = JOptionPane.showInputDialog(nomeProduto);
//		quantidade = Integer.valueOf(JOptionPane.showInputDialog(qtdProduto));
//		Produto segundoProduto = new Produto();
//		segundoProduto.setDescricao(produto);
//		segundoProduto.setQtdPedida(quantidade);
//		segundoProduto.setPreco(20D);
//		segundoProduto.setPedido(pedido);
//		
//		produto = JOptionPane.showInputDialog(nomeProduto);
//		quantidade = Integer.valueOf(JOptionPane.showInputDialog(qtdProduto));
//		Produto terceiroProduto = new Produto();
//		terceiroProduto.setDescricao(produto);
//		terceiroProduto.setQtdPedida(quantidade);
//		terceiroProduto.setPreco(25D);
//		terceiroProduto.setPedido(pedido);
		
		List<Produto>carrinhoCompras = new ArrayList<Produto>();
		carrinhoCompras.add(priProduto);
//		carrinhoCompras.add(segundoProduto);
//		carrinhoCompras.add(terceiroProduto);
		
		String nome   = JOptionPane.showInputDialog(nomeCliente);
		String endCli = JOptionPane.showInputDialog(endereco);
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setEndereco(endCli);

		pedido.setCarrinhoCompras(carrinhoCompras);
		pedido.setCliente(cliente);
		
		gravarPedidoNoBanco(pedido);
	}
	
	
	private void gravarPedidoNoBanco(Pedido pedido) throws SQLException {
		Connection con = null;
		try {
			String sqlCliente = "INSERT INTO CLIENTE (     " +
								"	NOME,  " +
								"	ENDERECO " +
								") VALUES( " +
								"   ?, ? " +
								")"; 
			
			String sqlPedido  = "INSERT INTO PEDIDO (  " +
								"	DATA_HORA_CRIACAO, " +
								"	ID_CLIENTE " +
								") VALUES(     " +
								"   ?, ? " +
								")"; 

			String sqlProduto = "INSERT INTO PRODUTO ( " +
								"	ID_PEDIDO,  " +
								"	DESCRICAO,  " +
								"	PRECO,      " +
								"	QTD_PEDIDA " +
								") VALUES(     " +
								"   ?, ?, ?, ? " +
								")"; 
			con = getConexao();
			
			Cliente cliente = pedido.getCliente();
			PreparedStatement statement = con.prepareStatement(sqlCliente);
			statement.setString(1, cliente.getNome());
			statement.setString(2, cliente.getEndereco());
			statement.executeUpdate();
			statement.close();
			statement = con.prepareStatement("CALL IDENTITY()");
			ResultSet rs = statement.executeQuery();
			rs.next();
			cliente.setId(rs.getInt(1));
			statement.close();
			
			java.sql.Date dtPedido = new java.sql.Date(pedido.getDataHoraCriacao().getTime());
			statement = con.prepareStatement(sqlPedido);
			statement.setDate(1, dtPedido);
			statement.setInt(2, cliente.getId());
			statement.executeUpdate();
			statement.close();
			statement = con.prepareStatement("CALL IDENTITY()");
			rs = statement.executeQuery();
			rs.next();
			pedido.setId(rs.getInt(1));
			statement.close();
			
			List<Produto>carrinho = pedido.getCarrinhoCompras();
			
			for (Produto produto: carrinho) {
				statement = con.prepareStatement(sqlProduto);
				statement.setInt(1, pedido.getId());
				statement.setString(2, produto.getDescricao());
				statement.setDouble(3, produto.getPreco());
				statement.setInt(4, produto.getQtdPedida());
				statement.executeUpdate();
				statement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			con.close();
		}
	}

	void criarBdECommerce() {
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
								"	ID		   INTEGER IDENTITY PRIMARY KEY, " +
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
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
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
