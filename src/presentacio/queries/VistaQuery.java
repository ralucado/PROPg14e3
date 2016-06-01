package presentacio.queries;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

/**
 * 
 * @author Martí Lloveras Rosales
 *
 */
public class VistaQuery{
	private CtrlPresentacio ctrl;
	public JFrame frame;
	public String pathCami;
	public String nomCami;
	private ArrayList<String> resClustering;
	
	/**
	 * 
	 * @param ctrl Controlador de presentació
	 * @throws Exception ...
	 */
	public VistaQuery(CtrlPresentacio ctrl) throws Exception {
		this.ctrl = ctrl;
		init();
		frame.setVisible(true);
		pathCami = new String();
	}
	
	/**
	 * Inicialitza els components
	 */
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
	
	/**
	 * Fa visible la finestra
	 */
	public void fesVisible() {
		frame.setVisible(true);
	}
	
	/**Actualitza els camps
	 * @param nom Nom del camí de la query a realitzar
	 * @param path Path del camí de la query a realitzar
	 */
	public void setCami(String nom, String path) {
		this.nomCami = nom;
		this.pathCami = path;
	}
	
	/**
	 * Executa una query
	 * @param camiNou Indica si el camí és del sistema o se n'introdueix un de nou
	 * @param entitat Entitat inicial
	 * @throws Exception ...
	 */
	public void executarNormal(boolean camiNou, String entitat) throws Exception{
			if (camiNou){
				ctrl.getDomini().inicialitzarQuerynormal(pathCami);
				ctrl.getDomini().seleccionarEntitatInicial(entitat);
				ctrl.getDomini().executarQuery();
			}
			else{
				ctrl.getDomini().inicialitzarQuerynormalNom(nomCami);
				ctrl.getDomini().seleccionarEntitatInicial(entitat);
				ctrl.getDomini().executarQuery();
			}

			new VistaResultatNew(ctrl, this);
	}
	
	/**
	 * Executa clustering
	 * @param camiNou Camí a fer servir
	 * @param k Nombre de clusters
	 */
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
			new VistaResultatClustering(ctrl,frame,this);
		}catch (Exception E){
			String[] botons = {"D'acord"};
			(new VistaDialog()).setDialog("No s'ha pogut executar clustering", E.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
		}
		fesVisible();
	}
	
	/**
	 * Obté el resultat de clustering tal com es mostrarà
	 * @return Resultat de clustering mostrable
	 */
	public ArrayList<String> getData(){
		return resClustering;
	}
	
	/**
	 * Prepara el resultat de clustering tal com es mostrarà a la pantalla
	 * @param res Resultat de clustering sense tractar
	 * @return Resultat formatejat
	 */
	public ArrayList<String> formatejaRes(ArrayList<ArrayList<String>> res){
		ArrayList<String> a = new ArrayList<String>();
		for (int i = 0; i < res.size(); ++i){
			a.add("----- CLUSTER " + (i+1) + " -----");
			for (int j = 0; j < res.get(i).size(); ++j){
				a.add(res.get(i).get(j));
			}
			a.add("");
		}
		return a;
	}

}
