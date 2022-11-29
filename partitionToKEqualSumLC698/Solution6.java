package partitionToKEqualSumLC698;
import java.util.*;

// Solution6 is a further improvement on top of Solution5 by adding another optimization: memoizing partialing searching results by cache.
// The idea is similar to the memoizing in Solution2.java

// But surprisingly, the performance of Solution6 is actually worse than Solution5 (without memoizing).
// It uses more memory, but much slower than Solution5 (about 20 times slower).
// Here, adding more optimization (memoizing/caching) make the performance becoming worse than without optimization

// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/851302495/
// Runtime 55 ms Beats 72.73% Memory 47.9 MB Beats 30.74%

// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/851303193/
// Runtime 112 ms Beats 57.48% Memory 73.1 MB Beats 6.58%

// The reason is this cache implemenation is very inefficient by using Map<String, Boolean>(HashMap) as a cache.
// Key value generation in getMemoizeResults(), MemoizeResults() is very inefficient (String concatenation by StringBuilder )
// Since getMemoizeResults() is called so frequently, and every timme it needs to build this key string again (O(n) time), so
// the runtime cost is very high. So this is why the performance is so bad !

public class Solution6 {
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
            // System.out.println(", cachedValUsed = " + cachedValUsed + ", cache size = " + cache.keySet().size() + cache.toString());
            return cacheRes;
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

            memoizeResults(false);
            usedNums[i] = false;
        }

        memoizeResults(false);
        return false;
    }

    private void reverse(int[] nums) {
        for (int i = 0; i < nums.length / 2; ++i) {
            int tmp = nums[i];
            nums[i] = nums[nums.length - 1 - i];
            nums[nums.length - 1 - i] = tmp;
        }
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

}
