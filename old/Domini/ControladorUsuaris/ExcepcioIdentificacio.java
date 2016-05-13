package ProvaCU;

public class ExcepcioIdentificacio extends Exception{
	public ExcepcioIdentificacio(){
		super("La identificacio de l'usuari no es correcte");
	}

	public ExcepcioIdentificacio(String string) {
		super(string);
	}
}
