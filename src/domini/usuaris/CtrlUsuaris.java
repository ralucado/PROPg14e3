package domini.usuaris;

import persistencia.*;
import java.util.*;

public class CtrlUsuaris {
    
    private Usuari usuari;
    private ConjuntUsuaris conjuntUsuaris;
    
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
            ArrayList<String> aux = new ArrayList();
            aux.add(s);
            aux.add(conjuntUsuaris.consultarContrasenya(s));
            if (conjuntUsuaris.esAdmin(s)) aux.add("1");
            else aux.add("0");
            ret.add(aux);
        }
        ctrlPersistencia.exportarUsuaris(ret);        
    }
    
    public CtrlUsuaris() throws Exception{
        CtrlPersistencia ctrlPersistencia = new CtrlPersistencia();
        usuari = new Usuari();
        conjuntUsuaris = new ConjuntUsuaris();
        carregarConjunt(ctrlPersistencia.importarUsuaris());
    }
    
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }
    
    public Usuari getUsuari() {
        return usuari;
    }
    
    public void setConjuntUsuaris(ConjuntUsuaris conjuntUsuaris) {
        this.conjuntUsuaris = conjuntUsuaris;
    }
    
    public ConjuntUsuaris getCjtUsuaris() {
        return conjuntUsuaris;
    }
    
    public Usuari carregarUsuari(String nom) throws Exception {
        return conjuntUsuaris.consultarUsuari(nom);
    }
    
    public void guardarCjtUsuaris() throws Exception {
        guardarConjunt(conjuntUsuaris.consultarNoms());
    }

    public void altaUsuari(String nom, String contrasenya) throws Exception {
        if (usuari.esAdmin()) {
            conjuntUsuaris.afegirUsuari(nom, contrasenya);
        }
        else
            throw new Exception("No tens permis per afegir un usuari");       
    }

    public void altaAdmin(String nom, String contrasenya) throws Exception {
        if (usuari.esAdmin())
            conjuntUsuaris.afegirAdmin(nom, contrasenya);
        else
            throw new Exception("No tens permis per afegir un administrador");
    }

    public void baixaUsuari(String nom) throws Exception{
        if (usuari.getNom().equals(nom) || usuari.esAdmin())
            conjuntUsuaris.eliminarUsuari(nom);
        else
            throw new Exception("No tens permis per donar de baixa un altre usuari");
    }
    
    public void modificarNom(String nomActual, String nomNou) throws Exception{
        if (usuari.getNom().equals(nomActual) || usuari.esAdmin())
            conjuntUsuaris.modificarNom(nomActual, nomNou);
        else
            throw new Exception("No tens permis per mdificar el nom d'un altre usuari");
    }
    
    public void modificarContrasenya(String nom, String novaContrasenya) throws Exception{
        if (usuari.getNom().equals(nom) || usuari.esAdmin())
            conjuntUsuaris.modificarContrasenya(nom, novaContrasenya);
        else
            throw new Exception("No tens permis per mdificar la contrasenya d'un altre usuari");
    }
    
    public void ferAdmin(String nom) throws Exception{
        if (usuari.esAdmin())
            conjuntUsuaris.ferAdmin(nom);
        else
            throw new Exception("No tens permis per donar permisos d'administrador");
    } 
    
    public ArrayList<String> consultarUsuari(String nom) throws Exception {
        ArrayList<String> ret = new ArrayList();
        ret.add(nom);
        if (conjuntUsuaris.esAdmin(nom)) ret.add("EsAdmin");
        else ret.add("NoEsAdmin");
        return ret;
    }
    
    public Set<String> consultarConjunt() {
        return conjuntUsuaris.consultarNoms(); 
    }

}
