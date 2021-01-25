package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApuracaoJDBC {

	
	public void executarApuracao() {
		List<Candidato> candidatos = new ArrayList<>();
		
		criarTabelaCandidatos();
		
		Connection con = null;
		
		try {
			con = getConexao();
			int id = 0;
			
			for (Candidato candidato : candidatos) {
				candidato.setId(++id);
				inserirCandidato(candidato, con);
			}
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
	
	public void inserirCandidato(Candidato candidato, Connection con) throws SQLException {
		String sql =  "INSERT INTO CANDIDATO( "
					+ "  ID, NOME, TOTAL_VOTOS "
					+ ") VALUES ( "
					+ "  ?, ?, ?"
					+ ")";
		PreparedStatement statement = con.prepareStatement(sql);
		statement.setInt(1, candidato.getId());
		statement.setString(2, candidato.getNome());
		statement.setInt(3, candidato.getTotalVotos());
		statement.executeUpdate();
		statement.close();
	}
	
	public void criarTabelaCandidatos() {
		Connection con = null;
		try {
			String sql =  "CREATE TABLE CANDIDATO( "
						+ "  ID INTEGER PRIMARY KEY,"
						+ "  NOME VARCHAR(500), "
						+ "  TOTAL_VOTOS INTEGER "
						+ ")";
			con = getConexao();
			
			PreparedStatement statement = con.prepareStatement(sql);
			statement.executeUpdate();
			
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
