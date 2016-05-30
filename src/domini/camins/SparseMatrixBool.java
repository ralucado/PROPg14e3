package domini.camins;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import domini.queries.SparseMatrix;

public class SparseMatrixBool {
	ArrayList<HashMap<Integer,Boolean>> rows = new ArrayList<HashMap<Integer,Boolean>>();
	ArrayList<HashMap<Integer,Boolean>> cols = new ArrayList<HashMap<Integer,Boolean>>();
	
	
	public SparseMatrixBool(SparseMatrix sm) {
		initMatrix(sm.getNRows(), sm.getNCols());
		for (int i = 0; i < sm.getNRows(); ++i) {
			for (HashMap.Entry<Integer,Float> e : sm.getRow(i).entrySet()){
				if (e.getValue() > 0.5f) this.set(i, e.getKey(), true);
			}
		}
	}
	
	/**
	 * Crea una matriu buida
	 */
	public SparseMatrixBool() {
		initMatrix(0,0);
	}
	
	
	/**
	 * crea una matriu de zeros de tamany nRows x nCols
	 * @param nRows
	 * @param nCols
	 */
	public SparseMatrixBool(int nRows, int nCols) {
		initMatrix(nRows, nCols);
	}
	
	/**
	 * creadora de copia a partir de unaltre SparseMatrix
	 * @param sm
	 */
	@SuppressWarnings("unchecked")
	public SparseMatrixBool(SparseMatrixBool sm) {
		ArrayList<HashMap<Integer,Boolean>> rows = sm.getRows();
		for (HashMap<Integer,Boolean> sv : rows) {
			this.rows.add((HashMap<Integer,Boolean>) sv.clone());
		}
		ArrayList<HashMap<Integer,Boolean>> cols = sm.getCols();
		for (HashMap<Integer,Boolean> sv : cols) {
			this.cols.add((HashMap<Integer,Boolean>) sv.clone());
		}
	}
	
	public SparseMatrixBool(ArrayList<HashMap<Integer,Boolean>> v){
		for (int i = 0; i < v.size(); ++i) {
			for (int j = 0; j < v.get(i).size(); ++j) {
				this.set(i, j, v.get(i).get(j));
			}
		}
	}
	
	/**
	 * 
	 * @return retorna la llista de columnes de la matriu
	 */
	public ArrayList<HashMap<Integer,Boolean>> getCols() {
		return cols;
	}
	
	/**
	 * 
	 * @return retorna la llista de files de la matriu
	 */
	public ArrayList<HashMap<Integer,Boolean>> getRows() {
		return rows;
	}
	
	/**
	 * Posa el valor indicat per value a la columna i fila indicades per row i col
	 * @param row
	 * @param col
	 * @param value
	 */
	void set(int row, int col, Boolean value) {
		if (value == null) value = false;
		if (value == false) {
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
	public HashMap<Integer,Boolean> getRow(int i) {
		return rows.get(i);
	}
	
	/**
	 * retorna la columna amb index j
	 * @param j
	 * @return
	 */
	public HashMap<Integer,Boolean> getCol(int j) {
		return cols.get(j);
	}
	
	/**
	 * retorna el valor albergat en la posicio ij
	 * @param i
	 * @param j
	 * @return
	 */
	public Boolean getValue(int i, int j) {
		if (i < rows.size() && rows.get(i).containsKey(j)) return rows.get(i).get(j);
		else return false;
	}
	
	/**
	 * Transposa la matriu
	 */
	public void transpose() {
		ArrayList<HashMap<Integer,Boolean>> aux = rows;
		rows = cols;
		cols = aux;
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
			rows.add(new HashMap<Integer,Boolean>());
		}
		for (int i = 0; i < nCols; ++i) {
			cols.add(new HashMap<Integer,Boolean>());
		}		
	}
}