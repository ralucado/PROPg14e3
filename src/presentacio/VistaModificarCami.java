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
public class VistaModificarCami extends VistaCanvisCami {
	private String nomIni;
	private String descIni;
	private String pathIni;

	public VistaModificarCami(CtrlPresentacio ctrl, JFrame parent, String nom) {
		super(ctrl, parent);
		this.nomIni = nom;
		inicialitzaCompPropis();
		setVisible(true);
	}

	private void inicialitzaCompPropis() {
		JLabel lblTitle = new JLabel("Modifica el camí");
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		GridBagConstraints gbc_lblAfegirNouCami= new GridBagConstraints();
		gbc_lblAfegirNouCami.gridwidth = 3;
		gbc_lblAfegirNouCami.insets = new Insets(0, 0, 5, 5);
		gbc_lblAfegirNouCami.gridx = 1;
		gbc_lblAfegirNouCami.gridy = 0;
		getContentPane().add(lblTitle, gbc_lblAfegirNouCami);
		
		try {
			String[] dades = ctrl.getDomini().consultarCamiUsuari(nomIni);
			pathIni = dades[1];
			descIni = dades[2];
			nomField.setText(nomIni);
			pathField.setText(pathIni);
			descField.setText(descIni);
		}
		catch (Exception e1) {
			String[] botons = {"D'acord"};
			(new VistaDialog()).setDialog("No s'ha pogut modificar el cami", e1.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
		}
		
		
		btnAccepta.setText("Modifica");
		btnAccepta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nom = nomField.getText();
					String path = pathField.getText();
					String desc = descField.getText();
					String[] botons = {"D'acord"};

					if (!nom.equals(nomIni)) {
						
						if (ctrl.getDomini().existeixCami(nom))
							(new VistaDialog()).setDialog("Nom no disponible", "El nom del camí ja s'està utilitzant!", botons, JOptionPane.WARNING_MESSAGE);
						else {
							ctrl.getDomini().modificarNomCami(nomIni, nom);
						}
					}
					
					if (!path.equals(pathIni)) {
						ctrl.getDomini().modificarPathCami(nom, path);
					}
					
					if (!desc.equals(descIni)) {
						ctrl.getDomini().modificarDescrCami(nom, desc);
					}
					dispose();
				}
				catch (Exception e2) {
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("Error al modificar el cami", e2.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

}
