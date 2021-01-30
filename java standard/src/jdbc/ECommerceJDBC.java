package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ECommerceJDBC {

	public static void main(String[] args) {

	}

	private void criarBdECommerce() throws SQLException {
		Connection con = null;
		try {
			String tabCliente = "CREATE TABLE CLIENTE (     " +
								"	ID INTEGER PRIMARY KEY, " +
								"	NOME 	 VARCHAR(300),  " +
								"	ENDERECO VARCHAR(1000)  " +
								")"; 
			
			String tabPedido  = "CREATE TABLE PEDIDO (        " +
								"	ID	INTEGER PRIMARY KEY,  " +
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
	
	public Connection getConexao() {
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
