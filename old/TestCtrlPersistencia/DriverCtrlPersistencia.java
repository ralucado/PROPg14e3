package TestCtrlPersistencia;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverCtrlPersistencia {
	public static void main(String[] args) {
		CtrlPersistencia cf = new CtrlPersistencia();
		
		System.out.print("#######################################################\n"
				+ "DRIVER CtrlPersistencia. Selecciona una de les següents proves:\n"
				+ "  1. Creadora\n"
				+ "  2. Importar conjunt d'usuaris\n"
				+ "  3. Exportar conjunt d'usuaris\n"
				+ "  4. Importar camins d'usuari\n"
				+ "  5. Exportar camins d'usuari\n"
				+ "  6. Importar camins predefinits\n"
				+ "  7. Exportar camins predefinits\n"
				+ "  8. Importar entitats\n"
				+ "  9. Exportar entitats\n"
				+ " 10. Importar relacions entre entitats\n"
				+ " 11. Exportar relacions entre entitats\n\n");
			//	+ " 12. Importar labels d'entitats\n"
			//	+ " 13. Exportar labels d'entitats\n\n");
		
		Scanner s = new Scanner(System.in);
		switch (s.nextInt()) {
		case 1:
			System.out.println("\nProva 1 - Creem un CtrlPersistencia:");
			CtrlPersistencia cf2 = new CtrlPersistencia();
			try {
				System.out.println("S'ha creat correctament.");
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			break;
		
			
		case 2:
			System.out.println("\nProva 2 - Importem un conjunt d'usuaris (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> users = cf.importarUsuaris();
				System.out.println("Conjunt d'usuaris importat (/DATA/Usuaris/Usuaris.txt):");
				for (ArrayList<String> user : users) 
				System.out.println(user);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			break;
			
			
		case 3:
			System.out.println("\nProva 3 - Exportem un conjunt d'usuaris que hem generat:");
			try {
				ArrayList<ArrayList<String>> cjtUsuaris = new ArrayList<ArrayList<String>>();
				System.out.println("Conjunt d'usuaris generat:");
				for (int i = 0; i < 10; ++i) {
					ArrayList<String> a = new ArrayList<String>();
					a.add("nomUsuari"+i); a.add("contrasenya"+i);
					if (i == 4 || i == 7) a.add("1");
					else a.add("0");
					System.out.println(a);
					cjtUsuaris.add(a);
				}
				cf.exportarUsuaris(cjtUsuaris);
				System.out.println("\nS'ha exportat correctament (/DATA/Usuaris/Usuaris.txt).");
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			break;
			
			
		case 4:
			System.out.println("\nProva 4 - Importem un conjunt de camins de l'usuari 'prova' (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> cjtCamins = cf.importarCaminsUsuari("prova");
				System.out.println("Conjunt de camins de l'usuari 'prova' importat (/DATA/Camins/dUsuari/prova.txt):");
				for (ArrayList<String> cami : cjtCamins)	System.out.println(cami);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			break;
			
			
		case 5:
			System.out.println("\nProva 5 - Exportem un conjunt de camins de l'usuari 'prova' (que hem generat):");
			try {
				ArrayList<ArrayList<String>> cjtCamins = new ArrayList<ArrayList<String>>();
				System.out.println("Conjunt de camins d'usuari 'prova' generat:");
				for (int i = 0; i < 10; ++i) {
					ArrayList<String> a = new ArrayList<String>();
					a.add("nomCami"+i); a.add("path"+i); a.add("descripcio"+i);
					System.out.println(a);
					cjtCamins.add(a);
				}
				cf.exportarCaminsUsuari("prova", cjtCamins);
				System.out.println("\nS'ha exportat correctament (/DATA/Camins/dUsuari/prova.txt).");
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			break;
			
			
		case 6:
			System.out.println("\nProva 6 - Importem un conjunt de camins predefinits (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> cjtCamins = cf.importarCaminsPredefinits();
				System.out.println("Conjunt de camins predefinits importat (/DATA/Camins/Predefinits.txt):");
				for (ArrayList<String> cami : cjtCamins)	System.out.println(cami);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			break;
		case 7:
			System.out.println("\nProva 7 - Exportem un conjunt de camins predefinits (que hem generat):");
			try {
				ArrayList<ArrayList<String>> cjtCamins = new ArrayList<ArrayList<String>>();
				System.out.println("Conjunt de camins predefinits generat:");
				for (int i = 0; i < 10; ++i) {
					ArrayList<String> a = new ArrayList<String>();
					a.add("nomCami"+i); a.add("path"+i); a.add("descripcio"+i);
					System.out.println(a);
					cjtCamins.add(a);
				}
				cf.exportarCaminsPredefinits(cjtCamins);
				System.out.println("\nS'ha exportat correctament (/DATA/Camins/Predefinits.txt).");
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			break;
		case 8:
	/*		System.out.println("\nProva 8.1 - Importem un conjunt d'autors (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> authors = cf.importarAuthors();
				System.out.println("Conjunt d'autors importat (/DATA/Dades/Entitats/author.txt):");
				for (ArrayList<String> author : authors)	System.out.println(author);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			
			System.out.println("\nProva 8.2 - Importem un conjunt de papers (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> papers = cf.importarPapers();
				System.out.println("Conjunt de papers importat (/DATA/Dades/Entitats/paper.txt):");
				for (ArrayList<String> paper : papers)	System.out.println(paper);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			
			System.out.println("\nProva 8.3 - Importem un conjunt de conferències (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> conferences = cf.importarConferences();
				System.out.println("Conjunt de conferències importat (/DATA/Dades/Entitats/conf.txt):");
				for (ArrayList<String> conf : conferences)	System.out.println(conf);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			
			System.out.println("\nProva 8.4 - Importem un conjunt de termes (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> terms = cf.importarTerms();
				System.out.println("Conjunt de termes importat (/DATA/Dades/Entitats/term.txt):");
				for (ArrayList<String> term : terms)	System.out.println(term);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}*/
			
			System.out.println("\nProva 8 - Importem un conjunt d'entitats (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> entitats = cf.importarEntitats();
				System.out.println("Conjunt d'entitats importat (/DATA/Dades/Entitats.txt):");
				for (ArrayList<String> entitat : entitats) System.out.println(entitat);
			}
			catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
			
			break;
			
			
		case 9:
			System.out.println("\nProva 9: Exportem un conjunt d'entitats (autors, papers, conferències i termes) que hem generat:");
			//ArrayList<ArrayList<String>> authors = new ArrayList<ArrayList<String>>();
			//System.out.println("Conjunt d'autors generat:");
			ArrayList<ArrayList<String>> entitats = new ArrayList<ArrayList<String>>();
			System.out.println("Conjunt d'entitats generat:");
			for (int i = 0; i < 10; ++i) {
				ArrayList<String> a = new ArrayList<String>();
				a.add("idAutor"+i); a.add("nomAutor"+i); a.add("label"+i%5);
				System.out.println(a);
				entitats.add(a);
			}
			
//			ArrayList<ArrayList<String>> papers = new ArrayList<ArrayList<String>>();
//			System.out.println("\nConjunt de papers generat:");
			for (int i = 0; i < 10; ++i) {
				ArrayList<String> a = new ArrayList<String>();
				a.add("idPaper"+i); a.add("nomPaper"+i); a.add("label"+i%5);
				System.out.println(a);
				entitats.add(a);
			}
			
		//	ArrayList<ArrayList<String>> conferences = new ArrayList<ArrayList<String>>();
		//	System.out.println("\nConjunt de conferències generat:");
			for (int i = 0; i < 10; ++i) {
				ArrayList<String> a = new ArrayList<String>();
				a.add("idConferència"+i); a.add("nomConferència"+i); a.add("label"+i%5);
				System.out.println(a);
				entitats.add(a);
			}
			
			//ArrayList<ArrayList<String>> terms = new ArrayList<ArrayList<String>>();
			//System.out.println("\nConjunt de termes generat:");
			for (int i = 0; i < 10; ++i) {
				ArrayList<String> a = new ArrayList<String>();
				a.add("idTerme"+i); a.add("nomTerme"+i);
				System.out.println(a);
				entitats.add(a);
			}
			
			try {
				//cf.exportarEntitats(papers, authors, conferences, terms);
				cf.exportarEntitats(entitats);
				System.out.println("\nS'han exportat correctament (/DATA/Dades/Entitats.txt).");
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			break;
			
		case 10:
			/*System.out.println("\nProva 10.1 - Importem un conjunt de relacions paper-autor (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> pa = cf.importarPaperAuthor();
				System.out.println("Conjunt de relacions paper-autor importat (/DATA/Dades/Relacions/paper_author.txt):");
				for (ArrayList<String> r : pa)	System.out.println(r);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			
			System.out.println("\nProva 10.2 - Importem un conjunt de relacions paper-conferència (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> pc = cf.importarPaperConference();
				System.out.println("Conjunt de relacions paper-conferència importat (/DATA/Dades/Relacions/paper_conf.txt):");
				for (ArrayList<String> r : pc)	System.out.println(r);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			
			System.out.println("\nProva 10.3 - Importem un conjunt de relacions paper-terme (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> pt = cf.importarPaperTerm();
				System.out.println("Conjunt de relacions paper-terme importat (/DATA/Dades/Relacions/paper_term.txt):");
				for (ArrayList<String> r : pt)	System.out.println(r);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}*/
			System.out.println("\nProva 10 - Importem un conjunt de relacions (paper-terme, paper-conferència i paper-autor):");
			try{
				ArrayList<ArrayList<String>> relacions = cf.importarRelacions();
				System.out.println("Conjunt de relacions importat (/DATA/Dades/Relacions.txt):");
				for (ArrayList<String> relacio : relacions)	System.out.println(relacio);
			}
			catch (Exception e) {System.out.println("EXCEPCIÓ: "+e.getMessage());}
			break;
			
		case 11:
			System.out.println("\nProva 11: Exportem un conjunt de relacions (paper-autor, paper-conferència i paper-terme) que hem generat:");
			//ArrayList<ArrayList<String>> pa = new ArrayList<ArrayList<String>>();
			//System.out.println("Conjunt de relacions paper-autor generat:");
			ArrayList<ArrayList<String>> relacions2 = new ArrayList<ArrayList<String>>();
			System.out.println("Conjunt de relacions generat:");
			for (int i = 0; i < 10; ++i) {
				ArrayList<String> a = new ArrayList<String>();
				a.add("idPaper"+i); a.add("idAutor"+i);
				System.out.println(a);
				relacions2.add(a);
			}
			
			//ArrayList<ArrayList<String>> pc = new ArrayList<ArrayList<String>>();
			//System.out.println("\nConjunt de relacions paper-conferència generat:");
			for (int i = 0; i < 10; ++i) {
				ArrayList<String> a = new ArrayList<String>();
				a.add("idPaper"+i); a.add("idConferència"+i);
				System.out.println(a);
				relacions2.add(a);
			}
			
			//ArrayList<ArrayList<String>> pt = new ArrayList<ArrayList<String>>();
			//System.out.println("\nConjunt de relacions paper-terme generat:");
			for (int i = 0; i < 10; ++i) {
				ArrayList<String> a = new ArrayList<String>();
				a.add("idPaper"+i); a.add("idTerme"+i);
				System.out.println(a);
				relacions2.add(a);
			}
			
			try {
				//cf.exportarRelacions(pa, pc, pt);
				cf.exportarRelacions(relacions2);
				System.out.println("\nS'han exportat correctament (/DATA/Dades/Relacions.txt):");
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			break;
		/*case 12:
			System.out.println("\nProva 12.1 - Importem un conjunt d'autors amb label (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> labelA = cf.importarAuthorLabel();
				System.out.println("Conjunt d'autors amb label importat (/DATA/Dades/Labels/author_label.txt):");
				for (ArrayList<String> r : labelA)	System.out.println(r);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			
			System.out.println("\nProva 12.2 - Importem un conjunt de conferències amb label (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> labelC = cf.importarConferenceLabel();
				System.out.println("Conjunt de conferències amb label importat (/DATA/Dades/Labels/conf_label.txt):");
				for (ArrayList<String> r : labelC)	System.out.println(r);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			
			System.out.println("\nProva 12.3 - Importem un conjunt de papers amb label (si no existeix, tenim excepció!):");
			try {
				ArrayList<ArrayList<String>> labelP = cf.importarPaperLabel();
				System.out.println("Conjunt de papers amb label importat (/DATA/Dades/Labels/paper_label.txt):");
				for (ArrayList<String> r : labelP)	System.out.println(r);
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			break;
			
			
		case 13:
			System.out.println("\nProva 13: Exportem un conjunt de labels d'entitats (paper, autor i conferència) que hem generat:");
			ArrayList<ArrayList<String>> labelA = new ArrayList<ArrayList<String>>();
			System.out.println("Conjunt d'autors amb label generat:");
			for (int i = 0; i < 10; ++i) {
				ArrayList<String> a = new ArrayList<String>();
				a.add("idAutor"+i); a.add("label"+i); a.add("nomAutor"+i);
				System.out.println(a);
				labelA.add(a);
			}
			
			ArrayList<ArrayList<String>> labelP = new ArrayList<ArrayList<String>>();
			System.out.println("\nConjunt de paper amb label generat:");
			for (int i = 0; i < 10; ++i) {
				ArrayList<String> a = new ArrayList<String>();
				a.add("idPaper"+i); a.add("label"+i); a.add("nomPaper"+i);
				System.out.println(a);
				labelP.add(a);
			}
			
			ArrayList<ArrayList<String>> labelC = new ArrayList<ArrayList<String>>();
			System.out.println("\nConjunt de conferències amb label generat:");
			for (int i = 0; i < 10; ++i) {
				ArrayList<String> a = new ArrayList<String>();
				a.add("idConferència"+i); a.add("label"+i); a.add("nomConferència"+i);
				System.out.println(a);
				labelC.add(a);
			}
			
			try {
				cf.exportarLabels(labelA, labelC, labelP);
				System.out.println("\nS'han exportat correctament: \n * /DATA/Dades/Labels/paper_label.txt"
						+ "\n * /DATA/Dades/Labels/author_label.txt\n * /DATA/Dades/Labels/conf_label.txt");
			}
			catch (Exception e) {
				System.out.println("EXCEPCIÓ: "+e.getMessage());
			}
			break;*/
		}
		s.close();
	}
}
