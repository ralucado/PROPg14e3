package domini.camins;

import java.util.ArrayList;

/**
 * Extensió de ControladorCamins
 * @author Cristina Raluca Vijulie
 */
public class myCtrlCamins extends ControladorCamins {
	
	/**
	 * Crea un nou myCtrlCamins per a l'usuari <tt>usuari</tt>
	 * @param usuari nom de l'usuari
	 * @throws Exception ...
	 */
	public myCtrlCamins(String usuari) throws Exception {
		super(usuari);
	}
	/**
	 * Retorna un boolea en funcio de si existeix o no el cami identificat per nom
	 * @param nom: el nom del cami
	 * @return indica si el cami existeix o no
	 */
	public boolean existeixCami(String nom){
        try {
            ArrayList<String[]> predef = consultarCaminsPredefinits();
            ArrayList<String[]> propis = consultarCaminsUsuari();
            for(int i = 0; i < predef.size(); ++i){
                if (predef.get(i)[0] == nom) return true;
            }
            for(int i = 0; i < propis.size(); ++i){
                if (propis.get(i)[0] == nom) return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }
}
