package presentacio.graf;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	private String nomE2;
	private JList list;
	private CtrlPresentacio ctrl;
	
	
	private String[] labels = {"UNKNOWN", "DATABSE", "DATA_MINING", "AI", "INFORMATION_RETRIVAL"};
	private String[] tipusE = {"", "A", "C", "T"};
	private String autors[];
	private String conferencies[];
	private String termes[];
	private String papers[];
	private String buit[] = {};
	
	private void initComponents(){
		//frame
		
		setBackground(SystemColor.control);
		setForeground(Color.BLACK);
		setSize(650, 400);
		setLocationRelativeTo(null);
				
		getContentPane().setLayout(new MigLayout("", "[100px,grow][20px, grow][30px, grow][50px, grow][][]", "[100px,grow][100px,grow][100px,grow][100px,grow][100px,grow][][][][][][][][][]"));
		
		
		//label entitat 1
		JLabel lblEntitat = new JLabel("Entitat 1");
		lblEntitat.setBounds(100, 12, 70, 15);
		getContentPane().add(lblEntitat, "cell 0 0,alignx center,aligny center");
		
		
		
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
						if(tipusE1=="A") ctrl.getDomini().afegirRelacioAP(nomE1, nomE2);
						else if(tipusE1=="C") ctrl.getDomini().afegirRelacioCP(nomE1, nomE2);
						else if(tipusE1=="T") ctrl.getDomini().afegirRelacioTP(nomE1, nomE2);
						
						dispose();
					}
					catch(Exception e2){
						System.out.println(e2.getMessage());
					}
				}
			}
		});
		
		
		//label entitat 2
		JLabel lblEntitat_1 = new JLabel("Entitat 2 (Paper)");
		lblEntitat_1.setBounds(401, 12, 70, 15);
		getContentPane().add(lblEntitat_1, "cell 4 0");
		
		//llista entitat1
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 85, 200, 300);
		getContentPane().add(scrollPane, "cell 0 1 2 8,grow");
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				nomE1 = (String) list.getSelectedValue();
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		
		//escollir tipus 1
		JComboBox comboBox = new JComboBox(tipusE);
		comboBox.setBounds(216, 56, 47, 24);
		comboBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie){
				if(ie.getStateChange() == ItemEvent.SELECTED){
					tipusE1 = (String) ie.getItem();
					if(tipusE1.equals("A")) list.setListData(autors);
					else if(tipusE1.equals("C")) list.setListData(conferencies);
					else if(tipusE1.equals("T")) list.setListData(termes);
					else list.setListData(buit);
				}
			}
		});
		getContentPane().add(comboBox, "cell 2 1");
		
		//llista entitat2
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(337, 85, 200, 300);
		getContentPane().add(scrollPane_1, "cell 3 1 3 8,grow");
		
		JList list2 = new JList();
		scrollPane_1.setViewportView(list2);
		list2.setListData(papers);
		list2.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				nomE2 = (String) list2.getSelectedValue();
			}
		});
		list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list2.setLayoutOrientation(JList.VERTICAL);
		
		getContentPane().add(okButton, "cell 0 12,alignx center");
		
		//boto cancelar
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(cancelButton, "cell 4 12");
		
	}
	
	private void carregarDades(){
		
		try {
			ArrayList<String> aux;
			aux = ctrl.getDomini().consultarAutors();
			Collections.sort(aux);
			autors = aux.toArray(new String[aux.size()]);
			
			aux = ctrl.getDomini().consultarPapers();
			Collections.sort(aux);
			papers = aux.toArray(new String[aux.size()]);
			
			aux = ctrl.getDomini().consultarConferencies();
			Collections.sort(aux);
			conferencies = aux.toArray(new String[aux.size()]);
			
			aux = ctrl.getDomini().consultarTermes();
			Collections.sort(aux);
			termes = aux.toArray(new String[aux.size()]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public VistaAfegirRelacio(CtrlPresentacio ctrl, JFrame owner) {
		super(owner, true);
		this.ctrl  =ctrl;
		carregarDades();
		initComponents();
		setVisible(true);
	}
}
