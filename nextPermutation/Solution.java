package nextPermutation;

// Leetcode problem: https://leetcode.com/problems/next-permutation/submissions/843058291/
// Solution is based on https://www.youtube.com/watch?v=6qXO72FkqwM&t=9s
// Very hard to figure out correct solution in short time 
// O(n) time and O(0) memory consumption

public class Solution {
    
    public void nextPermutation(int[] nums) {
 
        // empty or one element array just return
        if (nums.length <= 1) {
            return;
        }

        // find the last peak index
        int lastPeak = -1;
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i-1] < nums[i]) {
                lastPeak = i;
            }            
        } 

        // no peak exists, so it's a straight decending array,
        // e.g. [6,5,4,3,2]
        // so no next greater permutation exists, just reverse the array
        // and return the smallest permutation, e.g.
        // [2,3,4,5,6], as the next permuation  
        if (lastPeak == -1) {
            reverse(nums, 0, nums.length - 1); 
            return;     
        }

        // Now the peak exists, then we try to find the smallest number,
        // which is greater than 
        // nums[lastPeak -1], and smaller than the peak value itself. 
        int swapIdx = lastPeak;
        for (int i = lastPeak + 1; i < nums.length; ++i) {
            if (nums[i] > nums[lastPeak - 1]) {
                swapIdx = i;
            }
        }

        //swap two indices to get a next greater permutation than current,
        // but the new permutation might not be the smallest 
        swap(nums, lastPeak - 1, swapIdx);

        // reverse the numbers between lastPeak and the end in order to make
        // sure these numbers are in ascending order, so the new permutation
        // will be the smallest one which is greater than current one (nums)
        reverse(nums, lastPeak, nums.length - 1);
    }

    // reverse the order of the numbers in an array
    // Note end confition: (end - start + 1)/2 + start
    private void reverse(int[] nums, int start, int end) {
        for (int i = start; i < (end - start + 1)/2 + start; ++i) {
            swap(nums, i, end - (i - start));
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
