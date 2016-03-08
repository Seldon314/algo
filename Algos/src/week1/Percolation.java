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
    // this could be done in a byte array of length n2, resulting in memory gain
    // of 2*n2 (current total is ~11n2 + 32n + 256 bytes)
    // might update later 
    private boolean[][] openGrid;
    private boolean[] topConn;
    private boolean[] bottomConn;
    private int n;
    private int n2;
    private boolean percolates;
    private WeightedQuickUnionUF fullList;

    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException();
        n2 = n*n;
        openGrid = new boolean[n][n];
        topConn = new boolean[n2 + 1];
        bottomConn = new boolean[n2 + 1];
        // manages the components
        fullList = new WeightedQuickUnionUF(n2 + 1);
        this.n = n;
    }
    // resolve grid position to position in the unionList
    private int pointToNum(int i, int j) {
        return (i-1)*n + j;
        
    }
    // return every possible neighboring point
    // horribly long, maybe make it more compact
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
        // vars to track if the (new) root must be updated
        boolean setTop = false;
        boolean setBottom = false;
        int pos = pointToNum(i, j);
        if (i == 1) 
            setBottom = true;
        if (i == n) 
            setTop = true;

        // set the field to open
        openGrid[i-1][j-1] = true;
        int[][] neighs = getNeighs(i, j);
        // for every neighboring point, check if it is open. If it is, connect
        // them and also check if it's connected to top / bottom row
        for (int[] ne : neighs) {
            if (openGrid[ne[0]-1][ne[1]-1]) {
                int nPos = pointToNum(ne[0], ne[1]);
                if (bottomConn[fullList.find(nPos)])
                    setBottom = true;
                if (topConn[fullList.find(nPos)])
                    setTop = true;
                fullList.union(pos, nPos);
            }
        }
        int root = fullList.find(pos);
        if (setBottom)
            bottomConn[root] = true;
        if (setTop)
            topConn[root] = true;
        if (bottomConn[root] && topConn[root])
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
    // check if i,j is connected to the bottom row
    public boolean isFull(int i, int j) {
        checkInput(i, j);
        return bottomConn[fullList.find(pointToNum(i, j))];
    }
    // check if there is a connection between top and bottom row
    public boolean percolates() {
        return percolates;
    }
    // just for testing
    public static void main(String[] args) {
    }
}
