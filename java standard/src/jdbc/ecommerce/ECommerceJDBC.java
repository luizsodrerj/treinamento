package jdbc.ecommerce;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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

	private Pedido obterDadosPedido(Integer id) {
		PedidoDAO pedidoDAO = new PedidoDAO(getConexao());
		
		return pedidoDAO.obterDadosPedido(id);
	}

	private void criarBdECommerce() {
		PedidoDAO pedidoDAO = new PedidoDAO(getConexao());
		
		pedidoDAO.criarBdECommerce();
	}

	void imprimirDadosPedido(Pedido pedido) {
		StringBuilder buffer = new StringBuilder();
		DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance(new Locale("pt","BR"));
        decimalFormat.getDecimalFormatSymbols().setDecimalSeparator(',');
        decimalFormat.getDecimalFormatSymbols().setGroupingSeparator('.');
        decimalFormat.setMinimumFractionDigits(2);
		
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
			buffer.append("Preço: ").append(decimalFormat.format(produto.getPreco())).append('\n');
			buffer.append("Qtd: ").append(produto.getQtdPedida()).append('\n');
			buffer.append("Valor Total: ")
				  .append(
					  decimalFormat.format(
						produto.getValorTotal()
					  )
				   )
				   .append('\n');
		}
		buffer.append("Valor Total do Pedido: ")
			  .append(
				decimalFormat.format(
					pedido.getValorTotalPedido()
				)
			   );
		String text = buffer.toString();
		
		JTextArea textArea = new JTextArea(text);
		textArea.setFont(new Font("Arial", Font.BOLD, 20));
	    textArea.setColumns(30);
	    textArea.setRows(14);
	    textArea.setWrapStyleWord(true);
	    textArea.setLineWrap(true);
	    textArea.setSize(textArea.getPreferredSize().width, textArea.getPreferredSize().height);
	    JOptionPane.showConfirmDialog(
	    		null, 
	    		new JScrollPane(textArea), 
	    		"e-commerce", 
	    		JOptionPane.OK_OPTION
	    	);
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
		PedidoDAO pedidoDAO = null;
		Connection con = null;

		try {
			con = getConexao();
			pedidoDAO = new PedidoDAO(con);
			
			pedidoDAO.beginTransaction();
			
			pedidoDAO.insereCliente(pedido);
			
			pedidoDAO.inserePedido(pedido);
			
			pedidoDAO.insereCarrinhoCompras(pedido);

			pedidoDAO.commitTransaction();
			
		} catch (Exception e) {
			e.printStackTrace();
			pedidoDAO.rollbackTransaction();
			throw new RuntimeException(e);
		} finally {
			pedidoDAO.closeConnection();
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
