package controller;

import model.bo.LinhaTelefonicaBO;
import model.entity.Cliente;
import model.entity.Telefone;

public class LinhaTelefonicaController {
	
	public LinhaTelefonicaBO bo = new LinhaTelefonicaBO();
	
	public Cliente obterDonoAtualDoTelefone(int idTelefone){
		return bo.obterDonoAtualDoTelefone(idTelefone);
	}

	public boolean liberarLinha(int idTelefone) {
		return bo.liberarLinha(idTelefone);
	}

	public boolean criarLinha(Telefone telefoneSelecionado, Cliente clienteSelecionado) {
		return bo.criarLinha(telefoneSelecionado, clienteSelecionado);
	}
}
