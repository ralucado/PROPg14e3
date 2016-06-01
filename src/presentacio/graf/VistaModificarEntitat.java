package presentacio.graf;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

@SuppressWarnings({"rawtypes", "serial", "unchecked"})
public class VistaModificarEntitat extends JDialog {

	private JTextField textField;
	private JComboBox comboBox_1_1_1;
	private CtrlPresentacio ctrl;
	private String[] labels = {"UNKNOWN", "DATABASE", "DATA_MINING", "AI", "INFORMATION_RETRIEVAL"};
	
	//Entitat
	private String nomAct;
	private String nom;
	private String tipus;
	private String label;

	private void initComponents(){
		
		setBackground(SystemColor.control);
		setForeground(Color.BLACK);
		setSize(400, 200);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new MigLayout("", "[][][50px,grow][200px,grow][][][50px,grow][200px,grow][][]", "[][50px][][][][][][][][50px]"));
		
		//label escriure nom
		JLabel lblNomDeLentitat = new JLabel("Nou nom de l'entitat:");
		lblNomDeLentitat.setBounds(30, 71, 136, 15);
		getContentPane().add(lblNomDeLentitat, "cell 0 1,alignx left,aligny center");
		
		
		//escriure nom
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nom = textField.getText();
			}
		});
		textField.setBounds(30, 98, 114, 19);
		getContentPane().add(textField, "cell 3 1,growx,aligny center");
		textField.setColumns(10);
		textField.setText(nomAct);
		
		//label selecció label
		JLabel lblLabel = new JLabel("Nova label:");
		lblLabel.setBounds(255, 10, 136, 15);
		getContentPane().add(lblLabel, "cell 0 6,alignx left,aligny center");
		
		//seleció label
		comboBox_1_1_1 = new JComboBox(labels);
		comboBox_1_1_1.setBounds(255, 37, 113, 24);
		getContentPane().add(comboBox_1_1_1, "cell 3 6,alignx left,aligny top");
		
		comboBox_1_1_1.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie){
					if(ie.getStateChange() == ItemEvent.SELECTED){
							label = (String) ie.getItem();
					}
			}
		});
		comboBox_1_1_1.setSelectedItem(label);
			
		//boto modificar
		JButton okButton = new JButton("Modificar entitat");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					nom = textField.getText();
					if(nom.equals("")) throw new Exception("El nom no pot estar buit");
					label = (String) comboBox_1_1_1.getSelectedItem();
					ctrl.getDomini().modificarEntitat(nomAct, nom, label, tipus);
					dispose();
				} catch (Exception e1) {
					VistaDialog d = new VistaDialog();
					d.setDialog("Error", e1.getMessage(),new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		
		
		getContentPane().add(okButton, "cell 0 9");
		
		//boto cancelar
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(cancelButton, "cell 3 9");
		
	}
	
	
	/**
	 * Creadora VistaModificarEntitat
	 * @param ctrl Controlador presentació
	 * @param owner Frame que obra la vista
	 * @param tipus Tipus de l'entitat a modificar
	 * @param nomAct Nom actual de l'entitat a modificar
	 * @param label Label actual de l'entitat a modificar
	 */
	public VistaModificarEntitat(CtrlPresentacio ctrl, JFrame owner, String tipus, String nomAct, String label) {
		super(owner, true);
		this.ctrl = ctrl;
		this.nomAct = nomAct;
		this.tipus = tipus;
		this.label = label;
		initComponents();
		if(tipus=="Terme") comboBox_1_1_1.setEnabled(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
