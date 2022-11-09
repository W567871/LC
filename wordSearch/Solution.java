package wordSearch;

import java.util.*;
// https://leetcode.com/problems/word-search/

class Solution {

    private static char[][] board;
    private static  String word;
    private static int width;
    private static int height;
    private static Set<String> pathSet = new HashSet<>();

    public boolean exist(char[][] board, String word) {
        Solution.board = board;
        Solution.word = word;
        width = board[0].length;
        height = board.length;

        Set<Character> wordChars = new HashSet<>();
        for (char c :word.toCharArray()) {
            wordChars.add(c);
        }

        Set<Character> boardChars = new HashSet<>();
        for (int i = 0; i < height ; i++) {
            for (int j = 0; j < width; j++) {
                boardChars.add(board[i][j]);
            }
        }

        // Check if all word characters are contained by the board, 
        // and if not, short-circuit to return false.
        // DFS is time consuming (complexity: width * height * 4 ^ word.lengh()).
        // Leetcode submit got "Time limit passed error" without this.
        // For example, board = [[a,a,a,a,a,a,a,a], [a,a,a,a,a,a,a,a], 
        // [a,a,a,a,a,a,a,a], [a,a,a,a,a,a,a,a], [a,a,a,a,a,a,a,a], [a,a,a,a,a,a,a,a]],
        // word = "aaaaaaab"
        if (!boardChars.containsAll(wordChars)) {
            return false;
        }

        for (int i = 0; i < height ; i++) {
            for (int j = 0; j < width; j++) {
                if (dfs(i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(int row, int col, int idx) {
        if (idx == word.length()) {
            return true;
        }

        if (row < 0 || col < 0 || 
            row >= height || col >= width || 
            pathSet.contains(String.valueOf(row) + "," + col) ||
            word.charAt(idx) != board[row][col]) {
              return false;
        } 

        pathSet.add(String.valueOf(row) + "," + col);

        boolean next = false;
        int[][] directions = { {0,-1}, {0,1}, {-1,0}, {1,0}};
        for ( int[] d : directions) {
            if (dfs(row + d[0], col + d[1], idx +1)) {
                next = true;
                break;    
            }
        }
        pathSet.remove(String.valueOf(row) + "," + col);
        return next;
    }

}