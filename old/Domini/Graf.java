package Domini;

import java.util.ArrayList;

public class Graf {
	
	public Graf(){
		
	}
	
	private ArrayList< ArrayList<Entitat> > pA;
	private ArrayList< ArrayList<Entitat> > pT;
	private ArrayList< ArrayList<Entitat> > pC;
	
	public void carregarGraf(){
		System.out.println("carregar graf");

	}
	
	public Entitat consultarEntitat(int id){
		System.out.println("consulta entitat");
		Entitat e = new Entitat("prova");
		return e;
	}
	
}
