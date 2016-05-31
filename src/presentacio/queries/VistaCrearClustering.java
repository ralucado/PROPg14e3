package presentacio.queries;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;
public class VistaCrearClustering {

	public JFrame frame;
	private CtrlPresentacio ctrl;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnSeleccionarCam;
	private JButton btnExecutar;
	private VistaQuery vQ;
	
	/**
	 * Create the application.
	 */
	public VistaCrearClustering(CtrlPresentacio ctrl, VistaQuery vq) {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][::1000px,grow]", "[][][][][][][][]"));
		
		JLabel lblCam = new JLabel("Cam\u00ED: ");
		frame.getContentPane().add(lblCam, "cell 0 1,alignx left");
		
		textField = new JTextField();
		frame.getContentPane().add(textField, "flowx,cell 1 1,growx");
		textField.setColumns(10);
		
		JLabel lblEntitatInicial = new JLabel("Nombre de clusters: ");
		frame.getContentPane().add(lblEntitatInicial, "cell 0 3,alignx left");
		
		textField_1 = new JTextField();
		frame.getContentPane().add(textField_1, "cell 1 3,growx");
		textField_1.setColumns(10);
		
		btnSeleccionarCam = new JButton("Seleccionar cam\u00ED");
		btnSeleccionarCam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new VistaSeleccionarCami(ctrl, vQ.frame, vQ);
					textField.setText(vQ.cami);
					textField.setEditable(false);
				} catch (Exception exc) {
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("No s'ha pogut obrir la finestra de seleccio", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(btnSeleccionarCam, "cell 1 1");
		
		btnExecutar = new JButton("Executar");
		btnExecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_1.getText().length() < 1){
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("Has d'introduir un nombre v�lid", "", botons, JOptionPane.ERROR_MESSAGE);
				}
				else if (textField.getText().length() < 1){
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("Has d'introduir un cam�", "", botons, JOptionPane.ERROR_MESSAGE);
				}
				else{
					vQ.executarClustering();
				}
			}
		});
		frame.getContentPane().add(btnExecutar, "cell 1 7,alignx right,aligny bottom");
	}

}
