package Dominio;

import java.util.*;

/**
 * La classe <tt>QueryClustering</tt> representa una query sobre la que es vol aplicar clustering.
 * @author Arnau Blanch Cortès
 */
public class QueryClustering extends Query {
	private int k;
	private HeteSim hs;
	private ConjuntClusters cjt;
	
	/**
	 * Crea una nova <tt>QueryClustering</tt> amb el camí i la k passades com a paràmetres
	 * @param cami camí sobre el que fer la query
	 * @param k número de clústers
	 * @param hs referència a HeteSim
	 * @throws Exception si el camí no és vàl·lid per a clustering  o si la <tt>k</tt> no és vàl·lida
	 */
	public QueryClustering(Cami cami, int k, HeteSim hs) throws Exception {
		super();
		String c = cami.getPath();
		if (c.charAt(0) != c.charAt(c.length()-1)) throw new Exception("El camí ha de començar i acabar amb el mateix tipus d'entitat");
		// Controlo tipus d'inici i fi aquí? Cal constructor amb 'throws Exception'
		super.cami = cami;
		this.k = k;
		this.hs = hs;
		try {
			this.cjt = new ConjuntClusters(k);
		} catch(Exception e) {throw e;}
		
	}
	
	/**
	 * Retorna la <tt>k</tt> (número de clústers que es volen)
	 * @return número de clústers
	 */
	public int getK() {
		return this.k;
	}
	
	/**
	 * Retorna el resultat de la query, és a dir, el conjunt de clústers resultant, que representa grups d'entitats similars entre elles respecte el camí indicat 
	 * @return conjunt clústers (resultat)
	 */ // CONTROLAR SI LA QUERY ENCARA NO S'HA EXECUTAT
	public ConjuntClusters resultat() {
		return cjt;
	}
	
	/**
	 * Executa la query i guarda el conjunt de clústers resultant
	 */
	public void executa() {
		ArrayList<ArrayList<Double>> M = hs.calcula(cami.getPath());
		
		Random r = new Random();
		ArrayList<Integer> assignats = new ArrayList<Integer>();
		
		for (int i = 0; i < k; ++i) {
			int rand = r.nextInt(M.size());
			while (assignats.contains(rand)) { // si "rand" ja s'ha assignat, torna a generar "rand"
				rand = r.nextInt(M.size());
			}
			assignats.add(rand);	// Marquem "rand" com a assignat
			try {
				cjt.get(i).addMedoid(rand);	// Assignem com a medoide del clúster "i" l'element triat aleatòriament
			} catch (Exception e) {} // ?????¿?¿?¿?
		}
		for (int j = 0; j < M.size(); ++j) {
			if (!assignats.contains(j)) {
				try {
					int clProper = cjt.getClusterMesProper(j, M);
					if (clProper == -1) cjt.getNoAssig().add(j);
					else cjt.get(clProper).add(j);
				} catch (Exception e) {}
			}
		}
		Boolean changed = true;
		while (changed) {
			cjt.updateMedoids(M); // Recalculem els medoides de cada clúster
			changed = false;
			
			// Intentem moure els elements del conjunt al clúster que tingui més proper
			for (int i = 0; i < k; ++i) {
				for (int j = 0; j < k; ++j) { // Si esborrem element, podem saltar-ne algun
					try {
						int elem = cjt.get(i).get(j);
						int c = cjt.getClusterMesProper(elem, M);
						if (c == -1) { // l'eliminem i el guardem amb els no assignats
							cjt.get(i).deleteElem(elem);
							cjt.getNoAssig().add(elem);
							--j; // Al eliminar un element, cal tirar enrere l'índex (tot es mou enrere)
						}
						else if (c != i) { // el canviem del clúster 'i' al 'c'
							changed = true;
							cjt.changeCluster(elem, i, c);
							--j; // Al eliminar un element, cal tirar enrere l'índex (tot es mou enrere)
						}
					}
					catch (Exception e) {}
				}
			}
			
			// Ara fem el mateix pels no assignats
			for (int j = 0; j < cjt.getNoAssig().size(); ++j) { // Si esborrem element, podem saltar-ne algun
				try {
					int elem = cjt.getNoAssig().get(j);
					int c = cjt.getClusterMesProper(elem, M);
					if (c != -1) { // assignem 'elem' al clúster 'c'
						changed = true;
						cjt.getNoAssig().remove(j);  // Comprovar si índex (int) o element (Integer)
						cjt.get(c).add(elem);
						--j; // Al eliminar un element, cal tirar enrere l'índex (tot es mou enrere)
					}
				}
				catch (Exception e) {}
			}
		}
		// Ja ha convergit
	
	}
}
