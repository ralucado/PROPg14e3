package domini.graf;
import java.util.*;
import persistencia.*;

public class CtrlGraf {
	
    /**
     *
     */
    protected Graf graf;

		/**
		 *Pre: -
		 *Post: Crea un CtrlGraf.
		 */
	 	public CtrlGraf(){
	 		graf=new Graf();
	 	}
	 	
	 	/**
	 	 * Pre: -
	 	 * Post: graf=grafAux.
     * @param grafAux
	 	 */
	 	public void setGraf(Graf grafAux){
	 		graf=grafAux;
	 	}
	 	
    /**
     *
     * @return
     */
    public Graf consultarGraf(){
	 		return graf;
	 	}

	 	/**
	 	 * Pre: -
	 	 * Post: Carrega el graf des de un fitxer, i retorna el graf.
     * @return 
     * @throws java.lang.Exception
	 	 */
	 	public Graf carregarGraf() throws Exception{
	         
	         CtrlPersistencia ctPer=new CtrlPersistencia();
                 ArrayList<ArrayList<String>> Entitats = ctPer.importarEntitats();

	         ArrayList<ArrayList<String>> Relacions = ctPer.importarRelacions();
	         
	         TreeMap m = new TreeMap<Integer,Entitat>();
	         
	         for(int i=0; i<Entitats.size(); i++) {
                     
	        	 int id = Integer.parseInt(Entitats.get(i).get(0));
	        	 String nom = Entitats.get(i).get(1);
                         
                         
                     switch (id%10) {
                         case 0:
                             {
                                 //Es autor
                                 Autor a=new Autor(id, nom);
                                 int label = Integer.parseInt(Entitats.get(i).get(2));
                                 a.setLabel(label);
                                 m.put(id, a);
                                 break;
                             }
                         case 1:
                             {
                                 //Es Paper
                                 Paper p=new Paper(id,nom);
                                 int label = Integer.parseInt(Entitats.get(i).get(2));
                                 p.setLabel(label);
                                 m.put(id, p);
                                 break;
                             }
                         case 2:
                             {
                                 //Es conferencia
                                 Conferencia c=new Conferencia(id,nom);
                                 int label = Integer.parseInt(Entitats.get(i).get(2));
                                 c.setLabel(label);
                                 m.put(id, c);
                                 break;
                             }
                         case 3:
                         {
                             Terme t=new Terme(id,nom);
                             m.put(id, t);
                             break;
                         }
                         default:
                             break;
                     }
	         }
	         
	         Graf g=new Graf(m,Relacions); 

	         return g;
	    }
	 	
	 	/**
	 	 * Pre: -
	 	 * Post: Guarda el graf actual a un fitxer.
     * @throws java.lang.Exception
	 	 */
	 	public void guardarGraf() throws Exception {
	 		Map m = graf.getConjuntEntitats();
	 		ArrayList<ArrayList<String>> Entitats=new ArrayList<ArrayList<String>>();
	        	Set<Integer> s=m.keySet();
                                    
	 		
	    	for (int id : s) {
	    		
	    		ArrayList<String> aux=new ArrayList<String>();
	    		Entitat e=(Entitat)m.get(id);
                        
                        aux.add(0,Integer.toString(id));
	    	    	aux.add(1,e.getNom());
                        if (!e.isTerme()) {
                            aux.add(2,Integer.toString(e.getLabel()));
                        }
                        Entitats.add(aux);
                        
	    	}
	    	CtrlPersistencia inst = new CtrlPersistencia();
                inst.exportarEntitats(Entitats);
	    
	    	
	    	
	    	ArrayList<ArrayList<String>> Relacions=new ArrayList<ArrayList<String>> ();
	    	
	    	Map<Integer,HashSet<Integer>> PaperAutor=graf.getPaperAutor();
	    	Map<Integer,HashSet<Integer>> PaperConferencia=graf.getPaperConferencia();
	    	Map<Integer,HashSet<Integer>> PaperTerme=graf.getPaperTerme();
	    	
	    	for(int pos=0; pos<PaperAutor.size(); ++pos){
	    		
	    		int idP=graf.getIdByPositionPaper(pos);
	    		
	    		HashSet<Integer>PA=PaperAutor.get(pos);
	    		HashSet<Integer>PC=PaperConferencia.get(pos);
	    		HashSet<Integer>PT=PaperTerme.get(pos);
	    		
	    		for(int posA:PA){
	    			ArrayList<String> aux2=new ArrayList<String>();
	    			int idA=graf.getIdByPositionAutor(posA);
	    			aux2.add(Integer.toString(idP)); aux2.add(Integer.toString(idA));
	    			Relacions.add(aux2);
	    		}
	    		for(int posC:PC){
	    			ArrayList<String> aux2=new ArrayList<String>();
	    			int idC=graf.getIdByPositionConferencia(posC);
	    			aux2.add(Integer.toString(idP)); aux2.add(Integer.toString(idC));
	    			Relacions.add(aux2);
	    		}
	    		for(int posT:PT){
	    			ArrayList<String> aux2=new ArrayList<String>();
	    			int idT=graf.getIdByPositionTerme(posT);
	    			aux2.add(Integer.toString(idP)); aux2.add(Integer.toString(idT));
	    			Relacions.add(aux2);
	    		}
	    	}
	    	inst.exportarRelacions(Relacions);
	    	
	    }	 	
	 	
