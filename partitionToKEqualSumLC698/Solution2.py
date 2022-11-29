from typing import List

# this version is different from Solution.py by adding memoizing on subproblem results.
# but it didn't use previous number optimization like what Solution.py did
# Its  performance seems comparable to Solution.py:

#https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/850770344/
# Runtime 698 ms Beats 49.60% Memory 34 MB Beats 25.53%

# https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/851420366/
# Runtime 654 ms Beats 50.89% Memory 52.4 MB Beats 7.77%

# Looks like memoization performance seems about the same as previous unused value optimization 
# like in Solution.py 

class Solution2:
    def canPartitionKSubsets(self, nums: List[int], k: int) -> bool:
        used = [False]*len(nums)
        total_sum = sum(nums)
        if total_sum % k != 0:
            return False
        target = total_sum // k
        
        cachedValUsed = 0
        
        #sorting the array in descending order
        #so if first value is greater than target, it will not be included in any subset
        #so we cant partition the entire array into k equal sum subsets
        # nums.sort(reverse = True)
        if nums[0] > target:
            return False
        
        dp = {}
        def backtrack(i,k,rem):
            nonlocal cachedValUsed 
            #since subproblem depends on used indices of array
            #if same subproblem occurs again just return dp value
            if tuple(used) in dp:
                cachedValUsed += 1
                # print("\n cachedValUsed = " + str(cachedValUsed) + ", dp size =" + str(len(dp)) + ", value =" + str(dp))
                return dp[tuple(used)]
            if k == 0:
                return True
            if rem == 0:
                partition = backtrack(0,k-1,target)
                dp[tuple(used)] = partition
                return partition
            for j in range(i,len(nums)):
                if not used[j] and rem-nums[j] >= 0:
                    used[j] = True
                    if backtrack(j+1,k,rem-nums[j]):
                        return True
                    used[j] = False
            dp[tuple(used)] = False
            return False
        return backtrack(0,k,target)
    
s = Solution2()
# print(" result =", s.canPartitionKSubsets([4,1,1,2,5,5], 3))    
print(" result =", s.canPartitionKSubsets([4,5,9,3,10,2,10,7,10,8,5,9,4,6,4,9], 5))    