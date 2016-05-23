package presentacio;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

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
