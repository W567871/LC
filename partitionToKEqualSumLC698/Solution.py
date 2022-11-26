
# Accepted Python version by Leetcode
# https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/850211390/

# Based on Neetcode's code plus optimization from comments by Rg Prasad
# https://www.youtube.com/watch?v=mBk4I0X46oI&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=16

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