package domini.camins;
import domini.queries.Pair;
import domini.queries.SparseMatrix;
/**
 * Representa un cami, amb un nom, un path d'entitats i una descripcio
 * @author Martí Lloveras Rosales
 *
 */
public class Cami {
	protected String nom;
	protected String descripcio;
	protected String path;
	protected SparseMatrixBool matriuL, matriuR;
	
	/**
	 * Creadora amb paràmetres
	 * @param nom Nom del nou camí
	 * @param path Path d'entitats del nou camí
	 * @param descripcio Descripció del nou camí
	 * @throws Exception si el path és incorrecte
	 */
	public Cami(String nom, String path, String descripcio) throws Exception {
		if (badPath(path)) throw new Exception("! Path incorrecte");
        this.nom = nom;
        this.descripcio = descripcio;
        this.path = path;
        this.matriuL = null;
        this.matriuR = null;
    }

	/**
	 * Consulta el nom del camí
	 * @return String amb el nom del camí
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Posa el nom del camí a nom
	 * @param nom Nom del camí 
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Consulta la descripció del camí
	 * @return String amb la descripció del camí
	 */
	public String getDescripcio() {
		return descripcio;
	}

	/**
	 * Posa la descripció del camí a descripcio
	 * @param descripcio Descripció del camí
	 */
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
	
	/**
	 * Posa la matriu esquerra del camí a mL i la matriu dreta a mR
	 * @param mL Matriu esquerra
	 * @param mR Matriu dreta
	 */
	public void setMatrius(SparseMatrix mL, SparseMatrix mR){
		this.matriuL = new SparseMatrixBool(mL);
		this.matriuR = new SparseMatrixBool(mR);
	}
	
	/**
	 * Posa la matriu esquerra del camí a mL i la matriu dreta a mR
	 * @param mL Matriu esquerra
	 * @param mR Matriu dreta
	 */
	public void setMatrius(SparseMatrixBool mL, SparseMatrixBool mR){
		this.matriuL = new SparseMatrixBool(mL);
		this.matriuR = new SparseMatrixBool(mR);
	}

	/**
	 * Consulta les matrius del camí
	 * @return Pair de les dues matrius
	 */
	public Pair<SparseMatrixBool,SparseMatrixBool> getMatrius(){
		Pair<SparseMatrixBool,SparseMatrixBool> p = new Pair<SparseMatrixBool, SparseMatrixBool>(matriuL,matriuR);
		return p;
	}
	
	/**
	 * Reseteja les matrius del camí
	 */
	public void resetMatrius(){
		matriuL = matriuR = null;
	}
	
	/**
	 * Comprova si el camí té algun node de tipus autor
	 * @return True si té algun node d'autor, sino false
	 */
	public boolean teAutor(){
		return (path.contains("A"));
	}
	
	/**
	 * Comprova si el camí té algun node de tipus paper
	 * @return True si té algun node de paper, sino false
	 */
	public boolean tePaper(){
		return (path.contains("P"));
	}
	
	/**
	 * Comprova si el camí té algun node de tipus conferencia
	 * @return True si té algun node de conferencia, sino false
	 */
	public boolean teConferencia(){
		return (path.contains("C"));
	}
	
	/**
	 * Comprova si el camí té algun node de tipus terme
	 * @return True si té algun node de terme, sino false
	 */
	public boolean teTerme(){
		return (path.contains("T"));
	}
	
	/**
	 * Consulta el path del camí
	 * @return String amb el path del camí
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Posa el path del camí a path
	 * @param path Path del camí
	 * @throws Exception El path és incorrecte
	 */
	public void setPath(String path) throws Exception{
		if (badPath(path)) throw new Exception("! Path incorrecte");
		this.path = path;
	}
	
	/**
	 * Comprova si el path passat per parametre te un format correcte
	 * @param p Path a comprovar
	 * @return True si el path es incorrecte, false altrament
	 */
	protected static boolean badPath(String p){
		if(p.length() == 0) return false;
		char c = p.charAt(0);
		if (c != 'P' && c != 'A' && c != 'C' && c != 'T') return true;
		boolean b = (c != 'P');
		for (int i = 1; i < p.length(); ++i){
			c = p.charAt(i);
			if (b && c != 'P') return true;
			if (!b && c == 'P') return true;
			if (c != 'P' && c != 'A' && c != 'C' && c != 'T') return true;
			b = !b;
		}
		return false;
	}

}
