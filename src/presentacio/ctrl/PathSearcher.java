package presentacio.ctrl;

import javax.swing.JOptionPane;

public class PathSearcher {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater (
	      new Runnable() {
	          public void run() {
	        	  try {
					  CtrlPresentacio ctrlPresentacio = new CtrlPresentacio();
					  ctrlPresentacio.openLogIn();
	        	  }
	        	  catch (Exception exc) {
	        		  String[] botons = {"D'acord"};
	      			  (new VistaDialog()).setDialog("No s'ha pogut iniciar sessió", exc.getMessage(), botons, JOptionPane.WARNING_MESSAGE);
	      			  
	        	  }
      	}});
	}
}
