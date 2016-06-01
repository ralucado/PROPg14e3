package presentacio.queries;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

public class VistaResultatClustering extends JDialog{

	private JFrame frame;
	//private JTable table;
	private CtrlPresentacio ctrl;
	private VistaQuery vQ;
	private ClusterTableModel clusterData;
	private JTable table_1;

	/**
	 * Create the application.
	 */
	public VistaResultatClustering(CtrlPresentacio ctrl, JFrame parent, VistaQuery vq) {
		super(parent,true);
		this.ctrl = ctrl;
		this.vQ = vq;
		getContentPane().setLayout(new MigLayout("", "[][][][][][][grow]", "[][][][][grow]"));
		clusterData = new ClusterTableModel();
		
		table_1 = new JTable(clusterData);
		table_1.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		JScrollPane scrollPane = new JScrollPane(table_1);
		getContentPane().add(scrollPane, "grow");
		scrollPane.setViewportView(table_1);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	private class ClusterTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		protected String[] columnNames = {"Nom"};
		protected ArrayList<String> data;
		
		public ClusterTableModel() {
			try {
				this.data = vQ.getData();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		
		public String getColumnName(int column) {
			return columnNames[column];
		}
	
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		public int getRowCount() {
			return data.size();
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public String getValueAt(int rowIndex, int columnIndex) {
			return data.get(rowIndex);
		}
	}
}
