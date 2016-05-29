package domini.graf;

/**
 * Representa un paper
 */
public class Paper extends Entitat {

	/**
	 * Crea una entitat del tipus Paper amb el nom <tt>nom</tt> i li assigna una id
	 * @param nom nom del paper 
	 */
	public Paper(String nom) {
		super(nom);
		this.label = Label.UNKNOWN;
		this.setId();
	}
	
	/**
	 * Crea una entitat del tipus Paper amb l'identificador <tt>id</tt> i el nom <tt>nom</tt>
	 * @param id identificador del paper
	 * @param nom nom del paper
	 * @throws Exception si l'identificador és invàl·lid
	 */
	public Paper(int id, String nom) throws Exception {
		super(id, nom,1);
		if(id > maxIdPaper) maxIdPaper = id + 10;
		this.label = Label.UNKNOWN;
	}
	
	
	/**
	 * Assigna un identificador del paper
	 */
	public void setId() {
		super.setId(getMaxIdPaper()+10);
        Entitat.incrementaMaxIdPaper();
	}
}
