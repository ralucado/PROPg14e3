package presentacio.queries;

import java.awt.EventQueue;
import javax.swing.JFrame;
import presentacio.ctrl.*;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;


public class VistaCrearQuery {

	public JFrame frame;
	private CtrlPresentacio ctrl;
	private JTextField textField;
	private VistaQuery vQ;

	/**
	 * Create the application.
	 */
	public VistaCrearQuery(CtrlPresentacio ctrl, VistaQuery vq) {
		initialize();
		//marti pls
		this.ctrl = ctrl;
		vQ = vq;
		//facepalm
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 485, 313);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][][][][]"));
		
		JLabel lblCami = new JLabel("Cami: ");
		frame.getContentPane().add(lblCami, "cell 0 1,alignx left");
		
		textField = new JTextField();
		frame.getContentPane().add(textField, "flowx,cell 1 1,growx");
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Seleccionar Cam\u00ED");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//se li ha de passar la frame actual, no la frame del vista query
					new VistaSeleccionarCami(ctrl, frame, vQ);
					textField.setText(vQ.cami);
					textField.setEditable(false);
				} catch (Exception exc) {
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("No s'ha pogut obrir la finestra de seleccio", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(btnNewButton, "cell 1 1");
		
		JLabel lblEntitatInicial = new JLabel("Entitat inicial: ");
		frame.getContentPane().add(lblEntitatInicial, "cell 0 3,alignx left");
		
		JButton btnSeleccionarEntitatInicial = new JButton("Seleccionar entitat inicial");
		btnSeleccionarEntitatInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new VistaEntitatsDialog(...);
			}
		});
		frame.getContentPane().add(btnSeleccionarEntitatInicial, "cell 1 3");
		
		JButton btnExecutar = new JButton("Executar");
		btnExecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().length() < 1){
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("Has d'introduir un cam�", "", botons, JOptionPane.ERROR_MESSAGE);
				}
				else{
					vQ.executarNormal();
				}
			}
		});
		frame.getContentPane().add(btnExecutar, "cell 1 7,alignx right,aligny bottom");
	}

}
