package domini.graf;

public class Autor extends Entitat {

	
	/**
	 * PRE: -
	 * POST: Crea una entitat de tipus Autor amb nom el parametre i li assigna una id
	 */
	public Autor(String nom) {
		super(nom);
		this.label = Label.UNKNOWN;
		this.setId();
	}
	
	/**
	 * PRE: -
	 * POST: Retorna un Autor amb id i nom dels parametres
	 */
	
	public Autor(int id, String nom) throws Exception {
		super(id, nom, 0);
		if(id > maxIdAutor) maxIdAutor = id + 10;
		this.label = Label.UNKNOWN;
	}

	
	
	/**
	 * PRE: -
	 * POST: Retorna el label del parametre implicit
	 */
	public void setId() {
		super.setId(getMaxIdAutor()+10);
        Entitat.incrementaMaxIdAutor();
	}
}
