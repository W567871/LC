package LC1849;
import java.util.*;

// https://leetcode.com/problems/splitting-a-string-into-descending-consecutive-values/submissions/845422291/
//https://www.youtube.com/watch?v=eDtMmysldaw&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=8

// Check Solution2.java for code comments 
class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        // boolean res = solution.splitString("050043");
        // boolean res = solution.splitString("1234");

        // Leetcode always failed with below test case, but it's running fine on 
        // my local, it might be related to Java Long range.
        // Leetcode error:
        // java.lang.NumberFormatException: For input string: "9465072333777578147"
        // at line 67, java.base/java.lang.NumberFormatException.forInputString
        // at line 711, java.base/java.lang.Long.parseLong
        // at line 836, java.base/java.lang.Long.parseLong
        // at line 20, Solution.dfs
        // at line 4, Solution.splitString
        // at line 54, __DriverSolution__.__helper__
        // at line 84, __Driver__.main        
        boolean res = solution.splitString("9465072333777578147");
    
        System.out.println("res = " + res);
    }

    public boolean splitString(String s) {
        List<Long> nums = new ArrayList<>();
        return dfs(s, nums, 0);
    }
    
    private boolean dfs(String s, List<Long> nums, int start) {

        if (start == s.length()) {
            if (nums.size() > 1) {
                return true;
            }
        }

        for (int i = start; i < s.length(); ++i ) {
            if (start == 0 && i == s.length() - 1) {
                return false;
            }

            Long next  = Long.parseLong(s.substring(start, i + 1));
            
            if (nums.size() > 0) {
                Long last = nums.get(nums.size() - 1);
                if (last - next != 1) {
                    continue;
                } 
            } 
            
            nums.add(next);
            if (dfs(s, nums, i + 1)) {
                return true;
            }
            nums.remove(nums.size() - 1);            
        }
        return false;
    }
}