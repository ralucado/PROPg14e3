package domini.queries;
import java.util.ArrayList;

import domini.camins.Cami;
import domini.graf.CtrlGraf;
import domini.graf.Entitat;

public class ControladorQueries{
	private ArrayList<Query> UltimesQueries;
	private Query QueryActual;
	private Resultat ResultatActual;
	private ArrayList<Float> valors;
	private CtrlGraf CtrlGraf;
	private HeteSim HS;
	
	public ControladorQueries(CtrlGraf CtrlGraf){
		this.CtrlGraf = CtrlGraf;
		HS = new HeteSim(CtrlGraf.consultarGraf());
		UltimesQueries = new ArrayList<Query>();
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
			String tipus = QueryActual.getPath().substring(0,1);
			
			tipus = normalitzarTipus(tipus);
			int id = CtrlGraf.consultarGraf().getIdByNameAndType(nom, tipus);
			int pos = 0;
			if(tipus=="Autor") pos = CtrlGraf.consultarGraf().getPositionByIdAutor(id);
			else if(tipus=="Conferencia") pos = CtrlGraf.consultarGraf().getPositionByIdConferencia(id);
			else if(tipus=="Terme") pos = CtrlGraf.consultarGraf().getPositionByIdTerme(id);
			else if(tipus=="Paper") pos = CtrlGraf.consultarGraf().getPositionByIdPaper(id);
			Entitat e = CtrlGraf.consultarGraf().consultarEntitat(id);
			((QueryNormal) QueryActual).setEntitatInicial(e,pos);
		}
		else{
			throw new Exception("No es pot afegir entitat inicial a una query de clustering");
		}
	}
	
	public void seleccionarEntitatFinal(String nom) throws Exception{
		if(QueryActual instanceof QueryNormal){
			String tipus = QueryActual.getPath();
			tipus = tipus.substring(tipus.length()-1, tipus.length());
			
			tipus = normalitzarTipus(tipus);
			int id = CtrlGraf.consultarGraf().getIdByNameAndType(nom, tipus);
			int pos = 0;
			if(tipus=="Autor") pos = CtrlGraf.consultarGraf().getPositionByIdAutor(id);
			else if(tipus=="Conferencia") pos = CtrlGraf.consultarGraf().getPositionByIdConferencia(id);
			else if(tipus=="Terme") pos = CtrlGraf.consultarGraf().getPositionByIdTerme(id);
			else if(tipus=="Paper") pos = CtrlGraf.consultarGraf().getPositionByIdPaper(id);			
			Entitat e = CtrlGraf.consultarGraf().consultarEntitat(id);
			((QueryNormal) QueryActual).setEntitatNoInicial(e,pos);
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
	
	public ArrayList<Pair<Integer, Float>> executarQuery() throws Exception{ // String -> Integer
		if(QueryActual instanceof QueryNormal){
			ArrayList<Pair<Integer, Float>> resultat = ((QueryNormal) QueryActual).executa(HS);
			/*
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
			ResultatActual = new Resultat(entitats);
			valors = hetesimValors;
			ArrayList<Boolean> visibles = ResultatActual.getVisibles();
			ArrayList<Pair<String,Float>> R = new ArrayList<Pair<String,Float>>();
			for(int i = 0; i<entitats.size();i++){
				if(visibles.get(i)){
					Pair<String,Float> p = new Pair<String,Float>(entitats.get(i).getNom(), hetesimValors.get(i));
					R.add(p);
				}
			}
			afegirQueryRecent();*/
			return resultat;
		} 	
		else{
			throw new Exception("La query no es normal");
		}
	}
	
	public ArrayList<Pair<String, Float>> filtrarResultatN(String n) throws Exception{
		if(ResultatActual != null){
			ResultatActual.filtrarN(Integer.valueOf(n));
			ArrayList<Entitat> entitats = ResultatActual.getEntitats();
			ArrayList<Boolean> visibles = ResultatActual.getVisibles();
			ArrayList<Pair<String,Float>> R = new ArrayList<Pair<String,Float>>();
			for(int i = 0; i<entitats.size();i++){
				if(visibles.get(i)){
					Pair<String,Float> p = new Pair<String,Float>(entitats.get(i).getNom(), valors.get(i));
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
			
			ArrayList<Entitat> entitats = ResultatActual.getEntitats();
			ArrayList<Boolean> visibles = ResultatActual.getVisibles();
			ArrayList<Pair<String,Float>> R = new ArrayList<Pair<String,Float>>();
			for(int i = 0; i<entitats.size();i++){
				if(visibles.get(i)){
					Pair<String,Float> p = new Pair<String,Float>(entitats.get(i).getNom(), valors.get(i));
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
			if( ((QueryNormal)QueryActual).getEntitatInicial() != null) query[2] = ((QueryNormal)QueryActual).getEntitatInicial().getNom();
			if( ((QueryNormal)QueryActual).getEntitatNoInicial() != null) query[3] = ((QueryNormal)QueryActual).getEntitatNoInicial().getNom();
		}
		return query;
	}
	
	private void afegirQueryRecent(){
		if(UltimesQueries.size()==3){
			UltimesQueries.remove(2);
			UltimesQueries.add(0, QueryActual);
		}
		else{
			UltimesQueries.add(0,QueryActual);
		}
	}
	
	private String normalitzarTipus(String tipus){
		if(tipus.charAt(0)=='A') tipus = "Autor";
		else if(tipus.charAt(0)=='C') tipus = "Conferencia";
		else if(tipus.charAt(0)=='T') tipus = "Terme";
		else if(tipus.charAt(0)=='P') tipus = "Paper";
		
		return tipus;
	}
	
	
}
