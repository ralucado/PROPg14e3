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
				ctrl.openLogIn();
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
				try {
					ctrl.getDomini().altaUsuari("nou1", "contrasenyaNou1");
					usersData.updateData();
				}
				catch (Exception exc) {
					VistaDialog dialog = new VistaDialog();
					String[] botons = {"D'acord"};
					dialog.setDialog("No s'ha pogut afegir l'usuari", exc.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(btnAfegirUsuari, "flowx,cell 0 0,alignx right");
	}
	
	private void init_modificarUsuari() {
		btnModificarUsuari = new JButton("Modificar usuari");
		btnModificarUsuari.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnModificarUsuari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		frame.getContentPane().add(btnModificarUsuari, "cell 0 0,alignx right");
	}
	
	private void init_eliminarUsuari() {
		btnEliminarUsuari = new JButton("Eliminar usuari");
		btnEliminarUsuari.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnEliminarUsuari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = usersData.getValueAt(table.getSelectedRow(), 0);
				VistaDialog dialog = new VistaDialog();
				if (user == ctrl.getDomini().getUsuariActual()) {
					String[] botons = {"D'acord"};
					dialog.setDialog("Operació no permesa", "No pots esborrar el teu usuari!", botons, JOptionPane.WARNING_MESSAGE);
				}
				else {
					try {
						String[] botonsConfirma = {"No", "Sí"};
						int isel = dialog.setDialog("Eliminant usuari", "Estàs segur que vols eliminar l'usuari "+user+"?", botonsConfirma, JOptionPane.YES_NO_OPTION);
						if (isel == 1) {
							ctrl.getDomini().baixaUsuari(usersData.getValueAt(table.getSelectedRow(), 0));
							usersData.updateData();
						}
					}
					catch (Exception exc) {
						String[] botons = {"D'acord"};
						dialog.setDialog("No s'ha pogut eliminar l'usuari", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
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
	
	
	
	@SuppressWarnings("unused")
	private class UsersTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		protected String[] columnNames = {"Nom d'usuari", "Contrasenya", "és admin."};
		protected ArrayList<ArrayList<String>> data;
		
		public UsersTableModel() {
			this.data = ctrl.getDomini().getDadesUsuaris();
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
			data = ctrl.getDomini().getDadesUsuaris();
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
