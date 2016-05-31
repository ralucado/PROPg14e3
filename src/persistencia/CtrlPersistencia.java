package persistencia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * La classe ControladorFitxers és el controlador de persistència del programa. S'encarrega l'importació i exportació dels conjunts de dades del programa a través de matrius d'strings.
 * @author Arnau Blanch Cortès
 */
public class CtrlPersistencia {
	private String dataLocation = "DATA/";	// Ubicació carpeta de dades
	private String fileExtension = ".txt";	// Extensió dels fitxers
	
	public static void main(String[] args) {
		CtrlPersistencia ctrl = new CtrlPersistencia();
		System.out.println(ctrl.existeixenMatrius("PA"));
	}
	
	/**
	 * Crea un nou CtrlPersistencia
	 */
	public CtrlPersistencia() {
		
	}
	
	/**
	 * Retorna la ubicació de la carpeta amb les dades
	 * @return ubiciació carpeta de dades
	 */
	public String getDataLocation() {
		return dataLocation;
	}
	
	/**
	 * Canvia la ubicació de la carpeta amb les dades
	 * @param dir nova ubicació carpeta de dades
	 */
	public void setDataLocation(String dir) {
		dataLocation = dir;
	}
	
	/**
	 * Retorna l'extensió dels fitxers
	 * @return extensió dels fitxers
	 */
	public String getFileExtension() {
		return fileExtension;
	}
	
	/**
	 * Canvia l'extensió dels fitxers
	 * @param ext extensió dels fitxers
	 */
	public void setFileExtension(String ext) {
		fileExtension = ext;
	}
	
	/**
	 * Importa dades des de fitxer
	 * @param filename nom (i directori) del fitxer a importar
	 * @throws Exception pot retornar IOException
	 * @return matriu d'strings importades des del fitxer
	 */
	private ArrayList<ArrayList<String>> importar(String filename) throws Exception {
		FileReader fr = new FileReader(dataLocation + filename + fileExtension);
		Scanner s = new Scanner(fr);
		
		ArrayList<ArrayList<String>> linies = new ArrayList<ArrayList<String>>();
		
		while (s.hasNextLine()) {
			ArrayList<String> linia = new ArrayList<String>();
			String[] liniaArray = null;
			String liniaString = s.nextLine();
			liniaArray = liniaString.split("\t");
			for (String element : liniaArray) {
				linia.add(element);
			}
			linies.add(linia);
		}
		
		s.close(); fr.close();
		return linies;
	}
	
	/**
	 * Exporta matriu d'strings a fitxer
	 * @param filename nom (i directori) del fitxer a exportar
	 * @param cjt matriu d'strings a exportar
	 * @throws Exception pot retornar IOException
	 */
	private void exportar(String filename, ArrayList<ArrayList<String>> cjt) throws Exception {
		File f = new File (dataLocation + filename + fileExtension);
		f.getParentFile().mkdirs(); // Va creant directoris enrere, si no existien
		f.createNewFile(); // Crea nou fitxer
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
		
		for (ArrayList<String> linia : cjt) {
			for (int j = 0; j < linia.size(); ++j) {
				if (j != 0) pw.print("\t");
				pw.print(linia.get(j));
			}
			pw.println();
		}
		
		pw.close();
	}
	
	// IMPORTACIÓ ESPECÍFICA
	// Camins
	
	/**
	 * Importa els camins predefinits
	 * @return matriu d'strings amb els camins predefinits
	 * @throws Exception pot retornar IOException
	 */
	public ArrayList<ArrayList<String>> importarCaminsPredefinits() throws Exception {
		return importar("Camins/Predefinits");
	}
	
	/**
	 * Importa els camins de l'usuari especificat
	 * @param usuari nom d'usuari
	 * @return matriu d'strings amb els camins de l'usuari especificat
	 * @throws Exception pot retornar IOException
	 */
	public ArrayList<ArrayList<String>> importarCaminsUsuari(String usuari) throws Exception {
		return importar("Camins/dUsuari/"+usuari);
	}
	
	// Usuaris
	
	/**
	 * Importa el conjunt d'usuaris
	 * @return matriu d'strings amb l'usuari i la contrasenya de cada usuari
	 * @throws Exception pot retornar IOException
	 */
	public ArrayList<ArrayList<String>> importarUsuaris() throws Exception {
		return importar("Usuaris/Usuaris");
	}
	
	// Graf (dades): entitats
	
	/**
	 * Importa les entitats
	 * @return matriu d'strings amb l'id, el nom de l'entitat i el label (opcional)
	 * @throws Exception pot retornar IOException
	 */
	public ArrayList<ArrayList<String>> importarEntitats() throws Exception {
		return importar("Dades/Entitats");
	}
	
	// Graf (dades): relacions
	
	/**
	 * Importa les relacions entre entitats
	 * @return matriu d'strings amb l'id del paper i l'id de l'altra entitat
	 * @throws Exception pot retornar IOException
	 */
	public ArrayList<ArrayList<String>> importarRelacions() throws Exception {
		return importar("Dades/Relacions");
	}
	
	// EXPORTACIÓ ESPECÍFICA
	// Camins
	
