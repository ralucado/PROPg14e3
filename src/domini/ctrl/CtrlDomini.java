package domini.ctrl;

import java.util.ArrayList;
import java.util.Set;

import domini.camins.Cami;
import domini.camins.ConjuntCamins;
import domini.camins.ControladorCamins;
import domini.camins.ExcepcioCamiExistent;
import domini.camins.ExcepcioCamiNoExistent;
import domini.graf.CtrlGraf;
import domini.graf.Graf;
import domini.usuaris.ConjuntUsuaris;
import domini.usuaris.CtrlUsuaris;
import domini.usuaris.Usuari;

public class CtrlDomini {
	
	protected Graf graf;
	
	protected Usuari usuari;
    protected ConjuntUsuaris conjuntUsuaris;
    
    protected ConjuntCamins caminsUsuari;
	protected ConjuntCamins caminsPredefinits;
	
	protected CtrlUsuaris ctrlUsuari;
	protected CtrlGraf ctrlGraf;
	protected ControladorCamins ctrlCamins;
	
	/**
	 * Crea un nou CtrlDomini i inicialitza els components de la capa de domini
	 * @throws Exception ...
	 */
	public CtrlDomini() throws Exception {
            
            ctrlUsuari = new CtrlUsuaris();
            ctrlGraf = new CtrlGraf();
		
            usuari = new Usuari();
            caminsUsuari = new ConjuntCamins();
            caminsPredefinits = new ConjuntCamins();
            
            conjuntUsuaris = ctrlUsuari.getCjtUsuaris();
            graf = ctrlGraf.carregarGraf();
            ctrlGraf.setGraf(graf);
            
	}
	
	/**
	 * Inicia sessió al programa amb el nom <tt>nom</tt> i la contrasenya <tt>constrasenya</tt>
	 * @param nom nom d'usuari
	 * @param constrasenya contrasenya de l'usuari
	 * @throws Exception ...
	 */
	public void logIn(String nom, String constrasenya) throws Exception {
            Usuari aux = ctrlUsuari.carregarUsuari(nom);
            if (aux.getContrasenya().equals(constrasenya)){
                usuari = aux;
                ctrlUsuari.setUsuari(usuari);
                ctrlCamins = new ControladorCamins(usuari.getNom());
                caminsUsuari = crearConjunt(ctrlCamins.consultarCaminsUsuari());
                caminsPredefinits = crearConjunt(ctrlCamins.consultarCaminsPredefinits());
            }
            else throw new Exception("Contrasenya incorrecta");
	}
	
	/**
	 * Tanca la sessió actual
	 * @throws Exception ...
	 */
	public void logOut() throws Exception {
            ctrlUsuari.guardarCjtUsuaris();
            ctrlGraf.guardarGraf();
            ctrlCamins.guardarCamins(usuari.getNom());
	}
	
	/**
     * Assigna l'usuari <tt>usuari</tt> al controlador
     * @param usuari usuari a assignar
     * @throws Exception ...
     */
	public void setUsuari(Usuari usuari) throws Exception {
            this.usuari = usuari;
            ctrlUsuari.setUsuari(usuari);
	}
	
	@SuppressWarnings("unused")
	private Usuari carregarUsuari(String nom) throws Exception {
            return ctrlUsuari.carregarUsuari(nom);
	}
	
	/**
     * Retorna el conjunt d'usuaris del controlador
     * @return conjunt del controlador
     * @throws Exception ...
     */
	public ConjuntUsuaris carregarCjtUsuaris() throws Exception {	
            return ctrlUsuari.getCjtUsuaris();
	}
	
	/**
     * Guarda el conjunt d'usuaris del controlador a fitxers
     * @throws Exception pot retornar IOException
     */
	public void guardarCjtUsuaris() throws Exception {
            ctrlUsuari.guardarCjtUsuaris();
	}
	
	/**
	 * Crea un usuari amb nom = <tt>nom</tt>, contrasenya = <tt>contrasenya</tt> i admin = <tt>false</tt>, i l'afegeix al conjunt del controlador.
	 * @param nom nom de l'usuari a crear
	 * @param contrasenya contrasenya de l'usuari a crear
	 * @throws Exception retorna excepció si el conjunt d'usuaris del controlador ja conté un usuari amb el mateix nom o si l'usuari del controlador no és administrador
	 */
	public void altaUsuari(String nom, String contrasenya) throws Exception {
            ctrlUsuari.altaUsuari(nom, contrasenya);
	}
	
