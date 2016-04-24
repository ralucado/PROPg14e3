package TestConjuntClusters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

/**
 * 
 * La classe <tt>Cluster</tt> representa un conjunt d'elements. Un cluster té un <i>medoide</i>, que és l'element més similar a la resta.
 * @author Arnau Blanch
 * 
 */
public class Cluster {
	private ArrayList<Integer> list;
	private Integer medoid;
	
	/**
	 * Construeix un nou <tt>Cluster</tt> buit i sense cap <i>medoide</i> assignat.
	 */
	public Cluster() {
		list = new ArrayList<Integer>();
		medoid = null;
	}
	
	/**
	 * Retorna un <tt>ArrayList</tt> amb la llista d'entitats (posicions a la matriu de similaritat) que formen part del clúster.
	 * @return  una llista de les entitats que formen part del clúster
	 */
	public ArrayList<Integer> getList() {
		return list;
	}
	
	/**
	 * Retorna el número d'elements que té el clúster.
	 * @return núm. elements del clúster
	 */
	public int size() {
		return list.size();
	}
	
	/**
	 * Afegeix l'entitat <tt>m</tt> al clúster.
	 * @param e Entitat (posició) a afegir
	 * @throws Exception si l'entitat m ja existia al clúster
	 * @throws IllegalArgumentException si <tt>e</tt> no és vàl·lida
	 */
	public void add(int e) throws Exception {
		if (e < 0) throw new IllegalArgumentException("La posició d'entitat no és vàl·lida");
		else if (!list.contains(e)) list.add(e);
		else throw new Exception("La posició d'entitat ja existia al clúster");
		
	}
	
	/**
	 * Esborra l'element <tt>e</tt> del clúster.
	 * @param e Element a esborrar
	 * @throws Exception si l'element e no estava al clúster
	 */
	public void deleteElem(Integer e) throws Exception {
		if (!list.contains(e)) throw new Exception("'e' no estava al clúster");
		else {
			if (e == medoid) medoid = null;
			list.remove(e);
		}
	}
	
	/**
	 * Esborra l'element a la posició <tt>i</tt> del clúster.
	 * @param i L'índex de l'element a esborrar
	 * @throws Exception si l'índex 'i' està fora de rang (i &lt; 0 o i &gt;= mida clúster) 
	 */
	public void deleteIndex(int i) throws Exception {
		if (i < 0 || i >= list.size()) throw new IndexOutOfBoundsException("'i' fora de rang");
		else {
			if (list.get(i) == medoid) medoid = null;
			list.remove(i);
		}
	}
	
	/** 
	 * Retorna l'element a la posició <tt>i</tt> del clúster
	 * @param i posició de l'element
	 * @return element a la posició especificada
	 * @throws IndexOutOfBoundsException si l'índex 'i' està fora de rang (i &lt; 0 o i &gt;= mida clúster)
	 */
	
	public int get(int i) throws Exception {
		if (i < 0 || i >= list.size()) throw new IndexOutOfBoundsException("'i' fora de rang");
		return list.get(i);
	}
	
	/**
	 * Retorna la posició del medoide del clúster a la matriu de rellevància
	 * @return medoide del clúster
	 * @throws Exception si no hi ha cap medoide assignat
	 * @throws IndexOutOfBoundsException si el clúster està buit
	 */
	public int getMedoid() throws Exception {
		if (list.isEmpty()) throw new IndexOutOfBoundsException("El clúster està buit");
		else if (medoid == null) throw new Exception("No hi ha medoide assignat");
		return medoid;
	}
	
	/**
	 * Canvia el medoide del clúster (si existia)
	 * @param m medoide del clúster
	 * @throws Exception si el medoide no és vàl·lid o si no estava al clúster
	 */
	public void setMedoid(int m) throws Exception {
		if (m < 0) throw new IllegalArgumentException("'m' no és vàl·lida");
		else if (list.contains(m)) medoid = m;
		else throw new Exception("L'entitat no existeix al clúster");
	}
	
