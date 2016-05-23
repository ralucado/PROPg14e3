package domini.ctrl;
import java.util.ArrayList;

import domini.usuaris.Usuari;
import persistencia.CtrlPersistencia;

public class myCtrlDomini extends CtrlDomini {
	private CtrlPersistencia persistencia;
	public myCtrlDomini() throws Exception {
		super();
		persistencia = new CtrlPersistencia();
	}

	public ArrayList<ArrayList<String>> getDadesUsuaris() {
		return ctrlUsuari.getDadesUsuaris();
	}
	
	public boolean esAdmin() {
		return ctrlUsuari.getUsuari().esAdmin();
	}
	
	public String getUsuariActual() {
		return usuari.getNom();
	}
	
	public void ferNoAdmin(String nom) throws Exception {
		ctrlUsuari.ferNoAdmin(nom);
	}
	
	public int getNumAdmins() {
		return conjuntUsuaris.getNb_admins();
	}
	
	@Override
	public ArrayList<String> consultarUsuari(String nom) throws Exception {
        Usuari u1 = ctrlUsuari.carregarUsuari(nom);
        ArrayList<String> ret = new ArrayList<String>();
        ret.add(nom);
        ret.add(u1.getContrasenya());
        if(u1.esAdmin()) ret.add("1");
        else ret.add("0");
        return ret;
}

	public boolean existeixUsuari(String username) {
		return ctrlUsuari.consultarConjunt().contains(username);
	}
	
	public void altaUsuari(String nom, String contrasenya) throws Exception {
        ctrlUsuari.altaUsuari(nom, contrasenya);
        persistencia.creaFitxerCamins(nom);
	}
	
	public void altaAdmin(String nom, String contrasenya) throws Exception {
	    ctrlUsuari.altaAdmin(nom, contrasenya);
	    persistencia.creaFitxerCamins(nom);
	}
	
	public void baixaUsuari(String nom) throws Exception {
        ctrlUsuari.baixaUsuari(nom);
        persistencia.esborraFitxerCamins(nom);
	}
	
	public void modificarNomUsuari(String nomActual, String nomNou) throws Exception {
	    ctrlUsuari.modificarNom(nomActual, nomNou);
	    persistencia.reanomenaFitxerCamins(nomActual, nomNou);
	}
}