	/**
	 * Crea un usuari amb nom = <tt>nom</tt>, contrasenya = <tt>contrasenya</tt> i admin = <tt>false</tt>, i l'afegeix al conjunt del controlador, augmentant el nº d'admins.
	 * @param nom nom de l'usuari a crear
	 * @param contrasenya contrasenya de l'usuari a crear
	 * @throws Exception retorna excepció si el conjunt d'usuaris del controlador ja conté un usuari amb el mateix nom o si l'usuari del controlador no és administrador
	 */
	public void altaAdmin(String nom, String contrasenya) throws Exception {
            ctrlUsuari.altaAdmin(nom, contrasenya);
	}
	
	/**
	 * Elimina del conjunt del controlador l'usuari nom = <tt>nom</tt> i en cas de ser Admin, disminueix el nº d'admins.
	 * @param nom nom de l'usuari a eliminar
	 * @throws Exception retorna excepció si el conjunt del controlador no conté cap usuari amb el mateix nom, si l'usuari és l'únic administrador del conjunt o si l'usuari del controlador no és administrador
	 */
	public void baixaUsuari(String nom) throws Exception {
            ctrlUsuari.baixaUsuari(nom);
	}
	
	/**
	 * Modifica el nom de l'usuari amb nom=nomActual per tal que nom=nomNou.
	 * @param nomActual nom actual de l'usuari
	 * @param nomNou nom nou de l'usuari
	 * @throws Exception retorna excepció si el conjunt del controlador no conté cap usuari amb nom = <tt>nomActual</tt> o si l'usuari del controlador no és administrador
	 */
	public void modificarNomUsuari(String nomActual, String nomNou) throws Exception {
            ctrlUsuari.modificarNom(nomActual, nomNou);
	}
	
	/**
	 * Modifica la contrasenya de l'usuari amb nom = <tt>nom</tt> per tal que contrasenya = <tt>novaContrasenya</tt>.
	 * @param nom nom de l'usuari a modificar
	 * @param novaContrasenya nova contrasenya de l'usuari
	 * @throws Exception retorna excepció si el conjunt del controlador no conté cap usuari amb nom = <tt>nom</tt> o si l'usuari del controlador no és administrador
	 */
	public void modificarContrasenya(String nom, String novaContrasenya) throws Exception {
            ctrlUsuari.modificarContrasenya(nom, novaContrasenya);
	}
	
	/**
	 * Modifica l'usuari amb nom = <tt>nom</tt> per tal que admin = <tt>true</tt>.
	 * @param nom nom de l'usuari a modificar
	 * @throws Exception retorna excepció si el conjunt no conté cap usuari amb nom = <tt>nom</tt>, si l'usuari ja és administrador o si l'usuari del controlador no és administrador
	 */
	public void ferAdmin(String nom) throws Exception {
            ctrlUsuari.ferAdmin(nom);
	}
	
	/**
	 * Retorna el nom i un booleà que indica si és admin. de l'usuari amb nom = <tt>nom</tt>
	 * @param nom nom de l'usuari a consultar
	 * @return nom i un booleà que indica si és admin. de l'usuari amb nom = <tt>nom</tt>
	 * @throws Exception retorna excepció si el conjunt del controlador no conté cap usuari amb nom = <tt>nom</tt>
	 */
	@SuppressWarnings("unused")
	public ArrayList<String> consultarUsuari(String nom) throws Exception {
            Usuari u1 = ctrlUsuari.carregarUsuari(nom);
            ArrayList<String> ret = new ArrayList<String>();
            ret.add(nom);
            if(conjuntUsuaris.esAdmin(nom)) ret.add("1");
            else ret.add("0");
            return ret;
	}
	
	/**
	 * Retorna un Set amb tots els noms dels usuaris del conjunt del controlador
	 * @return noms dels usuaris del conjunt del controlador
	 * @throws Exception ...
	 */
	public Set<String> consultarConjunt() throws Exception {
            return conjuntUsuaris.consultarNoms();
	}
		
