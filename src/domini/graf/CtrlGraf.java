package domini.graf;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import persistencia.CtrlPersistencia;

/**
 * És el controlador del graf.
 * @author Enunciat 14.2
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CtrlGraf {
	
    /**
     *
     */
    protected Graf graf;

		/**
		 * Crea un nou CtrlGraf.
		 */
	 	public CtrlGraf(){
	 		graf=new Graf();
	 	}
	 	
	 	/**
	 	 * Assigna al CtrlGraf el graf <tt>grafAux</tt>.
	 	 * @param grafAux graf a assignar
	 	 */
	 	public void setGraf(Graf grafAux){
	 		graf=grafAux;
	 	}
	 	
    /**
     * Retorna el graf del controlador
     * @return graf del controlador
     */
    public Graf consultarGraf(){
	 		return graf;
	 	}

 	/**
 	 * Carrega el graf des de un fitxer, i retorna el graf.
     * @return graf carregat
     * @throws Exception ...
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
 	 * Guarda el graf actual a un fitxer.
 	 * @throws Exception ...
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
 	 * S'ha afegit l'Autor amb nom=nom i label=label al graf.
 	 * @param nom nom de l'autor
 	 * @param label label de l'autor
 	 * @throws Exception ...
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
 	 * S'ha afegit la Conferencia amb nom=nom i label=label al graf.
	 * @param nom nom de la conferència
	 * @param label label de la conferència
	 * @throws Exception ...
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
 	 * S'ha afegit el Paper amb nom=nom i label=label al graf.
	 * @param nom nom del paper
	 * @param label label del paper
	 * @throws Exception ...
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
 	 * S'ha afegit el Terme amb nom=nom al graf.
	 * @param nom nom del terme
	 * @throws Exception ...
 	 */
    public void altaTerme(String nom) throws Exception{
        Terme t = new Terme(nom);
        graf.afegirEntitat(t);
    }
    
    /**
     * S'ha eliminat l'Autor amb nom=nom del graf.
     * @param nom nom de l'autor
     * @throws Exception ...
     */
    public void baixaAutor(String nom) throws Exception{
        int id = graf.getIdByNameAndType(nom, "autor");
        graf.eliminarEntitat(id);
    }
  
    /**
     * S'ha eliminat la Conferencia amb nom=nom del graf.
	 * @param nom nom de la conferència
	 * @throws Exception ...
     */
    public void baixaConferencia(String nom) throws Exception{
        int id = graf.getIdByNameAndType(nom, "conferencia");
        graf.eliminarEntitat(id);
    }
    
    /**
     * S'ha eliminat el Paper amb nom=nom del graf.
	 * @param nom nom del paper
	 * @throws Exception ...
     */
    public void baixaPaper(String nom) throws Exception{
        int id = graf.getIdByNameAndType(nom, "paper");
        graf.eliminarEntitat(id);
    }
    
    /**
     * S'ha eliminat el Terme amb nom=nom del graf.
	 * @param nom nom del terme
	 * @throws Exception ...
     */
    public void baixaTerme(String nom) throws Exception{
        int id = graf.getIdByNameAndType(nom, "terme");
        graf.eliminarEntitat(id);
    }
    
    /**
     * Retorna un vector amb el nom, el tipus i el label (si en te).
	 * @param id identificador de l'entitat
	 * @return nom, tipus i label de l'entitat
	 * @throws Exception ... 
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
     * Retorna un vector amb el nom i el label de l'Autor.
	 * @param nom nom de l'autor
	 * @return nom i label de l'autor
	 * @throws Exception ... 
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
     * Retorna un vector amb el nom i el label de la Conferencia.
	 * @param nom nom de la conferència
	 * @return nom i label de la conferència
	 * @throws Exception ... 
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
     * Retorna un vector amb el nom i el label del Paper.
	 * @param nom nom del paper
	 * @return nom i label del paper
	 * @throws Exception ... 
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
     * Retorna el label en format String.
	 * @param nom nom de l'entitat
	 * @param tipus tipus de l'entitat
	 * @return label de l'entitat
	 * @throws Exception ...
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
     * Retorna un vector de vectors amb el nom i el tipus de totes les entitats del graf.
	 * @return noms i tipus de totes les entitats del graf
	 * @throws Exception ... 
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
     * Retorna un vector amb el nom de tots els Autors del graf.
	 * @return noms de tots els autors del graf
	 * @throws Exception ...
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
     * Retorna un vector amb el nom de totes les Conferencies del graf.
	 * @return noms de totes les conferències del graf
	 * @throws Exception ... 
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
     * Retorna un vector amb el nom de tots els Papers del graf.
	 * @return noms de tots els papers del graf
	 * @throws Exception ...
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
     * Retorna un vector amb el nom de tots els Termes del graf.
 	 * @return noms de tots els termes del graf 
 	 * @throws Exception ...
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
     * S'ha afegit la relacio entre l'Autor amb nom=nomA i el Paper amb nom=nomP.
	 * @param nomA nom de l'autor
	 * @param nomP nom del paper
	 * @throws Exception ...
     */
    public void afegirRelacioAP (String nomA, String nomP) throws Exception{
        int id1 = graf.getIdByNameAndType(nomA, "autor");
        int id2 = graf.getIdByNameAndType(nomP, "paper");
        graf.afegirRelacio(id1,id2);
    }
    
    /**
     * S'ha afegit la relacio entre la Conferencia amb nom=nomC i el Paper amb nom=nomP.
     * @param nomC nom de la conferència
     * @param nomP nom del paper
     * @throws Exception ...
     */
    public void afegirRelacioCP (String nomC, String nomP) throws Exception{
        int id1 = graf.getIdByNameAndType(nomC, "conferencia");
        int id2 = graf.getIdByNameAndType(nomP, "paper");
        graf.afegirRelacio(id1,id2);
    }
    
    /**
     * S'ha afegit la relacio entre el Terme amb nom=nomT i el Paper amb nom=nomP.
	 * @param nomT nom del terme
	 * @param nomP nom del paper
	 * @throws Exception ...
     */
    public void afegirRelacioTP (String nomT, String nomP) throws Exception{
        int id1 = graf.getIdByNameAndType(nomT, "terme");
        int id2 = graf.getIdByNameAndType(nomP, "paper");
        graf.afegirRelacio(id1,id2);
    }
    
    /**
     * S'ha esborrat la relacio entre l'Autor amb nom=nomA i el Paper amb nom=nomP.
	 * @param nomA nom de l'autor
	 * @param nomP nom del paper
	 * @throws Exception ...
     */
    public void esborrarRelacioAP (String nomA, String nomP) throws Exception{
        int id1 = graf.getIdByNameAndType(nomA, "autor");
        int id2 = graf.getIdByNameAndType(nomP, "paper");
        graf.esborrarRelacio(id1,id2);
    }
    
    /**
     * S'ha esborrat la relacio entre la Conferencia amb nom=nomC i el Paper amb nom=nomP.
	 * @param nomC nom de la conferència
	 * @param nomP nom del paper
	 * @throws Exception ...
     */
    public void esborrarRelacioCP (String nomC, String nomP) throws Exception{
        int id1 = graf.getIdByNameAndType(nomC, "conferencia");
        int id2 = graf.getIdByNameAndType(nomP, "paper");
        graf.esborrarRelacio(id1,id2);
    }
    
    /**
     * S'ha esborrat la relacio entre el Terme amb nom=nomT i el Paper amb nom=nomP.
	 * @param nomT nom del terme
	 * @param nomP nom del paper
	 * @throws Exception ...
     */
    public void esborrarRelacioTP (String nomT, String nomP) throws Exception{
        int id1 = graf.getIdByNameAndType(nomT, "terme");
        int id2 = graf.getIdByNameAndType(nomP, "paper");
        graf.esborrarRelacio(id1,id2);
    }
}




