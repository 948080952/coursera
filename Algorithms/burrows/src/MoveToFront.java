/**
 * Created by daipei on 2017/11/24.
 */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

public class MoveToFront {


    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] ascii = new char[256];
        for (int i = 0; i < 256; i++) {
            ascii[i] = (char)i;
        }
        String string = BinaryStdIn.readString();
        for (int i = 0; i < string.length(); i++) {
            BinaryStdOut.write(moveToFront1(string.charAt(i), ascii), 8);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] ascii = new char[256];
        for (int i = 0; i < 256; i++) {
            ascii[i] = (char)i;
        }
        String string = BinaryStdIn.readString();
        for (int i = 0; i < string.length(); i++) {
            BinaryStdOut.write(moveToFront2(string.charAt(i), ascii), 8);
        }
        BinaryStdOut.close();
    }

    private static int moveToFront1 (char c, char[] ascii) {
        if (c == ascii[0]) return 0;
        char key = ascii[0];
        int index = 1;
        while (ascii[index] != c) {
            char tmp = ascii[index];
            ascii[index] = key;
            key = tmp;
            index++;
        }
        ascii[0] = ascii[index];
        ascii[index] = key;
        return index;
    }

    private static char moveToFront2 (int index, char[] ascii) {
        char key = ascii[index];
        for (int i = index; i > 0; i--) {
            ascii[i] = ascii[i - 1];
        }
        ascii[0] = key;
        return key;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].length() != 1) {
            StdOut.print("usage: javac MoveToFront +|- [data]");
            return;
        }
        if (args[0].charAt(0) == '-') {
            encode();
        } else if (args[0].charAt(0) == '+') {
            decode();
        } else {
            StdOut.print("usage: javac MoveToFront +|- [data]");
        }
    }
}
