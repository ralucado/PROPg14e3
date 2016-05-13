package Domini;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverQueryNormal {
	public static void main(String[] args) {
		
		try{
			
		
		CtrlGraf cg = new CtrlGraf();
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
		
	

		System.out.println("prova");
		QueryNormal q = new QueryNormal(new Cami("nom", "APA", "descr"));
		HeteSim hs = new HeteSim(cg.consultarGraf());
		Scanner s = new Scanner(System.in);
		boolean menu = true;
		while(menu){
			System.out.print("#######################################################\n"
							+ "DRIVER QueryNormal. Selecciona una de les seg�ents proves:\n"
							+ " 1. Inicialitzar query\n"
							+ " 2. Comprovar si �s clustering\n"
							+ " 3. Comprovar si �s normal\n"
							+ " 4. Seleccionar entitat final\n"
							+ " 5. Seleccionar entitat inicial\n"
							+ " 6. Consultar cam�\n"
							+ " 7. Consultar entitat final\n"
							+ " 8. Consultar entitat inicial\n"
							+ " 9. Executar query\n"
							+ " 10. Acabar la prova\n");
			
			
			int n = s.nextInt();

			switch (n) {
			case 1: 
					System.out.println("Escriu el cam� de la teva query\n");
					String cami = s.next();
					q = new QueryNormal(new Cami("nom",cami,"descr"));
					break;
			case 2:
					if(q.esClustering()) System.out.println("La query �s de clustering");
					else System.out.println("La query no �s de clustering");
					break;
			case 3:
					if(q.esNormal()) System.out.println("La query �s normal");
					else System.out.println("La query no �s normal");
					break;
			case 4:
					System.out.println("Escriu el nom de l'entitat final");
					String nomEntFin = s.next();
					int mida = q.getPath().length();
					char c = q.getPath().charAt(mida-1);
					int id = 0;
					int posFi = 0;
					if(c=='A'){ 
						id =  cg.consultarGraf().getIdByNameAndType(nomEntFin, "Autor"); 
						posFi = cg.consultarGraf().getPositionByIdAutor(id);
					}
					else if(c=='P'){
						id = cg.consultarGraf().getIdByNameAndType(nomEntFin, "Paper");
						posFi = cg.consultarGraf().getPositionByIdPaper(id);
					}
					else if(c=='C'){
						id = cg.consultarGraf().getIdByNameAndType(nomEntFin, "Conferencia");
						posFi = cg.consultarGraf().getPositionByIdConferencia(id);
					}
					else if(c=='T'){
						id = cg.consultarGraf().getIdByNameAndType(nomEntFin, "Terme");
						posFi = cg.consultarGraf().getPositionByIdTerme(id);
					}
					System.out.println(c+" "+posFi+" "+id);
					q.setEntitatNoInicial(cg.consultarGraf().consultarEntitat(id), posFi);
					break;
			case 5: 
					System.out.println("Escriu el nom de l'entitat inicial");
					String nomEntIni = s.next();
					char cIni = q.getPath().charAt(0);
					int idIni = 0;
					int posIni = 0;
					if(cIni=='A'){ 
						idIni =  cg.consultarGraf().getIdByNameAndType(nomEntIni, "Autor"); 
						posIni = cg.consultarGraf().getPositionByIdAutor(idIni);
						System.out.println(cIni+" "+posIni+" "+idIni);
					}
					else if(cIni=='P'){
						idIni = cg.consultarGraf().getIdByNameAndType(nomEntIni, "Paper");
						posIni = cg.consultarGraf().getPositionByIdPaper(idIni);
						System.out.println(cIni+" "+posIni+" "+idIni);

					}
					else if(cIni=='C'){
						idIni = cg.consultarGraf().getIdByNameAndType(nomEntIni, "Conferencia");
						posIni = cg.consultarGraf().getPositionByIdConferencia(idIni);
						System.out.println(cIni+" "+posIni+" "+idIni);

					}
					else if(cIni=='T'){
						idIni = cg.consultarGraf().getIdByNameAndType(nomEntIni, "Terme");
						posIni = cg.consultarGraf().getPositionByIdTerme(idIni);
						System.out.println(cIni+" "+posIni+" "+idIni);

					}
					System.out.println(posIni);
					q.setEntitatInicial(cg.consultarGraf().consultarEntitat(idIni), posIni);
					break;
			case 6:
					System.out.println(q.getPath()+"\n");
					break;
			case 7:
					Entitat ef = q.getEntitatNoInicial();
					if(ef.isAutor()) System.out.println("Autor de nom: ");
					else if(ef.isConferencia()) System.out.println("Conferencia de nom: ");
					else if(ef.isPaper()) System.out.println("Paper de nom: ");
					else if(ef.isTerme()) System.out.println("Conferencia de nom: ");
					System.out.println(ef.getNom()+"\n");
					break;
			case 8:
					Entitat ei = q.getEntitatInicial();
					if(ei.isAutor()) System.out.println("Autor de nom: ");
					else if(ei.isConferencia()) System.out.println("Conferencia de nom: ");
					else if(ei.isPaper()) System.out.println("Paper de nom: ");
					else if(ei.isTerme()) System.out.println("Conferencia de nom: ");
					System.out.println(ei.getNom()+"\n");
					break;
			case 9:
					
					ArrayList<Pair<Integer,Float>> resultat = q.executa(hs);
					for(int i = 0; i<resultat.size(); i++){
						char c1 = q.getPath().charAt(q.getPath().length()-1);
						int id1 = 0;
						if(c1=='A') id1 = cg.consultarGraf().getIdByPositionAutor(resultat.get(i).first);
						else if(c1=='P') id1 = cg.consultarGraf().getIdByPositionPaper(resultat.get(i).first);
						else if(c1=='T') id1 = cg.consultarGraf().getIdByPositionTerme(resultat.get(i).first);
						else if(c1=='C') id1 = cg.consultarGraf().getIdByPositionConferencia(resultat.get(i).first);
						
						System.out.println("Resultat "+i+": "+cg.consultarGraf().consultarEntitat(id1).getNom()+"       "+resultat.get(i).second+"\n");
					}
					break;
			case 10:
				menu = false;
				break;	
			}
			
		}
		s.close();
	}
	catch(Exception e){System.out.println(e.getMessage());}
}
}
