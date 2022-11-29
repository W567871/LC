
# Accepted Python version by Leetcode
# https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/850211390/
# https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/851413176/

# Based on Neetcode's code plus optimization from comments by Rg Prasad
# https://www.youtube.com/watch?v=mBk4I0X46oI&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=16

# this version has same number value optimization.
# For example, if input is { 1, 1, 1, 1, 2, 2, 2, 2 }, k=4, and if a previous "1" or "2" is not 
# used for bucket sum, then next "1" or "2" can't be used either ("not used[i - 1] and nums[i] == nums[i - 1]")
# The reason is when checking a previous "1" or "2", it already checked the cases for including the next "1" or "2",
# so if the previous number is NOT selected for target sum, then it means including the next "1" or "2" won't work either     

# this version was accepted by Leetcode, but seems not very fast:
#Runtime 707 ms  Beats 49.19% Memory 13.9 MB Beats 93.65%

#Runtime 408 ms Beats 61.12% Memory 13.9 MB Beats 74%

from typing import List


class Solution:
    def canPartitionKSubsets(self, nums: List[int], k: int) -> bool:
        
        n = len(nums)
        nums.sort(reverse=True)
        total = sum(nums)
        if total % k != 0:
            return False
        
        target = int(total / k)
        used = [False] * n
        
        def dfs(index, total, k): 
            
            if k == 0:
                return True
            
            if total == 0:
                return dfs(0, target, k - 1)
            
            for i in range(index, n):
                
                # Rg Prasad added below optimization to avoid duplicate calculations on i
                # if i-1 has already checked the same case (target, inputs)
                if i > 0 and not used[i - 1] and nums[i] == nums[i - 1]:
                    continue
                    
                if used[i] or total - nums[i] < 0:
                    continue
                
                used[i] = True
                if dfs(i + 1, total - nums[i], k):
                    return True
                used[i] = False
            return False
        
        return dfs(0, target, k)
    
    
s = Solution()
print(" result =", s.canPartitionKSubsets([10, 10, 7, 8, 10, 4, 9, 7, 9, 10, 4, 6, 7, 1, 8, 5], 5))    