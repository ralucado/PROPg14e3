package presentacio.camins;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import presentacio.ctrl.CtrlPresentacio;

@SuppressWarnings("serial")
public class VistaCanvisCami extends JDialog {
	
	protected CtrlPresentacio ctrl;
	protected JTextField nomField;
	protected JTextField descField;
	protected JTextField pathField;
	protected JButton btnCancella;
	protected JButton btnAccepta;
	

	public VistaCanvisCami(CtrlPresentacio ctrl, JFrame parent) {
		super(parent, true);
		this.ctrl = ctrl;
		inicialitzaCompComuns(); 
	}


	private void inicialitzaCompComuns() {
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
		
		JLabel lblPath = new JLabel("Path:");
		GridBagConstraints gbc_lblPath = new GridBagConstraints();
		gbc_lblPath.anchor = GridBagConstraints.WEST;
		gbc_lblPath.insets = new Insets(0, 0, 5, 5);
		gbc_lblPath.gridx = 1;
		gbc_lblPath.gridy = 2;
		getContentPane().add(lblPath, gbc_lblPath);
		
		pathField = new JTextField();
		GridBagConstraints gbc_pathField = new GridBagConstraints();
		gbc_pathField.gridwidth = 2;
		gbc_pathField.insets = new Insets(0, 0, 5, 5);
		gbc_pathField.fill = GridBagConstraints.HORIZONTAL;
		gbc_pathField.gridx = 2;
		gbc_pathField.gridy = 2;
		getContentPane().add(pathField, gbc_pathField);
		
		JLabel lblDesc = new JLabel("Descripció:");
		GridBagConstraints gbc_lblDesc = new GridBagConstraints();
		gbc_lblDesc.anchor = GridBagConstraints.WEST;
		gbc_lblDesc.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesc.gridx = 1;
		gbc_lblDesc.gridy = 3;
		getContentPane().add(lblDesc, gbc_lblDesc);
		
		descField = new JTextField();
		GridBagConstraints gbc_descField = new GridBagConstraints();
		gbc_descField.gridwidth = 2;
		gbc_descField.insets = new Insets(0, 0, 5, 5);
		gbc_descField.fill = GridBagConstraints.HORIZONTAL;
		gbc_descField.gridx = 2;
		gbc_descField.gridy = 3;
		getContentPane().add(descField, gbc_descField);
		
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
