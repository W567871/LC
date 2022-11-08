
package threeSum;

import java.util.*;

// https://leetcode.com/problems/3sum/
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums); // in place sort to avoid deplicate results and to use double pointer 2Sum !!
        List<List<Integer>> results = new ArrayList<>();

        for (int i = 0; i < nums.length; ++i) {
            int cur = nums[i];

            // avoid duplicate results on the same input values
            // If nums[i-1] doesn't have a 2Sum match, nums[i] won't have match
            // either, because nums[i-1] has a longer subarray to find 2Sum.
            // If nums[i-1[ has a 2Sum match, then we don't care if nums[i-1] 
            // matches or not, since any possible 2Sum match has already been
            // found by nums[i-1], so no need to check for nums[i] if 
            // nums[i] == nums[i -1]
            if (i > 0 && cur == nums[i-1]) {
                continue;
            }

            // left pointer starts from i+i is good enough to capture all
            // valid results, no need to start from 0 again which will produce
            // duplicate results
            int l = i + 1, r = nums.length-1;

            // condition check here will make sure l++, r-- produce valid
            // pointer values
            while (l < nums.length && r > l) {
                int sum = cur + nums[l] + nums[r];
                if (sum < 0) {
                    l++;
                } 
                else if (sum > 0) {
                    r--;
                } else { // sum == 0
                   List<Integer> vals = new ArrayList<>();
                   vals.add(cur);
                   vals.add(nums[l]);
                   vals.add(nums[r]);
                   results.add(vals);
                   
                   // increase left pointer only is enough and while loop will
                   // avoid duplicate results by make sure left pointer has 
                   // a different value than before
                   // This pointer move is different from outer index move at line 22,
                   // because this is for inner pointer move inside 2Sum subarray
                   // after a match is found. So both inner and outer index moves 
                   // are needed   
                   l++;
                   while(l < r && nums[l-1] == nums[l]) {
                        l++;
                   } 
                }
            }
        }
        return results;
    }
}