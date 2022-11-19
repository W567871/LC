package coinChange;

import java.util.Arrays;

// https://leetcode.com/problems/coin-change/

// This is a DP problem: for total m coins with values C1,C2,C3,...,Cm, 
// f(amt) = 1+ min(f(amt-C1), f(amt-C2), f(amt-C3),..., f(amt-Cm)), for 
// each amt value from 1, 2, 3, 4, 5, ... , amount.


class Solution {
    public int coinChange(int[] coins, int amount) {
        if (amount < 0) {
            return -1;
        }
        int[] results = new int[amount + 1];
        Arrays.fill(results, Integer.MAX_VALUE);

        // set index 0 to value 0 to make calculation easier for the starting
        // conditions easier amt=1,2,...,Cm.
        results[0] = 0;

        for (int amt = 1; amt < amount + 1; ++amt) {
            for (int c : coins) {
                int prevAmt = amt - c;
                if (prevAmt < 0) {
                    continue;
                }

                if (results[prevAmt] != Integer.MAX_VALUE && results[amt] > results[prevAmt] + 1) {
                    results[amt] = results[prevAmt] + 1;
                }
            }
        }

        if (results[amount] == Integer.MAX_VALUE) {
            return -1;
        }

        return results[amount];
    }
}