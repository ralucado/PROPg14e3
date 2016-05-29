package domini.queries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import domini.graf.Entitat;
import domini.graf.Graf;

import java.util.Iterator;

/**
 * La classe <tt>ConjuntClusters</tt> representa un conjunt de clústers i un conjunt d'elements que no pertanyen a cap clúster.
 * @author Arnau Blanch Cortès
 *
 */
public class ConjuntClusters {
	private ArrayList<Cluster> cjt;
	private int k;
	private ArrayList<Integer> noAssignats;
	
	/**
	 * Crea un nou ConjuntClusters amb capacitat inicial per a <tt>k</tt> clústers
	 * @param k nombre de clústers
	 * @throws Exception si <tt>k</tt> és igual o inferior a 0
	 */
	public ConjuntClusters(int k) throws Exception {
		if (k <= 0) throw new Exception("'k' no vàl·lida");
		this.k = k;
		this.cjt = new ArrayList<Cluster>(k);
		for (int i = 0; i < k; ++i) this.cjt.add(new Cluster());
		this.noAssignats = new ArrayList<Integer>();
	}
	
	/**
	 * Retorna el número de clústers del conjunt (<tt>k</tt>)
	 * @return número de clústers
	 */
	public int getK() {
		return this.k;
	}
	
	/**
	 * Afegeix un element a un dels clústers en cas que no existeixi al conjunt
	 * @param cl número de clúster
	 * @param elem element a afegir
	 * @throws Exception si l'element ja existeix en algun dels clústers o està com a no assignat o si el núm. de clúster no és vàl·lid
	 */
	public void afegirElement(int cl, int elem) throws Exception {
		if (cl < 0 || cl >= k) throw new Exception("Número de clúster invàl·lid.");
		for (int i = 0; i < k; ++i) {
			if (cjt.get(i).getList().contains(elem)) throw new Exception("L'element ja existeix al conjunt de clústers.");
		}
		if (noAssignats.contains(elem)) throw new Exception("L'element ja existeix al conjunt de clústers.");
		cjt.get(cl).add(elem);
	}
	
	/**
	 * Afegir un element com a no assignat
	 * @param elem element a afegir
	 * @throws Exception si l'element ja existeix en algun dels clústers o com a no assignat
	 */
	public void afegirElementNoAssig(int elem) throws Exception {
		for (int i = 0; i < k; ++i) {
			if (cjt.get(i).getList().contains(elem)) throw new Exception("L'element ja existeix al conjunt de clústers.");
		}
		if (noAssignats.contains(elem)) throw new Exception("L'element ja existeix al conjunt de clústers.");
		noAssignats.add(elem);
	}
	
	
	/**
	 * Actualitza els medoides de tots els clústers del conjunt
	 * @param M matriu de rellevància
	 * @throws Exception si hi ha elements al conjunt de clústers que no són posicions vàl·lides a la matriu M o si M no és una matriu quadrada
	 */
	public void updateMedoids(SparseMatrix M) throws Exception {
		if (M.getNRows() != M.getNCols()) throw new Exception("Matriu no quadrada");
		for (Cluster c : cjt) c.updateMedoid(M);
	}
	
