package presentacio.queries;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

public class VistaQuery{
	private CtrlPresentacio ctrl;
	public JFrame frame;
	public String cami;
	
	public static void main(String[] args){
		try{new VistaQuery(new CtrlPresentacio());}catch(Exception e){}
	}
	
	
	public VistaQuery(CtrlPresentacio ctrl) throws Exception {
		this.ctrl = ctrl;
		init();
		frame.setVisible(true);
	}
	
	private void init() {
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
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, "cell 0 1,grow");
		//Normal
		VistaCrearQuery qN = new VistaCrearQuery(ctrl, this);
		tabbedPane.addTab("Query Normal", qN.frame.getContentPane());
		//Clustering
		VistaCrearClustering qC = new VistaCrearClustering(ctrl, this);
		tabbedPane.addTab("Query Clustering", qC.frame.getContentPane());
	}
	
	public void fesVisible() {
		frame.setVisible(true);
	}

	public void setCami(String nom) {
		this.cami = nom;
	}
	
	public void executarNormal(){
		try{
			ctrl.getDomini().inicialitzarQuerynormalNom(cami);
			ctrl.getDomini().executarQuery();
		}catch(Exception E){}
	}
	
	public void executarClustering(){
		try{
			ctrl.getDomini().inicialitzarQueryClusteringlNom(cami);
		}catch (Exception E){}
	}

}
