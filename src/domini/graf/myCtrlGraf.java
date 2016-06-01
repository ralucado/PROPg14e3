package domini.graf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Extensió del controlador de graf
 * @author Marc Villanueva Cuadrench
 */
public class myCtrlGraf extends CtrlGraf {

	/**
	 * Creadora de myCtrlGraf
	 * @throws Exception ...
	 */
	public myCtrlGraf() throws Exception {
		super();
	}
	
	/**
	 * Consulta les relacions d'Autor a Paper
	 * @return Matriu d'Strings . Representa una llista de les relacions en el format (nomE1, nomE2)
	 * @throws Exception ...
	 */
	public ArrayList<ArrayList<String>> consultarRelacionsAP() throws Exception{
        	ArrayList<ArrayList<String>> rel = new ArrayList<ArrayList<String>>();
            Map<Integer,HashSet<Integer>> TP = graf.getPaperAutor();
           
            for (Map.Entry<Integer,HashSet<Integer>> e: TP.entrySet()) {
                String e1 = graf.consultarEntitat(graf.getIdByPositionPaper(e.getKey())).getNom();
                for (Integer i : e.getValue()) {
                	String e2 = graf.consultarEntitat(graf.getIdByPositionAutor(i)).getNom();
                	 ArrayList<String> aux = new ArrayList<String>();
                     aux.add(e1);
                     aux.add(e2);
                     rel.add(aux);             
                }
            }
            return rel;
    }
    
	/**
	 * Consulta les relacions de Conferencia a Paper
	 * @return Matriu d'Strings . Representa una llista de les relacions en el format (nomE1, nomE2)
	 * @throws Exception ...
	 */
    public ArrayList<ArrayList<String>> consultarRelacionsCP() throws Exception{
        	ArrayList<ArrayList<String>> rel = new ArrayList<ArrayList<String>>();
            Map<Integer,HashSet<Integer>> TP = graf.getPaperConferencia();
            
            for (Map.Entry<Integer,HashSet<Integer>> e: TP.entrySet()) {
                String e1 = graf.consultarEntitat(graf.getIdByPositionPaper(e.getKey())).getNom();
                for (Integer i : e.getValue()) {
                	String e2 = graf.consultarEntitat(graf.getIdByPositionConferencia(i)).getNom();
                	 ArrayList<String> aux = new ArrayList<String>();
                     aux.add(e1);
                     aux.add(e2);
                     rel.add(aux);
                }
            }
         
            return rel;
    }
    
    /**
	 * Consulta les relacions de terme a Paper
	 * @return Matriu d'Strings . Representa una llista de les relacions en el format (nomE1, nomE2)
	 * @throws Exception ...
	 */
    public ArrayList<ArrayList<String>> consultarRelacionsTP() throws Exception{
        	ArrayList<ArrayList<String>> rel = new ArrayList<ArrayList<String>>();
            Map<Integer,HashSet<Integer>> TP = graf.getPaperTerme();
            
            for (Map.Entry<Integer,HashSet<Integer>> e: TP.entrySet()) {
                String e1 = graf.consultarEntitat(graf.getIdByPositionPaper(e.getKey())).getNom();
                for (Integer i : e.getValue()) {
                	String e2 = graf.consultarEntitat(graf.getIdByPositionTerme(i)).getNom();
                	 ArrayList<String> aux = new ArrayList<String>();
                     aux.add(e1);
                     aux.add(e2);
                     rel.add(aux);
                }
            }
         
            return rel;
    }
    
    /**
     * Consulta tots els autors del graf i la seva informació
     * @return Matriu d'Strings. Representa una llista dels autors en el format (id, nom, label)
     * @throws Exception ...
     */
    public ArrayList<ArrayList<String>> consultarAutorsExt() throws Exception {
    	ArrayList<ArrayList<String>> autors = new ArrayList<ArrayList<String>>();
    	int n = graf.getnAutors();
    	Map<Integer,Entitat> m = graf.getConjuntEntitats();
    	Set<Integer> s=m.keySet();
    	int i = 0;
    	for (int id : s) {  		
    		Entitat e=(Entitat)m.get(id);
    		if(e.isAutor()){
    			ArrayList<String> ent=new ArrayList<String>();
	    		ent.add(Integer.toString(id));
	    		ent.add(e.getNom());
	    		ent.add(e.getNameLabel());
	    		autors.add(ent);
	    		++i;
    		}	   
    		if(n==i) break;
    	}
    	return autors;
    }
    
