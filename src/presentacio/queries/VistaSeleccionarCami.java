package presentacio.queries;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

@SuppressWarnings("serial")
public class VistaSeleccionarCami extends JDialog{

	private CtrlPresentacio ctrl;
	private VistaQuery vq;
	private String camins[];
	private JTable table;
	private JButton btnSeleccionar;
	private JButton btnCancelar;
	String nom;
	
	public VistaSeleccionarCami(CtrlPresentacio ctrl, JFrame parent, VistaQuery vq) throws Exception {
		super(parent, true);
		this.ctrl = ctrl;
		this.vq = vq;
		importarCamins();
		init();
		setVisible(true);

	}

	private void importarCamins() {
		ArrayList<String[]> dades= new ArrayList<String[]>();
		try {
			dades = ctrl.getDomini().consultarCaminsPredefinits();
			dades.addAll(ctrl.getDomini().consultarCaminsUsuari());
			camins = new String[dades.size()];
		} catch (Exception e) {
			VistaDialog d = new VistaDialog();
			d.setDialog("Error en carregar els camins!", e.getMessage(),new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
		}
		for (int i = 0; i < dades.size(); ++i){
			camins[i] = dades.get(i)[0];
		}
	}

	private void init(){
		//frame
		
		setBackground(SystemColor.control);
		setForeground(Color.BLACK);
		setSize(300, 400);
		setLocationRelativeTo(null);
				
		getContentPane().setLayout(new MigLayout("", "[100px,grow][20px,grow][30px,grow][50px,grow][][]", "[100px,grow][100px,grow][100px,grow][100px,grow][100px,grow][][][][][][][][][]"));
		
		
		
		//boto afegir
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nom == null){
					VistaDialog d = new VistaDialog();
					d.setDialog("Error", "No has seleccionat cap cami!",new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
				}
				else{
					try{
						vq.setCami(nom);
						dispose();
					}
					catch(Exception e2){
						VistaDialog d = new VistaDialog();
						d.setDialog("Error", e2.getMessage(),new String[]{"Acceptar"}, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		//llista entitat2
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(337, 85, 200, 300);
		getContentPane().add(scrollPane_1, "cell 0 0 6 9,grow");
		
		JList list = new JList();
		scrollPane_1.setViewportView(list);
		list.setListData(camins);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				nom = (String) list.getSelectedValue();
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		
		getContentPane().add(btnSeleccionar, "cell 0 12,alignx center");
		
		//boto cancelar
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnCancelar, "cell 4 12");
		
	}
}