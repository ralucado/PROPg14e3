package Domini;
import java.util.ArrayList;

public class ControladorQueries{
	private ArrayList<Query> UltimesQueries;
	private Query QueryActual;
	private Resultat ResultatActual;
	private ControladorGraf CtrlGraf;
	private ConjuntClusters ConjClus;
	private HeteSim HS;
	
	public ControladorQueries(ControladorGraf CtrlGraf){
		this.CtrlGraf = CtrlGraf;
		HS = new HeteSim(CtrlGraf.consultarGraf());
	}
	
	public void inicialitzarQueryClustering(String cami, int k) throws Exception{
		Cami c = new Cami("nom", cami, "descr");
		QueryActual = new QueryClustering(c,k,HS);
	}
	
	public void inicialitzarQuerynormal(String cami) throws Exception{
		Cami c = new Cami("nom", cami, "descr");
		QueryActual = new QueryNormal(c);
	}
	
	public void seleccionarEntitatInicial(String nom) throws Exception{
		if(QueryActual instanceof QueryNormal){
			String tipus = QueryActual.getPath().substring(0,0);
			tipus = normalitzarTipus(tipus);
			int id = CtrlGraf.consultarGraf().getIdByNameAndType(nom, tipus);
			Entitat e = CtrlGraf.consultarGraf().consultarEntitat(id);
			((QueryNormal) QueryActual).setEntitatInicial(e);
		}
		else{
			throw new Exception("No es pot afegir entitat inicial a una query de clustering");
		}
	}
	
	public void seleccionarEntitatFinal(String nom) throws Exception{
		if(QueryActual instanceof QueryNormal){
			String tipusNoIni = QueryActual.getPath();
			tipusNoIni = tipusNoIni.substring(tipusNoIni.length()-1, tipusNoIni.length()-1);
			tipusNoIni = normalitzarTipus(tipusNoIni);
			int id = CtrlGraf.consultarGraf().getIdByNameAndType(nom, tipusNoIni);
			Entitat e = CtrlGraf.consultarGraf().consultarEntitat(id);
			((QueryNormal) QueryActual).setEntitatNoInicial(e);
		}
		else{
			throw new Exception("No es pot afegir entitat no inicial a una query de clustering");
		}
	}
	
	public ArrayList<ArrayList<String>> executarClustering() throws Exception{
		if(QueryActual instanceof QueryClustering){
			
			ArrayList<ArrayList<Entitat>> resultatClustering = ((QueryClustering) QueryActual).getResultatClusters(CtrlGraf.consultarGraf());
			ArrayList<ArrayList<String>> resultat = new ArrayList<ArrayList<String>>();
			for(int i = 0; i<resultatClustering.size();i++){
				ArrayList<String> ent = new ArrayList<String>();
				for(int j = 0; j<resultatClustering.get(i).size(); j++){
					ent.add(resultatClustering.get(i).get(j).getNom());
				}
				resultat.add(ent);
			}
			afegirQueryRecent();
			return resultat;
		} 	
		else{
			throw new Exception("La query no es de clustering");
		}
	}
	
	public ArrayList<Pair<String, Float>> executarQuery() throws Exception{
		if(QueryActual instanceof QueryClustering){
			ArrayList<Pair<Integer, Float>> resultat = ((QueryNormal) QueryActual).executa(HS);
			ArrayList<Entitat> entitats = new ArrayList<Entitat>();
			ArrayList<Float> hetesimValors = new ArrayList<Float>();
			char c = QueryActual.getPath().charAt(0); 
			for(int i = 0; i<resultat.size();i++){
				int id = 0;
				int pos = resultat.get(i).first;
				Float hs = resultat.get(i).second;
				if(c=='A'){
					id = CtrlGraf.consultarGraf().getIdByPositionAutor(pos);
				}
				else if(c=='P'){
					id = CtrlGraf.consultarGraf().getIdByPositionPaper(pos);
				}
				else if(c=='C'){
					id = CtrlGraf.consultarGraf().getIdByPositionConferencia(pos);
				}
				else if(c=='T'){
					id = CtrlGraf.consultarGraf().getIdByPositionTerme(pos);
				}
				Entitat e = CtrlGraf.consultarGraf().consultarEntitat(id);
				entitats.add(e);
				hetesimValors.add(hs);				
			}
			ResultatActual = new Resultat(entitats, hetesimValors);
			entitats = ResultatActual.getArray();
			hetesimValors = ResultatActual.getValors();
			ArrayList<Boolean> visibles = ResultatActual.getVisibles();
			ArrayList<Pair<String,Float>> R = new ArrayList<Pair<String,Float>>();
			for(int i = 0; i<entitats.size();i++){
				if(visibles.get(i)){
					Pair<String,Float> p = new Pair<String,Float>(entitats.get(i).getNom(), hetesimValors.get(i));
					R.add(p);
				}
			}
			afegirQueryRecent();
			return R;
		} 	
		else{
			throw new Exception("La query no es normal");
		}
	}
	
