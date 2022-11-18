package LC208;

import java.util.*;

// https://leetcode.com/problems/implement-trie-prefix-tree/submissions/845600406/
// https://www.youtube.com/watch?v=oobqoCJlHA0&t=11s

class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();    
    }
    
    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (current.children.get(c) == null) {
                current.children.put(c, new TrieNode());
            }
            
            // make sure always advance "current" no matter if current.children.get(c) == null
            // or not
            current = current.children.get(c);            
        }
        current.endNode = true;
    }
    
    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (current.children.get(c) == null) {
                return false;
            }
            current = current.children.get(c);
        }
        return current.endNode;       
    }
    
    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            if (current.children.get(c) == null) {
                return false;
            }
            current = current.children.get(c);
        }
        return true;               
    }

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean endNode;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */

