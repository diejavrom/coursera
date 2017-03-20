package puzzle8;

public class TestClient {

	public static void main(String[] args) {
	    // create initial board from file
		int[][] blocks3 = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
		Board initial = new Board(blocks3);

	    // solve the puzzle
	    Solver solver = new Solver(initial);
	    System.out.println(solver.isSolvable());
	    System.out.println(solver.moves());
	    for(Board b : solver.solution()) {
	    	System.out.println(b.toString());
	    }
	    // print solution to standard output
//	    if (!solver.isSolvable())
//	        StdOut.println("No solution possible");
//	    else {
//	        StdOut.println("Minimum number of moves = " + solver.moves());
//	        for (Board board : solver.solution())
//	            StdOut.println(board);
//	    }
	    
	}
	
}
