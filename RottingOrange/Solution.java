package RottingOrange;

import java.util.*;

class Solution {

    public static void main(String[] args) {
        // int[][] oranges = {{2,1,1}, {1,1,0},{0,1,1}}; // expected "4"
        // int[][] oranges = {{0,2}}; // expected "0"
        // int[][] oranges = {{1,2}}; // expected "1"
        // int [][] oranges = {{2,1,1},{1,1,1},{0,1,2}}; // expected of "2"
        // int[][] oranges = {{2,2,2,1,1}}; // expected "2"
        int[][] oranges = { { 1, 2, 1, 1 } }; // expected "2"

        Solution solution = new Solution();

        System.out.println("Oranges are all rotten in minutes of " + solution.orangesRotting(oranges));
    }

    private int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public int orangesRotting(int[][] grid) {
        int freshOranges = 0;
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (grid[i][j] == 1) {
                    freshOranges++;
                } else if (grid[i][j] == 2) {
                    queue.add(i * grid[0].length + j); // if it's rotting orange, add to queue.
                }
            }
        }

        int minutes = 0;

        while (queue.size() > 0 && freshOranges > 0) {
            int size = queue.size();
            for (int s = 0; s < size; ++s) {
                int r = queue.poll();

                for (int[] direction : directions) {
                    int i = r / grid[0].length + direction[0];
                    int j = r % grid[0].length + direction[1];

                    if (i > -1 && i < grid.length && j > -1 && j < grid[0].length && grid[i][j] == 1) { // if it's valid
                                                                                                        // postion and a
                                                                                                        // fresh orange
                        queue.add(i * grid[0].length + j);
                        grid[i][j] = 2;
                        freshOranges--;
                    }
                }
            }
            minutes++;
        }

        return freshOranges == 0 ? minutes : -1;
    }
}
