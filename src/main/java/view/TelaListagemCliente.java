package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.ClienteController;
import model.entity.Cliente;
import model.entity.Telefone;
import model.exception.ClienteComLinhaTelefonicaException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaListagemCliente {

	private JFrame frmListagemDeClientes;
	private JTable tblClientes;
	private JButton btnEditarCliente;
	private JButton btnExcluirCliente;
	
	private ClienteController controller = new ClienteController();
	private List<Cliente> clientes = new ArrayList<Cliente>();
	private Cliente clienteSelecionado;
	private JButton btnAtualizar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaListagemCliente window = new TelaListagemCliente();
					window.frmListagemDeClientes.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaListagemCliente() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmListagemDeClientes = new JFrame();
		frmListagemDeClientes.setTitle("Listagem de clientes");
		frmListagemDeClientes.setBounds(100, 100, 600, 400);
		frmListagemDeClientes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmListagemDeClientes.getContentPane().setLayout(null);
		
		tblClientes = new JTable();
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int indiceSelecionado = tblClientes.getSelectedRow();
				
				if(indiceSelecionado > 0) {
					clienteSelecionado = clientes.get(indiceSelecionado - 1);
				}else {
					clienteSelecionado = null;
				}
				
				verificarBloqueioBotoes();
			}
		});
		tblClientes.setBounds(10, 11, 570, 300);
		frmListagemDeClientes.getContentPane().add(tblClientes);
		
		JButton btnNovoCliente = new JButton("Novo Cliente");
		btnNovoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroCliente telaCadastro = new TelaCadastroCliente(null);
				telaCadastro.setVisible(true);
			}
		});
		btnNovoCliente.setBounds(160, 320, 120, 30);
		frmListagemDeClientes.getContentPane().add(btnNovoCliente);
		
		btnEditarCliente = new JButton("Editar Cliente");
		btnEditarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroCliente telaEdicao = new TelaCadastroCliente(clienteSelecionado);
				telaEdicao.setVisible(true);
			}
		});
		btnEditarCliente.setEnabled(false);
		btnEditarCliente.setBounds(310, 320, 120, 30);
		frmListagemDeClientes.getContentPane().add(btnEditarCliente);
		
		btnExcluirCliente = new JButton("Excluir Cliente");
		btnExcluirCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String mensagem;
				try {
					mensagem = controller.excluir(clienteSelecionado);
					
					JOptionPane.showMessageDialog(null, mensagem, "Sucesso", 
							JOptionPane.INFORMATION_MESSAGE);
					
					atualizarTabela();
				} catch (ClienteComLinhaTelefonicaException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnExcluirCliente.setEnabled(false);
		btnExcluirCliente.setBounds(460, 320, 120, 30);
		frmListagemDeClientes.getContentPane().add(btnExcluirCliente);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarTabela();
			}
		});
		btnAtualizar.setBounds(10, 320, 120, 30);
		frmListagemDeClientes.getContentPane().add(btnAtualizar);
		
		this.atualizarTabela();
	}
	
	protected void verificarBloqueioBotoes() {
		this.btnEditarCliente.setEnabled(clienteSelecionado != null);
		this.btnExcluirCliente.setEnabled(clienteSelecionado != null);
	}

	protected void atualizarTabela() {
		clientes = controller.consultarTodos();
		tblClientes.setModel(new DefaultTableModel(new String[][] { 
			{ "Id", "Nome", "CPF", "Nº de linhas telefônicas" }, },
			new String[] { "Id", "Nome", "CPF", "Nº de linhas telefônicas" }));
		
		DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
		
		for(Cliente cliente: clientes) {
			String[] novaLinha = new String[] {
					cliente.getId() + "", 
					cliente.getNome(),
					cliente.getCpf(),
					cliente.getLinhas().size() + ""
			};
			
			modelo.addRow(novaLinha);
		}
	}
}
