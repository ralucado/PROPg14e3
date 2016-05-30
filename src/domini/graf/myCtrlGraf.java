package domini.graf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class myCtrlGraf extends CtrlGraf {

	public myCtrlGraf() throws Exception {
		super();
	}
	
	public ArrayList<ArrayList<String>> consultarRelacionsAP(){
    	try{
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
        	catch(Exception e){
        		System.out.println("a");
        	}
			return null;
    }
    
    public ArrayList<ArrayList<String>> consultarRelacionsCP(){
    	try{
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
        	catch(Exception e){
        		System.out.println("a");
        	}
			return null;
    }
    
    public ArrayList<ArrayList<String>> consultarRelacionsTP(){
    	try{
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
        	catch(Exception e){
        		System.out.println("a");
        	}
			return null;
    }
    
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
    
    public void esborrarEntitat(String nom, String tipus) throws Exception{
    	int id = graf.getIdByNameAndType(nom, tipus);
    	graf.eliminarEntitat(id);
    }
    
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
     
}