package presentacio.graf;
	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.FlowLayout;
	import java.awt.SystemColor;
	import java.awt.TextField;

	import javax.swing.JButton;
	import javax.swing.JDialog;
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.border.EmptyBorder;

import domini.graf.Autor;
import domini.graf.Label;
	import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

import javax.swing.JComboBox;
	import java.awt.event.ActionListener;
	import java.awt.event.ItemEvent;
	import java.awt.event.ItemListener;
	import java.awt.event.ActionEvent;
	import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

	public class VistaModificarEntitat extends JDialog {

		private JTextField textField;
		private JComboBox comboBox_1;
		private JComboBox comboBox_1_1;
		private CtrlPresentacio ctrl;
		private String[] labels = {"", "UNKNOWN", "DATABSE", "DATA_MINING", "AI", "INFORMATION_RETRIVAL"};
		
		//Entitat
		private String nomAct;
		private String nom;
		private String tipus;
		private String label;

		private void initComponents(){
			//frame
			
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
			
			//label selecció label
			JLabel lblLabel = new JLabel("Nova label:");
			lblLabel.setBounds(255, 10, 136, 15);
			getContentPane().add(lblLabel, "cell 0 6,alignx left,aligny center");
			
				
			//boto modificar
			JButton okButton = new JButton("Modificar entitat");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(label==null || label == ""){
						if(nom != null){
							
							try {
								ctrl.getDomini().modificarEntitat(nomAct, nom, null, tipus);
								System.out.println("S'ha modificat el nom a: "+nom);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else{
							VistaDialog d = new VistaDialog();
							d.setDialog("Error", "Afegeix algun camp a modificar",new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
						}
					}
					else{
						if(nom != null){
							try{
								
								ctrl.getDomini().modificarEntitat(nomAct, nom, label, tipus);
								System.out.println("S'ha modificat el nom a :"+nom+" i la label a: "+label);
							}
							catch(Exception e2){
								e2.printStackTrace();
							}
						}
						else{
							try{
								System.out.println("S'ha modificat la label a: "+label);
								ctrl.getDomini().modificarEntitat(nomAct, null, label, tipus);
							}
							catch(Exception e3){
								e3.printStackTrace();
							}
						}
					}
					dispose();
					
				}
			});
			
			//seleció label
			JComboBox comboBox_1_1_1 = new JComboBox(labels);
			comboBox_1_1_1.setBounds(255, 37, 113, 24);
			getContentPane().add(comboBox_1_1_1, "cell 3 6,alignx left,aligny top");
			
			comboBox_1_1_1.addItemListener(new ItemListener(){
				public void itemStateChanged(ItemEvent ie){
						if(ie.getStateChange() == ItemEvent.SELECTED){
								label = (String) ie.getItem();
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
		 * Create the dialog.
		 */
		public VistaModificarEntitat(CtrlPresentacio ctrl, JFrame owner, String tipus, String nomAct) {
			super(owner, true);
			this.ctrl = ctrl;
			this.nomAct = nomAct;
			this.tipus = tipus;
			initComponents();		
			setVisible(true);
			setLocationRelativeTo(null);
		}
}