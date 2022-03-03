package model.dao;

import java.util.ArrayList;

import model.entity.Endereco;

public class EnderecoDAO {

	public Endereco inserir(Endereco novoEndereco) {
		//TODO implementar
		
		return novoEndereco;
	}
	
	public boolean atualizar(Endereco endereco) {
		boolean atualizou = false;
		//TODO implementar
		
		
		return atualizou;
	}
	
	public boolean remover(int id) {
		// DELETE FROM ENDERECO WHERE ID = ?
		boolean removeu = false;
		//TODO implementar
		
		
		return removeu;
	}
	
	public Endereco consultar(int id) {
		Endereco enderecoConsultado = null;
		//TODO implementar		
		//SELECT * FROM ENDERECO WHERE ID = ?
		
		return enderecoConsultado;
	}
	
	public ArrayList<Endereco> consultarTodos(){
		ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
		//TODO implementar
		//SELECT * FROM ENDERECO
		
		return enderecos;
	}
}
