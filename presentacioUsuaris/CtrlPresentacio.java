package presentacio;

import javax.swing.JOptionPane;
import domini.ctrl.myCtrlDomini;

public class CtrlPresentacio {
	private myCtrlDomini ctrlDomini;
	private VistaUsuaris vUsuaris;
	private VistaIniciSessio vIniciSessio;
	
	public CtrlPresentacio() {
		try {
			ctrlDomini = new myCtrlDomini();
			openLogIn();
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
		else vIniciSessio.fesVisible();
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
			dialog.setDialog("Error al iniciar programa", exc.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
		}
	}
}