package combinations;
import java.util.*;

// https://leetcode.com/problems/combinations/submissions/843510988/
// Solution based on https://www.youtube.com/watch?v=q0s6m7AiM7o&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=14

public class Solution {

    public List<List<Integer>> combine(int n, int k) {        
        List<Integer> combNums = new ArrayList<>();
        List<List<Integer>> results = new ArrayList<>();
        dfs(n, k, combNums, results, 1);
        return results;
    }
    
    private void dfs(int n, int k, List<Integer> combNums, List<List<Integer>> results, int start) {
        if (combNums.size() == k) {
            
            // Remember to add a copy !!!
            results.add(new ArrayList<>(combNums));
        }

        for (int i = start; i < n + 1; ++i) {
            combNums.add(i);

            // recursion from i+1 to avoid duplicate numbers used again
            dfs(n, k, combNums, results, i + 1);
            
            combNums.remove(combNums.size() - 1);   
        }
    }
}
