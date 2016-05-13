package domini.graf;

public class Conferencia extends Entitat {
	
	/**
	 * PRE: -
	 * POST: Crea una entitat de tipus Conferencia amb nom el parametre i li assigna una id
	 */
	public Conferencia(String nom) {
		super(nom);
		this.label = Label.UNKNOWN;
		this.setId();
	}
	
	/**
	 * PRE: -
	 * POST: Retorna una Conferencia amb id i nom dels parametres
	 */
	public Conferencia(int id, String nom) throws Exception {
		super(id, nom, 2);
		if(id > maxIdConferencia) maxIdConferencia = id + 10;
		this.label = Label.UNKNOWN;
	}
	
	/**
	 * PRE: labelId ha de ser un identificador de label valid
	 * POST: Afegeix al parametre implicit el label amb id del parametre
	 */
	
	
	
	/**
	 * PRE: -
	 * POST: Retorna el label del parametre implicit
	 */
	public void setId() {
		super.setId(getMaxIdConferencia()+10);
        Entitat.incrementaMaxIdConferencia();
	}
}
