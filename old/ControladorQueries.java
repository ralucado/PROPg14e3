import java.util.ArrayList;

public class ControladorQueries {
	private ArrayList<Query> UltimesQueries;
	private Query QueryActual;
	private Resultat ResultatActual;
	private ControladorGraf CtrlGraf;
	
	public ControladorQueries(ControladorGraf CtrlGraf){
		this.CtrlGraf = CtrlGraf;
	}
	
	public void SeleccionarCami(String cami){
		QueryActual.setPath(cami);
	}
	
	public void SeleccionarEntitatInicial(String nom, String tipus){
		Entitat e = CtrlGraf.EntitatNomTipus(nom, tipus);
		QueryActual.setEntitatInicial(e);
	}
	
	public void SeleccionarEntitatNoInicial(String nom, String tipus, int i) throws Exception{
		Entitat e = CtrlGraf.EntitatNomTipus(nom, tipus);
		QueryActual.setEntitatNoInicial(e, i);
	}
	
	public void ExecutarClustering(){
		
	}
	
	public void ExecutarQuery(){
		//agafa resultat i afegeix a recents
	}
	
	public void FiltrarResultatN(String n) throws Exception{
		if(ResultatActual != null){
			ResultatActual.filtrarN(Integer.valueOf(n));
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	
	public void FiltrarResultatLabel(String label) throws Exception{
		if(ResultatActual != null){
			ResultatActual.filtarLabel(new Label(label));
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	
	
}
