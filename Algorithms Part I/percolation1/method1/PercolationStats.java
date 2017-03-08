
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import java.util.List;
import java.util.ArrayList;

public class PercolationStats {
    
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int n, int trials) {
        validate(n);
        validate(trials);
        this.mean = 0;
        this.stddev = 0;
        this.confidenceHi = 0;
        this.confidenceLo = 0;
        double[] means = new double[trials];
        for (int j = 0; j < trials; j++) {
            Percolation percolation = new Percolation(n);
            int randomMax = n * n;
            List<Integer> closedSites = new ArrayList<Integer>();
            for (Integer i = 0; i < randomMax; i++) {
                closedSites.add(i);
            }
            while (!percolation.percolates()) {
                int random = StdRandom.uniform(0, randomMax);
                int site = closedSites.get(random);
                closedSites.remove(random);
                int row = site / n + 1;
                int col = site % n + 1;
                percolation.open(row, col);
                randomMax--;
            }
            double onceMean = (double)percolation.numberOfOpenSites() / (double)(n * n);
            this.mean += onceMean;
            means[j] = onceMean;
        }
        this.mean /= trials;
        for (int k = 0; k < trials; k++) {
            this.stddev += Math.pow((means[k] - this.mean), 2);
        }
        this.stddev /= (trials - 1);
        this.stddev = Math.sqrt(this.stddev);
        double change = 1.96 * this.stddev / Math.sqrt(trials);
        this.confidenceLo = this.mean - change;
        this.confidenceHi = this.mean + change;
    }
    
    public double mean() {
        return this.mean;
    }
    
    public double stddev() {
        return this.stddev;
    }
    
    public double confidenceLo() {
        return this.confidenceLo;
    }
    
    public double confidenceHi() {
        return this.confidenceHi;
    }

    private void validate(int a) {
        if (a <= 0) {
            throw new IllegalArgumentException("the n or trials should bigger than zero");
        }
    }
    
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trails = StdIn.readInt();
        PercolationStats percolationStats = new PercolationStats(n, trails);
        System.out.println("mean                    = " + percolationStats.mean);
        System.out.println("stddev                  = " + percolationStats.stddev);
        System.out.println("95% confidence interval = [" +
                        percolationStats.confidenceLo + ", " +
                        percolationStats.confidenceHi + "]");

    }
}