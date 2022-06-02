package view.exemplos;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

public class TelaComDatas {

	private JFrame frame;
	private JLabel lblDataSelecionada;
	private JLabel lblHoraSelecionada;
	private JLabel lblDataHoraSelecionada;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaComDatas window = new TelaComDatas();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaComDatas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(10, 64, 40, 20);
		frame.getContentPane().add(lblData);

		// Configurações da parte de DATAS do componente (OPCIONAL)
		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);
		
		TimePickerSettings timeSettings = new TimePickerSettings();
		timeSettings.setAllowKeyboardEditing(false);
		
		//Configuração para mostrar horas de 08:00 a 18:00, com intervalos de 15 minutos
		LocalTime horaInicial = LocalTime.of(8, 0); //08:00
		LocalTime horaFinal = LocalTime.of(18, 0);  //18:00
		timeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, horaInicial, horaFinal);
		
		
		final DateTimePicker dataTeste = new DateTimePicker(dateSettings, timeSettings);
		
		dataTeste.setBounds(80, 60, 540, 45);
		frame.getContentPane().add(dataTeste);
		
		

		JButton btnPegarData = new JButton("Criar data");
		btnPegarData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Atributos próprios do componente datePicker (date e time)
				LocalDate dataSelecionada = dataTeste.getDatePicker().getDate();
				LocalTime horaSelecionada = dataTeste.getTimePicker().getTime();
				
				//TODO validar adequadamente
				if(dataSelecionada != null && horaSelecionada != null) {
					LocalDateTime dataComHora = LocalDateTime.of(dataSelecionada, horaSelecionada);
					lblDataHoraSelecionada.setText(dataComHora.toString());
				}
				
				if(dataSelecionada != null) {
					lblDataSelecionada.setText(dataSelecionada.toString());
				}
				
				if(horaSelecionada != null) {
					lblHoraSelecionada.setText(horaSelecionada.toString());
				}
			}
		});
		btnPegarData.setBounds(238, 149, 181, 23);
		frame.getContentPane().add(btnPegarData);
		
		JLabel lbl1 = new JLabel("Data selecionada:");
		lbl1.setBounds(80, 211, 168, 14);
		frame.getContentPane().add(lbl1);
		
		JLabel lbl3 = new JLabel("Hora selecionada:");
		lbl3.setBounds(80, 250, 168, 14);
		frame.getContentPane().add(lbl3);
		
		JLabel lbl1_1_1 = new JLabel("Data e Hora selecionada:");
		lbl1_1_1.setBounds(80, 289, 168, 14);
		frame.getContentPane().add(lbl1_1_1);
		
		lblDataSelecionada = new JLabel("");
		lblDataSelecionada.setBounds(258, 211, 289, 14);
		frame.getContentPane().add(lblDataSelecionada);
		
		lblHoraSelecionada = new JLabel("");
		lblHoraSelecionada.setBounds(258, 250, 289, 14);
		frame.getContentPane().add(lblHoraSelecionada);
		
		lblDataHoraSelecionada = new JLabel("");
		lblDataHoraSelecionada.setBounds(258, 289, 289, 14);
		frame.getContentPane().add(lblDataHoraSelecionada);
	}
}
