package presentacio;

import java.awt.EventQueue;
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
		
		btnAfegeix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (cbEsAdmin.isSelected()) {
						ctrl.getDomini().altaAdmin(nomField.getText(), String.valueOf(passwordField.getPassword()));
					}
					else {
						ctrl.getDomini().altaUsuari(nomField.getText(), String.valueOf(passwordField.getPassword()));
					}
					dispose();
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
