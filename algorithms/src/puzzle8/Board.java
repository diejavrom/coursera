package puzzle8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Board {

	private int[][] blocks;

	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		if (blocks == null || blocks.length == 0) {
			throw new IllegalArgumentException("argument cannot be null or empty");
		}
		int n = blocks.length;
		for (int i = 0; i < n; i++) {
			if (blocks[i].length != n) {
				throw new IllegalArgumentException("argument must be square");
			}
		}
		if (!(n >= 2 && n < 128)) {
			throw new IllegalArgumentException("n must be between 2 and 127");
		}
		int[] tmp = new int[n * n];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = -1;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (blocks[i][j] < tmp.length) {
					tmp[blocks[i][j]] = 0;
				}
			}
		}
		OptionalInt findFirst = Arrays.stream(tmp).filter(x -> x == -1).findFirst();
		if (findFirst.isPresent()) {
			throw new IllegalArgumentException("argument is not consecutive numbers");
		}
		this.blocks = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				this.blocks[i][j] = blocks[i][j];
			}
		}
	}

	// board dimension n
	public int dimension() {
		return this.blocks.length;
	}

	// number of blocks out of place
	public int hamming() {
		int n = dimension();
		int[] tmp = IntStream.rangeClosed(1, n * n - 1).toArray();
		int[] tmp2 = new int[n * n - 1];
		int k = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (k < tmp2.length) {
					tmp2[k] = this.blocks[i][j];
				}
				k++;
			}
		}
		int hamming = IntStream.rangeClosed(0, tmp.length - 1).map(i -> tmp[i] == tmp2[i] ? 0 : 1).reduce(0,
				(x, y) -> x + y);
		return hamming;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int manhattan = 0;
		int n = dimension();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int val = this.blocks[i][j];
				if (val != 0) {
					boolean exact = val % n == 0;
					float a = (val / n) + (exact ? 0 : 1);
					int rx = (int) Math.round(a);
					int ry = val - n * (rx - 1);
					int dx = rx - (i + 1);
					int dy = ry - (j + 1);
					int dist = (dx < 0 ? dx * (-1) : dx) + (dy < 0 ? dy * (-1) : dy);
					// System.out.println(" val= " + val + " dist = " + dist);
					manhattan += dist < 0 ? dist * (-1) : dist;
				}
			}
		}
		return manhattan;
	}

	// is this board the goal board?
	public boolean isGoal() {
		int n = this.dimension();
		int k = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (this.blocks[i][j] != k) {
					if (!(i == n - 1 && j == n - 1 && this.blocks[i][j] == 0)) {
						return false;
					}
				}
				k++;
			}
		}
		return true;
	}

	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		int n = dimension();
		int x = -1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (this.blocks[i][j] == 0) {
					x = i;
					break;
				}
			}
		}
		int[][] copy = copyBlocks();
		for (int i = 0; i < n; i++) {
			if (i != x) {
				swap(i, 0, i, 1, copy);
				break;
			}
		}
		return new Board(copy);
	}

	// does this board equal y?
	public boolean equals(Object y) {
		if (y == null) {
			return false;
		}
		return toString().equals(y.toString());
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		return neighborsList();
	}

	private List<Board> neighborsList() {
		int n = dimension();
		List<Board> neighbors = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (this.blocks[i][j] == 0) {
					if (i > 0) {
						int[][] clone = copyBlocks();
						swap(i, j, i - 1, j, clone);
						neighbors.add(new Board(clone));
					}
					if (i < n - 1) {
						int[][] clone = copyBlocks();
						swap(i, j, i + 1, j, clone);
						neighbors.add(new Board(clone));
					}
					if (j > 0) {
						int[][] clone = copyBlocks();
						swap(i, j, i, j - 1, clone);
						neighbors.add(new Board(clone));
					}
					if (j < n - 1) {
						int[][] clone = copyBlocks();
						swap(i, j, i, j + 1, clone);
						neighbors.add(new Board(clone));
					}
					return neighbors;
				}
			}
		}
		throw new IllegalStateException("unreachable code??");
	}

	private int[][] copyBlocks() {
		int n = dimension();
		int[][] copy = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				copy[i][j] = this.blocks[i][j];
			}
		}
		return copy;
	}

	private void swap(int i, int j, int newi, int newj, int[][] a) {
		int tmp = a[i][j];
		a[i][j] = a[newi][newj];
		a[newi][newj] = tmp;
	}

	// string representation of this board (in the output format specifie below)
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int n = dimension();
		sb = sb.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				sb = sb.append(" " + this.blocks[i][j]);
			}
			if (i != n) {
				sb = sb.append("\n");
			}
		}
		return sb.toString();
	}

	// unit tests (not graded)
	public static void main(String[] args) {
		int[][] blocks = new int[3][3];
		int k = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				blocks[i][j] = k;
				k++;
			}
		}
		Board board = new Board(blocks);
		System.out.println(board.isGoal());
		System.out.println(board.hamming());
		System.out.println(board.manhattan());

		int[][] blocks2 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		Board board2 = new Board(blocks2);
		System.out.println(board2.isGoal());
		System.out.println(board2.hamming());
		System.out.println(board2.manhattan());

		int[][] blocks3 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } };
		Board board3 = new Board(blocks3);
		System.out.println(board3.isGoal());
		System.out.println(board3.hamming());
		System.out.println(board3.manhattan());

		int[][] blocks4 = { { 1, 2, 3 }, { 4, 5, 6 }, { 0, 8, 7 } };
		Board board4 = new Board(blocks4);
		System.out.println(board4.isGoal());
		System.out.println(board4.hamming());
		System.out.println(board4.manhattan());

		int[][] blocks5 = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
		Board board5 = new Board(blocks5);
		System.out.println(board5.isGoal());
		System.out.println(board5.hamming());
		System.out.println(board5.manhattan());
		System.out.println(board5.toString());
		List<Board> neighborsList = board5.neighborsList();
		for (Board b : neighborsList) {
			System.out.println(b.toString());
		}
	}

}