package domini.usuaris;
import java.util.HashMap;
import java.util.Set;



public class ConjuntUsuaris {
	
  //ATRIBUTS	
	
	protected HashMap<String,Usuari> conjunt_usuaris;
	protected int nb_admins;
	
	
  //CREADORES	

	/**
	 * Crea un conjunt d'usuaris but, amb nº d'admins a 0.
	 */
	public ConjuntUsuaris(){
		conjunt_usuaris=new HashMap<String, Usuari>();
		nb_admins=0;
	}
	
	
  //MODIFICADORES	
	
	/**
	 * Crea un usuari amb nom = <tt>nom</tt>, contrasenya = <tt>contrasenya</tt> i admin = <tt>false</tt>, i l'afegeix al conjunt.
	 * @param nom nom de l'usuari a crear
	 * @param contrasenya contrasenya de l'usuari a crear
	 * @throws Exception retorna excepció si el conjunt d'usuaris ja conté un usuari amb el mateix nom
	 */
	public void afegirUsuari(String nom,String contrasenya) throws Exception{
		if (conjunt_usuaris.containsKey(nom)){
			throw new Exception("Ja existeix un usuari amb el nom "+nom);
		}
		else {
			Usuari u=new Usuari(nom,contrasenya);
			conjunt_usuaris.put(nom,u);
		}
	}
	
	/**
	 * Crea un usuari amb nom = <tt>nom</tt>, contrasenya = <tt>contrasenya</tt> i admin = <tt>false</tt>, i l'afegeix al conjunt, augmentant el nº d'admins.
	 * @param nom nom de l'usuari a crear
	 * @param contrasenya contrasenya de l'usuari a crear
	 * @throws Exception retorna excepció si el conjunt d'usuaris ja conté un usuari amb el mateix nom
	 */
	public void afegirAdmin(String nom, String contrasenya) throws Exception{
		if (conjunt_usuaris.containsKey(nom)){
			throw new Exception("Ja existeix un usuari amb el nom "+nom);
		}
		else {
			Usuari u=new Usuari(nom,contrasenya);
			u.fesAdmin();
			++nb_admins;
			conjunt_usuaris.put(nom,u);
		}
	}
	
	/**
	 * Elimina del conjunt l'usuari nom = <tt>nom</tt> i en cas de ser Admin, disminueix el nº d'admins.
	 * @param nom nom de l'usuari a eliminar
	 * @throws Exception retorna excepció si el conjunt no conté cap usuari amb el mateix nom o si l'usuari és l'únic administrador del conjunt
	 */
	public void eliminarUsuari(String nom) throws Exception{
		if (conjunt_usuaris.containsKey(nom)){
			Usuari u=conjunt_usuaris.get(nom);
			if(u.esAdmin()) {
				if (nb_admins>1) --nb_admins;
				else throw new Exception ("L'usuari "+nom+" és l'únic Administrador del sistema.");
			}
			conjunt_usuaris.remove(nom);
		}
		else {
			throw new Exception("No existeix cap usuari amb el nom "+nom);
		}
	}
	
	/**
	 * Modifica el nom de l'usuari amb nom=nomActual per tal que nom=nomNou.
	 * @param nomActual nom actual de l'usuari
	 * @param nomNou nom nou de l'usuari
	 * @throws Exception retorna excepció si el conjunt no conté cap usuari amb nom = <tt>nomActual</tt>
	 */
	public void modificarNom(String nomActual,String nomNou) throws Exception{
		if (conjunt_usuaris.containsKey(nomActual)){
			Usuari u=conjunt_usuaris.get(nomActual);
			u.setNom(nomNou);
			conjunt_usuaris.remove(nomActual);
			conjunt_usuaris.put(nomNou,u);
		}
		else {
			throw new Exception("No existeix cap usuari amb el nom "+nomActual);
		}
	}
	
