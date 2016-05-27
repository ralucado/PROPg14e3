package domini.usuaris;

public class Usuari {
	
	protected String nom;
	protected String contrasenya;
	protected boolean admin;
	
	/**
	 * Crea un Usuari sense nom ni contrasenya
	 */
	public Usuari(){}
	
	/**
	 * Crea un Usuari amb nom d'usuari <tt>nom</tt> i contrasenya <tt>contrasenya</tt> 
	 * @param nom nom d'usuari
	 * @param contrasenya contrasenya de l'usuari
	 */
	public Usuari(String nom, String contrasenya) {
		this.nom = nom;
		this.contrasenya = contrasenya;
		admin = false;
	}
	
	/**
	 * Fa administrador l'usuari  
	 */
	public void fesAdmin () {
		admin = true;
	}
	
	/**
	 * Assigna el nom <tt>nom</tt> a l'usuari
	 * @param nom nou nom d'usuari
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Consulta el nom de l'usuari
	 * @return el nom d'usuari
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Assigna la contrasenya <tt>contrasenya</tt> a l'usuari
	 * @param contrasenya nova contrasenya
	 */
	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}
	
	/**
	 * Consulta la contrasenya de l'usuari
	 * @return la contrasenya
	 */
	public String getContrasenya() {
		return contrasenya;
	}
	
	/**
	 * Retorna si l'usuari és administrador o no
	 * @return 'true' si és administrador i 'false', altrament
	 */
	public boolean esAdmin() {
		return admin;
	}
	
}
