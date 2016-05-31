package presentacio.queries;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.VistaDialog;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import presentacio.ctrl.*;

public class VistaDescripcio {

	private JFrame frame;
	CtrlPresentacio ctrl;
	private JTextArea textArea;
	String nom, path;

	public VistaDescripcio(CtrlPresentacio ctrl, String nom, String path) {
		this.ctrl = ctrl;
		this.nom = nom;
		this.path = path;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][][][][][grow]", "[][][][grow]"));
		
		JLabel lblEscriuLaDescripci = new JLabel("Escriu la descripci\u00F3 del nou cam\u00ED: ");
		frame.getContentPane().add(lblEscriuLaDescripci, "cell 2 1,alignx left");
		
		JButton btnAcceptar = new JButton("Acceptar");
		btnAcceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String descr = textArea.getText();
				try{ctrl.getDomini().afegirCami(nom, path, descr);}
				catch(Exception E){
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("", "No s'ha pogut afegir el cami", botons, JOptionPane.ERROR_MESSAGE);
					System.out.println(E.getMessage());
				}
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnAcceptar, "cell 5 1");
		textArea = new JTextArea();
		frame.getContentPane().add(textArea, "cell 0 3 6 1,grow");
	}

}
