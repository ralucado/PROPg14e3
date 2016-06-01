package presentacio.queries;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;

@SuppressWarnings("serial")
public class VistaResultatClustering extends JDialog{

	private VistaQuery vQ;
	private ClusterTableModel clusterData;
	private JTable table_1;

	/**
	 * Create the application.
	 */
	public VistaResultatClustering(CtrlPresentacio ctrl, JFrame parent, VistaQuery vq) {
		super(parent,true);
		this.vQ = vq;
		getContentPane().setLayout(new MigLayout("", "[][438px,grow][]", "[25px][229px,grow][]"));
		clusterData = new ClusterTableModel();
		
		table_1 = new JTable(clusterData);
		table_1.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		JScrollPane scrollPane = new JScrollPane(table_1);
		getContentPane().add(scrollPane, "cell 1 1,grow");
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
