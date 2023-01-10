package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    boolean [][] field;
    WeightedQuickUnionUF set;
    int opensites;
    public Percolation(int N) {
        field = new boolean[N][N + 2];
        opensites = N*N;
        set = new WeightedQuickUnionUF((N*N) + N * 2);
    }
    public void open(int row, int col) {
        if(!field[row][col]){
            field[row][col] = true;
            opensites -= 1;
            if(row == 0){
                set.union(xyTo1D(row, col), 0);
            }
            if(row == field.length - 1){
                set.union(xyTo1D(row, col), field.length);
            }
            if(row - 1 >= 0 && isOpen(row - 1,col)) {
                set.union(xyTo1D(row - 1, col), xyTo1D(row, col));
            }
            if(row + 1 < field.length && isOpen(row + 1,col)) {
                set.union(xyTo1D(row + 1, col), xyTo1D(row, col));
            }
            if(col > 0 && isOpen(row,col - 1) ) {
                set.union(xyTo1D(row , col - 1), xyTo1D(row, col));
            }
            if(col <= field[row].length && isOpen(row,col + 1)) {
                set.union(xyTo1D(row, col + 1), xyTo1D(row, col));
            }
        }
    }
    public boolean isOpen(int row, int col){
        return field[row][col];
    }
    public boolean isFull(int row, int col){
        if(isOpen(row, col)){
            return(set.connected(xyTo1D(row, col), 0));

        }
        return false;
    }
    public int xyTo1D(int r, int c){
        return ((r + 1) * field.length) + c;
    }

    public int numberOfOpenSites(){
        return opensites;
    }
    public boolean percolates(){
        return(set.connected(0, field.length));
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
    }
}
