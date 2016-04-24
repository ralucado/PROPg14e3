package TestCluster;

import java.util.HashMap;
import java.util.Scanner;

public class DriverCluster {
	public static void main(String[] args) {
		
		System.out.print("#######################################################\n"
						+ "DRIVER Cluster. Selecciona una de les següents proves:\n"
						+ " 1. Afegir elements (no medoides)\n"
						+ " 2. Afegir medoide\n"
						+ " 3. Consultar llista d'elements\n"
						+ " 4. Eliminar elements\n"
						+ " 5. Eliminar elements per índex\n"
						+ " 6. Consultar element per índex\n"
						+ " 7. Consultar medoide\n"
						+ " 8. Assigna medoide\n"
						+ " 9. Consultar número d'elements del cluster\n"
						+ " 10. Actualitza medoide\n"
						+ " 11. Ordena HashMap<Integer,Float> descendentment per valor\n\n");
		Scanner s = new Scanner(System.in);
		Cluster cl = new Cluster();
		System.out.println("Escriu acció:");
		while (s.hasNextInt()) {
			int n = s.nextInt();
			switch (n) {
			case 1:
				System.out.println("Escriu elements (no medoides) a afegir al clúster (posa un punt per acabar).");
				while (s.hasNextInt()) {
					try {cl.add(s.nextInt());}
					catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				}
				s.next();
				break;
			
			case 2:
				System.out.println("Escriu medoide a afegir al clúster:");
				int medoide = s.nextInt();
				try {cl.addMedoid(medoide);}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				break;
			
			case 3:
				System.out.println("Mostrem llista d'elements:\n" + cl.getList());
				break;
				
			case 4:
				System.out.println("Escriu elements a eliminar del clúster (posa un punt per acabar).");
				while (s.hasNextInt()) {
					try {cl.deleteElem(s.nextInt());}
					catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				}
				s.next();
				break;
				
			case 5:
				System.out.println("Escriu índexs dels elements a eliminar del clúster (posa un punt per acabar).");
				while (s.hasNextInt()) {
					try {cl.deleteIndex(s.nextInt());}
					catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				}
				s.next();
				break;
			
			case 6:
				System.out.println("Escriu índex de l'element a consultar:");
				try {cl.get(s.nextInt());}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				break;
				
			case 7:
				try {System.out.println("Medoide del clúster: "+cl.getMedoid());}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				break;
				
			case 8:
				System.out.println("Escriu element (existent) a assignar com a medoide:");
				try {cl.setMedoid(s.nextInt());}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				break;
				
			case 9:
				System.out.println("Núm. elements del clúster: "+cl.size());
				break;
				
			case 10:
				System.out.println("Escriu mida de la matriu quadrada de rellevància:");
				int num = s.nextInt();
				if (num <= 0) {System.out.println("Mida no vàl·lida"); break;}
				SparseMatrix mat = new SparseMatrix(num, num);
				for (int i = 0; i < num; ++i) {
					for (int j = 0; j < num; ++j) mat.set(i, j, s.nextFloat());
				}
				System.out.println("Matriu llegida\n");
				System.out.println(mat);
				
				try{cl.updateMedoid(mat);}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				System.out.println("Hem actualitzat el medoide del clúster.");
				try {System.out.println("Nou medoide:"+cl.getMedoid());}
				catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
				break;
				
			case 12:
				System.out.println("Escriu elements del HashMap<Integer,Float>  (float amb coma; posa un punt per acabar):");
				HashMap<Integer, Float> testmap = new HashMap<Integer,Float>();
				while (s.hasNextInt()) {
					Integer i = s.nextInt();
					Float f = s.nextFloat();
					testmap.put(i, f);
				}
				System.out.println("Map llegit: "+testmap);
				
				HashMap<Integer, Float> mapOrd = Cluster.ordenaPerValor(testmap);
				System.out.println("Map ordenat per valor: "+mapOrd);
				break;
			}
			System.out.println("\nEscriu acció:");
		}
		s.close();
	}
}
