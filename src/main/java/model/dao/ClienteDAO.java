package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.entity.Cliente;

public class ClienteDAO {
 
	public Cliente inserir(Cliente novoCliente) {
		Connection conexao = Banco.getConnection();
		String sql = " INSERT INTO CLIENTE(NOME, CPF)" 
					+ "VALUES (?, ?);";
		
		//TODO como inserir o endereço e as linhas telefônicas?
		
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setString(1, novoCliente.getNome());
			stmt.setString(2, novoCliente.getCpf());
			
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
					+" SET NOME=?, CPF=? "
					+" WHERE ID=? ";
		
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getCpf());
			
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
		//TODO implementar		
		//SELECT * FROM CLIENTE WHERE ID = ?
		
		return clienteConsultado;
	}
	
	public ArrayList<Cliente> consultarTodos(){
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		//TODO implementar
		//SELECT * FROM CLIENTE
		
		return clientes;
	}
}
