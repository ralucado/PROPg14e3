import java.util.*;

public class Resultat {
	private ArrayList<Pair<Entitat,Float>> entitats;
	private ArrayList<Pair<Entitat,Float>> entitats_visibles;
	private ArrayList<Pair<Entitat,Float>> anterior;
    private static final int VISIBLES_PER_DEFECTE = 20;

/*
 * PRE: L'array entitats esta plena de Entitats del mateix tipus (Autor, Paper, Conferencia, Terme).
 * POST: Es crea el Resultat a partir de l'array entitats.
 */
    public Resultat (ArrayList<Pair<Entitat,Float>> entitats) {
        this.entitats = entitats;
        entitats_visibles = new ArrayList<Pair<Entitat,Float>>();
        anterior = new ArrayList<Pair<Entitat,Float>>();
        for(int i = 0; i < entitats.size() && i < VISIBLES_PER_DEFECTE; ++i) {
            entitats_visibles.add(entitats.get(i));
        }
    }
    
    /*
     * PRE: L'array entitats esta plena de Entitats del mateix tipus 
       (Autor, Paper, Conferencia, Terme).
     * POST: Es crea el Resultata partir de l'array entitats i el nombre maxim
       d'entitats visibles es canvia per filtreN.
     */
    public Resultat (ArrayList<Pair<Entitat,Float>> entitats, int filtreN) {
        this.entitats = entitats;
        entitats_visibles = new ArrayList<Pair<Entitat,Float>>();
        for(int i = 0; i < entitats.size() && i < filtreN; ++i) {
        	entitats_visibles.add(entitats.get(i));
        }
    }
    
    /*
     * PRE: Cert.
     * POST: El nombre maxim d'entitats visibles del resultat es canvia per quantsVisibles.
       Si quantsVisibles �s m�s gran que el nombre total d'entitats, es faran visibles el 
       nombre m�xim d'entitats possibles.
     */
    
    public void filtrarN(int n) {
    	anterior = (ArrayList<Pair<Entitat,Float>>)entitats_visibles.clone();
    	if (n > entitats_visibles.size()){
    		for (; n < entitats.size(); ++n){
    			entitats_visibles.add(entitats.get(n-1));
    		}
    	}
    	else {
    		for (; n < entitats_visibles.size(); ++n){
    			entitats_visibles.remove(n);
    		}
    	}
    }

    /*
     * PRE: 0 <= idLabel <= 4.
     * POST: Es filtren totes les entitats amb label igual a idLabel.
     */
    public void filtrarLabelEq(int idLabel) throws Exception {
    	if (idLabel < 0 || idLabel > 4) throw new Exception("idLabel ha d'estar entre 0 i 4 (inclosos)");
    	if (entitats.get(0).first.isTerme()) return;
    	anterior = (ArrayList<Pair<Entitat,Float>>)entitats_visibles.clone();
    	Entitat e;
        for(int i = 0; i < entitats.size(); ++i) {
        	e = entitats.get(i).first;
        	if(e.isAutor() || e.isConferencia() || e.isPaper()) {
        		if(e.getLabel() != idLabel){
        			entitats_visibles.remove(i);
        		}
        	}
        }
    }

    /*
     * PRE: 0 <= idLabel <= 4.
     * POST: Es filtren totes les entitats amb label diferent a idLabel.
     */
    public void filtrarLabelDif(int idLabel) throws Exception {
    	if (idLabel < 0 || idLabel > 4) throw new Exception("S'incompleix 0 <= idLabel <= 4");
    	if (entitats.get(0).first.isTerme()) return;
    	anterior = (ArrayList<Pair<Entitat,Float>>)entitats_visibles.clone();
    	Entitat e;
        for(int i = 0; i < entitats.size(); ++i) {
        	e = entitats.get(i).first;
        	if(e.isAutor() || e.isConferencia() || e.isPaper()) {
        		if(e.getLabel() == idLabel){
        			entitats_visibles.remove(i);
        		}
        	}
        }
    }
    
    /*
     * PRE: L'entitat amb id = idEntitat ha d'existir dins el resultat.
     * POST: Es filtra l'entitat amb id = idEntitat.
     */
    public void filtrarEntitat(int idEntitat) throws Exception {
    	boolean trobat = false;
    	anterior = (ArrayList<Pair<Entitat,Float>>)entitats_visibles.clone();
        for(int i = 0; i < entitats.size() && !trobat; ++i) {
        	if(entitats.get(i).first.getId() == idEntitat) {
        		entitats_visibles.remove(i);
        		trobat = true;
        	}
        }
        if(!trobat)throw new Exception("No existeix l'entitat que es desitja filtrar.");
        
    }
    
    /*
     * PRE: Cert.
     * POST: Es netegen tots els filtres existents i el nombre maxim d'entitats 
       visibles es canvia pel definit per defecte. Si el definit per defecte �s m�s 
       gran que el nombre total d'entitats, es faran visibles el nombre m�xim d'entitats 
       possibles.
     */
    public void netejaFiltres() {
    	anterior = (ArrayList<Pair<Entitat,Float>>)entitats_visibles.clone();
        entitats_visibles.clear();
    	for (int i = 0; i < entitats.size() && i < VISIBLES_PER_DEFECTE; ++i){
    		entitats_visibles.add(entitats.get(i));
    	}
    }
    
    public void desferFiltre(){
    	entitats_visibles = anterior;
    }
    
    /*
     * PRE: Cert.
     * POST: Es retorna cert si el resultat cont� Autors.
     */
    public boolean isAutor() {
    	return (entitats.get(0).first.isAutor());
    }
    
    /*
     * PRE: Cert.
     * POST: Es retorna cert si el resultat cont� Papers.
     */
    public boolean isPaper() {
    	return (entitats.get(0).first.isPaper());	
    }
    
    /*
     * PRE: Cert.
     * POST: Es retorna cert si el resultat cont� Conferencies.
     */
    public boolean isConferencia() {
    	return (entitats.get(0).first.isConferencia());	
    }
    
    /*
     * PRE: Cert.
     * POST: Es retorna cert si el resultat cont� Termes.
     */
    public boolean isTerme() {
    	return (entitats.get(0).first.isTerme());
    }
    
    /*
     * PRE: Cert.
     * POST: Es retorna l'array d'entitats.
     */
    public ArrayList<Pair<Entitat,Float>> getEntitats() {
    	return entitats;
    }
    
    /*
     * PRE: Cert.
     * POST: Es retorna l'array que indica les entitats visibles.
     */
    public ArrayList<Pair<Entitat,Float>> getVisibles() {
    	return entitats_visibles;
    }
    
    /*
     * PRE: Cert.
     * POST: S'imprimeixen per pantalla les entitats visibles del resultat.
     */
    public void print() {
    	for(int i = 0; i < entitats_visibles.size(); ++i) {
    		System.out.println(entitats_visibles.get(i).toString());
    	}	 
    	
    }   
}