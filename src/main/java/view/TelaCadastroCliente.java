package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ClienteController;
import controller.EnderecoController;
import model.entity.Cliente;
import model.entity.Endereco;
import model.exception.CpfInvalidoException;
import model.exception.ErroAoSalvarClienteException;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.MaskFormatter;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.border.BevelBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import net.miginfocom.swing.MigLayout;

public class TelaCadastroCliente extends JFrame {

	private ClienteController clienteController = new ClienteController();
	private EnderecoController enderecoController = new EnderecoController();
	private Cliente cliente; 
	
	private JLabel lblNome;
	private JLabel lblCpf;
	private JLabel lblEndereco;
	private JTextField txtNome;
	private JFormattedTextField txtCpf;
	private JComboBox cbEndereco;
	private JButton btnSalvar;
	private JButton btnLimpar;
	
	public TelaCadastroCliente() {
		this.setTitle("Cadastro de cliente");
		this.setBounds(300, 300, 530, 150);
		
		lblCpf = new JLabel("CPF:");

		ArrayList<Endereco> enderecos = enderecoController.pesquisarTodos();
		getContentPane().setLayout(new MigLayout("", "[100px,fill][fill][371.00px,fill][371.00px,fill]", "[20px][][20px][]"));
		
		//Referência: https://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html#constructors
		MaskFormatter formatador;
		try {
			formatador = new MaskFormatter("###.###.###-##");
			txtCpf = new JFormattedTextField(formatador);
		} catch (ParseException e1) {
			//TODO tratar
		}
		txtCpf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				try {
					cliente = clienteController.consultarPorCPF(txtCpf.getText());
					preencherCliente();
					lblCpf.setForeground(Color.BLACK);
				} catch (CpfInvalidoException e) {
					lblCpf.setForeground(Color.RED);
					cliente = null;
					preencherCliente();
				}
			}
		});
		this.getContentPane().add(txtCpf, "cell 2 0 2 1,growx,aligny center");
		
		//Cria os componentes da tela
		lblNome = new JLabel("Nome:");
		this.getContentPane().add(lblNome, "cell 0 1,grow");
		this.getContentPane().add(lblCpf, "cell 0 0,grow");
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_ENTER) {
					salvar();
				}
			}
		});
		this.getContentPane().add(txtNome, "cell 2 1 2 1,growx,aligny top");
		
		lblEndereco = new JLabel("Endereço:");
		this.getContentPane().add(lblEndereco, "cell 0 2,grow");
		
		cbEndereco = new JComboBox(enderecos.toArray());
		cbEndereco.setSelectedIndex(-1);
		this.getContentPane().add(cbEndereco, "cell 2 2 2 1,grow");
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(Color.lightGray);
		btnSalvar.setBorderPainted(false);
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		this.getContentPane().add(btnSalvar, "cell 2 3,grow");
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBorderPainted(false);
		btnLimpar.setBackground(Color.LIGHT_GRAY);
		btnLimpar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnLimpar.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnLimpar.setForeground(Color.blue);
			}
			@Override
			public void mouseClicked(MouseEvent evento) {
				if(evento.getButton() == MouseEvent.BUTTON1) {
					btnLimpar.setForeground(Color.GREEN);
				}
				
				//Botão direito
				if(evento.getButton() == MouseEvent.BUTTON3) {
					btnLimpar.setForeground(Color.YELLOW);
				}
			}
		});
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparCampos();
			}
		});
		getContentPane().add(btnLimpar, "cell 3 3,alignx left,growy");
	}

	protected void preencherCliente() {
		if(cliente != null) {
			int VaR_errada;
			
			this.txtNome.setText(cliente.getNome());
			this.txtCpf.setText(cliente.getCpf());
			this.cbEndereco.getModel().setSelectedItem(cliente.getEndereco());
			this.setTitle("EDIÇÃO de cliente");
		}else {
			this.setTitle("CADASTRO de cliente");
			this.limparCampos();
		}
	}

	protected void limparCampos() {
		this.txtNome.setText("");
		this.txtCpf.setText("");
		this.cbEndereco.setSelectedItem(null);
	}
	
	protected void salvar() {
		cliente.setNome(txtNome.getText());
		cliente.setCpf(txtCpf.getText());
		cliente.setEndereco((Endereco)cbEndereco.getSelectedItem());
		
		String mensagem;
		try {
			mensagem = clienteController.salvar(cliente);
			JOptionPane.showMessageDialog(null, mensagem,"Mensagem", JOptionPane.INFORMATION_MESSAGE);
			limparCampos();
		} catch (ErroAoSalvarClienteException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Atenção", JOptionPane.WARNING_MESSAGE);
		}
	}
}
