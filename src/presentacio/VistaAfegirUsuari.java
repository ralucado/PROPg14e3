package presentacio;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class VistaAfegirUsuari extends VistaCanvisUsuari {
	
	public VistaAfegirUsuari(CtrlPresentacio ctrl, JFrame parent) {
		super(ctrl, parent);
		inicialitzaCompPropis();
		setVisible(true);
	}
	
	
	void inicialitzaCompPropis() {
		JLabel lblTitle = new JLabel("Afegir nou usuari");
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		GridBagConstraints gbc_lblAfegirNouUsuari = new GridBagConstraints();
		gbc_lblAfegirNouUsuari.gridwidth = 3;
		gbc_lblAfegirNouUsuari.insets = new Insets(0, 0, 5, 5);
		gbc_lblAfegirNouUsuari.gridx = 1;
		gbc_lblAfegirNouUsuari.gridy = 0;
		getContentPane().add(lblTitle, gbc_lblAfegirNouUsuari);
		
		btnAccepta.setText("Afegeix");
		
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
					else {
					if (esAdmin) {
						ctrl.getDomini().altaAdmin(nomField.getText(), String.valueOf(passwordField.getPassword()));
					}
					else {
						ctrl.getDomini().altaUsuari(nomField.getText(), String.valueOf(passwordField.getPassword()));
					}
					dispose();
					}
				}
				catch (Exception e2) {
					VistaDialog dialog = new VistaDialog();
					String[] botons = {"D'acord"};
					dialog.setDialog("No s'ha pogut afegir l'usuari", e2.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	
}
