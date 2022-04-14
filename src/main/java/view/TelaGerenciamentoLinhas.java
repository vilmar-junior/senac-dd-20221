package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ClienteController;
import controller.LinhaTelefonicaController;
import controller.TelefoneController;
import model.entity.Cliente;
import model.entity.Telefone;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
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
	protected Telefone telefoneSelecionado;
	private List<Cliente> clientes;
	private ArrayList<Telefone> telefones;
	
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
		
		telefones = telefoneController.consultarTodos();
		cbTelefone = new JComboBox(telefones.toArray());
		cbTelefone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				telefoneSelecionado = (Telefone) cbTelefone.getSelectedItem();
				
				if(telefoneSelecionado != null) {
					if(telefoneSelecionado.isAtivo()) {
						//Mostrar o cliente atual da linha (pegar no backend) 
						//depois selecionar no cbCliente 
						btnLiberarTelefone.setEnabled(true);
						btnCriarLinha.setEnabled(false);
						
						Cliente dono = linhaTelefonicaController.obterDonoAtualDoTelefone(telefoneSelecionado.getId());
						cbCliente.getModel().setSelectedItem(dono);
					}else {
						btnLiberarTelefone.setEnabled(false);
						btnCriarLinha.setEnabled(true);
					}
				}
				
			}
		});
		cbTelefone.setBounds(20, 30, 370, 22);
		cbTelefone.setSelectedIndex(-1);
		contentPane.add(cbTelefone);
		
		clientes = clienteController.consultarTodos();
		cbCliente = new JComboBox(clientes.toArray());
		cbCliente.setBounds(20, 80, 370, 22);
		cbCliente.setSelectedIndex(-1);
		contentPane.add(cbCliente);
		
		btnCriarLinha = new JButton("Associar o telefone ao usuário");
		btnCriarLinha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cliente clienteSelecionado = (Cliente) cbCliente.getSelectedItem();
				boolean criou = linhaTelefonicaController
						.criarLinha(telefoneSelecionado, clienteSelecionado);
				
				if(criou) {
					atualizarCombos();
					JOptionPane.showMessageDialog(null, "Linha criada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Erro ao criar linha", "Atenção", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnCriarLinha.setEnabled(false);
		btnCriarLinha.setBounds(20, 115, 370, 23);
		contentPane.add(btnCriarLinha);
		
		btnLiberarTelefone = new JButton("Liberar telefone");
		btnLiberarTelefone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				boolean liberouLinha = 
						linhaTelefonicaController.liberarLinha(telefoneSelecionado.getId());
				
				if(liberouLinha) {
					JOptionPane.showMessageDialog(null, "Linha liberada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					atualizarCombos();
				}else {
					JOptionPane.showMessageDialog(null, "Erro ao liberar linha", "Atenção", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnLiberarTelefone.setEnabled(false);
		btnLiberarTelefone.setBounds(20, 145, 370, 23);
		contentPane.add(btnLiberarTelefone);
	}

	protected void atualizarCombos() {
		//Solução para forçar a atualização dos dados
		//FONTE: https://community.oracle.com/tech/developers/discussion/2130310/jcombobox-not-visually-updating-after-setselecteditem
		telefones = telefoneController.consultarTodos();
		cbTelefone.setModel(new DefaultComboBoxModel(telefones.toArray()));
		cbTelefone.repaint();
		
		clientes = clienteController.consultarTodos();
		cbCliente.setModel(new DefaultComboBoxModel(clientes.toArray()));
		cbCliente.repaint();
	}
}
