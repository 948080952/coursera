/**
 * Created by daipei on 2017/10/28.
 */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    private Digraph digraph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new java.lang.IllegalArgumentException();
        }
        digraph = new Digraph(G.V());
        for (int i = 0; i < G.V(); i++) {
            for (Integer j : G.adj(i)) {
                digraph.addEdge(i, j);
            }
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v >= digraph.V() || w >= digraph.V()) {
            throw new java.lang.IllegalArgumentException();
        }
        BreadthFirstDirectedPaths p1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths p2 = new BreadthFirstDirectedPaths(digraph, w);
        if (!p1.hasPathTo(w) && !p2.hasPathTo(v)) {
            return -1;
        }
        return Math.min(p1.distTo(w), p2.distTo(v));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v >= digraph.V() || w >= digraph.V()) {
            throw new java.lang.IllegalArgumentException();
        }
        return 0;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new java.lang.IllegalArgumentException();
        }
        BreadthFirstDirectedPaths p1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths p2 = new BreadthFirstDirectedPaths(digraph, w);
        int min = Integer.MAX_VALUE;
        for (Integer i : w) {
            int l = p1.distTo(i);
            if (l < min) {
                min = l;
            }
        }
        for (Integer i : v) {
            int l = p2.distTo(i);
            if (l < min) {
                min = l;
            }
        }
        if (min == Integer.MAX_VALUE) {
            return -1;
        }
        return min;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
