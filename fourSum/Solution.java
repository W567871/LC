package fourSum;

import java.util.*;

class Solution {

    public static void main(String[] args) {

        Solution sol = new Solution();

        // Four Leetcode test cases eventually help get all bugs fixed !
        // sol.fourSum(new int[] {1,0,-1,0,-2,2}, 0);
        // sol.fourSum(new int[] {2,2,2,2}, 8);        
        // sol.fourSum(new int[] {2,2,2,2,2}, 8);        
        sol.fourSum(new int[] {1000000000,1000000000,1000000000,1000000000}, -294967296);                
    }

    public List<List<Integer>> fourSum(int[] nums, long target) {
        int k = 4;
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(nums);

        kSum(nums, target, results, k, 0);

        return results;
    }

    private void kSum(int[] nums, long target, List<List<Integer>> results, int k, int start) {
        if (k>2) {
                for (int i = start; i < nums.length - 2; ++i ) {
                    
                    // This is to move the outer index for duplicate array 
                    //values to avoid possible duplicate matching results.
                    // No need to check for duplicate array values of nums[i]
                    // and nums[i-1]: if nums[i-1] doesn't have a k-match, then 
                    // nums[i] won't have a k-match either since the subarray 
                    // will be shorter for nums[i] case (less chance to have
                    // a match). If nums[i-1] has a match, then the match from
                    // nums[i] is not needed any more, since the match results
                    // from nums[i-1] should already inclue all matching results
                    // from nums[i]
                    if (i > start && nums[i] == nums[i - 1]) {
                        continue;
                    }

                    // Each level needs to have its own result container
                    List<List<Integer>> subResults = new ArrayList<>();

                    // Recursive to k-1 level if level > 2
                    kSum(nums, target - nums[i], subResults, k-1, i + 1);

                    if (subResults.size() > 0) {
                        final int val = nums[i];

                        // add the current index value to every matching result
                        // from k-1 level  
                        subResults.forEach(e -> e.add(val));
                        results.addAll(subResults);
                } 
            }
        }
        else if (k == 2) {
            // when recursion reaches the last 2 levels, use 2Sum pointer solution
            // to find the 2Sum matching results.
            int l = start, r = nums.length-1;

            while (l < nums.length && l < r) {

                if (l > start && nums[l-1] == nums[l]) {
                    l++;
                    continue;
                }

                long sum = nums[l] + nums[r];
                if (sum < target) {
                    l++;
                } 
                else if (sum > target) {
                    r--;
                } else {
                    List<Integer> subList = new ArrayList<>();
                    subList.add(nums[l]);
                    subList.add(nums[r]);
                    results.add(subList);

                    // This is to move the left pointer forward after found the matching
                    // 2Sum results. This inner pointer move is different from the outer
                    // above at line 41. This move only happens after a match is found
                    // and completely independent from from the outer index move above.
                    // Both are needed in order to get the correct results ! 
                    l++;
                    while (l<r && nums[l] == nums[l - 1]) {
                        l++;
                    }
                }
            }
        }

        return;

    }
}