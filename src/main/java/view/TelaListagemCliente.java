package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controller.ClienteController;
import model.entity.Cliente;
import model.entity.Telefone;
import model.exception.ClienteComLinhaTelefonicaException;
import model.seletor.TelefoneSeletor;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaListagemCliente {

	private static final int TAMANHO_PAGINA = 5;
	private JFrame frmListagemDeClientes;
	private JTable tblClientes;
	private JButton btnEditarCliente;
	private JButton btnExcluirCliente;
	
	private ClienteController controller = new ClienteController();
	private List<Cliente> clientes = new ArrayList<Cliente>();
	private Cliente clienteSelecionado;
	private JButton btnAtualizar;
	private JTextField txtNome;
	private JFormattedTextField txtCpf;
	protected TelefoneSeletor seletor = new TelefoneSeletor();
	private JButton btnAvancarPagina;
	private JButton btnVoltarPagina;
	private JLabel lblPaginaAtual;
	private JLabel lblNewLabel;
	private JLabel lblTotalPaginas;
	private JLabel lblNewLabel_1;
	private JLabel lblTotalClientes;
	private int totalClientes;
	private int quantidadePaginas;

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
		frmListagemDeClientes.setBounds(100, 100, 600, 470);
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
		tblClientes.setBounds(10, 81, 570, 110);
		frmListagemDeClientes.getContentPane().add(tblClientes);
		
		JButton btnNovoCliente = new JButton("Novo Cliente");
		btnNovoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroCliente telaCadastro = new TelaCadastroCliente(null);
				telaCadastro.setVisible(true);
			}
		});
		btnNovoCliente.setBounds(160, 390, 120, 30);
		frmListagemDeClientes.getContentPane().add(btnNovoCliente);
		
		btnEditarCliente = new JButton("Editar Cliente");
		btnEditarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroCliente telaEdicao = new TelaCadastroCliente(clienteSelecionado);
				telaEdicao.setVisible(true);
			}
		});
		btnEditarCliente.setEnabled(false);
		btnEditarCliente.setBounds(310, 390, 120, 30);
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
		btnExcluirCliente.setBounds(460, 390, 120, 30);
		frmListagemDeClientes.getContentPane().add(btnExcluirCliente);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarTabela();
			}
		});
		btnAtualizar.setBounds(10, 390, 120, 30);
		frmListagemDeClientes.getContentPane().add(btnAtualizar);
		
		try {
			MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
			txtCpf = new JFormattedTextField(mascaraCpf);
			txtCpf.setBounds(38, 32, 103, 20);
			frmListagemDeClientes.getContentPane().add(txtCpf);
		} catch (ParseException e1) {
			
		}
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(10, 35, 46, 14);
		frmListagemDeClientes.getContentPane().add(lblCpf);
		
		txtNome = new JTextField();
		txtNome.setBounds(200, 32, 275, 20);
		frmListagemDeClientes.getContentPane().add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(160, 35, 46, 14);
		frmListagemDeClientes.getContentPane().add(lblNome);
		
		JButton btnPesquisar = new JButton("Buscar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				seletor  = new TelefoneSeletor();
				seletor.setNomeDonoTelefone(txtNome.getText());
				seletor.setCpfDonoTelefone(txtCpf.getText());
				
				atualizarTabela();
			}
		});
		btnPesquisar.setBounds(485, 31, 89, 23);
		frmListagemDeClientes.getContentPane().add(btnPesquisar);
		
		btnAvancarPagina = new JButton(" Próxima Página >");
		btnAvancarPagina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				seletor.setPagina(seletor.getPagina() + 1);
				lblPaginaAtual.setText(seletor.getPagina() + "");
				atualizarTabela();
				
				if(seletor.getPagina() > 1) {
					btnVoltarPagina.setEnabled(true);
				}
				
				if(seletor.getPagina() == quantidadePaginas) {
					btnAvancarPagina.setEnabled(false);
				}else {
					btnAvancarPagina.setEnabled(true);
				}
			}
		});
		btnAvancarPagina.setBounds(355, 239, 153, 23);
		frmListagemDeClientes.getContentPane().add(btnAvancarPagina);
		
		btnVoltarPagina = new JButton("< Página Anterior");
		btnVoltarPagina.setEnabled(false);
		btnVoltarPagina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(seletor.getPagina() > 1) {
					seletor.setPagina(seletor.getPagina() - 1);
					lblPaginaAtual.setText(seletor.getPagina() + "");
					atualizarTabela();
				}
				
				if(seletor.getPagina() == 1) {
					btnVoltarPagina.setEnabled(false);
				}
				
				if(seletor.getPagina() == quantidadePaginas) {
					btnAvancarPagina.setEnabled(false);
				}else {
					btnAvancarPagina.setEnabled(true);
				}
			}
		});
		btnVoltarPagina.setBounds(75, 239, 146, 23);
		frmListagemDeClientes.getContentPane().add(btnVoltarPagina);
		
		lblPaginaAtual = new JLabel("");
		lblPaginaAtual.setBounds(256, 243, 24, 14);
		frmListagemDeClientes.getContentPane().add(lblPaginaAtual);
		
		lblNewLabel = new JLabel("/");
		lblNewLabel.setBounds(281, 243, 24, 14);
		frmListagemDeClientes.getContentPane().add(lblNewLabel);
		
		lblTotalPaginas = new JLabel("");
		lblTotalPaginas.setBounds(299, 243, 46, 14);
		frmListagemDeClientes.getContentPane().add(lblTotalPaginas);
		
		seletor.setPagina(1);
		seletor.setLimite(TAMANHO_PAGINA);
		lblPaginaAtual.setText(seletor.getPagina() + "");
		
		lblNewLabel_1 = new JLabel("Total de clientes:");
		lblNewLabel_1.setBounds(84, 285, 110, 14);
		frmListagemDeClientes.getContentPane().add(lblNewLabel_1);
		
		lblTotalClientes = new JLabel("");
		lblTotalClientes.setBounds(192, 285, 46, 14);
		frmListagemDeClientes.getContentPane().add(lblTotalClientes);
		
		this.atualizarTabela();
	}
	
	protected void verificarBloqueioBotoes() {
		this.btnEditarCliente.setEnabled(clienteSelecionado != null);
		this.btnExcluirCliente.setEnabled(clienteSelecionado != null);
	}

	protected void atualizarTabela() {
		clientes = controller.consultarComSeletor(seletor);
		totalClientes = controller.contarClientes();
		
		lblTotalClientes.setText(totalClientes + "");
		
		calcularQuantidadePaginas();
		
		
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

	private void calcularQuantidadePaginas() {
		quantidadePaginas = totalClientes / TAMANHO_PAGINA;
		
		if(totalClientes % TAMANHO_PAGINA > 0) {
			quantidadePaginas++;
		}
		
		lblTotalPaginas.setText(quantidadePaginas + "");
		
	}
}
