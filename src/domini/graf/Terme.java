package domini.graf;


public class Terme extends Entitat {
	
	/**
	 * PRE: -
	 * POST: Crea una entitat de tipus Terme amb nom el parametre i li assigna una id
	 */
	public Terme(String nom) {
		super(nom);
		this.setId();
		this.label = Label.NO_LABEL;
	}
	
	/**
	 * PRE: -
	 * POST: Retorna un terme amb id i nom dels parametres
	 */
	public Terme(int id, String nom) throws Exception {
		super(id, nom, 3);
		if(id > maxIdTerme) maxIdTerme = id + 10;
		this.label = Label.NO_LABEL;
		
	}
	
	/**
	 * PRE: -
	 * POST: Retorna el label del parametre implicit
	 */
	public void setId() {
		super.setId(getMaxIdTerme()+10);
        Entitat.incrementaMaxIdTerme();
	}
}
