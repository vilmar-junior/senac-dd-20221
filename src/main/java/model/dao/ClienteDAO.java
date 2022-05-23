package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.entity.Cliente;
import model.entity.Endereco;
import model.entity.LinhaTelefonica;
import model.seletor.TelefoneSeletor;

public class ClienteDAO implements BaseDAO<Cliente>{
	
	public Cliente inserir(Cliente novoCliente) {
		Connection conexao = Banco.getConnection();
		String sql = " INSERT INTO CLIENTE(NOME, CPF, ID_ENDERECO)" 
					+ "VALUES (?, ?, ?);";
		
		//TODO como inserir o endereço e as linhas telefônicas?
		
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setString(1, novoCliente.getNome());
			stmt.setString(2, novoCliente.getCpf());
			stmt.setInt(3, novoCliente.getEndereco().getId());
			
			stmt.execute();
			
			ResultSet chavesGeradas = stmt.getGeneratedKeys();
			if(chavesGeradas.next()) {
				novoCliente.setId(chavesGeradas.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao inserir cliente. Causa:" + e.getMessage());
		}
		
		return novoCliente;
	}
	
	public boolean atualizar(Cliente cliente) {
		boolean atualizou = false;
		Connection conexao = Banco.getConnection();
		String sql = " UPDATE CLIENTE "
					+" SET NOME=?, CPF=?, ID_ENDERECO=? "
					+" WHERE ID=? ";
		
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getCpf());
			stmt.setInt(3, cliente.getEndereco().getId());
			stmt.setInt(4, cliente.getId());
			
			int linhasAfetadas = stmt.executeUpdate();
			atualizou = linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar cliente. Causa:" + e.getMessage());
		}
		
		return atualizou;
	}
	
	public boolean remover(int id) {
		boolean removeu = false;
		
		Connection conexao = Banco.getConnection();
		String sql = " DELETE FROM CLIENTE "
					+" WHERE ID=?";
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setInt(1, id);
			removeu = stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao remover cliente. Causa:" + e.getMessage());
		}		
		
		return removeu;
	}
	
	public Cliente consultar(int id) {
		Cliente clienteConsultado = null;
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM CLIENTE "
					+" WHERE ID=?";
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);
		
		try {
			stmt.setInt(1, id);
			ResultSet resultado = stmt.executeQuery();
			
			if(resultado.next()) {
				clienteConsultado = construirDoResultSet(resultado);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente (id:" + id + ". Causa:" + e.getMessage());
		}
		
		return clienteConsultado;
	}
	
	private Cliente construirDoResultSet(ResultSet resultado) throws SQLException {
		Cliente clienteConsultado = new Cliente();
		clienteConsultado.setId(resultado.getInt("id"));
		clienteConsultado.setCpf(resultado.getString("cpf"));
		clienteConsultado.setNome(resultado.getString("nome"));
		
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		Endereco enderecoDoCliente = enderecoDAO.consultar(resultado.getInt("id_endereco"));
		clienteConsultado.setEndereco(enderecoDoCliente);
		
		LinhaTelefonicaDAO linhaDAO = new LinhaTelefonicaDAO();
		ArrayList<LinhaTelefonica> linhas = linhaDAO.consultarPorIdCliente(resultado.getInt("id"));
		clienteConsultado.setLinhas(linhas);
		
		return clienteConsultado;
	}

	public ArrayList<Cliente> consultarComSeletor(TelefoneSeletor seletor){
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM CLIENTE ";
		
		if(seletor.temFiltro()) {
			sql = preencherFiltros(sql, seletor);
		}
		
		if(seletor.temPaginacao()) {
			sql += " LIMIT " + seletor.getLimite() + " OFFSET " + seletor.getOffset(); 
		}
		
		
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);
		
		try {
			ResultSet resultado = stmt.executeQuery();
			
			while(resultado.next()) {
				Cliente clienteConsultado = construirDoResultSet(resultado);
				clientes.add(clienteConsultado);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar os clientes usando filtro. Causa:" + e.getMessage());
		}
		
		return clientes;
	}
	
	private String preencherFiltros(String sql, TelefoneSeletor seletor) {
		boolean primeiro = true;
		sql += " WHERE ";
		
		if(seletor.getCpfDonoTelefone() != null && !seletor.getCpfDonoTelefone().isEmpty()) {
			if(!primeiro) {
				sql = " AND ";
			}
			
			sql += " CPF = " + seletor.getCpfDonoTelefone();
			primeiro = false;
		}
		
		if(seletor.getNomeDonoTelefone() != null 
				&& !seletor.getNomeDonoTelefone().trim().isEmpty()) {
			if(!primeiro) {
				sql = " AND ";
			}
			
			sql += " UPPER(NOME) LIKE '%" + seletor.getNomeDonoTelefone().toUpperCase() + "%'";
			primeiro = false;
		}
		
		return sql;
	}

	public ArrayList<Cliente> consultarTodos(){
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM CLIENTE ";
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);
		
		try {
			ResultSet resultado = stmt.executeQuery();
			
			while(resultado.next()) {
				Cliente clienteConsultado = construirDoResultSet(resultado);
				clientes.add(clienteConsultado);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os clientes. Causa:" + e.getMessage());
		}
		
		return clientes;
	}
	
	public boolean cpfJaCadastrado(String cpf) {
		Connection conexao = Banco.getConnection();
		String sql = " SELECT COUNT(ID) FROM CLIENTE "
				+ "	WHERE CPF = ?";
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);
		
		boolean cpfJaCadastrado = false;
		
		try {
			stmt.setString(1, cpf);
			ResultSet resultado = stmt.executeQuery();
			
			if(resultado.next()) {
				cpfJaCadastrado = resultado.getInt(1) > 0;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os clientes. Causa:" + e.getMessage());
		}
		
		return cpfJaCadastrado;
	}

	public Cliente consultarPorCPF(String cpf) {
		Cliente clienteConsultado = null;
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM CLIENTE "
					+" WHERE CPF=?";
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);
		
		try {
			stmt.setString(1, cpf);
			ResultSet resultado = stmt.executeQuery();
			
			if(resultado.next()) {
				clienteConsultado = construirDoResultSet(resultado);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente por CPF (" + cpf + "). Causa:" + e.getMessage());
		}
		
		return clienteConsultado;
	}

	public int contarClientes() {
		Connection conexao = Banco.getConnection();
		String sql = " SELECT count(id) FROM CLIENTE";
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);
		int total = 0;
		
		try {
			ResultSet resultado = stmt.executeQuery();
			
			if(resultado.next()) {
				total = resultado.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao total de clientes. Causa:" + e.getMessage());
		}
		
		return total;
	}
}
