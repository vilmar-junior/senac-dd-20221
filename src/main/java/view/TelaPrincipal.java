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

import view.aula10.TelaSobreAutor;

import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TelaPrincipal {

	private JFrame frmTelaPrincipal;
	private JMenuItem menuItemConsultarClientes;

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
		
		menuItemConsultarClientes = new JMenuItem("Consultar");
		menuItemConsultarClientes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		menuItemConsultarClientes.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/icones/icons8-lista-com-marcadores.png")));
		menuItemConsultarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final PainelListagemCliente painelListagemClientes = new PainelListagemCliente();
				
				//Registra um ouvinte para o clique de um botão do painel
				//Esse evento será tratado pela tela pai (TelaPrincipal)
				painelListagemClientes.getBtnEditarCliente().addActionListener(new ActionListener() {
					private PainelCadastroCliente painelEdicao;

					public void actionPerformed(ActionEvent e) {
						
						painelEdicao = new PainelCadastroCliente(painelListagemClientes.getClienteSelecionado());
						frmTelaPrincipal.setContentPane(painelEdicao);
						frmTelaPrincipal.revalidate();	
						
						painelEdicao.getBtnSalvar().addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								//Salva o cliente
								painelEdicao.salvar();
								
								//Simula um clique na opção de menu "Clientes > Consultar"
								menuItemConsultarClientes.doClick();
							}
						});
						
					}
				});
				
				frmTelaPrincipal.setContentPane(painelListagemClientes);
				frmTelaPrincipal.revalidate();	
			}
		});
		menuCliente.add(menuItemConsultarClientes);
		
		JMenu menuSobre = new JMenu("Sobre (teste de listener ao fechar janela)");
		menuSobre.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/icones/icons8-сharlie-сhaplin.png")));
		menuBar.add(menuSobre);
		
		JMenuItem menuItemSobre = new JMenuItem("Sobre (teste de listener disparado ao fechar janela)");
		menuItemSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaSobreAutor telaSobre = new TelaSobreAutor();
				telaSobre.setVisible(true);
				telaSobre.addWindowListener(new WindowListener() {
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					
					public void windowClosed(WindowEvent e) {
						//Método que foi disparado quando a janela foi fechada
						frmTelaPrincipal.setTitle("TELA SOBRE FOI FECHADA");
					}
					
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
					}
				});
			}
		});
		menuSobre.add(menuItemSobre);
	}

}
