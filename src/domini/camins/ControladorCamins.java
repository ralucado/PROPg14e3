package domini.camins;
import java.util.*;
import persistencia.*;

public class ControladorCamins {
	private ConjuntCamins usuari;
	private ConjuntCamins predefinits;
	private CtrlPersistencia CtrlDad;
	
	/**
	 * Creadora de ControladorCamins
	 * @param usuari Nom de l'usuari actual
	 */
	public ControladorCamins(String usuari) throws Exception{			
		CtrlDad = new CtrlPersistencia();
		ArrayList<ArrayList<String>> caminsUsuari = CtrlDad.importarCaminsUsuari(usuari);
		ArrayList<ArrayList<String>> caminsPredefinits = CtrlDad.importarCaminsPredefinits();
		this.usuari = new ConjuntCamins();
		this.predefinits = new ConjuntCamins();
		
		for(int i = 0; i<caminsUsuari.size(); i++){
			Cami c = new Cami(caminsUsuari.get(i).get(0), caminsUsuari.get(i).get(1), caminsUsuari.get(i).get(2));
			this.usuari.afegirCami(c);
		}
		
		for(int i = 0; i<caminsPredefinits.size(); i++){
			Cami c = new Cami(caminsPredefinits.get(i).get(0), caminsPredefinits.get(i).get(1), caminsPredefinits.get(i).get(2));
			this.predefinits.afegirCami(c);
		}
	}
	
	/**
	 * Afegeix un camí amb nom=nom, path=path i descripcio=descr
	 * @param nom Nom del camí
	 * @param path String que designa el camí
	 * @param descr Descripció del camí
	 * @throws ExcepcioCamiExistent El nom del camí que s'està intentant inserir ja existeix.
	 */
	public void afegirCami(String nom, String path, String descr) throws ExcepcioCamiExistent, Exception{
		if(!usuari.existeixCami(nom)){
			Cami nouCami = new Cami(nom,path,descr);
			usuari.afegirCami(nouCami);
		}
		else{
			throw new ExcepcioCamiExistent(nom);
		}
	}
	
	/**
	 * Elimina el cami amb nom=nom
	 * @param nom Nom del camí a eliminar
	 * @throws ExcepcioCamiNoExistent No existeix cap camí de l'usuari amb el nom demanat
	 */
	public void esborrarCami(String nom) throws ExcepcioCamiNoExistent, Exception{			
		if(usuari.existeixCami(nom)){
			usuari.eliminarCami(nom);
		}
		else{
			throw new ExcepcioCamiNoExistent(nom);
		}
	}
	
	/**
	 * Consulta el camí de l'usuari amb nom=nom
	 * @param nom Nom del camí a consultar
	 * @return Retorna els atributs d'un camí en forma d'Array: [nom,path,descripcio]
	 */
	public String[] consultarCamiUsuari(String nom) throws Exception{			
		String cami[] = new String[3];
		Cami c = usuari.consultarCami(nom);
		cami[0] = c.getNom();
		cami[1] = c.getPath();
		cami[2] = c.getDescripcio();
		return cami;
	}
	
	/**
	 * Consulta el camí predefinit amb nom=nom
	 * @param nom Nom del camí a consultar
	 * @return Retorna els atributs d'un camí en forma d'Array: [nom,path,descripcio]
	 */
	public String[] consultarCamiPredefinit(String nom) throws Exception{		
		String cami[] = new String[3];
		Cami c = predefinits.consultarCami(nom);
		cami[0] = c.getNom();
		cami[1] = c.getPath();
		cami[2] = c.getDescripcio();
		return cami;
		
	}
	
	/**
	 * Modifica el nom d'un camí de l'usuari amb nom=nom
	 * @param nom Nom del camí a modificar
	 * @param nomNou Nom que es posarà al camí
	 * @throws ExcepcioCamiNoExistent No existeix cap camí de l'usuari amb aquest nom
	 */
	public void modificarNom(String nom, String nomNou) throws ExcepcioCamiNoExistent, Exception{
		if(usuari.existeixCami(nom)) modificarNomCami(nom, nomNou);		
		else{
			throw new ExcepcioCamiNoExistent(nom);
		}
	}
	
	/**
	 * Modifica el path d'un camí de l'usuari amb nom=nom
	 * @param nom Nom del camí a modificar
	 * @param pathNou Path que es posarà al camí
	 * @throws ExcepcioCamiNoExistent No existeix cap camí de l'usuari amb aquest nom
	 */
	public void modificarPath(String nom, String pathNou) throws ExcepcioCamiNoExistent, Exception{
		if(usuari.existeixCami(nom)) modificarPathCami(nom, pathNou);	
		else{
			throw new ExcepcioCamiNoExistent(nom);
		}
	}
	
