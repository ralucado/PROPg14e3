package presentacio.graf;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;

import domini.graf.CtrlGraf;
import domini.graf.Entitat;
import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

import javax.swing.event.ListSelectionEvent;

public class VistaAfegirRelacio extends JDialog {

	private String tipusE1;
	private String nomE1;
	private String labelE1;

	private String nomE2;
	private String labelE2;
	private JList list;
	private CtrlPresentacio ctrl;
	
	
	private String[] labels = {"UNKNOWN", "DATABSE", "DATA_MINING", "AI", "INFORMATION_RETRIVAL"};
	private String[] tipusE = {"", "Autor", "Conferencia", "Terme"};
	private String autors[];
	private String conferencies[];
	private String termes[];
	private String papers[];
	private String buit[] = {};
	
	private void initComponents(){
		
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		}
		catch(Exception e){}
		
		//frame
		JFrame frame = new JFrame();
		
		setBackground(SystemColor.control);
		setForeground(Color.BLACK);
		setSize(650, 400);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new MigLayout("", "[100px,grow][][20px,grow][30px,grow][50px,grow][][]", "[100px,grow][100px,grow][100px,grow][100px,grow][100px,grow][][][][][][][][][][]"));
		
		
		//label entitat 1
		JLabel lblEntitat = new JLabel("<html><b><u>Entitat 1</u></b></html>");
		lblEntitat.setBounds(100, 12, 70, 15);
		getContentPane().add(lblEntitat, "cell 1 0,alignx center,aligny center");
		
		
		//label entitat 2
		JLabel lblEntitat_1 = new JLabel("<html><b><u>Entitat 2 (Paper)</u></b></html>");
		lblEntitat_1.setBounds(401, 12, 70, 15);
		getContentPane().add(lblEntitat_1, "cell 5 0");
		
		
		
		
		//boto afegir
		JButton okButton = new JButton("Afegir relació");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nomE1==null || tipusE1 == null || nomE2==null){
					VistaDialog d = new VistaDialog();
					d.setDialog("Error", "Omple tots els camps",new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
				}
				else{
					
					try{
						if(tipusE1=="Autor") ctrl.getDomini().afegirRelacioAP(nomE1, nomE2);
						else if(tipusE1=="Conferencia") ctrl.getDomini().afegirRelacioCP(nomE1, nomE2);
						else if(tipusE1=="Terme") ctrl.getDomini().afegirRelacioTP(nomE1, nomE2);
						
						dispose();
					}
					catch(Exception e2){
						VistaDialog d = new VistaDialog();
						d.setDialog("Error", e2.getMessage(),new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		//escollir tipus 1
		JComboBox comboBox = new JComboBox(tipusE);
		comboBox.setBounds(216, 56, 47, 24);
		comboBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie){
				if(ie.getStateChange() == ItemEvent.SELECTED){
					tipusE1 = (String) ie.getItem();
				}
			}
		});
		
		JLabel label_3 = new JLabel("");
		getContentPane().add(label_3, "cell 2 3 3 1");
		
		JLabel label = new JLabel("");
		getContentPane().add(label, "cell 2 4 3 1");
		
		JLabel label_1 = new JLabel("");
		getContentPane().add(label_1, "cell 5 4");
		
		JLabel label_2 = new JLabel("");
		getContentPane().add(label_2, "cell 5 3");
		
		
		JButton button = new JButton("<html>Selecciona Entitat</html>");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> a = new ArrayList<String>();
				new VistaEntitatsDialog(ctrl, null, "Paper", a);
				if(a.size()!=0){
						try{
							ArrayList<String> paper = ctrl.getDomini().consultarPaper(a.get(0));
							nomE2 = a.get(0);
							labelE2 = paper.get(1);
							if(labelE2=="") labelE2="UNKNOWN";
							label_2.setText("<html>"+nomE2+"</html>");
							label.setText("<html>Label: </html>");
							label_1.setText("<html>"+labelE2+"</html>");
							label_3.setText("<html><b>Entitat seleccionada:</b></html>");
						} catch (Exception e1) {
							VistaDialog d = new VistaDialog();
							d.setDialog("Error", e1.getMessage(),new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
						}
				}
			}
		});
		getContentPane().add(button, "cell 5 2,alignx center,aligny center");
		
		
		
		JLabel lblLabel1 = new JLabel("");
		getContentPane().add(lblLabel1, "cell 0 4");
	
		JLabel lblLabel2 = new JLabel("");
		getContentPane().add(lblLabel2, "cell 1 4");
			
		JLabel lblNom = new JLabel("");
		getContentPane().add(lblNom, "cell 1 3");
		
		JLabel lblentitatSeleccionada = new JLabel("");
		getContentPane().add(lblentitatSeleccionada, "cell 0 3");
		
		JLabel lblSeleccionaElTipus = new JLabel("<html>Selecciona el tipus de l'entitat:</html>");
		getContentPane().add(lblSeleccionaElTipus, "cell 0 1,alignx trailing");
		getContentPane().add(comboBox, "cell 1 1");
		
		JButton btnNewButton = new JButton("<html>Selecciona Entitat</html>");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> a = new ArrayList<String>();
				new VistaEntitatsDialog(ctrl, null, tipusE1, a);
				if(a.size()!=0){
					if(tipusE1=="Conferencia"){
						try {
							ArrayList<String> conf = ctrl.getDomini().consultarConferencia(a.get(0));
							nomE1 = a.get(0);
							labelE1 = conf.get(1);
							if(labelE1=="") labelE1="UNKNOWN";
							lblNom.setText("<html>"+nomE1+"</html>");
							lblLabel1.setText("<html>Label: </html>");
							lblLabel2.setText("<html>"+labelE1+"</html>");
							lblentitatSeleccionada.setText("<html><b>Entitat seleccionada:</b></html>");
						} catch (Exception e1) {
							VistaDialog d = new VistaDialog();
							d.setDialog("Error", e1.getMessage(),new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
						}
					}
					else if(tipusE1 == "Autor"){
						try {
							ArrayList<String> autor = ctrl.getDomini().consultarAutor(a.get(0));
							nomE1 = a.get(0);
							labelE1 = autor.get(1);
							if(labelE1=="") labelE1="UNKNOWN";
							lblNom.setText("<html>"+nomE1+"</html>");
							lblLabel1.setText("<html>Label: </html>");
							lblLabel2.setText("<html>"+labelE1+"</html>");
							lblentitatSeleccionada.setText("<html><b>Entitat seleccionada:</b></html>");
						} catch (Exception e1) {
							VistaDialog d = new VistaDialog();
							d.setDialog("Error", e1.getMessage(),new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
						}
					}
					else if(tipusE1 == "Terme"){
						nomE1 = a.get(0);
						labelE1 = null;
						lblNom.setText("<html>"+nomE1+"</html>");
						lblLabel1.setText("");
						lblLabel2.setText("");
						lblentitatSeleccionada.setText("<html><b>Entitat seleccionada:</b></html>");
					}
				}
			}
		});
		getContentPane().add(btnNewButton, "cell 1 2,alignx center,aligny center");
		
		
		
		
		
		
		getContentPane().add(okButton, "cell 0 13,alignx center");
		
		//boto cancelar
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(cancelButton, "cell 5 13");
		
	}
	
	
	public VistaAfegirRelacio(CtrlPresentacio ctrl, JFrame owner) {
		super(owner, true);
		this.ctrl  =ctrl;
		initComponents();
		setVisible(true);
	}
}
