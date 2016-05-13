
import java.util.*;

public class ControladorUsuaris{

	private ControladorCamins CtrlCam;
	private ConjuntUsuaris usuaris;
	private String usuariAct;
	
	/**
	 * Creadora del Controlador d'Usuaris
	 * @param llistaUsuaris HashMap amb les parelles d'Usuari i Contrasenya
	 * @param llistaAdmins HashMap amb les parelles d'Admin i Contrasenya
	 * @throws Exception 
	 */
	public ControladorUsuaris(ArrayList<String> l) throws Exception{
		usuaris = new ConjuntUsuaris();
		usuaris.carregarConjunt_Usuaris(l);
	}
	
	/**
	 * logIn d'un Usuari amb la seva contrasenya
	 * @param usuari Nom de l'usuari
	 * @param contr Contrasenya de l'usuari
	 * @throws ExcepcioIdentificacio La identificacio de l'usuari no es correcte
	 */
	public void logIn(String usuari, String contr) throws ExcepcioIdentificacio, Exception{
		if(usuaris.existeixUsuari(usuari)){
			if(comprovarContrasenya(usuari, contr)){
				CtrlCam = new ControladorCamins(usuari);
				usuariAct = usuari;
			}
			else{
				throw new ExcepcioIdentificacio();
			}
		}
		else{
			throw new ExcepcioIdentificacio();
		}
	}

	/**
	 * logOut de l'usuari
	 * @throws ExcepcioNoLogin No s'ha fet login
	 */
	public void logOut() throws ExcepcioNoLogin, Exception{
		if(usuariAct==null) throw new ExcepcioNoLogin();
		guardar();
		usuariAct = null;
		CtrlCam = null;
	}
	
	/**
	 * Creacio d'un nou compte d'usuari
	 * @param usuari Nom d'usuari del nou compte 
	 * @param contr	Contrasenya del nou compte
	 * @throws Exception L'usuari que s'esta intentant crear ja existeix o no és correcte
	 */
	public void crearUsuari(String usuari, String contr) throws Exception{
		if(!usuaris.existeixUsuari(usuari)){
			if(usuariValid(usuari)){
					usuaris.afegirUsuari(usuari, contr);
			}
			else throw new Exception("L'usuari no és correcte");
		}
		else{
			throw new Exception("L'usuari ja existeix");
		}
	}
	
	/**
	 * Esborra l'usuari actual, requereix que s'hagi fet logIn
	 * @param contr Contrasenya de l'usuari actual
	 * @throws ExcepcioIdentificacio La identificacio de l'usuari no és correcte
	 * @throws ExcepcioNoLogin No s'ha fet logIn
	 */
	public void esborrarUsuari(String contr) throws ExcepcioNoLogin, ExcepcioIdentificacio, Exception{
		if(usuariAct==null){
			throw new ExcepcioNoLogin();
		}
		else if(comprovarContrasenya(usuariAct, contr)){
			usuaris.eliminarUsuari(usuariAct);
			logOut();
		}
		else{
			throw new ExcepcioIdentificacio();
		}
	}
	
	/**
	 * Canvia la contrasenya de l'usuari actual
	 * @param ContrOld Contrasenya antiga de l'usuari que s'usa com a verificació
	 * @param ContrNew Contrasenya nova de l'usuari
	 * @throws ExcepcioIdentificacio La identificació no és correcte
	 */
	public void canviarContrasenya(String ContrOld, String ContrNew) throws ExcepcioIdentificacio, Exception{
		if(usuariAct==null){
			throw new ExcepcioNoLogin();
		}
		else if(comprovarContrasenya(usuariAct,ContrOld)){			
			usuaris.modificarContrasenya(usuariAct, ContrNew);
		}
		else{
			throw new ExcepcioIdentificacio();
		}
	}
	
	/**
	 * Consulta tots els usuaris existents
	 * @return Retorna una matriu d'Strings. Cada fila és un usuari diferent. La primera columna és l'usuari, la segona la contrasenya.
	 * @throws Exception
	 */
	public String[][] consultarTotsUsuaris() throws Exception{
		int mida = numeroUsuaris();
		String[][] TotsUsuaris = new String[mida][2];
		for(int i = 0; i<mida; i++){
			Usuari u = consultaUsuariN(i);
			TotsUsuaris[i][0] = u.getNom();
			TotsUsuaris[i][1] = u.getContrasenya();
		}
		return TotsUsuaris;
	}
	
	/**
	 * Afegeix un camí a l'usuari.
	 * @param nom Nom del camí
	 * @param path Path del camí
	 * @param descr Descripció del camí
	 * @throws Exception
	 */
	public void afegirCami(String nom, String path, String descr) throws Exception{
		if(usuariAct==null){
			throw new ExcepcioNoLogin();
		}
		else{
			CtrlCam.afegirCami(nom, path, descr);
		}
	}
	
	/**
	 * Esborra un camí de l'usuari.
	 * @param nom Nom del camí a esborrar
	 * @throws Exception
	 */
	public void esborrarCami(String nom) throws Exception{
		if(usuariAct==null){
			throw new ExcepcioNoLogin();
		}
		else CtrlCam.esborrarCami(nom);
	}
	
