package TestCluster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class SparseMatrix {
	ArrayList<SparseVector> rows = new ArrayList<SparseVector>();
	ArrayList<SparseVector> cols = new ArrayList<SparseVector>();

	SparseMatrix(int nRows, int nCols) {
		initMatrix(nRows, nCols);
	}
	

	SparseMatrix(SparseMatrix sm) {
		ArrayList<SparseVector> rows = sm.getRows();
		for (SparseVector sv : rows) {
			this.rows.add((SparseVector) sv.clone());
		}
		ArrayList<SparseVector> cols = sm.getCols();
		for (SparseVector sv : cols) {
			this.cols.add((SparseVector) sv.clone());
		}
	}
	
	SparseMatrix(Map<Integer, HashSet<Integer>> m,int nRows, int nCols) {
		initMatrix(nRows, nCols); //crea matriz dispersa nRows*nCols vacia
		
		//every row you take...🎶
		for (Map.Entry<Integer, HashSet<Integer>> i: m.entrySet()) {
			for (Integer j : m.get(i.getKey())) {
				set(i.getKey(), j, (float)1);
			}
		}
		//ai crai ebri columna
	}

	public SparseMatrix() {
		// TODO Auto-generated constructor stub
	}
	
	private void initMatrix(int nRows, int nCols) {
		for (int i = 0; i < nRows; ++i) {
			rows.add(new SparseVector());
		}
		for (int i = 0; i < nCols; ++i) {
			cols.add(new SparseVector());
		}		
	}



	public ArrayList<SparseVector> getCols() {
		return cols;
	}

	public ArrayList<SparseVector> getRows() {
		return rows;
	}
	
	void set(int row, int col, Float value) {
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
	void setSize(int nRows, int nCols) {
		for (int i = 0; i < nRows; ++i) {
			rows.add(new SparseVector());
		}
		for (int i = 0; i < nCols; ++i) {
			cols.add(new SparseVector());
		}
	}
	
	public int getNRows() {
		return rows.size();
	}
	
	public int getNCols() {
		return cols.size();
	}
	
	SparseVector getRow(int i) {
		return rows.get(i);
	}
	
	SparseVector getCol(int j) {
		return cols.get(j);
	}
	
	Float getValue(int i, int j) {
		if (i < rows.size() && rows.get(i).containsKey(j)) return rows.get(i).get(j);
		else return 0.f;
	}
	
	void transpose() {
		ArrayList<SparseVector> aux = rows;
		rows = cols;
		cols = aux;
	}
	
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

	void normaliceRows() {
		for (int i = 0; i < getNRows(); ++i) {
			Double total = 0.0;
			for (Integer j : rows.get(i).keySet()) {
				total += Math.pow(getValue(i,j),2);
			}
			total = Math.sqrt(total);
			for (Integer j : rows.get(i).keySet()) {
				set(i,j,(float) (getValue(i,j)/total));
//				rows.get(i).put(j,(float) (rows.get(i).get(j)/total));
				
			}
		}
	}

	public int numberOfNotZeros() {
		int total = 0;
		
		for (SparseVector sv : rows) {
			total += sv.size();
		}
		
		return total;
	}
/*
	public Matrix toMatrix() {
		Matrix ret = new Matrix();
		ret.setTamany(getNRows(), getNCols());
		for (int i = 0; i < getNRows(); ++i) {
			for (Integer k : rows.get(i).keySet()){
				ret.getRow(i).set(k, getValue(i,k)); // bypassing the things and modifiying directly the 'm'
			}
		}
		return ret;
	}
	*/
	public String toString() {
		String s = new String();
		for (int i = 0; i < getNRows(); ++i) {
			for (int j = 0; j < getNCols(); ++j) {
				s+=getValue(i, j) + " ";
			}
			s+= '\n';
		} 
		return s;
	}
}