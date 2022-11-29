package MatchSticksToSqureLC473;

import java.util.*;

// https://leetcode.com/problems/matchsticks-to-square/submissions/851906613/
// this problem is almost exactly same as "Partition to K Equal Sum Subsets - Backtracking - Leetcode 698"
// Check my code comments there to understand this code. This class is almost same as my Solution5.java in  "Partition to K Equal Sum Subsets - Backtracking - Leetcode 698" with a minor difference inside for-loop to check if current bucket sum is already over the target, instead of leaving the check outside the loop and at the top of partition() method.

// Average run time is about 4 ms, pretty good
//https://leetcode.com/problems/matchsticks-to-square/submissions/851906613/
// Runtime 4 ms Beats 98.1% Memory 40.4 MB Beats 85.18%

// https://leetcode.com/problems/matchsticks-to-square/submissions/851906371/
// Runtime 4 ms Beats 98.1% Memory 39.9 MB Beats 95.52%

public class Solution {

    int[] nums;
    boolean[] used;
    int target;

    public boolean makesquare(int[] matchsticks) {
        if (matchsticks == null || matchsticks.length == 0) {
            return false;
        }

        int sum = Arrays.stream(matchsticks).sum();
        if (sum % 4 != 0) {
            return false;
        }

        target = sum / 4;

        nums = matchsticks;
        Arrays.sort(nums);
        reverse(nums);
        if (nums[0] > target) {
            return false;
        }

        used = new boolean[matchsticks.length];

        return partition(0, 4, 0);
    }

    private boolean partition(int start, int sides, int sum) {
        if (sides == 0) {
            return true;
        }

        if (sum == target) {
            return partition(0, sides - 1, 0);
        }

        // this is redundant since we already check this inside the loop below 
        if (sum > target) {
            return false;
        }

        if (start > nums.length) {
            return false;
        }

        for (int i = start; i < nums.length; ++i) {
            if (used[i]) {
                continue;
            }

            if (i > 0 && !used[i - 1] && nums[i] == nums[i - 1]) {
                continue;
            }


            // Add below check inside the loop, reduced runtime from 8ms to 4ms, it seems the extra check helps avoid extra the recursive call which will for sure return false, so it avoids stack prep and unwinding costs properly.            
            //https://leetcode.com/problems/matchsticks-to-square/submissions/851904601/
            // https://leetcode.com/problems/matchsticks-to-square/submissions/851905130/
            // Runtime 8 ms

            //https://leetcode.com/problems/matchsticks-to-square/submissions/851906371/
            // https://leetcode.com/problems/matchsticks-to-square/submissions/851906613/
            // Runtime 4 ms
            if (sum + nums[i] > target) {
                continue;
            } else {
                used[i] = true;


                // Remember to call partition() with "i +1", not "start". Even if the final result is correct, but performance difference is HUGE. runtime was TLE or 2762 ms with "start", while run time was 4-7 ms with "i + 1" !!!
      
                // https://leetcode.com/problems/matchsticks-to-square/submissions/851858096/
                // Runtime 2762 ms Beats 5.11%

                //https://leetcode.com/problems/matchsticks-to-square/submissions/851906371/
                // Runtime 4 ms Beats 98.1%

                // if (partition(start, sides, sum + nums[i])) {     
                if (partition(i + 1, sides, sum + nums[i])) {
                    return true;
                }
                used[i] = false;
            }
        }

        return false;

    }

    private void reverse(int[] nums) {
        int tmp;
        for (int i = 0; i < nums.length / 2; ++i) {
            tmp = nums[i];
            nums[i] = nums[nums.length - i - 1];
            nums[nums.length - i - 1] = tmp;
        }
    }
}
