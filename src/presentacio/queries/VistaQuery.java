package presentacio.queries;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import presentacio.ctrl.CtrlPresentacio;
import presentacio.ctrl.VistaDialog;

public class VistaQuery implements ActionListener{
	private CtrlPresentacio ctrl;
	private JFrame frame;
	private JButton btnAfegirCami;
	
	public VistaQuery(CtrlPresentacio ctrl) throws Exception {
		this.ctrl = ctrl;
		init();
		frame.setVisible(true);
	}
	
	private void init() {
		init_frame();
		init_afegirCami();
	}
	
	private void init_frame() {
		frame = new JFrame();
		frame.setSize(600, 400);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				try {
					ctrl.getDomini().guardarCamins(ctrl.getDomini().getUsuariActual());
				} catch (Exception exc) {
					String[] botons = {"D'acord"};
					(new VistaDialog()).setDialog("No s'ha pogut desar el conjunt de camins", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
				}
				ctrl.openMenu();
			}
		});
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new MigLayout("", "[452px,grow]", "[][218px,grow]"));
	}
	
	private void init_afegirCami() {
		btnAfegirCami = new JButton("Afegir cami");
		btnAfegirCami.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		btnAfegirCami.addActionListener(this);
		frame.getContentPane().add(btnAfegirCami, "flowx,cell 0 0,alignx right");
	}
	
	public void fesVisible() {
		frame.setVisible(true);
	}

	public void setCami(String nom) {
		System.out.println(nom);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAfegirCami)){
			try {
				new VistaSeleccionarCami(ctrl, frame, this);
			} catch (Exception exc) {
				String[] botons = {"D'acord"};
				(new VistaDialog()).setDialog("No s'ha pogut obrir la finestra de seleccio", exc.getMessage(), botons, JOptionPane.ERROR_MESSAGE);
			}
			System.out.println("Addició acabada");
		}
	}

}
