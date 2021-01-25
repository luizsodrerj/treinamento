package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class ApuracaoJDBC {

	
	
	public void verificarTabelaCandidatos() {
		Connection con = null;
		try {
			con = getConexao();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Connection getConexao() {
		Connection con = null;
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:file:apuracaodb", "SA", "");
			
		} catch (Exception e) {
			System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
			e.printStackTrace();
			return null;
		}
		return con;
	}

}
