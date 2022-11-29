package partitionToKEqualSumLC698;
import java.util.*;

// Solution8 is trying to further improve cache implemenation performance from Solution7 and Solution6.
// Solution8 is built on top of Solution6, Solution7, by using BitSet type for "usedNums", instead of "boolean[]"
// Also hashcode calculation has been changed to use BitSet's own hashCode() method, instead of "Arrays.hashCode(usedNums)"

// But BitSet change didn't improve speed at all. Solution8 runtime was about 32-34 ms.

// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/851346002/
// Runtime 32 ms Beats 75.96% Memory 47.3 MB Beats 30.92%

// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/851346085/
// Runtime 34 ms Beats 75.78% Memory 47.3 MB Beats 30.98%

// From above runtime results, we can see BitSet didn't improve speed, actually it was a little big slower
// But Solution8 and Solution7 are about the same speed.


public class Solution8 {
    public static void main(String[] args) {
        Solution8 solution = new Solution8();
        // boolean res = solution.canPartitionKSubsets(new int[] {4,3,2,3,5,2,1}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[] {2,2,2,2,3,4,5}, 4);
        // boolean res = solution.canPartitionKSubsets(new int[] {1,1,1,1,2,2,2,2}, 4);
        boolean res = solution.canPartitionKSubsets(new int[] { 10, 5, 5, 4, 3, 6, 6, 7, 6, 8, 6, 3, 4, 5, 3, 7 }, 8);
        System.out.println("cacheValUsed = " + solution.cachedValUsed);
        System.out.println(res);
    }

    int[] nums;
    int SUM;
    BitSet usedNums;
    Map<Integer, Boolean> cache = new HashMap<>();    
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

        usedNums = new BitSet(nums.length);

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
            if (usedNums.get(i)) {
                continue;
            }

            if (i > 0 && nums[i] == nums[i - 1] && !usedNums.get(i - 1)) {
                continue;
            }

            usedNums.set(i);
            if (partition(buckets, i + 1, bucketSum + nums[i])) {
                return true;
            }  
            
            memoizeResults(false);
            usedNums.clear(i);
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
        Integer hash = usedNums.hashCode();
        return cache.get(hash);
    }

    private void memoizeResults(boolean res) {
        Integer hash = usedNums.hashCode();
        if (!cache.containsKey(hash)) {
            cache.put(hash, res);
        }
    }
        
}
