package presentacio.ctrl;

import javax.swing.JOptionPane;
import domini.ctrl.myCtrlDomini;
import presentacio.camins.VistaCamins;
import presentacio.graf.VistaGestioGraf;
import presentacio.queries.VistaQuery;
import presentacio.usuaris.VistaIniciSessio;
import presentacio.usuaris.VistaUsuaris;

/**
 * 
 * @author Cristina Raluca Vijulie
 *
 */
public class CtrlPresentacio {
	private myCtrlDomini ctrlDomini;
	private VistaUsuaris vUsuaris;
	private VistaIniciSessio vIniciSessio;
	private VistaMenu vMenu;
	private VistaCamins vCamins;
	private VistaGestioGraf vGraf;
	private VistaQuery vQuery;
 	
	public CtrlPresentacio() {
		try {
			ctrlDomini = new myCtrlDomini();
		}
		catch (Exception exc) {
			VistaDialog dialog = new VistaDialog();
			String[] botons = {"D'acord"};
			dialog.setDialog("Error al iniciar programa", exc.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public myCtrlDomini getDomini() {
		return ctrlDomini;
	}
	
	public void openLogIn() {
		if (vIniciSessio == null)
			vIniciSessio = new VistaIniciSessio(this);
		else {
			System.out.println("Reset");
			vIniciSessio.reset();
			vIniciSessio.fesVisible();
		}
	}
	
	public void openUsuaris() {
		try {
			if (ctrlDomini.esAdmin()) {
				if (vUsuaris == null) vUsuaris = new VistaUsuaris(this);
				else vUsuaris.fesVisible();
			}
			else {
				VistaDialog dialog = new VistaDialog();
				String[] botons = {"D'acord"};
				dialog.setDialog("Error al iniciar programa", "No tens permís per accedir-hi!", botons, JOptionPane.WARNING_MESSAGE);
				try {
					ctrlDomini.logOut();
				}
				catch (Exception exc2) {
					VistaDialog dialog2 = new VistaDialog();
					String[] botons2 = {"D'acord"};
					dialog2.setDialog("Error al iniciar programa", exc2.getMessage(), botons2, JOptionPane.WARNING_MESSAGE);
				}
				openLogIn();
			}
		}
		catch (Exception exc) {
			VistaDialog dialog = new VistaDialog();
			String[] botons = {"D'acord"};
			dialog.setDialog("Error en obrir la finestra de gestió d'usuaris", exc.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
		}
	}

	public void openMenu() {
		try{
			if (vMenu == null) vMenu = new VistaMenu(this);
			else vMenu.fesVisible();

		} catch (Exception e) {
			VistaDialog dialog = new VistaDialog();
			String[] botons = {"D'acord"};
			dialog.setDialog("Error al iniciar programa", e.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
		}
	}

	public void openCamins() {
		try{
			vCamins = new VistaCamins(this);
			vCamins.fesVisible();

		} catch (Exception e) {
			VistaDialog dialog = new VistaDialog();
			String[] botons = {"D'acord"};
			dialog.setDialog("Error en obrir la finestra de gestió de camins", e.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void openGraf(){
		try{
			vGraf = new VistaGestioGraf(this);
			vGraf.fesVisible();
		}
		catch(Exception e){
			VistaDialog dialog = new VistaDialog();
			String[] botons = {"D'acord"};
			dialog.setDialog("Error en obrir la finestra de gestió de graf", e.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void openQuery(){
		try{
			if (vQuery == null) vQuery = new VistaQuery(this);
			else vQuery.fesVisible();
		}
		catch(Exception e){
			VistaDialog dialog = new VistaDialog();
			String[] botons = {"D'acord"};
			dialog.setDialog("Error en obrir la finestra de consultes", e.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
		}
	}
}
