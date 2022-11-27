package partitionToKEqualSumLC698;

import java.util.*;

public class Solution4 {

    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        // boolean res = solution.canPartitionKSubsets(new int[] {4,3,2,3,5,2,1}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[] {2,2,2,2,3,4,5}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[] {1,1,1,1,2,2,2,2}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[]
        // {10,5,5,4,3,6,6,7,6,8,6,3,4,5,3,7}, 8);
        boolean res = solution.canPartitionKSubsets(new int[] { 10, 10, 7, 8, 10, 4, 9, 7, 9, 10, 4, 6, 7, 1, 8, 5 },
                5); // true

        System.out.println(res);
    }

    int[] nums;
    int SUM;
    boolean[] usedNums;
    int trueNums;

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

        while (numPos < nums.length && usedNums[numPos]) {
            ++numPos;
        }

        if (numPos >= nums.length) {
            return false;
        }

        if (trueNums > 0 && numPos > 0 && nums[numPos] == nums[numPos - 1] && !usedNums[numPos - 1]) {
            return false;
        }

        if (bucketSum + nums[numPos] <= SUM) {

            usedNums[numPos] = true;
            ++trueNums;
            if (partition(buckets, numPos + 1, bucketSum + nums[numPos])) {
                return true;
            }
            ;
            usedNums[numPos] = false;
            --trueNums;
        } else {
            while (numPos < nums.length - 1 && bucketSum + nums[numPos + 1] > SUM) {
                ++numPos;
            }
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
