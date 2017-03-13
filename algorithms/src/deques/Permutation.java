package deques;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int k = StdIn.readInt();
        while (StdIn.hasNextLine()) {
            rq.enqueue(StdIn.readLine());
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }

}
