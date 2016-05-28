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
	
	public boolean esAdmin() {
		return ctrlUsuari.getUsuari().esAdmin();
	}
	
	public String getUsuariActual() {
		return usuari.getNom();
	}
	
	public String getContrasenya(String user) throws Exception {
		return conjuntUsuaris.consultarContrasenya(user);
	}
}
