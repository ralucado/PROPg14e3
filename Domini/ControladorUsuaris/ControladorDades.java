package ProvaCU;

import java.util.*;

public class ControladorDades {
	private static ControladorDades instancia = null;
	private ControladorDades(){}
	
	public static synchronized ControladorDades getInstance(){
		if(instancia==null){
			instancia = new ControladorDades();
		}
		return instancia;
	}
	
	public synchronized String[][] caminsUsuari(String Usuari){
		String[][] a = {
				{"cami1","APA","descr1"},
				{"cami2","APAPA","descr2"},
				{"cami3","APAPAPA","descr3"},
				{"cami4","APAPAPAPA","descr4"}
		};
		return a;
	}
	
	public String[][] caminsPredefinits(){
		String[][] a = {
				{"camiPred1","CPC","descrPred1"},
				{"camiPred2","CPCPC","descrPred2"},
				{"camiPred3","CPCPCPC","descrPred3"},
				{"camiPred4","CPCPCPCPC","descrPred4"}
		};
		return a;
	}
	
	public synchronized void  guardarCamins(ArrayList<String[]> camins, String usuari){
		
	}
	
	public synchronized void guardarUsuaris(String[][] usuaris){
		
	}
	
	public synchronized ArrayList<String> consultarUsuaris(){
		ArrayList<String> usuaris = new ArrayList<String>();
		usuaris.add("admin");
		usuaris.add("admin");
		usuaris.add("1");
		
		usuaris.add("usuari1");
		usuaris.add("usuari1");
		usuaris.add("0");
		
		usuaris.add("usuari2");
		usuaris.add("usuari2");
		usuaris.add("0");
		
		return usuaris;
	}
}
