package domini.queries;
import java.util.ArrayList;

import domini.camins.Cami;
import domini.camins.ControladorCamins;
import domini.graf.CtrlGraf;
import domini.graf.Entitat;

/**
 * La classe ControladorQueries és el controlador que s'encarrega de realitzar les cerques (queries) i gestionar els resultats
 * @author Marc Villanueva Cuadrench
 *
 */
public class ControladorQueries{
	private ArrayList<Query> UltimesQueries;
	private Query QueryActual;
	private Resultat ResultatActual;
	private CtrlGraf CtrlGraf;
	private HeteSim HS;
	private ControladorCamins CtrlCamins;
	
	/**
	 * Creadora de ControladorQueries
	 * @param CtrlGraf controlador del graf
	 * @param CtrlCamins controlador de camins
	 */
	public ControladorQueries(CtrlGraf CtrlGraf, ControladorCamins CtrlCamins){
		this.CtrlGraf = CtrlGraf;
		this.CtrlCamins = CtrlCamins;
		HS = new HeteSim(CtrlGraf.consultarGraf());
		UltimesQueries = new ArrayList<Query>();
	}
	
	/**
	 * Inicialitza una query de tipus clustering
	 * @param cami Camí sobre el que fer la query
	 * @param k Número de clústers
	 * @throws Exception ...
	 */
	public void inicialitzarQueryClustering(String cami, int k) throws Exception{
		Cami c = new Cami("nom", cami, "descr");
		QueryActual = new QueryClustering(c,k,HS);
	}
	
	/**
	 * Inicialitza una query de tipus clustering amb un camí existent
	 * @param nom Nom del camí que ja existeix
	 * @throws Exception ...
	 */
	public void inicialitzarQueryClusteringlNom(String nom) throws Exception{
		Cami c = CtrlCamins.consultarCamiExt(nom);
		QueryActual = new QueryNormal(c);
	}
	
	/**
	 * Inicialitza una query de tipus normal amb un camí no existent
	 * @param cami Camí sobre el que fer la query
	 * @throws Exception ...
	 */
	public void inicialitzarQuerynormal(String cami) throws Exception{
		Cami c = new Cami("nom", cami, "descr");
		QueryActual = new QueryNormal(c);
	}
	
	/**
	 * Inicialitza una query de tipus normal amb un camí existent
	 * @param nom Nom del camí que ja existeix
	 * @throws Exception ...
	 */
	public void inicialitzarQuerynormalNom(String nom) throws Exception{
		Cami c = CtrlCamins.consultarCamiExt(nom);
		QueryActual = new QueryNormal(c);
	}
	
	/**
	 * Guarda el camí de la query actual al conjunt de l'usuari
	 * @param nom Nom del camí que es guardarà
	 * @param descr Descripció del camí que es guardarà
	 * @throws Exception ...
	 */
	public void guardarCamiQuery(String nom, String descr) throws Exception{
		Cami c = QueryActual.cami;
		c.setNom(nom);
		c.setDescripcio(descr);
		CtrlCamins.afegirCami(c.getNom(), c.getPath(), c.getDescripcio());
	}
	
