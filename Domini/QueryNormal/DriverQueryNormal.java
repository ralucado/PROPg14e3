package ProvaQN;

import java.util.ArrayList;
import java.util.Scanner;

import Dominio.Pair;
import ProvaCU.Cami;

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
							+ "DRIVER QueryNormal. Selecciona una de les següents proves:\n"
							+ " 1. Inicialitzar query\n"
							+ " 2. Comprovar si és clustering\n"
							+ " 3. Comprovar si és normal\n"
							+ " 4. Seleccionar entitat final\n"
							+ " 5. Seleccionar entitat inicial\n"
							+ " 6. Consultar camí\n"
							+ " 7. Consultar entitat final\n"
							+ " 8. Consultar entitat inicial\n"
							+ " 9. Executar query\n"
							+ " 10. Acabar la prova\n");
			
			
			int n = s.nextInt();

			switch (n) {
			case 1: 
					System.out.println("Escriu el camí de la teva query\n");
					String cami = s.next();
					q = new QueryNormal(new Cami("nom",cami,"descr"));
					break;
			case 2:
					if(q.esClustering()) System.out.println("La query és de clustering");
					else System.out.println("La query no és de clustering");
					break;
			case 3:
					if(q.esNormal()) System.out.println("La query és normal");
					else System.out.println("La query no és normal");
					break;
			case 4:
					System.out.println("Escriu el nom de l'entitat final");
					String nomEntFin = s.next();
					int mida = q.getPath().length();
					char c = q.getPath().charAt(mida-1);
					int id = 0;
					int posFi = 0;
					if(c=='A'){ 
						id =  cg.consultarGraf().getIdByNameAndType(nomEntFin, c+"utor"); 
						posFi = cg.consultarGraf().getPositionByIdAutor(id);
					}
					else if(c=='P'){
						id = cg.consultarGraf().getIdByNameAndType(nomEntFin, c+"aper");
						posFi = cg.consultarGraf().getPositionByIdPaper(id);
					}
					else if(c=='C'){
						id = cg.consultarGraf().getIdByNameAndType(nomEntFin, c+"onferencia");
						posFi = cg.consultarGraf().getPositionByIdConferencia(id);
					}
					else if(c=='T'){
						id = cg.consultarGraf().getIdByNameAndType(nomEntFin, c+"erme");
						posFi = cg.consultarGraf().getPositionByIdTerme(id);
					}
					q.setEntitatNoInicial(cg.consultarGraf().consultarEntitat(id), posFi);
					break;
			case 5: 
					System.out.println("Escriu el nom de l'entitat inicial");
					String nomEntIni = s.next();
					char cIni = q.getPath().charAt(0);
					int idIni = 0;
					int posIni = 0;
					if(cIni=='A'){ 
						idIni =  cg.consultarGraf().getIdByNameAndType(nomEntIni, cIni+"utor"); 
						posIni = cg.consultarGraf().getPositionByIdAutor(idIni);
					}
					else if(cIni=='P'){
						idIni = cg.consultarGraf().getIdByNameAndType(nomEntIni, cIni+"aper");
						posIni = cg.consultarGraf().getPositionByIdPaper(idIni);
					}
					else if(cIni=='C'){
						idIni = cg.consultarGraf().getIdByNameAndType(nomEntIni, cIni+"onferencia");
						posIni = cg.consultarGraf().getPositionByIdConferencia(idIni);
					}
					else if(cIni=='T'){
						idIni = cg.consultarGraf().getIdByNameAndType(nomEntIni, cIni+"erme");
						posIni = cg.consultarGraf().getPositionByIdTerme(idIni);
					}
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
					System.out.println(resultat.size());
					for(int i = 0; i<resultat.size(); i++){
						char c1 = q.getPath().charAt(q.getPath().length()-1);
						int id1 = 0;
						if(c1=='A') id1 = cg.consultarGraf().getIdByPositionAutor(resultat.get(i).first);
						else if(c1=='P') id1 = cg.consultarGraf().getIdByPositionPaper(resultat.get(i).first);
						else if(c1=='T') id1 = cg.consultarGraf().getIdByPositionTerme(resultat.get(i).first);
						else if(c1=='C') id1 = cg.consultarGraf().getIdByPositionConferencia(resultat.get(i).first);
						
						System.out.println("Resultat "+i+": "+cg.consultarGraf().consultarEntitat(id1).getNom()+"       "+resultat.get(i).second+"\n");
						System.out.println(resultat.get(i).second);
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
