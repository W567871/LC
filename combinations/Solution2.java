package combinations;

import java.util.*;

// https://leetcode.com/problems/combinations/submissions/843510988/
// I came up with this decision tree based solution, which inspired by
// https://www.youtube.com/watch?v=REOH22Xwdkk&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=4 

public class Solution2 {

    public List<List<Integer>> combine(int n, int k) {
        List<Integer> combNums = new ArrayList<>();
        List<List<Integer>> results = new ArrayList<>();
        dfs(n, k, combNums, results, 1);
        return results;
    }

    private void dfs(int n, int k, List<Integer> combNums, List<List<Integer>> results, int i) {

        // when combNums has k numbers, recursion stops
        if (combNums.size() == k) {

            // Remember to make copy of combNums !!!
            results.add(new ArrayList<>(combNums));
            return;
        }

        // This end condition means all candidate numbers are exhausted, so 
        // recursion needs to return. For example, n=4, k=2, when i == 5, it 
        // means all previous decision tree decisions are "no 1", "no 2", "no 3",
        // "no 4", so there is no more candidate numbers left to try. 
        // If no this end condition exists, then it will result to stack overlow, 
        // because if decision tree always choose "no" branch, then no numbers 
        // will be added to combNums (so never reach size k), and recursion 
        // never stops 
        if (i > n) {
            return;
        }

        //Decision tree's "Yes" branch, which add number i to combNums for recurse
        combNums.add(i);
        dfs(n, k, combNums, results, i + 1);

        //Decision tree's "No" branch, which not add i to combNums for recurse
        combNums.remove(combNums.size() - 1);
        dfs(n, k, combNums, results, i + 1);
    }

}
