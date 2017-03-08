import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] openState;
    private int openCount;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF backwash;
    private int gridWidth;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("the grid width should bigger than zero");
        }
        openState = new int[n * n + 2];

        openState[0] = 1;
        openState[n * n + 1] = 1;
        openCount = 0;
        gridWidth = n;
        grid = new WeightedQuickUnionUF(n * n + 2);
        backwash = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            openCount++;
            openCore(row, col);
            openState[siteFrom(row, col)] = 1;
        }

    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openState[siteFrom(row, col)] == 1;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return backwash.connected(0, siteFrom(row, col));
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return grid.connected(0, gridWidth * gridWidth + 1);
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
        int site = siteFrom(row, col);
        if (row == 1) {
            makeUnion(site, 0);
            makeUnion(site, siteFrom(row + 1, col));
            makeBackwash(site, 0);
            makeBackwash(site, siteFrom(row + 1, col));
        } else if (row == gridWidth) {
            makeUnion(site, gridWidth * gridWidth + 1);
            makeUnion(site, siteFrom(row - 1, col));
            makeBackwash(site, siteFrom(row - 1, col));
            if (col > 1) {
                makeBackwash(site, siteFrom(row, col - 1));
            }
            if (col < gridWidth) {
                makeBackwash(site, siteFrom(row, col + 1));
            }
        } else if (col == 1) {
            makeUnion(site, siteFrom(row - 1, col));
            makeUnion(site, siteFrom(row, col + 1));
            makeUnion(site, siteFrom(row + 1, col));
            makeBackwash(site, siteFrom(row - 1, col));
            makeBackwash(site, siteFrom(row, col + 1));
            makeBackwash(site, siteFrom(row + 1, col));
        } else if (col == gridWidth) {
            makeUnion(site, siteFrom(row - 1, col));
            makeUnion(site, siteFrom(row, col - 1));
            makeUnion(site, siteFrom(row + 1, col));
            makeBackwash(site, siteFrom(row - 1, col));
            makeBackwash(site, siteFrom(row, col - 1));
            makeBackwash(site, siteFrom(row + 1, col));
        } else {
            makeUnion(site, siteFrom(row - 1, col));
            makeUnion(site, siteFrom(row, col - 1));
            makeUnion(site, siteFrom(row + 1, col));
            makeUnion(site, siteFrom(row, col + 1));
            makeBackwash(site, siteFrom(row - 1, col));
            makeBackwash(site, siteFrom(row, col - 1));
            makeBackwash(site, siteFrom(row + 1, col));
            makeBackwash(site, siteFrom(row, col + 1));
        }
    }

    private int siteFrom(int row, int col) {
        return (row - 1) * gridWidth + col;
    }

    private void makeUnion(int a, int b) {
        if (openState[b] == 1) {
            grid.union(a, b);
        }
    }

    private  void makeBackwash(int a, int b) {
        if (openState[b] == 1) {
            backwash.union(a, b);
        }
    }

}