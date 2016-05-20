package presentacio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class VistaAfegirUsuari {

	private JFrame frame;
	private JTextField nomField;
	private JLabel lblAdministrador;
	private JCheckBox chckbxNewCheckBox;
	private JButton btnCancellar;
	private JButton btnAfegeix;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaAfegirUsuari window = new VistaAfegirUsuari();
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
	public VistaAfegirUsuari() {
		inicialitzaComponents();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void inicialitzaComponents() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 314, 220);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 93, 160, 160, 0, 0};
		gridBagLayout.rowHeights = new int[]{52, 0, 5, 20, 45, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblAfegirNouUsuari = new JLabel("Afegir nou usuari");
		lblAfegirNouUsuari.setHorizontalAlignment(SwingConstants.LEFT);
		lblAfegirNouUsuari.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		GridBagConstraints gbc_lblAfegirNouUsuari = new GridBagConstraints();
		gbc_lblAfegirNouUsuari.gridwidth = 3;
		gbc_lblAfegirNouUsuari.insets = new Insets(0, 0, 5, 5);
		gbc_lblAfegirNouUsuari.gridx = 1;
		gbc_lblAfegirNouUsuari.gridy = 0;
		frame.getContentPane().add(lblAfegirNouUsuari, gbc_lblAfegirNouUsuari);
		
		JLabel lblNom = new JLabel("Nom:");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.WEST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 1;
		gbc_lblNom.gridy = 1;
		frame.getContentPane().add(lblNom, gbc_lblNom);
		
		nomField = new JTextField();
		GridBagConstraints gbc_nomField = new GridBagConstraints();
		gbc_nomField.gridwidth = 2;
		gbc_nomField.insets = new Insets(0, 0, 5, 5);
		gbc_nomField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nomField.gridx = 2;
		gbc_nomField.gridy = 1;
		frame.getContentPane().add(nomField, gbc_nomField);
		nomField.setColumns(10);
		
		JLabel lblContrasenya = new JLabel("Contrasenya:");
		GridBagConstraints gbc_lblContrasenya = new GridBagConstraints();
		gbc_lblContrasenya.anchor = GridBagConstraints.WEST;
		gbc_lblContrasenya.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasenya.gridx = 1;
		gbc_lblContrasenya.gridy = 2;
		frame.getContentPane().add(lblContrasenya, gbc_lblContrasenya);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 2;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 2;
		frame.getContentPane().add(passwordField, gbc_passwordField);
		
		lblAdministrador = new JLabel("Administrador:");
		GridBagConstraints gbc_lblAdministrador = new GridBagConstraints();
		gbc_lblAdministrador.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdministrador.gridx = 1;
		gbc_lblAdministrador.gridy = 3;
		frame.getContentPane().add(lblAdministrador, gbc_lblAdministrador);
		
		chckbxNewCheckBox = new JCheckBox("");
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.gridwidth = 2;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 2;
		gbc_chckbxNewCheckBox.gridy = 3;
		frame.getContentPane().add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		
		btnAfegeix = new JButton("Afegeix");
		GridBagConstraints gbc_btnAfegeix = new GridBagConstraints();
		gbc_btnAfegeix.insets = new Insets(0, 0, 0, 5);
		gbc_btnAfegeix.gridx = 1;
		gbc_btnAfegeix.gridy = 4;
		frame.getContentPane().add(btnAfegeix, gbc_btnAfegeix);
		
		btnCancellar = new JButton("Cancel·la");
		btnCancellar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnCancella = new GridBagConstraints();
		gbc_btnCancella.gridwidth = 2;
		gbc_btnCancella.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancella.gridx = 2;
		gbc_btnCancella.gridy = 4;
		frame.getContentPane().add(btnCancellar, gbc_btnCancella);
	}

}
