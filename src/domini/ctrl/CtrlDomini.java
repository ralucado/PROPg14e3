package domini.ctrl;

import domini.camins.*;
import domini.graf.*;
import domini.usuaris.*;
import java.util.ArrayList;
import java.util.Set;

public class CtrlDomini {
	
	protected Graf graf;
	
	protected Usuari usuari;
    protected ConjuntUsuaris conjuntUsuaris;
    
    protected ConjuntCamins caminsUsuari;
	protected ConjuntCamins caminsPredefinits;
	
	protected CtrlUsuaris ctrlUsuari;
	protected CtrlGraf ctrlGraf;
	protected ControladorCamins ctrlCamins;
	
	public CtrlDomini() throws Exception {
            
            ctrlUsuari = new CtrlUsuaris();
            ctrlGraf = new CtrlGraf();
		
            usuari = new Usuari();
            caminsUsuari = new ConjuntCamins();
            caminsPredefinits = new ConjuntCamins();
            
            conjuntUsuaris = ctrlUsuari.getCjtUsuaris();
            graf = ctrlGraf.carregarGraf();
            ctrlGraf.setGraf(graf);
            
	}
	
	public void logIn(String nom, String constrasenya) throws Exception {
            Usuari aux = ctrlUsuari.carregarUsuari(nom);
            if (aux.getContrasenya().equals(constrasenya)){
                usuari = aux;
                ctrlUsuari.setUsuari(usuari);
                ctrlCamins = new ControladorCamins(usuari.getNom());
                caminsUsuari = crearConjunt(ctrlCamins.consultarCaminsUsuari());
                caminsPredefinits = crearConjunt(ctrlCamins.consultarCaminsPredefinits());
            }
            else throw new Exception("Contrasenya incorrecta");
	}
	
	public void logOut() throws Exception {
            ctrlUsuari.guardarCjtUsuaris();
            ctrlGraf.guardarGraf();
            ctrlCamins.guardarCamins(usuari.getNom());
		
	}
	
	public void setUsuari(Usuari usuari) throws Exception {
            this.usuari = usuari;
            ctrlUsuari.setUsuari(usuari);
	}
	
	@SuppressWarnings("unused")
	private Usuari carregarUsuari(String nom) throws Exception {
            return ctrlUsuari.carregarUsuari(nom);
	}
	
	public ConjuntUsuaris carregarCjtUsuaris() throws Exception {	
            return ctrlUsuari.getCjtUsuaris();
	}
	
	public void guardarCjtUsuaris() throws Exception {
            ctrlUsuari.guardarCjtUsuaris();
	}
	
	public void altaUsuari(String nom, String contrasenya) throws Exception {
            ctrlUsuari.altaUsuari(nom, contrasenya);
	}
	
	public void altaAdmin(String nom, String contrasenya) throws Exception {
            ctrlUsuari.altaAdmin(nom, contrasenya);
	}
	
	public void baixaUsuari(String nom) throws Exception {
            ctrlUsuari.baixaUsuari(nom);
	}
	
	public void modificarNomUsuari(String nomActual, String nomNou) throws Exception {
            ctrlUsuari.modificarNom(nomActual, nomNou);
	}
	
	public void modificarContrasenya(String nom, String novaContrasenya) throws Exception {
            ctrlUsuari.modificarContrasenya(nom, novaContrasenya);
	}
	
	public void ferAdmin(String nom) throws Exception {
            ctrlUsuari.ferAdmin(nom);
	}
	
	public ArrayList<String> consultarUsuari(String nom) throws Exception {
            Usuari u1 = ctrlUsuari.carregarUsuari(nom);
            ArrayList<String> ret = new ArrayList<String>();
            ret.add(nom);
            if(conjuntUsuaris.esAdmin(nom)) ret.add("1");
            else ret.add("0");
            return ret;
	}
	
	public Set<String> consultarConjunt() throws Exception {
            return conjuntUsuaris.consultarNoms();
	}
		
	private void setGraf(Graf grafAux) throws Exception {
            this.graf = grafAux;
            ctrlGraf.setGraf(grafAux);
	}
	
	private Graf carregarGraf() throws Exception {
            return ctrlGraf.carregarGraf();
	}
	
	public void guardarGraf() throws Exception {
            ctrlGraf.guardarGraf();
	}
	
	public void altaAutor(String nom, String label) throws Exception {
            ctrlGraf.altaAutor(nom, label);
	}
	
	public void altaConferencia (String nom, String label) throws Exception{
            ctrlGraf.altaConferencia(nom, label);
	}
	
	public void altaPaper (String nom, String label) throws Exception{
            ctrlGraf.altaPaper(nom, label);
	}
	
