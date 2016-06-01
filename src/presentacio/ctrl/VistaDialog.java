package presentacio.ctrl;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * És la vista per fer diàlegs per tractament d'excepcions, avisos o preguntes Sí/No, entre d'altres usos.
 * @author Arnau Blanch Cortès
 *
 */
@SuppressWarnings("serial")
public class VistaDialog extends JDialog {

	/**
	 * Crea una nova VistaDialog buida.
	 */
	public VistaDialog() {
		
	}
	
	/**
	 * Emplena els elements del VistaDialog a partir dels paràmetres i retorna quin botó se selecciona.
	 * @param title títol del diàleg
	 * @param text text del diàleg
	 * @param buttons botons del diàleg
	 * @param type tipus de diàleg
	 * @return posició del botó que s'ha premut
	 */
	public int setDialog(String title, String text, String[] buttons, int type) {
		
		JOptionPane optionPane = new JOptionPane(text, type);
		optionPane.setOptions(buttons);
		JDialog dialogOptionPane = optionPane.createDialog(new JFrame(), title);
		dialogOptionPane.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialogOptionPane.pack();
		dialogOptionPane.setVisible(true);
		optionPane.getOptions();
		
		String vsel = String.valueOf(optionPane.getValue());
		int isel;
		for (isel = 0; isel < buttons.length && !buttons[isel].equals(vsel); isel++);
		
		return isel;
	}
}
