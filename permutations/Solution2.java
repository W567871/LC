
// Another solution based on this:
// https://www.youtube.com/watch?v=s7AvT7cGdSo&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=2
// This is a less efficient impl than my solution in Solution.java

package permutations;

import java.util.*;
import java.util.stream.Collectors;

public class Solution2 {
    public List<List<Integer>> permute(int[] nums) {

        // Below tried to create list from primitive array nums, but the return 
        // object is actually ArrayList<Integer>, which is not LinkedList<Integer>
        // because we need an Queue to be able to pop element from head
        // List<Integer> numsQueue = Arrays.stream(nums).boxed().collect(Collectors.toList());
        
        //Below is using LinkedList::new in order to 
        // return a LinkedList instead of ArrayList
        Queue<Integer> numsQueue = Arrays.stream(nums).boxed().collect(Collectors.toCollection(LinkedList::new));
        
        //Second way to create a LinkedList/Queue from primitive array
        // Queue<Integer> numsQueue = new LinkedList<>();        
        // Populate list from primitive array
        // Arrays.stream(nums).boxed().forEach(e -> numsQueue.add(e));

        return dfs(numsQueue);
        
    }    

    private List<List<Integer>> dfs(Queue<Integer> numsQueue) {
        List<List<Integer>> results = new ArrayList<>();
        if (numsQueue.size() == 1) {
            List<Integer> current = new ArrayList<Integer>();
            current.add(numsQueue.element());
            results.add(current);
            return results;
        }

        for (int i = 0; i < numsQueue.size(); ++i) {

            //Get and remove head element from Queue/LinkedList by poll()
            Integer n = numsQueue.poll();
            List<List<Integer>> perms = dfs(numsQueue);

            // p.add(n) will add element from the tail
            perms.stream().forEach(p -> p.add(n));

            // merge two lists
            results.addAll(perms);
            
            numsQueue.add(n);
        }        
        return results;
    } 
}
