package controller;

import java.util.ArrayList;

import model.bo.TelefoneBO;
import model.entity.Telefone;

public class TelefoneController {
	
	private TelefoneBO bo = new TelefoneBO();
	
	public ArrayList<Telefone> consultarTodos(){
		return bo.consultarTodos();
	}
}