	 	/**
	 	 * Pre: No existeix cap Autor amb nom=nom.
	 	 * Post: S'ha afegit l'Autor amb nom=nom i label=label al graf.
     * @param nom
     * @param label
     * @throws java.lang.Exception
	 	 */
	 	public void altaAutor(String nom, String label) throws Exception {
	 		Autor a = new Autor(nom);
	        switch (label) {
	            case "DATABASE":
	                a.setLabel(0);
	                break;
	            case "DATA_MINING":
	                a.setLabel(1);
	                break;
	            case "AI":
	                a.setLabel(2);
	                break;
	            case "INFORMATION_RETRIEVAL":
	                a.setLabel(3);
	                break;
	            default:
	                break;
	        }
	        graf.afegirEntitat(a);
	 	}

	 	/**
	 	 * Pre: No existeix cap Conferencia amb nom=nom.
	 	 * Post S'ha afegit la Conferencia amb nom=nom i label=label al graf.
     * @param nom
     * @param label
     * @throws java.lang.Exception
	 	 */
	 	public void altaConferencia (String nom, String label) throws Exception{
	 		Conferencia c = new Conferencia(nom);
	        switch (label) {
	            case "DATABASE":
	                c.setLabel(0);
	                break;
	            case "DATA_MINING":
	                c.setLabel(1);
	                break;
	            case "AI":
	                c.setLabel(2);
	                break;
	            case "INFORMATION_RETRIEVAL":
	                c.setLabel(3);
	                break;
	            default:
	                break;
	        }
	        graf.afegirEntitat(c);
	 	}
	 	
	 	/**
	 	 * Pre: No existeix cap Paper amb nom=nom.
	 	 * Post: S'ha afegit el Paper amb nom=nom i label=label al graf.
     * @param nom
     * @param label
     * @throws java.lang.Exception
	 	 */
	 	public void altaPaper(String nom, String label) throws Exception{
	        Paper p = new Paper(nom);
	        switch (label) {
	            case "DATABASE":
	                p.setLabel(0);
	                break;
	            case "DATA_MINING":
	                p.setLabel(1);
	                break;
	            case "AI":
	                p.setLabel(2);
	                break;
	            case "INFORMATION_RETRIEVAL":
	                p.setLabel(3);
	                break;
	            default:
	                break;
	        }
	        graf.afegirEntitat(p);
	    }
	    
	 	/**
	 	 * Pre: No existeix cap Terme amb nom=nom.
	 	 * Post: S'ha afegit el Terme amb nom=nom al graf.
     * @param nom
     * @throws java.lang.Exception
	 	 */
	    public void altaTerme(String nom) throws Exception{
	        Terme t = new Terme(nom);
	        graf.afegirEntitat(t);
	    }
	    
	    /**
	     * Pre: Existeix un Autor amb nom=nom.
	     * Post: S'ha eliminat l'Autor amb nom=nom del graf.
     * @param nom
     * @throws java.lang.Exception
	     */
	    public void baixaAutor(String nom) throws Exception{
	        int id = graf.getIdByNameAndType(nom, "autor");
	        graf.eliminarEntitat(id);
	    }
	  
	    /**
	     * Pre: Existeix una Conferencia amb nom=nom.
	     * Post: S'ha eliminat la Conferencia amb nom=nom del graf.
     * @param nom
     * @throws java.lang.Exception
	     */
	    public void baixaConferencia(String nom) throws Exception{
	        int id = graf.getIdByNameAndType(nom, "conferencia");
	        graf.eliminarEntitat(id);
	    }
	    
