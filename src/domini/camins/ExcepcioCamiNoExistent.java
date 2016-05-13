package domini.camins;

@SuppressWarnings("serial")
public class ExcepcioCamiNoExistent extends Exception{
	ExcepcioCamiNoExistent(String nom){
		super("No existeix un cami amb nom: "+nom);
	}
}
