package ProvaCQ;

import java.util.ArrayList;
import java.util.Scanner;

import Dominio.Pair;

public class DriverCQ {
	public static void main(String[] args) {
		
		try{
		ControladorGraf cg = new ControladorGraf();
		cg.altaAutor("a1", "DATABASE");
		cg.altaAutor("a2", "DATABASE");
		cg.altaAutor("a3", "DATABASE");
		cg.altaConferencia("c1", "DATABASE");
		cg.altaConferencia("c2", "DATABASE");
		cg.altaConferencia("c3", "DATABASE");
		cg.altaTerme("t1");
		cg.altaTerme("t2");
		cg.altaTerme("t3");
		cg.altaPaper("p1", "DATABASE");
		cg.altaPaper("p2", "DATABASE");
		cg.altaPaper("p3", "DATABASE");
		cg.afegirRelacioAP("a1", "p1");
		cg.afegirRelacioAP("a1", "p3");
		cg.afegirRelacioAP("a2", "p3");
		cg.afegirRelacioAP("a3", "p2");
		cg.afegirRelacioCP("c1", "p1");
		cg.afegirRelacioCP("c2", "p2");
		cg.afegirRelacioCP("c2", "p3");
		cg.afegirRelacioCP("c3", "p2");
		cg.afegirRelacioTP("t1", "p2");
		cg.afegirRelacioTP("t2", "p1");
		cg.afegirRelacioTP("t3", "p3");
		
		ControladorQueries cq = new ControladorQueries(cg);
		Scanner s = new Scanner(System.in);
		boolean menu = true;
		while(menu){
			System.out.print("#######################################################\n"
							+ "DRIVER ControladorQueries. Selecciona una de les següents proves:\n"
							+ " 1. Inicialitzar query normal\n"
							+ " 2. Inicialitzar query clustering\n"
							+ " 3. Seleccionar entitat inicial\n"
							+ " 4. Seleccionar entitat final\n"
							+ " 5. Executar query clustering\n"
							+ " 6. Executar query normal\n"
							+ " 7. Filtrar resultats N\n"
							+ " 8. Filtrar segons label\n"
							+ " 9. Consultar query actual\n"
							+ " 10. Consultar query recent\n"
							+ " 11. Acabar la prova\n");
			
			
			int n = s.nextInt();

			switch (n) {
			case 1: 
					System.out.println("Escriu el camí de la teva query\n");
					String cami = s.next();
					cq.inicialitzarQuerynormal(cami);
					break;
			case 2:
					System.out.println("Escriu el camí de la teva query clustering i el número de clusters a fer\n");
					String camiclust = s.next();
					int k = s.nextInt();
					cq.inicialitzarQueryClustering(camiclust ,k);
					break;
			case 3:
					System.out.println("Escriu el nom de l'entitat que vols afegir");
					String nom = s.next();
					cq.seleccionarEntitatInicial(nom);
					break;
			case 4:
					System.out.println("Escriu el nom de l'entitat que vols afegir");
					String nomEntFin = s.next();
					cq.seleccionarEntitatInicial(nomEntFin);
					break;
			case 5: 
					ArrayList< ArrayList<String>> resultatCluster = cq.executarClustering();
					for(int i = 0; i<resultatCluster.size(); i++){
						System.out.println("Cluster "+i);
						for(int j = 0; j<resultatCluster.get(i).size();i++){
							System.out.println(resultatCluster.get(i).get(j)+"   ");
						}
					}
					break;
			case 6:
					System.out.println("                Nom       HeteSim\n");
					ArrayList<Pair<String,Float>> resultat = cq.executarQuery();
					for(int i = 0; i<resultat.size(); i++){
						System.out.println("Resultat "+i+": "+resultat.get(i).first+"       "+resultat.get(i).second+"\n");
					}
					break;
			case 7:
					System.out.println("Escriu el numero del resultat que vols filtrar\n");
					String num = s.next();
					System.out.println("                Nom       HeteSim\n");
					ArrayList<Pair<String,Float>> resultatfiltre = cq.filtrarResultatN(num);
					for(int i = 0; i<resultatfiltre.size(); i++){
						System.out.println("Resultat "+i+": "+resultatfiltre.get(i).first+"       "+resultatfiltre.get(i).second+"\n");
					}
					break;
			case 8:
					System.out.println("Escriu la label dels resultats que vols filtrar\n");
					int label = s.nextInt();
					System.out.println("                Nom       HeteSim\n");
					ArrayList<Pair<String,Float>> resultatfiltrelabel = cq.filtrarResultatLabel(label);
					for(int i = 0; i<resultatfiltrelabel.size(); i++){
						System.out.println("Resultat "+i+": "+resultatfiltrelabel.get(i).first+"       "+resultatfiltrelabel.get(i).second+"\n");
					}
					break;
			case 9:
					String[] queryAct= cq.consultarQueryActual();
					for(int i = 0; i<queryAct.length; i++){
						if(queryAct[0]=="clustering"){
							System.out.println("QueryClustering -> Path: "+queryAct[1]+"\n");
						}
						else{
							System.out.println("QueryNormal -> Path: "+queryAct[1]+"  EntitatInicial: "+queryAct[2]);
							if(queryAct[3]!=null) System.out.println("EntitatFinal: "+queryAct[3]+"\n");
						}
					}
			case 10:
					System.out.println("Escriu el número de query recent a consultar");
					int numQueryRecent = s.nextInt();
					String[] queryRecent= cq.consultarQueryRecent(numQueryRecent);
					for(int i = 0; i<queryRecent.length; i++){
						if(queryRecent[0]=="clustering"){
							System.out.println("QueryClustering -> Path: "+queryRecent[1]+"\n");
						}
						else{
							System.out.println("QueryNormal -> Path: "+queryRecent[1]+"  EntitatInicial: "+queryRecent[2]);
							if(queryRecent[3]!=null) System.out.println("EntitatFinal: "+queryRecent[3]);
						}
					}
			case 11:
				menu = false;
				break;	
			}
			
		}
		s.close();
	}
	catch(Exception e){System.out.println(e.getMessage());}
}
}
