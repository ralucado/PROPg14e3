package domini.ctrl;
import java.util.ArrayList;

import domini.usuaris.Usuari;
import persistencia.CtrlPersistencia;
import domini.queries.*;

public class myCtrlDomini extends CtrlDomini {
	private CtrlPersistencia persistencia;
	private ControladorQueries queries;
	
	public myCtrlDomini() throws Exception {
		super();
		persistencia = new CtrlPersistencia();
		queries = new ControladorQueries(ctrlGraf);
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
	
	public ControladorQueries getCtrlQueries(){
		return queries;
	}
}