package domini.usuaris;

import java.util.ArrayList;
import java.util.Set;

import persistencia.CtrlPersistencia;

/**
 * És el controlador d'usuaris.
 * @author Grup 14.2
 *
 */
public class CtrlUsuaris {
    
    protected Usuari usuari;
    protected ConjuntUsuaris conjuntUsuaris;
    
    private void carregarConjunt(ArrayList<ArrayList<String>> array) throws Exception{
        for (int i = 0; i < array.size(); ++i) {
            if (array.get(i).get(2).equals("0")) 
                conjuntUsuaris.afegirUsuari(array.get(i).get(0), array.get(i).get(1));
            else
                conjuntUsuaris.afegirAdmin(array.get(i).get(0), array.get(i).get(1));
        }
    }
    
    private void guardarConjunt(Set<String> set) throws Exception {
        CtrlPersistencia ctrlPersistencia = new CtrlPersistencia();
        ArrayList<ArrayList<String>> ret = new ArrayList<>();
        for (String s: set) {
            ArrayList<String> aux = new ArrayList<String>();
            aux.add(s);
            aux.add(conjuntUsuaris.consultarContrasenya(s));
            if (conjuntUsuaris.esAdmin(s)) aux.add("1");
            else aux.add("0");
            ret.add(aux);
        }
        ctrlPersistencia.exportarUsuaris(ret);        
    }
    
    /**
     * Crea un nou CtrlUsuaris i n'importa els usuaris des de fitxer
     * @throws Exception pot retornar IOException o si dels usuaris del fitxer ja s'ha afegit 
     */
    public CtrlUsuaris() throws Exception{
        CtrlPersistencia ctrlPersistencia = new CtrlPersistencia();
        usuari = new Usuari();
        conjuntUsuaris = new ConjuntUsuaris();
        carregarConjunt(ctrlPersistencia.importarUsuaris());
    }
    
    /**
     * Assigna l'usuari <tt>usuari</tt> al controlador
     * @param usuari usuari a assignar
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }
    
    /**
     * Retorna l'usuari assignat al controlador
     * @return usuari del controlador
     */
    public Usuari getUsuari() {
        return usuari;
    }
    
    /**
     * Assigna el conjunt d'usuaris <tt>conjuntUsuaris</tt> al controlador
     * @param conjuntUsuaris conjunt a assignar al controlador
     */
    public void setConjuntUsuaris(ConjuntUsuaris conjuntUsuaris) {
        this.conjuntUsuaris = conjuntUsuaris;
    }
    
    /**
     * Retorna el conjunt d'usuaris del controlador
     * @return conjunt del controlador
     */
    public ConjuntUsuaris getCjtUsuaris() {
        return conjuntUsuaris;
    }
    
    /**
     * Retorna l'usuari amb nom = <tt>nom</tt>
     * @param nom nom de l'usuari a consultar
     * @return usuari amb nom = <tt>nom</tt>
     * @throws Exception retorna excepció si el conjunt no conté cap usuari amb nom = <tt>nom</tt>
     */
    public Usuari carregarUsuari(String nom) throws Exception {
        return conjuntUsuaris.consultarUsuari(nom);
    }
    
    /**
     * Guarda el conjunt d'usuaris del controlador a fitxers
     * @throws Exception pot retornar IOException
     */
    public void guardarCjtUsuaris() throws Exception {
        guardarConjunt(conjuntUsuaris.consultarNoms());
    }
    
    /**
	 * Crea un usuari amb nom = <tt>nom</tt>, contrasenya = <tt>contrasenya</tt> i admin = <tt>false</tt>, i l'afegeix al conjunt del controlador.
	 * @param nom nom de l'usuari a crear
	 * @param contrasenya contrasenya de l'usuari a crear
	 * @throws Exception retorna excepció si el conjunt d'usuaris del controlador ja conté un usuari amb el mateix nom o si l'usuari del controlador no és administrador
	 */
    public void altaUsuari(String nom, String contrasenya) throws Exception {
        if (usuari.esAdmin()) {
            conjuntUsuaris.afegirUsuari(nom, contrasenya);
        }
        else
            throw new Exception("No tens permis per afegir un usuari");       
    }

