package domini.queries;
import java.util.ArrayList;

import domini.camins.Cami;
import domini.graf.Entitat;

/**
 * La classe QueryNormal és una subclasse de la classe Query per les queries de tipus normal (no clustering)
 * @author Marc Villanueva Cuadrench
 *
 */
public class QueryNormal extends Query{
	private String cami;
	private Entitat EntitatInicial;
	private int posEI;
	private Entitat EntitatNoInicial;
	private int posEF;

	/**
	 * Crea una instancia de QueryNormal amb el camí passat com a paràmetre
	 * @param c Camí de la query
	 * @throws Exception ...
	 */
	public QueryNormal(Cami c) throws Exception{
		super.cami = c;
		cami = c.getPath();
	}
	
	/**
	 * Retorna false ja que no és una query de clustering
	 */
	public boolean esClustering(){
		return false;
	}
	
	/**
	 * Retorna true ja que és una query normal
	 */
	public boolean esNormal(){
		return true;
	}
	
	/**
	 * Consultora del camí de la query
	 * @return Retorna un String amb el path del camí
	 */
	public String getPath(){
		return cami;
	}
	
	/**
	 * Consultora de la posició al graf de l'entitat inicial
	 * @return int amb la posició de la matriu del graf on es troba l'entitat inicial
	 */
	public int getEI(){
		return posEI;
	}
	
	/**
	 * Consultora de la posició al graf de l'entitat final
	 * @return int amb la posició de la matriu del graf on es troba l'entitat final
	 */
	public int getEF(){
		return posEF;
	}
	
	/**
	 * Consultora de l'entitat inicial de la query
	 * @return Objecte de tipus Entitat
	 */
	public Entitat getEntitatInicial(){
		return EntitatInicial;
	}
	
	/**
	 * Consultora de l'entitat final de la query
	 * @return Objecte de tipus Entitat
	 */
	public Entitat getEntitatNoInicial(){
		return EntitatNoInicial;
	}
	
	/**
	 * Guarda l'entitat inicial de la query
	 * @param e Entitat que es guardarà
	 * @param pos Posició de l'entitat a la matriu del graf
	 * @throws Exception El tipus de l'entitat no concorda amb el camí
	 */
	public void setEntitatInicial(Entitat e, int pos) throws Exception{
		char c = cami.charAt(0);
		if(c=='A'){
			if(e.isAutor()){ EntitatInicial = e; posEI = pos;}
			else throw new Exception("Tipus d'entitat no concorda");
		}
		else if(c=='P'){
			if(e.isPaper()){ EntitatInicial = e; posEI = pos;}
			else throw new Exception("Tipus d'entitat no concorda");
		}
		else if(c=='C'){
			if(e.isConferencia()){ EntitatInicial = e; posEI = pos;}
			else throw new Exception("Tipus d'entitat no concorda");
		}
		else if(c=='T'){
			if(e.isTerme()){ EntitatInicial = e; posEI = pos;}
			else throw new Exception("Tipus d'entitat no concorda");
		}
	}
	
	
	/**
	 * Guarda l'entitat final de la query
	 * @param e Entitat que es guardarà
	 * @param pos Posició de l'entitat a la matriu del graf
	 * @throws Exception El tipus de l'entitat no concorda amb el camí
	 */
	public void setEntitatNoInicial(Entitat e, int pos) throws Exception{
			int i = cami.length()-1;
			char c = cami.charAt(i);
			if(c=='A'){
				if(e.isAutor()){ EntitatNoInicial = e; posEF = pos;}
				else throw new Exception("Tipus d'entitat no concorda");
			}
			else if(c=='P'){
				if(e.isPaper()){ EntitatNoInicial = e; posEF = pos;}
				else throw new Exception("Tipus d'entitat no concorda");
			}
			else if(c=='C'){
				if(e.isConferencia()){ EntitatNoInicial = e; posEF = pos;}
				else throw new Exception("Tipus d'entitat no concorda");
			}
			else if(c=='T'){
				if(e.isTerme()){ EntitatNoInicial = e; posEF = pos;}
				else throw new Exception("Tipus d'entitat no concorda");
			}
	}

	/**
	 * Executa la query
	 * @param HS Objecte de tipus HeteSim que s'utilitza per calcular el resultat
	 * @return Vector d'entitats amb els seus valors d'HeteSim que representa el resultat de la query
	 * @throws Exception Falta algun paràmetre de la query
	 */
	public ArrayList<Pair<Integer, Float>> executa(HeteSim HS) throws Exception{
		if(cami!=null){
			if(EntitatInicial != null){
				if(EntitatNoInicial != null){
					 Cami c =  new Cami("cami", cami, "descripcio");
					 Float f = HS.getHeteSim(c, posEI, posEF);
					 Pair<Integer,Float> p= new Pair<Integer,Float>(posEF, f);
					 ArrayList<Pair<Integer,Float> > A = new ArrayList<Pair<Integer,Float>>();
					 A.add(p);
					 return A;
				}
				else{
					Cami c =  new Cami("cami", cami, "descripcio");
					return HS.getHeteSim(c, posEI);
				}
			}
			else throw new Exception("No hi ha entitat inicial");
		}
		else throw new Exception("No hi ha camí");
		
	}
}

