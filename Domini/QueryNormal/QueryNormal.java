package Domini;
import java.util.ArrayList;

public class QueryNormal extends Query{
	private String cami;
	private Entitat EntitatInicial;
	private int posEI;
	private Entitat EntitatNoInicial;
	private int posEF;

	
	public QueryNormal(Cami c) throws Exception{
		cami = c.getPath();
	}
	
	public boolean esClustering(){
		return false;
	}
	
	public boolean esNormal(){
		return true;
	}
	
	public String getPath(){
		return cami;
	}
	
	
	public int getEI(){
		return posEI;
	}
	
	public int getEF(){
		return posEF;
	}
	
	public Entitat getEntitatInicial(){
		return EntitatInicial;
	}
	
	public Entitat getEntitatNoInicial(){
		return EntitatNoInicial;
	}
	
	
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

	
	
	public ArrayList<Pair<Integer, Float>> executa(HeteSim HS) throws Exception{
		if(cami!=null){
			if(EntitatInicial != null){
				if(EntitatNoInicial != null){
					 Cami c =  new Cami("cami", cami, "descripcio");
					 Float f = HS.getHeteSim(c, posEI, posEF);
					 Pair<Integer,Float> p= new Pair<Integer,Float>(EntitatNoInicial.getId(), f);
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
		else throw new Exception("No hi ha cam�");
		
	}
}
