package TestQueryClustering;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverQueryClustering {
	public static void main(String[] args) {
		
		System.out.print("#######################################################\n"
						+ "DRIVER QueryClustering. Selecciona una de les següents proves:\n"
						+ " 1. Consultar número de clústers\n"
						+ " 2. Consultar clústers resultants de la query i no assignats\n\n");
		
		Scanner s = new Scanner(System.in);
		try {
			System.out.println("Escriu número de clústers que es vol obtenir:");
			int k = s.nextInt();
			System.out.println("Utilitzarem el camí APA per a la prova (en cas de ser un camí incorrecte o amb inici i final de diferent tipus d'entitat tindríem excepció!).");
			Cami c = new Cami("cami1", "APA", "descripcio1");
			Graf g = new Graf();
			HeteSim hs = new HeteSim(g);
			QueryClustering qc = new QueryClustering(c, k, hs);
			
			System.out.println("Escriu acció:");
			switch (s.nextInt()) {
			case 1:
				System.out.println("Número de clústers: "+qc.getK());
				break;
				
			case 2:
				System.out.println("Hem generat un graf de prova. Conté 7 autors:");
				for (int i = 0; i < 7; ++i) {
					Entitat a = new Autor("autor"+i);
					g.afegirEntitat(a);
					System.out.println("["+a.getId()+", "+a.getNom()+"]");
				}
				
				System.out.println("\nLa matriu resultant 7x7 de HeteSim és:");
				
				ArrayList<ArrayList<Entitat>> resultat = qc.getResultatClusters(g);
				System.out.println("\nResultat k-Medoids:");
				
				for (int i = 0; i < resultat.size(); ++i) {
					System.out.println("Clúster "+i+":");
					for (Entitat elem : resultat.get(i)) System.out.println("Id: "+elem.getId()+", Nom: "+elem.getNom());
				}
				
				ArrayList<Entitat> noAssig = qc.getNoAssig(g);
				System.out.println("\nEntitats no assignades:");
				for (Entitat elem : noAssig) {
					System.out.println("Id: "+elem.getId()+", Nom: "+elem.getNom());
				}
				break;
			}
		}
		catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
	}

}
