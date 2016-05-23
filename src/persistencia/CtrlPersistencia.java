package persistencia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * La classe ControladorFitxers és el controlador de persistència del programa. S'encarrega l'importació i exportació dels conjunts de dades del programa a través de matrius d'strings.
 * @author Arnau Blanch Cortès
 */
public class CtrlPersistencia {
	private String dataLocation = "DATA/";
	private String fileExtension = ".txt";
	
	public CtrlPersistencia() {
		
	}
	
	public String getDataLocation() {
		return dataLocation;
	}
	
	public void setDataLocation(String dir) {
		dataLocation = dir;
	}
	
	public String getFileExtension() {
		return fileExtension;
	}
	
	public void setFileExtension(String ext) {
		fileExtension = ext;
	}
	
	/**
	 * Importa dades des de fitxer
	 * @param filename nom (i directori) del fitxer a importar
	 * @throws Exception ...
	 * @return matriu d'strings importades des del fitxer
	 */
	private ArrayList<ArrayList<String>> importar(String filename) throws Exception {
		FileReader fr = new FileReader(dataLocation + filename + fileExtension);
		Scanner s = new Scanner(fr);
		
		ArrayList<ArrayList<String>> t = new ArrayList<ArrayList<String>>();
		
		while (s.hasNextLine()) {
			ArrayList<String> linia = new ArrayList<String>();
			String[] liniaArray = null;
			String line = s.nextLine();
			liniaArray = line.split("\t");
			for (String str : liniaArray) {
				linia.add(str);
			}
			t.add(linia);
		}
		
		s.close(); fr.close();
		return t;
	}
	
	/**
	 * Exporta matriu d'strings a fitxer
	 * @param filename nom (i directori) del fitxer a exportar
	 * @param cjt martiu d'strings a exportar
	 * @throws Exception ...
	 */
	private void exportar(String filename, ArrayList<ArrayList<String>> cjt) throws Exception {
		File f = new File (dataLocation + filename + fileExtension);
		f.getParentFile().mkdirs();
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
		
		for (int i = 0; i < cjt.size(); ++i) {
			for (int j = 0; j < cjt.get(i).size(); ++j) {
				if (j != 0) pw.print("\t");
				pw.print(cjt.get(i).get(j));
			}
			pw.println("");
		}
		
		pw.close(); fw.close();
	}
	
	// IMPORTACIÓ ESPECÍFICA
	
	// Camins
	
	/**
	 * Importa els camins predefinits
	 * @return matriu d'strings amb els camins predefinits
	 * @throws Exception ...
	 */
	public ArrayList<ArrayList<String>> importarCaminsPredefinits() throws Exception {
		return importar("Camins/Predefinits");
	}
	
	/**
	 * Importa els camins de l'usuari especificat
	 * @param usuari nom d'usuari
	 * @return matriu d'strings amb els camins de l'usuari especificat
	 * @throws Exception ...
	 */
	public ArrayList<ArrayList<String>> importarCaminsUsuari(String usuari) throws Exception {
		return importar("Camins/dUsuari/"+usuari);
	}
	
	// Usuaris
	
	/**
	 * Importa el conjunt d'usuaris
	 * @return matriu d'strings amb l'usuari i la contrasenya de cada usuari
	 * @throws Exception ...
	 */
	public ArrayList<ArrayList<String>> importarUsuaris() throws Exception {
		return importar("Usuaris/Usuaris");
	}
	
	// Graf (dades): entitats
	
	/**
	 * Importa les entitats
	 * @return matriu d'strings amb l'id, el nom de l'entitat i el label (opcional)
	 * @throws Exception ...
	 */
	public ArrayList<ArrayList<String>> importarEntitats() throws Exception {
		return importar("Dades/Entitats");
	}
	
	// Graf (dades): relacions
	
	/**
	 * Importa les relacions entre entitats
	 * @return matriu d'strings amb l'id del paper i l'id de l'altra entitat
	 * @throws Exception ...
	 */
	public ArrayList<ArrayList<String>> importarRelacions() throws Exception {
		return importar("Dades/Relacions");
	}
	
	// EXPORTACIÓ ESPECÍFICA
	
	// Camins
	
	/**
	 * Exporta el conjunt de camins predefinits
	 * @param cjt matrius d'strings amb el nom d'usuari i la contrasenya de cada usuari del conjunt
	 * @throws Exception ...
	 */
	public void exportarCaminsPredefinits(ArrayList<ArrayList<String>> cjt) throws Exception {
		exportar("Camins/Predefinits", cjt);
	}
	
	/**
	 * Exporta el conjunt de camins de l'usuari especificat
	 * @param usuari nom de l'usuari
	 * @param cjt matriu d'strings amb el nom, el path i la descripció de cada camí del conjunt
	 * @throws Exception ...
	 */
	public void exportarCaminsUsuari(String usuari, ArrayList<ArrayList<String>> cjt) throws Exception {
		// S'ha de limitar que un nom d'usuari només tingui lletres o números (si es diu ../XXX o ../predefinits, malament)
		exportar("Camins/dUsuari/"+usuari, cjt);
	}
	
	// Usuaris
	
