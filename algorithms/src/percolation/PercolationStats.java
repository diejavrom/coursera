package percolation;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private static final double CONFIDENCE_INTERVAL = 1.96d;

	private double[] results;
	
	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		this.results = new double[trials];
		for(int i=0; i<trials; i++) {
			results[i] = threshold(new Percolation(n), n);
		}
	}

	private double threshold(Percolation percolation, int n) {
		do {
			int row = StdRandom.uniform(1, n+1);
			int col = StdRandom.uniform(1, n+1);
			percolation.open(row, col);
		} while(!percolation.percolates());
		return (percolation.numberOfOpenSites()*1d)/(n*n);
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(results);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(results);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return stddev() - CONFIDENCE_INTERVAL/Math.sqrt(results.length);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return stddev() + CONFIDENCE_INTERVAL/Math.sqrt(results.length);
	}

	// test client (described below)
	public static void main(String[] args) {
		int n = StdIn.readInt();
		int T = StdIn.readInt();
		PercolationStats stats = new PercolationStats(n, T);
		StdOut.println("mean\t\t\t = " + stats.mean());
		StdOut.println("stddev\t\t\t = " + stats.stddev());
		StdOut.println("95% confindence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() +"]");
	}

}