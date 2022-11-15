package subSets;
import java.util.*;

// https://leetcode.com/problems/subsets/submissions/844150722/
// This solution is based on https://www.youtube.com/watch?v=REOH22Xwdkk&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=4

// This decision tree solution is based on yes/no decision for each number in nums array in a top to down fashion.
// It will cover all different number combinations of different sizes (0 - nums.length) 

public class Solution2 {
    
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        // starting from index 0
        dfs(results, current, nums, 0);
        return results;
    }

    private void dfs(List<List<Integer>> results, List<Integer> current, int[] nums, int numIdx) {
        
        // base conditions: either all numbers have been used(current.size() == nums.length), or recursion has reached the bottom
        // of decision tree (start >= nums.length)  
        if (current.size() == nums.length || numIdx >= nums.length) {
           
           // make a copy of "current" before add to results !!!
           results.add(new ArrayList<>(current));
           return; 
        }


        //All "yes" and "no" branch recursions will cover all possible combinations of values in nums
        // This is why this code works

        // recursion on "yes" branch (add next number to "current")
        current.add(nums[numIdx]);
        dfs(results, current, nums, numIdx + 1);

        // recursion on "no" branch (doesn't add next number to "current")
        current.remove(current.size() - 1);
        dfs(results, current, nums, numIdx + 1);
    }
}
