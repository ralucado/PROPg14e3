package presentacio.usuaris;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import presentacio.ctrl.CtrlPresentacio;

/**
 * És la vista per fer canvis als usuaris.
 * @author Arnau Blanch Cortès
 *
 */
@SuppressWarnings("serial")
public abstract class VistaCanvisUsuari extends JDialog {
	
	protected CtrlPresentacio ctrl;
	protected JTextField nomField;
	protected JLabel lblAdministrador;
	protected JCheckBox cbEsAdmin;
	protected JButton btnCancella;
	protected JButton btnAccepta;
	protected JPasswordField passwordField;
	

	/**
	 * Crea una nova VistaCanvisUsuari.
	 * @param ctrl controlador de presentació
	 * @param parent frame pare
	 */
	public VistaCanvisUsuari(CtrlPresentacio ctrl, JFrame parent) {
		super(parent, true);
		this.ctrl = ctrl;
		inicialitzaCompComuns(); 
	}


	protected void inicialitzaCompComuns() {
		setResizable(false);
		setBounds(100, 100, 314, 220);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 93, 160, 160, 30, 0};
		gridBagLayout.rowHeights = new int[]{52, 0, 5, 20, 45, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		
		JLabel lblNom = new JLabel("Nom:");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.WEST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 1;
		gbc_lblNom.gridy = 1;
		getContentPane().add(lblNom, gbc_lblNom);
		
		nomField = new JTextField();
		GridBagConstraints gbc_nomField = new GridBagConstraints();
		gbc_nomField.gridwidth = 2;
		gbc_nomField.insets = new Insets(0, 0, 5, 5);
		gbc_nomField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nomField.gridx = 2;
		gbc_nomField.gridy = 1;
		getContentPane().add(nomField, gbc_nomField);
		nomField.setColumns(10);
		
		JLabel lblContrasenya = new JLabel("Contrasenya:");
		GridBagConstraints gbc_lblContrasenya = new GridBagConstraints();
		gbc_lblContrasenya.anchor = GridBagConstraints.WEST;
		gbc_lblContrasenya.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasenya.gridx = 1;
		gbc_lblContrasenya.gridy = 2;
		getContentPane().add(lblContrasenya, gbc_lblContrasenya);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 2;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 2;
		getContentPane().add(passwordField, gbc_passwordField);
		
		lblAdministrador = new JLabel("Administrador:");
		GridBagConstraints gbc_lblAdministrador = new GridBagConstraints();
		gbc_lblAdministrador.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdministrador.gridx = 1;
		gbc_lblAdministrador.gridy = 3;
		getContentPane().add(lblAdministrador, gbc_lblAdministrador);
		
		cbEsAdmin = new JCheckBox("");
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.gridwidth = 2;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 2;
		gbc_chckbxNewCheckBox.gridy = 3;
		getContentPane().add(cbEsAdmin, gbc_chckbxNewCheckBox);
		
		btnAccepta = new JButton();
		GridBagConstraints gbc_btnAccepta = new GridBagConstraints();
		gbc_btnAccepta.insets = new Insets(0, 0, 0, 5);
		gbc_btnAccepta.gridx = 1;
		gbc_btnAccepta.gridy = 4;
		getContentPane().add(btnAccepta, gbc_btnAccepta);
		getRootPane().setDefaultButton(btnAccepta);
		
		btnCancella = new JButton("Cancel·la");
		btnCancella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GridBagConstraints gbc_btnCancella = new GridBagConstraints();
		gbc_btnCancella.gridwidth = 2;
		gbc_btnCancella.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancella.gridx = 2;
		gbc_btnCancella.gridy = 4;
		getContentPane().add(btnCancella, gbc_btnCancella);
	}
}
