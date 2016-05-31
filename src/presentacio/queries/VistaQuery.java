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
import presentacio.queries.VistaResultat;

public class VistaQuery{
	private CtrlPresentacio ctrl;
	public JFrame frame;
	public String cami;
	
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
	
	//EXECUTAR
	public void executarNormal(boolean camiNou, String entitat){
		try{
			//System.out.println(" 
			if (camiNou){
				ctrl.getDomini().inicialitzarQuerynormal(cami);
				ctrl.getDomini().seleccionarEntitatInicial(entitat);
				ctrl.getDomini().executarQuery();
				//System.out.println(" OK!");
			}
			else{
				ctrl.getDomini().inicialitzarQuerynormalNom(cami);
				ctrl.getDomini().seleccionarEntitatInicial(entitat);
				ctrl.getDomini().executarQuery();
				//System.out.println(" OK!");
			}
			System.out.println(" OK Query!");
			
			//WTF?!
			VistaResultat v = new VistaResultat(ctrl);
			//WTF?!
			
		}catch(Exception E){
			E.printStackTrace();
		}
	}
	
	public void executarClustering(boolean camiNou, int k){
		try{
			if (camiNou){
				ctrl.getDomini().inicialitzarQueryClustering(cami,k);
				ctrl.getDomini().executarClustering();
			}
			else {
				ctrl.getDomini().inicialitzarQueryClusteringlNom(cami);
				ctrl.getDomini().executarClustering();
			}
			System.out.println(" OK Clustering!");
		}catch (Exception E){
			E.printStackTrace();
		}
		fesVisible();
	}

}
