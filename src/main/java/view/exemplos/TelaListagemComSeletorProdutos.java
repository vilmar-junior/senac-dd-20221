package view.exemplos;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.github.lgooddatepicker.components.DatePicker;

import controller.produto.ProdutoController;
import model.entity.produto.Produto;
import model.seletor.produtos.ProdutoSeletor;

public class TelaListagemComSeletorProdutos extends JFrame {

	private static final String COR_AZUL = "Azul";
	private static final String COR_AMARELO = "Amarelo";
	private static final String COR_PRETO = "Preto";
	private static final String COR_VERDE = "Verde";
	private static final String COR_VERMELHO = "Vermelho";
	private static final int TAMANHO_PAGINA = 0;

	private JPanel contentPane;
	private JTable tblProdutos;
	private JButton btnGerarXls;
	private JComboBox cbCor;
	private DatePicker dtInicioCadastro;
	private DatePicker dtFimCadastro;
	private int paginaAtual = 1;
	private List<Produto> produtosConsultados;
	private JTextField txtNome;
	private JTextField txtPeso;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaListagemComSeletorProdutos frame = new TelaListagemComSeletorProdutos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param cbCor
	 */
	public TelaListagemComSeletorProdutos() {
		setTitle("Consulta de Produtos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnConsultar = new JButton("Consultar");

		btnConsultar.setBounds(90, 210, 150, 40);
		contentPane.add(btnConsultar);

		String[] cores = { "---Selecione---", TelaListagemComSeletorProdutos.COR_AZUL,
				TelaListagemComSeletorProdutos.COR_AMARELO, TelaListagemComSeletorProdutos.COR_PRETO,
				TelaListagemComSeletorProdutos.COR_VERDE, TelaListagemComSeletorProdutos.COR_VERMELHO };

		JLabel lblFiltroNome = new JLabel("Nome:");
		lblFiltroNome.setBounds(10, 40, 50, 15);
		contentPane.add(lblFiltroNome);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 205, 462, 14);
		contentPane.add(separator);

		JLabel lblFiltrosDeConsulta = new JLabel("Filtros de consulta:");
		lblFiltrosDeConsulta.setHorizontalAlignment(SwingConstants.CENTER);
		lblFiltrosDeConsulta.setBounds(10, 11, 448, 14);
		contentPane.add(lblFiltrosDeConsulta);

		tblProdutos = new JTable();
		this.limparTabela();
		tblProdutos.setBounds(10, 259, 462, 174);
		contentPane.add(tblProdutos);

		JLabel lblFiltroCor = new JLabel("Cor:");
		lblFiltroCor.setBounds(255, 40, 46, 14);
		contentPane.add(lblFiltroCor);

		cbCor = new JComboBox(cores);
		cbCor.setBounds(300, 37, 160, 30);

		contentPane.add(cbCor);

		btnGerarXls = new JButton("Gerar XLS");
		btnGerarXls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser janelaArquivos = new JFileChooser();

				int opcaoSelecionada = janelaArquivos.showSaveDialog(null);

				if (opcaoSelecionada == JFileChooser.APPROVE_OPTION) {
					String caminho = janelaArquivos.getSelectedFile().getAbsolutePath();

					ProdutoController controller = new ProdutoController();
					String mensagem = controller.gerarPlanilha(produtosConsultados, caminho);

					JOptionPane.showMessageDialog(null, mensagem);
				}
			}
		});
		btnGerarXls.setBounds(240, 210, 150, 40);
		contentPane.add(btnGerarXls);
		btnGerarXls.setEnabled(false);

		JLabel lblPeriodoCadastro = new JLabel("Per??odo de cadastro");
		lblPeriodoCadastro.setBounds(10, 110, 170, 20);
		contentPane.add(lblPeriodoCadastro);

		dtInicioCadastro = new DatePicker();
		dtInicioCadastro.setBounds(80, 130, 378, 30);
		contentPane.add(dtInicioCadastro);

		JLabel lblDataInicioCadastro = new JLabel("De:");
		lblDataInicioCadastro.setBounds(10, 140, 61, 16);
		contentPane.add(lblDataInicioCadastro);

		JLabel lblDataFimCadastro = new JLabel("At??:");
		lblDataFimCadastro.setBounds(10, 170, 61, 16);
		contentPane.add(lblDataFimCadastro);

		dtFimCadastro = new DatePicker();
		dtFimCadastro.setBounds(80, 170, 378, 30);
		contentPane.add(dtFimCadastro);

		txtNome = new JTextField();
		txtNome.setBounds(80, 35, 160, 30);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblPeso = new JLabel("Peso:");
		lblPeso.setBounds(10, 70, 61, 16);
		contentPane.add(lblPeso);

		txtPeso = new JTextField();
		txtPeso.setBounds(80, 67, 160, 30);
		contentPane.add(txtPeso);
		txtPeso.setColumns(10);

		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultarProdutos();
			}
		});
	}

	protected void consultarProdutos() {
		ProdutoController controlador = new ProdutoController();
		ProdutoSeletor seletor = new ProdutoSeletor();

		seletor.setPagina(paginaAtual);
		seletor.setLimite(TAMANHO_PAGINA);

		// Preenche os campos de filtro da tela no seletor
		if (cbCor.getSelectedIndex() > 0) {
			seletor.setCorProduto(cbCor.getSelectedItem().toString());
		}

		seletor.setNomeProduto(txtNome.getText());

		if (txtPeso.getText() != null && !txtPeso.getText().isEmpty()) {
			seletor.setPesoProduto(Double.parseDouble(txtPeso.getText()));
		} else {
			seletor.setPesoProduto(-1.0);
		}

		seletor.setDataInicioCadastro(dtInicioCadastro.getDate());
		seletor.setDataFimCadastro(dtFimCadastro.getDate());

		// AQUI ?? feita a consulta dos produtos e atualiza????o na tabela
		List<Produto> produtos = controlador.listarProdutos(seletor);
		atualizarTabelaProdutos(produtos);

	}

	protected void atualizarTabelaProdutos(List<Produto> produtos) {
		// atualiza o atributo produtosConsultados
		produtosConsultados = produtos;
		
		btnGerarXls.setEnabled(!produtosConsultados.isEmpty());

		this.limparTabela();

		DefaultTableModel modelo = (DefaultTableModel) tblProdutos.getModel();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for (Produto produto : produtos) {
			// Crio uma nova linha na tabela
			// Preencher a linha com os atributos do produto
			// na ORDEM do cabe??alho da tabela
			String dataFormatada = produto.getDataCadastro().format(formatter);

			String[] novaLinha = new String[] { produto.getId() + "", produto.getNome(), produto.getFabricante(),
					produto.getPeso() + "", dataFormatada };
			modelo.addRow(novaLinha);
		}

	}

	private void limparTabela() {
		tblProdutos.setModel(new DefaultTableModel(new String[][] { { "#", "Nome", "Marca", "Peso", "Dt. Cadastro" }, },
				new String[] { "#", "Nome", "Marca", "Peso", "Dt. Cadastro" }));
	}
}