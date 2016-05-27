package domini.camins;

/**
 * Representa un cami, amb un nom, un path d'entitats i una descripcio
 * @author Martiruhay
 *
 */
public class Cami {
	protected String nom;
	protected String descripcio;
	protected String path;
	
	/**
	 * Creadora amb paràmetres
	 * @param nom Nom del nou camí
	 * @param path Path d'entitats del nou camí
	 * @param descripcio Descripció del nou camí
	 */
	public Cami(String nom, String path, String descripcio) throws Exception {
		if (badPath(path)) throw new Exception("! Path incorrecte");
        this.nom = nom;
        this.descripcio = descripcio;
        this.path = path;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) throws Exception{
		if (badPath(path)) throw new Exception("! Path incorrecte");
		this.path = path;
	}
	
	/**
	 * Comprova si el path passat per parametre te un format correcte
	 * @param p Path a comprovar
	 * @return True si el path es incorrecte, false altrament
	 */
	protected static boolean badPath(String p){
		if(p.length() == 0) return false;
		char c = p.charAt(0);
		if (c != 'P' && c != 'A' && c != 'C' && c != 'T') return true;
		boolean b = (c != 'P');
		for (int i = 1; i < p.length(); ++i){
			c = p.charAt(i);
			if (b && c != 'P') return true;
			if (!b && c == 'P') return true;
			if (c != 'P' && c != 'A' && c != 'C' && c != 'T') return true;
			b = !b;
		}
		return false;
	}
}