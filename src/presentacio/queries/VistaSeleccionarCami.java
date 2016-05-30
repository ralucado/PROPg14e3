package presentacio.queries;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

@SuppressWarnings("serial")
public class VistaSeleccionarCami extends JDialog{

	private CtrlPresentacio ctrl;
	private VistaQuery vq;
	private CaminsTableModel caminsData;
	private JTable table;
	private JButton btnSeleccionar;
	private JButton btnCancelar;
	
	public VistaSeleccionarCami(CtrlPresentacio ctrl, JFrame parent, VistaQuery vq) throws Exception {
		super(parent, true);
		this.ctrl = ctrl;
		this.vq = vq;
		this.caminsData = new CaminsTableModel();
		init();
		setVisible(true);
	}

	private void init_table() {
		getContentPane().setLayout(new MigLayout("", "[][438px,grow][]", "[25px][229px,grow][]"));
		table = new JTable(caminsData);
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, "cell 0 0 3 2,grow");
	}
	
	private void init_Seleccionar() {
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("clicat boto selecciona cami");
				String[] botons = {"D'acord"};
				if (table.getSelectedRow() == -1)
					(new VistaDialog()).setDialog("Cami no seleccionat", "No has seleccionat cap cami!", botons, JOptionPane.WARNING_MESSAGE);
				else {
					vq.setCami(caminsData.getValueAt(table.getSelectedRow(), 0));
					dispose();
				}
			}
		});
		getContentPane().add(btnSeleccionar, "cell 2 2,alignx center,aligny center");
	}

	private void init_Cancelar(){
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnCancelar, "cell 0 2,alignx center,aligny center");
	}
	
	private void init() {
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		init_table();
		init_Seleccionar();
		init_Cancelar();
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

		public int getColumnCount() {
			return columnNames.length;
		}

		public String getValueAt(int rowIndex, int columnIndex) {
			return data.get(rowIndex)[columnIndex];
		}
	}
}
