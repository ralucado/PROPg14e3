package domini.queries;

import java.util.ArrayList;
import java.util.Random;

import domini.camins.Cami;
import domini.graf.Entitat;
import domini.graf.Graf;

/**
 * La classe <tt>QueryClustering</tt> representa una query sobre la que es vol aplicar clustering.
 * @author Arnau Blanch Cortès
 */
public class QueryClustering extends Query {
	private int k;
	private HeteSim hs;
	private ConjuntClusters cjt;
	private boolean executat;
	
	/**
	 * Crea una nova <tt>QueryClustering</tt> amb el camí i la k passades com a paràmetres
	 * @param cami camí sobre el que fer la query
	 * @param k número de clústers
	 * @param hs referència a HeteSim
	 * @throws Exception si el camí no és vàl·lid per a clustering  o si la <tt>k</tt> no és vàl·lida
	 */
	public QueryClustering(Cami cami, int k, HeteSim hs) throws Exception {
		super();
		executat = false;
		String c = cami.getPath();
		if (c.charAt(0) != c.charAt(c.length()-1)) throw new Exception("El camí ha de començar i acabar amb el mateix tipus d'entitat");
		super.cami = cami;
		this.k = k;
		this.hs = hs;
		this.cjt = new ConjuntClusters(k);		
	}
	
	/**
	 * Retorna la <tt>k</tt> (número de clústers que es volen)
	 * @return número de clústers
	 */
	public int getK() {
		return this.k;
	}
	
	/**
	 * Retorna una matriu d'entitats corresponent al conjunt de clústers resultant de la query de clustering
	 * @param g graf de dades
	 * @return matriu d'entitats resultant
	 * @throws Exception pot retornar les excepcions de clúster i conjunt de clústers
	 */
	public ArrayList<ArrayList<Entitat>> getResultatClusters(Graf g) throws Exception {
		if (!executat) executaKMedoids();
		
		return cjt.getListEntitats(g, cami.getPath().charAt(0));
	}
	
	/**
	 * Retorna la llista d'entitats no assignades a cap clúster resultant de la query de clustering
	 * @param g graf de dades
	 * @return llista d'entitats no assinades resultant
	 * @throws Exception pot retornar les excepcions de clúster i conjunt de clústers
	 */
	public ArrayList<Entitat> getNoAssig(Graf g) throws Exception {
		if (!executat) executaKMedoids();
		
		return cjt.getNoAssigEntitats(g, cami.getPath().charAt(0));
	}
	
	/**
	 * Executa la query i guarda el conjunt de clústers resultant
	 * @throws pot retornar les excepcions de clúster i conjunt de clústers i si la k > núm. entitats de la matriu resultant de HeteSim
	 */
	private void executaKMedoids() throws Exception {
		System.out.println("Executant k-Medoids (demanem rellevància)");
		SparseMatrix M = hs.getHeteSim(cami);
		System.out.println("Ja tenim rellevàncies!");
		if (k > M.getNRows()) throw new Exception("k > núm. d'entitats de la matriu");
		Random r = new Random();
		ArrayList<Integer> assignats = new ArrayList<Integer>();
		
		for (int i = 0; i < k; ++i) {
			int rand = r.nextInt(M.getNRows());
			while (assignats.contains(rand)) { // si "rand" ja s'ha assignat, torna a generar "rand"
				rand = r.nextInt(M.getNRows());
			}
			assignats.add(rand);	// Marquem "rand" com a assignat
			cjt.get(i).addMedoid(rand);	// Assignem com a medoide del clúster "i" l'element triat aleatòriament
			//System.out.println("Medoide inicial clúster "+i+" = "+rand);
		}
		
		
		for (int j = 0; j < M.getNRows(); ++j) {
			if (!assignats.contains(j)) {
				int clProper = cjt.getClusterMesProper(j, M);
				if (clProper == -1) cjt.getNoAssig().add(j);
				
				else cjt.get(clProper).add(j);
//				System.out.println("Clúster més proper a "+j+" = "+clProper);
//				if (clProper != -1) System.out.println("Medoide clúster "+clProper+" = "+cjt.get(clProper).getMedoid());
			}
		}
				
		Boolean changed = true;
		while (changed) {
			//System.out.println("No convergeix!!!!");
			cjt.updateMedoids(M); // Recalculem els medoides de cada clúster
			/*for (int i = 0; i < cjt.getK(); ++i) {
				System.out.println("Clúster "+i+": "+cjt.get(i).getList());
				System.out.println("Medoide = "+cjt.get(i).getMedoid());
				
			}*/

			changed = false;
			// Intentem moure els elements del conjunt al clúster que tingui més proper
			for (int i = 0; i < k; ++i) {
				for (int j = 0; j < cjt.get(i).size(); ++j) { // Si esborrem element, podem saltar-ne algun
					int elem = cjt.get(i).get(j);
					int c = cjt.getClusterMesProper(elem, M);
					//System.out.println("Clúster més proper a "+elem+" = "+c);
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
			}			
			// Ara fem el mateix pels no assignats
			for (int j = 0; j < cjt.getNoAssig().size(); ++j) { // Si esborrem element, podem saltar-ne algun
				int elem = cjt.getNoAssig().get(j);
				int c = cjt.getClusterMesProper(elem, M);
				if (c != -1) { // assignem 'elem' al clúster 'c'
					changed = true;
					cjt.getNoAssig().remove(j);  // Comprovar si índex (int) o element (Integer)
					cjt.get(c).add(elem);
					--j; // Al eliminar un element, cal tirar enrere l'índex (tot es mou enrere)
				}
			}
		}
		// Ja ha convergit
		executat = true;
	}
	
	/**
	 * Retorna si la query és de clústering
	 */
	public boolean esClustering() {
		return true;
	}

	/**
	 * Retorna si la query és normal
	 */
	public boolean esNormal() {
		return false;
	}
}
