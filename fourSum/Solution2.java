package fourSum;

import java.util.*;

class Solution2 {

    //Difference between Solution and Solution2 is that Solution2 is using one 
    // list "quad" to store all internal results between steps, which reuse the
    // the same list but needs more cleanup (remove()) after each step
    public static void main(String[] args) {

        Solution2 sol = new Solution2();

        // Four Leetcode test cases eventually help get all bugs fixed !
        //sol.fourSum(new int[] {1,0,-1,0,-2,2}, 0);
        // sol.fourSum(new int[] {2,2,2,2}, 8);        
         sol.fourSum(new int[] {2,2,2,2,2}, 8);        
        // sol.fourSum(new int[] {1000000000,1000000000,1000000000,1000000000}, -294967296);                
    }

    public List<List<Integer>> fourSum(int[] nums, long target) {
        int k = 4;
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(nums);
        List<Integer> quad = new ArrayList<>();

        kSum(nums, target, results, k, 0, quad);

        return results;
    }

    private void kSum(int[] nums, long target, List<List<Integer>> results, int k, int start, List<Integer> quad) {
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

                    // use the same "quad" list to store each matching result
                    quad.add(nums[i]); 

                    // Recursive to k-1 level if level > 2
                    kSum(nums, target - nums[i], results, k-1, i + 1, quad);

                    // pop the last value from quad list
                    quad.remove(quad.size()-1);
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

                    // add two matching 2Sum values to quad
                    quad.add(nums[l]);
                    quad.add(nums[r]);

                    results.add(new ArrayList<>(quad));

                    // pop the matching 2Sum values out, so quad it's ready for 
                    // storing the next matching 2Sum values
                    quad.remove(quad.size()-1);
                    quad.remove(quad.size()-1);

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