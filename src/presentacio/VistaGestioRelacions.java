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

public class VistaGestioRelacions{

	public JFrame frame;
	private JTable table;
	private CtrlPresentacio ctrl;
	private String tipusE1;
	private String nomE1;
	private String nomE2;
	private RelTableModel model;
	private JTextField textField;
	
	//sort
	private TableRowSorter<TableModel> rowSorter;
	
	//botons
	private JButton btnPA;
	private JButton btnPC;
	private JButton btnPT;

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
		frame.setLocationRelativeTo(null);
		frame.setSize(800, 600);
		frame.getContentPane().setLayout(new MigLayout("", "[62px][62px,grow][62px,grow][62px,grow][][][]", "[50px][][350px,grow][][][][][][]"));
		
		//taula
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(201, 26, 225, 212);
		frame.getContentPane().add(scrollPane, "cell 0 2 6 4,grow");
		
		model = new RelTableModel();
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2 && table.getSelectedRow()!=-1){
					String e1 = (String) table.getValueAt(table.getSelectedRow(), 0);
					String e2 = (String) table.getValueAt(table.getSelectedRow(), 1);

					new VistaDetallsRelacio(ctrl, frame, e1, e2, "Paper", tipusE1);
				}
			}
		});							
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
					
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
				frame.getContentPane().add(textField, "cell 0 6 6 1,growx");
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
		
		//botó P-A
		btnPA = new JButton("Relacions P-A");
		btnPA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPA.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				btnPT.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				btnPC.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				model.updateData("PA");
				tipusE1 = "Autor";
			}
		});
		btnPA.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		/*//enrere
		JButton button = new JButton(".");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GestioGraf(ctrl, frame);
				frame.dispose();
			}
		});
		
		button.setBounds(12, 4, 39, 13);
		frame.getContentPane().add(button, "cell 0 0,alignx center");*/
		
		btnPA.setBounds(37, 22, 143, 25);
		frame.getContentPane().add(btnPA, "cell 1 0,alignx center");
		
		//botó P-C
		btnPC = new JButton("Relacions P-C");
		btnPC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPC.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				btnPT.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				btnPA.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				model.updateData("PC");
				tipusE1 = "Conferencia";
			}
		});
		btnPC.setBounds(37, 59, 143, 25);
		frame.getContentPane().add(btnPC, "cell 2 0,alignx center");
		btnPC.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		//botó P-T
		btnPT = new JButton("Relacions P-T");
		btnPT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPT.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				btnPC.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				btnPA.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				model.updateData("PT");
				tipusE1 = "Terme";
			}
		});
		btnPT.setBounds(37, 96, 143, 25);
		frame.getContentPane().add(btnPT, "cell 3 0,alignx center");

		btnPT.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		
			//botó afegir relació
			JButton btnAfegirRelacio = new JButton("Afegir Relació");
			btnAfegirRelacio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new VistaAfegirRelacio(ctrl, frame);
					model.updateData(tipusE1);
				}
			});
			btnAfegirRelacio.setBounds(37, 186, 143, 58);
			frame.getContentPane().add(btnAfegirRelacio, "cell 1 9,alignx center");
			
			
			
			//botó esborrar relacio
			JButton btnNewButton = new JButton("Esborrar Relació");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.getSelectedRow();
					if(row!=-1){
						row = table.convertRowIndexToModel(row);
						nomE1 = model.getValueAt(row, 0);
						nomE2 = model.getValueAt(row, 1);
						VistaDialog d = new VistaDialog();
						int k = d.setDialog("Confirmació", "Vols esborrar la relació '"+nomE1+" - "+nomE2+"' ?",new String[]{"Esborrar", "Cancelar"}, JOptionPane.QUESTION_MESSAGE);
						if(k==0){
							try {
								
								if(tipusE1=="Autor"){
									System.out.println(nomE1+" "+nomE2);
									ctrl.getDomini().esborrarRelacioAP(nomE2, nomE1);
								}
								else if(tipusE1=="Conferencia") ctrl.getDomini().esborrarRelacioCP(nomE2, nomE1);
								else if(tipusE1=="Terme") ctrl.getDomini().esborrarRelacioTP(nomE2, nomE1);
								
								model.updateData(tipusE1);
								
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
					}
					else{
						VistaDialog d = new VistaDialog();
						d.setDialog("Error", "Selecciona alguna relació a esborrar",new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
					}
				
				}
			});
			btnNewButton.setBounds(30, 133, 150, 41);
			frame.getContentPane().add(btnNewButton, "cell 2 9,alignx center");
		
	}
	/**
	 * Create the frame.
	 */
	public VistaGestioRelacions(CtrlPresentacio ctrl, JFrame owner) {
		this.ctrl = ctrl;
		initComponents();
		//frame.setVisible(true);
		frame.setLocationRelativeTo(owner);
	}
	
	private class RelTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		protected String[] columnNames = {"Entitat 1", "Entitat 2"};
		protected ArrayList<ArrayList<String>> data;
		
		public RelTableModel() {
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
				if(tipus == "PA" || tipus=="Autor"){
					aux = ctrl.getDomini().consultarRelacionsAP();
					Collections.sort(aux, new CustomComparator());
					 rowSorter.setRowFilter(null);
					fireTableStructureChanged();
					this.data = aux;	
					 textField.setText("");
				}
				else if(tipus=="PC" || tipus=="Conferencia"){
					aux = ctrl.getDomini().consultarRelacionsCP();
					Collections.sort(aux, new CustomComparator());
					 rowSorter.setRowFilter(null);
					fireTableStructureChanged();
					this.data = aux;
					 textField.setText("");
				}
				else if(tipus=="PT" || tipus=="Terme"){
					aux = ctrl.getDomini().consultarRelacionsTP();
					Collections.sort(aux, new CustomComparator());
					 rowSorter.setRowFilter(null);
					fireTableStructureChanged();
					this.data = aux;
					 textField.setText("");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public String getValueAt(int rowIndex, int columnIndex) {
			return data.get(rowIndex).get(columnIndex);
		}
	}
		private class CustomComparator implements Comparator<ArrayList<String>> {
            @Override
            public int compare(ArrayList<String> a1, ArrayList<String> a2) {
               return a1.get(0).compareTo(a2.get(0));
            }
        }
}

