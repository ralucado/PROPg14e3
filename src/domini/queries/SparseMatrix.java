package domini.queries;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
//import java.util.Set;
import java.util.HashSet;
import java.util.Map;

public class SparseMatrix {
	ArrayList<SparseVector> rows = new ArrayList<SparseVector>();
	ArrayList<SparseVector> cols = new ArrayList<SparseVector>();
	
	
	/**
	 * Crea una matriu buida
	 */
	public SparseMatrix() {
	}
	
	
	/**
	 * crea una matriu de zeros de tamany nRows x nCols
	 * @param nRows
	 * @param nCols
	 */
	public SparseMatrix(int nRows, int nCols) {
		initMatrix(nRows, nCols);
	}
	
	/**
	 * creadora de copia a partir de unaltre SparseMatrix
	 * @param sm
	 */
	public SparseMatrix(SparseMatrix sm) {
		ArrayList<SparseVector> rows = sm.getRows();
		for (SparseVector sv : rows) {
			this.rows.add((SparseVector) sv.clone());
		}
		ArrayList<SparseVector> cols = sm.getCols();
		for (SparseVector sv : cols) {
			this.cols.add((SparseVector) sv.clone());
		}
	}
	
	/**
	 * converteix la matriu m de tamany nRows x nCols a SparseMatrix
	 * @param m
	 * @param nRows
	 * @param nCols
	 */
	public SparseMatrix(Map<Integer, HashSet<Integer>> m,int nRows, int nCols) {
		initMatrix(nRows, nCols); //crea matriz dispersa nRows*nCols vacia
		
		//every row you take...🎶
		for (Map.Entry<Integer, HashSet<Integer>> i: m.entrySet()) {
			for (Integer j : m.get(i.getKey())) {
				set(i.getKey(), j, (float)1);
			}
		}
		//ai crai ebri columna
	}
	
	/**
	 * 
	 * @return retorna la llista de columnes de la matriu
	 */
	public ArrayList<SparseVector> getCols() {
		return cols;
	}
	
	/**
	 * 
	 * @return retorna la llista de files de la matriu
	 */
	public ArrayList<SparseVector> getRows() {
		return rows;
	}
	
	/**
	 * Posa el valor indicat per value a la columna i fila indicades per row i col
	 * @param row
	 * @param col
	 * @param value
	 */
	void set(int row, int col, Float value) {
		if (value == null) value = 0.f;
		if (value == 0.f) {
			try {
				if (rows.get(row).containsKey(col)) {
					rows.get(row).remove(col);
					cols.get(col).remove(row);
				}
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("out of bounds position");
				throw e;
			}
			return;
		}
		try {
			rows.get(row).put(col, value);
			cols.get(col).put(row, value);
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("out of bounds position");
			throw e;
		}
	}

	/**
	 * Retorna el nombre de files de la matriu
	 * @return
	 */
	public int getNRows() {
		return rows.size();
	}
	
	/**
	 * Retorna el nombre de columnes de la matriu
	 * @return
	 */
	public int getNCols() {
		return cols.size();
	}
	
	/**
	 * Transforma la matriu en una matriu de zeros del tamany indicat
	 * @param nRows
	 * @param nCols
	 */
	void setSize(int nRows, int nCols) {
		initMatrix( nRows, nCols);
	}
	
	
	/**
	 * retorna la fila amb index i
	 * @param i
	 * @return
	 */
	public SparseVector getRow(int i) {
		return rows.get(i);
	}
	
	/**
	 * retorna la columna amb index j
	 * @param j
	 * @return
	 */
	public SparseVector getCol(int j) {
		return cols.get(j);
	}
	
	/**
	 * retorna el valor albergat en la posicio ij
	 * @param i
	 * @param j
	 * @return
	 */
	public Float getValue(int i, int j) {
		if (i < rows.size() && rows.get(i).containsKey(j)) return rows.get(i).get(j);
		else return 0.f;
	}
	
	/**
	 * Transposa la matriu
	 */
	public void transpose() {
		ArrayList<SparseVector> aux = rows;
		rows = cols;
		cols = aux;
	}
	
	/**
	 * Multiplica m1 i m2
	 * @param m1 matriu esquerra
	 * @param m2 matriu dreta
	 * @return producte de m1 i m2
	 */
	static SparseMatrix multiply(SparseMatrix m1, SparseMatrix m2) {
		SparseMatrix ret = new SparseMatrix(m1.getNRows(), m2.getNCols());
		for (int i = 0; i < ret.getNRows(); ++i) {
			SparseVector v1 = m1.getRow(i);
			for (int j = 0; j < ret.getNCols(); ++j) {
				ret.set(i, j, SparseVector.multiply(v1, m2.getCol(j)));
//				System.out.println("m1.row: " + v1);
//				System.out.println("m2.col: " + m2.getRow(j));
//				System.out.println("M1\n" + m1);
//				System.out.println("M2\n" + m2);
//				System.out.println("ret\n" + ret);
			}
		}
		return ret;
	}

	
	/**
	 * @override imprimeix els elements de la matriu ordenats
	 */
	public String toString() {
		String s = new String();
		DecimalFormat df = new DecimalFormat("##.###");
		df.setRoundingMode(RoundingMode.DOWN);
		for (int i = 0; i < getNRows(); ++i) {
			for (int j = 0; j < getNCols(); ++j) {
				s+=df.format(getValue(i, j))+ " ";
			}
			s+= '\n';
		} 
		return s;
	}
	

	protected void initMatrix(int nRows, int nCols) {
		for (int i = 0; i < nRows; ++i) {
			rows.add(new SparseVector());
		}
		for (int i = 0; i < nCols; ++i) {
			cols.add(new SparseVector());
		}		
	}
}