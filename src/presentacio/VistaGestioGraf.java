package presentacio;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.SpringLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTabbedPane;

public class VistaGestioGraf{

	private JFrame frame;
	private JButton btnGestionarEntitats;
	private JButton btnGestionarRelacions;
	private CtrlPresentacio ctrl;

	public void fesVisible(){
		frame.setVisible(true);
	}
	
	public void activa(){
		frame.setEnabled(true);
	}
	
	public void desactiva(){
		frame.setEnabled(false);
	}
	
	private void initComponents(){
		//frame
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.control);
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[400px,grow]", "[10px][][400px,grow]"));
		
		//label titol
		JLabel titol = new JLabel("Gestió del graf");
		titol.setFont(new Font("Dialog", Font.BOLD, 16));
		titol.setHorizontalAlignment(SwingConstants.CENTER);
		titol.setBounds(10, 0, 50, 5);
		frame.getContentPane().add(titol, "cell 0 1,growx");
		
		//tabs
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, "flowx,cell 0 2,grow");
		
		VistaGestioEntitats ge = new VistaGestioEntitats(ctrl, null);
		//ge.setNoVisible();
		
		tabbedPane.addTab("Entitats", ge.frame.getContentPane());
		
		VistaGestioRelacions gr = new VistaGestioRelacions(ctrl, null);
		//gr.setNoVisible();
		
		tabbedPane.addTab("Relacions", gr.frame.getContentPane());
		
		
	}

	/**
	 * Create the frame.
	 */
	public VistaGestioGraf(CtrlPresentacio ctrl, JFrame owner) {
		this.ctrl = ctrl;
		initComponents();	
		this.frame.setVisible(true);
		frame.setLocationRelativeTo(owner);
	}

}

