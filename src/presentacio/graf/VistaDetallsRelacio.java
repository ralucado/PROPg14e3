package presentacio.graf;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

/**
 * És la vista que mostra els detalls d'una relació entre entitats.
 * @author Marc Villanueva Cuadrench
 *
 */
@SuppressWarnings("serial")
public class VistaDetallsRelacio extends JDialog {

	private String nomE1;
	private String nomE2;
	private String tipusE1;
	private String tipusE2;
	private String idE1;
	private String idE2;


	private void initComponents(){
		
		//frame
		
		setBackground(SystemColor.control);
		setForeground(Color.BLACK);
		setSize(800, 150);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new MigLayout("", "[800px,grow]", "[30px,grow][30px,grow][30px,grow][30px,grow][30px,grow][30px,grow][30px, grow]"));
		
		//label nom1
		JLabel lblLabel = new JLabel("<html><b>El nom de l'entitat 1 és:</b>  "+nomE1+"</html>");
		lblLabel.setBounds(255, 10, 136, 15);
		getContentPane().add(lblLabel, "cell 0 0,alignx left,aligny center");
		
		JLabel lbllaIdDe = new JLabel("<html><b>La ID de l'entitat 1 és:</b>"+idE1+"</html>");
		getContentPane().add(lbllaIdDe, "cell 0 1");

		//label nom2
		JLabel lblNomDeLentitat = new JLabel("<html><b>El nom de l'entitat 2 és:  </b>"+nomE2+"</html>");
		lblNomDeLentitat.setBounds(30, 71, 136, 15);
		getContentPane().add(lblNomDeLentitat, "cell 0 4,alignx left,aligny center");
		
		//label tipus1
		JLabel lblTipusDentitat = new JLabel("<html><b>L'entitat 1 és del tipus:  </b>"+tipusE1+"</html>");
		lblTipusDentitat.setBounds(30, 10, 136, 15);
		getContentPane().add(lblTipusDentitat, "cell 0 2,alignx left,aligny center");
		
		JLabel lbllaIdDe_1 = new JLabel("<html><b>La ID de l'entitat 2 és:</b>"+idE2+"</html>");
		getContentPane().add(lbllaIdDe_1, "cell 0 5");
		
		//label nom2
		JLabel lblId = new JLabel("<html><b>L'entitat 2 és del tipus:  </b>"+tipusE2+"</html>");
		getContentPane().add(lblId, "cell 0 6,alignx left, aligny center");
		
		//boto cancelar
		JButton cancelButton = new JButton("D'acord");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(cancelButton, "cell 0 7,alignx left,aligny center");
	}
	
	
	/**
	 * Creadora de VistaDetallsRelacio
	 * @param ctrl Controlador de presentació
	 * @param owner Frame que obra la vista 
	 * @param nom1 Nom de l'entitat 1
	 * @param nom2 Nom de l'entitat 2
	 * @param tipus1 Tipus de l'entitat 1 
	 * @param tipus2 Tipus de l'entitat 2
	 * @param idE1 Id de l'entitat 1
	 * @param idE2 Id de l'entitat 2
	 */
	public VistaDetallsRelacio(JFrame owner, String nom1, String nom2, String tipus1, String tipus2, String idE1, String idE2) {
		super(owner, true);
		this.setTitle("Detalls de l'entitat");
		
		this.nomE1 = nom1;
		this.nomE2 = nom2;
		this.tipusE1 = tipus1;
		this.tipusE2 = tipus2;
		this.idE1 = idE1;
		this.idE2 = idE2;
		initComponents();
		this.setSize(500, 300);
		setVisible(true);
		setLocationRelativeTo(owner);
	}

}
