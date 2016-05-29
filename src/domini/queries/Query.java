package domini.queries;

import domini.camins.Cami;
/**
 * La classe Query és una classe abstracta que té com a subclasses QueryNormal i QueryClustering
 * @author Marc Villanueva Cuadrench
 *
 */
public abstract class Query {
	protected Cami cami;
	
	/**
	 * Creadora que guarda un Camí 
	 * @param c Camí a guardar
	 */
	public Query(Cami c) {
		this.cami = c;
	}
	
	/**
	 * Creadora per defecte
	 * @throws Exception
	 */
	protected Query(){
		
	}
	
	/**
	 * Consultora del camí de la query
	 * @return String amb el camí de la query
	 */
	public String getPath() {
		return cami.getPath();
	}

	/**
	 * Mètode abstracte per saber si la query és de tipus clustering
	 * @return Depen de la subclasse
	 */
	public abstract boolean esClustering();
	
	/**
	 * Mètode abstracte per saber si la query és de tipus normal
	 * @return Depen de la subclasse
	 */
	public abstract boolean esNormal();
}

