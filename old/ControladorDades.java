
public class ControladorDades {
	private static ControladorDades instancia = null;
	private ControladorDades(){}
	
	public static synchronized ControladorDades getInstance(){
		if(instancia==null){
			instancia = new ControladorDades();
		}
		return instancia;
	}
	
	public synchronized String[][] caminsPredefinits(){
		String[][] a = {
				{"cami1","path1","descr1"},
				{"cami2","path2","descr2"},
				{"cami3","path3","descr3"},
				{"cami4","path4","descr4"}
		};
		return a;
	}
	
	public String[][] caminsUsuari(String Usuari){
		String[][] a = {
				{"camiPred1","pathPred1","descrPred1"},
				{"camiPred2","pathPred2","descrPred2"},
				{"camiPred3","pathPred3","descrPred3"},
				{"camiPred4","pathPred4","descrPred4"}
		};
		return a;
	}
	
	public synchronized void  guardarCamins(String[][] camins, String usuari){
		
	}
	
	public synchronized void guardarUsuaris(String[][] usuaris){
		
	}
}
