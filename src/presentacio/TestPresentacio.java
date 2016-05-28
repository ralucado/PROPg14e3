package presentacio;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import com.jtattoo.plaf.fast.*;

public class TestPresentacio {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater (
	      new Runnable() {
	          public void run() {
	        	  try {
	        		  UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
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
