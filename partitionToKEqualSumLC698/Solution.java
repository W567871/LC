package partitionToKEqualSumLC698;

import java.util.*;

// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/description/

// First working version by using binary decision tree instead of non-binary dfs tree
// used by Neetcode and others:
// https://www.youtube.com/watch?v=mBk4I0X46oI&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=16 (Python)
// https://www.youtube.com/watch?v=8t8TeyRJDvk (Java code: https://github.com/Sunchit/Coding-Decoded/blob/master/September2021/PartitiontoKEqualSumSubsets.java)

// This version is based on binary decision tree( pick a number / not pick a number). It's different from Neetcode's version, which essentially a compressed decision tree.  

// This version was working, but performance was bad, got following below TLE error:
// Time Limit Exceeded
// Last Executed Input
// 151 / 162 testcases passed
// nums =
// [3,9,4,5,8,8,7,9,3,6,2,10,10,4,10,2]
// k =
// 10

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        // boolean res = solution.canPartitionKSubsets(new int[] {4,3,2,3,5,2,1}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[] {2,2,2,2,3,4,5}, 4);
        boolean res = solution.canPartitionKSubsets(new int[] { 1, 1, 1, 1, 2, 2, 2, 2 }, 4);

        System.out.println(res);
    }

    int[] nums;
    int SUM;
    List<Integer> usedNums = new ArrayList<>();

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

        while (usedNums.contains(numPos)) {
            ++numPos;
        }

        if (numPos >= nums.length) {
            return false;
        }

        // if (numPos > 0 && nums[numPos] == nums[numPos - 1] &&
        // !usedNums.contains(numPos - 1)) {
        // return false;
        // }

        if (bucketSum + nums[numPos] <= SUM) {

            usedNums.add(numPos);
            if (partition(buckets, numPos + 1, bucketSum + nums[numPos])) {
                return true;
            }
            ;
            usedNums.remove(usedNums.size() - 1);
        }

        if (partition(buckets, numPos + 1, bucketSum)) {
            return true;
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
