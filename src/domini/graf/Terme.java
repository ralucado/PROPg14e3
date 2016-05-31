package domini.graf;

/**
 * Representa un Terme
 * @author Grup 14.1
 */
public class Terme extends Entitat {
	
	/**
	 * Crea una entitat del tipus Terme amb el nom <tt>nom</tt> i li assigna una id
	 * @param nom nom del terme 
	 */
	public Terme(String nom) {
		super(nom);
		this.setId();
		this.label = Label.NO_LABEL;
	}
	
	/**
	 * Crea una entitat del tipus Terme amb l'identificador <tt>id</tt> i el nom <tt>nom</tt>
	 * @param id identificador del terme
	 * @param nom nom del terme
	 * @throws Exception si l'identificador és invàl·lid
	 */
	public Terme(int id, String nom) throws Exception {
		super(id, nom, 3);
		if(id > maxIdTerme) maxIdTerme = id + 10;
		this.label = Label.NO_LABEL;
		
	}
	
	/**
	 * Assigna un identificador al terme
	 */
	public void setId() {
		super.setId(getMaxIdTerme()+10);
        Entitat.incrementaMaxIdTerme();
	}
}
