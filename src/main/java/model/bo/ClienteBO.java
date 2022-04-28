package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.dao.ClienteDAO;
import model.entity.Cliente;
import model.exception.ClienteComLinhaTelefonicaException;
import model.exception.ErroAoSalvarClienteException;

public class ClienteBO {

	private ClienteDAO dao = new ClienteDAO();

	public String salvar(Cliente cliente) throws ErroAoSalvarClienteException {
		String mensagem = "";

		if(cliente.getId() > 0) {
			if(dao.atualizar(cliente)) {
				mensagem = "Cliente atualizado com sucesso";
			}else {
				throw new ErroAoSalvarClienteException(
						"Erro ao atualizars cliente, entre em contato com o suporte");
			}
		}else {
			if(dao.cpfJaCadastrado(cliente.getCpf())) {
				throw new ErroAoSalvarClienteException(
						"CPF informado (" + cliente.getCpf() + ") já foi utilizado");
			}else {
				cliente = dao.inserir(cliente);
				if(cliente.getId() > 0) {
					mensagem = "Cliente cadastrado com sucesso (id: " + cliente.getId() + ")";
				}else {
					throw new ErroAoSalvarClienteException(
							"Erro ao cadastrar cliente, entre em contato com o suporte");
				}
			}
		}

		return mensagem;
	}

	public List<Cliente> consultarTodos() {
		return dao.consultarTodos();
	}

	/**
	 * Exclui um cliente, somente se ele não possuir nenhuma linha telefônica
	 * @param clienteParaExcluir o cliente a ser excluído
	 * @return se excluiu ou não
	 * @throws ClienteComLinhaTelefonicaException lançada quando o cliente possui linha(s)
	 */
	public boolean excluir(Cliente clienteParaExcluir) throws ClienteComLinhaTelefonicaException {
		boolean excluiu;

		if(!clienteParaExcluir.getLinhas().isEmpty()){
			throw new ClienteComLinhaTelefonicaException("Cliente informado não pode ser excluído,"
					+ " pois possui linha(s) telefônica(s)");
		}else {
			excluiu = dao.remover(clienteParaExcluir.getId());
		}

		return excluiu;
	}

	public Cliente consultarPorCPF(String cpf) {
		return dao.consultarPorCPF(cpf);
	}










}