	@SuppressWarnings("unused")
	private void setGraf(Graf grafAux) throws Exception {
            this.graf = grafAux;
            ctrlGraf.setGraf(grafAux);
	}
	
	@SuppressWarnings("unused")
	private Graf carregarGraf() throws Exception {
            return ctrlGraf.carregarGraf();
	}
	
	/**
 	 * Guarda el graf actual a un fitxer.
 	 * @throws Exception ...
 	 */
	public void guardarGraf() throws Exception {
            ctrlGraf.guardarGraf();
	}
	
	/**
 	 * S'ha afegit l'Autor amb nom=nom i label=label al graf.
 	 * @param nom nom de l'autor
 	 * @param label label de l'autor
 	 * @throws Exception ...
 	 */
	public void altaAutor(String nom, String label) throws Exception {
            ctrlGraf.altaAutor(nom, label);
	}
	
	/**
 	 * S'ha afegit la Conferencia amb nom=nom i label=label al graf.
	 * @param nom nom de la conferència
	 * @param label label de la conferència
	 * @throws Exception ...
 	 */
	public void altaConferencia (String nom, String label) throws Exception{
            ctrlGraf.altaConferencia(nom, label);
	}
	
	/**
 	 * S'ha afegit el Paper amb nom=nom i label=label al graf.
	 * @param nom nom del paper
	 * @param label label del paper
	 * @throws Exception ...
 	 */
	public void altaPaper (String nom, String label) throws Exception{
            ctrlGraf.altaPaper(nom, label);
	}
	
	/**
 	 * S'ha afegit el Terme amb nom=nom al graf.
	 * @param nom nom del terme
	 * @throws Exception ...
 	 */
	public void altaTerme (String nom) throws Exception{
            ctrlGraf.altaTerme(nom);
	}
	
	/**
     * S'ha eliminat l'Autor amb nom=nom del graf.
     * @param nom nom de l'autor
     * @throws Exception ...
     */
	public void baixaAutor(String nom) throws Exception {
            ctrlGraf.baixaAutor(nom);
	}
	
	/**
     * S'ha eliminat la Conferencia amb nom=nom del graf.
	 * @param nom nom de la conferència
	 * @throws Exception ...
     */
	public void baixaPaper(String nom) throws Exception {
            ctrlGraf.baixaPaper(nom);
	}
	
	/**
     * S'ha eliminat el Paper amb nom=nom del graf.
	 * @param nom nom del paper
	 * @throws Exception ...
     */
	public void baixaConferencia(String nom) throws Exception {
            ctrlGraf.baixaConferencia(nom);
	}
	
	/**
     * S'ha eliminat el Terme amb nom=nom del graf.
	 * @param nom nom del terme
	 * @throws Exception ...
     */
	public void baixaTerme(String nom) throws Exception {
            ctrlGraf.baixaTerme(nom);
	}
	
	/**
     * Retorna un vector amb el nom i el label de l'Autor.
	 * @param nom nom de l'autor
	 * @return nom i label de l'autor
	 * @throws Exception ... 
     */
	public ArrayList<String> consultarAutor(String nom) throws Exception{
            return ctrlGraf.consultarAutor(nom);
	}
	
	/**
     * Retorna un vector amb el nom i el label del Paper.
	 * @param nom nom del paper
	 * @return nom i label del paper
	 * @throws Exception ... 
     */
	public ArrayList<String> consultarPaper(String nom) throws Exception{
            return ctrlGraf.consultarPaper(nom);
	}
	
	/**
     * Retorna un vector amb el nom i el label de la Conferencia.
	 * @param nom nom de la conferència
	 * @return nom i label de la conferència
	 * @throws Exception ... 
     */
	public ArrayList<String> consultarConferencia(String nom) throws Exception{
            return ctrlGraf.consultarConferencia(nom);
	}
	
	/**
     * Retorna el label en format String.
	 * @param nom nom de l'entitat
	 * @param tipus tipus de l'entitat
	 * @return label de l'entitat
	 * @throws Exception ...
     */
	public String consultarLabel(String nom, String tipus) throws Exception{
            return ctrlGraf.consultarLabel(nom, tipus);
	}
	
