package permutations;
import java.util.*;
import java.util.stream.Collectors;


// This is a better solution than Solution, Solution2, because no unnecessary 
// recursive operations are made like in Solution class. 
public class Solution3 {
    
    public List<List<Integer>> permute(int[] nums) {
        return dfs(Arrays.stream(nums).boxed().collect(Collectors.toList()));       
    }   
    
    private List<List<Integer>> dfs(List<Integer> nums) {
        List<List<Integer>> results = new ArrayList<>();
        if (nums.size() == 1) {            
            results.add(new ArrayList<>(nums)); 
            return results;
        }

        for (int i = 0; i < nums.size(); ++i) {
            //Must create a new ArrayList out of nums.subList(), because in Java,
            // List.subList() only returns a "View" of its original list:
            // "The returned list is backed by this list, so non-structural changes in the returned list are reflected in this list, and vice-versa."
            // https://docs.oracle.com/javase/8/docs/api/java/util/List.html#subList-int-int-
            // The changes you made to the sublist will affect the original LinkedList,
            // which we don't want to happen, so we have to create a copy out of sublist 
            List<Integer> subNums = new ArrayList<>(nums.subList(0, i));
            subNums.addAll(nums.subList(i + 1, nums.size()));
            
            List<List<Integer>> perms = dfs(subNums);
            Integer n = nums.get(i);
            perms.stream().forEach(perm -> perm.add(n));
            results.addAll(perms);
        }

        return results;
    }

}
