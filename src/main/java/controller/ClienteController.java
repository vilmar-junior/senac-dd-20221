package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.bo.ClienteBO;
import model.entity.Cliente;
import model.exception.ClienteComLinhaTelefonicaException;
import model.exception.CpfInvalidoException;
import model.exception.ErroAoSalvarClienteException;

public class ClienteController {

	private static final int QUANTIDADE_DIGITOS_CPF = 11;
	public ClienteBO bo = new ClienteBO();
	
	public String salvar(Cliente novo) throws ErroAoSalvarClienteException {
		String mensagem = "";
		
		if(novo == null) {
			mensagem = "Informe todos os dados do novo cliente";
		}else if(novo.getCpf().trim().length() != 11){
			try {
				Long.parseLong(novo.getCpf());
			} catch (NumberFormatException excecao) {
				mensagem = "CPF deve somente números \n";
			}
			
			mensagem += "CPF deve conter 11 dígitos";
			
			throw new ErroAoSalvarClienteException(mensagem);
		}
		
		if(mensagem.isEmpty()) {
			mensagem = bo.salvar(novo);
		}
		
		return mensagem;
	}

	public ArrayList<Cliente> consultarTodos() {
		return bo.consultarTodos();
	}

	public String excluir(Cliente clienteParaExcluir) throws ClienteComLinhaTelefonicaException {
		String mensagem = "";
		
		if(bo.excluir(clienteParaExcluir)) {
			mensagem = "Cliente " + clienteParaExcluir.getNome() 
				+ " (" + clienteParaExcluir.getCpf() + ") foi excluído";
		}
		
		return mensagem;
	}

	public Cliente consultarPorCPF(String cpf) throws CpfInvalidoException {
		if(cpf == null || cpf.isEmpty() || cpf.trim().length() != QUANTIDADE_DIGITOS_CPF) {
			throw new CpfInvalidoException("Informe um CPF válido");
		}
		
		return bo.consultarPorCPF(cpf);
	}
	
}
