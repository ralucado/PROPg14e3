package domini.camins;
import java.util.ArrayList;
import java.util.HashMap;

import domini.queries.Pair;
import persistencia.CtrlPersistencia;

public class ControladorCamins {
	private ConjuntCamins usuari;
	private ConjuntCamins predefinits;
	private CtrlPersistencia CtrlDad;
	
	/**
	 * Creadora de ControladorCamins
	 * @param usuari Nom de l'usuari actual
	 * @throws Exception ...
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
			importarMatriusCami(c);
		}
		
		for(int i = 0; i<caminsPredefinits.size(); i++){
			Cami c = new Cami(caminsPredefinits.get(i).get(0), caminsPredefinits.get(i).get(1), caminsPredefinits.get(i).get(2));
			this.predefinits.afegirCami(c);
			importarMatriusCami(c);
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
	 * @throws Exception ...
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
	 * @throws Exception ...
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
	 * Consulta un camí existent
	 * @param nom Nom del camí a consultar
	 * @return Retorna el camí amb el nom demanat 
	 * @throws Exception ...
	 */
	public Cami consultarCamiExt(String nom) throws Exception{
		Cami c;
		if(predefinits.existeixCami(nom)) c = predefinits.consultarCami(nom);
		else if(usuari.existeixCami(nom)) c = usuari.consultarCami(nom);
		else throw new Exception("El camí no existeix");
		return c;
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
	 * @throws Exception ...
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
	 * @throws Exception ...
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
	 * @throws Exception ...
	 */
	public void guardarCamins(String nomUsuari) throws Exception{		
		ArrayList<ArrayList<String>> caminsUsuari = new ArrayList<ArrayList<String>>();
		for (Cami c : usuari.getConjunt().values()) {
			ArrayList<String> s = new ArrayList<String>();
			s.add(c.getNom());
			s.add(c.getPath());
			s.add(c.getDescripcio());
			caminsUsuari.add(s);
			exportarMatriusCami(c);
		}
		CtrlDad.exportarCaminsUsuari(nomUsuari, caminsUsuari);
		
		for(Cami c : predefinits.getConjunt().values()){
			exportarMatriusCami(c);
		}
	}
	
	/**
	 * Guarda les matrius d'un camí en un fitxer
	 * @param c Camí que es vol guardar les matrius
	 * @throws Exception ...
	 */
	public void exportarMatriusCami(Cami c) throws Exception{

		SparseMatrixBool matriuE, matriuD;
		Pair<SparseMatrixBool, SparseMatrixBool> matrius = c.getMatrius();
		matriuE = matrius.first;
		matriuD = matrius.second;
		if(matriuE != null && matriuD != null){
			//matriuE
			ArrayList<ArrayList<String>> matriuE2 = new ArrayList<ArrayList<String>>();
			ArrayList<HashMap<Integer,Boolean>> rowsE = matriuE.getRows();
			
			for(int i = 0; i<rowsE.size(); i++){
				ArrayList<String> aux = new ArrayList<String>();
				for(Integer k : rowsE.get(i).keySet()){
					String clau = String.valueOf(k);
					aux.add(clau);
				}
				matriuE2.add(aux);
			}
			ArrayList<String> midesE = new ArrayList<String>();
			midesE.add(String.valueOf(matriuE.getNRows()));
			midesE.add(String.valueOf(matriuE.getNCols()));
			matriuE2.add(midesE);
			
			//matriuD
			ArrayList<ArrayList<String>> matriuD2 = new ArrayList<ArrayList<String>>();
			ArrayList<HashMap<Integer,Boolean>> rowsD = matriuE.getRows();
			
			for(int i = 0; i<rowsD.size(); i++){
				ArrayList<String> aux = new ArrayList<String>();
				for(Integer k : rowsD.get(i).keySet()){
					String clau = String.valueOf(k);
					aux.add(clau);
				}
				matriuE2.add(aux);
			}
			
			ArrayList<String> midesD = new ArrayList<String>();
			midesD.add(String.valueOf(matriuD.getNRows()));
			midesD.add(String.valueOf(matriuD.getNCols()));
			matriuD2.add(midesD);
			
			CtrlDad.exportarMatrius(matriuE2, matriuD2, c.getPath());
		}
		
	}
	
	/**
	 * Carrega les matrius d'un camí des d'un fitxer
	 * @param c Camí a importar les matrius
	 * @throws Exception ...
	 */
	 public void importarMatriusCami(Cami c) throws Exception{
		if(CtrlDad.existeixenMatrius(c.getPath())){
			ArrayList<ArrayList<String>> matriuE = CtrlDad.importarMatriuLeft(c.getPath());
			ArrayList<ArrayList<String>> matriuD = CtrlDad.importarMatriuRight(c.getPath());
			if(!matriuE.isEmpty() || !matriuD.isEmpty()){
				//matriuE
				int nRowsE = Integer.valueOf(matriuE.get(matriuE.size()-1).get(0));
				int nColsE = Integer.valueOf(matriuE.get(matriuE.size()-1).get(1));
				SparseMatrixBool matriuE2 = new SparseMatrixBool(nRowsE, nColsE);
				
				for(int i = 0; i<matriuE.size()-1;i++){
					for(int j = 0; j<matriuE.get(i).size(); j++){
						
						matriuE2.set(i, j, true);
					}
				}
				
				
				//matriuD
				int nRowsD = Integer.valueOf(matriuE.get(matriuE.size()-1).get(0));
				int nColsD = Integer.valueOf(matriuE.get(matriuE.size()-1).get(1));
				SparseMatrixBool matriuD2 = new SparseMatrixBool(nRowsD, nColsD);
			
						
				for(int i = 0; i<matriuD.size();i++){
					for(int j = 0; j<matriuD.get(i).size(); j++){
						matriuD2.set(i, j, true);
					}
				}
				
				c.setMatrius(matriuE2, matriuD2);
			}
		}
		
	}


	/**
	 * Reseteja les matrius necessaries quan s'ha realitzat un canvi en el graf
	 * @param c Tipus de l'entitat en que s'ha produit un canvi (P,T,C,A)
	 */
	public void resetMatrius(char c){
		CtrlDad.esborrarMatrius(c);
		for(Cami ca : usuari.getConjunt().values()){
			String path = ca.getPath();
			int k = path.indexOf(c);
			if(k!=-1) ca.resetMatrius();
		}
	}
	
	
	/**
	 * Consulta el camí de l'usuari número n ordenat per nom
	 * @param n Número del camí
	 * @return Retorna els atributs d'un camí en forma d'Array: [nom,path,descripcio]
	 * @throws Exception ...
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
	 * @throws Exception ...
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
	 * @throws Exception ...
	 */
	private void modificarNomCami(String nom, String nomNou) throws Exception{
		usuari.consultarCami(nom).setNom(nomNou);
	}
	
	/**
	 * Modifica el path del camí de l'usuari amb nom nom
	 * @param nom Nom del camí a modificar
	 * @param nomNou Path nou que se li assignarà al camí
	 * @throws Exception ...
	 */
	private void modificarPathCami(String nom, String pathNou) throws Exception{
		usuari.consultarCami(nom).setPath(pathNou);
	}
	
	/**
	 * Modifica la descripció del camí de l'usuari amb nom nom
	 * @param nom Nom del camí a modificar
	 * @param nomNou Descripció nova que se li assignarà al camí
	 * @throws Exception ...
	 */
	private void modificarDescrCami(String nom, String descrNou) throws Exception{
		usuari.consultarCami(nom).setDescripcio(descrNou);
	}
}