	/**
	 * Exporta el conjunt de camins de l'usuari especificat
	 * @param usuari nom de l'usuari
	 * @param cjt matriu d'strings amb el nom, el path i la descripció de cada camí del conjunt
	 * @throws Exception pot retornar IOException
	 */
	public void exportarCaminsUsuari(String usuari, ArrayList<ArrayList<String>> cjt) throws Exception {
		exportar("Camins/dUsuari/"+usuari, cjt);
	}
	
	// Usuaris
	
	/**
	 * Exporta el conjunt d'usuaris
	 * @param cjt matrius d'strings amb el nom d'usuari i la contrasenya de cada usuari del conjunt
	 * @throws Exception pot retornar IOException
	 */
	public void exportarUsuaris(ArrayList<ArrayList<String>> cjt) throws Exception {
		exportar("Usuaris/Usuaris", cjt);
	}
	
	// Graf (dades)
	
	/**
	 * Exporta un conjunt d'entitats
	 * @param entitats matriu d'strings amb l'id, el nom i el label
	 * @throws Exception pot retornar IOException
	 */
	public void exportarEntitats(ArrayList<ArrayList<String>> entitats) throws Exception {
		exportar("Dades/Entitats", entitats);
	}

	
	/**
	 * Exporta un conjunt de relacions
	 * @param relacions matriu d'strings amb l'id del paper i de l'altra entitat
	 * @throws Exception pot retornar IOException
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
	
	/**
	 * Transforma el dataset DBLP estàndard al format d'entitats i relacions requerit pel programa
	 * @throws Exception pot retornar IOException
	 */
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
	
		// Author
		assignaLabel(autors, autorsL);
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
				
		ArrayList<ArrayList<String>> entitats = new ArrayList<ArrayList<String>>();
		entitats.addAll(autors);
		entitats.addAll(papers);
		entitats.addAll(conferencies);
		entitats.addAll(termes);
		
		ArrayList<ArrayList<String>> relacions = new ArrayList<ArrayList<String>>();
		relacions.addAll(pa);
		relacions.addAll(pc);
		relacions.addAll(pt);
		
		exportar("DadesNoves/Entitats", entitats);
		exportar("DadesNoves/Relacions", relacions);
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

	/**
	 * Esborra el fitxer de camins de l'usuari amb nom d'usuari <tt>nom</tt>
	 * @param nom nom d'usuari
	 */
	public void esborraFitxerCamins(String nom) {
		File f = new File(dataLocation + "Camins/dUsuari/" + nom + fileExtension);
		f.delete();
	}

	/**
	 * Reanomena el fitxer de camins de l'usuari antic <tt>nomActual</tt> amb el nom de l'usuari nou <tt>nomNou</tt>
	 * @param nomActual nom d'usuari antic
	 * @param nomNou nom d'usuari nou
	 */
	public void reanomenaFitxerCamins(String nomActual, String nomNou) {
		File orig = new File(dataLocation + "Camins/dUsuari/" + nomActual + fileExtension);
		File nou = new File(dataLocation + "Camins/dUsuari/" + nomNou + fileExtension);
		orig.renameTo(nou);
	}
	
	/**
	 * Exporta la matriu que correspon al camí <tt>cami</tt>
	 * @param matrixLeft matriu esquerra a exportar
	 * @param matrixRight matriu dreta a exportar
	 * @param cami camí de la matriu
	 * @throws Exception pot retornar IOException
	 */
	public void exportarMatrius(ArrayList<ArrayList<String>> matrixLeft, ArrayList<ArrayList<String>> matrixRight, String cami) throws Exception {
		exportar("Camins/Matrius/" + cami + "/left", matrixLeft);
		exportar("Camins/Matrius/" + cami + "/right", matrixRight);
	}
	
	/**
	 * Importa la matriu esquerra que correspon al camí <tt>cami</tt>
	 * @param cami camí de la matriu
	 * @return matriu esquerra del camí <tt>cami</tt>
	 * @throws Exception pot retornar IOException
	 */
	public ArrayList<ArrayList<String>> importarMatriuLeft(String cami) throws Exception {
		return importar("Camins/Matrius/" + cami + "/left");
	}
	
	/**
	 * Importa la matriu dreta que correspon al camí <tt>cami</tt>
	 * @param cami camí de la matriu
	 * @return matriu dreta del camí <tt>cami</tt>
	 * @throws Exception pot retornar IOException
	 */
	public ArrayList<ArrayList<String>> importarMatriuRight(String cami) throws Exception {
		return importar("Camins/Matrius/" + cami + "/right");
	}
	
	/**
	 * Esborra les matrius desades que corresponguin a un camí que contingui <tt>c</tt>
	 * @param c caràcter a buscar
	 */
	public void esborrarMatrius(char c) {
		File carpetaMatrius = new File(dataLocation + "Camins/Matrius/");
		carpetaMatrius.getParentFile().mkdirs();
		carpetaMatrius.mkdir();
		File[] matrius = carpetaMatrius.listFiles();
		for (File carpeta : matrius) {
			if (carpeta.isDirectory() && carpeta.getName().indexOf(c) != -1) {
				for (File f : carpeta.listFiles()) f.delete();
				carpeta.delete();
			}
		}
	}
	
	public boolean existeixenMatrius(String cami) {
		File f = new File(dataLocation + "Camins/Matrius/" + cami);
		if(f.exists() && f.isDirectory()) {
			return true;
		}
		return false;
	}
}
