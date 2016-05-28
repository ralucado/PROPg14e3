package presentacio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import domini.queries.*;
import domini.graf.*;

public class VistaResultat {

	public JFrame frame;
	private CtrlPresentacio ctrl;
	private JTextField nEntitats;
	private JTable resultats;
	private ResTableModel resData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		CtrlPresentacio ctrlPresentacio = new CtrlPresentacio();
		
		ArrayList<Pair<Entitat,Float>> A = new ArrayList<Pair<Entitat,Float>>();
		Autor au = new Autor("Burlao");
		Pair<Entitat,Float> p = new Pair<Entitat,Float>(au,0F);
		A.add(p);
		Resultat R = new Resultat(A);
		ctrlPresentacio.getDomini().getCtrlQueries().setResultat(R);
		ctrlPresentacio.openResultat();
		
		//VistaResultat window = new VistaResultat(ctrlPresentacio);
		//window.frame.setVisible(true);
			
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public VistaResultat(CtrlPresentacio ctrl) {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow][][][][][grow][][][][grow]", "[][][][][grow]"));
		JButton btnEnrere = new JButton("Enrere");
		frame.getContentPane().add(btnEnrere, "cell 0 0,alignx left,aligny top");
		btnEnrere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		
		String[] llista = {"","0","1","2","3","4"};
		JComboBox cmbFiltreLabel = new JComboBox(llista);
		frame.getContentPane().add(cmbFiltreLabel, "cell 2 0,growx");
		
		JButton FiltreLabel = new JButton("FiltreLabel");
		frame.getContentPane().add(FiltreLabel, "cell 3 0,alignx left");
		
		nEntitats = new JTextField();
		frame.getContentPane().add(nEntitats, "cell 4 0 2 1,growx");
		nEntitats.setColumns(2);
		
		JButton btnFiltran = new JButton("FiltraN");
		btnFiltran.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (! nEntitats.getText().equals("")){
					ctrl.getDomini().getCtrlQueries().getResultat().filtrarN(Integer.parseInt(nEntitats.getText()));
					resData.updateData();
				}
			}
		});
		frame.getContentPane().add(btnFiltran, "cell 6 0,alignx right,aligny top");
		
		JButton NetejaFiltres = new JButton("Neteja Filtres");
		NetejaFiltres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.getDomini().getCtrlQueries().getResultat().netejaFiltres();
				resData.updateData();
			}
		});
		frame.getContentPane().add(NetejaFiltres, "cell 7 0,alignx right,aligny top");
		
		resultats = new JTable(resData);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 1 8 4,grow");
		//scrollPane.setViewportView(resultats);
		
		frame.setVisible(true);
	}
	
	private class ResTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		protected String[] columnNames = {"Entitat", "Label", "Hetesim"};
		protected String[][] data;
		
		public ResTableModel() {
			this.data = ctrl.getDomini().getCtrlQueries().getResultat().getDadesString();
		}
		
		public String getColumnName(int column) {
			return columnNames[column];
		}
		
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		public int getRowCount() {
			return data.length;
		}
		
		public void updateData() {
			data = ctrl.getDomini().getCtrlQueries().getResultat().getDadesString();
			fireTableDataChanged();
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public String getValueAt(int fila, int columna) {
			return data[fila][columna];
		}
	}
}