	/**
	 * Modifica un camí de l'usuari.
	 * @param nom Nom del camí a modificar
	 * @param numCanvi 0, 1 o 2. 0 equival a modificar el nom del camí, 1 el path i 2 la descripció 
	 * @param canvi Paràmetre pel qual es substituirà l'atribut seleccionat segons numCanvi
	 * @throws Exception
	 */
	public void modificarCami(String nom, int numCanvi, String canvi) throws Exception{
		if(usuariAct==null) throw new ExcepcioNoLogin();
		if(numCanvi==0){
			CtrlCam.modificarNom(nom, canvi);
		}
		else if(numCanvi==1){
			CtrlCam.modificarPath(nom, canvi);
		}
		else if(numCanvi==2){
			CtrlCam.modificarDescr(nom, canvi);
		}
	}
	
	/**
	 * Consulta un camí de l'usuari
	 * @param nom Nom del camí de l'usuari a consultar
	 * @return Retorna un Array d'Strings amb els camps del camí (nom, path, descripció)
	 * @throws Exception
	 */
	public String[] consultarCamiUsuari(String nom) throws Exception{
		if(usuariAct==null) throw new ExcepcioNoLogin();
		String[] cami = new String[3];
		cami = CtrlCam.consultarCamiUsuari(nom);
		return cami;
	}
	
	/**
	 * Consulta un camí predefinit
	 * @param nom Nom del camí predefinit a consultar
	 * @return Retorna un Array d'Strings amb els camps del camí (nom, path, descripció)
	 * @throws Exception
	 */
	public String[] consultarCamiPredefinit(String nom) throws Exception{
		if(usuariAct==null) throw new ExcepcioNoLogin(); 
		String[] cami = new String[3];
		cami = CtrlCam.consultarCamiPredefinit(nom);
		return cami;
	}
	
	/**
	 * Consulta tots els camins de l'usuari
	 * @return Retorna una matriu de Strings. Cada fila representa un camí, que té el nom, el path i la descripció un a cada columna.
	 */
	public ArrayList<String[]> consultarCaminsUsuari() throws Exception{
		if(usuariAct==null) throw new ExcepcioNoLogin();
		else return CtrlCam.consultarCaminsUsuari();
	}
	
	/**
	 * Consulta tots els camins predefinits
	 * @return Retorna una matriu de Strings. Cada fila representa un camí, que té el nom, el path i la descripció un a cada columna.
	 */
	public ArrayList<String[]> consultarCaminsPredefinits() throws Exception{
		if(usuariAct==null) throw new ExcepcioNoLogin();
		else return CtrlCam.consultarCaminsPredefinits();
	}
	
	/**
	 * Guarda els camins de l'usuari actual i la llista d'usuaris en fitxers.
	 * @throws Exception
	 */
	public void guardar() throws Exception{
		ControladorDades CD = ControladorDades.getInstance();
		CtrlCam.guardarCamins(usuariAct);
		String[][] llistaUsuaris = new String[numeroUsuaris()][2];
		
		for(int i = 0; i<numeroUsuaris(); i++){
			Usuari u = consultaUsuariN(i);
			llistaUsuaris[i][0] = u.getNom();
			llistaUsuaris[i][1] = u.getContrasenya();
		}
		
		CD.guardarUsuaris(llistaUsuaris);
		
	}
	
	/**
	 * Comprova que la contrasenya contr sigui la de l'Usuari usuari.
	 * @param usuari Usuari al que ha de pertànyer la contrasenya.
	 * @param contr Contrasenya a comprovar.
	 * @return Retorna true en cas que contr sigui la contrasenya de l'Usuari usuari.
	 * @throws Exception
	 */
	private boolean comprovarContrasenya(String usuari, String contr) throws Exception{
		if(usuaris.existeixUsuari(usuari)){
			String contrCorr = usuaris.consultarContrasenya(usuari);
			return (contrCorr.equals(contr));
		}
		else{
			return false;
		}
	}
	
	/**
	 * Comprova si un nom d'usuari és vàlid.
	 * @param usuari Nom de l'usuari a comprovar
	 * @return Returna true si l'usuari és correcte (no conté caràcters no desitjats)
	 */
	private boolean usuariValid(String usuari){
		for(int i = 0; i<usuari.length();i++){
			char c = usuari.charAt(i);
			if(c=='/' || c=='.' || c=='~') return false;
		}
		return true;
	}
	
	/**
	 * Consulta el número d'usuaris que existeixen
	 * @return Retorna el número d'usuaris que existeixen
	 */
	private int numeroUsuaris(){
		Set<String> nomsUsuaris = usuaris.consultarNoms();
		return nomsUsuaris.size();
	}
	
	/**
	 * Consulta l'usuari N del conjunt d'usuaris.
	 * @param n Número de l'usuari a consultar
	 * @return Retorna l'Usuari demanat
	 * @throws Exception
	 */
	private Usuari consultaUsuariN(int n) throws Exception{
		Set<String> nomsUsuaris = usuaris.consultarNoms();
		Iterator<String> it = nomsUsuaris.iterator();
		int i = 0;
		String nom = new String();
		while(i<=n){ nom = it.next(); ++i;}
		return usuaris.consultarUsuari(nom);
	}
}
