package presentacio;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;

public class VistaMenu {

	private CtrlPresentacio ctrl;
	private JFrame frame;
	private JButton btnCamins;
	private JButton btnUsuaris;
	private JButton btnQuery;
	private JButton btnTancaSessi;
	public VistaMenu(CtrlPresentacio ctrl) throws Exception {
		this.ctrl = ctrl;
		init();
		frame.setVisible(true);
	}
	
	public void fesVisible() {
		frame.setVisible(true);
	}
	
	private void init_frame() {
		frame = new JFrame();
		frame.setSize(600, 400);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				ctrl.openLogIn();
			}
		});
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new MigLayout("", "[][][][][][grow][][grow][]", "[grow][][grow][][grow][][grow][][grow]"));
				
	}
	
	private void init() {
		init_frame();
		init_usuaris();
		init_camins();
		init_query();
		init_tancaSessi();
	}

	private void init_tancaSessi() {
		btnTancaSessi = new JButton("Tanca sessió");
		btnTancaSessi.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnTancaSessi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				ctrl.openLogIn();

			}
		});
		frame.getContentPane().add(btnTancaSessi, "cell 6 7,alignx center");
	}

	private void init_query() {
		btnQuery = new JButton("Query");
		btnQuery.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		frame.getContentPane().add(btnUsuaris, "cell 6 1,alignx center");
	}
}