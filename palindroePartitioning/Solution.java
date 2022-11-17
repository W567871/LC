package palindroePartitioning;

//https://leetcode.com/problems/palindrome-partitioning/submissions/844338757/
// Solution based on https://www.youtube.com/watch?v=3jvWodd7ht0&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=5

import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        s.partition("aab");
    }

    public List<List<String>> partition(String s) {
        List<List<String>> results = new ArrayList<>();
        List<String> part = new ArrayList<>();
        dfs(s, results, part, 0);
        return results;
    }

    private void dfs(String s, List<List<String>> results, List<String> part, int start) {

        if (start >= s.length()) {
            results.add(new ArrayList<>(part));
            return;
        }

        // It's wrong to use 2nd loop! 
        // for (int i = start; i < s.length(); ++i) {

            //Partition from every position first, then if partition left part is a 
            //palindrome, then use recursion to find all palindrome from the partition
            // right part. If left part is not a palindrome, stop trying and return 
            for (int j = start + 1; j < s.length() + 1; ++j) {
                if (isPalindrome(s, start, j)) {
                    part.add(s.substring(start, j));
                    dfs(s, results, part, j);

                    // pop out to prepare for next j position
                    part.remove(part.size() - 1);
                }
            }

        // }
    }

    private boolean isPalindrome(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i) != s.charAt(j - 1)) {
                return false;
            }
            ++i;
            --j;
        }
        return true;
    }
}
