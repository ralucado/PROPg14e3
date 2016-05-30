package domini.graf;

import java.util.*;

/**
 * Representa un graf (conjunt de dades) que conté entitats i les relacions entre elles
 */
public class Graf {

	private Map<Integer,Entitat> conjuntEntitats; //id,Entitat

    private Map<Integer,Integer> posicioAutor;  //id,posicio
    private Map<Integer,Integer> posicioPaper;
    private Map<Integer,Integer> posicioConferencia;
    private Map<Integer,Integer> posicioTerme;

    private Map<Integer,Integer> posicioAIdAutor; // Posicio,Id
    private Map<Integer,Integer> posicioAIdPaper;
    private Map<Integer,Integer> posicioAIdConferencia;
    private Map<Integer,Integer> posicioAIdTerme;

	private int nPapers;
    private int nConferencies;
    private int nAutors;
    private int nTermes;

    private Matriu paperAutor, paperConferencia, paperTerme;

    /**
     * Crea un nou Graf buit
     */
    public Graf() {
        conjuntEntitats =    new HashMap<Integer,Entitat>(); //id,Entitat

        posicioAutor =       new HashMap<Integer,Integer>(); //id,posicio
        posicioPaper =       new HashMap<Integer,Integer>(); //id,posicio
        posicioConferencia = new HashMap<Integer,Integer>(); //id,posicio
        posicioTerme =       new HashMap<Integer,Integer>(); //id,posicio

        posicioAIdAutor =       new TreeMap<Integer,Integer>();
        posicioAIdPaper =       new TreeMap<Integer,Integer>();  // Posicio,Id
        posicioAIdConferencia = new TreeMap<Integer,Integer>();
        posicioAIdTerme =       new TreeMap<Integer,Integer>();

        nPapers = nConferencies = nAutors = nTermes = 0;

        paperAutor = new Matriu(0);
        paperConferencia = new Matriu(0);
        paperTerme = new Matriu(0);
    }

