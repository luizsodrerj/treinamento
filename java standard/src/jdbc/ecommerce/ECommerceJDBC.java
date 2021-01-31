package jdbc.ecommerce;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
			
			Pedido pedido = ecommerce.comprarProdutos();
		
			pedido = ecommerce.obterDadosPedido(pedido.getId());
			
			ecommerce.imprimirDadosPedido(pedido);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void imprimirDadosPedido(Pedido pedido) {
		StringBuilder buffer = new StringBuilder();

		buffer.append("Dados do Pedido:\n");
		buffer.append("Data do Pedido: ");
		Date data    = pedido.getDataHoraCriacao();
		String dtFmt = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data);
		buffer.append(dtFmt).append('\n');
		buffer.append("Dados do Cliente:\n");
		buffer.append("Nome: ");
		buffer.append(pedido.getCliente().getNome()).append('\n');
		buffer.append("Endereço: ");
		buffer.append(pedido.getCliente().getEndereco()).append('\n');
		buffer.append("Dados do Carrinho de Compras:\n");
		List<Produto>carrinho = pedido.getCarrinhoCompras();
		
		for (Produto produto : carrinho) {
			buffer.append("Produto: ").append(produto.getDescricao()).append('\n');
			buffer.append("Preço: ").append(produto.getPreco()).append('\n');
			buffer.append("Qtd: ").append(produto.getQtdPedida()).append('\n');
			buffer.append("Valor Total: ").append(produto.getValorTotal()).append('\n');
		}
		buffer.append("Valor Total do Pedido: ").append(pedido.getValorTotalPedido());
		
		System.out.println(buffer.toString());
	}

	Pedido comprarProdutos() throws Exception {
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
				
		produto = JOptionPane.showInputDialog(nomeProduto);
		quantidade = Integer.valueOf(JOptionPane.showInputDialog(qtdProduto));
		Produto segundoProduto = new Produto();
		segundoProduto.setDescricao(produto);
		segundoProduto.setQtdPedida(quantidade);
		segundoProduto.setPreco(20D);
		segundoProduto.setPedido(pedido);
		
		produto = JOptionPane.showInputDialog(nomeProduto);
		quantidade = Integer.valueOf(JOptionPane.showInputDialog(qtdProduto));
		Produto terceiroProduto = new Produto();
		terceiroProduto.setDescricao(produto);
		terceiroProduto.setQtdPedida(quantidade);
		terceiroProduto.setPreco(25D);
		terceiroProduto.setPedido(pedido);
		
		List<Produto>carrinhoCompras = new ArrayList<Produto>();
		carrinhoCompras.add(priProduto);
		carrinhoCompras.add(segundoProduto);
		carrinhoCompras.add(terceiroProduto);
		
		String nome   = JOptionPane.showInputDialog(nomeCliente);
		String endCli = JOptionPane.showInputDialog(endereco);
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setEndereco(endCli);

		pedido.setCarrinhoCompras(carrinhoCompras);
		pedido.setCliente(cliente);
		
		gravarPedidoNoBanco(pedido);
		
		return pedido;
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
			cliente.setId(retornaUltimoIdCriado(con));
			statement.close();
			
			java.sql.Date dtPedido = new java.sql.Date(pedido.getDataHoraCriacao().getTime());
			statement = con.prepareStatement(sqlPedido);
			statement.setDate(1, dtPedido);
			statement.setInt(2, cliente.getId());
			statement.executeUpdate();
			statement.close();
			pedido.setId(retornaUltimoIdCriado(con));
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

	private Pedido obterDadosPedido(Integer idPedido) throws SQLException {
		Connection con = null;
		Pedido pedido  = null;
		
		try {
			String sqlCliente = "SELECT NOME,  " +
								"		ENDERECO " +
								"FROM 	CLIENTE " +
								"WHERE 	ID = ? "; 
			
			String sqlPedido  = "SELECT DATA_HORA_CRIACAO, " +
								"		ID_CLIENTE " +
								"FROM 	PEDIDO " +
								"WHERE 	ID = ? "; 

			String sqlProduto = "SELECT DESCRICAO,  " +
								"		PRECO,      " +
								"		QTD_PEDIDA 	" +
								"FROM 	PRODUTO 	" +
								"WHERE 	ID_PEDIDO = ?"; 
			con = getConexao();

			Cliente cliente = new Cliente();
			pedido 			= new Pedido();
			
			PreparedStatement statement = con.prepareStatement(sqlPedido);
			statement.setInt(1, idPedido);
			ResultSet rs = statement.executeQuery();
			rs.next();
			pedido.setId(idPedido);
			pedido.setDataHoraCriacao(rs.getDate("DATA_HORA_CRIACAO"));
			cliente.setId(rs.getInt("ID_CLIENTE"));
			pedido.setCliente(cliente);
			statement.close();

			statement = con.prepareStatement(sqlCliente);
			statement.setInt(1, cliente.getId());
			rs = statement.executeQuery();
			rs.next();
			cliente.setEndereco(rs.getString("ENDERECO")); 
			cliente.setNome(rs.getString("NOME")); 
			statement.close();
			
			List<Produto>carrinho = new ArrayList<Produto>();
			statement = con.prepareStatement(sqlProduto);
			statement.setInt(1, pedido.getId());
			rs = statement.executeQuery();
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setDescricao(rs.getString("DESCRICAO"));
				produto.setQtdPedida(rs.getInt("QTD_PEDIDA"));
				produto.setPreco(rs.getDouble("PRECO"));
				carrinho.add(produto);
			}
			pedido.setCarrinhoCompras(carrinho);
			statement.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			con.close();
		}
		return pedido;
	}
	
	private int retornaUltimoIdCriado(Connection con) throws SQLException {
		PreparedStatement statement = con.prepareStatement("CALL IDENTITY()");
		ResultSet resultSet 		= statement.executeQuery();
		
		resultSet.next();
		int id = resultSet.getInt(1);
		statement.close();
		
		return id;
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
