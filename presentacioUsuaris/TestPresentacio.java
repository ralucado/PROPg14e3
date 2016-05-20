package presentacio;

import javax.swing.JOptionPane;

public class TestPresentacio {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater (
	      new Runnable() {
	          public void run() {
	        	  try {
	        		  @SuppressWarnings("unused")
					  CtrlPresentacio ctrlPresentacio = new CtrlPresentacio();
	        	  }
	        	  catch (Exception exc) {
	        		  String[] botons = {"D'acord"};
	      			  (new VistaDialog()).setDialog("No s'ha pogut iniciar sessió", "HOLA", botons, JOptionPane.WARNING_MESSAGE);
	        	  }
      	}});
	}
}
