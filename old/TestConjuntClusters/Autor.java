package TestConjuntClusters;

public class Autor extends Entitat {
	private Label label;
	
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
	public Autor(int id, String nom) {
		super(id, nom);
		this.label = Label.UNKNOWN;
	}
	
	/**
	 * PRE: labelId ha de ser un identificador de label valid
	 * POST: Afegeix al parametre implicit el label amb id del parametre
	 */

	public void setLabel(int labelId) {
		Label l;
		switch(labelId) {
		case 0: l = Label.DATABASE;
				break;
		case 1: l = Label.DATA_MINING;
				break;
		case 2: l = Label.AI;
				break;
		case 3: l = Label.INFORMATION_RETRIEVAL;
				break;
		default:l = Label.UNKNOWN;
		}
		this.label = l;
	}
	
	/**
	 * PRE: El label del parametre ha de ser valid
	 * POST: Afegeix al parametre implicit el label del parametre
	 */
	public void setLabel(Label l) {
		switch(l) {
		case DATABASE: 
			this.label = l;
			break;
		case DATA_MINING: 
			this.label = l;
			break;
		case AI: 
			this.label = l;
			break;
		case INFORMATION_RETRIEVAL: 
			this.label = l;
			break;
		default: 
			break;
		}
	}
	
	/**
	 * PRE: -
	 * POST: Retorna el identificador del label del parametre implicit
	 */
	public int getLabel() {
		return this.label.ordinal();
	}
	
	/**
	 * PRE: -
	 * POST: Retorna el nom del label del parametre implicit
	 */
	
	public String getNameLabel() {
		return this.label.name();
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