	    /**
	     * Pre: Existeix un Paper amb nom=nom.
	     * Post: S'ha eliminat el Paper amb nom=nom del graf.
     * @param nom
     * @throws java.lang.Exception
	     */
	    public void baixaPaper(String nom) throws Exception{
	        int id = graf.getIdByNameAndType(nom, "paper");
	        graf.eliminarEntitat(id);
	    }
	    
	    /**
	     * Pre: Existeix un Terme amb nom=nom.
	     * Post: S'ha eliminat el Terme amb nom=nom del graf.
     * @param nom
     * @throws java.lang.Exception
	     */
	    public void baixaTerme(String nom) throws Exception{
	        int id = graf.getIdByNameAndType(nom, "terme");
	        graf.eliminarEntitat(id);
	    }
	    
	    /**
	     * Pre: -
	     * Post: Retorna un vector amb el nom, el tipus i el label (si en te).
     * @param id
     * @return 
     * @throws java.lang.Exception 
	     */
	    public ArrayList<String> consultarEntitat(int id) throws Exception{
	    	ArrayList<String> list =new ArrayList<String>();
	    	Entitat e=graf.consultarEntitat(id);
	    	list.add(e.getNom());
	    	int num=4;
	    	if (e.isAutor()){
	    		list.add("autor");
	    		Autor a=(Autor) e; 
	    		num=a.getLabel();
	    	}
	    	else if (e.isConferencia()){
	    		list.add("conferencia");
	    		Conferencia c=(Conferencia) e;
	    		num=c.getLabel();
	    	}
	    	else if (e.isPaper()){
	    		list.add("paper");
	    		Paper p=(Paper) e;
	    		num=p.getLabel();
	    	}
	    	else {list.add("terme");}
	    	String label="";
	    	switch (num) {
            case 0:
                label="DATABASE";
                break;
            case 1:
                label="DATA_MINING";
                break;
            case 2:
                label="AI";
                break;
            case 3:
                label="INFORMATION_RETRIEVAL";
                break;
            default:
                break;
	    	}
	    	
	    	if (!e.isTerme()) list.add(label);
	    	return list;
	    	
	    }
	    
	    /**
	     * Pre: Existeix un Autor amb nom=nom.
	     * Post: Retorna un vector amb el nom i el label de l'Autor.
     * @param nom
     * @return 
     * @throws java.lang.Exception 
	     */
	    public ArrayList<String> consultarAutor(String nom) throws Exception{
	    	int id = graf.getIdByNameAndType(nom, "autor");
	    	Autor a=(Autor) graf.consultarEntitat(id);
	    	int num=a.getLabel();
	    	String label="";
	    	switch (num) {
            case 0:
                label="DATABASE";
                break;
            case 1:
                label="DATA_MINING";
                break;
            case 2:
                label="AI";
                break;
            case 3:
                label="INFORMATION_RETRIEVAL";
                break;
            default:
                break;
	    	}
	    	ArrayList<String> list =new ArrayList<String>();
	    	list.add(nom); 
	    	list.add(label);
	    	return list;
	    }
	    
	    /**
	     * Pre: Existeix una Conferencia amb nom=nom.
	     * Post: Retorna un vector amb el nom i el label de la Conferencia.
     * @param nom
     * @return 
     * @throws java.lang.Exception 
	     */
	    public ArrayList<String> consultarConferencia(String nom) throws Exception{
	    	int id = graf.getIdByNameAndType(nom, "conferencia");
	    	Conferencia c=(Conferencia) graf.consultarEntitat(id);
	    	int num=c.getLabel();
	    	String label="";
	    	switch (num) {
            case 0:
                label="DATABASE";
                break;
            case 1:
                label="DATA_MINING";
                break;
            case 2:
                label="AI";
                break;
            case 3:
                label="INFORMATION_RETRIEVAL";
                break;
            default:
                break;
	    	}
	    	ArrayList<String> list =new ArrayList<String>();
	    	list.add(nom); 
	    	list.add(label);
	    	return list;
	    }
	    
