package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.ClienteController;
import model.entity.Cliente;
import model.exception.ClienteComLinhaTelefonicaException;

public class PainelListagemCliente extends JPanel{

	private JTable tblClientes;
	private JButton btnEditarCliente;
	private JButton btnExcluirCliente;
	
	private ClienteController controller = new ClienteController();
	private List<Cliente> clientes = new ArrayList<Cliente>();
	private Cliente clienteSelecionado;
	private JButton btnAtualizar;
	public PainelListagemCliente() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setLayout(null);

		tblClientes = new JTable();
		JScrollPane painelTabela = new JScrollPane(tblClientes);
		painelTabela.setSize(1000, 400);
		painelTabela.setLocation(10, 10);
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int indiceSelecionado = tblClientes.getSelectedRow();
				
				if(indiceSelecionado >= 0) {
					clienteSelecionado = clientes.get(indiceSelecionado);
				}else {
					clienteSelecionado = null;
				}
				
				verificarBloqueioBotoes();
			}
		});
		tblClientes.setBounds(10, 10, 1000, 400);
		this.add(painelTabela);
		
		JButton btnNovoCliente = new JButton("Novo Cliente");
		btnNovoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO trocar: a TelaPrincipal é que deve trocar para um painel de cadastro de clientes
				TelaCadastroCliente telaCadastro = new TelaCadastroCliente(null);
				telaCadastro.setVisible(true);
			}
		});
		btnNovoCliente.setBounds(160, 420, 120, 30);
		this.add(btnNovoCliente);
		
		btnEditarCliente = new JButton("Editar Cliente");
		btnEditarCliente.setEnabled(false);
		btnEditarCliente.setBounds(310, 420, 120, 30);
		this.add(btnEditarCliente);
		
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
		btnExcluirCliente.setBounds(460, 420, 120, 30);
		this.add(btnExcluirCliente);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarTabela();
			}
		});
		btnAtualizar.setBounds(10, 420, 120, 30);
		this.add(btnAtualizar);
		
		this.atualizarTabela();
	}
	
	protected void verificarBloqueioBotoes() {
		this.btnEditarCliente.setEnabled(clienteSelecionado != null);
		this.btnExcluirCliente.setEnabled(clienteSelecionado != null);
	}

	protected void atualizarTabela() {
		clientes = controller.consultarTodos();
		tblClientes.setModel(new DefaultTableModel(new String[] { "Id", "Nome", "CPF", 
				"Nº de linhas telefônicas" }, 0));
		
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

	public JButton getBtnEditarCliente() {
		return btnEditarCliente;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}
}
