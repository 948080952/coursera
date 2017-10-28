/**
 * Created by daipei on 2017/10/28.
 */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph digraph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new java.lang.IllegalArgumentException();
        }
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (!isValidId(v) || !isValidId(w)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        BreadthFirstDirectedPaths p1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths p2 = new BreadthFirstDirectedPaths(digraph, w);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (p1.hasPathTo(i) && p2.hasPathTo(i)) {
                int l = p1.distTo(i) + p2.distTo(i);
                if (l < min) {
                    min = l;
                }
            }
        }
        if (min == Integer.MAX_VALUE) {
            return -1;
        }
        return min;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!isValidId(v) || !isValidId(w)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        BreadthFirstDirectedPaths p1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths p2 = new BreadthFirstDirectedPaths(digraph, w);
        int min = Integer.MAX_VALUE;
        int anc = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (p1.hasPathTo(i) && p2.hasPathTo(i)) {
                int l = p1.distTo(i) + p2.distTo(i);
                if (l < min) {
                    min = l;
                    anc = i;
                }
            }
        }
        return anc;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for (Integer i : v) {
            if (!isValidId(i)) {
                throw new java.lang.IndexOutOfBoundsException();
            }
        }
        for (Integer i : w) {
            if (!isValidId(i)) {
                throw new java.lang.IndexOutOfBoundsException();
            }
        }
        BreadthFirstDirectedPaths p1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths p2 = new BreadthFirstDirectedPaths(digraph, w);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (p1.hasPathTo(i) && p2.hasPathTo(i)) {
                int l = p1.distTo(i) + p2.distTo(i);
                if (l < min) {
                    min = l;
                }
            }
        }
        if (min == Integer.MAX_VALUE) {
            return -1;
        }
        return min;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for (Integer i : v) {
            if (!isValidId(i)) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        for (Integer i : w) {
            if (!isValidId(i)) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        BreadthFirstDirectedPaths p1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths p2 = new BreadthFirstDirectedPaths(digraph, w);
        int min = Integer.MAX_VALUE;
        int anc = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (p1.hasPathTo(i) && p2.hasPathTo(i)) {
                int tmp = p1.distTo(i) + p2.distTo(i);
                if (tmp < min) {
                    min = tmp;
                    anc = i;
                }
            }
        }
        return anc;
    }

    private boolean isValidId(int id) {
        if (id >= 0 && id < digraph.V()) {
            return true;
        }
        return false;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
