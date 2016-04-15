package Dominio;

public class QueryClustering extends Query {
	private int k;

	public QueryClustering(int k) {
		this.k = k;
	}

	public int getK() {
		return k;
	}

	public ConjuntClusters executar() {
		ConjuntClusters conjunt = new ConjuntClusters(k);
		/*

			Codi k-means
		
		*/
		return conjunt;
	}
}