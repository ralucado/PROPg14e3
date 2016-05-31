package presentacio.ctrl;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author Arnau Blanch Cortès
 *
 */
@SuppressWarnings("serial")
public class VistaDialog extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VistaDialog dialog = new VistaDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VistaDialog() {
		
	}
	
	public int setDialog(String title, String text, String[] buttons, int type) {
		
		JOptionPane optionPane = new JOptionPane(text, type);
		optionPane.setOptions(buttons);
		JDialog dialogOptionPane = optionPane.createDialog(new JFrame(), title);
		dialogOptionPane.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialogOptionPane.pack();
		dialogOptionPane.setVisible(true);
		optionPane.getOptions();
		
		String vsel = (String) optionPane.getValue();
		int isel;
		for (isel = 0; isel < buttons.length && !buttons[isel].equals(vsel); isel++);
		
		return isel;
	}
}
