package domini.ctrl;
import java.util.ArrayList;

import domini.camins.Cami;
import domini.camins.ConjuntCamins;
import domini.camins.myCtrlCamins;
import domini.graf.Entitat;
import domini.graf.myCtrlGraf;
import domini.queries.ControladorQueries;
import domini.queries.Pair;
import domini.queries.QueryClustering;
import domini.queries.QueryNormal;
import domini.usuaris.CtrlUsuaris;
import domini.usuaris.Usuari;
import persistencia.CtrlPersistencia;

public class myCtrlDomini extends CtrlDomini {
	private CtrlPersistencia persistencia;
	private ControladorQueries queries;
	
	public myCtrlDomini() throws Exception {
        ctrlUsuari = new CtrlUsuaris();
        ctrlGraf = new myCtrlGraf();
        usuari = new Usuari();
        caminsUsuari = new ConjuntCamins();
        caminsPredefinits = new ConjuntCamins();
        conjuntUsuaris = ctrlUsuari.getCjtUsuaris();
        graf = ctrlGraf.carregarGraf();
        ctrlGraf.setGraf(graf);
       
        persistencia = new CtrlPersistencia();
        queries = new ControladorQueries(ctrlGraf, ctrlCamins);
	}
	
	public void logIn(String nom, String constrasenya) throws Exception {
        Usuari aux = ctrlUsuari.carregarUsuari(nom);
        if (aux.getContrasenya().equals(constrasenya)){
            usuari = aux;
            ctrlUsuari.setUsuari(usuari);
            ctrlCamins = new myCtrlCamins(usuari.getNom());
            caminsUsuari = crearConjunt(ctrlCamins.consultarCaminsUsuari());
            caminsPredefinits = crearConjunt(ctrlCamins.consultarCaminsPredefinits());
        }
        else throw new Exception("Contrasenya incorrecta");
}
	
	public boolean esAdmin() {
		return ctrlUsuari.getUsuari().esAdmin();
	}
	
	public String getUsuariActual() {
		return usuari.getNom();
	}
	
	public String getContrasenya(String user) throws Exception {
		return conjuntUsuaris.consultarContrasenya(user);
	}
	
	public ControladorQueries getCtrlQueries(){
		return queries;
	}
	
	public void altaUsuari(String nom, String contrasenya) throws Exception {
		super.altaUsuari(nom, contrasenya);
		persistencia.creaFitxerCamins(nom);
	}
	
	public void altaAdmin(String nom, String contrasenya) throws Exception {
		super.altaAdmin(nom, contrasenya);
		persistencia.creaFitxerCamins(nom);
	}
	
	public void baixaUsuari(String nom) throws Exception {
        super.baixaUsuari(nom);
        persistencia.esborraFitxerCamins(nom);
	}
	
	public void modificarNomUsuari(String nomActual, String nomNou) throws Exception {
	    super.modificarNomUsuari(nomActual, nomNou);
	    persistencia.reanomenaFitxerCamins(nomActual, nomNou);
	}
	
	public ArrayList<ArrayList<String>> consultarRelacionsAP(){
    	return ((myCtrlGraf) ctrlGraf).consultarRelacionsAP();
    }
    
    public ArrayList<ArrayList<String>> consultarRelacionsCP(){
    	return ((myCtrlGraf) ctrlGraf).consultarRelacionsCP();
    }
    
    public ArrayList<ArrayList<String>> consultarRelacionsTP(){
    	return ((myCtrlGraf) ctrlGraf).consultarRelacionsTP();
    }
    
    public ArrayList<ArrayList<String>> consultarAutorsExt() throws Exception {
    	return ((myCtrlGraf) ctrlGraf).consultarAutorsExt();
    }

    public ArrayList<ArrayList<String>> consultarPapersExt() throws Exception {
    	return ((myCtrlGraf) ctrlGraf).consultarPapersExt();
    }

    public ArrayList<ArrayList<String>> consultarConferenciesExt() throws Exception {
    	return ((myCtrlGraf) ctrlGraf).consultarConferenciesExt();
    }	
    
    public ArrayList<ArrayList<String>> consultarTermesExt() throws Exception {
    	return ((myCtrlGraf) ctrlGraf).consultarTermesExt();
    }
    
    
    /**
	 * Inicialitza una query de tipus clustering
	 * @param cami Camí sobre el que fer la query
	 * @param k Número de clústers
	 * @throws Exception 
	 */
	public void inicialitzarQueryClustering(String cami, int k) throws Exception{
		queries.inicialitzarQueryClustering(cami, k);
	}
	
	/**
	 * Inicialitza una query de tipus clustering amb un camí existent
	 * @param nom Nom del camí que ja existeix
	 * @throws Exception
	 */
	public void inicialitzarQueryClusteringlNom(String nom) throws Exception{
		queries.inicialitzarQueryClusteringlNom(nom);
	}
	
	/**
	 * Inicialitza una query de tipus normal amb un camí no existent
	 * @param cami Camí sobre el que fer la query
	 * @throws Exception
	 */
	public void inicialitzarQuerynormal(String cami) throws Exception{
		queries.inicialitzarQuerynormal(cami);
	}
	
	/**
	 * Inicialitza una query de tipus normal amb un camí existent
	 * @param nom Nom del camí que ja existeix
	 * @throws Exception
	 */
	public void inicialitzarQuerynormalNom(String nom) throws Exception{
		queries.inicialitzarQueryClusteringlNom(nom);
	}
	
	/**
	 * Guarda el camí de la query actual al conjunt de l'usuari
	 * @param nom Nom del camí que es guardarà
	 * @param descr Descripció del camí que es guardarà
	 * @throws Exception
	 */
	public void guardarCamiQuery(String nom, String descr) throws Exception{
		queries.guardarCamiQuery(nom, descr);
	}
	
