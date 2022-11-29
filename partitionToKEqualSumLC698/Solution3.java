package partitionToKEqualSumLC698;

import java.util.*;


// Tried to solve TLE issue in Solution.java by improving performance
// Use Set<Integer> for "usedNums", instead of List<Integer> in Solution.java
// But the performance is better, still too slow to pass TLE 

public class Solution3 {

    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        // boolean res = solution.canPartitionKSubsets(new int[] {4,3,2,3,5,2,1}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[] {2,2,2,2,3,4,5}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[] {1,1,1,1,2,2,2,2}, 4);
        boolean res = solution.canPartitionKSubsets(new int[] { 10, 5, 5, 4, 3, 6, 6, 7, 6, 8, 6, 3, 4, 5, 3, 7 }, 8);

        System.out.println(res);
    }

    int[] nums;
    int SUM;
    Set<Integer> usedNums = new HashSet<>();

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
            usedNums.remove(numPos);
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
