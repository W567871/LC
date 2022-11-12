package permutations;

import java.util.*;

class Solution {

    private static List<List<Integer>> results;

    public List<List<Integer>> permute(int[] nums) {

        //Must create new ArrayList instance here instead of instantiate it 
        // at where results is declared, because Leetcode will call "permute"
        // method multiple times for each test cases !!!
        results = new ArrayList<>();

        dfs(nums, new HashSet<>(), new ArrayList<>());
        return results;
    }

    private void dfs(int[] nums, Set<Integer> selections, List<Integer> current) {
        if (selections.size() == nums.length) {
            results.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < nums.length; ++i) {
            if (!selections.contains(nums[i])) {
                current.add(nums[i]);
                selections.add(nums[i]);
            }
            else {
                continue;
            }
            dfs(nums, selections, current);
            selections.remove(current.get(current.size() -1));
            current.remove(current.size() - 1);
        }
    }

}

