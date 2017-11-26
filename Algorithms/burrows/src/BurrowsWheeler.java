/**
 * Created by daipei on 2017/11/24.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;

public class BurrowsWheeler {

    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        String string = BinaryStdIn.readString();
        StringBuilder result = new StringBuilder();
        CircularSuffixArray suffix = new CircularSuffixArray(string);
        int start = 0;
        for (int i = 0; i < suffix.length(); i++) {
            result.append(string.charAt((suffix.index(i) + suffix.length() - 1) % suffix.length()));
            if (suffix.index(i) == 0) {
                start = i;
            }
        }
        BinaryStdOut.write(start, 32);
        for (int i = 0; i < result.length(); i++) {
            BinaryStdOut.write(result.charAt(i), 8);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int start = BinaryStdIn.readInt();
        // generate s
        String t = BinaryStdIn.readString();
        int ascii[] = new int[256];
        for (int i = 0; i < t.length(); i++) {
            ascii[t.charAt(i)]++;
        }
        char[] s = new char[t.length()];
        int[] map = new int[256];
        for (int i = 0; i < map.length; i++) {
            map[i] = -1;
        }
        int p1 = 0;
        int p2 = 0;
        while (p1 < s.length) {
            if (ascii[p2] == 0) {
                p2++;
            } else {
                ascii[p2]--;
                s[p1] = (char)p2;
                if (map[p2] == -1) {
                    map[p2] = p1;
                }
                p1++;
            }
        }
        // generate next
        int[] next = new int[t.length()];
        for (int i = 0; i < t.length(); i++) {
            next[map[t.charAt(i)]] = i;
            map[t.charAt(i)]++;
        }
        // generate result
        int c = 0;
        int n = start;
        while (c < next.length) {
            BinaryStdOut.write(s[n], 8);
            n = next[n];
            c++;
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if (args[0].length() != 1) {
            StdOut.print("usage: javac BurrowsWheeler +|- [data]");
            return;
        }
        if (args[0].charAt(0) == '-') {
            encode();
        } else if (args[0].charAt(0) == '+') {
            decode();
        } else {
            StdOut.print("usage: javac BurrowsWheeler +|- [data]");
        }
    }
}
