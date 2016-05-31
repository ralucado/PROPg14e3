package presentacio.queries;

import java.awt.EventQueue;
import presentacio.ctrl.*;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VistaResultatClustering {

	private JFrame frame;
	private JTable table;
	private CtrlPresentacio ctrl;

	/**
	 * Create the application.
	 */
	public VistaResultatClustering(CtrlPresentacio ctrl) {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				ctrl.openMenu();
			}
		});
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}

}
