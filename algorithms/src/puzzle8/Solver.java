package puzzle8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {

	private SearchNode solution;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		MinPQ<SearchNode> pq = new MinPQ<>();
		MinPQ<SearchNode> pq2 = new MinPQ<>();
		SearchNode delMin = new SearchNode(null, initial, 0);
		SearchNode delMin2 = new SearchNode(null, initial.twin(), 0);
		pq.insert(delMin);
		pq2.insert(delMin2);
		while (true) {
			delMin = pq.delMin();
			delMin2 = pq2.delMin();
			for (Board nb : delMin.board.neighbors()) {
				if (delMin.init == null || !nb.equals(delMin.init.board)) {
					pq.insert(new SearchNode(delMin, nb, delMin.moves + 1));
				}
			}
			for (Board nb2 : delMin2.board.neighbors()) {
				if (delMin2.init == null || !nb2.equals(delMin2.init.board)) {
					pq2.insert(new SearchNode(delMin2, nb2, delMin2.moves + 1));
				}
			}
			if (delMin.board.isGoal() || delMin2.board.isGoal()) {
				break;
			}
		}
		if (delMin.board.isGoal()) {
			this.solution = delMin;
		} else {
			this.solution = null;
		}
	}

	// is the initial board solvable?
	public boolean isSolvable() {
		return this.solution != null;
	}

	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		if (this.solution == null) {
			return -1;
		} else {
			return this.solution.moves;
		}
	}

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if (isSolvable()) {
			List<Board> path = new ArrayList<>();
			SearchNode sn = this.solution;
			path.add(sn.board);
			while (sn.init != null) {
				sn = sn.init;
				path.add(sn.board);
			}
			Collections.reverse(path);
			return path;
		} else {
			return null;
		}
	}

	// solve a slider puzzle (given below)
	public static void main(String[] args) {
		int[][] b = { { 1, 2, 3 }, { 4, 5, 6 }, { 8, 7, 0 } };
		Board board = new Board(b);
		Solver s = new Solver(board);
		System.out.println(s.isSolvable());
	}

	private class SearchNode implements Comparable<SearchNode> {

		private SearchNode init;
		private Board board;
		private int moves;

		public SearchNode(SearchNode init, Board current, int moves) {
			this.init = init;
			this.board = current;
			this.moves = moves;
		}

		@Override
		public int compareTo(SearchNode o) {
			return (this.board.manhattan() + this.moves) - (o.board.manhattan() + o.moves);
		}

	}

}