    /**
     * Consulta tots els papers del graf i la seva informació
     * @return Matriu d'Strings. Representa una llista dels papers en el format (id, nom, label)
     * @throws Exception ...
     */
    public ArrayList<ArrayList<String>> consultarPapersExt() throws Exception {
    	ArrayList<ArrayList<String>> papers = new ArrayList<ArrayList<String>>();
    	
    	Map<Integer,Entitat> m=graf.getConjuntEntitats();
    	Set<Integer> s=m.keySet();
    	for (int id : s) {
    		Entitat e=(Entitat)m.get(id);
    		if(e.isPaper()){
    			ArrayList<String> ent=new ArrayList<String>();
	    		ent.add(Integer.toString(id));
	    		ent.add(e.getNom());
	    		ent.add(e.getNameLabel());
	    		papers.add(ent);
    		}	    	  
    	}
    	return papers;
    }

    /**
     * Consulta totes les conferencies del graf i la seva informació
     * @return Matriu d'Strings. Representa una llista de les conferencies en el format (id, nom, label)
     * @throws Exception ...
     */
    public ArrayList<ArrayList<String>> consultarConferenciesExt() throws Exception {
    	ArrayList<ArrayList<String>> confs = new ArrayList<ArrayList<String>>();
    	int n = graf.getnConferencies();
    	Map<Integer,Entitat> m=graf.getConjuntEntitats();
    	Set<Integer> s=m.keySet();
    	int i = 0;
    	for (int id : s) {
    		Entitat e=(Entitat)m.get(id);
    		if(e.isConferencia()){
    			ArrayList<String> ent=new ArrayList<String>();
	    		ent.add(Integer.toString(id));
	    		ent.add(e.getNom());
	    		ent.add(e.getNameLabel());
	    		confs.add(ent);
	    		++i;
    		}
    		if(i==n) break;
    	}
    	return confs;
    }	
    
    /**
     * Consulta tots els termes del graf i la seva informació
     * @return Matriu d'Strings. Representa una llista dels termes en el format (id, nom)
     * @throws Exception ...
     */
    public ArrayList<ArrayList<String>> consultarTermesExt() throws Exception {
    	ArrayList<ArrayList<String>> termes = new ArrayList<ArrayList<String>>();
    	int n = graf.getnTermes();
    	Map<Integer,Entitat> m=graf.getConjuntEntitats();
    	Set<Integer> s=m.keySet();
    	int i = 0;
    	for (int id : s) {
    		Entitat e=(Entitat)m.get(id);
    		if(e.isTerme()){
    		   	ArrayList<String> ent=new ArrayList<String>();
	    		ent.add(Integer.toString(id));
	    		ent.add(e.getNom());
	    		termes.add(ent);
	    		++i;
    		}	    
    		if(i==n) break;
    	}
    	return termes;
    }
    
    /**
     * Esborra una entitat del graf
     * @param nom Nom de l'entitat a esborrar
     * @param tipus Tipus de l'entitat a esborrar
     * @throws Exception ...
     */
    public void esborrarEntitat(String nom, String tipus) throws Exception{
    	int id = graf.getIdByNameAndType(nom, tipus);
    	graf.eliminarEntitat(id);
    }
    
    /**
     * Modifica una entitat del graf
     * @param nom Nom de l'entitat a modificar
     * @param nomNou Nom nou de l'entitat
     * @param labelNova Nova Label nova de l'entitat
     * @param tipus Tipus de l'entitat
     * @throws Exception ...
     */
    public void modificarEntitat(String nom, String  nomNou, String labelNova, String tipus) throws Exception {
    	int id = graf.getIdByNameAndType(nom, tipus);
    	Entitat e = graf.consultarEntitat(id);
    	if(nomNou!=null) e.setNom(nomNou);
    	if(labelNova!=null){
    		int lid;
    		switch(labelNova) {
    		case "DATABASE": lid = 0;
    				break;
    		case "DATA_MINING": lid = 1;
    				break;
    		case "AI": lid = 2;
    				break;
    		case "INFORMATION_RETRIEVAL": lid = 3;
    				break;
    		default: lid = 4;
    		}
    		e.setLabel(lid);
    	}
    }
    
    /**
     * Retorna l'ID d'una entitat a partir del nom i el tipus
     * @param nom Nom d'entitat
     * @param tipus Tipus d'entitat
     * @return ID de l'entitat
     * @throws Exception ...
     */
    public int consultarIdEntitat(String nom, String tipus) throws Exception{
		return graf.getIdByNameAndType(nom, tipus);
	}
}
