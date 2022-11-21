package numberOfIslandsLC200;

// https://leetcode.com/problems/number-of-islands/submissions/847255163/

public class Solution {

    char[][] grid;
    int COL; 
    int ROW;
    int count;

    public int numIslands(char[][] grid) {
        this.grid = grid;
        ROW = grid.length;
        COL = grid[0].length;

        //Starting from every possible island seeding position, each of which
        // will grow into a connected island all marked with "M".
        for (int i = 0; i < ROW; ++i ) {
            for (int j = 0; j < COL; ++j) {
                if (grid[i][j] == '1') {
                    ++count;
                    dfs(i, j);
                }
            }
        }

        return count;
    }

    private void dfs(int i, int j) {
        if ( i < 0 || j < 0 || i == ROW || j == COL || grid[i][j] == '0' || grid[i][j] == 'M') {
            return;
        }

        // Replace current node '1' with 'M', which marks this '1' node has been visited and 
        // should belong to current island group 
        grid[i][j] = 'M';

        // Search left, right, up, down to try to grow current island until all connected '1' nodes
        // have been found and marked with 'M' 
        dfs(i - 1, j);
        dfs(i + 1, j);
        dfs(i, j - 1);
        dfs(i, j + 1);
    }
}
