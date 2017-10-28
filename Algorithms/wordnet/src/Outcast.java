/**
 * Created by daipei on 2017/10/28.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;
    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        int[] distances = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            int dis = 0;
            for (String noun : nouns) {
                dis += wordNet.distance(nouns[i], noun);
            }
            distances[i] = dis;
        }
        int index = 0;
        for (int i = 1; i < distances.length; i++) {
            int dis = distances[i];
            if (dis > distances[index]) {
                index = i;
            }
        }
        return nouns[index];
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
