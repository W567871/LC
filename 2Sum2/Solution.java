package 2Sum2;

// https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/

class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length-1;

        while (left < numbers.length && right > -1) {            
            int sum = numbers[left] + numbers[right];
            if (sum == target && right - left > 0) {
                return new int[] { ++left, ++right};
            }
            else if (sum < target) {
                left++;
            }
            else if (sum > target) {
                right--;
            } 
            else
                break; 
        }
        return null;
    }
}