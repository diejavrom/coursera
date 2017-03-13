package percolation;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private WeightedQuickUnionUF quickunion;
	private int n;
	private int[][] opens;
	private int nOpens;

	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		if(n < 1) {
			throw new IllegalArgumentException("invalid n " + n);
		}
		this.n = n;
		this.quickunion = new WeightedQuickUnionUF(n*n + 2); //i.e. n-by-n grid + 2 artificial sites
		this.opens = new int[n][n];
	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		validate(row, col);		
		if(isOpen(row, col)) {
			return;
		}
		if(row == 1) {// connect to upper artificial site
			this.quickunion.union(0, getSite(row, col));
		}
		if(row == this.n) {// connect to bottom artificial site
			this.quickunion.union(n*n + 1, getSite(row, col));
		}
		if(col < n && isOpen(row, col+1)) { // site *->
			this.quickunion.union(getSite(row, col), getSite(row, col+1));
		}
		if(col > 1 && isOpen(row, col-1)) { // site  
			this.quickunion.union(getSite(row, col-1), getSite(row, col));
		}
		if(row > 1 && isOpen(row-1, col)) {
			this.quickunion.union(getSite(row-1, col), getSite(row, col));
		}
		if(row < n && isOpen(row+1, col)) {
			this.quickunion.union(getSite(row, col), getSite(row+1, col));
		}
		opens[row-1][col-1] = 1;
		nOpens++;
	}

	private int getSite(int row, int col) {
		return col + n * (row - 1);
	}
	
	private void validate(int row, int col) {
		if(row < 1 || row > n) {
			throw new IndexOutOfBoundsException("row index " + row + " out of bounds");
		}
		if(col < 1 || col > n) {
			throw new IndexOutOfBoundsException("col index " + col + " out of bounds");
		}
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		validate(row, col);
		return opens[row-1][col-1] == 1;
	}

	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		validate(row, col);		
		return isOpen(row, col) && quickunion.connected(0, getSite(row, col));
	}

	// number of open sites
	public int numberOfOpenSites() {
		return nOpens;
	}

	// does the system percolate?
	public boolean percolates() {
		return quickunion.connected(0, n*n+1);
	}

	// test client (optional)
	public static void main(String[] args) {
		Percolation percolation = new Percolation(5);
		percolation.open(1, 1);
		percolation.open(1, 2);
		percolation.open(1, 4);
		
		percolation.open(2, 4);
		
		percolation.open(3, 2);
		percolation.open(3, 4);
		percolation.open(3, 5);
		
		percolation.open(4, 1);
		percolation.open(4, 3);
		
		percolation.open(5, 1);
		percolation.open(5, 2);
		percolation.open(5, 3);
		percolation.open(5, 4);

		StdOut.println("percolate? -> " + percolation.percolates());   //false
		StdOut.println("isOpen(1,1)? -> " + percolation.isOpen(1, 1)); //true
		StdOut.println("isOpen(4,2)? -> " + percolation.isOpen(4, 2)); //false
	}

}