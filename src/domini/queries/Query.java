package domini.queries;

import domini.camins.Cami;

public abstract class Query {
	protected Cami cami;
	
	public Query(Cami c) {
		this.cami = c;
	}
	
	protected Query() throws Exception {
		
	}
	
	public String getPath() {
		return cami.getPath();
	}

	
	
	public abstract boolean esClustering();
	
	public abstract boolean esNormal();
}

