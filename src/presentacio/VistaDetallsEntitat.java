package presentacio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.TextField;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domini.graf.Label;
import net.miginfocom.swing.MigLayout;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

public class VistaDetallsEntitat extends JDialog {

	private CtrlPresentacio ctrl;
	private String tipusE;
	private String labelE;
	private String nomE;
	private String idE;


	private void initComponents(){
		//frame
		
		setBackground(SystemColor.control);
		setForeground(Color.BLACK);
		setSize(800, 200);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new MigLayout("", "[250px,grow][250px,grow]", "[50px,grow][50px,grow][50px,grow][50px,grow][50px,grow][50px,grow]"));
		
		//label label
		if(tipusE!="Terme"){
			
			
		
			

			
			JLabel lblLabel = new JLabel("La label de l'entitat és:  "+labelE);
			lblLabel.setBounds(255, 10, 136, 15);
			getContentPane().add(lblLabel, "cell 0 6,alignx left,aligny center");
		}
		
		//label nom
		JLabel lblNomDeLentitat = new JLabel("El nom de l'entitat és:  "+nomE);
		lblNomDeLentitat.setBounds(30, 71, 136, 15);
		getContentPane().add(lblNomDeLentitat, "cell 0 0,alignx left,aligny center");
		
		//label tipus
		JLabel lblTipusDentitat = new JLabel("L'entitat és del tipus:  "+tipusE);
		lblTipusDentitat.setBounds(30, 10, 136, 15);
		getContentPane().add(lblTipusDentitat, "cell 0 4,alignx left,aligny center");
		
		//label id
		JLabel lblId = new JLabel("L'Id de l'entitat és:  "+idE);
		getContentPane().add(lblId, "cell 0 2");
		
		//boto cancelar
		JButton cancelButton = new JButton("D'acord");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(cancelButton, "cell 1 8,alignx left,aligny center");
	}
	
	
	/**
	 * Create the dialog.
	 */
	public VistaDetallsEntitat(CtrlPresentacio ctrl, JFrame owner, String id, String nom, String label, String tipus) {
		super(owner, true);
		this.setTitle("Detalls de l'entitat");
		
		this.nomE = nom;
		this.labelE = label;
		this.tipusE = tipus;
		this.idE = id;
		this.ctrl = ctrl;
		initComponents();
		int l = maxLength();
		this.setSize(330+7*l, 200);
		setVisible(true);
		setLocationRelativeTo(owner);
	}
	
	private int maxLength(){
		int l1 = idE.length();
		int l2 = nomE.length();
		int l3 = labelE.length();
		int l4 = tipusE.length();
		int la, lb;
		if(l1>l2) la = l1;
		else la = l2;
		if(l3>l4) lb = l3;
		else lb = l4;
		
		if(la>lb) return la;
		else return lb;
	}
}
