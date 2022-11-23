package MaxLengthWithConcanatedStringLC1239;
import java.util.*;

// https://leetcode.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/submissions/848334040/

// Solution1 is an improvement of Solution, which removed unnessary "count" parameter from dfs() method, because charSet.size()
// will able to return the correct character count, no need to use a sepearte variable to track it. 
// Inspiration was from Neetcode: https://www.youtube.com/watch?v=d4SPuvkaeoo&t=52s

public class Solution1 {

    int maxLen;
    List<String> arr;
    Set<Character> charSet = new HashSet<>();

    public int maxLength(List<String> arr) {
        this.arr = arr;
        
        return dfs(0);
    }

    // Decision tree dfs based on try to add next element string or not add next element to generate all possible combinations
    private int dfs(int pos) {
        
        // all "arr" elements have been used now, so has reached tree leaf and need to return
        if (pos == arr.size()) {
            return charSet.size();
        }

        // max count if not try to concatenate arr[pos] element string  
        int countNotAdd = dfs(pos + 1);

        int countAdd = countNotAdd;

        //max count if try to concatenate arr[pos] string
        if (canAdd(arr.get(pos))) {
            addStr(arr.get(pos));

            countAdd = dfs(pos + 1);
            
            // backtracking to try other positions
            removeStr(arr.get(pos));
        } 
                
        // return the greater of two choices (add/not add)
        return countAdd > countNotAdd ? countAdd : countNotAdd;
    }
    
    private boolean canAdd(String s) {

        // below is for edge cases like arr = [ "aa", "bb"], both elements have 
        // duplicate characters, so neither of them are considered as valid concatenation strings
        // Only Strings "has unique characters" can be added to set
        Set<Character> chars = new HashSet<>();
        for ( char c : s.toCharArray()) {
            chars.add(c);
        }
        if (chars.size() != s.length()) {
            return false;
        }

        // check s has any duplicate with existing strings already concatenated
        for (char c : s.toCharArray()) {
            if (charSet.contains(c)) {
                return false;
            }
        }   
        return true;
    }

    private void addStr(String s) {
        for (char c : s.toCharArray()) {
            charSet.add(c);
        }
    }
    
    private void removeStr(String s) {
        for (char c : s.toCharArray()) {
            charSet.remove(c);
        }
    }
    
}
