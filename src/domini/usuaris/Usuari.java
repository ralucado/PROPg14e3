package domini.usuaris;

public class Usuari {
	
	private String nom;
	private String contrasenya;
	private boolean admin;
	
	public Usuari(){}
	
	public Usuari(String nom, String contrasenya) {
		this.nom = nom;
		this.contrasenya = contrasenya;
		admin = false;
	}
	
	public void fesAdmin () {
		admin = true;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}
	
	public String getContrasenya() {
		return contrasenya;
	}
	
	public boolean esAdmin() {
		return admin;
	}
	
}
