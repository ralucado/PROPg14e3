package TestQueryClustering;

/**
 * Representa un cami, amb un nom, un path d'entitats i una descripcio
 * @author Martiruhay
 *
 */
public class Cami {
	private String nom;
	private String descripcio;
	private String path;
	
	/**
	 * Creadora amb paràmetres
	 * @param nom Nom del nou cam�
	 * @param path Path d'entitats del nou cam�
	 * @param descripcio Descripci� del nou cam�
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
	
	private static Boolean badPath(String p){
		for (int i = 0; i < p.length(); ++i){
			char c = p.charAt(i);
			if (c != 'P' && c != 'A' && c != 'C' && c != 'T') return true;
			if (i%2 != 0 && c != 'P') return true;
		}
		return false;
	}
}
