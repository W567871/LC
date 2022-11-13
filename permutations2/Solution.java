package permutations2;

import java.util.*;
import java.util.stream.Collectors;

// https://leetcode.com/problems/permutations-ii/submissions/842712013/

public class Solution {

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<Integer> numsList = Arrays.stream(nums).boxed().collect(Collectors.toList());   
        return dfs(numsList);
    }
    

    private List<List<Integer>> dfs(List<Integer> numsList) {
        List<List<Integer>> results = new ArrayList<>();
        
        if (numsList.size() == 1) {
            results.add( new ArrayList<>(numsList));
            return results;
        }

        // use set to track visited numbers
        Set<Integer> visited = new HashSet<>();

        for (int i = 0; i < numsList.size(); ++i) {

            // bypass any duplicate numbers
            if (visited.contains(numsList.get(i))) {
                continue;
            }

            visited.add(numsList.get(i));

            // subNums has all the numbers of nums except at index i
            List<Integer> subNums = new ArrayList<>(numsList.subList(0, i));
            subNums.addAll(numsList.subList(i + 1, numsList.size()));

            // Recursive call to get all permutations on sub number list 
            List<List<Integer>> perms = dfs(subNums);
            
            // append number at index i to each permutation to complete a permutation
            Integer n = numsList.get(i);
            perms.stream().forEach(p -> p.add(n));

            results.addAll(perms);
        }

        return results;
    }
}
