/**
 * Created by daipei on 2017/3/19.
 */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Solver {

    private int totalMoves = 0;
    private boolean solvable;
    private Board[] solutions;

    private class SearchNode implements Comparable{
        Board board;
        SearchNode previous;
        SearchNode pTrash;
        int priority;
        int moves;
        public SearchNode(Board board, SearchNode previous, int moves) {

            this.board = board;
            this.previous = previous;
            this.moves = moves;
            priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(Object o) {
            SearchNode that = (SearchNode) o;
            if (this.priority > that.priority) {
                return 1;
            } else if (this.priority < that.priority) {
                return -1;
            }
            return 0;
        }
    }

    public Solver(Board initial) {

        if (initial == null) {
            throw new java.lang.NullPointerException();
        }

        SearchNode firstNode = new SearchNode(initial, null, 0);
        SearchNode firstPair = new SearchNode(initial.twin(), null, 0);

        MinPQ<SearchNode> priorityQueue1 = new MinPQ<>();
        MinPQ<SearchNode> priorityQueue2 = new MinPQ<>();

        SearchNode delNode1 = firstNode;
        SearchNode delNode2 = firstPair;

        SearchNode trashNode1 = delNode1;
        SearchNode trashNode2 = delNode2;

        while (!delNode1.board.isGoal() && !delNode2.board.isGoal()) {

//            System.out.println(delNode1.board.toString());

            for (Board board :
                    delNode1.board.neighbors()) {
                if (!validateBoard(board, firstNode)) {
                    continue;
                }
                SearchNode node = new SearchNode(board, delNode1, delNode1.moves + 1);
                priorityQueue1.insert(node);
            }
            delNode1 = priorityQueue1.delMin();
            trashNode1.pTrash = delNode1;
            trashNode1 = trashNode1.pTrash;

            for (Board board :
                    delNode2.board.neighbors()) {
                if (!validateBoard(board, firstPair)) {
                    continue;
                }
                SearchNode node = new SearchNode(board, delNode2, delNode2.moves + 1);
                priorityQueue2.insert(node);
            }
            delNode2 = priorityQueue2.delMin();
            trashNode2.pTrash = delNode2;
            trashNode2 = trashNode2.pTrash;
        }
        SearchNode resultNode;
        if (delNode1.board.isGoal()) {
            solvable = true;
            resultNode = delNode1;
        } else {
            solvable = false;
            resultNode = delNode2;
        }
        totalMoves = resultNode.moves;
        solutions = new Board[totalMoves + 1];
        SearchNode pNode = resultNode;
        for (int i = 0; i <= totalMoves; i++) {
            solutions[i] = pNode.board;
            pNode = pNode.previous;
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (isSolvable()) {
            return totalMoves;
        } else {
            return -1;
        }

    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            return new Iterable<Board>() {
                @Override
                public Iterator<Board> iterator() {

                    return new Iterator<Board>() {
                        private int a = totalMoves + 1;

                        @Override
                        public boolean hasNext() {
                            return a > 0;
                        }

                        @Override
                        public Board next() {
                            if (!hasNext())
                                throw new java.util.NoSuchElementException();
                            return solutions[--a];
                        }
                    };
                }
            };
        } else {
            return null;
        }

    }

    private boolean validateBoard(Board board, SearchNode node) {

        SearchNode pNode = node;
        while (pNode != null) {
            if (board.equals(pNode.board)) {
                return false;
            }
            pNode = pNode.pTrash;
        }
        return true;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