    /**
	 * Crea un usuari amb nom = <tt>nom</tt>, contrasenya = <tt>contrasenya</tt> i admin = <tt>false</tt>, i l'afegeix al conjunt del controlador, augmentant el nº d'admins.
	 * @param nom nom de l'usuari a crear
	 * @param contrasenya contrasenya de l'usuari a crear
	 * @throws Exception retorna excepció si el conjunt d'usuaris del controlador ja conté un usuari amb el mateix nom o si l'usuari del controlador no és administrador
	 */
    public void altaAdmin(String nom, String contrasenya) throws Exception {
        if (usuari.esAdmin()) {
            conjuntUsuaris.afegirAdmin(nom, contrasenya);
    	}
        else
            throw new Exception("No tens permis per afegir un administrador");
    }

	/**
	 * Elimina del conjunt del controlador l'usuari nom = <tt>nom</tt> i en cas de ser Admin, disminueix el nº d'admins.
	 * @param nom nom de l'usuari a eliminar
	 * @throws Exception retorna excepció si el conjunt del controlador no conté cap usuari amb el mateix nom, si l'usuari és l'únic administrador del conjunt o si l'usuari del controlador no és administrador
	 */
    public void baixaUsuari(String nom) throws Exception{
        if (usuari.getNom().equals(nom) || usuari.esAdmin())
            conjuntUsuaris.eliminarUsuari(nom);
        else
            throw new Exception("No tens permis per donar de baixa un altre usuari");
    }
    
    /**
	 * Modifica el nom de l'usuari amb nom=nomActual per tal que nom=nomNou.
	 * @param nomActual nom actual de l'usuari
	 * @param nomNou nom nou de l'usuari
	 * @throws Exception retorna excepció si el conjunt del controlador no conté cap usuari amb nom = <tt>nomActual</tt> o si l'usuari del controlador no és administrador
	 */
    public void modificarNom(String nomActual, String nomNou) throws Exception{
        if (usuari.getNom().equals(nomActual) || usuari.esAdmin())
            conjuntUsuaris.modificarNom(nomActual, nomNou);
        else
            throw new Exception("No tens permis per mdificar el nom d'un altre usuari");
    }
    
    /**
	 * Modifica la contrasenya de l'usuari amb nom = <tt>nom</tt> per tal que contrasenya = <tt>nova_contra</tt>.
	 * @param nom nom de l'usuari a modificar
	 * @param novaContrasenya nova contrasenya de l'usuari
	 * @throws Exception retorna excepció si el conjunt del controlador no conté cap usuari amb nom = <tt>nom</tt> o si l'usuari del controlador no és administrador
	 */
    public void modificarContrasenya(String nom, String novaContrasenya) throws Exception{
        if (usuari.getNom().equals(nom) || usuari.esAdmin())
            conjuntUsuaris.modificarContrasenya(nom, novaContrasenya);
        else
            throw new Exception("No tens permis per mdificar la contrasenya d'un altre usuari");
    }
    
    /**
	 * Modifica l'usuari amb nom = <tt>nom</tt> per tal que admin = <tt>true</tt>.
	 * @param nom nom de l'usuari a modificar
	 * @throws Exception retorna excepció si el conjunt no conté cap usuari amb nom = <tt>nom</tt>, si l'usuari ja és administrador o si l'usuari del controlador no és administrador
	 */
    public void ferAdmin(String nom) throws Exception{
        if (usuari.esAdmin())
            conjuntUsuaris.ferAdmin(nom);
        else
            throw new Exception("No tens permis per donar permisos d'administrador");
    } 

    /**
	 * Retorna el nom i un booleà que indica si és admin. de l'usuari amb nom = <tt>nom</tt>
	 * @param nom nom de l'usuari a consultar
	 * @return nom i un booleà que indica si és admin. de l'usuari amb nom = <tt>nom</tt>
	 * @throws Exception retorna excepció si el conjunt del controlador no conté cap usuari amb nom = <tt>nom</tt>
	 */
    public ArrayList<String> consultarUsuari(String nom) throws Exception {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add(nom);
        if (conjuntUsuaris.esAdmin(nom)) ret.add("EsAdmin");
        else ret.add("NoEsAdmin");
        return ret;
    }
    
    /**
	 * Retorna un Set amb tots els noms dels usuaris del conjunt del controlador
	 * @return noms dels usuaris del conjunt del controlador
	 */
    public Set<String> consultarConjunt() {
        return conjuntUsuaris.consultarNoms(); 
    }
}