	/**
     * Retorna un vector de vectors amb el nom i el tipus de totes les entitats del graf.
	 * @return noms i tipus de totes les entitats del graf
	 * @throws Exception ... 
     */
	public ArrayList<ArrayList<String>> consultarConjuntEntitats() throws Exception {
            return ctrlGraf.consultarConjuntEntitats();
	}
	
	/**
     * Retorna un vector amb el nom de tots els Autors del graf.
	 * @return noms de tots els autors del graf
	 * @throws Exception ...
     */
	public ArrayList<String> consultarAutors() throws Exception {
            return ctrlGraf.consultarAutors();
	}
	
	/**
     * Retorna un vector amb el nom de totes les Conferencies del graf.
	 * @return noms de totes les conferències del graf
	 * @throws Exception ... 
     */
	public ArrayList<String> consultarPapers() throws Exception {
            return ctrlGraf.consultarPapers();
	}
	
	/**
     * Retorna un vector amb el nom de tots els Papers del graf.
	 * @return noms de tots els papers del graf
	 * @throws Exception ...
     */
	public ArrayList<String> consultarConferencies() throws Exception {
            return ctrlGraf.consultarConferencies();
	}
	
	/**
     * Retorna un vector amb el nom de tots els Termes del graf.
 	 * @return noms de tots els termes del graf 
 	 * @throws Exception ...
     */
	public ArrayList<String> consultarTermes() throws Exception {
            return ctrlGraf.consultarTermes();
	}
	
	/**
     * S'ha afegit la relacio entre l'Autor amb nom=nomA i el Paper amb nom=nomP.
	 * @param nomA nom de l'autor
	 * @param nomP nom del paper
	 * @throws Exception ...
     */
	public void afegirRelacioAP (String nomA, String nomP) throws Exception {
            ctrlGraf.afegirRelacioAP(nomA, nomP);
	}
	
	/**
     * S'ha afegit la relacio entre la Conferencia amb nom=nomC i el Paper amb nom=nomP.
     * @param nomC nom de la conferència
     * @param nomP nom del paper
     * @throws Exception ...
     */
	public void afegirRelacioCP (String nomC, String nomP) throws Exception{
            ctrlGraf.afegirRelacioCP(nomC, nomP);
	}
	
	/**
     * S'ha afegit la relacio entre el Terme amb nom=nomT i el Paper amb nom=nomP.
	 * @param nomT nom del terme
	 * @param nomP nom del paper
	 * @throws Exception ...
     */
	public void afegirRelacioTP (String nomT, String nomP) throws Exception{
            ctrlGraf.afegirRelacioTP(nomT, nomP);
	}
	
	/**
     * S'ha esborrat la relacio entre l'Autor amb nom=nomA i el Paper amb nom=nomP.
	 * @param nomA nom de l'autor
	 * @param nomP nom del paper
	 * @throws Exception ...
     */
	public void esborrarRelacioAP (String nomA, String nomP) throws Exception{
            ctrlGraf.esborrarRelacioAP(nomA, nomP);
	}
	
	/**
     * S'ha esborrat la relacio entre la Conferencia amb nom=nomC i el Paper amb nom=nomP.
	 * @param nomC nom de la conferència
	 * @param nomP nom del paper
	 * @throws Exception ...
     */
	public void esborrarRelacioCP (String nomC, String nomP) throws Exception{
            ctrlGraf.esborrarRelacioAP(nomC, nomP);
	}
	
	/**
     * S'ha esborrat la relacio entre el Terme amb nom=nomT i el Paper amb nom=nomP.
	 * @param nomT nom del terme
	 * @param nomP nom del paper
	 * @throws Exception ...
     */
	public void esborrarRelacioTP (String nomT, String nomP) throws Exception{
            ctrlGraf.esborrarRelacioAP(nomT, nomP);
	}
	
