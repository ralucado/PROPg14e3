package domini.queries;
import java.util.*;

import domini.camins.Cami;
import domini.graf.Graf;
import domini.camins.SparseMatrixBool;
import domini.queries.SparseMatrix;
public class HeteSim{	

	//PUBLIC ARGUMENTS

	/**
	 * Crea un objecte HeteSim asociat al graf del parametre. Si el graf canvia s'ha de crear un nou objecte
	 * @param g es el graf al queal esta vinculat l'hetesim
	 */
	public HeteSim(Graf g) {
		graph = g;
		paperAuthor = paperConf = paperTerm = authorMid = confMid = termMid = false;
	}
	
	/**
	 *
	 * @param p es el cami per el queal calcular
	 * @return  retorna la matriu de resultats del hetesim per cada parell d'entitats final i inicial del path
	 */
	public SparseMatrix getHeteSim(Cami p){
		SparseMatrix pl = new SparseMatrix(), pr = new SparseMatrix();
		if(p.getMatrius().first != null){
			pl = new SparseMatrix(p.getMatrius().first);
			pr = new SparseMatrix(p.getMatrius().second);
		}
		else{
		ArrayList<EntitatType> left = null;
		ArrayList<EntitatType> right = null;
		Pair<ArrayList<EntitatType>, ArrayList<EntitatType>> aux = parsePath(p.getPath()); 
		left = aux.first;
		right = aux.second;
		Collections.reverse(right);
		//System.out.println("Normalitzant");
		pl = mutiplyMatrixes(  getMatrixesToMultiply(left,right));
		pr = mutiplyMatrixes(  getMatrixesToMultiply(right,left));
		p.setMatrius(pl, pr);
		}
		return normalizeHeteSim(pl, pr);
	}

	/**
	 * 
	 * @param p es el cami per el queal calcular
	 * @param n es l'entitat inicial per la qual volem calcular l'hetesim
	 * @return el resultat es el valor hetesim de cada entitat final relacionada amb la entitat inicial n seleccionada
	 */
	
