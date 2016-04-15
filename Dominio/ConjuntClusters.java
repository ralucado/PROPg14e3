package Dominio;

import java.util.ArrayList;

public class ConjuntClusters {
	private ArrayList<Cluster> cjt;
	private int k;

	public ConjuntClusters(int k) {
		this.k = k;
		cjt = new ArrayList<Cluster>(k);

		for (int i = 0; i < k; ++i) cjt.add(new Cluster());
	}

	public Cluster getCluster(int i) {
		return cjt.get(i);
	}

	public int getK() {
		return k;
	}

	public void updateCentroids() {
		for (int i = 0; i < k; ++i) cjt.get(i).updateCentroid();
	}

	public void changeCluster(ElemCluster e, int cl1, int cl2) {
		cjt.get(cl2).addElem(e);
		cjt.get(cl1).deleteElem(e);
	}
	
	public int getClosestCentroid(double hs) {
		// Moure a QueryClustering?
		return 0;
	}

}