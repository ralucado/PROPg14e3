package TestQueryClustering;

import java.util.Random;

public class HeteSim {
	public HeteSim(Graf g) {
		
	}
	public SparseMatrix getHeteSim(Cami p) {
		SparseMatrix M = new SparseMatrix(7, 7);
		for (int i = 0; i < 7; ++i) {
			for (int j = i; j < 7; ++j) {
				if (i == j) M.set(i, j, new Float(1.0));
				else {
					Random r = new Random();
					int rand = r.nextInt(17);
					M.set(i, j, new Float(0.05*rand + 0.1));
					M.set(j, i, new Float(0.05*rand + 0.1));
				}
			}
		}
		
		System.out.println(M);
		
		return M;
	}
}
