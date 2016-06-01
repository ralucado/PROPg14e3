package presentacio.camins;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

@SuppressWarnings("serial")
public class VistaAfegirCami extends VistaCanvisCami{
	
	public VistaAfegirCami(CtrlPresentacio ctrl, JFrame parent) {
		super(ctrl, parent);
		inicialitzaCompPropis();
		setVisible(true);
	}
	
	
	private void inicialitzaCompPropis() {
		JLabel lblTitle = new JLabel("Afegir nou cami");
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		GridBagConstraints gbc_lblAfegirNouCami = new GridBagConstraints();
		gbc_lblAfegirNouCami.gridwidth = 3;
		gbc_lblAfegirNouCami.insets = new Insets(0, 0, 5, 5);
		gbc_lblAfegirNouCami.gridx = 1;
		gbc_lblAfegirNouCami.gridy = 0;
		getContentPane().add(lblTitle, gbc_lblAfegirNouCami);
		
		btnAccepta.setText("Afegeix");
		
		btnAccepta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nomField.getText().isEmpty() || pathField.getText().isEmpty()){
					VistaDialog dialog = new VistaDialog();
					String[] botons = {"D'acord"};
					dialog.setDialog("Dades insuficients!", "Has d'omplir obligatoriament els camps Nom i Path", botons, JOptionPane.ERROR_MESSAGE);
				}
				else{
					try {
						ctrl.getDomini().afegirCami(nomField.getText(), pathField.getText(), descField.getText());
						dispose();
					}
					catch (Exception e2) {
						VistaDialog dialog = new VistaDialog();
						String[] botons = {"D'acord"};
						dialog.setDialog("No s'ha pogut afegir el cami", e2.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
	
}
