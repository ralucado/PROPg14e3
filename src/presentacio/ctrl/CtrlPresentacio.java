package presentacio.ctrl;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.*;


import domini.ctrl.myCtrlDomini;
import presentacio.camins.VistaCamins;
import presentacio.graf.VistaGestioGraf;
import presentacio.queries.VistaQuery;
import presentacio.usuaris.VistaIniciSessio;
import presentacio.usuaris.VistaUsuaris;

/**
 * És el controlador de la capa de presentació.
 * @author Cristina Raluca Vijulie
 */
public class CtrlPresentacio {
	private myCtrlDomini ctrlDomini;
	private VistaUsuaris vUsuaris;
	private VistaIniciSessio vIniciSessio;
	private VistaMenu vMenu;
	private VistaCamins vCamins;
	private VistaGestioGraf vGraf;
	private VistaQuery vQuery;
 	
	/**
	 * Crea un nou CtrlPresentacio.
	 */
	public CtrlPresentacio() {
		try {
			ctrlDomini = new myCtrlDomini();

			try {
			    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }
			    }
			} catch (Exception e) {
			    // If Nimbus is not available, you can set the GUI to another look and feel.
			}
		}
		catch (Exception exc) {
			VistaDialog dialog = new VistaDialog();
			String[] botons = {"D'acord"};
			dialog.setDialog("Error al iniciar programa", exc.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Retorna el controlador de domini
	 * @return controlador de domini
	 */
	public myCtrlDomini getDomini() {
		return ctrlDomini;
	}
	
	/**
	 * Obre la vista d'inici de sessió.
	 */
	public void openLogIn() {
		if (vIniciSessio == null)
			vIniciSessio = new VistaIniciSessio(this);
		else {
			System.out.println("Reset");
			vIniciSessio.reset();
			vIniciSessio.fesVisible();
		}
	}
	
	/**
	 * Obre la vista de gestió d'usuaris.
	 */
	public void openUsuaris() {
		try {
			if (vUsuaris == null) vUsuaris = new VistaUsuaris(this);
			else vUsuaris.fesVisible();
		}
		catch (Exception exc) {
			VistaDialog dialog = new VistaDialog();
			String[] botons = {"D'acord"};
			dialog.setDialog("Error en obrir la finestra de gestió d'usuaris", exc.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Obre la vista del menú principal.
	 */
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

	/**
	 * Obre la vista de gestió de camins.
	 */
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
	
	/**
	 * Obre la vista de gestió del graf.
	 */
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
	
	/**
	 * Obre la vista per a realitzar queries.
	 */
	public void openQuery(){
		try{
			vQuery = new VistaQuery(this);
			vQuery.fesVisible();
		}
		catch(Exception e){
			VistaDialog dialog = new VistaDialog();
			String[] botons = {"D'acord"};
			dialog.setDialog("Error en obrir la finestra de consultes", e.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
		}
	}
}
