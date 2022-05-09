package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

public class TelaPrincipal {

	private JFrame frmTelaPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frmTelaPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
					window.frmTelaPrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTelaPrincipal = new JFrame();
		frmTelaPrincipal.setTitle("Tela principal do sistema");
		frmTelaPrincipal.setBounds(100, 100, 450, 300);
		frmTelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmTelaPrincipal.setJMenuBar(menuBar);
		
		JMenu menuCliente = new JMenu("Clientes");
		menuCliente.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/icones/icons8-fila.png")));
		menuBar.add(menuCliente);
		
		JMenuItem menuItemCadastroCliente = new JMenuItem("Cadastrar");
		menuItemCadastroCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		menuItemCadastroCliente.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/icones/scrooge-McDuck-icon.png")));
		menuItemCadastroCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PainelCadastroCliente painelCliente = new PainelCadastroCliente(null);
				
				frmTelaPrincipal.setContentPane(painelCliente);
				frmTelaPrincipal.revalidate();	
			}
		});
		menuCliente.add(menuItemCadastroCliente);
		
		JMenuItem menuItemConsultarClientes = new JMenuItem("Consultar");
		menuItemConsultarClientes.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/icones/icons8-lista-com-marcadores.png")));
		menuItemConsultarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final PainelListagemCliente painelListagemClientes = new PainelListagemCliente();
				
				painelListagemClientes.getBtnEditarCliente().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						PainelCadastroCliente painelEdicao = 
								new PainelCadastroCliente(painelListagemClientes.getClienteSelecionado());
						frmTelaPrincipal.setContentPane(painelEdicao);
						frmTelaPrincipal.revalidate();	
					}
				});
				
				frmTelaPrincipal.setContentPane(painelListagemClientes);
				frmTelaPrincipal.revalidate();	
			}
		});
		menuCliente.add(menuItemConsultarClientes);
	}

}
