package presentacio.queries;

import java.awt.EventQueue;
import java.awt.Font;

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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import domini.queries.*;
import domini.graf.*;
import javax.swing.JPanel;
import javax.swing.table.TableRowSorter;

public class VistaResultatNew {

	private JFrame frame;
	private JTextField nEntitats;
	private CtrlPresentacio ctrl;
	private ResTableModel resData;
	private JTable table;
	private VistaQuery vQ;
	private TableRowSorter rowSorter;

	public VistaResultatNew(CtrlPresentacio ctrl, VistaQuery vQ) {
		this.ctrl = ctrl;
		this.vQ = vQ;
		this.resData = new ResTableModel();
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 744, 304);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				//ctrl.openMenu();
			}
		});
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new MigLayout("", "[100px:1000px,grow,left][][grow][][][][]", "[][][][][grow]"));
		
		//TABLE
		table = new JTable(resData);
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		JScrollPane scrollPane = new JScrollPane(table);
		frame.getContentPane().add(scrollPane, "cell 0 3 6 2,grow");
		//sorter
		rowSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);

		//nENTITATS
		nEntitats = new JTextField();
		frame.getContentPane().add(nEntitats, "cell 0 0,growx");
		nEntitats.setColumns(4);
		
		//FILTRA N
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
		frame.getContentPane().add(btnFiltran, "cell 1 0");
		
		//COMBOBOX
		String[] llista = {"","DATABASE","DATA_MINING","AI","INFORMATION_RETRIEVAL","UNKNOWN","NO_LABEL"};
		JComboBox comboBox = new JComboBox(llista);
		frame.getContentPane().add(comboBox, "cell 2 0,growx");
		
		//FILTRE LABEL
		JButton btnFiltrelabel = new JButton("FiltreLabel");
		btnFiltrelabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() != 0){
					try{ctrl.getDomini().filtrarResultatLabel(comboBox.getSelectedIndex()-1);}
					catch(Exception E){}
					resData.updateData();
				}
			}
		});
		frame.getContentPane().add(btnFiltrelabel, "cell 3 0");
		
		//DESFER FILTRE
		JButton btnDesferfiltre = new JButton("Desfer Filtre");
		btnDesferfiltre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{ctrl.getDomini().resultatDesferFiltre();}catch(Exception E){};
				resData.updateData();
			}
		});
		frame.getContentPane().add(btnDesferfiltre, "cell 4 0");
		
		//NETEJAR FILTRES
		JButton btnNetejaFiltres = new JButton("Neteja Filtres");
		btnNetejaFiltres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{ctrl.getDomini().netejaFiltresResultat();}catch(Exception E){}
				resData.updateData();
			}
		});
		frame.getContentPane().add(btnNetejaFiltres, "cell 5 0");
		
		//DESAR CAMI
		JButton btnDesarCami = new JButton("Desar Cami");
		btnDesarCami.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new VistaDescripcio(ctrl, vQ.pathCami);
			}
		});
		frame.getContentPane().add(btnDesarCami, "cell 6 0");
		
		JLabel lblCami = new JLabel("Cami: ");
		frame.getContentPane().add(lblCami, "cell 1 1");
		
		JLabel label = new JLabel("");
		frame.getContentPane().add(label, "cell 2 1");
		label.setText(vQ.pathCami);
		
	}
	
	private void init_frame() {
		frame = new JFrame();
		frame.setSize(600, 400);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				try {
					ctrl.getDomini().guardarCamins(ctrl.getDomini().getUsuariActual());
				} catch (Exception exc) {
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("No s'ha pogut desar el conjunt de camins", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
				ctrl.openMenu();
			}
		});
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new MigLayout("", "[452px,grow]", "[][218px,grow]"));
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
