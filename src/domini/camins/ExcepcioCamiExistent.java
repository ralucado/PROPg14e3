package domini.camins;

@SuppressWarnings("serial")
public class ExcepcioCamiExistent extends Exception{
	public ExcepcioCamiExistent(String nom){
		super("Ja existeix un cami amb nom: "+nom);
	}
}
