package presentacio.usuaris;

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

import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

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

	protected void inicialitzaCompPropis() {
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
			if (dades.get(1) == "1") esAdminIni = true;
			else esAdminIni = false;
			passwordIni = ctrl.getDomini().getContrasenya(usernameIni);
			// Controlar que no quedin 0 admins
			System.out.println("usernameIni: "+usernameIni+"; passwordIni: "+passwordIni+"; esAdminIni: "+esAdminIni);
			
			
			if (usernameIni == ctrl.getDomini().getUsuariActual() || esAdminIni) cbEsAdmin.setEnabled(false);
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
					
					if (username.equals("") && password.equals("")) {
						(new VistaDialog()).setDialog("Nom d'usuari i contrasenya buits", "Has d'escriure un nom d'usuari i una contrasenya", botons, JOptionPane.WARNING_MESSAGE);
					}
					else if (password.equals("")) {
						(new VistaDialog()).setDialog("Contrasenya buida", "Has d'escriure una contrasenya", botons, JOptionPane.WARNING_MESSAGE);
					}
					else if (username.equals("")) {
						(new VistaDialog()).setDialog("Nom d'usuari buit", "Has d'escriure un nom d'usuari", botons, JOptionPane.WARNING_MESSAGE);
					}
					else if (username.indexOf('/') != -1) {
						(new VistaDialog()).setDialog("Caràcter no permès", "El nom d'usuari no pot contenir el caràcter '/'.", botons, JOptionPane.WARNING_MESSAGE);
					} else {
						if (!username.equals(usernameIni)) {
							try {
								System.out.println("TRRY");
								ctrl.getDomini().modificarNomUsuari(usernameIni, username);
							} catch (Exception ex) {
								(new VistaDialog()).setDialog("Error al modificar nom d'usuari", ex.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
							}
					}
					
					if (!password.equals(passwordIni)) {
						ctrl.getDomini().modificarContrasenya(username, password);
					}
					
					if (esAdmin != esAdminIni) {
						ctrl.getDomini().ferAdmin(username);
					}
					dispose();
					}
				}
				catch (Exception e2) {
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("Error al modificar l'usuari", e2.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

}
