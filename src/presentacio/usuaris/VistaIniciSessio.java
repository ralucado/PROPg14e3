package presentacio.usuaris;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

public class VistaIniciSessio {
	
	private CtrlPresentacio ctrl;
	
	private JFrame frame;
	private JTextField usuariField;
	private JPasswordField contrasenyaField;
	private JButton btnLogIn;

	public VistaIniciSessio(CtrlPresentacio ctrl) {
		this.ctrl = ctrl;
		inicialitzarComponents();
		this.frame.setVisible(true);
	}
	
	public void fesVisible() {
		frame.setVisible(true);
	}
	
	public void activa() {
		frame.setEnabled(true);
	}
	
	public void desactiva() {
		frame.setEnabled(false);
	}
	
	public void reset() {
		usuariField.setText("");
		contrasenyaField.setText("");
		frame.getRootPane().setDefaultButton(btnLogIn);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void inicialitzarComponents() {
		// Frame
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(220, 210);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Títol ("Inici de sessió")
		JLabel lblTitol = new JLabel("Inici de sessió");
		lblTitol.setForeground(Color.BLACK);
		lblTitol.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblTitol.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitol.setBounds(20, 6, 180, 25);
		frame.getContentPane().add(lblTitol);
		
		// Label: nom d'usuari
		JLabel lblUsuari = new JLabel("Nom d'usuari:");
		lblUsuari.setLabelFor(usuariField);
		lblUsuari.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblUsuari.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuari.setBounds(40, 35, 140, 20);
		frame.getContentPane().add(lblUsuari);
		
		// Camp: nom d'usuari
		usuariField = new JTextField();
		usuariField.setForeground(Color.DARK_GRAY);
		usuariField.setHorizontalAlignment(SwingConstants.CENTER);
		usuariField.setBounds(40, 55, 140, 30);
		frame.getContentPane().add(usuariField);
		usuariField.setColumns(10);
		
		// Label: contrasenya
		JLabel lblContrasenya = new JLabel("Contrasenya:");
		lblContrasenya.setLabelFor(contrasenyaField);
		lblContrasenya.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasenya.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblContrasenya.setBounds(40, 85, 145, 20);
		frame.getContentPane().add(lblContrasenya);
		
		// Camp: contrasenya (amb ocultació)
		contrasenyaField = new JPasswordField();
		contrasenyaField.setHorizontalAlignment(SwingConstants.CENTER);
		contrasenyaField.setForeground(Color.DARK_GRAY);
		contrasenyaField.setBounds(40, 105, 140, 30);
		frame.getContentPane().add(contrasenyaField);
		
		// Botó: iniciar sessió
		btnLogIn = new JButton("Inicia sessió");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuari = usuariField.getText();
				String contrasenya = String.valueOf(contrasenyaField.getPassword());
				String[] botons = {"D'acord"};
				
				if (usuari.length() == 0 || contrasenya.length() == 0)
					(new VistaDialog()).setDialog("Camps incomplets", "Has d'emplenar tots els camps per iniciar sessió!", botons, JOptionPane.INFORMATION_MESSAGE);
				else {	
				try {
					ctrl.getDomini().logIn(usuari, contrasenya);
					frame.dispose();
					ctrl.openMenu();
				}
				catch (Exception e2) {
					if (!e2.getMessage().equals("Contrasenya incorrecta") && !e2.getMessage().equals("No existeix cap usuari amb el nom " + usuari)) {
						(new VistaDialog()).setDialog("No s'ha pogut iniciar sessió", "L'usuari " + usuari+" no té fitxer de camins!", botons, JOptionPane.QUESTION_MESSAGE);
					}
					else (new VistaDialog()).setDialog("No s'ha pogut iniciar sessió", e2.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
				}}
			}
		});
		btnLogIn.setBounds(40, 145, 140, 30);
		frame.getContentPane().add(btnLogIn);
		frame.getRootPane().setDefaultButton(btnLogIn);
		
	}
}
