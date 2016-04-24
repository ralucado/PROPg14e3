package ProvaCU;

import java.util.*;

public class DriverCU {

		public static void main(String[] args) {
				
			try{
				ControladorDades cd = ControladorDades.getInstance(); 
				ControladorUsuaris cu = new ControladorUsuaris(cd.consultarUsuaris());
				Scanner s = new Scanner(System.in);
				boolean menu = true;
				while(menu){
					System.out.print("#######################################################\n"
									+ "DRIVER ControladorCamins. Selecciona una de les següents proves:\n"
									+ " 1. logIn\n"
									+ " 2. logOut\n"
									+ " 3. Crear usuari\n"
									+ " 4. Esborrar usuari\n"
									+ " 5. Canviar contrasenya\n"
									+ " 6. Consultar tots els usuaris\n"
									+ " 7. Afegir camí d'usuari\n"
									+ " 8. Esborrar camí d'usuari\n"
									+ " 9. Modificar camí d'usuari\n"
									+ " 10. Consultar camí d'usuari\n"
									+ " 11. Consultar camí predefinit\n"
									+ " 12. Consultar tots els camins de l'usuari\n"
									+ " 13. Consultar tots els camins predefinits\n"
									+ " 14. Acabar la prova\n");
					
					
					int n = s.nextInt();
		
					switch (n) {
					case 1: 
							String usuari, contr;
							System.out.println("Escriu el teu usuari i la contrasenya\n");
							usuari = s.next();
							contr = s.next();
							cu.logIn(usuari, contr);
							break;
						
					case 2:
							cu.logOut();
							break;
					case 3: 
						System.out.println("Escriu el nom i la contrasenya de l'usuari que vols crear\n");
						
						String nomUsuari = s.next();
						String contrUsuari = s.next();
						cu.crearUsuari(nomUsuari, contrUsuari);

						break;
					case 4:
						System.out.println("Escriu la contrasenya del teu usuari com a confirmació per esborrar el compte\n");
						
						String contrUsuariEl = s.next();
						cu.esborrarUsuari(contrUsuariEl);
						break;
						
					case 5: 
						System.out.println("Escriu la contrasenya antiga com a confirmació i la contrasenya nova");
						
						String contrOld = s.next();
						String contrNew = s.next();
						cu.canviarContrasenya(contrOld, contrNew);
						break;
							
					case 6:
							String[][] usuaris = cu.consultarTotsUsuaris();
							for(int i = 0; i<usuaris.length;i++){
								for(int j = 0; j<usuaris[i].length; j++){
									System.out.println(usuaris[i][j]+" ");
								}
								System.out.println("\n");
							}
							break;
					case 7:
						System.out.println("Escriu el nom, path i descripció del camí que vols crear\n");
						
						String nomCami = s.next();
						String path = s.next();
						String descr = s.next();
						cu.afegirCami(nomCami,path,descr);

						break;
					case 8:
						System.out.println("Escriu el nom del camí que vols eliminar\n");
						
						String nomCamiEl = s.next();
						cu.esborrarCami(nomCamiEl);
						break;
					case 9:
						System.out.println("Escriu el nom del camí a modificar\n");
						String nomCamiMo = s.next();
						System.out.print("Selecciona un dels següents atributs a modificar:\n"
										+"1. Nom\n"
										+"2. Path\n"
										+"3. Descripció\n");
						
						int k = s.nextInt();
						String canvi = new String();
						switch(k){
						case 1:
							System.out.println("Escriu el nom que li vols ficar al camí\n");
							canvi = s.next();
							break;
						case 2:
							System.out.println("Escriu el path que li vols ficar al camí\n");
							canvi = s.next();
							break;
						case 3:
							System.out.println("Escriu la descripció que li vols ficar al camí\n");
							canvi = s.next();
							break;
						}
						cu.modificarCami(nomCamiMo, k-1, canvi);
						break;
					case 10:
						System.out.println("Escriu el nom del camí d'usuari que vols buscar\n");
						String nom = s.next();
						String[] cami = cu.consultarCamiUsuari(nom);
						System.out.print("    Nom: "+cami[0]+"\n"
										+"    Path: "+cami[1]+"\n"
										+"    Descripció: "+cami[2]+"\n\n");
						break;
					case 11:
						System.out.println("Escriu el nom del camí predefinit que vols buscar\n");
						String nomPredef = s.next();
						String[] camiPredef = cu.consultarCamiUsuari(nomPredef);
						System.out.print("    Nom: "+camiPredef[0]+"\n"
										+"    Path: "+camiPredef[1]+"\n"
										+"    Descripció: "+camiPredef[2]+"\n\n");
						break;
					case 12:
						ArrayList<String[]> caminsUsuari = cu.consultarCaminsUsuari();
						
						for(int i = 0; i<caminsUsuari.size();i++){
							System.out.print("Camí "+i+": \n"
										+"    Nom: "+caminsUsuari.get(i)[0]+"\n"
										+"    Path: "+caminsUsuari.get(i)[1]+"\n"
										+"    Descripció: "+caminsUsuari.get(i)[2]+"\n");
						}
						break;
					case 13:
						ArrayList<String[]> caminsPredefinits = cu.consultarCaminsPredefinits();
						
						for(int i = 0; i<caminsPredefinits.size();i++){
							System.out.print("Camí "+i+": \n"
										+"    Nom: "+caminsPredefinits.get(i)[0]+"\n"
										+"    Path: "+caminsPredefinits.get(i)[1]+"\n"
										+"    Descripció: "+caminsPredefinits.get(i)[2]+"\n\n");
						}
						break;
					case 14:
						menu = false;
						break;	
					}
					
				}
				s.close();
			}
			catch(Exception e){System.out.println(e.getMessage());
		}
	}
}
