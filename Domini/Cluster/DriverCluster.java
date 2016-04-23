package Domini;

import java.util.*;

public class DriverCluster {
	public static void main(String[] args) {
		ArrayList<ArrayList<Double>> M = new ArrayList<ArrayList<Double>>();
		// Falta acabar updateMedoid() utilitzant la matriu resultant de la HeteSim!
		
		System.out.print("#######################################################\n"
						+ "DRIVER Cluster. Selecciona una de les següents proves:\n"
						+ " 1. Creadora\n"
						+ " 2. Consultar llista elements\n"
						+ " 3. Consultar número d'elements del cluster\n"
						+ " 4. Afegeix un element al cluster\n"
						+ " 5. Eliminar element\n"
						+ " 6. Eliminar element per índex\n"
						+ " 7. Consulta element\n"
						+ " 8. Consulta medoide\n"
						+ " 9. Afegeix medoide\n"
						+ " 10. Actualitza medoide\n"
						+ " 11. Ordena map en descendentment per valor\n\n");
		Scanner s = new Scanner(System.in);
		Cluster cl = null;
		int n = s.nextInt();
		switch (n) {
			case 1: //////////////////////////////////////////////////////////////////
				System.out.println("Prova 1:");
				cl = new Cluster();
				System.out.println("S'ha creat un clúster satisfactòriament");
				break;
				
				
			case 2: //////////////////////////////////////////////////////////////////
				System.out.println("Prova 2: Tenim un clúster amb 5 elements.");
				cl = new Cluster();
				try {
					cl.add(10); cl.add(2); cl.add(5); cl.add(20);
					ArrayList<Integer> llista = cl.getList();
					System.out.println("Hem obtingut la llista d'elements, la mostrem: "+llista);
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				break;
				
				
			case 3: //////////////////////////////////////////////////////////////////
				System.out.println("Prova 3.1 - Tenim un clúster amb 4 elements:");
				cl = new Cluster();
				try {
					cl.add(10); cl.add(2); cl.add(5); cl.add(20);
					System.out.println("El clúster té mida: "+cl.size());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				
				System.out.println("\nProva 3.2 - Tenim un clúster buit:");
				cl = new Cluster();
				System.out.println("El clúster té mida: "+cl.size());
				break;
				
				
			case 4: //////////////////////////////////////////////////////////////////
				System.out.println("Prova 4.1 - Afegim un element vàl·lid que no estava contingut al clúster:");
				cl = new Cluster();
				try {
					cl.add(11);
					System.out.println("S'ha afegit correctament.");
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				
				System.out.println("\nProva 4.2 - Afegim un element vàl·lid que ja estava contingut al clúster:");
				cl = new Cluster();
				try {
					cl.add(10); cl.add(13); cl.add(13);
					System.out.println("S'ha afegit correctament.");
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				
				System.out.println("\nProva 4.3 - Afegim un element no vàl·lid (negatiu):");
				cl = new Cluster();
				try {
					cl.add(-1);
					System.out.println("S'ha afegit correctament.");
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				break;
				
				
			case 5: //////////////////////////////////////////////////////////////////
				System.out.println("Prova 5.1 - Eliminem un element (20) que estava al clúster:");
				cl = new Cluster();
				try {
					cl.addMedoid(10); cl.add(20); cl.add(30);
					cl.deleteElem(20);
					System.out.println("S'ha eliminat correctament.");
					System.out.println("Contingut clúster: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				
				System.out.println("\nProva 5.2 - Eliminem un element (20) que estava al clúster i era el medoide:");
				cl = new Cluster();
				try {
					cl.addMedoid(20);
					cl.add(10); cl.add(30);
					cl.deleteElem(20);
					System.out.println("S'ha eliminat correctament.");
					System.out.println("Contingut clúster: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());
				}
				
				System.out.println("\nProva 5.3 - Eliminem un element (50) que no estava al clúster:");
				cl = new Cluster();
				try {
					cl.addMedoid(20);
					cl.add(10); cl.add(30);
					cl.deleteElem(50);
					System.out.println("S'ha eliminat correctament.");
					System.out.println("Contingut clúster: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());
				}
				break;
				
				
			case 6: //////////////////////////////////////////////////////////////////
				System.out.println("Prova 6.1 - Eliminem l'element a la 2a posició (índex = 1) que estava al clúster:");
				cl = new Cluster();
				try {
					cl.addMedoid(10); cl.add(20); cl.add(30);
					System.out.println("Contingut inicial: "+cl.getList());
					cl.deleteIndex(1);
					System.out.println("S'ha eliminat correctament.");
					System.out.println("Contingut clúster: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				
				System.out.println("\nProva 6.2 - Eliminem un element a la 2a posició (índex = 1) que estava al clúster i era el medoide:");
				cl = new Cluster();
				try {
					cl.add(10); cl.addMedoid(20); cl.add(30);
					System.out.println("Contingut inicial: "+cl.getList());
					cl.deleteIndex(1);
					System.out.println("S'ha eliminat correctament.");
					System.out.println("Contingut clúster: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());
				}
				
				System.out.println("\nProva 6.3 - Eliminem un element amb índex invàl·lid (5):");
				cl = new Cluster();
				try {
					cl.add(10); cl.addMedoid(20); cl.add(30);
					System.out.println("Contingut inicial: "+cl.getList());
					cl.deleteIndex(5);
					System.out.println("S'ha eliminat correctament.");
					System.out.println("Contingut clúster: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());
				}
				break;
				
			case 7: //////////////////////////////////////////////////////////////////
				System.out.println("\nProva 7.1 - Consultem un element en un índex vàl·lid:");
				cl = new Cluster();
				try {
					cl.addMedoid(10); cl.add(20); cl.add(40); cl.add(50);
					System.out.println("Contingut inicial: "+cl.getList());
					System.out.print("Element amb índex = 2: ");
					System.out.println(cl.get(2));
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				
				System.out.println("\nProva 7.2 - Consultem un element en un índex invàl·lid:");
				cl = new Cluster();
				try {
					cl.addMedoid(10); cl.add(20); cl.add(40); cl.add(50);
					System.out.println("Contingut inicial: "+cl.getList());
					System.out.print("Element amb índex = 6: ");
					System.out.println(cl.get(6));
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				break;
				
			case 8: //////////////////////////////////////////////////////////////////
				System.out.println("\nProva 8.1 - Consultem medoide en un clúster amb medoide assignat:");
				cl = new Cluster();
				try {
					cl.add(10); cl.addMedoid(20); cl.add(30); cl.add(40);
					System.out.println("Contingut: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				
				System.out.println("\nProva 8.2 - Consultem medoide en un clúster amb sense medoide assignat:");
				cl = new Cluster();
				try {
					cl.add(10);
					System.out.println("Contingut: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				
				System.out.println("\nProva 8.3 - Consultem medoide en un clúster buit:");
				cl = new Cluster();
				try {
					System.out.println("Contingut: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				break;
				
			case 9: //////////////////////////////////////////////////////////////////
				System.out.println("\nProva 9.1 - Afegim medoide no existent al clúster:");
				cl = new Cluster();
				try {
					System.out.println("Contingut inicial: "+cl.getList());
					cl.addMedoid(30);
					System.out.println("Contingut final: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				
				System.out.println("\nProva 9.2 - Afegim medoide ja existent (20) al clúster:");
				cl = new Cluster();
				try {
					cl.add(10); cl.addMedoid(20);
					System.out.println("Contingut inicial: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
					cl.addMedoid(20);
					System.out.println("Contingut final: "+cl.getList());
					System.out.print("Medoide clúster: ");
					System.out.println(cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				break;
			
			case 10: //////////////////////////////////////////////////////////////////
				System.out.println("\nProva 10.1 - Actualitzem medoide d'un clúster buit:");
				cl = new Cluster();
				try {
					System.out.println("Contingut inicial: "+cl.getList());
					cl.updateMedoid(M);
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				
				System.out.println("\nProva 10.2 - Actualitzem medoide d'un clúster correcte:");
				cl = new Cluster();
				try {
					cl.add(1); cl.add(2); cl.addMedoid(3);
					System.out.println("Contingut inicial: "+cl.getList());
					System.out.println("Medoide clúster: "+cl.getMedoid());
					cl.updateMedoid(M);
					System.out.println("Contingut final: "+cl.getList());
					System.out.println("Medoide clúster: "+cl.getMedoid());
				}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				break;
				
			case 11:
				System.out.println("\nProva 11 - Ordenem map descedentment per valor");
				Map<Integer,Double> map = new HashMap<Integer, Double>();
				map.put(1, 0.23);
				map.put(5, 0.88);
				map.put(4, 0.23);
				map.put(3, 0.50);
				map.put(10, 0.818);
				
				System.out.println("Contingut inicial: " + map);
				Map<Integer,Double> ordMap = Cluster.ordenaPerValor(map);
				System.out.println("Contingut ordenat: " + ordMap);
				break;
		}
		
		s.close();
	}
}
