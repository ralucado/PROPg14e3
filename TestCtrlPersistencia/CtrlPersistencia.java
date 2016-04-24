package TestCtrlPersistencia;

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
			Scanner s2 = new Scanner(s.nextLine());
			while (s2.hasNext()) {
				String test = s2.next();
				linia.add(test);
			}
			t.add(linia);
			s2.close();
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
	 * Importa els papers 
	 * @return matriu d'strings amb l'id i el nom del paper
	 * @throws Exception ...
	 */
/*	public ArrayList<ArrayList<String>> importarPapers() throws Exception {
		return importar("Dades/Entitats/paper");
	}
	*/
	/**
	 * Importar les conferències
	 * @return matriu d'strings amb l'id i el nom de la conferència
	 * @throws Exception ...
	 */
	/*public ArrayList<ArrayList<String>> importarConferences() throws Exception {
		return importar("Dades/Entitats/conf");
	}
	*/
	/**
	 * Importar els autors 
	 * @return matriu d'strings amb l'id i el nom de l'autor
	 * @throws Exception ...
	 */
	/*public ArrayList<ArrayList<String>> importarAuthors() throws Exception {
		return importar("Dades/Entitats/author");
	}
	*/
	/**
	 * Importar els termes
	 * @return matriu d'strings amb l'id i el nom del term
	 * @throws Exception ...
	 */
	/*public ArrayList<ArrayList<String>> importarTerms() throws Exception {
		return importar("Dades/Entitats/term");
	}*/
	
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
	
	/**
	 * Importar les relacions paper-autor
	 * @return matriu d'strings amb l'id del paper i l'id de l'autor 
	 * @throws Exception ...
	 */
/*	public ArrayList<ArrayList<String>> importarPaperAuthor() throws Exception {
		return importar("Dades/Relacions/paper_author");
	}
	*/
	/**
	 * Importar les relacions paper-conferència
	 * @return matriu d'strings amb l'id del paper i l'id de la conferència
	 * @throws Exception ...
	 */
/*	public ArrayList<ArrayList<String>> importarPaperConference() throws Exception {
		return importar("Dades/Relacions/paper_conf");
	}*/
	
	/**
	 * Importar les relacions paper-terme
	 * @return matriu d'strings amb l'id del paper i l'id del terme
	 * @throws Exception ...
	 */
/*	public ArrayList<ArrayList<String>> importarPaperTerm() throws Exception {
		return importar("Dades/Relacions/paper_term");
	}*/
	
	// Graf (dades): labels
	
	/**
	 * Importar les labels dels papers
	 * @return matriu d'strings amb l'id del paper, la label [i el nom del paper]
	 * @throws Exception ...
	 */
/*	public ArrayList<ArrayList<String>> importarPaperLabel() throws Exception {
		return importar("Dades/Labels/paper_label");
	}
	*/
	/**
	 * Importar les labels de les conferències
	 * @return matriu d'strings amb l'id de la conferència, la label [i el nom de la conferència]
	 * @throws Exception ...
	 */
/*	public ArrayList<ArrayList<String>> importarConferenceLabel() throws Exception {
		return importar("Dades/Labels/conf_label");
	}*/
	
	/**
	 * Importar les labels dels autors
	 * @return matriu d'strings amb l'id de l'autor, la label [i el nom de l'autor]
	 * @throws Exception ...
	 */
	/*public ArrayList<ArrayList<String>> importarAuthorLabel() throws Exception {
		return importar("Dades/Labels/author_label");
	}*/
	
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
	 * Exporta el conjunt d'entitats
	 * @param papers matriu d'strings amb l'id i el nom de cada paper del conjunt
	 * @param authors matriu d'strings amb l'id i el nom de cada autor del conjunt
	 * @param conferences matriu d'strings amb l'id i el nom de cada conferència del conjunt
	 * @param terms matriu d'strings amb l'id i el nom de cada terme del conjunt
	 * @throws Exception ...
	 */
	/*public void exportarEntitats(ArrayList<ArrayList<String>> papers, ArrayList<ArrayList<String>> authors,
			ArrayList<ArrayList<String>> conferences, ArrayList<ArrayList<String>> terms) throws Exception {
		exportar("Dades/Entitats/paper", papers);
		exportar("Dades/Entitats/author", authors);
		exportar("Dades/Entitats/conf", conferences);
		exportar("Dades/Entitats/term", terms);
	}*/
	
	/**
	 * Exporta un conjunt d'entitats
	 * @param entitats matriu d'strings amb l'id, el nom i el label (opcional)
	 * @throws Exception
	 */
	public void exportarEntitats(ArrayList<ArrayList<String>> entitats) throws Exception {
		exportar("Dades/Entitats", entitats);
	}
	
	/**
	 * Exporta el conjunt de relacions
	 * @param pa matriu d'strings amb l'id del paper i l'id de l'autor de cada relació paper-autor
	 * @param pc matriu d'strings amb l'id del paper i l'id de la conferència de cada relació paper-conferència
	 * @param pt matriu d'strings amb l'id del paper i l'id del terme de cada relació paper-terme
	 * @throws Exception ...
	 */
	/*public void exportarRelacions(ArrayList<ArrayList<String>> pa, ArrayList<ArrayList<String>> pc,
			ArrayList<ArrayList<String>> pt) throws Exception {
		exportar("Dades/Relacions/paper", pa);
		exportar("Dades/Relacions/author", pc);
		exportar("Dades/Relacions/conf", pt);
	}*/
	
	/**
	 * Exporta un conjunt de relacions
	 * @param relacions matriu d'strings amb l'id del paper i de l'altra entitat
	 * @throws Exception ...
	 */
	public void exportarRelacions(ArrayList<ArrayList<String>> relacions) throws Exception {
		exportar("Dades/Relacions", relacions);
	}
	
	/**
	 * Exporta el conjunt de labels
	 * @param labelA matriu d'strings amb l'id, la label [i el nom] de cada autor amb label
	 * @param labelC matriu d'strings amb l'id, la label [i el nom] de cada conferència amb label
	 * @param labelP matriu d'strings amb l'id, la label [i el nom] de cada paper amb label
	 * @throws Exception ...
	 */
	/*public void exportarLabels(ArrayList<ArrayList<String>> labelA, ArrayList<ArrayList<String>> labelC, 
			ArrayList<ArrayList<String>> labelP) throws Exception {
		exportar("Dades/Labels/author_label", labelA);
		exportar("Dades/Labels/conf_label", labelC);
		exportar("Dades/Labels/paper_label", labelP);
	}*/

}
