package wordSearch2LC212;
import java.util.*;

// https://leetcode.com/problems/word-search-ii/
// https://leetcode.com/problems/word-search-ii/submissions/846362827/
// https://www.youtube.com/watch?v=asbcE9mZz_U&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=9

// Neetcode solution above failed with TLE error. Extra trie pruning is added to further 
// improve performance !!!  Above Neetcode video's comments explain the idea of pruning trie
// after each word has been found. So I added prune(String word) method to TrieNode class

public class Solution2 {
    private int ROW;
    private int COL;

    // results can be List now, because the same word won't be added again since after add new prune(word),
    // the same word won't be added again during the search ( by removing the word from the search list)
    // private Set<String> results = new HashSet<>();
    private List<String> results = new ArrayList<>();

    // Leave shared variables as instance variables reduce number of method parameters of dfs()
    private StringBuilder curWrd = new StringBuilder();
    private Set<String> visited = new HashSet<>();
    private TrieNode trie;
    private char[][] board;
    private TrieNode root;

    public List<String> findWords(char[][] board, String[] words) {
        ROW = board.length;
        COL = board[0].length;
        root = new TrieNode();

        // Initialize tree
        Arrays.stream(words).forEach(w -> root.insert(w));
        this.board = board;

        for (int i = 0; i < ROW; ++i) {
            for (int j = 0; j < COL; ++j) {
                // int prevSize = results.size();
                trie = root;                
                dfs(i,j);
                curWrd = new StringBuilder();
                visited = new HashSet<>();

                // No need to rebuild the tree after added prune(word)     
                // if (results.size() > prevSize) {
                //     rebuildTrie(words);
                // }
            }
        }

        return results; 
        // return new ArrayList<String>(results); 
    }  
    
    // private void rebuildTrie(String[] words) {
    //     trie = new TrieNode();
    //     for (String word : words) {
    //         if (!results.contains(word)) {
    //             trie.insert(word);
    //         }
    //     }
    // }

    private void dfs(int i, int j) {
        String visitedPos = i + "," +j;

        // trie.children.get(board[i][j]) == null will check if there is any word
        // left left to check from trie
        if (i < 0 || j < 0 || i >= ROW || j >= COL || visited.contains(visitedPos)
         || trie.children.get(board[i][j]) == null) {
            return;
        }

        // Save current trie search position in order to restore it after searching 
        // this node. Alternatively, we can pass curTrie as a parameter of dfs()
        // like i, j, which I think it should be a better choice since "trie"
        // behaves exactly like i, j
        TrieNode curTrie = trie;

        curWrd.append(board[i][j]);

        //advance trie search to the next position
        trie = trie.children.get(board[i][j]);

        // Found a complete word 
        if (trie.endNode) {
            String wordStr = curWrd.toString(); 
            results.add(wordStr);
            // trie.endNode = false; // unnecessary due to trie word pruning

            // prune trie immediately to reduce the overall search space
            root.prune(wordStr);  
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

        // Try to prune the newly found word branch from its trie by checking if its nodes and children 
        // branches are shared by other words. If not, remove the nodes and branches until reach a node
        // which has been shared by other words.       
        void prune(String word) {
            List<TrieNode> nodes = new LinkedList<>();
            TrieNode cur = this;
            nodes.add(this);

            char[] chars = word.toCharArray();
            for (Character c : chars) {                
                nodes.add(cur.children.get(c));
                cur = cur.children.get(c);
            }

            // Cutting from bottom up to find all noded which are not shared by other words
            for (int i = nodes.size() - 1; i > 0 ; --i) {
                
                // if current node is not shared by other words, remove the link at the parent
                // node, so its parent node can be removed in next loop if not shared with others
                if (nodes.get(i).children.isEmpty()) {
                    nodes.get(i - 1).children.remove(chars[i - 1]);
                } else {
                    
                    //found a shared node at the lowest level, so stops prune because all its parents
                    // will be shared
                    break;
                }
            }                        
        }
    }    
}
