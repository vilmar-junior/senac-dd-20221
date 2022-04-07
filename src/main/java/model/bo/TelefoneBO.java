package model.bo;

import java.util.ArrayList;

import model.dao.TelefoneDAO;
import model.entity.Telefone;

public class TelefoneBO {
	
	private TelefoneDAO dao = new TelefoneDAO();
	
	public ArrayList<Telefone> consultarTodos(){
		return dao.consultarTodos();
	}
}
