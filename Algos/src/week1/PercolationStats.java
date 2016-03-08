/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 *
 * @author Stefan
 */
public class PercolationStats {

    private double[] frac;
    private int n;
    private double mean;
    private double stdDev;
    private double delta;

    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        double n2 = n*n;
        frac = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            frac[i] = runPerc(p) / n2;
        }
        stdDev = StdStats.stddev(frac);
        mean = StdStats.mean(frac);
        delta = 1.96*stdDev / Math.sqrt(t);
    }    
    // returns number of opened fields until the grid percolates
    private double runPerc(Percolation p) {
        int c = 0;
        while (!p.percolates()) {
            int p1;
            int p2;
            int cnt = 0;
            do {
                p1 = StdRandom.uniform(1, n+1);
                p2 = StdRandom.uniform(1, n+1);
            }
            while (p.isOpen(p1, p2));
            cnt++;
            p.open(p1, p2);
            c++;
        }
        return c;
    }

    public double mean() {
        return mean;
    }

    
    public double stddev() {
        return stdDev;
    }

    public double confidenceLo() {
        return mean - delta;
    }

    public double confidenceHi() {
        return mean + delta;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        System.out.format("mean                     = %f%n", ps.mean());
        System.out.format("stddev                   = %f%n", ps.stddev());
        System.out.format("95%% confidence interval  = %f, %f%n", ps.confidenceLo(), ps.confidenceHi());
    }
}
