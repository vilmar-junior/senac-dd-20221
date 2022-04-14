package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import controller.TelefoneController;
import model.entity.Telefone;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class TelaListagemTelefones {

	private JFrame frmTelefonesCadastrados;
	private JTable tabelaTelefones;
	private TelefoneController telefoneController = new TelefoneController();
	private List<Telefone> telefones = new ArrayList<Telefone>(); 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaListagemTelefones window = new TelaListagemTelefones();
					window.frmTelefonesCadastrados.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaListagemTelefones() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTelefonesCadastrados = new JFrame();
		frmTelefonesCadastrados.setTitle("Telefones cadastrados");
		frmTelefonesCadastrados.setBounds(100, 100, 450, 300);
		frmTelefonesCadastrados.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabelaTelefones = new JTable();
		tabelaTelefones.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"DD", "Número", "Tipo", "Ativo?"
			}
		));
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				telefones = telefoneController.consultarTodos();
				atualizarTabela();
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmTelefonesCadastrados.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabelaTelefones, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
					.addGap(8))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(178)
					.addComponent(btnAtualizar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(181))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnAtualizar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabelaTelefones, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
					.addGap(2))
		);
		frmTelefonesCadastrados.getContentPane().setLayout(groupLayout);
	}

	protected void atualizarTabela() {
		tabelaTelefones.setModel(new DefaultTableModel(new String[][] { 
			{ "DD", "Número", "Tipo", "Ativo" }, },
			new String[] { "DD", "Número", "Tipo", "Ativo" }));
		
		DefaultTableModel modelo = (DefaultTableModel) tabelaTelefones.getModel();
		
		for(Telefone t: telefones) {
			String[] novaLinha = new String[] {
					t.getDdd(), 
					t.getNumero(),
					t.getTipo() == Telefone.TIPO_FIXO ? "Fixo" : "Móvel",
					t.isAtivo() ? "Sim" : "Não"
			};
			
			modelo.addRow(novaLinha);
		}
	}
}
