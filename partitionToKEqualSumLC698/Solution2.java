package partitionToKEqualSumLC698;

import java.util.*;

// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/description/
// https://www.youtube.com/watch?v=mBk4I0X46oI&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=16 (Python)

// This version was trying to resolve TLE error in previous Solution.java by introducing caching as discussed in 
// the comments of Neetcode's video. The idea was trying to cache the intermediatary computing results (memoizing)
// in order to improve performance.

public class Solution2 {

    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        // boolean res = solution.canPartitionKSubsets(new int[] {4,3,2,3,5,2,1}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[] {2,2,2,2,3,4,5}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[]
        // {3,3,10,2,6,5,10,6,8,3,2,1,6,10,7,2}, 6);
        boolean res = solution.canPartitionKSubsets(new int[] { 1, 1, 1, 1, 2, 2, 2, 2 }, 4);
        System.out.println(res);
    }

    int[] nums;
    int SUM;
    List<Integer> usedNums = new ArrayList<>();
    Map<String, Boolean> cache = new HashMap<>();

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

        Boolean cacheRes = getMemoizeResults();
        if (cacheRes != null) {
            return cacheRes;
        }

        if (bucketSum == SUM) {

            Boolean res = getMemoizeResults();
            if (res != null) {
                return res;
            }

            res = partition(buckets - 1, 0, 0);
            memoizeResults(res);
            return res;
        }
        // if (bucketSum == SUM) {
        // return partition(buckets - 1, 0, 0);
        // }

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

        StringBuilder str = new StringBuilder();
        usedNums.stream().forEach(e -> str.append(e + ","));
        String key = str.toString();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        if (bucketSum + nums[numPos] <= SUM) {

            usedNums.add(numPos);

            if (getMemoizeResults() != null) {
                return getMemoizeResults();
            }

            if (partition(buckets, numPos + 1, bucketSum + nums[numPos])) {
                memoizeResults(true);
                return true;
            } else {
                memoizeResults(false);
            }

            usedNums.remove(usedNums.size() - 1);
        }

        if (getMemoizeResults() != null) {
            return getMemoizeResults();
        }

        if (partition(buckets, numPos + 1, bucketSum)) {
            memoizeResults(true);
            return true;
        }

        memoizeResults(false);
        return false;
    }

    private Boolean getMemoizeResults() {
        StringBuilder str = new StringBuilder();
        usedNums.stream().forEach(e -> str.append(e + ","));
        String key = str.toString();
        return cache.get(key);
    }

    private void memoizeResults(boolean res) {
        StringBuilder str = new StringBuilder();
        usedNums.stream().forEach(e -> str.append(e + ","));
        String key = str.toString();
        if (!cache.containsKey(key)) {
            cache.put(key, res);
        }
    }

    private void reverse(int[] nums) {
        for (int i = 0; i < nums.length / 2; ++i) {
            int tmp = nums[i];
            nums[i] = nums[nums.length - 1 - i];
            nums[nums.length - 1 - i] = tmp;
        }
    }
}
