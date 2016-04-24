package Domini;

public class QueryNormal extends Query{
	private String cami;
	private Entitat EntitatInicial;
	private Entitat EntitatNoInicial;

	
	public QueryNormal(Cami c) throws Exception{
		
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
	
	
	public Entitat getEntitatInicial(){
		return EntitatInicial;
	}
	
	public Entitat getEntitatNoInicial(){
		return EntitatNoInicial;
	}
	
	
	public void setEntitatInicial(Entitat e) throws Exception{
		char c = cami.charAt(0);
		if(c=='A'){
			if(e.isAutor()) EntitatInicial = e;
			else throw new Exception("Tipus d'entitat no concorda");
		}
		else if(c=='P'){
			if(e.isPaper()) EntitatInicial = e;
			else throw new Exception("Tipus d'entitat no concorda");
		}
		else if(c=='C'){
			if(e.isConferencia()) EntitatInicial = e;
			else throw new Exception("Tipus d'entitat no concorda");
		}
		else if(c=='T'){
			if(e.isTerme()) EntitatInicial = e;
			else throw new Exception("Tipus d'entitat no concorda");
		}
	}
	
	
	
	public void setEntitatNoInicial(Entitat e) throws Exception{
			int i = cami.length()-1;
			char c = cami.charAt(i);
			if(e.isAutor() && c=='A'){
				EntitatNoInicial = e;
				
				
			}
			
			else if(e.isPaper() && c=='P'){
				EntitatNoInicial = e;
			}
			
			else if(e.isConferencia() && c=='C'){
				EntitatNoInicial = e;
			}
			
			else if(e.isTerme() && c=='T'){
				EntitatNoInicial = e;
			}
			else throw new Exception("Tipus d'entitat no concorda");
	}

	
	
	public ArrayList<Pair<Integer, Float>> executa(HeteSim HS) throws Exception{
		if(cami!=null){
			if(EntitatInicial != null){
				if(EntitatNoInicial != null){
					 Cami c =  new Cami("cami", cami, "descripcio");
					 Float f = HS.getHeteSim(c, EntitatInicial, Entitatcg.NoInicial);
					 Pair<Integer,Float> p= new Pair<Integer,Float>(EntitatNoInicial.getId(), f);
					 ArrayList<Pair<Integer,Float> > A = new ArrayList<Pair<Integer,Float>>();
					 A.add(p);
					 return A;
				}
				else{
					Cami c =  new Cami("cami", cami, "descripcio");
					return HS.getHeteSim(c, EntitatInicial);
				}
			}
			else throw new Exception("No hi ha entitat inicial");
		}
		else throw new Exception("No hi ha camí");
		
	}
}