	/**
	 * Modifica la contrasenya de l'usuari amb nom = <tt>nom</tt> per tal que contrasenya = <tt>nova_contra</tt>.
	 * @param nom nom de l'usuari a modificar
	 * @param nova_contra nova contrasenya de l'usuari
	 * @throws Exception retorna excepció si el conjunt no conté cap usuari amb nom = <tt>nom</tt>
	 */
	public void modificarContrasenya(String nom, String nova_contra) throws Exception{
		if (conjunt_usuaris.containsKey(nom)){
			Usuari u=conjunt_usuaris.get(nom);
			u.setContrasenya(nova_contra);
			conjunt_usuaris.put(nom, u);
		}
		else {
			throw new Exception("No existeix cap usuari amb el nom "+nom);
		}
	}
	
	/**
	 * Modifica l'usuari amb nom = <tt>nom</tt> per tal que admin = <tt>true</tt>.
	 * @param nom nom de l'usuari a modificar
	 * @throws Exception retorna excepció si el conjunt no conté cap usuari amb nom = <tt>nom</tt> o si l'usuari ja és administrador
	 */
	public void ferAdmin(String nom) throws Exception{
		if (conjunt_usuaris.containsKey(nom)){
			Usuari u=conjunt_usuaris.get(nom);
			if (u.esAdmin()) throw new Exception("L'usuari "+nom+ " ja és Administrador.");
			else {
				u.fesAdmin();
				++nb_admins;
				conjunt_usuaris.put(nom, u);
			}
		}
		else {
			throw new Exception("No existeix cap usuari amb el nom "+nom);
		}
	}
	
	/**
	 * Buida el conjunt d'usuaris
	 */
	public void buidar(){
		nb_admins=0;
		conjunt_usuaris.clear();
	}

	
  //CONSULTORES	
	
	/**
	 * Retorna l'usuari amb nom = <tt>nom</tt>
	 * @param nom nom de l'usuari a consultar
	 * @return usuari amb nom = <tt>nom</tt>
	 * @throws Exception retorna excepció si el conjunt no conté cap usuari amb nom = <tt>nom</tt>
	 */
	public Usuari consultarUsuari(String nom) throws Exception{
		if (!conjunt_usuaris.containsKey(nom)) throw new Exception("No existeix cap usuari amb el nom "+nom);
		return conjunt_usuaris.get(nom);
	}
	
	/**
	 * Retorna la contrasenya de l'usuari amb nom = <tt>nom</tt>
	 * @param nom nom de l'usuari a consultar
	 * @return contrasenya de l'usuari amb nom = <tt>nom</tt>
	 * @throws Exception retorna excepció si el conjunt no conté cap usuari amb nom = <tt>nom</tt>
	 */
	public String consultarContrasenya(String nom) throws Exception{
		if (!conjunt_usuaris.containsKey(nom)) throw new Exception("No existeix cap usuari amb el nom "+nom);
		return conjunt_usuaris.get(nom).getContrasenya();
	}
	
	/**
	 * Retorna true si l'usuari amb nom = <tt>nom</tt> és Admin, i false en cas contrari.
	 * @param nom nom de l'usuari a consultar
	 * @return si l'usuari amb nom = <tt>nom</tt> és Admin retorna 'true', altrament 'false'
	 * @throws Exception retorna excepció si el conjunt no conté cap usuari amb nom = <tt>nom</tt>
	 */
	public boolean esAdmin(String nom) throws Exception{
		if (!conjunt_usuaris.containsKey(nom)) throw new Exception("No existeix cap usuari amb el nom "+nom);
		return conjunt_usuaris.get(nom).esAdmin();
	}
	
	/**
	 * Retorna si existeix un usuari amb nom = <tt>nom</tt>
	 * @param nom nom de l'usuari a consultar
	 * @return retorna 'true' si existeix l'usuari amb nom = <tt>nom</tt>, altrament 'false'
	 */
	public boolean existeixUsuari(String nom){
		return conjunt_usuaris.containsKey(nom);
	}
	
	/**
	 * Retorna el nº d'admins del conjunt
	 * @return nº admins
	 */
	public int getNb_admins(){
		return nb_admins;
	}
	
	/**
	 * Retorna un Set amb tots els noms dels usuaris del conjunt
	 * @return noms dels usuaris del conjunt
	 */
	public Set<String> consultarNoms(){
		return conjunt_usuaris.keySet();
	}

}




