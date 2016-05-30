package presentacio;
import java.awt.Color;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;

public class VistaGestioEntitats{

	public JFrame frame;
	private String tipusActual;
	private CtrlPresentacio ctrl;
	private JTable table;
	private EntTableModel model;
	
	//botons
	private JButton btnAutors;
	private JButton btnConferencies;
	private JButton btnTermes;
	private JButton btnPapers;
	
	

	private JTextField textField;
	
	//sort
	private TableRowSorter<TableModel> rowSorter;

	public void setNoVisible(){
		frame.setVisible(false);
	}
	
	public void activa(){
		frame.setEnabled(true);
	}
	
	public void desactiva(){
		frame.setEnabled(false);
	}
	
	private void initComponents(){
		
		

		//frame
		frame = new JFrame();
		frame.setBackground(SystemColor.control);
		frame.setForeground(Color.BLACK);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new MigLayout("", "[62px,grow][62px,grow][62px,grow][62px,grow][][]", "[50px,grow][][][300px,grow][50px,grow][50px,grow][][][][][][][][]"));
		
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
				
		//Botó papers
		btnPapers = new JButton("Papers");
		frame.getContentPane().add(btnPapers, "cell 0 0,growx,aligny center");
		btnPapers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				btnPapers.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				btnConferencies.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				btnTermes.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				btnAutors.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				tipusActual = "Paper";
				model.updateData(tipusActual);
			}
		});
		btnPapers.setBounds(37, 22, 143, 25);
		btnPapers.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				
				//Botó autors
				btnAutors = new JButton("Autors");
				btnAutors.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						btnPapers.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						btnConferencies.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						btnTermes.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						btnAutors.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						tipusActual = "Autor";
						model.updateData(tipusActual);
					}
				});
				btnAutors.setBounds(37, 59, 143, 25);
				frame.getContentPane().add(btnAutors, "flowx,cell 1 0,growx,aligny center");
				btnAutors.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				
				//Botó termes
				btnTermes = new JButton("Termes");
				btnTermes.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						btnPapers.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						btnConferencies.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						btnTermes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						btnAutors.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tipusActual = "Terme";
						model.updateData(tipusActual);
					}
				});
				btnTermes.setBounds(37, 96, 143, 25);
				frame.getContentPane().add(btnTermes, "cell 2 0,growx,aligny center");
				btnTermes.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				
				
				
				
				
				//Botó conferencies
				btnConferencies = new JButton("Conferencies");
				btnConferencies.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						btnPapers.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						btnConferencies.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						btnTermes.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						btnAutors.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tipusActual = "Conferencia";
						model.updateData(tipusActual);
					}
				});
				btnConferencies.setBounds(37, 133, 143, 25);
				frame.getContentPane().add(btnConferencies, "cell 3 0,growx,aligny center");
				btnConferencies.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);				
				table.setFillsViewportHeight(true);
				
				scrollPane.setViewportView(table);
				frame.getContentPane().add(scrollPane, "cell 0 3 6 5,grow");
		
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
		frame.getContentPane().add(textField, "cell 0 8 6 1,growx");
		textField.setColumns(10);
		
		
		//sorter
		rowSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);
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
		
		
		//Botó modificar entitat
		JButton btnModificarEntitat = new JButton();
		btnModificarEntitat.setText("<html>Modificar<br />entitat</html>");
		btnModificarEntitat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row!=-1){
					row = table.convertRowIndexToModel(row);
					
					new VistaModificarEntitat(ctrl, frame, tipusActual, model.getValueAt(row, 1));
					
					model.updateData(tipusActual);
				}
				else{
					VistaDialog d = new VistaDialog();
					d.setDialog("Error", "Selecciona alguna entitat a modificar",new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificarEntitat.setBounds(37, 230, 146, 25);
		frame.getContentPane().add(btnModificarEntitat, "cell 2 23,grow");
		
		//Botó afegir entitat
		JButton btnAfegirEntitat = new JButton();
		btnAfegirEntitat.setText("<html>Afegir<br />entitat</html>");
		btnAfegirEntitat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VistaAfegirEntitat(ctrl, frame);
				model.updateData(tipusActual);
			}
		});
		btnAfegirEntitat.setBounds(37, 160, 143, 31);
		frame.getContentPane().add(btnAfegirEntitat, "cell 1 23,grow");
		

		
		
		//Botó esborrar entitat
		JButton btnEsborrarEntitat = new JButton();
		btnEsborrarEntitat.setText("<html>Esborrar<br />entitat</html>");
		btnEsborrarEntitat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row!=-1){
					row = table.convertRowIndexToModel(row);
					String entitat = model.getValueAt(row, 1);
					VistaDialog d = new VistaDialog();
					int k = d.setDialog("Confirmació", "Vols esborrar l'entitat "+entitat+" ?",new String[]{"Esborrar", "Cancelar"}, JOptionPane.QUESTION_MESSAGE);
					if(k==0){
						try {
							ctrl.getDomini().esborrarEntitat(entitat, tipusActual);
							model.updateData(tipusActual);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				}
				else{
					VistaDialog d = new VistaDialog();
					d.setDialog("Error", "Selecciona alguna entitat a esborrar",new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEsborrarEntitat.setBounds(37, 195, 143, 31);
		frame.getContentPane().add(btnEsborrarEntitat, "cell 3 23,grow");
		
		
	}
	

	
	/**
	 * Create the frame.
	 * @wbp.parser.entryPoint
	 */
	public VistaGestioEntitats(CtrlPresentacio ctrl, JFrame owner) {
		this.ctrl = ctrl;
		initComponents();
		
		//frame.setVisible(true);
		frame.setLocationRelativeTo(owner);
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
	
	boolean similar(String a, String b){
		int k = a.length();
		for(int i = 0; i+k<b.length(); i++){
			String subB = b.substring(i, i+k-1);
			if(subB.equals(a)) return true;
		}
		return false;
	}
	
	
	
}
