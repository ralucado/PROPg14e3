package domini.queries;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import domini.graf.Autor;
import domini.camins.Cami;
import domini.graf.Conferencia;
import domini.graf.Entitat;
import domini.graf.Graf;
import domini.queries.HeteSim;
import domini.queries.Pair;
import domini.graf.Paper;
import domini.graf.Terme;
import domini.queries.SparseMatrix;
public class TestHeteSim {
	
	/**
	 * Readme:
	 * Podeu canviar el valor dels atributs N_PAPERS, N_AUTORS, N_CONFERENCIES, N_TERMS per determinar quantes entitats voleu que tingui el graf
	 * 
	 */

	public static void main (String [ ] args) throws Exception {
    	int N_PAPERS = 3, N_AUTORS=4, N_CONFERENCIES = 3, N_TERMS = 0;
    	int N_FIRST = N_AUTORS, N_SECOND = N_CONFERENCIES;
    	String PATH = "APCPCPCPCPCPCPCPCPCPC";
    	
    	
    	
    	//////////
    	
    	ArrayList<ArrayList<String>> Relacions = new ArrayList<ArrayList<String>>();
        
        TreeMap<Integer, Entitat> m = new TreeMap<Integer,Entitat>();
        
        for(Integer i=1; i<=N_AUTORS; i++) {
	       	 Integer id = 10*i+0;
	       	 Autor a = new Autor(id.toString()); 
	       	 m.put(id, a);	
        }
        for(Integer i=1; i<=N_PAPERS; i++) {
      	 Integer id = 10*i+1;
      	 Paper p = new Paper(id.toString()); 	
      	 m.put(id, p);	
        }
        for(Integer i=1; i<=N_CONFERENCIES; i++) {
      	 Integer id = 10*i+2;
      	 Conferencia p = new Conferencia(id.toString()); 	
      	 m.put(id, p);	
        }
        for(Integer i=1; i<=N_TERMS; i++) {
         	 Integer id = 10*i+3;
         	 Terme p = new Terme(id.toString()); 	
         	 m.put(id, p);	
           }
        
        Graf g=new Graf(m,Relacions);
        ///////////////////////////////////////
        
        
        
        /*les relacions s'afegeixen de la seguent manera:
        primera xifra del id: index de la entitat: enre 1 i N_ENTITAT
        la segona xifra del id es
        Autor: 0
        Paper: 1
        Conferencia: 2
        Terme: 3
        
        No es poden afegir relacions entre entitats que no existeixen
        No es poden afegir les mateixes relacions mes duna vegada
        */
        
        //Autor->Paper
    	g.afegirRelacio(10, 11);
    	g.afegirRelacio(20, 21);
    	g.afegirRelacio(30, 21);
    	g.afegirRelacio(40, 31);
    	
    	//Conferencia -> Paper
    	g.afegirRelacio(12, 11);
    	g.afegirRelacio(22, 11);
    	g.afegirRelacio(22, 21);
    	g.afegirRelacio(22, 31);
    		//tambe es poden deixar entitats sense relacionar
    	
    	//Terme->Paper
    		//no en posem cap per provar casos extrems, podeu afegirne si desitjeu
    	
    	
    	
    	//NO TOCAR RES A PARTIR D'AQUI
    	
		DecimalFormat df = new DecimalFormat("##.###");
		df.setRoundingMode(RoundingMode.DOWN);
    	
    	HeteSim HS = new HeteSim(g);
    	Cami p = new Cami("coautor",PATH,"desc");
    	
    	SparseMatrix res = HS.getHeteSim(p);
    	System.out.println(res.toString());
    	
    	System.out.println("\nEl mateix per cada entitat inicial\n");
    	
    	for(Integer i = 1; i <= N_FIRST; ++i){
    		System.out.println(i);
    		int id1;
			if(N_FIRST == N_PAPERS) id1 = g.getPositionByIdPaper(10*i+1);
			else if (N_FIRST == N_AUTORS) id1 = g.getPositionByIdAutor(10*i);
			else if (N_FIRST == N_CONFERENCIES) id1 = g.getPositionByIdConferencia(10*i+2);
			else id1 = g.getPositionByIdTerme(10*i+3);
			
	    	ArrayList<Pair<Integer, Float>> resa = HS.getHeteSim(p, id1);
	    	System.out.println("autor id: " + 10*i + "->"+ resa.toString());
    	}
    	
    	System.out.println("\nEl mateix per cada entitat inicial i final\n");
    	
    	for(int i = 1; i <= N_FIRST; ++i){
    		for (int j = 1; j <= N_SECOND; ++j){
    			
    			
    			int id1;
    			if(N_FIRST == N_PAPERS) id1 = g.getPositionByIdPaper(10*i+1);
    			else if (N_FIRST == N_AUTORS) id1 = g.getPositionByIdAutor(10*i);
    			else if (N_FIRST == N_CONFERENCIES) id1 = g.getPositionByIdConferencia(10*i+2);
    			else id1 = g.getPositionByIdTerme(10*i+3);
    			
    			
    			int id2;
    			if(N_SECOND == N_PAPERS) id2 = g.getPositionByIdPaper(10*j+1);
    			else if (N_SECOND == N_AUTORS) id2 = g.getPositionByIdAutor(10*j);
    			else if (N_SECOND == N_CONFERENCIES) id2 = g.getPositionByIdConferencia(10*j+2);
    			else id2 = g.getPositionByIdTerme(10*j+3);
    			
	    	Float fresa = HS.getHeteSim(p, id1,id2);
	    	System.out.println("autor1 id: " + 10*i + " autor2 id: " + 10*j + " -> " + df.format(fresa));
	    	
    		}
    		System.out.println('\n');
    	}

    }
}