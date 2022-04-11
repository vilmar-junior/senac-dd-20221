package model.bo;

import java.time.LocalDateTime;

import model.dao.LinhaTelefonicaDAO;
import model.dao.TelefoneDAO;
import model.entity.Cliente;
import model.entity.LinhaTelefonica;
import model.entity.Telefone;

public class LinhaTelefonicaBO {

	private LinhaTelefonicaDAO linhaDAO = new LinhaTelefonicaDAO();
	private TelefoneDAO telefoneDAO = new TelefoneDAO();
	
	public Cliente obterDonoAtualDoTelefone(int idTelefone) {
		return linhaDAO.obterDonoAtualDoTelefone(idTelefone);
	}

	public boolean liberarLinha(int idTelefone) {
		boolean desativouTelefone = false;
		boolean liberouLinha = false;
		
		//1- Desativar o telefone -> update em telefone (alterando a flag 'ativo')
		Telefone tel = telefoneDAO.consultar(idTelefone);
		tel.setAtivo(false);
		desativouTelefone = telefoneDAO.atualizar(tel);
		
		//2- Desativar o registro atual da linha  
		//   -> update em linha_telefonica (alterando a 'dt_desativacao')
		liberouLinha = linhaDAO.desativarLinhaAtual(idTelefone);
		
		return (liberouLinha && desativouTelefone);
	}

	public boolean criarLinha(Telefone telefoneSelecionado, Cliente clienteSelecionado) {
		boolean criouNovaLinha = false;
		boolean ativouTelefone = false;

		//1- criar um novo registro de linha telefônica
		LinhaTelefonica novaLinha = new LinhaTelefonica();
		novaLinha.setTelefone(telefoneSelecionado);
		novaLinha.setIdCliente(clienteSelecionado.getId());
		novaLinha.setDataAtivacao(LocalDateTime.now());
		novaLinha = linhaDAO.inserir(novaLinha);
		
		criouNovaLinha = novaLinha.getId() > 0;
		
		//2 - ativar o telefone
		telefoneSelecionado.setAtivo(true);
		ativouTelefone = telefoneDAO.atualizar(telefoneSelecionado);
		
		return criouNovaLinha && ativouTelefone;
	}
}
