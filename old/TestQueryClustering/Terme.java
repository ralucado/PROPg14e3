package TestQueryClustering;

public class Terme extends Entitat {
	
	/**
	 * PRE: -
	 * POST: Crea una entitat de tipus Terme amb nom el parametre i li assigna una id
	 */
	public Terme(String nom) {
		super(nom);
		this.setId();
	}
	
	/**
	 * PRE: -
	 * POST: Retorna un terme amb id i nom dels parametres
	 */
	public Terme(int id, String nom) {
		super(id, nom);
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
