package partitionToKEqualSumLC698;

import java.util.*;

// This version is similar to Solution4.java, but instead of using binary decision tree, Solution5 is using the decision tree equavalent for-loop based iterative recursion, which should be a little bit more efficient than binary decision tree, because for-loop reduced the total number of recursive calls. But this for-loop apporach is exactly equavalent to the non for-loop binary tree Solution4.

// This version also has the unused previous value optimization (nums[i] == nums[i - 1] && !usedNums[i - 1])
// So far, Solution5 is the fasted solution: about 6 ms run time 
// the speed of Solution5 and Solution6 are about the same, both are around 6-7 ms.

// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/849938921/
// Runtime 6 ms Beats 88.58% Memory 40 MB Beats 87.20%



public class Solution5 {

    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        // boolean res = solution.canPartitionKSubsets(new int[] {4,3,2,3,5,2,1}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[] {2,2,2,2,3,4,5}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[] {1,1,1,1,2,2,2,2}, 4);
        boolean res = solution.canPartitionKSubsets(new int[] { 10, 5, 5, 4, 3, 6, 6, 7, 6, 8, 6, 3, 4, 5, 3, 7 }, 8);

        System.out.println(res);
    }

    int[] nums;
    int SUM;
    boolean[] usedNums;

    public boolean canPartitionKSubsets(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        SUM = Arrays.stream(nums).sum();

        if (SUM % k != 0) {
            return false;
        }
        SUM /= k;

        Arrays.sort(nums);
        reverse(nums);
        if ((nums[0]) > SUM) {
            return false;
        }

        this.nums = nums;

        usedNums = new boolean[nums.length];

        return partition(k, 0, 0);
    }

    private boolean partition(int buckets, int numPos, int bucketSum) {
        if (buckets == 0) {
            return true;
        }

        if (bucketSum == SUM) {
            boolean res = partition(buckets - 1, 0, 0);
            return res;
        }

        if (numPos >= nums.length) {
            return false;
        }

        if (bucketSum > SUM) {
            return false;
        }

        for (int i = numPos; i < nums.length; ++i) {
            if (usedNums[i]) {
                continue;
            }

            if (i > 0 && nums[i] == nums[i - 1] && !usedNums[i - 1]) {
                continue;
            }

            usedNums[i] = true;
            if (partition(buckets, i + 1, bucketSum + nums[i])) {
                return true;
            }
            ;
            usedNums[i] = false;
        }

        return false;
    }

    private void reverse(int[] nums) {
        for (int i = 0; i < nums.length / 2; ++i) {
            int tmp = nums[i];
            nums[i] = nums[nums.length - 1 - i];
            nums[nums.length - 1 - i] = tmp;
        }
    }
}
