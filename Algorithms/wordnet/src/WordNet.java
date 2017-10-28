/**
 * Created by daipei on 2017/10/28.
 */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private Digraph wordGraph;
    private HashMap<String, HashSet<Integer>> idMap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null) {
            throw new java.lang.IllegalArgumentException();
        }

        int count = initIdMap(synsets);

        initWordGraph(hypernyms, count);

    }

    private int initIdMap(String synsets) {
        idMap = new HashMap<>();
        In synIn = new In(synsets);
        int count = 0;
        while (synIn.hasNextLine()) {
            String[] argvs = synIn.readLine().split(",");
            String[] words = argvs[1].split(" ");
            int id = Integer.parseInt(argvs[0]);
            for (String word : words) {
                if (idMap.containsKey(word)) {
                    HashSet<Integer> set = idMap.get(word);
                    set.add(id);
                } else {
                    HashSet<Integer> set = new HashSet<>();
                    set.add(id);
                    idMap.put(word, set);
                }
            }
            count++;
        }
        return count;
    }

    private void initWordGraph(String hypernyms, int count) {
        wordGraph = new Digraph(count);
        In hypIn = new In(hypernyms);
        while (hypIn.hasNextLine()) {
            String[] points = hypIn.readLine().split(",");
            for (int i = 1; i < points.length; i++) {
                wordGraph.addEdge(Integer.parseInt(points[0]), Integer.parseInt(points[i]));
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return idMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return idMap.containsKey(word);
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new java.lang.IllegalArgumentException();
        }
        return 0;
    }

    // a synset that is the common ancestor of nounA and nounB
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new java.lang.IllegalArgumentException();
        }
        return "";
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet net = new WordNet(args[0], args[1]);
        System.out.println(net.nouns());
        return;
    }
}