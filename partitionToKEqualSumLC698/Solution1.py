from typing import List

# Solution from Matthew
# https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/850342941/
# Runtime 233 ms Beats 76.5% Memory 13.9 MB, Beats 93.39%

class Solution:
    def canPartitionKSubsets(self, nums: List[int], k: int) -> bool:
        """
        We can solve this by forming decision trees
        We can either choose a certain number to add to our rolling sum or we can choose to ignore it
        We can keep track of already used values in an array used
        If we get a rolling sum equal to sum(nums)/k, we decrement the count of buckets we need to fill by 1
        if we get a rolling sum greater than sum(nums)/k we return out one level with false
        if we get to bucket total of 0, we can roll all the way up and return true
        if we exhaust all options, we return false
        """
        # dp = {}
        m = sum(nums)/k
        if sum(nums) % k != 0 or max(nums) > m:
            return False
        nums.sort(reverse=True)

        used = [False] * len(nums)

        def helper(j, rs,  k):
            # t = tuple(used + [rs])
            # if t in dp:
            #     return dp[t]

            if rs == m:
                if k == 1:
                    return True
                
                # Remember j must be reset to 0
                return helper(0, 0, k-1)
            
            elif rs > m:
                # dp[t] = False
                return False
            else:
                for i in range(j, len(nums)):
                    if not used[i]:
                        if i > 0 and nums[i-1] == nums[i] and not used[i-1]:
                            continue
                        used[i] = True
                        if helper(i+1, rs + nums[i], k):
                            return True
                        used[i] = False

            # dp[t] = False
            return False

        return helper(0, 0, k)


s = Solution()
print(" result =", s.canPartitionKSubsets(
    [10, 10, 7, 8, 10, 4, 9, 7, 9, 10, 4, 6, 7, 1, 8, 5], 5))
