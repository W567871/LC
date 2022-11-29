package partitionToKEqualSumLC698;

import java.util.*;

// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/description/
// https://www.youtube.com/watch?v=mBk4I0X46oI&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=16 (Python)

// This version was trying to resolve TLE error in previous Solution.java by introducing caching as discussed in 
// the comments of Neetcode's video. The idea was trying to cache the intermediatary computing results (memoizing)
// in order to improve performance.

// The idea is to use "usedNums" array values as key, and partition result (true/false) as the cached value (e.g. truetruetruetruetruefalsetruetruetruetruefalsetruefalsefalsetruetrue=false), which means if we select current numbers,
// can we get a valid partition result out of it. 
// why caching works? 
// for example, if input nums are { 4,1,1,2,5,5}, and k =3, then we will hit cache by this number selection 
// (k=2, 4,2,1,1), which will return the cached result from the previous result of (k=3, 4,1,1,2).   

// In fact, cache hitting is NOT very high. For example, for 17 nums problem ({ 4,5,9,3,10,2,10,7,10,8,5,9,4,6,4,9 }, k= 5), cache had been hit 
// only 48 times, with cache size of 136.

// The idea is borrowed from Solution2.py, which was inspired by commens in Neetcode's video 

// This cache only version took about 450ms run time on Leetcode:
// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/851399323/

// Runtime 477 ms Beats 43.48% Memory 50 MB Beats 29.7%
// But it's still consistently accepted by Leetcode, which solved TLE error in Solution.java (which doesn't have any optimization)


public class Solution2 {

    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        // boolean res = solution.canPartitionKSubsets(new int[] {4,3,2,3,5,2,1}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[] {2,2,2,2,3,4,5}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[]
        // {3,3,10,2,6,5,10,6,8,3,2,1,6,10,7,2}, 6);
        // boolean res = solution.canPartitionKSubsets(new int[] { 1, 1, 1, 1, 2, 2, 2, 2 }, 4);

        // boolean res = solution.canPartitionKSubsets(new int[] { 724,3908,1444,522,325,322,1037,5508,1112,724,424,2017,1227,6655,5576,543 }, 4);
         boolean res = solution.canPartitionKSubsets(new int[] { 4,5,9,3,10,2,10,7,10,8,5,9,4,6,4,9 }, 5);
        // boolean res = solution.canPartitionKSubsets(new int[] { 4,1,1,2,5,5}, 3);

        System.out.println("cachedValUsed = " + solution.cachedValUsed + ", cache size = " + solution.cache.keySet().size() + solution.cache.toString());
        System.out.println("\n" + res);
    }

    int[] nums;
    int SUM;
    boolean[] usedNums;
    Map<String, Boolean> cache = new HashMap<>();

    int cachedValUsed;

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

        Boolean cacheRes = getMemoizeResults();
        if (cacheRes != null) {
            ++cachedValUsed;
            // System.out.println("cachedValUsed = " + cachedValUsed + ", cache size = " + cache.keySet().size());
            // System.out.println(", cachedValUsed = " + cachedValUsed + ", cache size = " + cache.keySet().size() + cache.toString());
            return cacheRes;
        }

        if (bucketSum == SUM) {
            boolean res = partition(buckets - 1, 0, 0);
            memoizeResults(res);
            return res;
        }

        while (numPos < nums.length && usedNums[numPos]) {
            ++numPos;
        }

        if (numPos >= nums.length) {
            return false;
        }

        if (bucketSum + nums[numPos] <= SUM) {

            usedNums[numPos] = true;

            if (partition(buckets, numPos + 1, bucketSum + nums[numPos])) {
                return true;
            } 

            memoizeResults(false);
            usedNums[numPos] = false;
        }

        if (partition(buckets, numPos + 1, bucketSum)) {
            // memoizeResults(true, bucketSum);
            return true;
        }

        memoizeResults(false);
        return false;
    }

    private Boolean getMemoizeResults() {
        StringBuilder str = new StringBuilder();
        for (boolean b : usedNums) {
            str.append(b);
        }
        String key = str.toString();
        return cache.get(key);
    }

    private void memoizeResults(boolean res) {
        StringBuilder str = new StringBuilder();
        for (boolean b : usedNums) {
            str.append(b);
        }
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
