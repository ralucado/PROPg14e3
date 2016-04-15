package Dominio;

public class ElemCluster {
	private Entitat e1;
	private Entitat e2;
	private double heteSim;

	public ElemCluster(Entitat e1, Entitat e2, double heteSim) {
		this.e1 = e1;
		this.e2 = e2;
		this.heteSim = heteSim;
	}

	public Entitat getEnt1() {
		return e1;
	}

	public Entitat getEnt2() {
		return e2;
	}

	public double getHeteSim() {
		return heteSim;
	}
}