	/**
	 * Modifica la descripció d'un camí de l'usuari amb nom=nom
	 * @param nom Nom del camí a modificar
	 * @param descrNou Descripció que es posarà al camí
	 * @throws ExcepcioCamiNoExistent No existeix cap camí de l'usuari amb aquest nom
	 */
	public void modificarDescr(String nom, String descrNou) throws ExcepcioCamiNoExistent, Exception{
		if(usuari.existeixCami(nom)) modificarDescrCami(nom, descrNou);
		else{
			throw new ExcepcioCamiNoExistent(nom);
		}
	}
	
	/**
	 * Consulta tots els camins de l'usuari
	 * @return Retorna una matriu de Strings. Cada fila representa un camí, que té el nom, el path i la descripció un a cada columna.
	 */
	public ArrayList<String[]> consultarCaminsUsuari() throws Exception{	
		
		int numCamins = usuari.getTamany();
		ArrayList<String[]> caminsUsuari = new ArrayList<String[]>();
		for(int i=0;i<numCamins;i++){
			caminsUsuari.add(consultarCamiUsuariN(i));
		}
		return caminsUsuari;
	}
	
	/**
	 * Consulta tots els camins predefinits
	 * @return Retorna una matriu de Strings. Cada fila representa un camí, que té el nom, el path i la descripció un a cada columna.
	 */
	public ArrayList<String[]> consultarCaminsPredefinits() throws Exception{
		
		int numCamins = predefinits.getTamany();
		ArrayList<String[]> caminsPredefinits = new ArrayList<String[]>();
		for(int i=0;i<numCamins;i++){
			caminsPredefinits.add(consultarCamiPredefinitN(i));
		}
		return caminsPredefinits;
	}
	
	/**
	 * Guarda tots els camins de l'usuari en un fitxer
	 * @param nomUsuari Nom de l'usuari actual
	 */
	public void guardarCamins(String nomUsuari) throws Exception{		
		ArrayList<ArrayList<String>> caminsUsuari = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i<usuari.getTamany();i++){
			ArrayList<String> s = new ArrayList<String>();
			s.add(usuari.getConjunt().get(i).getNom());
			s.add(usuari.getConjunt().get(i).getPath());
			s.add(usuari.getConjunt().get(i).getDescripcio());
			caminsUsuari.add(s);
		}
		
		CtrlDad.exportarCaminsUsuari(nomUsuari, caminsUsuari);
	}
	
	/**
	 * Consulta el camí de l'usuari número n ordenat per nom
	 * @param n Número del camí
	 * @return Retorna els atributs d'un camí en forma d'Array: [nom,path,descripcio]
	 * @throws Exception
	 */
	private String[] consultarCamiUsuariN(int n) throws Exception{		
		HashMap<String,Cami> camins = usuari.getConjunt();
		ArrayList<String> claus =  new ArrayList<String>(camins.keySet());
		Cami c = usuari.consultarCami((String)claus.get(n));
		String[] cami = new String[3];
		cami[0] = c.getNom();
		cami[1] = c.getPath();
		cami[2] = c.getDescripcio();
		return cami;
	}
	
	/**
	 * Consulta el camí predefinit número n ordenat per nom
	 * @param n Número del camí
	 * @return Retorna els atributs d'un camí en forma d'Array: [nom,path,descripcio]
	 * @throws Exception
	 */
	private String[] consultarCamiPredefinitN(int n) throws Exception{			
		HashMap<String,Cami> camins = predefinits.getConjunt();
		ArrayList<String> claus =  new ArrayList<String>(camins.keySet());
		Cami c = predefinits.consultarCami((String)claus.get(n));
		String[] cami = new String[3];
		cami[0] = c.getNom();
		cami[1] = c.getPath();
		cami[2] = c.getDescripcio();
		return cami;
	}
	
	/**
	 * Modifica el nom del camí de l'usuari amb nom nom
	 * @param nom Nom del camí a modificar
	 * @param nomNou Nom nou que se li assignarà al camí
	 * @throws Exception
	 */
	private void modificarNomCami(String nom, String nomNou) throws Exception{
		usuari.consultarCami(nom).setNom(nomNou);
	}
	
	/**
	 * Modifica el path del camí de l'usuari amb nom nom
	 * @param nom Nom del camí a modificar
	 * @param nomNou Path nou que se li assignarà al camí
	 * @throws Exception
	 */
	private void modificarPathCami(String nom, String pathNou) throws Exception{
		usuari.consultarCami(nom).setPath(pathNou);
	}
	
	/**
	 * Modifica la descripció del camí de l'usuari amb nom nom
	 * @param nom Nom del camí a modificar
	 * @param nomNou Descripció nova que se li assignarà al camí
	 * @throws Exception
	 */
	private void modificarDescrCami(String nom, String descrNou) throws Exception{
		usuari.consultarCami(nom).setDescripcio(descrNou);
	}
}
