package partitionToKEqualSumLC698;

import java.util.*;

// Tried to solve TLE issue in Solution.java, Solution3.java by improving performance
// Use "boolean[]" for "usedNums", instead of List<Integer>, or Set<Integer> in Solution.java
// Now, no more TLE, this version was accepted by leetcode.
// Looks like List<Integer> or Set<Integer> are performance bottleneck, since "usedNums" operations have 
// been called so frequently, sees only primitive boolean array provides enough performance to resolve TLE.


// Run time was improved from TLE to beteen 630 - 900 ms by just replace List<Integer> or Set<Integer> with boolean[] without further optimization
// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/849826015/ 
// Runtime 637 ms Beats 41.93% Memory 40 MB Beats 90.49%

//https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/849840345/
// Runtime 878 ms Beats 37.62% Memory 42.2 MB Beats 40.97%

//But  after add the previous value check optimization (if (trueNums > 0 && numPos > 0 && nums[numPos] == nums[numPos - 1] && !usedNums[numPos - 1]) { return false;}), the performance has been improved about 100 times. The running time was reduced to about 6-7ms.

// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/850193939/
// Runtime 6 ms Beats 88.58% Memory 42.1 MB Beats44.50%

//https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/850178407/
//Runtime 7 ms Beats 87.68% Memory 41.6 MB Beats 69.86%

// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/850178840/
// Runtim 7 ms Beats 87.68% Memory 41.9 MB Beats 51.55%

// The means "the previous value check optimization" and "boolean[]" are the most important factors to impact performance and resolve TLE.
// Solution4 has both optimizations and is the binary decision tree version of backtracking solution. 

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

        // SUM must be divisiable by k, otherwise it's impossbile to find k partitions of integer numbers
        if (SUM % k != 0) {
            return false;
        }
        SUM /= k;

        // Sort reversely will improve peformance a lot, 1. it will make bigger numbers to appear first, which will make partitioning fail quickly. 
        // 2. Sorting will make the same value numbers next to each other, which will create the chances for the not used previous value optimization to work properly.   
        Arrays.sort(nums);
        reverse(nums);
        
        // if 1st number is bigger than the target partition sum, then there is no way to get a valid partition
        if ((nums[0]) > SUM) {
            return false;
        }

        this.nums = nums;

        usedNums = new boolean[nums.length];

        return partition(k, 0, 0);
    }

    private boolean partition(int buckets, int numPos, int bucketSum) {
        // a valid parition has been found, this is the only place to return true
        if (buckets == 0) {
            return true;
        }

        if (bucketSum == SUM) {
          // The numbers for one bucket/partition has been found, start to search from the beginning again to find the numbers for next bucket 
            boolean res = partition(buckets - 1, 0, 0);
            return res;
        }

        // if a number has been used previously, go to next one until an unused is found
        while (numPos < nums.length && usedNums[numPos]) {
            ++numPos;
        }

        // if run out of the numbers from the array, it means bucket can't be filled and return false 
        if (numPos >= nums.length) {
            return false;
        }

        // if the previous number is not used, then it means the bucket/partition sum can be satified by include the previous number, and if the current number is the same the previous number, then we know the current number can't be used to satisfy the bucket/partition sum either, so just return false directly. This optimization only works the best if the numbers have been sorted. This optimization was also the biggest performance improvement for this leetcode problem
        // "trueNums > 0" is necessary here, which means this optimization only works if there is at least one number has been added to a bucket, because
        // for binary tree based decision tree backtracking, it's possible the backtracking will go up all the way to the top, and "usedNums" array will be all false. In this case, the recursion should continue from the top "No" branch because the top "Yes" has been tried and no valid partition could be found. So here if "trueNums" <= 0, even if "nums[numPos] == nums[numPos - 1]", we still could not return "false", becase this is the backtracking to the very top of the decision tree, we need to continue to go to the "No" branch, instead of return false and stop the search from the top !  
        if (trueNums > 0 && numPos > 0 && nums[numPos] == nums[numPos - 1] && !usedNums[numPos - 1]) {
            return false;
        }

        if (bucketSum + nums[numPos] <= SUM) {
            // this means we can add the current number to bucket/partition sum, and start to search for the next number
            usedNums[numPos] = true;
            ++trueNums;

            // we are now trying "Yes" brach of binary decision tree on nums[numPos]
            if (partition(buckets, numPos + 1, bucketSum + nums[numPos])) {
                return true;
            }
            ;

            //Here means including "nums[numPos]" to the backet sum has failed, we need to backtracking this decision by unsetting "usedNums[numPos]", and prepare to go to the "No" branch for "nums[numPos]" selection  
            usedNums[numPos] = false;
            --trueNums;
        } else {

            // This is a small optimization: if "bucketSum + nums[numPos + 1] > SUM", then it means we can't include nums[numPos + 1] into the current bucket sum, so just move to next position by incrementing numPos until a small enough number is found or reach the end of input numbers.
            // This optimization actually is not very important and can be optional, because the recursion call right after this (partition(buckets, numPos + 1, bucketSum)), will also check the condition again((bucketSum + nums[numPos] <= SUM)), so we don't have to do this check right now. 
            // we can see, in Solution5.java, we don't have this optimization in code, but the performance is equally as good as the Solutoin4.java.
            while (numPos < nums.length - 1 && bucketSum + nums[numPos + 1] > SUM) {
                ++numPos;
            }
        }

        // we are now trying "No" brach of binary decision tree on nums[numPos]
        if (partition(buckets, numPos + 1, bucketSum)) {
            return true;
        }

        // If both branches from nums[numPos] failes, means we need to backtrack from numPos and go up to the tree to select next branch from the parent node
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