	    /**
	     * Pre: Existeix un Paper amb nom=nom.
	     * Post: Retorna un vector amb el nom i el label del Paper.
     * @param nom
     * @return 
     * @throws java.lang.Exception 
	     */
	    public ArrayList<String> consultarPaper(String nom) throws Exception{
	    	int id = graf.getIdByNameAndType(nom, "paper");
	    	Paper p=(Paper) graf.consultarEntitat(id);
	    	int num=p.getLabel();
	    	String label="";
	    	switch (num) {
            case 0:
                label="DATABASE";
                break;
            case 1:
                label="DATA_MINING";
                break;
            case 2:
                label="AI";
                break;
            case 3:
                label="INFORMATION_RETRIEVAL";
                break;
            default:
                break;
	    	}
	    	ArrayList<String> list =new ArrayList<String>();
	    	list.add(nom); 
	    	list.add(label);
	    	return list;
	    }
	    
	    /**
	     * Pre: Existeix una Entitat amb nom=nom i tipus=tipus.
	     * Post: Retorna el label en format String.
     * @param nom
     * @param tipus
     * @return 
     * @throws java.lang.Exception
	     */
	    public String consultarLabel(String nom, String tipus) throws Exception{
	    	int id = graf.getIdByNameAndType(nom, tipus);
	    	Entitat e=graf.consultarEntitat(id);
	    	int num=4;
	    	if (e.isAutor()){
	    		Autor a=(Autor) e;
	    		num=a.getLabel();
	    	}
	    	else if (e.isConferencia()){
	    		Conferencia c=(Conferencia) e;
	    		num=c.getLabel();
	    	}
	    	else if (e.isPaper()){
	    		Paper p=(Paper) e;
	    		num=p.getLabel();
	    	}
	    	else {
	    		//Exception
	    	}
	    	
	    	String label="";
	    	switch (num) {
            case 0:
                label="DATABASE";
                break;
            case 1:
                label="DATA_MINING";
                break;
            case 2:
                label="AI";
                break;
            case 3:
                label="INFORMATION_RETRIEVAL";
                break;
            default:
                break;
	    	}
	    	return label;
	    }
	    
	    /**
	     * Pre: -
	     * Post: Retorna un vector de vectors amb el nom i el tipus de totes les entitats del graf.
     * @return 
     * @throws java.lang.Exception 
	     */
	    public ArrayList<ArrayList<String>> consultarConjuntEntitats() throws Exception{
	    	
	    	ArrayList<ArrayList<String>> list=new ArrayList<ArrayList<String>>();
	    	Map m=graf.getConjuntEntitats();
	    	Set<Integer> s=m.keySet();
	    	for (int id : s) {
	    		ArrayList<String> aux=new ArrayList<String>();
	    		Entitat e=(Entitat)m.get(id);
	    		String nom=e.getNom();
	    		String tipus;
	    		if(e.isAutor()) tipus="autor";
	    	    else if(e.isConferencia()) tipus="conferencia";
	    	    else if(e.isPaper()) tipus="paper";
	    	    else tipus="terme";
	    	    aux.add(nom);aux.add(tipus);
	    	    list.add(aux);
	    	}
	    	return list;
	    }
	    
	    /**
	     * Pre: -
	     * Post: Retorna un vector amb el nom de tots els Autors del graf.
     * @return 
     * @throws java.lang.Exception
	     */
	    public ArrayList<String> consultarAutors() throws Exception{
	    	ArrayList<String> list=new ArrayList<String>();
	    	Map m=graf.getConjuntEntitats();
	    	Set<Integer> s=m.keySet();
	    	for (int id : s) {
	    		Entitat e=(Entitat)m.get(id);
	    	    if(e.isAutor()) list.add(e.getNom());
	    	}
	    	return list;
	    }
	    
	    /**
	     * Pre: -
	     * Post: Retorna un vector amb el nom de totes les Conferencies del graf.
     * @return 
     * @throws java.lang.Exception 
	     */
	    public ArrayList<String> consultarConferencies() throws Exception{
	    	ArrayList<String> list=new ArrayList<String>();
	    	Map m=graf.getConjuntEntitats();
	    	Set<Integer> s=m.keySet();
	    	for (int id : s) {
	    		Entitat e=(Entitat)m.get(id);
	    	    if(e.isConferencia()) list.add(e.getNom());
	    	}
	    	return list;
	    }
	    
	    /**
	     * Pre: -
	     * Post: Retorna un vector amb el nom de tots els Papers del graf.
     * @return 
     * @throws java.lang.Exception 
	     */
	    public ArrayList<String> consultarPapers() throws Exception{
	    	ArrayList<String> list=new ArrayList<String>();
	    	Map m=graf.getConjuntEntitats();
	    	Set<Integer> s=m.keySet();
	    	for (int id : s) {
	    		Entitat e=(Entitat)m.get(id);
	    	    if(e.isPaper()) list.add(e.getNom());
	    	}
	    	return list;
	    }
	    
