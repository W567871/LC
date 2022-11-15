package subSets;
import java.util.*;

//https://leetcode.com/problems/subsets/submissions/843715791/

// DP solution based on the comments from "Anish M" and "Martin Sundermeyer"
// of https://www.youtube.com/watch?v=REOH22Xwdkk&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=4

// Very good and efficient solution: build 1st level power set first({[]}), then add 1st number so power set becomes {[],[1]}. Then add 2nd number, so power set becomes {[],[1], [2],[1,2]}. Then add 3rd number, 4th number, ..., so on.   A very clever and elegant solution.

public class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

        // now results only has one empty set
        results.add(new ArrayList<Integer>());
        
        for (int i = 0; i < nums.length; ++i) {

            // Save "results" size in variable "size", because "results" set will keep changing inside the loop !
            int size = results.size();
            
            for (int j = 0; j < size; ++j) {

                //must create a new copy of "results.get(j)", becasue this will be a new set and we don't want to update the old set !!!
                List<Integer> newSet =  new ArrayList<>(results.get(j));
                
                // expand current set by one number
                newSet.add(nums[i]);

                // add the updated set to the "results"
                results.add(newSet);
            }
        }

        return results;

    }    
}
