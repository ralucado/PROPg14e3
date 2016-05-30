package presentacio.graf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import domini.graf.Entitat;
import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

public class VistaEntitatsDialog extends JDialog {

	private JFrame frame;
	private CtrlPresentacio ctrl;
	private JTable table;
	private EntTableModel model;
	private TableRowSorter<TableModel> rowSorter;
	private String tipusActual;
	private JTextField textField;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private ArrayList<String> ent;

	
	private void initComponents(){
		//frame
		frame = new JFrame();
		setTitle("Seleccionar entitat");
		setBackground(SystemColor.control);
		setForeground(Color.BLACK);
		setSize(600, 400);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new MigLayout("", "[62px,grow][62px,grow][62px,grow][62px,grow][][]", "[50px,grow][][][300px,grow][50px,grow][50px,grow][][][][][][][][][][][][]"));
		
		//taula / scrollpane
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(205, 10, 200, 250);
				model = new EntTableModel();
				table = new JTable(model);
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount()==2 && table.getSelectedRow()!=-1){
							String id = (String) table.getValueAt(table.getSelectedRow(), 0);
							String nom = (String) table.getValueAt(table.getSelectedRow(), 1);
							if(tipusActual=="Terme") new VistaDetallsEntitat(ctrl, frame, id, nom, "", tipusActual);
							else{
								String label = (String) table.getValueAt(table.getSelectedRow(),2);
								new VistaDetallsEntitat(ctrl, frame, id, nom, label, tipusActual);
							}
						}
					}
				});
				
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);				
				table.setFillsViewportHeight(true);
				
				scrollPane.setViewportView(table);
				getContentPane().add(scrollPane, "cell 0 0 6 10,grow");
		
		
		//sorter
		rowSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);
		
		btnNewButton = new JButton("Acceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row!=-1){
					row = table.convertRowIndexToModel(row);
					ent.add(model.getValueAt(row, 1));
					ent.add(tipusActual);
					dispose();
				}
				else{
					VistaDialog d = new VistaDialog();
					d.setDialog("Error", "Selecciona alguna entitat",new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//barra cerca
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String text = textField.getText();
				  if (text.trim().length() == 0) {
				     rowSorter.setRowFilter(null);
				  } else {
				     rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				  }
			}
		});
		getContentPane().add(textField, "cell 0 13 6 1,growx");
		textField.setColumns(10);
		textField.getDocument().addDocumentListener(new DocumentListener(){
	
			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = textField.getText();
	
				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}
	
			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = textField.getText();
	
				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}
	
			@Override
			public void changedUpdate(DocumentEvent e) {
				String text = textField.getText();
				
				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}
		});
		getContentPane().add(btnNewButton, "cell 1 15,grow");
		
		
		btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnNewButton_1, "cell 3 15,grow");
			
	}
	
	public VistaEntitatsDialog(CtrlPresentacio ctrl, JFrame owner, String tipusE, ArrayList<String> e) {
		super(owner, true);
		this.ctrl = ctrl;
		this.tipusActual = tipusE;
		this.ent = e;
		initComponents();
		model.updateData(tipusActual);
		setVisible(true);
	}
	
	private class EntTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		protected String[] columnNames = {"Id", "Nom", "Label"};
		protected ArrayList<ArrayList<String>> data;
		
		public EntTableModel() {
			this.data = new ArrayList<ArrayList<String>>();
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
		

		public void updateData(String tipus) {
			try{
				ArrayList<ArrayList<String>> aux;
				if(tipus=="Autor"){
					aux = ctrl.getDomini().consultarAutorsExt();
					Collections.sort(aux, new CustomComparator());
					columnNames = new String[]{"Id", "Nom", "Label"};
					 rowSorter.setRowFilter(null);
					fireTableStructureChanged();
					this.data = aux;	
					 textField.setText("");
				}
				else if(tipus=="Conferencia"){
					aux = ctrl.getDomini().consultarConferenciesExt();
					Collections.sort(aux, new CustomComparator());
					columnNames = new String[]{"Id", "Nom", "Label"};
					 rowSorter.setRowFilter(null);
					fireTableStructureChanged();
					this.data = aux;
					 textField.setText("");
				}
				else if(tipus=="Paper"){
					aux = ctrl.getDomini().consultarPapersExt();
					Collections.sort(aux, new CustomComparator());
					columnNames = new String[]{"Id", "Nom", "Label"};
					 rowSorter.setRowFilter(null);
					fireTableStructureChanged();
 					this.data = aux;
 					textField.setText("");
				}
				else if(tipus=="Terme"){
					aux = ctrl.getDomini().consultarTermesExt();
					Collections.sort(aux, new CustomComparator());	
					columnNames = new String[]{"Id", "Nom"};
					 rowSorter.setRowFilter(null);
					fireTableStructureChanged();
					this.data = aux;
				
					 textField.setText("");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			fireTableDataChanged();
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public String getValueAt(int rowIndex, int columnIndex) {
			return data.get(rowIndex).get(columnIndex);
		}
		
		private class CustomComparator implements Comparator<ArrayList<String>> {
            @Override
            public int compare(ArrayList<String> a1, ArrayList<String> a2) {
               return a1.get(0).compareTo(a2.get(0));
            }
        }
	
	}

}
