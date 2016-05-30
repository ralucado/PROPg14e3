package presentacio.camins;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

public class VistaCamins {

	private CtrlPresentacio ctrl;
	private JFrame frame;
	private CaminsTableModel caminsData;
	private JTable table;
	private JButton btnAfegirCami;
	private JButton btnModificarCami;
	private JButton btnEliminarCami;
	private ArrayList<String[]> predefinits;
	
	public VistaCamins(CtrlPresentacio ctrl) throws Exception {
		this.ctrl = ctrl;
		this.caminsData = new CaminsTableModel();
		init();
		frame.setVisible(true);
		predefinits = ctrl.getDomini().consultarCaminsPredefinits();
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
	
	private void init_table() {
		table = new JTable(caminsData);
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		JScrollPane scrollPane = new JScrollPane(table);
		frame.getContentPane().add(scrollPane, "cell 0 1,grow");
	}
	
	private void init_afegirCami() {
		btnAfegirCami = new JButton("Afegir cami");
		btnAfegirCami.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnAfegirCami.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VistaAfegirCami(ctrl, frame);
				System.out.println("Addició acabada");
				caminsData.updateData();
			}
		});
		frame.getContentPane().add(btnAfegirCami, "flowx,cell 0 0,alignx right");
	}
	
	private void init_modificarCami() {
		btnModificarCami = new JButton("Modificar cami");
		btnModificarCami.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnModificarCami.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("clicat boto modifica cami");
				String[] botons = {"D'acord"};
				if (table.getSelectedRow() == -1)
					(new VistaDialog()).setDialog("Cami no seleccionat", "No has seleccionat cap cami a modificar!", botons, JOptionPane.WARNING_MESSAGE);
				else {
					boolean b = false;
					String camiSeleccionat = caminsData.getValueAt(table.getSelectedRow(), 0);
					for (int i = 0; i < predefinits.size(); ++i){
						if (predefinits.get(i)[0] == camiSeleccionat) b = true;
					}
					if (b){
						(new VistaDialog()).setDialog("Cami no editable", "No pots editar aquest camí!", botons, JOptionPane.WARNING_MESSAGE);
					}
					else{
						new VistaModificarCami(ctrl, frame, camiSeleccionat);
						System.out.println("Modificació acabada");
						caminsData.updateData();
					}
				}
			}
		});
		frame.getContentPane().add(btnModificarCami, "cell 0 0,alignx right");
	}
	
	private void init_eliminarCami() {
		btnEliminarCami = new JButton("Eliminar cami");
		btnEliminarCami.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnEliminarCami.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] botons = {"D'acord"};
				if (table.getSelectedRow() == -1)
					(new VistaDialog()).setDialog("Cami no seleccionat", "No has seleccionat cap cami a eliminar!", botons, JOptionPane.WARNING_MESSAGE);
				else {
					String nom = caminsData.getValueAt(table.getSelectedRow(), 0);
					boolean b = false;
					for (int i = 0; i < predefinits.size(); ++i){
						if (predefinits.get(i)[0] == nom) b = true;
					}
					if (b){
						(new VistaDialog()).setDialog("Cami no editable", "No pots eliminar aquest camí!", botons, JOptionPane.WARNING_MESSAGE);
					}
					else{
						try {
							String[] botonsConfirma = {"No", "Sí"};
							int isel = (new VistaDialog()).setDialog("Eliminant cami", "Estàs segur que vols eliminar el cami "+nom+"?", botonsConfirma, JOptionPane.YES_NO_OPTION);
							if (isel == 1) {
								ctrl.getDomini().esborrarCami(caminsData.getValueAt(table.getSelectedRow(), 0));
								caminsData.updateData();
							}
						}
						catch (Exception exc) {
							(new VistaDialog()).setDialog("No s'ha pogut eliminar el cami", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
						}
					}	
				}
			}
		});
		frame.getContentPane().add(btnEliminarCami, "cell 0 0,alignx right");
	}
	
	private void init() {
		init_frame();
		init_table();
		init_afegirCami();
		init_modificarCami();
		init_eliminarCami();
	}
	
	
	
	private class CaminsTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		protected String[] columnNames = {"Nom", "Path", "Descripció"};
		protected ArrayList<String[]> data;
		
		public CaminsTableModel() {
			try {
				this.data = ctrl.getDomini().consultarCaminsPredefinits();
				this.data.addAll(ctrl.getDomini().consultarCaminsUsuari());

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
		
		public void updateData() {
			try {
				this.data = ctrl.getDomini().consultarCaminsPredefinits();
				this.data.addAll(ctrl.getDomini().consultarCaminsUsuari());

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			fireTableDataChanged();
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public String getValueAt(int rowIndex, int columnIndex) {
			return data.get(rowIndex)[columnIndex];
		}
	}
}
