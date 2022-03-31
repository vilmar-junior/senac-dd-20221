package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.EnderecoController;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class TelaCadastroEndereco extends JFrame {

	private JPanel contentPane;
	private JTextField txtCep;
	private JTextField txtRua;
	private JTextField txtNumero;
	private JTextField txtCidade;
	private JComboBox cbEstados;
	
	private EnderecoController controller = new EnderecoController(); 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroEndereco frame = new TelaCadastroEndereco();
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
	public TelaCadastroEndereco() {
		setTitle("Cadastro de endereço");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 281);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCep = new JLabel("Cep:");
		lblCep.setBounds(20, 20, 100, 14);
		contentPane.add(lblCep);
		
		txtCep = new JTextField();
		txtCep.setBounds(70, 17, 200, 20);
		contentPane.add(txtCep);
		txtCep.setColumns(10);
		
		JLabel lblRua = new JLabel("Rua:");
		lblRua.setBounds(20, 56, 46, 14);
		contentPane.add(lblRua);
		
		txtRua = new JTextField();
		txtRua.setBounds(70, 53, 200, 20);
		contentPane.add(txtRua);
		txtRua.setColumns(10);
		
		JLabel lblNumero = new JLabel("Número:");
		lblNumero.setBounds(20, 91, 46, 14);
		contentPane.add(lblNumero);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(70, 88, 200, 20);
		contentPane.add(txtNumero);
		txtNumero.setColumns(10);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(20, 131, 46, 14);
		contentPane.add(lblCidade);
		
		txtCidade = new JTextField();
		txtCidade.setBounds(70, 128, 200, 20);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(20, 167, 46, 14);
		contentPane.add(lblEstado);
		
		cbEstados = new JComboBox(controller.pesquisarEstados().toArray());
		cbEstados.setBounds(70, 163, 200, 22);
		cbEstados.setSelectedIndex(-1);
		contentPane.add(cbEstados);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(20, 208, 250, 23);
		contentPane.add(btnSalvar);
	}
}
