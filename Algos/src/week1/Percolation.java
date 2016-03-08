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

    private boolean[][] openGrid;
    private int n;
    private int n2;
    private boolean percolates;
    private WeightedQuickUnionUF unionList;
    // 2 uf, there is probably a better solution
    private WeightedQuickUnionUF fullList;

    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException();
        n2 = n*n;
        openGrid = new boolean[n][n];
        // for constant lookup of full
        fullList = new WeightedQuickUnionUF(n2 + 1);
        // 2 extra to check faster if top is connected to bottom
        unionList = new WeightedQuickUnionUF(n2 + 2);
        this.n = n;
        // connect first field to the first row, last field to last row
        for (int i = 1; i <= n; i++) {
            unionList.union(0, i);
            unionList.union(n2 + 1, n2  + 1 - i);
        }
    }
    // resolve grid position to position in the unionList
    private int pointToNum(int i, int j) {
        return (i-1)*n + j;
        
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
            res[--cnt][0] = i - 1;
            res[cnt][1] = j;
        }
        if (hasBottom == 1) {
            res[--cnt][0] = i + 1;
            res[cnt][1] = j;
        }
        return res;
    }

    public void open(int i, int j){
        checkInput(i, j);
        // do nothing if it is already open
        if (openGrid[i-1][j-1])
            return;
        int pos = pointToNum(i, j);
        if (i == 1)
            fullList.union(0, pos);
        // set the field to open
        openGrid[i-1][j-1] = true;
        int[][] neighs = getNeighs(i, j);
        // for every neighboring point, check if it is open. If it is, connect
        // them.
        for (int[] n : neighs) {
            if (openGrid[n[0]-1][n[1]-1]) {
                fullList.union(pos, pointToNum(n[0], n[1]));
                unionList.union(pos, pointToNum(n[0], n[1]));
        }
        }
        if (unionList.connected(0, n2 + 1))
            percolates = true;
    }

    private void checkInput(int i, int j) {
        if ((i < 1 || i > n || j < 1 || j > n))
            throw new IndexOutOfBoundsException();
    }

    public boolean isOpen(int i, int j) {
        checkInput(i, j);
        return openGrid[i-1][j-1];
    }
    // check if field 0 is connected to i,j
    public boolean isFull(int i, int j) {
        checkInput(i, j);
        return fullList.connected(0, pointToNum(i, j));
    }

    public boolean percolates() {
        return percolates;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        System.out.println(p.isFull(1, 1));
        p.open(1,1);
        System.out.println(p.isFull(1, 1));
        p.open(3,1);
        p.open(2, 1);
    }
}
