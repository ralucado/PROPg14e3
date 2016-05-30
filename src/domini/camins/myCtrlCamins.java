package domini.camins;

import java.util.ArrayList;

public class myCtrlCamins extends ControladorCamins {
	public myCtrlCamins(String usuari) throws Exception {
		super(usuari);
	}
	
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
