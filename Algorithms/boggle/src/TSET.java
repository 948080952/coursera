/**
 * Created by daipei on 2017/11/21.
 */

import edu.princeton.cs.algs4.In;

public class TSET {

    private Node[] roots; // root of TSET

    private static class Node {
        private char c;
        private boolean isString = false;
        private Node left, mid, right;
    }

    public TSET() {
        roots = new Node[26];
    }

    public TSET(String[] strings) {
        roots = new Node[26];
        for (String string : strings) {
            insert(string);
        }
    }

    public boolean contain(String string) {
        if (string == null) {
            throw new IllegalArgumentException();
        }
        Node node = get(string);
        return node != null && node.isString;
    }

    public boolean containPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
        Node node = get(prefix);
        return node != null;
    }

    private Node get(String key) {
        if (key == null) throw new IllegalArgumentException();
        if (key.length() == 0) throw new IllegalArgumentException();
        char c = key.charAt(0);
        return get(roots[c - 'A'], key, 0);
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if (c < x.c)                    return get(x.left, key, d);
        else if (c > x.c)               return get(x.right, key, d);
        else if (d < key.length() - 1)  return get(x.mid, key, d + 1);
        else                            return x;
    }

    public void insert(String string) {
        if (string == null) {
            throw new IllegalArgumentException();
        }
        char c = string.charAt(0);
        roots[c - 'A'] = insert(roots[c - 'A'], string, 0);
    }

    private Node insert(Node x, String string, int d) {
        char c = string.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if (c < x.c)                        x.left = insert(x.left, string, d);
        else if (c > x.c)                   x.right = insert(x.right, string, d);
        else if (d < string.length() - 1)   x.mid = insert(x.mid, string, d + 1);
        else                                x.isString = true;
        return x;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        In in2 = new In(args[1]);
        String[] dic2 = in2.readAllStrings();
        TSET tset = new TSET(dictionary);
        for (String string : dictionary) {
            assert tset.contain(string);
        }
        for (String string : dic2) {
            if (tset.contain(string)) {
                System.out.println("contain: " + string);
            }
            if (tset.containPrefix(string)) {
                System.out.println("contain prefix: " + string);
            }
        }
        System.out.println("finish!");
    }

}
