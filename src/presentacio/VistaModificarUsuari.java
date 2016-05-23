package presentacio;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class VistaModificarUsuari extends VistaCanvisUsuari {
	private String usernameIni;
	private String passwordIni;
	private boolean esAdminIni;
	
	public VistaModificarUsuari(CtrlPresentacio ctrl, JFrame parent, String username) {
		super(ctrl, parent);
		this.usernameIni = username;
		inicialitzaCompPropis();
		setVisible(true);
	}

	private void inicialitzaCompPropis() {
		JLabel lblTitle = new JLabel("Modifica l'usuari");
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		GridBagConstraints gbc_lblAfegirNouUsuari = new GridBagConstraints();
		gbc_lblAfegirNouUsuari.gridwidth = 3;
		gbc_lblAfegirNouUsuari.insets = new Insets(0, 0, 5, 5);
		gbc_lblAfegirNouUsuari.gridx = 1;
		gbc_lblAfegirNouUsuari.gridy = 0;
		getContentPane().add(lblTitle, gbc_lblAfegirNouUsuari);
		
		try {
			ArrayList<String> dades = ctrl.getDomini().consultarUsuari(usernameIni);
			passwordIni = dades.get(1);
			if (dades.get(2) == "1") esAdminIni = true;
			else esAdminIni = false;
			// Controlar que no quedin 0 admins
			System.out.println("usernameIni: "+usernameIni+"; passwordIni: "+passwordIni+"; esAdminIni: "+esAdminIni);
			
			
			if (usernameIni == ctrl.getDomini().getUsuariActual()) cbEsAdmin.setEnabled(false);
			nomField.setText(usernameIni);
			passwordField.setText(passwordIni);
			cbEsAdmin.setSelected(esAdminIni);
		}
		catch (Exception e1) {
			String[] botons = {"D'acord"};
			(new VistaDialog()).setDialog("No s'ha pogut modificar l'usuari", e1.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
		}
		
		
		btnAccepta.setText("Modifica");
		btnAccepta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = nomField.getText();
					String password = String.valueOf(passwordField.getPassword());
					boolean esAdmin = cbEsAdmin.isSelected();
					String[] botons = {"D'acord"};
					
					
					// EN PROCEEEES
					if (!username.equals(usernameIni)) {
						System.out.println("Check username: "+usernameIni+ " - " + username);
						if (ctrl.getDomini().existeixUsuari(username))
							(new VistaDialog()).setDialog("Nom d'usuari no disponible", "El nom d'usuari ja s'està utilitzant!", botons, JOptionPane.WARNING_MESSAGE);
						else {
							ctrl.getDomini().modificarNomUsuari(usernameIni, username);
						}
					}
					
					if (!password.equals(passwordIni)) {
						System.out.println("Check password: "+passwordIni+" - "+password);
						ctrl.getDomini().modificarContrasenya(username, password);
					}
					
					if (esAdmin != esAdminIni) {
						System.out.println("Check esAdmin: "+esAdminIni+ " - "+esAdmin);
						if (esAdmin) ctrl.getDomini().ferAdmin(username);
						else if (ctrl.getDomini().getNumAdmins() > 1) {ctrl.getDomini().ferNoAdmin(username); System.out.println("Fent noAdmin");}
					}
					dispose();
				}
				catch (Exception e2) {
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("Error al modificar l'usuari", e2.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

}
