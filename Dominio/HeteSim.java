package Dominio;

import java.util.ArrayList;

public class HeteSim {
	
	
	
	private Graf graf;
	
	private Matrix normaliceRows(Matrix m) {
		Matrix result = new Matrix(m.getRows(), m.getCols());
		
		for (Integer i = 0; i < m.getRows(); ++i) {
			Double total = 0.0;
			for (Integer j = 0; j < m.getCols(); ++j) {
				total += Math.pow(m.getRow(i).get(j),2);
			}
			total = Math.sqrt(total);
			for (Integer j = 0; j < m.getNCols(); ++j) {
				result.getRow(i).set(j,(float) (m.getRow(i).get(j)/total));
			}
		}
		return result;
	}
	
	/*
	 * Hay un objeto HeteSim para todo el grafo. Cuando el grafo cambia hay que rehacer el hetesim
	 */
	public HeteSim(Graf g){
		System.out.println("contructor Hetsim");
		graf = g;
	}
	
	public void setGraf(Graf g){
		System.out.println("set graf Hetsim");
		graf = g;
	}
	
	public Matrix consulta(Path cami){
		System.out.println("consulta path");
		return null;	
	}
	
	public ArrayList<Float> consulta(Path cami, Entitat e){
		System.out.println("consulta path + entitat");
		return null;	
	}
	
	public Float consulta(Path cami, Entitat e1, Entitat e2){
		System.out.println("consulta entitat + path + entitat");
		return null;	
	}

}