	public void altaTerme (String nom) throws Exception{
            ctrlGraf.altaTerme(nom);
	}
	
	public void baixaAutor(String nom) throws Exception {
            ctrlGraf.baixaAutor(nom);
	}
	
	public void baixaPaper(String nom) throws Exception {
            ctrlGraf.baixaPaper(nom);
	}
	
	public void baixaConferencia(String nom) throws Exception {
            ctrlGraf.baixaConferencia(nom);
	}
	
	public void baixaTerme(String nom) throws Exception {
            ctrlGraf.baixaTerme(nom);
	}
	
	public ArrayList<String> consultarAutor(String nom) throws Exception{
            return ctrlGraf.consultarAutor(nom);
	}
	
	public ArrayList<String> consultarPaper(String nom) throws Exception{
            return ctrlGraf.consultarPaper(nom);
	}
	
	public ArrayList<String> consultarConferencia(String nom) throws Exception{
            return ctrlGraf.consultarConferencia(nom);
	}
	
	public String consultarLabel(String nom, String tipus) throws Exception{
            return ctrlGraf.consultarLabel(nom, tipus);
	}
	
	public ArrayList<ArrayList<String>> consultarConjuntEntitats() throws Exception {
            return ctrlGraf.consultarConjuntEntitats();
	}
	
	public ArrayList<String> consultarAutors() throws Exception {
            return ctrlGraf.consultarAutors();
	}
	
	public ArrayList<String> consultarPapers() throws Exception {
            return ctrlGraf.consultarPapers();
	}
	
	public ArrayList<String> consultarConferencies() throws Exception {
            return ctrlGraf.consultarConferencies();
	}
	
	public ArrayList<String> consultarTermes() throws Exception {
            return ctrlGraf.consultarTermes();
	}
	
	public void afegirRelacioAP (String nomA, String nomP) throws Exception {
            ctrlGraf.afegirRelacioAP(nomA, nomP);
	}
	
	public void afegirRelacioCP (String nomC, String nomP) throws Exception{
            ctrlGraf.afegirRelacioCP(nomC, nomP);
	}
	
	public void afegirRelacioTP (String nomT, String nomP) throws Exception{
            ctrlGraf.afegirRelacioTP(nomT, nomP);
	}
	
	public void esborrarRelacioAP (String nomA, String nomP) throws Exception{
            ctrlGraf.esborrarRelacioAP(nomA, nomP);
	}
	
	public void esborrarRelacioCP (String nomC, String nomP) throws Exception{
            ctrlGraf.esborrarRelacioAP(nomC, nomP);
	}
	
	public void esborrarRelacioTP (String nomT, String nomP) throws Exception{
            ctrlGraf.esborrarRelacioAP(nomT, nomP);
	}
	
	public void afegirCami(String nom, String path, String descr) throws Exception {
            ctrlCamins.afegirCami(nom, path, descr);
	}
	
	public void esborrarCami(String nom) throws Exception {
            ctrlCamins.esborrarCami(nom);
	}
	
	public String[] consultarCamiUsuari(String nom) throws Exception {
            return ctrlCamins.consultarCamiUsuari(nom);
	}
	
	public String[] consultarCamiPredefinit(String nom) throws Exception {
            return ctrlCamins.consultarCamiPredefinit(nom);
	}
	
	public void modificarNom(String nom, String nomNou) throws Exception {
            ctrlCamins.modificarNom(nom, nomNou);
	}
	
	public void modificarPath(String nom, String pathNou) throws Exception {
            ctrlCamins.modificarPath(nom, pathNou);
	}
	
	public void modificarDescr(String nom, String descrNou) throws Exception {
            ctrlCamins.modificarDescr(nom, descrNou);
	}
	
	public ArrayList<String[]> consultarCaminsUsuari() throws Exception {
            return ctrlCamins.consultarCaminsUsuari();
	}
	
	public ArrayList<String[]> consultarCaminsPredefinits() throws Exception {
            return ctrlCamins.consultarCaminsPredefinits();
	}
        
    public void guardarCamins(String nomUsuari) throws Exception {
            ctrlCamins.guardarCamins(nomUsuari);
	}
        
        private ConjuntCamins crearConjunt(ArrayList<String[]> camins) throws Exception {
            ConjuntCamins cc = new ConjuntCamins();
            for(int i = 0; i < camins.size(); ++i) {
                String nom = camins.get(i)[0];
                String path = camins.get(i)[1];
                String desc = camins.get(i)[2];
                Cami c = new Cami(nom, path, desc);
                cc.afegirCami(c);
            }
            return cc;
	}
}