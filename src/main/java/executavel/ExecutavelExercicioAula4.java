package executavel;

import java.util.ArrayList;
import java.util.List;

import model.dao.EnderecoDAO;
import model.entity.Cliente;
import model.entity.Endereco;
import model.entity.Telefone;

public class ExecutavelExercicioAula4 {

	public static void main(String[] argumentos) {
		//TESTE (aula 4)
//		Endereco novo = new Endereco("Anita Garibaldi", "300", "Florianópolis", 
//				"SC", "88320005");
//
//		novo.setId(1);
//		
		EnderecoDAO dao = new EnderecoDAO();
//		if(dao.atualizar(novo)) {
//			System.out.println("Endereço atualizado");
//		}else {
//			System.out.println("Não atualizou");
//		}
		
		dao.remover(1);
		
//		dao.inserir(novo);
		
//		if(novo.getId() > 0) {
//			System.out.println("Salvo com sucesso. Id: " + novo.getId());
//		}else {
//			System.out.println("Não salvou endereço");
//		}
		
		
//		List<Cliente> clientes = new ArrayList<Cliente>();
//		
//		Endereco endereco1 = new Endereco("Mauro Ramos", "10", "Florianópolis", 
//				"SC", "88320-005"); 
//		
//		List<Telefone> telefonesDoPele = new ArrayList<Telefone>();
//		Telefone t1 = new Telefone("48", "3232-5555", Telefone.TIPO_FIXO, true);
//		Telefone t2 = new Telefone("47", "3232-1010", Telefone.TIPO_MOVEL, true);
//		
//		telefonesDoPele.add(t1);
//		telefonesDoPele.add(t2);
//		
//		Cliente pele = new Cliente("Edson Arantes", "01001011100", 
//				endereco1, telefonesDoPele);
//		
//		clientes.add(pele);
//		
//		for(Telefone t: pele.getTelefones()) {
//			System.out.println(t.getNumero());
//		}
	}
}
