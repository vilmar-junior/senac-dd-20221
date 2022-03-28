package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.ClienteController;
import controller.EnderecoController;
import model.entity.Cliente;
import model.entity.Endereco;

public class TelaCadastroCliente extends JFrame {

	private ClienteController clienteController = new ClienteController();
	private EnderecoController enderecoController = new EnderecoController();
	private JLabel lblNome;
	private JLabel lblCpf;
	private JLabel lblEndereco;
	private JTextField txtNome;
	private JTextField txtCpf;
	private JComboBox cbEndereco;
	private JButton btnSalvar;
	
	public TelaCadastroCliente() {
		this.setTitle("Cadastro de cliente");
		this.setBounds(300, 300, 600, 170);
		
		//Cria os componentes da tela
		lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 10, 100, 20);
		
		txtNome = new JTextField();
		txtNome.setBounds(110, 10, 460, 20);
		
		lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(10, 40, 100, 20);
		
		txtCpf = new JTextField();
		txtCpf.setBounds(110, 40, 460, 20);
		
		lblEndereco = new JLabel("Endereço:");
		lblEndereco.setBounds(10, 70, 100, 20);

		ArrayList<Endereco> enderecos = enderecoController.pesquisarTodos();
		
		cbEndereco = new JComboBox(enderecos.toArray());
		cbEndereco.setBounds(110, 70, 460, 20);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(250, 100, 100, 20);
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente novoCliente = new Cliente();
				novoCliente.setNome(txtNome.getText());
				novoCliente.setCpf(txtCpf.getText());
				novoCliente.setEndereco((Endereco)cbEndereco.getSelectedItem());
				
				String mensagem = clienteController.salvar(novoCliente);
				JOptionPane.showMessageDialog(null, mensagem,"Mensagem", JOptionPane.INFORMATION_MESSAGE);
				
				limparCampos();
			}
		});

		this.setLayout(null);
		this.getContentPane().add(lblNome);
		this.getContentPane().add(txtNome);
		this.getContentPane().add(lblCpf);
		this.getContentPane().add(txtCpf);
		this.getContentPane().add(lblEndereco);
		this.getContentPane().add(cbEndereco);
		this.getContentPane().add(btnSalvar);
	}

	protected void limparCampos() {
		this.txtNome.setText("");
		this.txtCpf.setText("");
		this.cbEndereco.setSelectedItem(null);
	}
}