    /**
     * Crea un nou Graf amb les entitats i relacions que es passen com a paràmetre
     * @param entitats entitats a afegir al graf
     * @param relacions relacions a afegir al graf
     */
    public Graf(TreeMap<Integer,Entitat> entitats, ArrayList<ArrayList<String>> relacions) { //Para cargarlo desde fichero
        conjuntEntitats = entitats;
        nPapers = nConferencies = nAutors = nTermes = 0;

        posicioAutor =       new HashMap<Integer,Integer>(); //id,posicio
        posicioPaper =       new HashMap<Integer,Integer>(); //id,posicio
        posicioConferencia = new HashMap<Integer,Integer>(); //id,posicio
        posicioTerme =       new HashMap<Integer,Integer>(); //id,posicio

        posicioAIdAutor =       new TreeMap<Integer,Integer>();
        posicioAIdPaper =       new TreeMap<Integer,Integer>();  // Posicio,Id
        posicioAIdConferencia = new TreeMap<Integer,Integer>();
        posicioAIdTerme =       new TreeMap<Integer,Integer>();

        paperAutor = new Matriu(0);
        paperConferencia = new Matriu(0);
        paperTerme = new Matriu(0);


        for (Map.Entry<Integer,Entitat> entry : entitats.entrySet()) {
            if (entry.getValue().isPaper()) {
                paperAutor.afegirFila();
                paperTerme.afegirFila();
                paperConferencia.afegirFila();
                posicioPaper.put(entry.getKey(), nPapers);
                posicioAIdPaper.put(nPapers, entry.getKey());
                ++nPapers;
            } else if (entry.getValue().isAutor()) {
                posicioAutor.put(entry.getKey(), nAutors);
                posicioAIdAutor.put(nAutors, entry.getKey());
                paperAutor.afegirColumna();
                ++nAutors;
            } else if (entry.getValue().isConferencia()) {
                posicioConferencia.put(entry.getKey(), nConferencies);
                posicioAIdConferencia.put(nConferencies, entry.getKey());
                paperConferencia.afegirColumna();
                ++nConferencies;
            } else if (entry.getValue().isTerme()) {
                posicioTerme.put(entry.getKey(), nTermes);
                posicioAIdTerme.put(nTermes, entry.getKey());
                paperTerme.afegirColumna();
                ++nTermes;
            }
        }
        int errors = 0;
        for (ArrayList<String> arrayList: relacions) {
            Integer id1 = Integer.parseInt(arrayList.get(0));
            Integer id2 = Integer.parseInt(arrayList.get(1));
            if (conjuntEntitats.containsKey(id1) && conjuntEntitats.containsKey(id2)) {
                Entitat e1 = conjuntEntitats.get(id1);
                Entitat e2 = conjuntEntitats.get(id2);
                if (((e1.isPaper()) && !(e2.isPaper())) ||
                        ((e2.isPaper()) && !(e1.isPaper()))) {
                    try {
                        if (e1.isPaper()) {
                            if (e2.isAutor()) {
                                paperAutor.afegirRelacio(posicioPaper.get(id1), posicioAutor.get(id2));
                            } else if (e2.isConferencia()) {
                                paperConferencia.afegirRelacio(posicioPaper.get(id1), posicioConferencia.get(id2));
                            } else if (e2.isTerme()) {
                                paperTerme.afegirRelacio(posicioPaper.get(id1), posicioTerme.get(id2));
                            }
                        } else { //e2 instanceof Paper
                            if (e1.isAutor()) {
                                paperAutor.afegirRelacio(posicioPaper.get(id2), posicioAutor.get(id1));
                            } else if (e1.isConferencia()) {
                                paperConferencia.afegirRelacio(posicioPaper.get(id2), posicioConferencia.get(id1));
                            } else if (e1.isTerme()) {
                                paperTerme.afegirRelacio(posicioPaper.get(id2), posicioTerme.get(id1));
                            }
                        }
                    } catch (Exception e) {
                       ++errors;
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
        if (errors > 0) System.out.println("La carrega desde fitxer ha finalitzat amb "+errors+" errors");
    }

    /**
     * Afegeix una entitat al graf
     * @param e entitat a afegir
     * @throws Exception ...
     */
	public void afegirEntitat(Entitat e) throws Exception {
        if (!existeixEntitat(e.getId())) {
            if (e.isPaper()) {
                if (nomDisponible(e.getNom(),"paper")) {
                    paperAutor.afegirFila();
                    paperTerme.afegirFila();
                    paperConferencia.afegirFila();
                    posicioPaper.put(e.getId(), nPapers);
                    posicioAIdPaper.put(nPapers, e.getId());
                    ++nPapers;
                }
                else {
                    throw new Exception ("El paper "+e.getNom()+ " ja existeix");
                }
            } else if (e.isAutor()) {
                if (nomDisponible(e.getNom(),"autor")){
                    paperAutor.afegirColumna();
                    posicioAutor.put(e.getId(), nAutors);
                    posicioAIdAutor.put(nAutors, e.getId());
                    ++nAutors;
                }
                else {
                    throw new Exception ("L'autor "+e.getNom()+ " ja existeix");
                }
            } else if (e.isTerme()) {
                if (nomDisponible(e.getNom(),"terme")) {
                    paperTerme.afegirColumna();
                    posicioTerme.put(e.getId(), nTermes);
                    posicioAIdTerme.put(nTermes, e.getId());
                    ++nTermes;
                }
                else {
                    throw new Exception ("El Terme "+e.getNom()+ " ja existeix");
                }
            } else if (e.isConferencia()) {
                if (nomDisponible(e.getNom(),"conferencia")) {
                    paperConferencia.afegirColumna();
                    posicioConferencia.put(e.getId(), nConferencies);
                    posicioAIdConferencia.put(nConferencies, e.getId());
                    ++nConferencies;
                }
                else {
                    throw new Exception ("La conferència "+e.getNom()+ " ja existeix");
                }
            }
            conjuntEntitats.put(e.getId(),e);
        }
        else {
            throw new Exception("L'identificador "+e.getId()+ " ja esta sent usat");
        }
	}

	/**
	 * Retorna si el graf conté una entitat amb l'identificador <tt>id</tt>
	 * @param id identificador d'entitat
	 * @return si el graf conté una entitat amb id = <tt>id</tt>, 'true'; altrament, 'false'
	 */
	public boolean existeixEntitat(int id) {
        return conjuntEntitats.containsKey(id);
    }

	/**
	 * Retorna l'entitat del graf amb id = <tt>id</tt>
	 * @param id identificador de l'entitat
	 * @return entitat amb id = <tt>id</tt>
	 * @throws Exception ...
	 */
	public Entitat consultarEntitat(int id) throws Exception {
		if (existeixEntitat(id)) return conjuntEntitats.get(id);
        else throw new Exception("L'entitat amb id " + id + " no existeix");
	}

	/**
	 * Elimina l'entitat amb id = <tt>id</tt> del graf
	 * @param id identificador d'entitat
	 * @throws Exception ...
	 */
	public void eliminarEntitat(int id) throws Exception {
		if (existeixEntitat(id)) {
            Entitat victima = conjuntEntitats.get(id);
            Integer idVictima = victima.getId();
            Integer posVictima;

            //System.out.println("Abans de l'esborrat: nPapers: "+nPapers+" nAutors: "+nAutors+" nConferencies: "+nConferencies+ "nTermes: "+nTermes);
            if (victima.isPaper() && nPapers > 0) {
/*
                System.out.println("PaperAutor abans de l'esborrat: ");
                paperAutor.imprimirEstat();

                System.out.println("PaperConferencia abans de l'esborrat");
                paperConferencia.imprimirEstat();

                System.out.println("PaperTerme abans de l'esborrat");
                paperTerme.imprimirEstat();*/


                posVictima = posicioPaper.get(id);
                paperAutor.esborrarFila(posVictima);
                paperConferencia.esborrarFila(posVictima);
                paperTerme.esborrarFila(posVictima);
                posicioPaper.remove(idVictima);
                posicioAIdPaper.remove(posVictima);
                actualitzarPosicions(posicioPaper,posVictima);
                actualitzarPosicionsMapaId(posicioAIdPaper,posVictima);
                --nPapers;

              /*  System.out.println("PaperAutor despres de l'esborrat: ");
                paperAutor.imprimirEstat();

                System.out.println("PaperConferencia abans de l'esborrat");
                paperConferencia.imprimirEstat();

                System.out.println("PaperTerme despres de l'esborrat");
                paperTerme.imprimirEstat();*/

            }
            else if (victima.isAutor() && nAutors > 0) {


                posVictima = posicioAutor.get(id);
                paperAutor.esborrarColumna(posVictima);
                posicioAutor.remove(idVictima);
                posicioAIdAutor.remove(posVictima);
                actualitzarPosicions(posicioAutor,posVictima);
                actualitzarPosicionsMapaId(posicioAIdAutor,posVictima);
                --nAutors;

            /*    System.out.println("PaperAutor despres de l'esborrat: ");
                paperAutor.imprimirEstat();*/
            }
            else if (victima.isConferencia() && nConferencies > 0) {

                /*System.out.println("PaperConferencia abans de l'esborrat");
                paperConferencia.imprimirEstat();*/


                posVictima = posicioConferencia.get(id);
                paperConferencia.esborrarColumna(posVictima);
                posicioConferencia.remove(idVictima);
                posicioAIdConferencia.remove(posVictima);
                actualitzarPosicions(posicioConferencia,posVictima);
                actualitzarPosicionsMapaId(posicioAIdConferencia,posVictima);
                --nConferencies;

             /*   System.out.println("PaperConferencia despres de l'esborrat");
                paperConferencia.imprimirEstat();*/

            }
            else if (victima.isTerme() && nTermes > 0) {

           /*     System.out.println("PaperTerme abans de l'esborrat");
                paperTerme.imprimirEstat();
*/
                posVictima = posicioTerme.get(id);
                paperTerme.esborrarColumna(posVictima);
                posicioTerme.remove(idVictima);
                posicioAIdTerme.remove(posVictima);
                actualitzarPosicions(posicioTerme,posVictima);
                actualitzarPosicionsMapaId(posicioAIdTerme,posVictima);
                --nTermes;
/*
                System.out.println("PaperTerme despres de l'esborrat");
                paperTerme.imprimirEstat();*/
            }
            conjuntEntitats.remove(id);
//            System.out.println("Despres de l'esborrat: nPapers: "+nPapers+" nAutors: "+nAutors+" nConferencies: "+nConferencies+ "nTermes: "+nTermes);

        }
		else {
            throw new Exception("L'entitat amb id "+ id +" no existeix"); //No se ha encontrado, y por lo tanto no se puede borrar
        }
	}

	/**
	 * Retorna si existeix al graf una relació entre les entitats amb identificador <tt>id1</tt> i <tt>id2</tt>
	 * @param id1 identificador d'entitat 1
	 * @param id2 identificador d'entitat 2
	 * @return si existeix la relació 'true'; altrament, 'false'
	 * @throws Exception ...
	 */
	public boolean existeixRelacio(int id1, int id2) throws Exception {
        if (existeixEntitat(id1)) {
            if (existeixEntitat(id2)) {
                Entitat e1 = conjuntEntitats.get(id1);
                Entitat e2 = conjuntEntitats.get(id2);
                if (((e1.isPaper()) && !(e2.isPaper())) ||
                        ((e2.isPaper()) && !(e1.isPaper()))) {
                    if (e1.isPaper()) {
                        if (e2.isAutor()) {
                            return paperAutor.contingut(posicioPaper.get(id1), posicioAutor.get(id2));
                        } else if (e2.isConferencia()) {
                            return paperConferencia.contingut(posicioPaper.get(id1), posicioConferencia.get(id2));
                        } else if (e2.isTerme()) {
                            return paperTerme.contingut(posicioPaper.get(id1), posicioTerme.get(id2));
                        }
                    } else { //e2.isPaper()
                        if (e1.isAutor()) {
                            return paperAutor.contingut(posicioPaper.get(id2), posicioAutor.get(id1));
                        } else if (e1.isConferencia()) {
                            return paperConferencia.contingut(posicioPaper.get(id2), posicioConferencia.get(id1));
                        } else if (e1.isTerme()) {
                            return paperTerme.contingut(posicioPaper.get(id2), posicioTerme.get(id1));
                        }
                    }
                }
                else throw new Exception("Paràmetres incorrectes, un i nomes un ha de ser un Paper");
            }
            else throw new Exception("L'entitat amb id " + id2 + "no existeix");
        }
        else throw new Exception("L'entitat amb id " + id1 + "no existeix");
        return false;
    }

	/**
	 * Afegeix al graf una relació entre les entitats amb identificador <tt>id1</tt> i <tt>id2</tt>
	 * @param id1 identificador d'entitat 1
	 * @param id2 identificador d'entitat 2
	 * @throws Exception ...
	 */
	public void afegirRelacio(int id1, int id2) throws Exception {
        if (existeixEntitat(id1)) {
            if (existeixEntitat(id2)) {
                Entitat e1 = conjuntEntitats.get(id1);
                Entitat e2 = conjuntEntitats.get(id2);
                if (((e1.isPaper()) && !(e2.isPaper())) ||
                        ((e2.isPaper()) && !(e1.isPaper()))) {
                    if (e1.isPaper()) {
                        if (e2.isAutor()) {
                            paperAutor.afegirRelacio(posicioPaper.get(id1), posicioAutor.get(id2));
                        } else if (e2.isConferencia()) {
                            paperConferencia.afegirRelacio(posicioPaper.get(id1), posicioConferencia.get(id2));
                        } else if (e2.isTerme()) {
                            paperTerme.afegirRelacio(posicioPaper.get(id1), posicioTerme.get(id2));
                        }
                    } else { //e2 instanceof Paper
                        if (e1.isAutor()) {
                            paperAutor.afegirRelacio(posicioPaper.get(id2), posicioAutor.get(id1));
                        } else if (e1.isConferencia()) {
                            paperConferencia.afegirRelacio(posicioPaper.get(id2), posicioConferencia.get(id1));
                        } else if (e1.isTerme()) {
                            paperTerme.afegirRelacio(posicioPaper.get(id2), posicioTerme.get(id1));
                        }
                    }
                }
                else throw new Exception("Paràmetres incorrectes, un i nomes un ha de ser un Paper");
            }
            else  throw new Exception("L'entitat amb id " + id2 + " no existeix");
        }
        else  throw new Exception("L'entitat amb id " + id1 + " no existeix");
    }

	/**
	 * Esborra del graf la relació entre les entitats amb identificador <tt>id1</tt> i <tt>id2</tt>
	 * @param id1 identificador d'entitat 1
	 * @param id2 identificador d'entitat 2
	 * @throws Exception ...
	 */
	public void esborrarRelacio(int id1, int id2) throws Exception {
        if (existeixEntitat(id1)) {
            if (existeixEntitat(id2)) {
                Entitat e1 = conjuntEntitats.get(id1);
                Entitat e2 = conjuntEntitats.get(id2);
                if (((e1.isPaper()) && !(e2.isPaper())) ||
                        ((e2.isPaper()) && !(e1.isPaper()))) {
                    if (e1.isPaper()) {
                        if (e2.isAutor()) {
                            paperAutor.esborrarRelacio(posicioPaper.get(id1),posicioAutor.get(id2));
                        }
                        else if (e2.isConferencia()) {
                            paperConferencia.esborrarRelacio(posicioPaper.get(id1),posicioConferencia.get(id2));
                        }
                        else if (e2.isTerme()) {
                            paperTerme.esborrarRelacio(posicioPaper.get(id1),posicioTerme.get(id2));
                        }
                    }
                    else { //e2 instanceof Paper
                        if (e1.isAutor()) {
                            paperAutor.esborrarRelacio(posicioPaper.get(id2),posicioAutor.get(id1));
                        }
                        else if (e1.isConferencia()) {
                            paperConferencia.esborrarRelacio(posicioPaper.get(id2),posicioConferencia.get(id1));
                        }
                        else if (e1.isTerme()) {
                            paperTerme.esborrarRelacio(posicioPaper.get(id2),posicioTerme.get(id1));
                        }
                    }
                }
                else {
                    throw new Exception("Paràmetres incorrectes, un i nomes un ha de ser un Paper");
                }

            }
            else {
                throw new Exception("L'entitat amb id " + id2 + "no existeix");
            }
        }
        else {
            throw new Exception("L'entitat amb id " + id1 + "no existeix");
        }
    }



    private void actualitzarPosicions(Map<Integer,Integer> mapa, int aPartirDe) {
        for (Map.Entry<Integer, Integer> e: mapa.entrySet()) {
            if (e.getValue() > aPartirDe) {
                mapa.put(e.getKey(),e.getValue()-1);
            }
        }
    }

    private void actualitzarPosicionsMapaId(Map<Integer,Integer> mapa, int aPartirDe) {
        Iterator<Integer> it = mapa.keySet().iterator();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        while (it.hasNext()) {
            Integer key = it.next();
            if (key > aPartirDe) {
                Integer contingut = mapa.get(key);
                arrayList.add(key);
                arrayList.add(contingut);
                it.remove();
            }
        }
        for (int i = 0; i < arrayList.size(); i = i+2) {
            mapa.put(arrayList.get(i)-1,arrayList.get(i+1));
        }
    }


    /**
     * Retorna el conjunt de relacions paper-autor
     * @return relacions paper-autor
     */
    public Map<Integer,HashSet<Integer>> getPaperAutor() {
        return paperAutor.getMatriu();
    }

    /**
     * Retorna el conjunt de relacions paper-conferència
     * @return relacions paper-conferència
     */
    public Map<Integer,HashSet<Integer>> getPaperConferencia() {
        return paperConferencia.getMatriu();
    }

    /**
     * Retorna el conjunt de relacions paper-terme
     * @return relacions paper-terme
     */
    public Map<Integer,HashSet<Integer>> getPaperTerme() {
        return paperTerme.getMatriu();
    }


    /**
     * Retorna una llista amb l'identificador de tots els autors i les seves posicions al graf
     * @return llista d'IDs i posicions d'autors
     */
    public Map<Integer, Integer> getPosicioAutor() {
        return posicioAutor;
    }

    /**
     * Retorna una llista amb l'identificador de tots els papers i les seves posicions al graf
     * @return llista de posicions i IDs de papers
     */
    public Map<Integer, Integer> getPosicioPaper() {
        return posicioPaper;
    }
    
    /**
     * Retorna una llista amb l'identificador de totes les conferències i les seves posicions al graf
     * @return llista d'IDs i posicions de conferències
     */
    public Map<Integer, Integer> getPosicioConferencia() {
        return posicioConferencia;
    }

    /**
     * Retorna una llista amb l'identificador de tots els termes i les seves posicions al graf
     * @return llista d'IDs i posicions de termes
     */
    public Map<Integer, Integer> getPosicioTerme() {
        return posicioTerme;
    }


    /**
     * Retorna una llista amb les posicions de tots els termes al graf i els seus identificadors
     * @return llista de posicions i IDs de termes
     */
    public Map<Integer, Integer> getPosicioAIdTerme() {return posicioAIdTerme;}

    /**
     * Retorna una llista amb les posicions de totes les conferències al graf i els seus identificadors
     * @return llista de posicions i IDs de conferències
     */
    public Map<Integer, Integer> getPosicioAIdConferencia() {return posicioAIdConferencia;}
    
    /**
     * Retorna una llista amb les posicions de tots els papers al graf i els seus identificadors
     * @return llista de posicions i IDs de papers
     */
    public Map<Integer, Integer> getPosicioAIdPaper() {return posicioAIdPaper;}

    /**
     * Retorna una llista amb les posicions de tots els autors al graf i els seus identificadors
     * @return llista de posicions i IDs d'autors
     */
    public Map<Integer, Integer> getPosicioAIdAutor() {return posicioAIdAutor;}

    /**
     * Retorna el conjunt d'entitats del graf
     * @return conjunt d'entitats
     */
    public Map<Integer, Entitat> getConjuntEntitats() {return conjuntEntitats;}

    /**
     * Retorna l'ID d'una entitat a partir del nom i el tipus
     * @param nomEntitat nom d'entitat
     * @param tipus tipus d'entitat
     * @return ID de l'entitat
     * @throws Exception ...
     */
    public int getIdByNameAndType(String nomEntitat, String tipus) throws Exception {
        for (Map.Entry<Integer, Entitat> e: conjuntEntitats.entrySet()) {
            if (e.getValue().getNom().equals(nomEntitat)) {
                if (
                        ((tipus.equals("autor") || tipus.equals("Autor")) && e.getValue().isAutor()) ||
                        ((tipus.equals("conferencia") || tipus.equals("Conferencia")) && e.getValue().isConferencia()) ||
                        ((tipus.equals("paper") || tipus.equals("Paper")) && e.getValue().isPaper()) ||
                        ((tipus.equals("terme") || tipus.equals("Terme")) && e.getValue().isTerme())
                        ) {
                    return e.getValue().getId();
                }
            }
        }
        throw new Exception("L'entitat de tipus " + tipus+ " i nom " + nomEntitat + " no existeix");
    }

    /**
     * Retorna si el graf ja té una entitat del tipus <tt>tipus</tt> amb el nom <tt>nomEntitat</tt> 
     * @param nomEntitat nom d'entitat
     * @param tipus tipus d'entitat
     * @return 'true' si ja existeix; altrament, 'false'
     */
    public boolean nomDisponible(String nomEntitat, String tipus) {
        for (Map.Entry<Integer, Entitat> e: conjuntEntitats.entrySet()) {
            if (e.getValue().getNom().equals(nomEntitat) && (
                    ((tipus.equals("autor") ||       tipus.equals("Autor"))       && e.getValue().isAutor()) ||
                    ((tipus.equals("conferencia") || tipus.equals("Conferencia")) && e.getValue().isConferencia())  ||
                    ((tipus.equals("paper") ||       tipus.equals("Paper"))       && e.getValue().isPaper())  ||
                    ((tipus.equals("terme") ||       tipus.equals("Terme"))       && e.getValue().isTerme())
            )) {
                return false;
            }
        }
        return true;
    }

    public int getIdByPositionPaper(int pos) throws Exception {
        if (pos < nPapers && pos >= 0) return posicioAIdPaper.get(pos);
        else throw new Exception("Posicio "+pos+" invalida a les matrius de Papers");
    }

    public int getIdByPositionAutor(int pos) throws Exception  {
       if (pos < nAutors && pos >= 0) return posicioAIdAutor.get(pos);
       else throw new Exception("Posicio "+pos+" invalida a la matriu paperAutor");
    }

    public int getIdByPositionConferencia(int pos) throws Exception  {
        if (pos < nConferencies && pos >= 0) return posicioAIdConferencia.get(pos);
        else throw new Exception("Posicio "+pos+" invalida a la matriu paperConferencia");
    }

    public int getIdByPositionTerme(int pos) throws Exception  {
        if (pos < nTermes && pos >= 0) return posicioAIdTerme.get(pos);
        else throw new Exception("Posicio "+pos+" invalida a la matriu paperTerme");
    }


    public int getPositionByIdPaper(int id) throws Exception  {
        if (existeixEntitat(id)) {
            if (consultarEntitat(id).isPaper()) return posicioPaper.get(id);
            else throw new Exception("L'entitat amb id "+id+ " no és un Paper");
        }
        else throw new Exception("No existeix cap entitat amb id "+id);
    }

    public int getPositionByIdAutor(int id) throws Exception  {
        if (existeixEntitat(id)) {
            if (consultarEntitat(id).isAutor()) return posicioAutor.get(id);
            else throw new Exception("L'entitat amb id "+id+ " no és un Autor");
        }
        else throw new Exception("No existeix cap entitat amb id "+id);
    }

    public int getPositionByIdConferencia(int id) throws Exception  {
        if (existeixEntitat(id)) {
            if (consultarEntitat(id).isConferencia()) return posicioConferencia.get(id);
            else throw new Exception("L'entitat amb id "+id+ " no és una Conferència");
        }
        else throw new Exception("No existeix cap entitat amb id "+id);
    }

    public int getPositionByIdTerme(int id) throws Exception  {
        if (existeixEntitat(id)) {
            if (consultarEntitat(id).isTerme()) return posicioTerme.get(id);
            else throw new Exception("L'entitat amb id "+id+ " no és un Terme");
        }
        else throw new Exception("No existeix cap entitat amb id "+id);
    }

    public int getnConferencies() {
        return nConferencies;
    }


    public int getnTermes() {
        return nTermes;
    }

    public int getnAutors() {
        return nAutors;
    }

    public int getnPapers() {
        return nPapers;
    }

    public void imprimirPaperAutor() {
        paperAutor.imprimirEstat();
    } //PARA EL DRIVER

    public void imprimirPaperConferencia() {
        paperConferencia.imprimirEstat();
    } //PARA EL DRIVER

    public void imprimirPaperTerme() {
        paperTerme.imprimirEstat();
    } //PARA EL DRIVER

    private class Matriu {

        private Integer files, columnes;
        private Map<Integer,HashSet<Integer>> matriuDispersa;

        public Map<Integer,HashSet<Integer>> getMatriuDispersa() {
            return matriuDispersa;
        }

        public Matriu(Integer columnes) {
			files = 0;
            this.columnes = columnes;
            matriuDispersa = new TreeMap<Integer,HashSet<Integer>>(); //int: fila
        }

        public void afegirFila() {
            matriuDispersa.put(files, new HashSet<Integer>());
            ++files;
        }

        public void afegirColumna() {
            ++columnes;
        }

		public void esborrarColumna(Integer c) throws Exception {
            if (c < columnes) {
                for (Map.Entry<Integer,HashSet<Integer>> entry : matriuDispersa.entrySet()) {
                    HashSet<Integer> s = entry.getValue();
                    for (int i = c; i < columnes-1; ++i) {
                        if (s.contains(i) && !s.contains(i+1)) s.remove(i);
                        else if (!s.contains(i) && s.contains(i+1)) s.add(i);
                    }
                    if (s.contains(columnes-1)) s.remove(columnes-1);
                }
                --columnes;
            }
            else throw new Exception("No existeix la columna "+c);
		}

		public void esborrarFila(Integer f) throws Exception {
            if (f < files && matriuDispersa.containsKey(f)) {
                matriuDispersa.remove(f);
                --files;
                ArrayList<Integer> keyses = new ArrayList<Integer>();
                ArrayList<HashSet<Integer>> conjs = new ArrayList<HashSet<Integer>>();
                for(Map.Entry<Integer,HashSet<Integer>> e : matriuDispersa.entrySet()) {
                    if (e.getKey() > f) {
                        keyses.add(e.getKey());
                        conjs.add(e.getValue());
                    }
                }
                for (Integer i : keyses) {
                    matriuDispersa.remove(i);
                }
                for (int i = 0; i < keyses.size(); ++i) {
                    matriuDispersa.put(keyses.get(i)-1,conjs.get(i));
                }
            }
            else throw new Exception("No existeix la fila "+f);
		}

        public void afegirRelacio(Integer f, Integer c) throws Exception{
            if (f < files && c < columnes) {
                if (!contingut(f,c)) {
                    matriuDispersa.get(f).add(c);
                }
                else {
                    throw new Exception("Les entitats ja estaven relacionades");
                }
            }
        }

        public void esborrarRelacio(Integer f, Integer c) throws Exception {
            if (f < files && c < columnes) {
                if (contingut(f,c)) matriuDispersa.get(f).remove(c);
                else throw new Exception("No es pot esborrar la relacio: les entitats no estaven relacionades");
            }
            else throw new Exception("Fora de rang");
        }

		public boolean contingut(Integer f, Integer c) throws Exception{
            if (f < files && c < columnes) {
                for (Integer i : matriuDispersa.get(f)) {
                    if (i.equals(c)) return true;
                }
                return false;
            }
            else throw new Exception("Fora de rang");
        }

        public Map<Integer,HashSet<Integer>> getMatriu() { return matriuDispersa; }

        public void imprimirEstat() {
            System.out.println("");
            System.out.println("----Contingut simbòlic----");
            System.out.println("");

            for (Map.Entry<Integer,HashSet<Integer>> e: matriuDispersa.entrySet()) {
                System.out.print("Fila "+e.getKey()+": ");
                for (int i = 0; i < columnes; ++i) {
                    if (e.getValue().contains(i)) System.out.print("1 ");
                    else System.out.print("0 ");
                }
                try {
                    System.out.print(" ("+consultarEntitat(getIdByPositionPaper(e.getKey())).getNom()+")");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.out.println("");
            }

            System.out.println("");
            System.out.println("----Contingut real----");
            System.out.println("");
            for (Map.Entry<Integer,HashSet<Integer>> entry : matriuDispersa.entrySet()) {
                System.out.print("Fila "+entry.getKey()+": ");
                for (Integer i : entry.getValue()) {
                    System.out.print(i+" ");
                }
                System.out.println("");
            }

            System.out.println("");
            System.out.println("----Mida simbòlica----");
            System.out.println("files: "+files+" columnes: "+columnes);
            System.out.println("----------------------");
        }

        public Integer getFiles() {
            return files;
        }

        public Integer getColumnes() {
            return columnes;
        }
	}
}
