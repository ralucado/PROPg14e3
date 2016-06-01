package presentacio.ctrl;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

/**
 * És la vista del menú principal del programa.
 * @author Cristina Raluca Vijulie
 */
public class VistaMenu {

	private CtrlPresentacio ctrl;
	private JFrame frame;
	private JButton btnCamins;
	private JButton btnUsuaris;
	private JButton btnQuery;
	private JButton btnTancaSessi;
	private JButton btnGraf;
	private JLabel lblNewLabel;
	
	/**
	 * Crea una nova VistaMenu
	 * @param ctrl controlador de presentació
	 * @throws Exception ...
	 */
	public VistaMenu(CtrlPresentacio ctrl) throws Exception {
		this.ctrl = ctrl;
		init();
		frame.setVisible(true);
	}
	
	/**
	 * Fa visible la vista.
	 */
	public void fesVisible() {
		frame.setVisible(true);
	}
	
	private void init_frame() {
		frame = new JFrame();
		frame.setSize(384, 298);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				try {
					ctrl.getDomini().logOut();
				}
				catch (Exception e1) {
					String[] botons = {"D'acord"};
	      			(new VistaDialog()).setDialog("No s'ha pogut tancar sessió", e1.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
				}
				ctrl.openLogIn();
			}
		});
		frame.setTitle("PathSearcher");
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new MigLayout("", "[][][][][85.00][grow][]", "[grow][][grow][][grow][][grow][][grow][][grow]"));
				
	}
	
	private void init() {
		init_frame();
		init_usuaris();
		init_camins();
		init_query();
		init_graf();
		init_tancaSessi();
	}

	private void init_tancaSessi() {
		btnTancaSessi = new JButton("Tanca sessió");
		btnTancaSessi.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnTancaSessi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					ctrl.getDomini().logOut();
				} catch (Exception e1) {
					String[] botons = {"D'acord"};
	      			(new VistaDialog()).setDialog("No s'ha pogut tancar sessió", e1.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
				}
				ctrl.openLogIn();
				
			}
		});
		frame.getContentPane().add(btnTancaSessi, "cell 6 9,alignx center");
	}
	
	private void init_graf(){
		btnGraf = new JButton("Graf");
		btnGraf.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnQuery.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnGraf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				ctrl.openGraf();
			}
		});
		frame.getContentPane().add(btnGraf, "cell 6 7,alignx center");

	}

	private void init_query() {
		btnQuery = new JButton("Query");
		btnQuery.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				ctrl.openQuery();
			}
		});
		frame.getContentPane().add(btnQuery, "cell 6 5,alignx center");

	}

	private void init_camins() {
		btnCamins = new JButton("Camins");
		btnCamins.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnCamins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				ctrl.openCamins();
			}
		});
		frame.getContentPane().add(btnCamins, "cell 6 3,alignx center");
	}

	private void init_usuaris() {
		btnUsuaris = new JButton("Usuaris");
		btnUsuaris.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		if (!ctrl.getDomini().esAdmin()) btnUsuaris.setEnabled(false);
		btnUsuaris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				ctrl.openUsuaris();
			}
		});
		{
			try {BufferedImage logo = ImageIO.read(new File("logo.png"));
			lblNewLabel = new JLabel(new ImageIcon(logo));
			frame.getContentPane().add(lblNewLabel, "cell 0 1 5 9");
			}
			catch (Exception e) {String[] botons = {"D'acord"};
  			(new VistaDialog()).setDialog("Error al iniciar components", e.getMessage(), botons, JOptionPane.WARNING_MESSAGE);}
		}
		frame.getContentPane().add(btnUsuaris, "cell 6 1,alignx center");
	}
}
