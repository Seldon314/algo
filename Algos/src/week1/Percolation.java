/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 *
 * @author Stefan
 */
public class Percolation {

//    private int[][] grid;
    private boolean[][] openGrid;
    private boolean[][] fullGrid;
    private int n;
    private boolean percolates;
    private WeightedQuickUnionUF unionList;

    public Percolation(int n){
        if (n < 1)
            throw new IllegalArgumentException();
        int n2 = n*n;
        openGrid = new boolean[n][n];
        fullGrid = new boolean[n][n];
        // 2 extra to check faster if top is connected to bottom
        unionList = new WeightedQuickUnionUF(n*n + 2);
        this.n = n;
        // initialize grids with false
        for (int i = 0; i < n2; i++) {
            openGrid[i/n][i%n] = false;
            fullGrid[i/n][i%n] = false;
        }
        // connect first field to the first row, last field to last row
        for (int i = 1; i <= n; i++) {
            unionList.union(0, i);
            unionList.union(n2, n2 - i);
        }
    }
    // resolve grid position to position in the unionList
    private int pointToNum(int i, int j) {
        return 1 + (i-1)*n + (j-1)*n;
        
    }
    // return every possible neighboring point
    private int[][] getNeighs(int i, int j) {
        int hasLeft = j > 1 ? 1 : 0;
        int hasTop = i > 1 ? 1 : 0;
        int hasBottom = i < n ? 1 : 0;
        int hasRight = j < n ? 1 : 0;
        int cnt = hasLeft + hasRight + hasTop + hasBottom; 
        int[][] res = new int[cnt][2];
        if (hasLeft == 1) {
            res[--cnt][0] = i;
            res[cnt][1] = j - 1;
        }
        if (hasRight == 1) {
            res[--cnt][0] = i;
            res[cnt][1] = j + 1;
        }
        if (hasTop == 1) {
            res[--cnt][0] = i + 1;
            res[cnt][1] = j;
        }
        if (hasBottom == 1) {
            res[--cnt][0] = i - 1;
            res[cnt][1] = j;
        }
        return res;
    }

    public void open(int i, int j){
        checkInput(i, j);
        // do nothing if it is already open
        if (openGrid[i][j])
            return;
        // set the field to open
        openGrid[i][j] = true;
        // set full if it is in the first row
        if (i == 0)
            fullGrid[i][j] = true;
        int[][] neighs = getNeighs(i, j);
        // for every neighboring point, check if it is open. If it is, connect
        // them.
        for (int[] n : neighs) {
            if (openGrid[n[0]][n[1]]) {
            unionList.union(pointToNum(i, j), pointToNum(n[0], n[1]));
        }
        }
    }

    private void checkInput(int i, int j) {
        if ((i < 1 || i > n || j < 1 || j > n))
            throw new IndexOutOfBoundsException();
    }

    public boolean isOpen(int i, int j) {
        checkInput(i, j);
        return openGrid[i][j];
    }

    public boolean isFull(int i, int j) {
        checkInput(i, j);
        return fullGrid[i][j];
    }

    public boolean percolates() {
        return percolates;
    }

    public static void main(String[] args) {
        System.out.println("for testing");
        Percolation p = new Percolation(1);
    }
}
