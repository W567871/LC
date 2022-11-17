package letterCombinationPhoneNum;
import java.util.*;

// https://leetcode.com/problems/letter-combinations-of-a-phone-number/submissions/844981471/
//https://www.youtube.com/watch?v=0snEunUacZY&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=6


public class Solution {
    public List<String> letterCombinations(String digits) {
        
        // Map<Character, String> seems to be a better choice than Map<Integer, String>,
        // because "digits" is a string, not a  int[]
        Map<Character, String> numChars = new HashMap<>();
        numChars.put('2', "abc");       
        numChars.put('3', "def");       
        numChars.put('4', "ghi");       
        numChars.put('5', "jkl");       
        numChars.put('6', "mno");       
        numChars.put('7', "qprs");       
        numChars.put('8', "tuv");       
        numChars.put('9', "wxyz");
        
        List<String> results = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        // Be careful, check problem examples for edge cases
        if (digits==null || digits.isEmpty()) {
            return results;
        }

        dfs(numChars, digits, results, current, 0);
        return results;
    } 

    // StringBuilder seems works better than List<Character> as type of "current"
    private void dfs(Map<Character, String> numChars, String digits, List<String> results, StringBuilder current, int idx) {

            if (digits.length() == current.length()) {
                results.add(current.toString());
                return;
            }

            // Exporle each char choice one by one for digits[idx] 
            for (char c : numChars.get(digits.charAt(idx)).toCharArray()) {
                current.append(c);
                
                dfs(numChars, digits, results, current, idx + 1);

                // clean up for backtracking to previous recursion level to explore a different branch
                current.deleteCharAt(current.length() -1);
            }
    }
}
