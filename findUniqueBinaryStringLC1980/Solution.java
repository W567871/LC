package findUniqueBinaryStringLC1980;

import java.util.*;

// https://leetcode.com/problems/find-unique-binary-string/submissions/847825812/

// I didn't watch Neetcode's solution until after submitted my solution to Leetcode,
// but looks like our solutions have the same idea: 
// Neetcode solution: https://www.youtube.com/watch?v=aHqn4Dynd1k&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=13


public class Solution {

    Set<String> numSet = new HashSet<>();
    int numLen; 
    String[] nums;
    StringBuilder next = new StringBuilder();
    public String findDifferentBinaryString(String[] nums) {
        this.nums = nums;
        Arrays.stream(nums).forEach(numSet::add);
        numLen = nums[0].length();
        return dfsFind(0);
    }   

    // Use dfs to generate all possible lengh N binary string one by one until a 
    // string not exists in numSet has been generated 
    // Backtracking or recursive function can short-circuit and return value to 
    // stop the backtracking or recursive process, like what's shown here 
    private String dfsFind(int pos) {
      
        if (pos == nums.length) {
            // binary tree search (dfs) has reached leaf, we need to
            // check if we get the correct result
            if (!numSet.contains(next.toString())) {
                return next.toString(); 
            } else {
                // continue search if wrong result
                return null;
            }            
        }

        // Binary search with two branches: "0" and "1",
        // go to each branch to do dfs search
        for (int i = 0; i < 2; ++i) {
            
            //add current digit choice to the result
            next.append(i);
            
            // add next digit until the result reaches lengh N
            String more = dfsFind(pos + 1);

            // after adding next digit, if a valid result has been found,
            // short-circuit and stop search to return the result
            if (more != null) {
                return more;
            }
            
            // if not found a valid result from current digit choice, we
            // start to backtrack and try the next digit choice 
            next.deleteCharAt(next.length() - 1);
        }

        // if reach here, it means we exhaust all choices and still could not 
        // find a valid result to return, so basically, the whole search has failed
        return null;
    }
}
