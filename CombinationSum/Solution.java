import java.util.ArrayList;

class Solution {

   public List<List<Integer>> combinationSum(int[] candidates, int target) {

    ArrayList<List<Integer>> results = new ArrayList();    
    ArrayList<Integer> curResult = new ArrayList();
    dfsSumSearch(0, curResult, target, candidates, results);
    return results;
    }

    private void dfsSumSearch(int curPos, ArrayList<Integer> curResult, int target, int[] candidates, ArrayList<List<Integer>> results) {

        if (target==0) {
            results.add((ArrayList<Integer>)(curResult.clone()));
            return;
         }

         if (target<0) {
            return;
         }

        if (curPos >= candidates.length) {
            return ;
         } 

         //branch 1: include candidates[curPos] in result 
         curResult.add(candidates[curPos]);
         dfsSumSearch(curPos, curResult, target - candidates[curPos], candidates, results);
        
         //branch 2: don't include candidates[curPos] in result
         curResult.remove(curResult.size()-1);
         dfsSumSearch(curPos + 1, curResult, target, candidates, results);   
   }
}