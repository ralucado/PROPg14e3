package domini.graf;

/**
 * Representa un Autor
 * @author Grup 14.1
 */
public class Autor extends Entitat {

	
	/**
	 * Crea una entitat del tipus Autor amb el nom <tt>nom</tt> i li assigna una id
	 * @param nom nom de l'autor 
	 */
	public Autor(String nom) {
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
	public Autor(int id, String nom) throws Exception {
		super(id, nom, 0);
		if(id > maxIdAutor) maxIdAutor = id + 10;
		this.label = Label.UNKNOWN;
	}

	
	
	/**
	 * Assigna un identificador a l'autor
	 */
	public void setId() {
		super.setId(getMaxIdAutor()+10);
        Entitat.incrementaMaxIdAutor();
	}
}
