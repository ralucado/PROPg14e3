public class ControladorCamins {
	private ConjuntCamins usuari;
	private ConjuntCamins predefinits;
	
	
	public ControladorCamins(String usuari){
		ControladorDades CtrlDad = ControladorDades.getInstance();
		String[][] caminsUsuari = CtrlDad.caminsUsuari(usuari);
		String[][] caminsPredefinits = CtrlDad.caminsPredefinits();
		this.usuari = new ConjuntCamins();
		this.predefinits = new ConjuntCamins();
		
		for(int i = 0; i<caminsUsuari.length; i++){
			Cami c = new Cami();
			c.setNom(caminsUsuari[i][0]);
			c.setPath(caminsUsuari[i][1]);
			c.setDescr(caminsUsuari[i][2]);
			
			this.usuari.afegirCami(c);
		}
		
		for(int i = 0; i<caminsPredefinits.length; i++){
			Cami c = new Cami();
			c.setNom(caminsPredefinits[i][0]);
			c.setPath(caminsPredefinits[i][1]);
			c.setDescr(caminsPredefinits[i][2]);
			
			this.predefinits.afegirCami(c);
		}
	}
	
	public void AfegirCami(String nom, String path, String descr) throws Exception{
		if(!usuari.Existeix(nom)){
			Cami nouCami = new Cami();
			nouCami.setDescr(descr);
			nouCami.setNom(nom);
			nouCami.setPath(path);
			usuari.afegirCami(nouCami);
		}
		else{
			throw new Exception("Ja existeix un cami amb aquest nom: "+nom);
		}
	}
	
	public void EsborrarCami(String nom) throws Exception{
		if(usuari.Existeix(nom)){
			usuari.EliminarCami(nom);
		}
		else{
			throw new Exception("No existeix un cami amb aquest nom: "+nom);
		}
	}
	
	public String[] ConsultarCamiUsuari(String nom){
		String cami[] = new String[3];
		cami = usuari.ConsultarCami(nom);
		return cami;
	}
	
	public String[] ConsultarCamiPredefinit(String nom){
		String cami[] = new String[3];
		cami = predefinits.ConsultarCami(nom);
		return cami;
		
	}
	
	public void ModificarNom(String nom, String nomNou) throws Exception{
		if(usuari.Existeix(nom)) usuari.ModificarCami(nom, nomNou);		
		else{
			throw new Exception("No existeix un cami amb aquest nom "+nom);
		}
	}
	
	public void ModificarPath(String nom, String pathNou) throws Exception{
		if(usuari.Existeix(nom)) usuari.ModificarCami(nom, pathNou);	
		else{
			throw new Exception("No existeix un cami amb aquest nom: "+nom);
		}
	}
	
	public void ModificarDescr(String nom, String descrNou) throws Exception{
		if(usuari.Existeix(nom)) usuari.ModificarCami(nom, descrNou);
		else{
			throw new Exception("No existeix un cami amb aquest nom: "+nom);
		}
	}
	
	public String[][] ConsultarCaminsUsuari(){
		
		int numCamins = usuari.Mida();
		String[][] caminsUsuari = new String[numCamins][3];
		for(int i=0;i<numCamins;i++){
			caminsUsuari[i] = usuari.ConsultarCamiN(i);
		}
		return caminsUsuari;
	}
	
	public String[][] ConsultarCaminsPredefinits(){
		int numCamins = usuari.Mida();
		String[][] caminsPredefinits = new String[numCamins][3];
		for(int i=0;i<numCamins;i++){
			caminsPredefinits[i] = predefinits.ConsultarCamiN(i);
		}
		return caminsPredefinits;
	}
	
	public void guardarCamins(String nomUsuari){
		ControladorDades CtrlDad = ControladorDades.getInstance();
		String[][] caminsUsuari = new String[usuari.Mida()][3];
		
		for(int i = 0; i<usuari.Mida();i++){
			caminsUsuari[i] = usuari.ConsultarCamiN(i);
		}
		
		CtrlDad.guardarCamins(caminsUsuari, nomUsuari);
	}
}
