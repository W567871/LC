package SurroundedRegions;
import java.util.*;

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        // char[][] board = {{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};

        char[][] board = {{'X','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},{'O','X','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O','O','X','X'},{'O','O','O','O','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O','X'},{'O','O','X','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','X','O'},{'O','O','O','O','O','X','O','O','O','O','X','O','O','O','O','O','X','O','O','X'},{'X','O','O','O','X','O','O','O','O','O','X','O','X','O','X','O','X','O','X','O'},{'O','O','O','O','X','O','O','X','O','O','O','O','O','X','O','O','X','O','O','O'},{'X','O','O','O','X','X','X','O','X','O','O','O','O','X','X','O','X','O','O','O'},{'O','O','O','O','O','X','X','X','X','O','O','O','O','X','O','O','X','O','O','O'},{'X','O','O','O','O','X','O','O','O','O','O','O','X','X','O','O','X','O','O','X'},{'O','O','O','O','O','O','O','O','O','O','X','O','O','X','O','O','O','X','O','X'},{'O','O','O','O','X','O','X','O','O','X','X','O','O','O','O','O','X','O','O','O'},{'X','X','O','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O','O','O'},{'O','X','O','X','O','O','O','X','O','X','O','O','O','X','O','X','O','X','O','O'},{'O','O','X','O','O','O','O','O','O','O','X','O','O','O','O','O','X','O','X','O'},{'X','X','O','O','O','O','O','O','O','O','X','O','X','X','O','O','O','X','O','O'},{'O','O','X','O','O','O','O','O','O','O','X','O','O','X','O','X','O','X','O','O'},{'O','O','O','X','O','O','O','O','O','X','X','X','O','O','X','O','O','O','X','O'},{'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},{'X','O','O','O','O','X','O','O','O','X','X','O','O','X','O','X','O','X','O','O'}};


        solution.solve(board);
        System.out.println("board=" + board);
    }


    int width;
    int height;
    private int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public void solve(char[][] board) {
        if (board==null) {
            return;
        }

        width = board[0].length;
        height = board.length;
    
        for (int i=0; i<height; ++i){
            markCell(i, 0, board);
            markCell(i, width-1, board);
        }
  
        for (int i=0; i<width; ++i){
            markCell(0, i, board);
            markCell(height-1, i, board);
        }

        for (int i=0; i<height; ++i){
            for (int j=0; j<width; j++) {
                if (board[i][j]=='O') {
                    board[i][j]='X';
                } else if (board[i][j]=='M') {
                    board[i][j]='O';
                }
            }
        }
    }

    private void markCell(int i, int j, char[][] board) {
        if (board[i][j]!='O') {
            return;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(i*width+j);
        board[i][j]='M';
        while (queue.size()>0) {
            int size = queue.size();
            for (int s=0; s<size; ++s) {
                int e = queue.poll();
                int x = e/width;
                int y = e%width;
                // board[x][y]='M';
                for (int[] direction : directions) {
                    int p = x + direction[0];
                    int q = y + direction[1];
                    if (p<0 || q<0 || p>height-1 || q>width-1 || board[p][q]!='O') {
                        continue;
                    } else {
                        board[p][q]='M';  // this line is very important. Without this line to remember which nodes have been visited,
                                          //  the speed would be very slow, because it would do many redundant work 
                        queue.add(p*width+q);
                    }           
                }
            }
        }

    }

}