	    /**
	     * Pre: -
	     * Post: Retorna un vector amb el nom de tots els Termes del graf.
     * @return 
     * @throws java.lang.Exception
	     */
	    public ArrayList<String> consultarTermes() throws Exception{
	    	ArrayList<String> list=new ArrayList<String>();
	    	Map m=graf.getConjuntEntitats();
	    	Set<Integer> s=m.keySet();
	    	for (int id : s) {
	    		Entitat e=(Entitat)m.get(id);
	    	    if(e.isTerme()) list.add(e.getNom());
	    	}
	    	return list;
	    }	    
	    
	    /**
	     * Pre: Existeix un Autor amb nom=nomA i un Paper amb nom=nomP.
	     * Post: S'ha afegit la relacio entre l'Autor amb nom=nomA i el Paper amb nom=nomP.
     * @param nomA
     * @param nomP
     * @throws java.lang.Exception
	     */
	    public void afegirRelacioAP (String nomA, String nomP) throws Exception{
	        int id1 = graf.getIdByNameAndType(nomA, "autor");
	        int id2 = graf.getIdByNameAndType(nomP, "paper");
	        graf.afegirRelacio(id1,id2);
	    }
	    
	    /**
	     * Pre: Existeix una Conferencia amb nom=nomC i un Paper amb nom=nomP.
	     * Post: S'ha afegit la relacio entre la Conferencia amb nom=nomC i el Paper amb nom=nomP.
     * @param nomC
     * @param nomP
     * @throws java.lang.Exception
	     */
	    public void afegirRelacioCP (String nomC, String nomP) throws Exception{
	        int id1 = graf.getIdByNameAndType(nomC, "conferencia");
	        int id2 = graf.getIdByNameAndType(nomP, "paper");
	        graf.afegirRelacio(id1,id2);
	    }
	    
	    /**
	     * Pre: Existeix un Terme amb nom=nomT i un Paper amb nom=nomP.
	     * Post: S'ha afegit la relacio entre el Terme amb nom=nomT i el Paper amb nom=nomP.
     * @param nomT
     * @param nomP
     * @throws java.lang.Exception
	     */
	    public void afegirRelacioTP (String nomT, String nomP) throws Exception{
	        int id1 = graf.getIdByNameAndType(nomT, "terme");
	        int id2 = graf.getIdByNameAndType(nomP, "paper");
	        graf.afegirRelacio(id1,id2);
	    }
	    
	    /**
	     * Pre: Existeix una relacio entre l'Autor amb nom=nomA i el Paper amb nom=nomP.
	     * Post: S'ha esborrat la relacio entre l'Autor amb nom=nomA i el Paper amb nom=nomP.
     * @param nomA
     * @param nomP
     * @throws java.lang.Exception
	     */
	    public void esborrarRelacioAP (String nomA, String nomP) throws Exception{
	        int id1 = graf.getIdByNameAndType(nomA, "autor");
	        int id2 = graf.getIdByNameAndType(nomP, "paper");
	        graf.esborrarRelacio(id1,id2);
	    }
	    
	    /**
	     * Pre: Existeix una relacio entre la Conferencia amb nom=nomC i el Paper amb nom=nomP.
	     * Post: S'ha esborrat la relacio entre la Conferencia amb nom=nomC i el Paper amb nom=nomP.
     * @param nomC
     * @param nomP
     * @throws java.lang.Exception
	     */
	    public void esborrarRelacioCP (String nomC, String nomP) throws Exception{
	        int id1 = graf.getIdByNameAndType(nomC, "conferencia");
	        int id2 = graf.getIdByNameAndType(nomP, "paper");
	        graf.esborrarRelacio(id1,id2);
	    }
	    
	    /**
	     * Pre: Existeix una relacio entre el Terme amb nom=nomT i el Paper amb nom=nomP.
	     * Post: S'ha esborrat la relacio entre el Terme amb nom=nomT i el Paper amb nom=nomP.
     * @param nomT
     * @param nomP
     * @throws java.lang.Exception
	     */
	    public void esborrarRelacioTP (String nomT, String nomP) throws Exception{
	        int id1 = graf.getIdByNameAndType(nomT, "terme");
	        int id2 = graf.getIdByNameAndType(nomP, "paper");
	        graf.esborrarRelacio(id1,id2);
	    }
}