	/**
	 * Selecciona l'entitat inicial de la query
	 * @param nom Nom de l'entitat inicial a afegir
	 * @throws Exception No es pot afegir una entitat inicial a una query de tipus clustering
	 */
	public void seleccionarEntitatInicial(String nom) throws Exception{
		queries.seleccionarEntitatInicial(nom);
	}
	
	/**
	 * Selecciona l'entitat final de la query
	 * @param nom Nom de l'entitat final a afegir
	 * @throws Exception No es pot afegir una entitat final a una query de tipus clustering
	 */
	public void seleccionarEntitatFinal(String nom) throws Exception{
		queries.seleccionarEntitatFinal(nom);
	}
	
	/**
	 * Executa una query de tipus clustering 
	 * @return Matriu d'Strings que són clústers d'entitats
	 * @throws Exception La query no és de tipus clustering
	 */
	public ArrayList<ArrayList<String>> executarClustering() throws Exception{
		return queries.executarClustering();
	}
	
	/**
	 * Executa una query de tipus normal
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception La query no és de tipus normal
	 */
	public ArrayList<Pair<Entitat, Float>> executarQuery() throws Exception{ // String -> Integer
		return queries.executarQuery();
	}
	
	/**
	 * Filtra el resultat actual segons el número d'entitats a mostrar
	 * @param n Número d'entitats que es vol que es mostrin
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public ArrayList<Pair<Entitat, Float>> filtrarResultatN(int n) throws Exception{
		return queries.filtrarResultatN(n);
	}
	
	/**
	 * Filtrar el resultat actual segons la label de les entitats a mostrar
	 * @param label Label de les entitats que es volen mostrar
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public ArrayList<Pair<Entitat, Float>> filtrarResultatLabel(int label) throws Exception{
		return queries.filtrarResultatLabel(label);
	}
	
	/**
	 * Filtra el resultat actual segons la label de les entitats que no es volen mostrar
	 * @param label Label de les entitats que no es volen mostrar
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public ArrayList<Pair<Entitat, Float>> filtrarResultatNoLabel(int label) throws Exception{
		return queries.filtrarResultatNoLabel(label);
	}
	
	/**
	 * Neteja els filtres del resultat
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public ArrayList<Pair<Entitat, Float>> netejaFiltresResultat() throws Exception{
		return queries.netejaFiltresResultat();
	}
	
	/**
	 * Filtra el resultat segons l'entitat que es vol ocultar
	 * @param id Id de l'entitat que es vol ocultar
	 * @return Llista de parelles d'Entitat i valor d'HeteSim que formen el resultat a mostrar
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public ArrayList<Pair<Entitat,Float>> filtrarResultatEntitat(int id) throws Exception{
		return queries.filtrarResultatEntitat(id);
	}

	/**
	 * Elimina l'ultim filtre activat
	 * @throws Exception El resultat actual és d'una query de clustering
	 * @throws Exception No hi ha resultat actual
	 */
	public void resultatDesferFiltre() throws Exception{
		queries.resultatDesferFiltre();
	 }
	    
	/**
	 * Comprova si el resultat actual està format per autors
	 * @return True si el resultat són autors, false en cas contrari
	 * @throws Exception No hi ha resultat actual
	 */
	public boolean resultatIsAutor() throws Exception {
		return queries.resultatIsAutor();
	}
	    
	/**
	 * Comprova si el resultat actual està format per papers
	 * @return True si el resultat són papers, false en cas contrari
	 * @throws Exception No hi ha resultat actual
	 */
	public boolean resultatIsPaper() throws Exception{
		return queries.resultatIsPaper();
	}
	    
	/**
	 * Comprova si el resultat actual està format per conferencies
	 * @return True si el resultat són conferencies, false en cas contrari
	 * @throws Exception No hi ha resultat actual
	 */
	public boolean resultatIsConferencia() throws Exception{
    	return queries.resultatIsConferencia();
	}
	    
	/**
	 * Comprova si el resultat actual està format per papers
	 * @return True si el resultat són papers, false en cas contrari
	 * @throws Exception No hi ha resultat actual
	 */
	public boolean resultatIsTerme() throws Exception{
    	return queries.resultatIsTerme();
	}
	
	/**
	 * Consulta una query recent. Es guarden les 3 queries més recents
	 * @param i Número de la query recent a consultar (0 és la query més recent)
	 * @return Vector de Strings amb les característiques de la query consultada
	 * @throws Exception No hi ha queries recents
	 * @throws Exception i>=3
	 */
	public String[] consultarQueryRecent(int i) throws Exception{
		return queries.consultarQueryRecent(i);
	}
	
	/**
	 * Consulta la query actual
	 * @return Vector de Strings amb les característiques de la query actual
	 * @throws Exception No hi ha query actual
	 */
	public String[] consultarQueryActual() throws Exception{
		return queries.consultarQueryActual();
	}
	
	/**
	 * Consulta les dades del resultat actual d'una query normal
	 * @return Matriu d'Strings amb les dades del resultat
	 * @throws Exception No hi ha resultat actual
	 * @throws Exception La query actual és de clustering
	 */
    public String[][] getDadesNormal() throws Exception{
    	return queries.getDadesNormal();
    }

	public void esborrarEntitat(String nom, String tipus) throws Exception {
		((myCtrlGraf) ctrlGraf).esborrarEntitat(nom, tipus);
		
	}

	public void modificarEntitat(String nom, String  nomNou, String labelNova, String tipus) throws Exception {
		((myCtrlGraf) ctrlGraf).modificarEntitat(nom, nomNou, labelNova, tipus);
		
	}
	
	public boolean existeixCami(String nom){
		return ((myCtrlCamins) ctrlCamins).existeixCami(nom);
	}
}