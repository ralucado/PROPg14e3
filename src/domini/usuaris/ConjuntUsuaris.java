package domini.usuaris;
import java.util.HashMap;
import java.util.Set;



public class ConjuntUsuaris {
	
  //ATRIBUTS	
	
	private HashMap<String, Usuari> conjunt_usuaris;
	private int nb_admins;
	
	
  //CREADORES	
	
	/*Pre: -
	  Post: Crea un conjunt d'usuaris buit, amb nº d'admins a 0.*/
	public ConjuntUsuaris(){
		conjunt_usuaris=new HashMap<String, Usuari>();
		nb_admins=0;
	}
	
	
  //MODIFICADORES	
	
	/*Pre: No existeix cap usuari amb nom=nom.
	  Post: Crea un usuari amb nom=nom, contrasenya=contrasenya i admin=false, i l'afegeix al conjunt.*/
	public void afegirUsuari(String nom,String contrasenya) throws Exception{
		if (conjunt_usuaris.containsKey(nom)){
			throw new Exception("Ja existeix un usuari amb el nom "+nom);
		}
		else {
			Usuari u=new Usuari(nom,contrasenya);
			conjunt_usuaris.put(nom,u);
		}
	}
	
	/*Pre: No existeix cap usuari amb nom=nom.
	  Post: Crea un usuari amb nom=nom, contrasenya=contrasenya i admin=true, i l'afegeix al conjunt, augmentant el nº d'admins.*/
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
	
	/*Pre: Si l'usuari amb nom=nom és Admin, nb_admins>1. Existeix un usuari amb nom=nom.
	  Post: Elimina del conjunt l'usuari nom=nom i en cas de ser Admin, disminueix el nº d'admins.*/
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
	
	/*Pre: Existeix un usuari amb nom=nomActual.
	  Post: Modifica el nom de l'usuari amb nom=nomActual per tal que nom=nomNou.*/
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
	
	/*Pre: Existeix un usuari amb nom=nom.
	  Post: Modifica la contrasenya de l'usuari amb nom=nom per tal que contrasenya=nova_contra.*/
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
	
	/*Pre: Existeix un usuari amb nom=nom i no és Admin.
	  Post: Modifica l'usuari amb nom=nom per tal que admin=true.*/
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
	
	/*Pre: -
	  Post: conjunt_usuaris no conté cap usuari.*/
	public void buidar(){
		nb_admins=0;
		conjunt_usuaris.clear();
	}

	
  //CONSULTORES	
	
	/*Pre: Ha d'existir l'usuari amb nom=nom dins del conjunt d'usuaris.
	  Post: Retorna l'usuari amb nom=nom.*/
	public Usuari consultarUsuari(String nom) throws Exception{
		if (!conjunt_usuaris.containsKey(nom)) throw new Exception("No existeix cap usuari amb el nom "+nom);
		return conjunt_usuaris.get(nom);
	}
	
	/*Pre: Ha d'existir l'usuari amb nom=nom dins del conjunt d'usuaris.
	  Post: Retorna la contrasenya de l'usuari amb nom=nom.*/
	public String consultarContrasenya(String nom) throws Exception{
		if (!conjunt_usuaris.containsKey(nom)) throw new Exception("No existeix cap usuari amb el nom "+nom);
		return conjunt_usuaris.get(nom).getContrasenya();
	}
	
	/*Pre: Ha d'existir l'usuari amb nom=nom dins del conjunt d'usuaris.
	  Post: Retorna true si l'usuari amb nom=nom és Admin, i false en cas contrari.*/
	public boolean esAdmin(String nom) throws Exception{
		if (!conjunt_usuaris.containsKey(nom)) throw new Exception("No existeix cap usuari amb el nom "+nom);
		return conjunt_usuaris.get(nom).esAdmin();
	}
	
	/*Pre: -
	  Post: Retorna true si existeix un usuari amb nom=nom, i retorna false en cas contrari.*/
	public boolean existeixUsuari(String nom){
		return conjunt_usuaris.containsKey(nom);
	}
	
	/*Pre: -
	  Post: Retorna el nº d'admins.*/
	public int getNb_admins(){
		return nb_admins;
	}
	
	/*Pre: -
	  Post: Retorna un Set amb tots els noms dels usuaris del conjunt.*/
	public Set<String> consultarNoms(){
		return conjunt_usuaris.keySet();
	}

}




