package Domini;

import java.util.*;

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
	 * Actualitza els medoides de tots els clústers del conjunt
	 * @param M matriu de rellevància
	 */
	public void updateMedoids(ArrayList<ArrayList<Double>> M) {
		for (Cluster c : cjt) c.updateMedoid(M);
	}
	
	/**
	 * Retorna el clúster amb índex <tt>i</tt>
	 * @param i índex del clúster a consultar
	 * @return clúster amb l'índex indicat
	 * @throws IndexOutOfBoundsException si <tt>i</tt> està fora del rang (0 &lt; i &gt; k)
	 */
	public Cluster get(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i >= k) throw new IndexOutOfBoundsException("'i' fora de rang");
		return cjt.get(i);
	}
	
	/**
	 * Canvia l'element <tt>e</tt> del clúster <tt>cl1</tt> al clúster <tt>cl2</tt>
	 * @param e element a canviar
	 * @param cl1 clúster origen
	 * @param cl2 clúster destinació
	 * @throws Exception si el clúster <tt>cl1</tt> no conté <tt>e</tt> 
	 */
	public void changeCluster(int e, int cl1, int cl2) throws Exception {
		if (!cjt.get(cl1).getList().contains(e)) throw new Exception("El clúster 'cl1' no conté 'e'");
		//else if (cjt.get(cl2).getList().contains(e)) throw new Exception("El clúster 'cl2' ja conté 'e'");
		else {
			cjt.get(cl1).deleteElem(e);
			cjt.get(cl2).add(e);
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
	 * @throws Exception si hi ha algun clúster sense medoide assignat
	 */
	public int getClusterMesProper(int e, ArrayList<ArrayList<Double>> M) throws Exception {
		HashMap<Integer, Double> relevClusters = new HashMap<Integer, Double>();
		int clustProper;
		for (int j = 0; j < k; ++j) {
			int med = cjt.get(j).getMedoid();
			double relev = M.get(med).get(e);
			if (relev != 0) relevClusters.put(j,relev);
		}
		
		if (relevClusters.size() == 0) clustProper = -1;
		else {
			Map<Integer,Double> ordenat = Cluster.ordenaPerValor(relevClusters);
			// Ara tenim un map ordenat per la suma de similaritats (descendent)
			
			// Agafem els elements que tenen el valor màxim de la suma de rellevàncies
			Iterator<Map.Entry<Integer,Double>> it = ordenat.entrySet().iterator();
			Boolean changed = false;
			LinkedList<Map.Entry<Integer,Double>> candidats =
					new LinkedList<Map.Entry<Integer,Double>>(); // candidats a cluster
			Map.Entry<Integer, Double> first = it.next(); // 1r element amb rellevància màxima
			candidats.add(first);
			
			while (it.hasNext() && !changed) { // mentre quedin elements i no hagi canviat la rellevància
				Map.Entry<Integer, Double> elem = it.next();
				changed = (elem.getValue() != first.getValue());
				if (!changed) candidats.add(elem);
			}
			
			// En cas d'haver-hi més d'un candidat, el medoide es selecciona aleatòriament
			if (candidats.size() > 1) {
				Random r = new Random();
				int c = r.nextInt(candidats.size());
				clustProper = candidats.get(c).getKey();
			}
			else clustProper = candidats.getFirst().getKey();
		}
		
		return clustProper;
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
		if (tipus != 'A' || tipus != 'C' || tipus != 'P' || tipus != 'T') throw new Exception("Tipus d'entitat no vàl·lid");
		else {
			ArrayList<Entitat> listEntitats = new ArrayList<Entitat>();
			
			for (int pos : listPos) {
				int id;
				switch (tipus) {
					case 'A': id = g.getIdByPositionAutor(pos);
					case 'C': id = g.getIdByPositionConferencia(pos);
					case 'P': id = g.getIdByPositionPaper(pos);
					default: id = g.getIdByPositionTerme(pos);
				}
				Entitat e = g.consultarEntitat(id);
				listEntitats.add(e);
			}

			return listEntitats;
		}
	}
}
