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

@SuppressWarnings({"unused", "rawtypes", "serial", "unchecked"})
public class VistaAfegirEntitat extends JDialog {

	private JTextField textField;
	private JComboBox comboBox_1;
	private JComboBox comboBox_1_1;
	private CtrlPresentacio ctrl;
	private String[] labels = {"UNKNOWN", "DATABSE", "DATA_MINING", "AI", "INFORMATION_RETRIVAL"};
	private String[] tipusE = {"", "Paper", "Autor", "Conferencia", "Terme"};
	
	//Entitat
	private String nom;
	private String tipus;
	private String label = "UNKNOWN";

	private void initComponents(){
		
		
		//frame
		
		setBackground(SystemColor.control);
		setForeground(Color.BLACK);
		setSize(400, 200);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new MigLayout("", "[][][50px,grow][200px,grow][][][50px,grow][200px,grow][][]", "[][50px][][][][][][50px]"));
		
		//seleció label
		JComboBox comboBox_1_1 = new JComboBox(labels);
		comboBox_1_1.setBounds(255, 37, 113, 24);
		getContentPane().add(comboBox_1_1, "cell 3 6,alignx left,aligny top");
				
		comboBox_1_1.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie){
				if(ie.getStateChange() == ItemEvent.SELECTED){
						label = (String) ie.getItem();
				}
			}
		});

		//label seleccio tipus
		JLabel lblTipusDentitat = new JLabel("Tipus d'Entitat:");
		lblTipusDentitat.setBounds(30, 10, 136, 15);
		getContentPane().add(lblTipusDentitat, "cell 0 0,alignx left,aligny center");
		
		//selecció tipus	
		JComboBox comboBox = new JComboBox(tipusE);
		comboBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie){
				String tipusAnterior = tipus;
				if(ie.getStateChange() == ItemEvent.SELECTED){
					tipus = (String) ie.getItem();
					if(tipus=="Terme"){
						comboBox_1_1.setEnabled(false);
						label = "NO_LABEL";
					}
					else{
						comboBox_1_1.setEnabled(true);
						if(tipusAnterior == "Terme") label = "UNKNOWN";
					}
				}
			}
		});
		
		
		comboBox.setBounds(30, 37, 113, 24);
		getContentPane().add(comboBox, "cell 3 0,alignx left,aligny top");
		
		
		//escriure nom
		textField = new JTextField();
		
		
		//label escriure nom
		JLabel lblNomDeLentitat = new JLabel("Nom de l'entitat:");
		lblNomDeLentitat.setBounds(30, 71, 136, 15);
		getContentPane().add(lblNomDeLentitat, "cell 0 2,alignx left,aligny center");
		textField.setBounds(30, 98, 114, 19);
		getContentPane().add(textField, "cell 3 2,growx,aligny center");
		textField.setColumns(10);
		
		//label selecció label
		JLabel lblLabel = new JLabel("Label:");
		lblLabel.setBounds(255, 10, 136, 15);
		getContentPane().add(lblLabel, "cell 0 6,alignx left,aligny center");
		
		
		
		//boto afegir
		JButton okButton = new JButton("Afegir entitat");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nom = textField.getText();
				if(nom==null || tipus == null){
					VistaDialog d = new VistaDialog();
					d.setDialog("Error", "Omple tots els camps",new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
				}
				else{
					try{
						if(tipus=="Paper") ctrl.getDomini().altaPaper(nom, label);
						else if(tipus=="Autor") ctrl.getDomini().altaAutor(nom, label);
						else if(tipus=="Conferencia") ctrl.getDomini().altaConferencia(nom, label);
						else if(tipus=="Terme") ctrl.getDomini().altaTerme(nom);
						
						dispose();
					}
					catch(Exception e2){
						VistaDialog d = new VistaDialog();
						d.setDialog("Error", e2.getMessage(),new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		getContentPane().add(okButton, "cell 0 7");
		
		//boto cancelar
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(cancelButton, "cell 3 7");
		
	}
	
	
	/**
	 * Creadora de VistaAfegirEntitat
	 * @param ctrl Controlador de presentació
	 * @param owner Frame que obra la vista
	 */
	public VistaAfegirEntitat(CtrlPresentacio ctrl, JFrame owner) {
		super(owner, true);
		this.ctrl = ctrl;
		initComponents();	
		setVisible(true);
		setLocationRelativeTo(owner);
	}
}
