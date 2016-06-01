package presentacio.queries;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;

public class VistaQuery{
	private CtrlPresentacio ctrl;
	public JFrame frame;
	public String pathCami;
	public String nomCami;
	private ArrayList<String> resClustering;
	
	public VistaQuery(CtrlPresentacio ctrl) throws Exception {
		this.ctrl = ctrl;
		init();
		frame.setVisible(true);
		pathCami = new String();
	}
	
	private void init() {
		resClustering = new ArrayList<String>();
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

	public void setCami(String nom, String path) {
		this.nomCami = nom;
		this.pathCami = path;
	}
	
	//EXECUTAR
	public void executarNormal(boolean camiNou, String entitat) throws Exception{
			if (camiNou){
				ctrl.getDomini().inicialitzarQuerynormal(pathCami);
				ctrl.getDomini().seleccionarEntitatInicial(entitat);
				ctrl.getDomini().executarQuery();
				//System.out.println(" OK!");
			}
			else{
				ctrl.getDomini().inicialitzarQuerynormalNom(nomCami);
				ctrl.getDomini().seleccionarEntitatInicial(entitat);
				ctrl.getDomini().executarQuery();
				//System.out.println(" OK!");
			}
			System.out.println(" OK Query!");

			new VistaResultatNew(ctrl, this);
	}
	
	public void executarClustering(boolean camiNou, int k){
		try{
			if (camiNou){
				ctrl.getDomini().inicialitzarQueryClustering(pathCami,k);
				resClustering = formatejaRes(ctrl.getDomini().executarClustering());
			}
			else {
				ctrl.getDomini().inicialitzarQueryClusteringlNom(nomCami, k);
				resClustering = formatejaRes(ctrl.getDomini().executarClustering());
			}
			System.out.println(" OK Clustering!");
		}catch (Exception E){
			E.printStackTrace();
		}
		new VistaResultatClustering(ctrl,frame,this);
		fesVisible();
	}
	
	public ArrayList<String> getData(){
		return resClustering;
	}
	
	public ArrayList<String> formatejaRes(ArrayList<ArrayList<String>> res){
		ArrayList<String> a = new ArrayList<String>();
		for (int i = 0; i < res.size(); ++i){
			a.add("----- CLUSTER " + i+1 + " -----");
			for (int j = 0; j < res.get(i).size(); ++j){
				a.add(res.get(i).get(j));
			}
			a.add("");
		}
		return a;
	}

}
