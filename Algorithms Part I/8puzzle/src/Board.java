import java.util.Iterator;
import edu.princeton.cs.algs4.In;

/**
 * Created by daipei on 2017/3/19.
 */

public class Board {

    private int[][] blocks;
    private int[][] twinBlocks;
    private int dimension;
    private int hamming = 0;
    private int manhattan = 0;
    private boolean isGoal = true;
    private String string;
    private BoardNode neighborNode;

    public Board(int[][] blocks) {

        this.blocks = blocks;
        this.dimension = blocks.length;

        StringBuilder sb = new StringBuilder();

        twinBlocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int value = blocks[i][j];
                twinBlocks[i][j] = value;
                if (value == 0) {
                    sb.append("   ");
                } else {
                    sb.append(String.format("%3d", value));
                    manhattan += stepBetween(i, j, value);
                }
                if ((value - 1)!= (i * dimension + j) && value != 0) {
                    isGoal = false;
                    hamming++;
                }
            }
            sb.append("\n");
        }
        int tmp = twinBlocks[0][0];
        twinBlocks[0][0] = twinBlocks[0][1];
        twinBlocks[0][1] = tmp;
        string = sb.toString();



    }

    private int[][] blocksFromCurrentBoard() {
        int[][] newBlocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                newBlocks[i][j] = blocks[i][j];
            }
        }
        return newBlocks;
    }

    private class BoardNode {
        Board value;
        BoardNode next;
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public Board twin() {
        return new Board(twinBlocks);
    }

    public boolean equals(Object y) {
        return false;
    }

    public Iterable<Board> neighbors() {

        if (neighborNode == null) {
            generateNeighbors();
        }

        return new Iterable<Board>() {
            public BoardNode currentNode = neighborNode;

            @Override
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {
                    @Override
                    public boolean hasNext() {
                        if (currentNode.value != null) {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public Board next() {
                        if (!hasNext()) {
                            throw new java.util.NoSuchElementException();
                        }
                        Board item = currentNode.value;
                        currentNode = currentNode.next;
                        return item;
                    }
                };
            }
        };
    }

    private void generateNeighbors() {

        neighborNode = new BoardNode();
        int zeroRow = 0, zeroColum = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    zeroRow = i;
                    zeroColum = j;
                }
            }
        }

        BoardNode currentNode = neighborNode;
        int zeroRow_n = zeroRow - 1;
        int zeroColum_n = zeroColum;
        int[][] newBlocks = blocksFromCurrentBoard();

        if (zeroRow_n >= 0) {
            newBlocks[zeroRow_n][zeroColum_n] = blocks[zeroRow][zeroColum];
            newBlocks[zeroRow][zeroColum] = blocks[zeroRow_n][zeroColum_n];
            currentNode.value = new Board(newBlocks);
            currentNode.next = new BoardNode();
            currentNode = currentNode.next;
            newBlocks = blocksFromCurrentBoard();
        }

        zeroRow_n = zeroRow;
        zeroColum_n = zeroColum - 1;

        if (zeroColum_n >= 0) {
            newBlocks[zeroRow_n][zeroColum_n] = blocks[zeroRow][zeroColum];
            newBlocks[zeroRow][zeroColum] = blocks[zeroRow_n][zeroColum_n];
            currentNode.value = new Board(newBlocks);
            currentNode.next = new BoardNode();
            currentNode = currentNode.next;
            newBlocks = blocksFromCurrentBoard();
        }

        zeroRow_n = zeroRow + 1;
        zeroColum_n = zeroColum;

        if (zeroRow_n < dimension) {
            newBlocks[zeroRow_n][zeroColum_n] = blocks[zeroRow][zeroColum];
            newBlocks[zeroRow][zeroColum] = blocks[zeroRow_n][zeroColum_n];
            currentNode.value = new Board(newBlocks);
            currentNode.next = new BoardNode();
            currentNode = currentNode.next;
            newBlocks = blocksFromCurrentBoard();
        }

        zeroRow_n = zeroRow;
        zeroColum_n = zeroColum_n + 1;

        if (zeroColum_n < dimension) {
            newBlocks[zeroRow_n][zeroColum_n] = blocks[zeroRow][zeroColum];
            newBlocks[zeroRow][zeroColum] = blocks[zeroRow_n][zeroColum_n];
            currentNode.value = new Board(newBlocks);
        }
    }

    public String toString() {
        return string;
    }

    private int stepBetween(int i, int j, int value) {
        int result = 0;

        int j_t = (value - 1) % dimension;
        int i_t = (value - 1) / dimension;

        result = Math.abs(i_t - i) + Math.abs(j_t - j);

        return result;
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }

        Board initial = new Board(blocks);

        System.out.println(initial.toString());

        System.out.println("hamming:" + initial.hamming() + " manhattan:" + initial.manhattan());

        if (initial.isGoal()) {
            System.out.println("is goal");
        } else {
            System.out.println("not goal");
        }

        System.out.println("neighbors:");

        for (Board board :
                initial.neighbors()) {
            System.out.println(board.toString());
        }

    }

}
