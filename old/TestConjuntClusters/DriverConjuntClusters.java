package TestConjuntClusters;

import java.util.*;

public class DriverConjuntClusters {
	public static void main(String[] args) {
		//ArrayList<ArrayList<Double>> M = new ArrayList<ArrayList<Double>>();
		// Falta acabar updateMedoid() utilitzant la matriu resultant de la HeteSim!
		
		System.out.print("#######################################################\n"
						+ "DRIVER ConjuntClusters. Selecciona una de les següents proves:\n"
						+ " 1. Consultar número de clústers\n"
						+ " 2. Afegir elements a un clúster\n"
						+ " 3. Afegir elements com a no assignats\n"
						+ " 4. Consultar clúster\n"
						+ " 5. Consultar elements no assignats\n"
						+ " 6. Canviar element de clúster\n"
						+ " 7. Assignar clúster a element no assignat\n"
						+ " 8. Consultar clúster més proper\n"
						+ " 9. Consulta entitats de tots els clústers del conjunt\n"
						+ " 10. Consulta llista d'entitats no assignades del conjunt\n"
						+ " 11. Eliminar elements d'un clúster o no assignats\n\n");
		
		Scanner s = new Scanner(System.in);
		try {
			System.out.println("Introdueix número de clústers que ha de tenir el conjunt:");
			ConjuntClusters cc = new ConjuntClusters(s.nextInt());
			System.out.println("S'ha creat correctament.\n\nEscriu acció:");
			
			while (s.hasNextInt()) {
				int n = s.nextInt();
				switch (n) {
				case 1:
					System.out.println("Número de clústers del conjunt: "+cc.getK());
					break;
					
				case 2:
					System.out.println("Escriu número de clúster on vols afegir elements:");
					int numC = s.nextInt();
					System.out.println("Escriu elements a afegir al clúster "+numC+" (posa un punt per acabar):");
					while (s.hasNextInt()) {
						try {cc.afegirElement(numC, s.nextInt());}
						catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
					}
					s.next();
					break;
				
				case 3:
					System.out.println("Escriu elements a afegir com a no assignats (posa un punt per acabar):");
					while (s.hasNextInt()) {
						try {cc.afegirElementNoAssig(s.nextInt());}
						catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
					}
					s.next();
					break;
				
				case 4:
					System.out.println("Escriu número de clúster que vols consultar:");
					int numCl = s.nextInt();
					try {System.out.println("Clúster "+numCl+": "+cc.get(numCl).getList());}
					catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
					break;
				
				case 5:
					System.out.println("No assignats: "+cc.getNoAssig());
					break;
					
				case 6:
					System.out.println("Escriu clúster origen:");
					int cl1 = s.nextInt();
					System.out.println("Escriu clúster destinació:");
					int cl2 = s.nextInt();
					System.out.println("Escriu element a canviar de clúster:");
					try {cc.changeCluster(s.nextInt(), cl1, cl2);}
					catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
					break;
				
				case 7:
					System.out.println("Escriu clúster destinació:");
					int clDest = s.nextInt();
					System.out.println("Escriu element a assignar al clúster "+clDest+":");
					try {cc.assignaCluster(s.nextInt(), clDest);}
					catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
					break;
					
				case 8:
					System.out.println("Escriu mida de la matriu quadrada de rellevància:");
					int num = s.nextInt();
					if (num <= 0) {System.out.println("Mida no vàl·lida"); break;}
					SparseMatrix mat = new SparseMatrix(num, num);
					for (int i = 0; i < num; ++i) {
						for (int j = 0; j < num; ++j) mat.set(i, j, s.nextFloat());
					}
					System.out.println("Matriu llegida\n");
					System.out.println(mat);
					
					System.out.println("Escriu l'element del qual vols saber el clúster més proper:");
					int el = s.nextInt();
					try {System.out.println("Clúster més proper a "+el+": "+cc.getClusterMesProper(el, mat));}
					catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
					break;
					
				case 9:
					System.out.println("Escriu entitats a afegir al graf (un per línia en el format 'id nom tipus', tipus és A, C, P o T):");
					Graf g = new Graf();
					while (s.hasNextInt()) {
						Integer id = s.nextInt();
						String nom = s.next();
						String t = s.next();
						Entitat e;
						switch (t) {
							case "A": e = new Autor(id, nom);
							case "C": e = new Conferencia(id, nom);
							case "P": e = new Paper(id, nom);
							default:  e = new Terme(id, nom); 
						}
						try {g.afegirEntitat(e);}
						catch (Exception ex) {System.out.println("EXCEPCIÓ: "+ex.getMessage());}
						
					}
					s.next();

					System.out.println("Escriu tipus d'entitat del conjunt:");
					String tipus = s.next();
					char tip = tipus.charAt(0);					
					
					try{
						ArrayList<ArrayList<Entitat>> entitats = cc.getListEntitats(g, tip);
						for (int i = 0; i < entitats.size(); ++i) {
							System.out.println("Clúster "+i+":");
							for (Entitat ent : entitats.get(i)) {
								System.out.println("Id: " + ent.getId() + " - Nom: "+ ent.getNom());
							}
						}
					}
					catch (Exception ex) {System.out.println("EXCEPCIÓ: "+ex.getMessage());}
					break;
					
				case 10:
					System.out.println("Escriu entitats a afegir al graf (un per línia en el format 'id nom tipus', tipus és A, C, P o T):");
					Graf g2 = new Graf();
					while (s.hasNextInt()) {
						Integer id = s.nextInt();
						String nom = s.next();
						String t = s.next();
						Entitat e;
						switch (t) {
							case "A": e = new Autor(id, nom);
							case "C": e = new Conferencia(id, nom);
							case "P": e = new Paper(id, nom);
							default:  e = new Terme(id, nom); 
						}
						try {g2.afegirEntitat(e);}
						catch (Exception ex) {System.out.println("EXCEPCIÓ: "+ex.getMessage());}
						
					}
					s.next();

					System.out.println("Escriu tipus d'entitat del conjunt:");
					String tipus2 = s.next();
					char tip2 = tipus2.charAt(0);					
					
					try{
						System.out.println(g2.getPosicioAIdAutor());
						ArrayList<Entitat> entitats = cc.getNoAssigEntitats(g2, tip2);
						System.out.println("Entitats no assignades: ");
						for (Entitat ent : entitats) {
							System.out.println("Id: " + ent.getId() + "Nom: "+ ent.getNom());
						}
					}
					catch (Exception ex) {System.out.println("EXCEPCIÓ: "+ex.getMessage());}
					break;
				
				case 11:
					System.out.println("Escriu número de clúster de l'element a eliminar (posa -1 per als no assignats):");
					int clD = s.nextInt();
					System.out.println("Escriu element a eliminar:");
					try {cc.eliminarElem(clD, s.nextInt());}
					catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
					break;
				}
				
				System.out.println("\nEscriu acció");
			}
		}
		catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
		
		s.close();
		
	}
}