	/**
	 * Afegeix un camí amb nom=nom, path=path i descripcio=descr
	 * @param nom Nom del camí
	 * @param path String que designa el camí
	 * @param descr Descripció del camí
	 * @throws ExcepcioCamiExistent El nom del camí que s'està intentant inserir ja existeix.
	 */
	public void afegirCami(String nom, String path, String descr) throws Exception {
            ctrlCamins.afegirCami(nom, path, descr);
	}
	
	/**
	 * Elimina el cami amb nom=nom
	 * @param nom Nom del camí a eliminar
	 * @throws ExcepcioCamiNoExistent No existeix cap camí de l'usuari amb el nom demanat
	 */
	public void esborrarCami(String nom) throws Exception {
            ctrlCamins.esborrarCami(nom);
	}
	
	/**
	 * Consulta el camí de l'usuari amb nom=nom
	 * @param nom Nom del camí a consultar
	 * @return Retorna els atributs d'un camí en forma d'Array: [nom,path,descripcio]
	 * @throws Exception ...
	 */
	public String[] consultarCamiUsuari(String nom) throws Exception {
            return ctrlCamins.consultarCamiUsuari(nom);
	}
	
	/**
	 * Consulta el camí predefinit amb nom=nom
	 * @param nom Nom del camí a consultar
	 * @return Retorna els atributs d'un camí en forma d'Array: [nom,path,descripcio]
	 * @throws Exception ...
	 */
	public String[] consultarCamiPredefinit(String nom) throws Exception {
            return ctrlCamins.consultarCamiPredefinit(nom);
	}
	
	/**
	 * Modifica el nom d'un camí de l'usuari amb nom=nom
	 * @param nom Nom del camí a modificar
	 * @param nomNou Nom que es posarà al camí
	 * @throws ExcepcioCamiNoExistent No existeix cap camí de l'usuari amb aquest nom
	 */
	public void modificarNom(String nom, String nomNou) throws Exception {
            ctrlCamins.modificarNom(nom, nomNou);
	}
	
	/**
	 * Modifica el path d'un camí de l'usuari amb nom=nom
	 * @param nom Nom del camí a modificar
	 * @param pathNou Path que es posarà al camí
	 * @throws ExcepcioCamiNoExistent No existeix cap camí de l'usuari amb aquest nom
	 */
	public void modificarPath(String nom, String pathNou) throws Exception {
            ctrlCamins.modificarPath(nom, pathNou);
	}
	
	/**
	 * Modifica la descripció d'un camí de l'usuari amb nom=nom
	 * @param nom Nom del camí a modificar
	 * @param descrNou Descripció que es posarà al camí
	 * @throws ExcepcioCamiNoExistent No existeix cap camí de l'usuari amb aquest nom
	 */
	public void modificarDescr(String nom, String descrNou) throws Exception {
            ctrlCamins.modificarDescr(nom, descrNou);
	}
	
	/**
	 * Consulta tots els camins de l'usuari
	 * @return Retorna una matriu de Strings. Cada fila representa un camí, que té el nom, el path i la descripció un a cada columna.
	 * @throws Exception ...
	 */
	public ArrayList<String[]> consultarCaminsUsuari() throws Exception {
            return ctrlCamins.consultarCaminsUsuari();
	}
	
	/**
	 * Consulta tots els camins predefinits
	 * @return Retorna una matriu de Strings. Cada fila representa un camí, que té el nom, el path i la descripció un a cada columna.
	 * @throws Exception ...
	 */
	public ArrayList<String[]> consultarCaminsPredefinits() throws Exception {
            return ctrlCamins.consultarCaminsPredefinits();
	}
        
	/**
	 * Guarda tots els camins de l'usuari en un fitxer
	 * @param nomUsuari Nom de l'usuari actual
	 * @throws Exception ...
	 */
    public void guardarCamins(String nomUsuari) throws Exception {
            ctrlCamins.guardarCamins(nomUsuari);
	}
    
    protected ConjuntCamins crearConjunt(ArrayList<String[]> camins) throws Exception {
            ConjuntCamins cc = new ConjuntCamins();
            for(int i = 0; i < camins.size(); ++i) {
                String nom = camins.get(i)[0];
                String path = camins.get(i)[1];
                String desc = camins.get(i)[2];
                Cami c = new Cami(nom, path, desc);
                cc.afegirCami(c);
            }
            return cc;
	}
}