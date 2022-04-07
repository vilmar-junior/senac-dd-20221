package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ClienteController;
import controller.LinhaTelefonicaController;
import controller.TelefoneController;
import model.entity.Cliente;
import model.entity.Telefone;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaGerenciamentoLinhas extends JFrame {

	private JPanel contentPane;
	private JComboBox cbTelefone;
	private JComboBox cbCliente;
	private ClienteController clienteController = new ClienteController();
	private TelefoneController telefoneController = new TelefoneController();
	private LinhaTelefonicaController linhaTelefonicaController = new LinhaTelefonicaController();
	private JButton btnCriarLinha;
	private JButton btnLiberarTelefone;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaGerenciamentoLinhas frame = new TelaGerenciamentoLinhas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaGerenciamentoLinhas() {
		setTitle("Gerenciar linhas telefônicas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(20, 10, 150, 14);
		contentPane.add(lblTelefone);
		
		JLabel lblUsuario = new JLabel("Usuário:");
		lblUsuario.setBounds(20, 60, 150, 14);
		contentPane.add(lblUsuario);
		
		ArrayList<Telefone> telefones = telefoneController.consultarTodos();
		cbTelefone = new JComboBox(telefones.toArray());
		cbTelefone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Telefone telefoneSelecionado = (Telefone) cbTelefone.getSelectedItem();
				
				if(telefoneSelecionado != null) {
					if(telefoneSelecionado.isAtivo()) {
						//TODO continuar
						
						//Mostrar o cliente atual da linha (pegar no backend) 
						//depois selecionar no cbCliente 
						btnLiberarTelefone.setEnabled(true);
						btnCriarLinha.setEnabled(false);
						
						Cliente dono = linhaTelefonicaController.obterDonoAtualDoTelefone(telefoneSelecionado.getId());
						cbCliente.getModel().setSelectedItem(dono);
					}else {
						//TODO continuar
						btnLiberarTelefone.setEnabled(false);
						btnCriarLinha.setEnabled(true);
					}
				}
				
			}
		});
		cbTelefone.setBounds(20, 30, 370, 22);
		cbTelefone.setSelectedIndex(-1);
		contentPane.add(cbTelefone);
		
		ArrayList<Cliente> clientes = clienteController.consultarTodos();
		cbCliente = new JComboBox(clientes.toArray());
		cbCliente.setBounds(20, 80, 370, 22);
		cbCliente.setSelectedIndex(-1);
		contentPane.add(cbCliente);
		
		btnCriarLinha = new JButton("Associar o telefone ao usuário");
		btnCriarLinha.setEnabled(false);
		btnCriarLinha.setBounds(20, 115, 370, 23);
		contentPane.add(btnCriarLinha);
		
		btnLiberarTelefone = new JButton("Liberar telefone");
		btnLiberarTelefone.setEnabled(false);
		btnLiberarTelefone.setBounds(20, 145, 370, 23);
		contentPane.add(btnLiberarTelefone);
	}
}