	/**
	 * Exporta el conjunt d'usuaris
	 * @param cjt matrius d'strings amb el nom d'usuari i la contrasenya de cada usuari del conjunt
	 * @throws Exception ...
	 */
	public void exportarUsuaris(ArrayList<ArrayList<String>> cjt) throws Exception {
		exportar("Usuaris/Usuaris", cjt);
	}
	
	// Graf (dades)
	
	/**
	 * Exporta un conjunt d'entitats
	 * @param entitats matriu d'strings amb l'id, el nom i el label (opcional)
	 * @throws Exception
	 */
	public void exportarEntitats(ArrayList<ArrayList<String>> entitats) throws Exception {
		exportar("Dades/Entitats", entitats);
	}

	
	/**
	 * Exporta un conjunt de relacions
	 * @param relacions matriu d'strings amb l'id del paper i de l'altra entitat
	 * @throws Exception ...
	 */
	public void exportarRelacions(ArrayList<ArrayList<String>> relacions) throws Exception {
		exportar("Dades/Relacions", relacions);
	}
	
	private void ultimaXifra(ArrayList<ArrayList<String>> a, int index, char c) {
		char xifra;
		if (c == 'A') xifra = '0';
		else if (c == 'C') xifra = '2';
		else if (c == 'P') xifra = '1';
		else xifra = '3';
		
		for (ArrayList<String> aa : a) {
			String st = aa.get(index) + xifra;
			aa.set(index, st);
		}
	}
	
	private void assignaLabel(ArrayList<ArrayList<String>> ent, ArrayList<ArrayList<String>> lab) {
		ArrayList<String> entId = new ArrayList<String>();
		for (ArrayList<String> e : ent)  entId.add(e.get(0));
		
		for (ArrayList<String> l : lab) {
			int index = entId.indexOf(l.get(0));
			ent.get(index).add(l.get(1));
		}
	}
	
	public void transformaEntitats() throws Exception {
		ArrayList<ArrayList<String>> autors = importar("Dades/author");
		ArrayList<ArrayList<String>> conferencies = importar("Dades/conf");
		ArrayList<ArrayList<String>> papers = importar("Dades/paper");
		ArrayList<ArrayList<String>> termes = importar("Dades/term");
		
		ArrayList<ArrayList<String>> autorsL = importar("Dades/author_label");
		ArrayList<ArrayList<String>> conferenciesL = importar("Dades/conf_label");
		ArrayList<ArrayList<String>> papersL = importar("Dades/paper_label");
		
		ArrayList<ArrayList<String>> pa = importar("Dades/paper_author");
		ArrayList<ArrayList<String>> pc = importar("Dades/paper_conf");
		ArrayList<ArrayList<String>> pt = importar("Dades/paper_term");
		System.out.println("IMPORTAT!");
	
		// Author
		assignaLabel(autors, autorsL);
		System.out.println("ep");
		ultimaXifra(autors, 0, 'A');
		// Conference
		assignaLabel(conferencies, conferenciesL);
		ultimaXifra(conferencies, 0, 'C');
		// Paper
		assignaLabel(papers, papersL);
		ultimaXifra(papers, 0, 'P');
		// Term
		ultimaXifra(termes, 0, 'T');
		
		// PaperAuthor
		ultimaXifra(pa, 0, 'P');
		ultimaXifra(pa, 1, 'A');
		// PaperConference
		ultimaXifra(pc, 0, 'P');
		ultimaXifra(pc, 1, 'C');
		// PaperTerm
		ultimaXifra(pt, 0, 'P');
		ultimaXifra(pt, 1, 'T');
		
		System.out.println("CANVIAT!");
		
		ArrayList<ArrayList<String>> entitats = new ArrayList<ArrayList<String>>();
		entitats.addAll(autors);
		entitats.addAll(papers);
		entitats.addAll(conferencies);
		entitats.addAll(termes); System.out.println("ent: "+ entitats.size());
		
		ArrayList<ArrayList<String>> relacions = new ArrayList<ArrayList<String>>();
		relacions.addAll(pa);
		relacions.addAll(pc);
		relacions.addAll(pt); System.out.println("rel: "+ relacions.size());
		
		/*exportar("DadesNoves/author", autors);
		exportar("DadesNoves/paper", papers);
		exportar("DadesNoves/conf", conferencies);
		exportar("DadesNoves/term", termes);*/
		
		exportar("DadesNoves/Entitats", entitats);
		exportar("DadesNoves/Relacions", relacions);
		System.out.println("EXPORTAT!");
	}
	
	/**
	 * Crea un nou fitxer de camins per a l'usuari <tt>user</tt>
	 * @param user nom d'usuari
	 * @throws Exception pot retornar IOException
	 */
	public void creaFitxerCamins(String user) throws Exception {
		File f = new File (dataLocation + "Camins/dUsuari/" + user + fileExtension);
		f.getParentFile().mkdirs();
		f.createNewFile();
	}

	public void esborraFitxerCamins(String nom) {
		File f = new File(dataLocation + "Camins/dUsuari/" + nom + fileExtension);
		f.delete();
	}

	public void reanomenaFitxerCamins(String nomActual, String nomNou) {
		File orig = new File(dataLocation + "Camins/dUsuari/" + nomActual + fileExtension);
		File nou = new File(dataLocation + "Camins/dUsuari/" + nomNou + fileExtension);
		orig.renameTo(nou);
	}
}
