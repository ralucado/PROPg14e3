package presentacio.graf;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;

public class VistaGestioGraf{

	private JFrame frame;
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
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				ctrl.openMenu();
				
			}
		});
		frame.setLocationRelativeTo(null);
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
	 * Creadora de VistaGestioGraf
	 * @param ctrl Controlador de presentació
	 */
	public VistaGestioGraf(CtrlPresentacio ctrl) {
		this.ctrl = ctrl;
		initComponents();	
		this.frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

}
