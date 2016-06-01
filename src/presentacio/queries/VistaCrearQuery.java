package presentacio.queries;

import java.awt.EventQueue;
import javax.swing.JFrame;
import presentacio.ctrl.*;
import presentacio.graf.*;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;


public class VistaCrearQuery {

	public JFrame frame;
	private CtrlPresentacio ctrl;
	private JTextField textCami;
	private VistaQuery vQ;
	private JTextField textEntitat;
	private boolean camiNou;

/**
 * Create the application.
 * @param ctrl Control presentacio
 * @param vq VistaQuery
 */
	public VistaCrearQuery(CtrlPresentacio ctrl, VistaQuery vq) {
		camiNou = true;
		//marti pls
		this.ctrl = ctrl;
		vQ = vq;
		//facepalm
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 485, 313);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow 1000][grow]", "[][][][][][][][]"));
		
		JLabel lblCami = new JLabel("Cami: ");
		frame.getContentPane().add(lblCami, "cell 0 1,alignx left");
		
		textCami = new JTextField();
		textCami.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				camiNou = true;
			}
		});
		frame.getContentPane().add(textCami, "flowx,cell 2 1,growx");
		textCami.setColumns(10);
		
		//SELECCIONAR CAMI
		JButton btnNewButton = new JButton("Seleccionar Cam\u00ED");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//se li ha de passar la frame actual, no la frame del vista query
					new VistaSeleccionarCami(ctrl, frame, vQ);
					textCami.setText(vQ.pathCami);
					//textCami.setEditable(false);
					camiNou = false;
				} catch (Exception exc) {
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("No s'ha pogut obrir la finestra de seleccio", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(btnNewButton, "cell 2 1");
		
		JLabel lblEntitatInicial = new JLabel("Entitat inicial: ");
		frame.getContentPane().add(lblEntitatInicial, "cell 0 3,alignx trailing");
		
		//SELECCIONAR ENTITAT INICIAL
		JButton btnSeleccionarEntitatInicial = new JButton("Seleccionar entitat inicial");
		btnSeleccionarEntitatInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textCami.getText().length() < 1){
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("Primer has de seleccionar un cami valid", "", botons, JOptionPane.ERROR_MESSAGE);
				}
				else{
					ArrayList<String> A = new ArrayList<String>();
					String s = null;
					Boolean camiValid = true;
					switch(textCami.getText().charAt(0)){
					case 'P':
						s = "Paper"; break;
					case 'T':
						s = "Terme"; break;
					case 'A':
						s = "Autor"; break;
					case 'C':
						s = "Conferencia"; break;
					default:
						camiValid = false;
						String[] botons = {"D'acord"};
						(new VistaDialog()).setDialog("Primer has de seleccionar un cami valid", "", botons, JOptionPane.ERROR_MESSAGE);
					}
					if (camiValid){
						try{
						new VistaEntitatsDialog(ctrl,frame,s,A);
						if (A.size() > 0) textEntitat.setText(A.get(0));
						}
						catch (Exception exc){
							String[] botons = {"D'acord"};
							(new VistaDialog()).setDialog("Error", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		frame.getContentPane().add(btnSeleccionarEntitatInicial, "cell 2 3,alignx right");
		
		textEntitat = new JTextField();
		frame.getContentPane().add(textEntitat, "cell 1 3,growx");
		textEntitat.setColumns(10);
		textEntitat.setEditable(false);
		
		//EXECUTAR
		JButton btnExecutar = new JButton("Executar");
		btnExecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textCami.getText().length() < 1){
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("Has d'introduir un cami", "", botons, JOptionPane.ERROR_MESSAGE);
				}
				else if (textEntitat.getText().length() < 1){
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("Has de seleccionar entitat inicial", "", botons, JOptionPane.ERROR_MESSAGE);
				}
				else {
					if (camiNou){
						vQ.setCami("",textCami.getText());
					}
					try{
					vQ.executarNormal(camiNou, textEntitat.getText());
					}
					catch (Exception exc){
						String[] botons = {"D'acord"};
						(new VistaDialog()).setDialog("Error en executar la query", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
					}
					frame.dispose();
				}
			}
		});
		frame.getContentPane().add(btnExecutar, "cell 2 7,alignx right,aligny bottom");
	}

}
