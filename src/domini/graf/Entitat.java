package domini.graf;

public class Entitat {
	protected int id;
	protected String nom;
	protected Label label;
	protected static int maxIdAutor;
    protected static int maxIdPaper;
    protected static int maxIdConferencia;
    protected static int maxIdTerme;
    
	
	static {
		maxIdAutor = 0;
        maxIdPaper = 1;
        maxIdConferencia = 2;
        maxIdTerme = 3;
	}
	
	public Entitat(String nom) {
		this.nom = nom;
	}
	
	
	protected Entitat(int id, String nom, int modul) throws Exception{
		if(id % 10 != modul) throw new Exception("ID invalid");
		this.id = id;
		this.nom = nom;
	}
	
	public int getId() {
		return id;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	protected static void incrementaMaxIdAutor() {
        maxIdAutor += 10;
    }

    protected static void incrementaMaxIdPaper() {
        maxIdPaper += 10;
    }

    protected static void incrementaMaxIdConferencia() {
        maxIdConferencia += 10;
    }

    protected static void incrementaMaxIdTerme() {
        maxIdTerme += 10;
    }

    protected static int getMaxIdAutor() {
        return maxIdAutor;
    }

    protected static int getMaxIdPaper() {
        return maxIdPaper;
    }

    protected static int getMaxIdConferencia() {
        return maxIdConferencia;
    }

    protected static int getMaxIdTerme() {
        return maxIdTerme;
    }
    
    protected void setId(int id) {
        this.id = id;
    }
    
    public boolean isAutor() {
        return this.getId() % 10 == 0;
    }
    
    public boolean isPaper() {
        return this.getId() % 10 == 1;
    }

    public boolean isConferencia() {
        return this.getId() % 10 == 2;
    }
    public boolean isTerme() {
        return this.getId() % 10 == 3;
    }
    
    
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
    
    
    @Override
    public String toString() {
    	String returnString;
    	if (this.isTerme()) returnString = ("Nom: " + this.getNom() + ", Tipus: Terme");
    	else {
    		if(this.isAutor()) {
    			Autor a = (Autor) this;
    			returnString = ("Nom: " + a.getNom() + ", Tipus: Autor, " + "Label: " + a.getNameLabel());
    		}
    		else if(this.isPaper()) {
    			Paper p = (Paper) this;
    			returnString = ("Nom: " + p.getNom() + ", Tipus: Paper, " + "Label: " + p.getNameLabel());
    		}
    		else {
    			Conferencia c = (Conferencia) this;
    			returnString = ("Nom: " + c.getNom() + ", Tipus: Conferencia, " + "Label: " + c.getNameLabel());
    		}
    	}
    	return returnString;
    }
    
}
