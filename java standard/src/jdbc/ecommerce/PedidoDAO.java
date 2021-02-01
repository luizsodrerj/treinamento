package jdbc.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

	private Connection con;
	
	public PedidoDAO(Connection con) {
		this.con = con;
	}

	public void insereCliente(Pedido pedido) throws SQLException {
		String sqlCliente = "INSERT INTO CLIENTE ( " +
							"	NOME,  " +
							"	ENDERECO " +
							") VALUES( " +
							"   ?, ? " +
							")"; 
		Cliente cliente = pedido.getCliente();
		PreparedStatement statement = con.prepareStatement(sqlCliente);
		statement.setString(1, cliente.getNome());
		statement.setString(2, cliente.getEndereco());
		statement.executeUpdate();
		statement.close();
		cliente.setId(retornaUltimoIdCriado());
		statement.close();
	}

	public void inserePedido(Pedido pedido) throws SQLException {
		String sqlPedido  = "INSERT INTO PEDIDO (  " +
							"	DATA_HORA_CRIACAO, " +
							"	ID_CLIENTE " +
							") VALUES(     " +
							"   ?, ? " +
							")"; 
		java.sql.Date dtPedido = new java.sql.Date(pedido.getDataHoraCriacao().getTime());
		PreparedStatement statement = con.prepareStatement(sqlPedido);
		statement.setDate(1, dtPedido);
		statement.setInt(2, pedido.getCliente().getId());
		statement.executeUpdate();
		statement.close();
		pedido.setId(retornaUltimoIdCriado());
		statement.close();
	}

	public void insereCarrinhoCompras(Pedido pedido) throws SQLException {
		String sqlProduto = "INSERT INTO PRODUTO ( " +
							"	ID_PEDIDO,  " +
							"	DESCRICAO,  " +
							"	PRECO,      " +
							"	QTD_PEDIDA " +
							") VALUES(     " +
							"   ?, ?, ?, ? " +
							")"; 
		List<Produto>carrinho = pedido.getCarrinhoCompras();
		
		for (Produto produto: carrinho) {
			PreparedStatement statement = con.prepareStatement(sqlProduto);
			statement.setInt(1, pedido.getId());
			statement.setString(2, produto.getDescricao());
			statement.setDouble(3, produto.getPreco());
			statement.setInt(4, produto.getQtdPedida());
			statement.executeUpdate();
			statement.close();
		}
	}
	
	public Pedido obterDadosPedido(Integer idPedido) {
		Pedido pedido  = null;
		
		try {
			pedido = obterPedidoPorId(idPedido);
			Cliente cliente = obterClientePorIdPedido(idPedido);
			pedido.setCliente(cliente);
			
			List<Produto>carrinho = obterCarrinhoComprasPedido(idPedido);
			pedido.setCarrinhoCompras(carrinho);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return pedido;
	}

	public List<Produto> obterCarrinhoComprasPedido(Integer idPedido) throws SQLException {
		String sqlProduto = "SELECT DESCRICAO,  " +
							"		PRECO,      " +
							"		QTD_PEDIDA 	" +
							"FROM 	PRODUTO 	" +
							"WHERE 	ID_PEDIDO = ?"; 
		
		List<Produto>carrinho = new ArrayList<Produto>();
		
		PreparedStatement statement = con.prepareStatement(sqlProduto);
		statement.setInt(1, idPedido);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			Produto produto = new Produto();
			produto.setDescricao(rs.getString("DESCRICAO"));
			produto.setQtdPedida(rs.getInt("QTD_PEDIDA"));
			produto.setPreco(rs.getDouble("PRECO"));
			carrinho.add(produto);
		}
		statement.close();
		
		return carrinho;
	}
	
	public Cliente obterClientePorIdPedido(Integer idPedido) throws SQLException {
		String sqlCliente = "SELECT C.NOME,  " +
							"		C.ENDERECO " +
							"FROM 	CLIENTE C, " +
							"		PEDIDO P " +
							"WHERE 	C.ID = P.ID_CLIENTE " + 
							"AND	P.ID = ? "; 
		Cliente cliente = new Cliente();
		
		PreparedStatement statement = con.prepareStatement(sqlCliente);
		statement.setInt(1, idPedido);
		ResultSet rs = statement.executeQuery();
		rs.next();
		cliente.setEndereco(rs.getString("ENDERECO")); 
		cliente.setNome(rs.getString("NOME")); 
		statement.close();
		
		return cliente;
	}
	
	public Pedido obterPedidoPorId(Integer idPedido) throws SQLException {
		String sqlPedido  = "SELECT DATA_HORA_CRIACAO " +
							"FROM 	PEDIDO " +
							"WHERE 	ID = ? "; 

		Pedido pedido = new Pedido();

		PreparedStatement statement = con.prepareStatement(sqlPedido);
		statement.setInt(1, idPedido);
		ResultSet rs = statement.executeQuery();
		rs.next();
		pedido.setId(idPedido);
		pedido.setDataHoraCriacao(rs.getDate("DATA_HORA_CRIACAO"));
		statement.close();
		
		return pedido;
	}
	
	public int retornaUltimoIdCriado() throws SQLException {
		PreparedStatement statement = con.prepareStatement("CALL IDENTITY()");
		ResultSet resultSet 		= statement.executeQuery();
		
		resultSet.next();
		int id = resultSet.getInt(1);
		statement.close();
		
		return id;
	}
	
	public void criarBdECommerce() {
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
			closeConnection();
		}
	}
	
	public void closeConnection() {
		try {
			con.close();		
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
