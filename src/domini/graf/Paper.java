package domini.graf;

public class Paper extends Entitat {

	/**
	 * PRE: -
	 * POST: Crea una entitat de tipus Paper amb nom el parametre i li assigna una id
	 */
	public Paper(String nom) {
		super(nom);
		this.label = Label.UNKNOWN;
		this.setId();
	}
	
	/**
	 * PRE: -
	 * POST: Retorna un Paper amb id i nom dels parametres
	 */
	public Paper(int id, String nom) throws Exception {
		super(id, nom,1);
		if(id > maxIdPaper) maxIdPaper = id + 10;
		this.label = Label.UNKNOWN;
	}
	
	
	/**
	 * PRE: -
	 * POST: Retorna el label del parametre implicit
	 */
	public void setId() {
		super.setId(getMaxIdPaper()+10);
        Entitat.incrementaMaxIdPaper();
	}
}