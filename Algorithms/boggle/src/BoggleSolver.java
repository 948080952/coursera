/**
 * Created by daipei on 2017/11/21.
 */

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {

    private final TSET allWords;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        allWords = new TSET(dictionary);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        SET<String> validWords = new SET<>();
        boolean[][] mark;
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                mark = new boolean[board.rows()][board.cols()];
                dfs(board, new StringBuilder(), validWords, i, j, mark);
            }
        }
        return validWords;
    }

    private void dfs(BoggleBoard board, StringBuilder string, SET<String> collecion, int x, int y, boolean[][] mark) {
        mark[x][y] = true;
        boolean isQ = board.getLetter(x, y) == 'Q';
        string.append(board.getLetter(x, y));
        if (isQ) {
            string.append('U');
        }
        if (!allWords.containPrefix(string.toString())) {
            string.deleteCharAt(string.length() - 1);
            if (isQ) {
                string.deleteCharAt(string.length() - 1);
            }
            mark[x][y] = false;
            return;
        }
        if (allWords.contain(string.toString()) && string.length() >= 3) {
            collecion.add(string.toString());
        }
        int w = board.rows(), h = board.cols();
        int x1, y1 = y;
        x1 = x - 1;
        if (x1 >= 0 && !mark[x1][y1]) {
            dfs(board, string, collecion, x1, y1, mark);
        }
        y1 = y - 1;
        if (y1 >= 0 && x1 >= 0 && !mark[x1][y1]) {
            dfs(board, string, collecion, x1, y1, mark);
        }
        x1 = x;
        if (y1 >= 0 && !mark[x1][y1]) {
            dfs(board, string, collecion, x1, y1, mark);
        }
        x1 = x + 1;
        if (y1 >= 0 && x1 < w && !mark[x1][y1]) {
            dfs(board, string, collecion, x1, y1, mark);
        }
        y1 = y;
        if (x1 < w && !mark[x1][y1]) {
            dfs(board, string, collecion, x1, y1, mark);
        }
        y1 = y + 1;
        if (x1 < w && y1 < h && !mark[x1][y1]) {
            dfs(board, string, collecion, x1, y1, mark);
        }
        x1 = x;
        if (y1 < h && !mark[x1][y1]) {
            dfs(board, string, collecion, x1, y1, mark);
        }
        x1 = x - 1;
        if (x1 >= 0 && y1 < h && !mark[x1][y1]) {
            dfs(board, string, collecion, x1, y1, mark);
        }
        string.deleteCharAt(string.length() - 1);
        if (isQ) {
            string.deleteCharAt(string.length() - 1);
        }
        mark[x][y] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (allWords.contain(word)) {
            return score(word);
        }
        return 0;
    }

    private int score(String word) {
        if (word.length() <= 2) {
            return 0;
        } else if (word.length() <= 4) {
            return 1;
        } else if (word.length() <= 5) {
            return 2;
        } else if (word.length() <= 6) {
            return 3;
        } else if (word.length() <= 7) {
            return 5;
        } else {
            return 11;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
