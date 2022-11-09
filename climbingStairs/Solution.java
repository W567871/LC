package climbingStairs;

// https://leetcode.com/problems/climbing-stairs/description/

class Solution {
    public int climbStairs(int n) {
        if ( n == 1 || n == 0 || n == 2) {
            return n;
        }

        // DP problem, which is equivalenet to f3 = f2 + f1,
        // which is a fibonaci series   
        int f1 = 1, f2 = 2, f3 = f2;
        for (int i = 3; i <= n; ++i) {
            f2 = f3; // save old f3
            f3 = f1 + f2;  
            f1 = f2;  // move f2 value to f1
        }
        return f3;
    }
}