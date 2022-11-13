package permutations2;

import java.util.*;

// Solution based on Neetcode video:
// https://www.youtube.com/watch?v=qhBVWf0YafA&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=3
// This solution is not as intuitive as my solution in Solution.java

// Map based solution to avoid using duplicate numbers again at each recurive level
public class Solution2 {
    public List<List<Integer>> permuteUnique(int[] nums) {
        Map<Integer, Integer> numCount = new HashMap<>();
        Arrays.stream(nums).boxed().forEach(n -> addNum(numCount, n));

        List<List<Integer>> results = new ArrayList<>();
        List<Integer> perm = new ArrayList<>();
        dfs(results, numCount, nums, perm);
        return results;
    }

    private void addNum(Map<Integer, Integer> map, Integer n) {

        // Below two lines are equivalent
        // if (!map.containsKey(n)) map.put(n, 0);
        map.putIfAbsent(n, 0);

        map.put(n, map.get(n) + 1);
    }

    private void dfs(List<List<Integer>> results,
            Map<Integer, Integer> numCount,
            int[] nums,
            List<Integer> perm) {

        // base condition to return        
        if (perm.size() == nums.length) {

            //Must create a copy of perm, because perm is reused !!!
            results.add(new ArrayList<>(perm));
            return;
        }

        // numCount map is used to avoid duplicate numbers are used again
        // by iterating on map keys only. So at any recursive levels,
        // duplicate numbers won't be used for permutations.
        // Map values are number counts, which are used to track if 
        // how many numbers 
        for (int num : numCount.keySet()) {
            if (numCount.get(num) > 0) {

                // this num will be used by current permutation, so decrease its count 
                numCount.put(num, numCount.get(num) - 1);
                
                perm.add(num);

                // recurse on building one step towards a final permutation.
                // This is different from recursion in Solution.java, where each
                // recursion is building all the permutations at a smaller 
                // number set 
                dfs(results, numCount, nums, perm);

                numCount.put(num, numCount.get(num) + 1);
                perm.remove(perm.size() - 1);
            }
        }
    }

}