	/**
	 * Retorna el clúster amb índex <tt>i</tt>
	 * @param i índex del clúster a consultar
	 * @return clúster amb l'índex indicat
	 * @throws IndexOutOfBoundsException si <tt>i</tt> està fora del rang (0 &lt; i &gt; k)
	 */
	public Cluster get(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i >= k) throw new IndexOutOfBoundsException("Número de clúster invàl·lid");
		return cjt.get(i);
	}
	
	/**
	 * Canvia l'element <tt>e</tt> del clúster <tt>cl1</tt> al clúster <tt>cl2</tt>
	 * @param e element a canviar
	 * @param cl1 clúster origen
	 * @param cl2 clúster destinació
	 * @throws Exception si el clúster <tt>cl1</tt> no conté <tt>e</tt>, si el clúster <tt>cl2</tt> ja conté <tt>e</tt> o si el números de clústers <tt>cl1</tt> i/o <tt>cl2</tt> no són vàl·lids
	 */
	public void changeCluster(int e, int cl1, int cl2) throws Exception {
		if (cl1 < 0 || cl1 >= k) throw new Exception("Número de clúster origen invàl·lid");
		else if (cl2 < 0 || cl2 >= k) throw new Exception("Número de clúster destinació invàl·lid");
		if (!cjt.get(cl1).getList().contains(e)) throw new Exception("El clúster origen no conté l'element");
		else if (cjt.get(cl2).getList().contains(e)) throw new Exception("El clúster destinació ja conté l'element");
		else {
			cjt.get(cl1).deleteElem(e);
			cjt.get(cl2).add(e);
		}
	}
	
	/**
	 * Assigna un clúster a l'element no assignat <tt>e</tt>
	 * @param e element a assignar
	 * @param cl número de clúster
	 * @throws Exception si el número de clúster no és vàl·lid, si l'element no està amb els no assignats o si el clúster <tt>cl</tt> ja conté l'element
	 */
	public void assignaCluster(Integer e, int cl) throws Exception {
		if (cl < 0 || cl >= k) throw new Exception("Número de clúster invàl·lid.");
		else if (!noAssignats.contains(e)) throw new Exception("L'element no està a la llista de no assignats.");
		else if (cjt.get(cl).getList().contains(e)) throw new Exception("El clúster ja conté l'element.");
		else {
			noAssignats.remove(e);
			cjt.get(cl).add(e);
		}
	}
	
	/**
	 * Retorna el conjunt d'elements no assignats a cap clúster
	 * @return conjunt d'elements no assignats
	 */
	public ArrayList<Integer> getNoAssig() {
		return noAssignats;
	}
	
	/**
	 * Retorna el clúster amb centroide més similar a <tt>e</tt>
	 * @param e element pel qual cal buscar clúster
	 * @param M matriu de rellevància
	 * @return índex del clúster amb centroide més similar a <tt>e</tt>
	 * @throws Exception si hi ha algun clúster sense medoide assignat, si la matriu M no és quadrada o si <tt>e</tt> no és una posició vàl·lida a la matriu M
	 */
	public int getClusterMesProper(int e, SparseMatrix M) throws Exception {
		if (M.getNRows() != M.getNCols()) throw new Exception("Matriu no quadrada");
		if (e < 0 || e >= M.getNRows()) throw new Exception("Posició d'entitat passada com a paràmetre no vàl·lida a la matriu");
				
		HashMap<Integer, Float> relevClusters = new HashMap<Integer, Float>();
		for (int j = 0; j < k; ++j) {
			int med = cjt.get(j).getMedoid();
			if (med < 0 || med >= M.getNRows()) throw new Exception ("Elements no vàl·lids al conjunt per a la matriu donada");
			Float relev = M.getValue(med, e);
			if (relev != 0) relevClusters.put(j, relev);
		} // Agafem la rellevància cada medoide amb 'e' (si en té)
				
		int clustProper;
		if (relevClusters.size() == 0) clustProper = -1; // No és rellevant amb cap medoide (no es podrà assignar)
		else {
			
			HashMap<Integer, Float> ordenat = Cluster.ordenaPerValor(relevClusters);
			// Ara tenim un map ordenat per la suma de similaritats (descendent)
			
			// Agafem els elements que tenen el valor màxim de la suma de rellevàncies
			Iterator<HashMap.Entry<Integer, Float>> it = ordenat.entrySet().iterator();
			Boolean changed = false;
			LinkedList<HashMap.Entry<Integer,Float>> candidats =
					new LinkedList<HashMap.Entry<Integer,Float>>(); // candidats a cluster
			HashMap.Entry<Integer, Float> first = it.next(); // 1r element amb rellevància màxima
			candidats.add(first);
			
			while (it.hasNext() && !changed) { // mentre quedin elements i no hagi canviat la rellevància
				HashMap.Entry<Integer, Float> elem = it.next();
				changed = (elem.getValue() != first.getValue());
				if (!changed) candidats.add(elem);
			}
			
			// En cas d'haver-hi més d'un candidat, el medoide es selecciona aleatòriament
			if (candidats.size() > 1) {
				Random r = new Random();
				int c = r.nextInt(candidats.size()); // núm. aleatòri que pertany a [0, núm. candidats)
				clustProper = candidats.get(c).getKey();
			}
			else clustProper = candidats.getFirst().getKey();
		}
		return clustProper;
	}
	
	/**
	 * Eliminar element d'un clúster o dels no assignats
	 * @param cl número de clústers (-1 correspon als no assignats)
	 * @param e element a eliminar
	 * @throws Exception si el núm. de clúster és invàl·lid o l'element no estava al clúster (o no assignats)
	 */
	public void eliminarElem(int cl, Integer e) throws Exception {
		if (cl < -1 || cl >= k) throw new Exception("Número de clúster invàl·lid");
		else if (cl == -1) {
			if (!noAssignats.contains(e)) throw new Exception("L'element no estava als no assignats");
			else {
				noAssignats.remove(e);
			}
		}
		else {
			cjt.get(cl).deleteElem(e);
		}
	}
	
	/**
	 * Retorna una matriu d'Entitats on a cada línia hi han les entitats corresponents a cada clúster del conjunt
	 * @param g graf de dades
	 * @param tipus tipus d'entitat (A, P, C o T)
	 * @return matriu d'Entitats corresponent al conjunt de clústers
	 * @throws Exception si el tipus d'entitat no és vàl·lid
	 */
	public ArrayList<ArrayList<Entitat>> getListEntitats(Graf g, char tipus) throws Exception {
		ArrayList<ArrayList<Entitat>> listEntitats = new ArrayList<ArrayList<Entitat>>();
		
		for (int i = 0; i < k; ++i) {
			listEntitats.add(getEntitatsFromPositions(g, cjt.get(i).getList(), tipus));
		}
		
		return listEntitats;
	}
	
	/**
	 * Retorna una llista d'Entitats corresponent a les entitats no assignades del conjunt de clústers
	 * @param g graf de dades
	 * @param tipus tipus d'entitat (A, P, C o T)
	 * @return llista d'Entitats corresponent a les no assignades
	 * @throws Exception si el tipus d'entitat no és vàl·lid
	 */
	public ArrayList<Entitat> getNoAssigEntitats(Graf g, char tipus) throws Exception {
		return getEntitatsFromPositions(g, noAssignats, tipus);
	}
	
	/**
	 * Retorna 
	 * @param g graf de les dades
	 * @param listPos llista de posicions en la matriu de rellevància (que corresponen a entitats)
	 * @param tipus tipus d'entitat (A, P, C o T)
	 * @return llista d'entitats del tipus <tt>T</tt> corresponents a les posicions de <tt>listPos</tt>
	 * @throws Exception si el tipus d'entitat no és vàl·lid
	 */
	public ArrayList<Entitat> getEntitatsFromPositions(Graf g, ArrayList<Integer> listPos, char tipus) throws Exception {
		if (tipus != 'A' && tipus != 'C' && tipus != 'P' && tipus != 'T') throw new Exception("Tipus d'entitat no vàl·lid");
		else {
			ArrayList<Entitat> listEntitats = new ArrayList<Entitat>();
			for (int pos : listPos) {
				int id;
				switch (tipus) {
					case 'A': id = g.getIdByPositionAutor(pos); break;
					case 'C': id = g.getIdByPositionConferencia(pos); break;
					case 'P': id = g.getIdByPositionPaper(pos); break;
					default: id = g.getIdByPositionTerme(pos); break;
				}
				Entitat e = g.consultarEntitat(id);
				listEntitats.add(e);
			}
			return listEntitats;
		}
	}
}
