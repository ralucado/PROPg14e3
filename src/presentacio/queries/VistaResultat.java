package presentacio.queries;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private JFrame parent;

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
		//ctrlPresentacio.getDomini().getCtrlQueries().setResultat(R);
		
		//VistaResultat window = new VistaResultat(ctrlPresentacio);
		//window.frame.setVisible(true);
			
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public VistaResultat(CtrlPresentacio ctrl, JFrame parent) {
		this.parent = parent;
		initialize();
		this.resData = new ResTableModel();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 570, 300);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				try {
					ctrl.getDomini().guardarCjtUsuaris();
				} catch (Exception exc) {
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("No s'ha pogut desar el conjunt d'usuaris", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
				ctrl.openMenu();
			}
		});
		frame.getContentPane().setLayout(new MigLayout("", "[grow][][][30px:n][][][][][][][][grow][][][][][grow]", "[][][][][grow]"));
		
		String[] llista = {"","0","1","2","3","4"};
		JComboBox cmbFiltreLabel = new JComboBox(llista);
		frame.getContentPane().add(cmbFiltreLabel, "cell 1 0,growx");
		
		JButton FiltreLabel = new JButton("FiltreLabel");
		frame.getContentPane().add(FiltreLabel, "cell 2 0,alignx left");
		
		nEntitats = new JTextField();
		frame.getContentPane().add(nEntitats, "cell 3 0,growx");
		nEntitats.setColumns(4);
		
		JButton btnFiltran = new JButton("FiltraN");
		btnFiltran.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (! nEntitats.getText().equals("")){
					try{
						ctrl.getDomini().filtrarResultatN(Integer.parseInt(nEntitats.getText()));
					}catch(Exception E){}
					resData.updateData();
				}
			}
		});
		frame.getContentPane().add(btnFiltran, "cell 4 0,alignx right,aligny top");
		
		JButton btnDesferFiltre = new JButton("Desfer Filtre");
		btnDesferFiltre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{ctrl.getDomini().resultatDesferFiltre();}catch(Exception E){};
				resData.updateData();
			}
		});
		frame.getContentPane().add(btnDesferFiltre, "cell 5 0");
		
		resultats = new JTable(resData);
		
		JButton NetejaFiltres = new JButton("Neteja Filtres");
		NetejaFiltres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{ctrl.getDomini().netejaFiltresResultat();}catch(Exception E){}
				resData.updateData();
			}
		});
		frame.getContentPane().add(NetejaFiltres, "cell 6 0,alignx right,aligny top");
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 1 15 4,grow");
		//scrollPane.setViewportView(resultats);
		
		frame.setVisible(true);
	}
	
	private class ResTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		protected String[] columnNames = {"Entitat", "Label", "Hetesim"};
		protected String[][] data;
		
		public ResTableModel() {
			try{this.data = ctrl.getDomini().getDadesNormal();}catch(Exception E){}
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
			try{this.data = ctrl.getDomini().getDadesNormal();}catch(Exception E){}
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