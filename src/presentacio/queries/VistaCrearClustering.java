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
	private JTextField textCami;
	private JTextField textNum;
	private JButton btnSeleccionarCam;
	private JButton btnExecutar;
	private VistaQuery vQ;
	private boolean camiNou;
	
	/**
	 * Create the application.
	 */
	public VistaCrearClustering(CtrlPresentacio ctrl, VistaQuery vq) {
		this.ctrl = ctrl;
		this.vQ = vq;
		camiNou = true;
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
		
		textCami = new JTextField();
		frame.getContentPane().add(textCami, "flowx,cell 1 1,growx");
		textCami.setColumns(10);
		
		JLabel lblEntitatInicial = new JLabel("Nombre de clusters: ");
		frame.getContentPane().add(lblEntitatInicial, "cell 0 3,alignx left");
		
		textNum = new JTextField();
		frame.getContentPane().add(textNum, "cell 1 3,growx");
		textNum.setColumns(10);
		
		//SELECCIONAR CAMI
		btnSeleccionarCam = new JButton("Seleccionar cam\u00ED");
		btnSeleccionarCam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new VistaSeleccionarCami(ctrl, vQ.frame, vQ);
					textCami.setText(vQ.pathCami);
					textCami.setEditable(false);
					camiNou = false;
				} catch (Exception exc) {
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("No s'ha pogut obrir la finestra de seleccio", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(btnSeleccionarCam, "cell 1 1");
		
		//EXECUTAR
		btnExecutar = new JButton("Executar");
		btnExecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textNum.getText().length() < 1){
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("Has d'introduir un nombre valid", "", botons, JOptionPane.ERROR_MESSAGE);
				}
				else if (textCami.getText().length() < 1){
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("Has d'introduir un cami", "", botons, JOptionPane.ERROR_MESSAGE);
				}
				else{
					if (camiNou){
						vQ.setCami("",textCami.getText());
					}
					vQ.executarClustering(camiNou, Integer.parseInt(textNum.getText()));
				}
			}
		});
		frame.getContentPane().add(btnExecutar, "cell 1 7,alignx right,aligny bottom");
	}

}
