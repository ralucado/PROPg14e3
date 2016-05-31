package domini.camins;
import java.util.HashMap;

/**
 * Representa un conjunt de camins definits
 * @author Marti Lloveras Rosales
 *
 */
public class ConjuntCamins {
	protected HashMap<String,Cami> conjunt;
	protected String nom;
	
	/**
	 * Creadora per defecte
	 */
	public ConjuntCamins() {
		conjunt = new HashMap<String,Cami>();
	}
	
	public void setNom(String nom){
		this.nom = nom;
	}
	
	public String getNom(){
		return this.nom;
	}
	
	/**
	 * Retorna el mapa que representa el conjunt de camins
	 * @return conjunt de camins
	 */
	public HashMap<String,Cami> getConjunt(){
		return conjunt;
	}
	
	/**
	 * Afegeix un camí al conjunt
	 * @param c Camí a afegir
	 * @throws Exception si el nom és repetit
	 */
	public void afegirCami(Cami c)throws Exception{
		if (conjunt.containsKey(c.getNom())) throw new Exception("! Nom repetit");
		conjunt.put(c.getNom(), c);
	}
	
	/**
	 * Elimina un camí del conjunt
	 * @param nom Nom del camí a eliminar
	 * @throws Exception si el camí no existeix
	 */
	public void eliminarCami(String nom) throws Exception{
		if (!conjunt.containsKey(nom)) throw new Exception("! No existeix el cami");
		conjunt.remove(nom);
	}
	
	/**
	 * Consulta un camí del conjunt
	 * @param nom Nom del camí a consultar
	 * @return Retorna el camí consultat
	 * @throws Exception si el camí no existeix
	 */
	public Cami consultarCami(String nom) throws Exception{
		if (!conjunt.containsKey(nom)) throw new Exception("! No existeix el cami");
		return conjunt.get(nom);
	}
	
	/**
	 * Modifica el nom d'un cami del conjunt
	 * @param nomVell Nom del cami a modificar
	 * @param nomNou Nom que s'assigna al cami a modificar
	 * @throws Exception Excepcio si el cami no existeix al conjunt
	 */
	public void modificarNomCami(String nomVell, String nomNou) throws Exception{
		if (!conjunt.containsKey(nomVell)) throw new Exception("! No existeix el cami");
		if (conjunt.containsKey(nomNou)) throw new Exception("! Nom repetit");
		Cami c = conjunt.get(nomVell);
		conjunt.remove(nomVell);
		c.setNom(nomNou);
		conjunt.put(nomNou, c);
	}
	
	/**
	 * Consulta si el cami indicat es troba al conjunt
	 * @param nom Nom del cami a consultar
	 * @return Cert si el cami especificat esta al conjunt, fals altrament
	 */
	public boolean existeixCami(String nom){
		return conjunt.containsKey(nom);
	}
	
	/**
	 * Consulta el nombre de camins del conjunt
	 * @return Tamany del conjunt
	 */
	public int getTamany(){
		return conjunt.size();
	}
	
	/**
	 * El conjunt passa a ser un conjunt buit
	 */
	public void buidar(){
		conjunt.clear();
	}
	
}