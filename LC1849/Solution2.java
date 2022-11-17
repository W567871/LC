package LC1849;
import java.math.BigInteger;
import java.util.*;

//https://leetcode.com/problems/splitting-a-string-into-descending-consecutive-values/submissions/845422291/
//https://www.youtube.com/watch?v=eDtMmysldaw&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=8

// Leetcode always failed with below test case when using Long, but it's running fine on 
// my local, it might be related to Java Long range.
// Leetcode error:
// java.lang.NumberFormatException: For input string: "9465072333777578147"
// at line 67, java.base/java.lang.NumberFormatException.forInputString
// at line 711, java.base/java.lang.Long.parseLong
// at line 836, java.base/java.lang.Long.parseLong
// at line 20, Solution.dfs
// at line 4, Solution.splitString
// at line 54, __DriverSolution__.__helper__
// at line 84, __Driver__.main
// boolean res = solution.splitString("9465072333777578147");

// So I have to use BigInteger to handle this over Long range test case:
// The long data type is a 64-bit two's complement integer. 
// Its value-range lies between -9,223,372,036,854,775,808(-2^63) to 9,223,372,036,854,775,807(2^63 -1)


// This is BigInteger version of Solution.java (Long version)
public class Solution2 {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Some test cases
        // boolean res = solution.splitString("050043");   //true
        // boolean res = solution.splitString("1234");  // false

        // Leetcode always failed with using Long below test case, but it's running fine on 
        // my local, it might be related to Java Long range.
        // Leetcode error:
        // java.lang.NumberFormatException: For input string: "9465072333777578147"
        // at line 67, java.base/java.lang.NumberFormatException.forInputString
        // at line 711, java.base/java.lang.Long.parseLong
        // at line 836, java.base/java.lang.Long.parseLong
        // at line 20, Solution.dfs
        // at line 4, Solution.splitString
        // at line 54, __DriverSolution__.__helper__
        // at line 84, __Driver__.main        
        boolean res = solution.splitString("9465072333777578147");  // false

        System.out.println("res = " + res);
    }

    public boolean splitString(String s) {
        //Use BigInteger because s might be 20 digits long, which is over Long range
        List<BigInteger> nums = new ArrayList<>();

        return dfs(s, nums, 0);
    }
    
    private boolean dfs(String s, List<BigInteger> nums, int start) {

        if (start == s.length()) {
            //If reach here, it means all numbers have been used, so
            // "nums" might contain a valid answer now 

            //Only two or more partitions are valid answers
            //Below check is to eliminate one partition case (e.g. "1234")  
            if (nums.size() > 1) {
                return true;
            }
        }

        // Looping through each possible partition position
        for (int i = start; i < s.length(); ++i ) {

            // below code is unnecessary anymore after using BigInteger, it just tries
            // to shortcircuit single partition case like "987654321", because at least
            // two partitiions are needed for a valid answer
            if (start == 0 && i == s.length() - 1) {
                return false;
            }

            // Next partition number 
            BigInteger next  = new BigInteger(s.substring(start, i + 1));
            
            //Below tree pruning only works if there are at least ONE number
            //If "nums" is empty, then it means this is 1st partition number
            //, and should not check and always add 1st number to "nums"
            if (nums.size() > 0) {
                BigInteger last = nums.get(nums.size() - 1);
                if (!last.subtract(next).equals(BigInteger.ONE)) {
                    continue;
                } 
            } 
            
            // Here means either "next" is 1st number, or "last - next == 1",
            // so "next" is a valid number and add it to "nums", then should 
            // continue to recurse to next level partitions
            nums.add(next);
            
            // If next partition level check returns true, it means there is at least 
            // one valid way of partitioning the rest of digits, so we can directly
            // return true, and no need to check for the rest of possible partitions.
            // This is like tree pruning by returning without to check for the rest
            // of possible partitions.
            if (dfs(s, nums, i + 1)) {
                return true;
            }

            //Reach here means above next level checking failed, we need to clean up
            // and continue to check for next possible digit partitioning.
            nums.remove(nums.size() - 1);            
        }

        // Here means we exhaust all possible digit partitions and still couldn't 
        // find a valid partition, so just reture false
        return false;
    }    
}
