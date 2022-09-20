package SurroundedRegions;

import java.util.*;

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        // char[][] board =
        // {{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};

        // char[][] board =
        // {{'X','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},{'O','X','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O','O','X','X'},{'O','O','O','O','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O','X'},{'O','O','X','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','X','O'},{'O','O','O','O','O','X','O','O','O','O','X','O','O','O','O','O','X','O','O','X'},{'X','O','O','O','X','O','O','O','O','O','X','O','X','O','X','O','X','O','X','O'},{'O','O','O','O','X','O','O','X','O','O','O','O','O','X','O','O','X','O','O','O'},{'X','O','O','O','X','X','X','O','X','O','O','O','O','X','X','O','X','O','O','O'},{'O','O','O','O','O','X','X','X','X','O','O','O','O','X','O','O','X','O','O','O'},{'X','O','O','O','O','X','O','O','O','O','O','O','X','X','O','O','X','O','O','X'},{'O','O','O','O','O','O','O','O','O','O','X','O','O','X','O','O','O','X','O','X'},{'O','O','O','O','X','O','X','O','O','X','X','O','O','O','O','O','X','O','O','O'},{'X','X','O','O','O','O','O','X','O','O','O','O','O','O','O','O','O','O','O','O'},{'O','X','O','X','O','O','O','X','O','X','O','O','O','X','O','X','O','X','O','O'},{'O','O','X','O','O','O','O','O','O','O','X','O','O','O','O','O','X','O','X','O'},{'X','X','O','O','O','O','O','O','O','O','X','O','X','X','O','O','O','X','O','O'},{'O','O','X','O','O','O','O','O','O','O','X','O','O','X','O','X','O','X','O','O'},{'O','O','O','X','O','O','O','O','O','X','X','X','O','O','X','O','O','O','X','O'},{'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},{'X','O','O','O','O','X','O','O','O','X','X','O','O','X','O','X','O','X','O','O'}};

        char[][] board = { { 'O', 'X', 'X', 'O', 'X' }, { 'X', 'O', 'O', 'X', 'O' }, { 'X', 'O', 'X', 'O', 'X' },
                { 'O', 'X', 'O', 'O', 'O' }, { 'X', 'X', 'O', 'X', 'O' } };

        solution.solve(board);
        System.out.println("board=" + board);
    }

    int width;
    int height;
    private int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public void solve(char[][] board) {
        // solveBFS(board);
        solveDFS(board);  // DFS seems a little bit faster (4ms vs 2ms), but very marginally

    }

    private void init(char[][] board) {
        if (board == null) {
            return;
        }
        width = board[0].length;
        height = board.length;
    }

    private void solveDFS(char[][] board) {
        init(board);
        for (int i = 0; i < height; ++i) {
            markDFS(i, 0, board);
            markDFS(i, width - 1, board);
        }
        for (int i = 0; i < width; ++i) {
            markDFS(0, i, board);
            markDFS(height - 1, i, board);
        }

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'M') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void solveBFS(char[][] board) {
        init(board);

        for (int i = 0; i < height; ++i) {
            markBFS(i, 0, board);
            markBFS(i, width - 1, board);
        }

        for (int i = 0; i < width; ++i) {
            markBFS(0, i, board);
            markBFS(height - 1, i, board);
        }

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'M') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void markDFS(int i, int j, char[][] board) {
        if (board[i][j] != 'O') {
            return;
        }
        board[i][j] = 'M';
        for (int[] direction : directions) {
            int p = i + direction[0];
            int q = j + direction[1];
            if (p < 0 || q < 0 || p > height - 1 || q > width - 1 || board[p][q] != 'O') {
                continue; // don't use "return" here !
            } else {
                markDFS(p, q, board);
            }
        }
    }

    private void markBFS(int i, int j, char[][] board) {
        if (board[i][j] != 'O') {
            return;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(i * width + j);
        board[i][j] = 'M';
        while (queue.size() > 0) {
            int size = queue.size();
            for (int s = 0; s < size; ++s) {
                int e = queue.poll();
                int x = e / width;
                int y = e % width;
                // board[x][y]='M';
                for (int[] direction : directions) {
                    int p = x + direction[0];
                    int q = y + direction[1];
                    if (p < 0 || q < 0 || p > height - 1 || q > width - 1 || board[p][q] != 'O') {
                        continue;
                    } else {
                        board[p][q] = 'M'; // this line is very important. Without this line to remember which nodes
                                           // have been visited,
                        // the speed would be very slow, because it would do many redundant work
                        queue.add(p * width + q);
                    }
                }
            }
        }

    }

}
