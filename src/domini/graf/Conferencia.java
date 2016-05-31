package domini.graf;

/**
 * Representa una conferència
 * @author Grup 14.1
 */
public class Conferencia extends Entitat {
	
	/**
	 * Crea una entitat del tipus Conferencia amb el nom <tt>nom</tt> i li assigna una id
	 * @param nom nom de la conferència 
	 */
	public Conferencia(String nom) {
		super(nom);
		this.label = Label.UNKNOWN;
		this.setId();
	}
	
	/**
	 * Crea una entitat del tipus Conferencia amb l'identificador <tt>id</tt> i el nom <tt>nom</tt>
	 * @param id identificador de la conferència
	 * @param nom nom de la conferència
	 * @throws Exception si l'identificador és invàl·lid
	 */
	public Conferencia(int id, String nom) throws Exception {
		super(id, nom, 2);
		if(id > maxIdConferencia) maxIdConferencia = id + 10;
		this.label = Label.UNKNOWN;
	}
	
	/**
	 * Assigna un identificador a la conferència
	 */
	public void setId() {
		super.setId(getMaxIdConferencia()+10);
        Entitat.incrementaMaxIdConferencia();
	}
}
