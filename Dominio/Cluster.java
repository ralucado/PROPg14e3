package Dominio;

import java.util.ArrayList;

public class Cluster {
	private ArrayList<ElemCluster> list;
	private double centroid;

	public Cluster() {
		centroid = 0;
	}

	public int getSize() {
		return list.size();
	}

	public ElemCluster getElem(int i) {
		return list.get(i);
	}

	public void deleteElem(int i) {
		list.remove(i);
	}
	
	public void deleteElem(ElemCluster ec) {
		list.remove(ec);
	}

	public void addElem(ElemCluster ec) {
		list.add(ec);
	}

	public double getCentroid() {
		return centroid;
	}

	public void setCentroid(double cent) {
		this.centroid = cent;
	}

	public void updateCentroid() {
		double suma = 0;
		for (int i = 0; i < list.size(); ++i) {
			suma += list.get(i).getHeteSim();
		}

		centroid = suma/(double)list.size();
	}
}