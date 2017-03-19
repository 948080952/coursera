package method2;

/**
 * Created by daipei on 2017/3/19.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;



public class Percolation {
    private int[] openState;
    private int openCount;
    private WeightedQuickUnionUF grid;
    private int gridWidth;
    private boolean percolates;

    private static final int open = 1 << 0;
    private static final int full = 1 << 1;
    private static final int backwash = 1 << 2;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("the grid width should bigger than zero");
        }
        percolates = false;
        openState = new int[n * n];
        openCount = 0;
        gridWidth = n;
        grid = new WeightedQuickUnionUF(n * n);
    }

    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            openCount++;
            openCore(row, col);
        }

    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openState[siteFrom(row, col)] > 0;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        int root = grid.find(siteFrom(row, col));
        return (openState[root] & full) > 0;
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return  percolates;
    }

    public static void main(String[] args) {
        Percolation test = new Percolation(5);
        test.open(1, 2);
        test.open(2, 2);
        test.open(3, 2);
        test.open(4, 3);
        if (test.percolates()) {
            System.out.println("connected");
        } else {
            System.out.println("not connected");
        }
    }

    private void validate(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("the row and col should bigger than zero");
        } else if (row > gridWidth || col > gridWidth || row == 0 || col == 0) {
            throw new IndexOutOfBoundsException("the row or col is out of bounds");
        }
    }

    private void openCore(int row, int col) {
        openState[siteFrom(row, col)] = open;
        int status = open;
        if (row == 1) {
           status = status | full;
        }
        if (row == gridWidth) {
            status = status | backwash;
        }
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                int root = grid.find(siteFrom(row - 1, col));
                status = status | openState[root];
                grid.union(siteFrom(row, col), siteFrom(row - 1, col));
            }
        }
        if (row < gridWidth) {
            if (isOpen(row + 1, col)) {
                int root = grid.find(siteFrom(row + 1, col));
                status = status | openState[root];
                grid.union(siteFrom(row, col), siteFrom(row + 1, col));
            }
        }
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                int root = grid.find(siteFrom(row, col - 1));
                status = status | openState[root];
                grid.union(siteFrom(row, col), siteFrom(row, col - 1));
            }
        }
        if (col < gridWidth) {
            if (isOpen(row, col + 1)) {
                int root = grid.find(siteFrom(row, col + 1));
                status = status | openState[root];
                grid.union(siteFrom(row, col), siteFrom(row, col + 1));
            }
        }

        int finalRoot = grid.find(siteFrom(row, col));

        openState[finalRoot] = openState[finalRoot] | status;
        if (openState[finalRoot] == 7 && !percolates) {
            percolates = true;
        }
    }

    private int siteFrom(int row, int col) {
        return (row - 1) * gridWidth + col - 1;
    }



}