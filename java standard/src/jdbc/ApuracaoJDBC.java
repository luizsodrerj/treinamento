package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fluxobytes.ApuracaoVotosArquivo;
import fluxobytes.Candidato;

public class ApuracaoJDBC {

	
	public void executarApuracao() {
		try {
			criarTabelaCandidatos();

			ApuracaoVotosArquivo apuracaoArquivo = new ApuracaoVotosArquivo();
			apuracaoArquivo.apurarVotos();
			List<Candidato> candidatos = apuracaoArquivo.getCandidatos();
			
			inserirCandidatos(candidatos);

			imprimirApuracao();
			
			imprimirCandidatoMaisVotado();
			
			imprimirCandidatoMenosVotado();

			imprimirTotalDeVotos();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}

	public void imprimirApuracao() throws SQLException {
		Connection con = null;
		String sql =  "SELECT ID, "
					+ "		  NOME, "
					+ "		  TOTAL_VOTOS "
					+ "FROM   CANDIDATO";
		try {
			con 						= getConexao();
			List<Candidato> candidatos  = new ArrayList<>();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet resultSet         = statement.executeQuery();
			
			while (resultSet.next()) {
				Candidato candidato = new Candidato(
										resultSet.getString("NOME"), 
										resultSet.getInt("TOTAL_VOTOS")
									  );
				candidato.setId(resultSet.getInt("ID"));
				candidatos.add(candidato);
			}
			for (Candidato candidato: candidatos) {
				System.out.println(
					"Candidato: " + candidato.getNome() + " - " + 
					"Total de Votos: " + candidato.getTotalVotos()
				);
			}
			statement.close();
			
		} finally {
			con.close();
		}
	}

	public void imprimirCandidatoMaisVotado() throws SQLException {
		Connection con = null;
		String sql =  "SELECT NOME, "
					+ "		  TOTAL_VOTOS "
					+ "FROM   CANDIDATO C "
					+ "WHERE  c.TOTAL_VOTOS = ( "
					+ "		SELECT MAX(CN.TOTAL_VOTOS) "
					+ "		FROM CANDIDATO CN "
					+ "	  )";
		try {
			con 						= getConexao();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet resultSet         = statement.executeQuery();
			Candidato candidato			= null;
			
			if (resultSet.next()) {
				candidato = new Candidato();
				candidato.setNome(resultSet.getString("NOME"));
				candidato.setTotalVotos(resultSet.getInt("TOTAL_VOTOS")); 
			}
			System.out.println(
				"Candidato mais votado: " + candidato.getNome() + " - " + 
				"Total de Votos: " + candidato.getTotalVotos()
			);
			statement.close();
			
		} finally {
			con.close();
		}
	}

	public void imprimirTotalDeVotos() throws SQLException {
		Connection con = null;
		String sql =  "SELECT SUM(TOTAL_VOTOS) AS TOTAL "
					+ "FROM   CANDIDATO ";
		try {
			con 						= getConexao();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet resultSet         = statement.executeQuery();
			int totalDeVotos			= 0;
			
			if (resultSet.next()) {
				totalDeVotos = resultSet.getInt("TOTAL");
			}
			System.out.println("Total de Votos: " + totalDeVotos);
			statement.close();
			
		} finally {
			con.close();
		}
	}
	
	public void imprimirCandidatoMenosVotado() throws SQLException {
		Connection con = null;
		String sql =  "SELECT NOME, "
					+ "		  TOTAL_VOTOS "
					+ "FROM   CANDIDATO C "
					+ "WHERE  c.TOTAL_VOTOS = ( "
					+ "		SELECT MIN(CN.TOTAL_VOTOS) "
					+ "		FROM CANDIDATO CN "
					+ "	  )";
		try {
			con 						= getConexao();
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet resultSet         = statement.executeQuery();
			Candidato candidato			= null;
			
			if (resultSet.next()) {
				candidato = new Candidato();
				candidato.setNome(resultSet.getString("NOME"));
				candidato.setTotalVotos(resultSet.getInt("TOTAL_VOTOS")); 
			}
			System.out.println(
				"Candidato menos votado: " + candidato.getNome() + " - " + 
				"Total de Votos: " + candidato.getTotalVotos()
			);
			statement.close();
			
		} finally {
			con.close();
		}
	}
	
	public void inserirCandidatos(Collection<Candidato> candidatos) throws SQLException {
		Connection con = null;
		try {
			String sql =  "INSERT INTO CANDIDATO( "
						+ "  ID, NOME, TOTAL_VOTOS "
						+ ") VALUES ( "
						+ "  ?, ?, ?"
						+ ")";
			con = getConexao();
			int id = 0;
			
			for (Candidato candidato : candidatos) {
				candidato.setId(++id);
				PreparedStatement statement = con.prepareStatement(sql);
				statement.setInt(1, candidato.getId());
				statement.setString(2, candidato.getNome());
				statement.setInt(3, candidato.getTotalVotos());
				statement.executeUpdate();
				statement.close();
			}
		} finally {
			con.close();
		}
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
