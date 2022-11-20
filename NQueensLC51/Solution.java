package NQueensLC51;
import java.util.*;

// https://leetcode.com/problems/n-queens/submissions/846951236/
// https://www.youtube.com/watch?v=Ph95IHmRp5M&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=12

// I think my solution is better than Neetcode's. For example, "current" list only needs to 
// contain column value, row value is implicit by its index value.

public class Solution {

    //Use instance variables as shared variables will make dfs() less bloated with shared variable parameters
    List<List<String>> results = new ArrayList<>();
    List<Integer> current = new ArrayList<>();

    // positive diagonal, (row + col) = constant (0,1,2,3,4,5,6,7,8)
    Set<Integer> posDiags = new HashSet<>();
    
    // negative diagonal, (row - col) = constant (-4,-3,-2,-1,0,1,2,3)
    Set<Integer> negDiags = new HashSet<>();
    
    Set<Integer> cols = new HashSet<>();
    
    int Size; // Board size

    public List<List<String>> solveNQueens(int n) {
        Size = n;
        dfs(0);
        return results;
    }

    private void dfs(int row) {
        // valid row value is (0 - (Size - 1)). When row reach Size, it means a valid Queen column has been found
        // for each row, so "current" will have a valid result;
        if (row == Size) {
            results.add(addResult(current));
        }

        for (int col = 0; col < Size; ++col) {

            // Check if there is other Queen on the same column, any positive diagonal, or negative diagonal
            if ((cols.contains(col) || posDiags.contains(row + col) || negDiags.contains(row - col))) {
                continue;
            }

            current.add(col);
            cols.add(col);
            posDiags.add(row + col);
            negDiags.add(row - col);
            
            // recurse to next row
            dfs(row + 1);

            // remember to pop out all backtrace values like "current" !!!
            current.remove(current.size() - 1);
            cols.remove(col);
            posDiags.remove(row + col);
            negDiags.remove(row - col);
        }
    }

    // Generate a result string from a valid Queen board according to the required format
    // StringBuilder is better than List<Character>
    private List<String> addResult(List<Integer> current) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < Size; ++i) {
            StringBuilder rowRes = new StringBuilder();
            int queenCol = current.get(i);
            for (int j = 0; j < Size; ++j) {
                if (j != queenCol) {
                    rowRes.append('.');
                } else {
                    rowRes.append("Q");
                }
            }
            result.add(rowRes.toString());
        }
        return result;
    }
}