	public ArrayList<Pair<String, Float>> filtrarResultatN(String n) throws Exception{
		if(ResultatActual != null){
			ResultatActual.filtrarN(Integer.valueOf(n));
			ArrayList<Entitat> entitats = ResultatActual.getArray();
			ArrayList<Float> hetesimValors = ResultatActual.getValors();
			ArrayList<Boolean> visibles = ResultatActual.getVisibles();
			ArrayList<Pair<String,Float>> R = new ArrayList<Pair<String,Float>>();
			for(int i = 0; i<entitats.size();i++){
				if(visibles.get(i)){
					Pair<String,Float> p = new Pair<String,Float>(entitats.get(i).getNom(), hetesimValors.get(i));
					R.add(p);
				}
			}
			return R;
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	
	public ArrayList<Pair<String, Float>> filtrarResultatLabel(int label) throws Exception{
		if(ResultatActual != null){
			ResultatActual.filtrarLabelEq(label);
			
			ArrayList<Entitat> entitats = ResultatActual.getArray();
			ArrayList<Float> hetesimValors = ResultatActual.getValors();
			ArrayList<Boolean> visibles = ResultatActual.getVisibles();
			ArrayList<Pair<String,Float>> R = new ArrayList<Pair<String,Float>>();
			for(int i = 0; i<entitats.size();i++){
				if(visibles.get(i)){
					Pair<String,Float> p = new Pair<String,Float>(entitats.get(i).getNom(), hetesimValors.get(i));
					R.add(p);
				}
			}
			return R;
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	
	public String[] consultarQueryRecent(int i){
		Query c = UltimesQueries.get(i);
		String[] query;
		if(c.esClustering()){
			query = new String[2];
			query[0] = "clustering";
			query[1] = c.getPath();
		}
		else{
			query = new String[4];
			query[0] = "normal";
			query[1] = ((QueryNormal)c).getPath();
			query[2] = ((QueryNormal)c).getEntitatInicial().getNom();
			query[3] = ((QueryNormal)c).getEntitatNoInicial().getNom();
		}
		return query;
	}
	
	public String[] consultarQueryActual(){
		String[] query;
		if(QueryActual.esClustering()){
			query = new String[2];
			query[0] = "clustering";
			query[1] = QueryActual.getPath();
		}
		else{
			query = new String[4];
			query[0] = "normal";
			query[1] = ((QueryNormal)QueryActual).getPath();
			query[2] = ((QueryNormal)QueryActual).getEntitatInicial().getNom();
			query[3] = ((QueryNormal)QueryActual).getEntitatNoInicial().getNom();
		}
		return query;
	}
	
	private void afegirQueryRecent(){
		if(UltimesQueries.size()==3){
			UltimesQueries.remove(3);
			UltimesQueries.add(1, QueryActual);
		}
		else{
			UltimesQueries.add(1,QueryActual);
		}
	}
	
	private String normalitzarTipus(String tipus){
		if(tipus=="A") tipus = tipus+"utor";
		else if(tipus=="C") tipus = tipus+"onferencia";
		else if(tipus=="T") tipus = tipus+"erme";
		else if(tipus=="P") tipus = tipus+"aper";
		
		return tipus;
	}
	
	
}
