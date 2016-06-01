package presentacio.usuaris;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

/**
 * És la vista per iniciar sessió al programa.
 * @author Arnau Blanch Cortès
 *
 */
public class VistaIniciSessio {
	
	private CtrlPresentacio ctrl;
	
	private JFrame frame;
	private JTextField usuariField;
	private JPasswordField contrasenyaField;
	private JButton btnLogIn;
	private JLabel lblNewLabel;

	/**
	 * Crea una nova VistaIniciSessio
	 * @param ctrl controlador de presentació
	 */
	public VistaIniciSessio(CtrlPresentacio ctrl) {
		this.ctrl = ctrl;
		try {
			inicialitzarComponents();
		}
		catch (Exception e) {
			String[] botons = {"D'acord"};
			(new VistaDialog()).setDialog("Error al iniciar components", e.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
		}
		this.frame.setVisible(true);
	}
	
	/**
	 * Fa visible la vista.
	 */
	public void fesVisible() {
		frame.setVisible(true);
	}
	
	/**
	 * Resetejar els components de la vista.
	 */
	public void reset() {
		usuariField.setText("");
		contrasenyaField.setText("");
		frame.getRootPane().setDefaultButton(btnLogIn);
	}

	private void inicialitzarComponents() throws IOException {
		// Frame
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(416, 241);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("PathSearcher");
		
		// Label: nom d'usuari
		JLabel lblUsuari = new JLabel("Nom d'usuari:");
		lblUsuari.setLabelFor(usuariField);
		lblUsuari.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblUsuari.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuari.setBounds(248, 34, 140, 20);
		frame.getContentPane().add(lblUsuari);
		
		// Camp: nom d'usuari
		usuariField = new JTextField();
		usuariField.setForeground(Color.DARK_GRAY);
		usuariField.setHorizontalAlignment(SwingConstants.CENTER);
		usuariField.setBounds(248, 54, 140, 30);
		frame.getContentPane().add(usuariField);
		usuariField.setColumns(10);
		
		// Label: contrasenya
		JLabel lblContrasenya = new JLabel("Contrasenya:");
		lblContrasenya.setLabelFor(contrasenyaField);
		lblContrasenya.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasenya.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblContrasenya.setBounds(248, 84, 145, 20);
		frame.getContentPane().add(lblContrasenya);
		
		// Camp: contrasenya (amb ocultació)
		contrasenyaField = new JPasswordField();
		contrasenyaField.setHorizontalAlignment(SwingConstants.CENTER);
		contrasenyaField.setForeground(Color.DARK_GRAY);
		contrasenyaField.setBounds(248, 104, 140, 30);
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
					if (e2.getMessage().equals("DATA/Camins/dUsuari/"+usuari+".txt (No such file or directory)")) {
						(new VistaDialog()).setDialog("No s'ha pogut iniciar sessió", "L'usuari " + usuari+" no té fitxer de camins!", botons, JOptionPane.QUESTION_MESSAGE);
					}
					else (new VistaDialog()).setDialog("No s'ha pogut iniciar sessió", e2.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
				}}
			}
		});
		btnLogIn.setBounds(248, 144, 140, 30);
		frame.getContentPane().add(btnLogIn);
		frame.getRootPane().setDefaultButton(btnLogIn);
		BufferedImage logo = ImageIO.read(new File("logo.png"));
		
		lblNewLabel = new JLabel(new ImageIcon(logo));
		lblNewLabel.setBounds(5, 6, 230, 207);
		frame.getContentPane().add(lblNewLabel);
		

	}
	
}
