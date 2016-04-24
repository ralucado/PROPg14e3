public class Query {
	protected Cami cami;
	
	public Query(Cami c) {
		this.cami = c;
	}
	
	protected Query() throws Exception {
		
	}
	
	public String getPath() {
		return cami.getPath();
	}
	
	public void setPath(String cami){}
	
	
	public boolean esClustering(){
		return false;
	}
	
	public boolean esNormal(){
		return false;
	}
}
