package TestConjuntClusters;

public class Entitat {
	private int id;
	private String nom;
	private static int maxIdAutor;
	private static int maxIdPaper;
    	private static int maxIdConferencia;
    	private static int maxidTerme;
    
	
	static {
		maxIdAutor = 0;
        maxIdPaper = 1;
        maxIdConferencia = 2;
        maxidTerme = 3;
	}
	
	public Entitat(String nom) {
		this.nom = nom;
	}
	
	//Nomes utilitzar per importar entitats de fitxer
	public Entitat(int id, String nom) {
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
        maxidTerme += 10;
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
        return maxidTerme;
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
