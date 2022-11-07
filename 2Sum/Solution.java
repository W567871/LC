import java.util.HashMap;

package 2Sum;

// https://leetcode.com/problems/two-sum/description/

class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> valIdx = new HashMap<>();
        for (int i = 0; i < nums.length; ++i){
            valIdx.put(nums[i], i);
        }       

        for (int i = 0; i < nums.length; ++i){
            int second = target - nums[i];  // be careful about index and value, don't confuse about them 
            Integer idx = valIdx.get(second);  // Be careful, idx is an Integer from HashMap
            if ( idx!= null && idx != i ) {
                return new int[] {i, idx};  // return daynamic primitive array
            }            
        }
        return null;       
    }
}