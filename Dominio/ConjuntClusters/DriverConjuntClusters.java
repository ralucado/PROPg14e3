package Dominio;

import java.util.*;

public class DriverConjuntClusters {
	public static void main(String[] args) {
		//ArrayList<ArrayList<Double>> M = new ArrayList<ArrayList<Double>>();
		// Falta acabar updateMedoid() utilitzant la matriu resultant de la HeteSim!
		
		System.out.print("#######################################################\n"
						+ "DRIVER ConjuntClusters. Selecciona una de les següents proves:\n"
						+ " 1. Creadora\n"
						+ " 2. Consultar 'k'\n"
						+ " 3. Consultar clúster\n"
						+ " 4. Canviar element de clúster\n"
						+ " 5. Consultar elements no assignats\n"
						+ " 6. Consultar clúster més proper\n\n");
		
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		ConjuntClusters cc;
		switch (n) {
		case 1: //////////////////////////////////////////////////////////////////
			System.out.println("\nProva 1.1 - Creem un ConjuntClusters amb k vàl·lida (5):");
			try {
				cc = new ConjuntClusters(5);
			System.out.println("S'ha creat un conjunt de clústers amb k = " + cc.getK());
			}
			catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
			
			System.out.println("\nProva 1.2 - Creem un ConjuntClusters amb k no vàl·lida (0):");
			try {
				cc = new ConjuntClusters(0);
			System.out.println("S'ha creat un conjunt de clústers amb k = " + cc.getK());
			}
			catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
			break;
		
		case 2: //////////////////////////////////////////////////////////////////
			System.out.println("\nProva 2 - Tenim un conjunt de clústers amb k = 6:");
			try {
				cc = new ConjuntClusters(6);
				System.out.println("El conjunt de clústers té k = "+cc.getK());
			}
			catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
			break;
			
		case 3: //////////////////////////////////////////////////////////////////
			System.out.println("\nProva 3.1 - Consultem el clúster 2 (k = 4):");
			try {
				cc = new ConjuntClusters(4);
				cc.get(2).add(1); cc.get(2).add(3);
				System.out.println("Contingut clúster 2: "+ cc.get(2).getList());
			}
			catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
			
			System.out.println("\nProva 3.2 - Consultem el clúster 5 (k = 2):");
			try {
				cc = new ConjuntClusters(2);
				System.out.print("Contingut clúster 5: ");
				System.out.println(cc.get(5).getList());
			}
			catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
			break;
			
		case 4: //////////////////////////////////////////////////////////////////
			System.out.println("Prova 4.1 - Canviem element 10 de clúster 0 al 2:");
			try {
				cc = new ConjuntClusters(3);
				cc.get(0).add(10); cc.get(1).add(20); cc.get(2).add(30);
				System.out.println("Contingut inicial clúster 0: "+cc.get(0).getList());
				System.out.println("Contingut inicial clúster 1: "+cc.get(1).getList());
				System.out.println("Contingut inicial clúster 2: "+cc.get(2).getList());
				cc.changeCluster(10, 0, 2);
				System.out.println("Contingut final clúster 0: "+cc.get(0).getList());
				System.out.println("Contingut final clúster 1: "+cc.get(1).getList());
				System.out.println("Contingut final clúster 2: "+cc.get(2).getList());
			}
			catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
			
			System.out.println("\nProva 4.2 - Canviem un element d'un clúster on no hi és:");
			try {
				cc = new ConjuntClusters(3);
				cc.get(0).add(10); cc.get(1).add(20); cc.get(2).add(30);
				System.out.println("Contingut inicial clúster 0: "+cc.get(0).getList());
				System.out.println("Contingut inicial clúster 1: "+cc.get(1).getList());
				System.out.println("Contingut inicial clúster 2: "+cc.get(2).getList());
				System.out.println("Movem l'element 10 del clúster 1 al 2.");
				cc.changeCluster(10, 1, 2);
				System.out.println("Contingut final clúster 0: "+cc.get(0).getList());
				System.out.println("Contingut final clúster 1: "+cc.get(1).getList());
				System.out.println("Contingut final clúster 2: "+cc.get(2).getList());
			}
			catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
			
			break;
			
		case 5: //////////////////////////////////////////////////////////////////
			System.out.println("\nProva 5 - Consultem el conjunt d'elements no assignats:");
			try {
				cc = new ConjuntClusters(3);
				cc.getNoAssig().add(10); cc.getNoAssig().add(25);
				System.out.println("S'han afegit dos elements com a no assignats");
				System.out.println("Elem. no assignats: "+cc.getNoAssig());
			}
			catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
			
			break;
			
		case 6: //////////////////////////////////////////////////////////////////
			System.out.println("\nProva 6 - Consultem el clúster més similar per a l'element 3:");
			System.out.println("Matriu rellevància:");
			// Imprimir matriu rellev.
			try {
				cc = new ConjuntClusters(3);
				cc.get(0).addMedoid(1); cc.get(1).addMedoid(2); cc.get(2).addMedoid(0);
				for (int i = 0; i < cc.getK(); ++i) {
					System.out.println("Contingut clúster "+i+": "+cc.get(i).getList());
					System.out.println("Medoide clúster "+i+": "+cc.get(i).getMedoid());
				}
				//System.out.println("Clúster més similar a 3: " + cc.getClusterMesProper(3, M));
			}
			catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
			
		}
		s.close();
	}
}
