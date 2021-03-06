package presentacio.queries;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

public class VistaDescripcio {

	private JFrame frame;
	CtrlPresentacio ctrl;
	private JTextArea textArea;
	String path;
	private JTextField textField;

	public VistaDescripcio(CtrlPresentacio ctrl, String path) {
		this.ctrl = ctrl;
		this.path = path;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				ctrl.openQuery();
			}
		});
		frame.getContentPane().setLayout(new MigLayout("", "[grow][][][][][grow]", "[][][][grow]"));
		
		JLabel lblEscriuLaDescripci = new JLabel("Escriu la descripci\u00F3 i el nom del nou cam\u00ED: ");
		frame.getContentPane().add(lblEscriuLaDescripci, "cell 2 1,alignx left");
		
		JButton btnAcceptar = new JButton("Acceptar");
		btnAcceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String descr = textArea.getText();
				String nom = textField.getText(); 
				try{ctrl.getDomini().afegirCami(nom, path, descr);}
				catch(Exception E){
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("No s'ha pogut afegir el cami", E.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnAcceptar, "cell 5 1");
		
		textField = new JTextField();
		frame.getContentPane().add(textField, "cell 0 2,growx");
		textField.setColumns(10);
		textArea = new JTextArea();
		frame.getContentPane().add(textArea, "cell 0 3 6 1,grow");
		frame.setVisible(true);
	}

}