	public ArrayList<Pair<Integer,Float>> getHeteSim(Cami p, int posEntitat){

		ArrayList<Pair<Integer,Float>> ret = new ArrayList<Pair<Integer,Float>>();
		SparseMatrix hete = getHeteSim(p);
		for(int j = 0; j < hete.getNCols(); ++j){

			if(hete.getRow(posEntitat).containsKey(j)){
				Pair<Integer,Float> aux = new Pair<Integer,Float>(j,hete.getValue(posEntitat,j));
				ret.add(aux);
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @param p es el cami per el queal calcular
	 * @param n1 es l'entitat inicial de la qual volem calcular l'hetesim
	 * @param n2 es l'entitat final de la qual volem calcular l'hetesim
	 * @return ens retorna el valor hetesim entre les dues entitats seleccionades per el path especificat
	 */
	
	public Float getHeteSim(Cami p, int posEntitat1, int posEntitat2){
		SparseMatrix hete = getHeteSim(p);
		return hete.getValue(posEntitat1, posEntitat2);
	}
	
	
	//PRIVATE ARGUMENTS
	
	protected boolean paperAuthor;
	protected SparseMatrix paper2author;
	protected SparseMatrix author2paper;
	
	protected boolean paperConf;
	protected SparseMatrix paper2conf;
	protected SparseMatrix conf2paper;
	
	protected boolean paperTerm;
	protected SparseMatrix paper2term;
	protected SparseMatrix term2paper;
	
	protected boolean authorMid;
	protected SparseMatrix author2mid;
	protected SparseMatrix paper2authorMid;
	
	protected boolean confMid;
	protected SparseMatrix conf2mid;
	protected SparseMatrix paper2confMid;
	
	protected boolean termMid;
	protected SparseMatrix term2mid;
	protected SparseMatrix paper2termMid;
	
		
	protected Graf graph;
	
	protected enum PathTypes {
		Author2Paper, Conf2Paper, Term2Paper, 
		Author2Mid, Paper2MidAut,
		Conf2Mid, Paper2MidConf,
		Term2Mid, Paper2MidTerm
	}
	
	protected enum EntitatType {
		Paper, Author, Conference, Term, E
	}
	class Partite {
		SparseMatrix leftToMid;
		SparseMatrix midToRight;
		Partite(SparseMatrix f,SparseMatrix s){
			leftToMid = f;
			midToRight = s;
		}
	}
	
	class WhatMatrix {
		boolean transposeMatrix;
		HeteSim.PathTypes pathType;
		WhatMatrix(boolean trans, HeteSim.PathTypes t) {
			this.transposeMatrix = trans;
			this.pathType = t;
		}
	}
	
	/**
	 * 
	 * @param leftSide part esquerra de la multiplicacio
	 * @param rightSide part dreta de la multiplicacio
	 * @return retorna el producte dels dos parametres
	 */
	protected SparseMatrix multiply(SparseMatrix leftSide, SparseMatrix rightSide) {
		System.out.println("Multiplicant");
		SparseMatrix result = new SparseMatrix();
		result.setSize(leftSide.getRows().size(), rightSide.getCols().size());
		for (int i = 0; i < leftSide.getRows().size(); ++i) {
			for (int j = 0; j < rightSide.getCols().size(); ++j) {
				leftSide.getRow(i);
				result.set(i,j, SparseVector.multiply(leftSide.getRow(i), rightSide.getCol(j)));
			}
		}
		return result;
	}

	/**
	 * 
	 * @param m es una matriu dispersa
	 * @return retorna la matriu U, es a dir, normalitzada per files 
	 */
	protected SparseMatrix normalizeRows(SparseMatrix m) {
		SparseMatrix result = new SparseMatrix();
		result.setSize(m.getNRows(),m.getNCols());
		
		for(Integer i = 0; i < m.getNRows(); ++i){
			Double total = 0.0;
			for(HashMap.Entry<Integer, Float> j: m.getRows().get(i).entrySet()){
				total += Math.pow(m.getValue(i,j.getKey()),2);
			}
			total = Math.sqrt(total);
			for(HashMap.Entry<Integer, Float> j: m.getRows().get(i).entrySet()) {
				result.set(i, j.getKey(),(float) (j.getValue()/total));
			}
		}
		return result;
	}

	
	/**
	 *  
	 * @param left es el producte de les matrius U de la meitat esquerra del path, equivalent a MPpl
	 * @param right es el producte de les matrius de la meitat dreta del path, equivalent a (MPpr-1)'
	 * @return SparseMatrix of HeteSims
	 */
	protected SparseMatrix normalizeHeteSim(SparseMatrix left, SparseMatrix right) {
		SparseMatrix result = new SparseMatrix();
		result.setSize(left.getNRows(),right.getNRows());
		System.out.println("Dins norm");
		for (int i = 0; i < result.getNRows(); ++i) {
			for (int j = 0; j < result.getNCols(); ++j) {
				//if (j%500 == 0) System.out.println("it - I: "+i+" - J: " + j);
				float top = SparseVector.multiply(left.getRow(i),right.getRow(j)); 	//agafem la fila enlloc de la columna de right
				double bot = left.getRow(i).norm()*right.getRow(j).norm();			//ja que esta transposada respecte a la formula del paper
				float div = (float)(top/bot);
				if (Math.abs(bot) < 0.000001) div =  0.f;
				if (div > 1) div = 1.f;
				else result.set(i, j,div);
				
			}
		}
		
		return result;
	}
	
	
	
	/**
	 * Transforma un string al seu equivalent en entitats afegint la entitat intermitja si escau
	 * @param path: es l'string format per 'A','P','C','T'
	 * @return .first representa Pl i .second Pr
	 */
	protected Pair<ArrayList<EntitatType>, ArrayList<EntitatType>> parsePath(String path){
		String left = null, right = null;
		
		if (path.length()%2 == 0){
			left = path.substring(0, path.length()/2) + "E";
			right = "E"+path.substring(path.length()/2, path.length());
		}
		else{
			left = path.substring(0, path.length()/2+1);
			right = path.substring(path.length()/2, path.length());
		}
		

		//System.out.println("left path: "+left+" right path: " + right);
		
		ArrayList<EntitatType> Pl = new ArrayList<EntitatType>();
		ArrayList<EntitatType> Pr = new ArrayList<EntitatType>();
		for(int i = 0; i < left.length(); ++i){
			switch(left.charAt(i)){
			case 'A':
				Pl.add(EntitatType.Author);
				break;
			case 'P':
				Pl.add(EntitatType.Paper);
				break;
			case 'T':
				Pl.add(EntitatType.Term);
				break;
			case 'C':
				Pl.add(EntitatType.Conference);
				break;
			case 'E':
				Pl.add(EntitatType.E);
				break;
			default:
				break;
				
			}
		}
		for(int i = 0; i < right.length(); ++i){
			switch(right.charAt(i)){
			case 'A':
				Pr.add(EntitatType.Author);
				break;
			case 'P':
				Pr.add(EntitatType.Paper);

				break;
			case 'T':
				Pr.add(EntitatType.Term);

				break;
			case 'C':
				Pr.add(EntitatType.Conference);

				break;
			case 'E':
				Pr.add(EntitatType.E);
				break;
			default:
				break;
			}
		}
		Pair<ArrayList<EntitatType>, ArrayList<EntitatType>> res = new Pair<ArrayList<EntitatType>,ArrayList<EntitatType>>(Pl, Pr);
		return res;
	}
	
	
	/**
	 * Normalitza les matrius especificades (W->U) i les multiplica, serveix per calcular PMpl i PMpr-1
	 * @param matrixesToMultiply Especifica les matrius W a multiplicar
	 * @return retorna el producte de les matrius, despres de normalitzarles
	 */
	protected SparseMatrix mutiplyMatrixes(ArrayList<SparseMatrix> matrixesToMultiply) {
		System.out.println("Multipliquem matrius");
		SparseMatrix ret = matrixesToMultiply.get(0);
		for (int i = 1; i < matrixesToMultiply.size(); ++i) {
			ret = multiply(ret,normalizeRows(matrixesToMultiply.get(i)));
		}
		System.out.println("Multiplicades!");
		return ret;
	}
	
	/**
	 * Construeix la matriu intermitja (objecte E)
	 * @param SparseMatrix la matriu que desitjem expandir
	 * @return la matriu E asociada al parametre
	 */
	protected Partite partiteMatrix(SparseMatrix SparseMatrix) {
		SparseMatrix thingA2Mid = new SparseMatrix();
		SparseMatrix mid2ThingB = new SparseMatrix();
		int total = 0;
		for (int i = 0; i < SparseMatrix.getNRows(); ++i) {
			for (int j = 0; j < SparseMatrix.getNCols(); ++j) {
				total += Math.round((Float) SparseMatrix.getValue(i, j));		
			}
		}
		thingA2Mid.setSize(SparseMatrix.getNRows(), total);
		mid2ThingB.setSize(total, SparseMatrix.getNCols());
		int index = 0;
		for (int i = 0; i < SparseMatrix.getNRows(); ++i) {
			for (int j = 0; j < SparseMatrix.getNCols(); ++j) {
				if ((float) SparseMatrix.getValue(i, j) == 1.f) {
					thingA2Mid.set(i,index,1.f);
					mid2ThingB.set(index,j, 1.f);
					index += 1;
				}
			}
		}
		return new Partite(thingA2Mid,mid2ThingB);
	}
	
	
	/**
	 * Donades les dues meitats del cami, crea una llista amb les matrius W que shan de multiplicar posteriorment
	 * @param path primera meitat del path
	 * @param aux  segona meitat del path
	 * @return retorna la llista de matrius del graf es multipliquen en l'ordre adequat
	 */
	protected ArrayList<SparseMatrix> getMatrixesToMultiply(ArrayList<EntitatType> path,ArrayList<EntitatType> aux)  {
		
		ArrayList<SparseMatrix> matrixesToMultiply = new ArrayList<SparseMatrix>();
		
		ArrayList<WhatMatrix> whatMatrixes = getPairs(path, aux);
		
		for (int i = 0; i < whatMatrixes.size(); ++i) {
			WhatMatrix w = whatMatrixes.get(i);
			switch (w.pathType) {
			case Author2Paper:
				if (!paperAuthor) { // init paper2Author
					this.paper2author = new SparseMatrix(graph.getPaperAutor(), graph.getnPapers(), graph.getnAutors());
					this.author2paper = new SparseMatrix(this.paper2author);
					this.author2paper.transpose();
					this.paperAuthor = true;
				}
				if (w.transposeMatrix) matrixesToMultiply.add(paper2author);
				else matrixesToMultiply.add(author2paper);
				break;
			case Conf2Paper:
				if (!paperConf) { // init paper2Author
					this.paper2conf = new SparseMatrix(graph.getPaperConferencia(), graph.getnPapers(), graph.getnConferencies());
					this.conf2paper = new SparseMatrix(this.paper2conf);
					this.conf2paper.transpose();
					this.paperConf = true;
				}
				if (w.transposeMatrix) matrixesToMultiply.add(paper2conf);
				else matrixesToMultiply.add(conf2paper);
				break;
			case Term2Paper:
				if (!paperTerm) { // init paper2Author
					this.paper2term = new SparseMatrix(graph.getPaperTerme(), graph.getnPapers(), graph.getnTermes());
					this.term2paper = new SparseMatrix(this.paper2term);
					this.term2paper.transpose();
					this.paperTerm = true;
				}
				if (w.transposeMatrix) matrixesToMultiply.add(paper2term);
				else matrixesToMultiply.add(term2paper);
				break;
			case Author2Mid:
			case Paper2MidAut:
				if (!authorMid) {
					Partite p = partiteMatrix(new SparseMatrix(graph.getPaperAutor(), graph.getnPapers(), graph.getnAutors()));
					this.paper2authorMid = (p.leftToMid);
					this.author2mid = new SparseMatrix(p.midToRight);
					this.author2mid.transpose();
					authorMid = true;
				}
				if (w.pathType == PathTypes.Author2Mid) matrixesToMultiply.add(author2mid);
				else matrixesToMultiply.add(paper2authorMid);
				break;
			case Conf2Mid:
			case Paper2MidConf:
				if (!confMid) {
					Partite p = partiteMatrix(new SparseMatrix(graph.getPaperConferencia(), graph.getnPapers(), graph.getnConferencies()));
					this.paper2confMid = (p.leftToMid);
					this.conf2mid = new SparseMatrix(p.midToRight);
					this.conf2mid.transpose();
					confMid = true;
				}
				if (w.pathType == PathTypes.Conf2Mid) matrixesToMultiply.add(conf2mid);
				else matrixesToMultiply.add(paper2confMid);
				break;
			case Term2Mid:
			case Paper2MidTerm:
				if (!termMid) {
					Partite p = partiteMatrix(new SparseMatrix(graph.getPaperTerme(), graph.getnPapers(), graph.getnTermes()));
					this.paper2termMid = (p.leftToMid);
					this.term2mid = new SparseMatrix(p.midToRight);
					this.term2mid.transpose();
					termMid = true;
				}
				if (w.pathType == PathTypes.Term2Mid) matrixesToMultiply.add(term2mid);
				else matrixesToMultiply.add(paper2termMid);
				break;
			}
		}
		
		return matrixesToMultiply;
	}
	
	
	/**
	 * Donades les dues meitats del cami, crea una llista amb la enumeracio de les matrius que s'hauran de multiplicar
	 * @param path primera meitat del path
	 * @param aux  segona meitat del path
	 * @return retorna la llista que indica quines de les matrius del graf es multipliquen i en quin ordre
	 */
	protected ArrayList<WhatMatrix> getPairs(ArrayList<EntitatType> path, ArrayList<EntitatType> aux){
		ArrayList<WhatMatrix> ret = new ArrayList<WhatMatrix>();
		for (int i = 1; i < path.size(); ++i) {
			EntitatType last = path.get(i-1);
			EntitatType current = path.get(i);
			if (current == EntitatType.E) {
				if (last == EntitatType.Paper) {
					EntitatType nextToMid = aux.get(aux.size()-2);
					switch (nextToMid) {
						case Author:
							ret.add(new WhatMatrix(false,PathTypes.Paper2MidAut));
							break;
						case Conference:
							ret.add(new WhatMatrix(false,PathTypes.Paper2MidConf));
							break;
						case Term:
							ret.add(new WhatMatrix(false,PathTypes.Paper2MidTerm));
							break;
						default:
							//System.out.println("broken path, Pe links to P or E");
							//System.out.println(path);
							//System.out.println(aux);

					}
				}
				else {
					switch (last) {
						case Author:
							ret.add(new WhatMatrix(false,PathTypes.Author2Mid));
							break;
						case Conference:
							ret.add(new WhatMatrix(false,PathTypes.Conf2Mid));
							break;
						case Term:
							ret.add(new WhatMatrix(false,PathTypes.Term2Mid));
							break;
						default:
							//System.out.println("Maybe you dont know how2hetesim, or mB the path is brken. IoKze, no soi 100tifico :(");
							//System.out.println(path);
							//System.out.println(last);

					}
				}
			}
			else {
				boolean trans = false;
				if (last == EntitatType.Paper) { //Swap
					EntitatType ntAux = last;
					last = current;
					current = ntAux;
					trans = true;
				}
				switch (last) {
					case Author:
						ret.add(new WhatMatrix(trans,PathTypes.Author2Paper));
						break;
					case Conference:
						ret.add(new WhatMatrix(trans,PathTypes.Conf2Paper));
						break;
					case Term:
						ret.add(new WhatMatrix(trans,PathTypes.Term2Paper));
						break;
					default:
						//System.out.println("Two papers together :( amor imposible i cri ebritiem");
						//System.out.println(path);
						//System.out.println(current);
						//System.out.println(last);

				}
			}
		}

		return ret;
	}
	
}


