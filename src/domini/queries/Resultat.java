package domini.queries;

import java.util.ArrayList;

import domini.graf.Autor;
import domini.graf.Entitat;

/**
 * Representa el resultat d'una query normal
 * @author Martí Lloveras Rosales
 *
 */
@SuppressWarnings("unchecked")
public class Resultat {
	private ArrayList<Pair<Entitat,Float>> entitats;
	private ArrayList<Pair<Entitat,Float>> entitats_visibles;
	private ArrayList<Pair<Entitat,Float>> anterior;
    private static final int VISIBLES_PER_DEFECTE = 20;
    
  
    /**
     * Es crea el Resultat a partir de l'array entitats.
     * @param entitats entitats del resultat
     */
    public Resultat (ArrayList<Pair<Entitat,Float>> entitats) {
        this.entitats = entitats;
        entitats_visibles = new ArrayList<Pair<Entitat,Float>>();
        anterior = new ArrayList<Pair<Entitat,Float>>();
        for(int i = 0; i < entitats.size() && i < VISIBLES_PER_DEFECTE; ++i) {
            entitats_visibles.add(entitats.get(i));
        }
    }
    
    /**
     * Es crea el Resultat a partir de l'array entitats i el nombre màxim d'entitats visibles es canvia per filtreN.
     * @param entitats entitats del resultat
     * @param filtreN núm. entitats visibles
     */
    public Resultat (ArrayList<Pair<Entitat,Float>> entitats, int filtreN) {
        this.entitats = entitats;
        entitats_visibles = new ArrayList<Pair<Entitat,Float>>();
        for(int i = 0; i < entitats.size() && i < filtreN; ++i) {
        	entitats_visibles.add(entitats.get(i));
        }
    }
    
    /**
     * El nombre maxim d'entitats visibles del resultat es canvia per quantsVisibles.
       Si quantsVisibles és més gran que el nombre total d'entitats, es faran visibles el 
       nombre màxim d'entitats possibles.
     * @param n núm. entitats visibles
     */
	public void filtrarN(int n) {
    	anterior = (ArrayList<Pair<Entitat,Float>>)entitats_visibles.clone();
    	if (n > entitats_visibles.size()){
    		for (int i = entitats_visibles.size(); i < n; ++i){
    			entitats_visibles.add(entitats.get(i));
    		}
    	}
    	else {
    		while (n < entitats_visibles.size()){
    			entitats_visibles.remove(n);
    		}
    	}
    }

	/**
	 * Es filtren totes les entitats amb label = <tt>idLabel</tt>.
	 * @param idLabel ID del label a filtrar
	 * @throws Exception IdLabel ha d'estar entre 0 i 4 (inclosos)
	 */
    public void filtrarLabelEq(int idLabel) throws Exception {
    	//if (idLabel < 0 || idLabel > 4) throw new Exception("idLabel ha d'estar entre 0 i 4 (inclosos)");
    	if (entitats.get(0).first.isTerme()) return;
    	anterior = (ArrayList<Pair<Entitat,Float>>)entitats_visibles.clone();
    	Entitat e;
        for(int i = 0; i < entitats_visibles.size(); ++i) {
        	e = entitats_visibles.get(i).first;
        	if(e.isAutor() || e.isConferencia() || e.isPaper()) {
        		if(e.getLabel() != idLabel){
        			entitats_visibles.remove(i);
        		}
        	}
        }
    }

    /**
     * Es filtren totes les entitats amb label != <tt>idLabel</tt>.
     * @param idLabel ID del label a filtrar
     * @throws Exception IdLabel ha d'estar entre 0 i 4 (inclosos)
     */
    public void filtrarLabelDif(int idLabel) throws Exception {
    	//if (idLabel < 0 || idLabel > 4) throw new Exception("S'incompleix 0 <= idLabel <= 4");
    	if (entitats.get(0).first.isTerme()) return;
    	anterior = (ArrayList<Pair<Entitat,Float>>)entitats_visibles.clone();
    	Entitat e;
        for(int i = 0; i < entitats_visibles.size(); ++i) {
        	e = entitats_visibles.get(i).first;
        	if(e.isAutor() || e.isConferencia() || e.isPaper()) {
        		if(e.getLabel() == idLabel){
        			entitats_visibles.remove(i);
        		}
        	}
        }
    }
    
    /**
     * Es filtra l'entitat amb id = idEntitat.
     * @param idEntitat ID de l'entitat a filtrar
     * @throws Exception ...
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
    
    /**
     * Es netegen tots els filtres existents i el nombre maxim d'entitats 
       visibles es canvia pel definit per defecte. Si el definit per defecte és més 
       gran que el nombre total d'entitats, es faran visibles el nombre màxim d'entitats 
       possibles.
     */
    public void netejaFiltres() {
    	anterior = (ArrayList<Pair<Entitat,Float>>)entitats_visibles.clone();
        entitats_visibles.clear();
    	for (int i = 0; i < entitats.size() && i < VISIBLES_PER_DEFECTE; ++i){
    		entitats_visibles.add(entitats.get(i));
    	}
    }
    
    /**
     * Desfa l'últim filtre.
     */
    public void desferFiltre(){
    	entitats_visibles = anterior;
    }
    
    /**
     * Es retorna cert si el resultat conté Autors.
     * @return 'true' si és d'autors; altrament, 'false'
     */
    public boolean isAutor() {
    	return (entitats.get(0).first.isAutor());
    }
    
    /**
     * Es retorna cert si el resultat conté Papers.
     * @return 'true' si és de papers; altrament, 'false'
     */
    public boolean isPaper() {
    	return (entitats.get(0).first.isPaper());	
    }
    
    /**
     * Es retorna cert si el resultat conté Conferencies.
     * @return 'true' si és de conferències; altrament, 'false'
     */
    public boolean isConferencia() {
    	return (entitats.get(0).first.isConferencia());	
    }
    
    /**
     * Es retorna cert si el resultat conté Termes.
     * @return 'true' si és de termes; altrament, 'false'
     */
    public boolean isTerme() {
    	return (entitats.get(0).first.isTerme());
    }
    
    /**
     * Es retorna l'array d'entitats.
     * @return conjunt d'entitats
     */
    public ArrayList<Pair<Entitat,Float>> getEntitats() {
    	return entitats;
    }
    
    /**
     * Es retorna l'array que indica les entitats visibles.
     * @return conjunt en
     */
    public ArrayList<Pair<Entitat,Float>> getVisibles() {
    	return entitats_visibles;
    }
    
    /**
     * S'imprimeixen per pantalla les entitats visibles del resultat.
     */
    public void print() {
    	for(int i = 0; i < entitats_visibles.size(); ++i) {
    		System.out.println(entitats_visibles.get(i).toString());
    	}	 
    	
    }   
    
    /**
     * Retorna el resultat a través d'strings
     * @return resultat en strings
     */
    public String[][] getDadesString(){
    	String[][] resultat = new String[entitats_visibles.size()][3];
    	for(int i = 0; i < entitats_visibles.size(); ++i){
    		resultat[i][0] = entitats_visibles.get(i).first.getNom();
    		resultat[i][1] = entitats_visibles.get(i).first.getNameLabel();
    		resultat[i][2] = entitats_visibles.get(i).second.toString();
    	}
    	
    	return resultat;
    }

}
