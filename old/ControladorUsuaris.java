import java.util.HashMap;

public class ControladorUsuaris {
	
	private ControladorCamins CtrlCam;
	private ConjuntUsuaris usuaris;
	private String usuariAct;
	
	
	public ControladorUsuaris(HashMap<String, String> llistaUsuaris, HashMap<String,String> llistaAdmins){
		usuaris = new ConjuntUsuaris();
		for(String key : llistaUsuaris.keySet()){
			usuaris.afegirUsuari(key, llistaUsuaris.get(key));
		}
		
		for(String key : llistaAdmins.keySet()){
			usuaris.afegirAdmin(key, llistaUsuaris.get(key));
		}
	}
	
	public void LogIn(String usuari, String contr) throws Exception{
		if(usuaris.existeixUsuari(usuari)){
			if(comprovarContrasenya(usuari, contr)){
				CtrlCam = new ControladorCamins(usuari);
				usuariAct = usuari;
			}
			else{
				throw new Exception("La contrasenya no es correcte");
			}
		}
		else{
			throw new Exception("L'usuari no existeix");
		}
	}

	public void LogOut(){
		guardar();
		usuariAct = null;
		CtrlCam = null;
	}
	
	public void CrearUsuari(String usuari, String contr) throws Exception{
		if(!usuaris.existeixUsuari(usuari)){
			usuaris.afegirUsuari(usuari, contr);
		}
		else{
			throw new Exception("L'usuari ja existeix");
		}
	}
	
	public void EsborrarUsuari(String contr) throws Exception{
		if(comprovarContrasenya(usuariAct, contr)){
			usuaris.eliminarUsuari(usuariAct);
			LogOut();
		}
		else{
			throw new Exception("La contrasenya no es correcte");
		}
	}
	
	public void CanviarContrasenya(String ContrOld, String ContrNew){
		if(comprovarContrasenya(usuariAct,ContrOld)){			
			usuaris.modificarContrasenya(usuariAct, ContrNew);
		}
	}
	
	public String[][] ConsultarTotsUsuaris(){
		int mida = usuaris.Mida();
		String[][] TotsUsuaris = new String[mida][2];
		for(int i = 0; i<mida; i++){
			Usuari u = usuaris.consultaUsuariN(i);
			TotsUsuaris[i][0] = u.getNom();
			TotsUsuaris[i][1] = u.getContr();
		}
		return TotsUsuaris;
	}
	
	public void AfegirCami(String nom, String path, String descr) throws Exception{
		CtrlCam.AfegirCami(nom, path, descr);
	}
	
	public void EsborrarCami(String nom) throws Exception{
		CtrlCam.EsborrarCami(nom);
	}
	
	public void ModificarCami(String nom, int numCanvi, String canvi) throws Exception{
		if(numCanvi==0){
			CtrlCam.ModificarNom(nom, canvi);
		}
		else if(numCanvi==1){
			CtrlCam.ModificarPath(nom, canvi);
		}
		else if(numCanvi==2){
			CtrlCam.ModificarDescr(nom, canvi);
		}
	}
	
	public String[] ConsultarCami(String nom){
		String[] cami = new String[3];
		boolean esUsuari = true;
		
		if(esUsuari) cami = CtrlCam.ConsultarCamiUsuari(nom);
		else cami = CtrlCam.ConsultarCamiPredefinit(nom);
		
		return cami;
	}
	
	public void guardar(){
		ControladorDades CD = ControladorDades.getInstance();
		CtrlCam.guardarCamins(usuariAct);
		String[][] llistaUsuaris = new String[usuaris.Mida()][2];
		
		for(int i = 0; i<usuaris.Mida(); i++){
			Usuari u = usuaris.consultaUsuariN(i);
			llistaUsuaris[i][0] = u.getNom();
			llistaUsuaris[i][1] = u.getContr();
		}
		
		CD.guardarUsuaris(llistaUsuaris);
		
	}
	
	private boolean comprovarContrasenya(String usuari, String contr){
		if(usuaris.existeixUsuari(usuari)){
			String contrCorr = usuaris.consultarContrasenya(usuari);
			return contrCorr==contr;
		}
		else{
			return false;
		}
	}
	
}
