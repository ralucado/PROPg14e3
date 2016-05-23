package domini.usuaris;

public class myUsuari extends Usuari {
	
	public myUsuari(){
		super();
	}
	
	public myUsuari(String nom, String contrasenya) {
		super(nom, contrasenya);
	}
	
	public void fesNoAdmin () {
		admin = false;
	}	
}
