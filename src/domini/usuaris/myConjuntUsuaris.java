package domini.usuaris;

import java.util.HashMap;

public class myConjuntUsuaris extends ConjuntUsuaris {
	
	public myConjuntUsuaris() {
		super();
	}

	public HashMap<String, myUsuari> getConjunt() {
		return conjunt_usuaris;
	}
	
	public void ferNoAdmin(String nom) throws Exception{
		if (conjunt_usuaris.containsKey(nom)){
			myUsuari u=conjunt_usuaris.get(nom);
			if (!u.esAdmin()) throw new Exception("L'usuari "+nom+ " no era Administrador.");
			else {
				u.fesNoAdmin();
				--nb_admins;
				conjunt_usuaris.put(nom, u);
			}
		}
		else {
			throw new Exception("No existeix cap usuari amb el nom "+nom);
		}
	}
}