	/**
	 * Selecciona l'entitat inicial de la query
	 * @param nom Nom de l'entitat inicial a afegir
	 * @throws Exception No es pot afegir una entitat inicial a una query de tipus clustering
	 */
	public void seleccionarEntitatInicial(String nom) throws Exception{
		if(QueryActual.esNormal()){
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
	
	/**
	 * Selecciona l'entitat final de la query
	 * @param nom Nom de l'entitat final a afegir
	 * @throws Exception No es pot afegir una entitat final a una query de tipus clustering
	 */
	public void seleccionarEntitatFinal(String nom) throws Exception{
		if(QueryActual.esNormal()){
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
	
	/**
	 * Executa una query de tipus clustering 
	 * @return Matriu d'Strings que són clústers d'entitats
	 * @throws Exception La query no és de tipus clustering
	 */
	public ArrayList<ArrayList<String>> executarClustering() throws Exception{
		if(QueryActual.esClustering()){
			
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
	
	/**
	 * Executa una query de tipus normal
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception La query no és de tipus normal
	 */
	public ArrayList<Pair<Entitat, Float>> executarQuery() throws Exception{ // String -> Integer
		if(QueryActual.esNormal()){
			ArrayList<Pair<Integer, Float>> resultat = ((QueryNormal) QueryActual).executa(HS);
			ArrayList<Pair<Entitat, Float>> r = new ArrayList<Pair<Entitat,Float>>();
		
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
				
				Pair<Entitat, Float> p = new Pair<Entitat,Float>(e,hs);
				r.add(p);
				
			}
			ResultatActual = new Resultat(r);
			afegirQueryRecent();
			return ResultatActual.getVisibles();
		} 	
		else{
			throw new Exception("La query no es normal");
		}
	}
	
	/**
	 * Filtra el resultat actual segons el número d'entitats a mostrar
	 * @param n Número d'entitats que es vol que es mostrin
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public ArrayList<Pair<Entitat, Float>> filtrarResultatN(int n) throws Exception{
		if(ResultatActual != null){
			if(QueryActual.esClustering()) throw new Exception("La query actual és de clustering");
			ResultatActual.filtrarN(n);
			ArrayList<Pair<Entitat, Float>> entitats = ResultatActual.getVisibles();
			
			return entitats;
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	
	/**
	 * Filtrar el resultat actual segons la label de les entitats a mostrar
	 * @param label Label de les entitats que es volen mostrar
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public ArrayList<Pair<Entitat, Float>> filtrarResultatLabel(int label) throws Exception{
		if(ResultatActual != null){
			if(QueryActual.esClustering()) throw new Exception("La query actual és de clustering");
			ResultatActual.filtrarLabelEq(label);
			ArrayList<Pair<Entitat, Float>> entitats = ResultatActual.getVisibles();
			
			return entitats;
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	
	/**
	 * Filtra el resultat actual segons la label de les entitats que no es volen mostrar
	 * @param label Label de les entitats que no es volen mostrar
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public ArrayList<Pair<Entitat, Float>> filtrarResultatNoLabel(int label) throws Exception{
		if(ResultatActual != null){
			if(QueryActual.esClustering()) throw new Exception("La query actual és de clustering");
			ResultatActual.filtrarLabelDif(label);
			ArrayList<Pair<Entitat, Float>> entitats = ResultatActual.getVisibles();
			
			return entitats;
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	
	/**
	 * Neteja els filtres del resultat
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public ArrayList<Pair<Entitat, Float>> netejaFiltresResultat() throws Exception{
		if(ResultatActual != null){
			if(QueryActual.esClustering()) throw new Exception("La query actual és de clustering");
			ResultatActual.netejaFiltres();
			ArrayList<Pair<Entitat, Float>> entitats = ResultatActual.getVisibles();
			
			return entitats;
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	
	/**
	 * Filtra el resultat segons l'entitat que es vol ocultar
	 * @param id Id de l'entitat que es vol ocultar
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public ArrayList<Pair<Entitat,Float>> filtrarResultatEntitat(int id) throws Exception{
		if(ResultatActual != null){
			if(QueryActual.esClustering()) throw new Exception("La query actual és de clustering");
			ResultatActual.filtrarEntitat(id);
			ArrayList<Pair<Entitat, Float>> entitats = ResultatActual.getVisibles();
			
			return entitats;
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	}

	/**
	 * Elimina l'ultim filtre activat
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public void resultatDesferFiltre() throws Exception{
		if(ResultatActual != null){
			if(QueryActual.esClustering()) throw new Exception("La query actual és de clustering");
			ResultatActual.desferFiltre();
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	 }
	    
	/**
	 * Comprova si el resultat actual està format per autors
	 * @return True si el resultat són autors, false en cas contrari
	 * @throws Exception No hi ha resultat actual
	 */
	public boolean resultatIsAutor() throws Exception {
		if(ResultatActual != null){
			if(QueryActual.esNormal()) return ResultatActual.isAutor();
			else{
				return (QueryActual.getPath().charAt(0)=='A');
			}
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	    
	/**
	 * Comprova si el resultat actual està format per papers
	 * @return True si el resultat són papers, false en cas contrari
	 * @throws Exception No hi ha resultat actual
	 */
	public boolean resultatIsPaper() throws Exception{
		if(ResultatActual != null){
			if(QueryActual.esNormal()) return ResultatActual.isPaper();
			else{
				return (QueryActual.getPath().charAt(0)=='P');
			}
		}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	    
	/**
	 * Comprova si el resultat actual està format per conferencies
	 * @return True si el resultat són conferencies, false en cas contrari
	 * @throws Exception No hi ha resultat actual
	 */
	public boolean resultatIsConferencia() throws Exception{
    	if(ResultatActual != null){
    		if(QueryActual.esNormal()) return ResultatActual.isConferencia();
			else{
				return (QueryActual.getPath().charAt(0)=='C');
			}
    	}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	    
	/**
	 * Comprova si el resultat actual està format per papers
	 * @return True si el resultat són papers, false en cas contrari
	 * @throws Exception No hi ha resultat actual
	 */
	public boolean resultatIsTerme() throws Exception{
    	if(ResultatActual != null){
    		if(QueryActual.esNormal()) return ResultatActual.isTerme();
			else{
				return (QueryActual.getPath().charAt(0)=='T');
			}
    	}
		else{
			throw new Exception("No hi ha resultat");
		}
	}
	
	/**
	 * Consulta una query recent. Es guarden les 3 queries més recents
	 * @param i Número de la query recent a consultar (0 és la query més recent)
	 * @return Vector de Strings amb les característiques de la query consultada
	 * @throws Exception No hi ha queries recents
	 * @throws Exception i és més gran o igual 3
	 */
	public String[] consultarQueryRecent(int i) throws Exception{
		if(UltimesQueries.size()==0) throw new Exception("No hi ha queries recents");
		if(i>=3) throw new Exception("L'index supera el número de queries guardades");
		
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
	
	/**
	 * Consulta la query actual
	 * @return Vector de Strings amb les característiques de la query actual
	 * @throws Exception No hi ha query actual
	 */
	public String[] consultarQueryActual() throws Exception{
		if(QueryActual==null) throw new Exception("No hi ha query actual");
		
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
	
	/**
	 * Afegeix una query a les queries recents
	 * @throws Exception No hi ha query actual
	 */
	private void afegirQueryRecent() throws Exception{
		if(QueryActual==null) throw new Exception("No hi ha query actual");
		
		if(UltimesQueries.size()==3){
			UltimesQueries.remove(2);
			UltimesQueries.add(0, QueryActual);
		}
		else{
			UltimesQueries.add(0,QueryActual);
		}
	}
	
	/**
	 * Normalitza el tipus passant de la inicial a l'String complet
	 * @param tipus String amb el tipus desitjat al primer caràcter.
	 * @return Tipus normalitzat
	 */
	private String normalitzarTipus(String tipus){
		if(tipus.charAt(0)=='A') tipus = "Autor";
		else if(tipus.charAt(0)=='C') tipus = "Conferencia";
		else if(tipus.charAt(0)=='T') tipus = "Terme";
		else if(tipus.charAt(0)=='P') tipus = "Paper";
		
		return tipus;
	}
	
	/**
	 * Consulta les dades del resultat actual d'una query normal
	 * @return Matriu d'Strings amb les dades del resultat
	 * @throws Exception No hi ha resultat actual
	 * @throws Exception La query actual és de clustering
	 */
    public String[][] getDadesNormal() throws Exception{
    	if(ResultatActual==null) throw new Exception("No hi ha resultat actual");
    	if(QueryActual.esClustering()) throw new Exception("La query actual és de clustering");
    	
    	return ResultatActual.getDadesString();
    }
	
}
