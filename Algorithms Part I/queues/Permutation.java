
import edu.princeton.cs.algs4.*;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String str1 = StdIn.readString();
            queue.enqueue(str1);
        }
        for (int i = 0; i < k; i++) {
            String str = queue.dequeue();
            System.out.println(str);
        }
    }
}