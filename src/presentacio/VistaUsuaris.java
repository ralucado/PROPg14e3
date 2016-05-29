package presentacio;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;

public class VistaUsuaris {

	private CtrlPresentacio ctrl;
	private JFrame frame;
	private UsersTableModel usersData;
	private JTable table;
	private JButton btnAfegirUsuari;
	private JButton btnModificarUsuari;
	private JButton btnEliminarUsuari;

	public VistaUsuaris(CtrlPresentacio ctrl) throws Exception {
		if (!ctrl.getDomini().esAdmin()) throw new Exception("No tens permís per accedir-hi!");
		this.ctrl = ctrl;
		this.usersData = new UsersTableModel();
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
				try {
					ctrl.getDomini().guardarCjtUsuaris();
				} catch (Exception exc) {
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("No s'ha pogut desar el conjunt d'usuaris", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
				ctrl.openMenu();
			}
		});
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new MigLayout("", "[452px,grow]", "[][218px,grow]"));
	}
	
	private void init_table() {
		table = new JTable(usersData);
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		JScrollPane scrollPane = new JScrollPane(table);
		frame.getContentPane().add(scrollPane, "cell 0 1,grow");
	}
	
	private void init_afegirUsuari() {
		btnAfegirUsuari = new JButton("Afegir usuari");
		btnAfegirUsuari.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnAfegirUsuari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VistaAfegirUsuari(ctrl, frame);
				System.out.println("Addició acabada");
				usersData.updateData();
			}
		});
		frame.getContentPane().add(btnAfegirUsuari, "flowx,cell 0 0,alignx right");
	}
	
	private void init_modificarUsuari() {
		btnModificarUsuari = new JButton("Modificar usuari");
		btnModificarUsuari.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnModificarUsuari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] botons = {"D'acord"};
				if (table.getSelectedRow() == -1)
					(new VistaDialog()).setDialog("Usuari no seleccionat", "No has seleccionat cap usuari a modificar!", botons, JOptionPane.WARNING_MESSAGE);
				else {
					new VistaModificarUsuari(ctrl, frame, usersData.getValueAt(table.getSelectedRow(), 0));
					System.out.println("Modificació acabada");
					usersData.updateData();
				}
			}
		});
		frame.getContentPane().add(btnModificarUsuari, "cell 0 0,alignx right");
	}
	
	private void init_eliminarUsuari() {
		btnEliminarUsuari = new JButton("Eliminar usuari");
		btnEliminarUsuari.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnEliminarUsuari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] botons = {"D'acord"};
				if (table.getSelectedRow() == -1)
					(new VistaDialog()).setDialog("Usuari no seleccionat", "No has seleccionat cap usuari a eliminar!", botons, JOptionPane.WARNING_MESSAGE);
				else {
					String user = usersData.getValueAt(table.getSelectedRow(), 0);
					if (user == ctrl.getDomini().getUsuariActual()) {
						(new VistaDialog()).setDialog("Operació no permesa", "No pots esborrar el teu usuari!", botons, JOptionPane.WARNING_MESSAGE);
					}
					else {
						try {
							String[] botonsConfirma = {"No", "Sí"};
							int isel = (new VistaDialog()).setDialog("Eliminant usuari", "Estàs segur que vols eliminar l'usuari "+user+"?", botonsConfirma, JOptionPane.YES_NO_OPTION);
							if (isel == 1) {
								ctrl.getDomini().baixaUsuari(usersData.getValueAt(table.getSelectedRow(), 0));
								usersData.updateData();
							}
						}
						catch (Exception exc) {
							(new VistaDialog()).setDialog("No s'ha pogut eliminar l'usuari", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		frame.getContentPane().add(btnEliminarUsuari, "cell 0 0,alignx right");
	}
	
	private void init() {
		init_frame();
		init_table();
		init_afegirUsuari();
		init_modificarUsuari();
		init_eliminarUsuari();
	}
	
	
	
	@SuppressWarnings("serial")
	private class UsersTableModel extends AbstractTableModel {
		protected String[] columnNames = {"Nom d'usuari", "és admin."};
		protected ArrayList<ArrayList<String>> data;
		
		public UsersTableModel() {
			this.data = new ArrayList<ArrayList<String>>();
			this.data = new ArrayList<ArrayList<String>>();
			TreeMap<String,String> d = new TreeMap<String,String>();
			try {
				Set<String> nomsUsuaris = ctrl.getDomini().consultarConjunt();
				for (String nom : nomsUsuaris) {
					System.out.println("EI");
					ArrayList<String> usuari = ctrl.getDomini().consultarUsuari(nom);
					d.put(usuari.get(0), usuari.get(1));
				}
			} catch (Exception e) {
				String[] botons = {"D'acord"};
				(new VistaDialog()).setDialog("Error al carregar usuaris", e.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
			}
			for (Map.Entry<String, String> u : d.entrySet()) {
				ArrayList<String> uu = new ArrayList<String>();
				uu.add(u.getKey()); uu.add(u.getValue());
				this.data.add(uu);
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
			this.data = new ArrayList<ArrayList<String>>();
			TreeMap<String,String> d = new TreeMap<String,String>();
			try {
				Set<String> nomsUsuaris = ctrl.getDomini().consultarConjunt();
				for (String nom : nomsUsuaris) {
					ArrayList<String> usuari = ctrl.getDomini().consultarUsuari(nom);
					d.put(usuari.get(0), usuari.get(1));
				}
			} catch (Exception e) {
				String[] botons = {"D'acord"};
				(new VistaDialog()).setDialog("Error al carregar usuaris", e.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
			}
			for (Map.Entry<String, String> u : d.entrySet()) {
				ArrayList<String> uu = new ArrayList<String>();
				uu.add(u.getKey()); uu.add(u.getValue());
				this.data.add(uu);
			}
			fireTableDataChanged();
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public String getValueAt(int rowIndex, int columnIndex) {
			return data.get(rowIndex).get(columnIndex);
		}
	}
}
