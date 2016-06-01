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

@SuppressWarnings("serial")
public class VistaDetallsEntitat extends JDialog {

	private String tipusE;
	private String labelE;
	private String nomE;
	private String idE;


	private void initComponents(){
		
		
		//frame
		
		setBackground(SystemColor.control);
		setForeground(Color.BLACK);
		setSize(800, 150);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new MigLayout("", "[800px,grow]", "[30px,grow][30px,grow][30px,grow][30px,grow][30px,grow][30px,grow]"));
		
		//label label
		if(tipusE!="Terme"){	
			JLabel lblLabel = new JLabel("<html><b>La label de l'entitat és:  </b>"+labelE+"</html>");
			lblLabel.setBounds(255, 10, 136, 15);
			getContentPane().add(lblLabel, "cell 0 6,alignx left,aligny center");
		}
		
		//label nom
		JLabel lblNomDeLentitat = new JLabel("<html><b>El nom de l'entitat és:  </b>"+nomE+"</html>");
		lblNomDeLentitat.setBounds(30, 71, 136, 15);
		getContentPane().add(lblNomDeLentitat, "cell 0 0,alignx left,aligny center");
		
		//label tipus
		JLabel lblTipusDentitat = new JLabel("<html><b>L'entitat és del tipus:  </b>"+tipusE+"</html");
		lblTipusDentitat.setBounds(30, 10, 136, 15);
		getContentPane().add(lblTipusDentitat, "cell 0 4,alignx left,aligny center");
		
		//label id
		if(idE!=null){
			JLabel lblId = new JLabel("<html><b>L'Id de l'entitat és:  </b>"+idE+"</html>");
			getContentPane().add(lblId, "cell 0 2");
		}
		
		//boto cancelar
		JButton cancelButton = new JButton("D'acord");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(cancelButton, "cell 0 6,alignx left,aligny center");
	}
	
	
	/**
	 * Creadora de VistaDetallsEntitat
	 * @param owner Frame que obra la vista
	 * @param id Id de l'entitat
	 * @param nom Nom de l'entitat
	 * @param label Label de l'entita
	 * @param tipus Tipus de l'entitat
	 */
	public VistaDetallsEntitat(JFrame owner, String id, String nom, String label, String tipus) {
		super(owner, true);
		this.setTitle("Detalls de l'entitat");
		
		this.nomE = nom;
		this.labelE = label;
		this.tipusE = tipus;
		this.idE = id;
		initComponents();
		this.setSize(500, 300);
		setVisible(true);
		setLocationRelativeTo(owner);
	}
}
