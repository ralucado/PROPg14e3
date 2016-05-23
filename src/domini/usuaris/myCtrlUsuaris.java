package domini.usuaris;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class myCtrlUsuaris extends CtrlUsuaris {

	public myCtrlUsuaris() throws Exception {
		super();
	}

	public ArrayList<ArrayList<String>> getDadesUsuaris() {
		ArrayList<ArrayList<String>> users = new ArrayList<ArrayList<String>>();
		TreeMap<String,Usuari> cjt = new TreeMap<String,Usuari>(conjuntUsuaris.getConjunt());
		for (Usuari user : cjt.values()) {
			ArrayList<String> userData = new ArrayList<String>();
			userData.add(user.getNom());
			userData.add(user.getContrasenya());
			userData.add(Boolean.toString(user.esAdmin()));
			
			users.add(userData);
		}
		
		return users;
	}
	
	public void ferNoAdmin(String nom) throws Exception{
        if (usuari.esAdmin())
            conjuntUsuaris.ferNoAdmin(nom);
        else
            throw new Exception("No tens permis per donar permisos d'administrador");
    }
}