	/**
	 * Afegeix l'entitat <tt>m</tt> al clúster i l'assigna com a <i>medoide</i>.
	 * @param m Entitat (posició) a afegir al clúster
	 * @throws Exception si l'entitat m ja existia al clúster
	 * @throws IllegalArgumentException si <tt>m</tt> no és vàl·lida
	 */
	public void addMedoid(int m) throws Exception {
		if (m < 0) throw new IllegalArgumentException("'m' no és vàl·lida");
		else if (!list.contains(m)) {
			medoid = m;
			list.add(m);
		}
		else throw new Exception("L'entitat ja existia al clúster");
	}

	
	/**
	 * Recalcula el medoide del clúster segons la rellevància entre elements
	 * @param M matriu de rellevància
	 * @throws Exception si hi ha elements al clúster que no són posicions vàl·lides a la matriu M
	 */
	public void updateMedoid(SparseMatrix M) throws Exception {
		HashMap<Integer,Float> relevances = new HashMap<Integer,Float>();
		
		// Per a cada entitat de la llista, sumem la similaritat amb cada altre element del clúster
		for (Integer i : list) {
			if (i < 0 || i >= M.getNRows()) throw new Exception ("Elements no vàl·lids al clúster per a la matriu donada");
			Float sum = new Float(0.0);
			for (Integer j : list) {
				if (j < 0 || j >= M.getNRows()) throw new Exception ("Elements no vàl·lids al clúster per a la matriu donada");
				else if (i != j)  sum += M.getRow(i).get(j);
			}
			relevances.put(i, sum);
		}

		// No cal l'if que hi ha a ConjuntClusters perquè no pot ser que els elements del cluster no es relacionin entre ells 
		
		HashMap<Integer,Float> orderedRelev = ordenaPerValor(relevances);
		// Tenim un map ordenat per la suma de similaritats (ordre descendent)
		
		//System.out.println("Test: "+orderedRelev);
		
		// Agafem els elements que tenen el valor màxim de la suma de rellevàncies
		Iterator<HashMap.Entry<Integer,Float>> it = orderedRelev.entrySet().iterator();
		Boolean changed = false;
		LinkedList<HashMap.Entry<Integer,Float>> medCandidates =
				new LinkedList<HashMap.Entry<Integer,Float>>(); // Candidats a medoide
		HashMap.Entry<Integer, Float> first = it.next(); // 1r element amb suma màxima
		medCandidates.add(first);
		
		
		while (it.hasNext() && !changed) { // mentre quedin elements i no hagi canviat la suma
			HashMap.Entry<Integer, Float> elem = it.next();
			changed = (elem.getValue() != first.getValue());
			if (!changed) medCandidates.add(elem);
		}
		
		// En cas d'haver-hi més d'un candidat, el medoide es selecciona aleatòriament
		if (medCandidates.size() > 1) {
			Random r = new Random();
			int c = r.nextInt(medCandidates.size()); // es genera un nombre aleatori de l'interval [0, medCandidates.size())
			medoid = medCandidates.get(c).getKey();
		}
		else medoid = medCandidates.getFirst().getKey();
	}

	/**
	 * Ordena 'map' en ordre descedent per valor
	 * @param map Map a ordenar
	 * @return mapa ordenat
	 */
	public static HashMap<Integer,Float> ordenaPerValor(HashMap<Integer,Float> map) {
		HashMap<Integer, Float> mapOrdenat = new LinkedHashMap<Integer,Float>();
		
		// Llista amb els elements de "map"
		LinkedList<HashMap.Entry<Integer, Float>> elems =
				new LinkedList<HashMap.Entry<Integer,Float>>(map.entrySet());
		
		// Sobreescrivim el mètode de comparació i ordenem la llista "elems"
		Collections.sort(elems, new Comparator<HashMap.Entry<Integer,Float>>() {
            @Override
            public int compare(HashMap.Entry<Integer, Float> e1, HashMap.Entry<Integer, Float> e2) {
                return e2.getValue().compareTo(e1.getValue()); // Ordre descendent
            }
        });
		
		// Inserim el contingut d'"elems" a "mapOrdenat"
		for (HashMap.Entry<Integer,Float> e : elems) 
			mapOrdenat.put(e.getKey(), e.getValue());
		
		return mapOrdenat;
	}
}
