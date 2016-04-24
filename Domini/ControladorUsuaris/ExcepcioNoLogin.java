package ProvaCU;

public class ExcepcioNoLogin extends Exception{
	public ExcepcioNoLogin(){
		super("No s'ha fet logIn");
	}
}
