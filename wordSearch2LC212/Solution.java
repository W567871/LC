package wordSearch2LC212;
import java.util.*;

// This solution still get "Time Limit Exceeded" error in leetcode submission. It passed 63/64 test cases, but 
// still not enough, check Solution2 for extra tree prune in code, because performance optimization put in was
// not enough. Check below submission error
// Solution2 has correct perf improvement to pass Leetcode

// https://leetcode.com/problems/word-search-ii/
// https://leetcode.com/problems/word-search-ii/submissions/846040219/
// https://www.youtube.com/watch?v=asbcE9mZz_U&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=9
public class Solution {

    private int ROW;
    private int COL;

    // Use Set to prevent duplicate words been added because we're not prune trie after
    // found a word during search.
    private Set<String> results = new HashSet<>();
    
    // StringBulder is a better choice than List<Character> for char related manipulation
    private StringBuilder curWrd = new StringBuilder();

    // Remember which nodes have already been searched 
    private Set<String> visited = new HashSet<>();

    private TrieNode trie;
    private char[][] board;
    private TrieNode root;

    public List<String> findWords(char[][] board, String[] words) {
        ROW = board.length;
        COL = board[0].length;
        root = new TrieNode();

        // Populate trie
        Arrays.stream(words).forEach(w -> root.insert(w));
        
        this.board = board;

        for (int i = 0; i < ROW; ++i) {
            for (int j = 0; j < COL; ++j) {
                int prevSize = results.size();
                trie = root;                
                dfs(i,j);
                curWrd = new StringBuilder();
                visited = new HashSet<>();

                // Rebuild trie after each top iteration to prune the newly found words from
                // trie. The rationale is to reduce the search space and a word only needs to 
                // found once.
                // Rebuild trie after find each completed word is not possible, because the 
                // search might still not be completed, so trie node are still be referenced 
                // at frame stacks in middle of search. 
                // Obviously, this optimization is still not enough, needs to prune tree immediately
                // after each word to pass Leetcode, check Solution2 for details.
                if (results.size() > prevSize) {
                    rebuildTrie(words);
                }
            }
        }
        
        return new ArrayList<String>(results); 
    }  
    
    private void rebuildTrie(String[] words) {
        trie = new TrieNode();
        for (String word : words) {

            // Add a word to trie which has NOT already been found 
            if (!results.contains(word)) {
                trie.insert(word);
            }
        }
    }

    private void dfs(int i, int j) {
        String visitedPos = i + "," +j;

        if (i < 0 || j < 0 || i >= ROW || j >= COL || visited.contains(visitedPos)
         || trie.children.get(board[i][j]) == null) {
            return;
        }

        TrieNode curTrie = trie;

        curWrd.append(board[i][j]);
        trie = trie.children.get(board[i][j]);
        if (trie.endNode) {
            results.add(curWrd.toString());

        // part of pruning optimzation by labeling found word node as false to reduce
        // search space, but still not enough 
            trie.endNode = false; 
        }

        visited.add(visitedPos);

        dfs(i - 1, j );
        dfs(i + 1, j);
        dfs(i, j - 1);
        dfs(i, j + 1);

        curWrd.deleteCharAt(curWrd.length() - 1);
        visited.remove(visitedPos);
        trie = curTrie;
    }


    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean endNode;

        void insert(String word) {
            TrieNode current = this;
            for ( char c : word.toCharArray()) {
               if (!current.children.containsKey(c)) {
                    current.children.put(c, new TrieNode());
               } 
               current = current.children.get(c);
            }
            current.endNode = true;
        }
    }
}
