
//给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。 
//
// 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。 
//
// 你可以认为每种硬币的数量是无限的。 
//
// 
//
// 示例 1： 
//
// 
//输入：coins = [1, 2, 5], amount = 11
//输出：3 
//解释：11 = 5 + 5 + 1 
//
// 示例 2： 
//
// 
//输入：coins = [2], amount = 3
//输出：-1 
//
// 示例 3： 
//
// 
//输入：coins = [1], amount = 0
//输出：0
// 
//
// 示例 4： 
//
// 
//输入：coins = [1], amount = 1
//输出：1
// 
//
// 示例 5： 
//
// 
//输入：coins = [1], amount = 2
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 1 <= coins.length <= 12 
// 1 <= coins[i] <= 231 - 1 
// 0 <= amount <= 104 
// 
// Related Topics 广度优先搜索 数组 动态规划


package leetcode.editor.cn5And6;

import java.util.Arrays;

//Java：零钱兑换
public class P322CoinChange {
    public static void main(String[] args) {
        //Solution solution = new Solution();
        //TEST
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
//递归非缓存，超时
class SolutionP322_1 {
    int res = Integer.MAX_VALUE;

    public int coinChange(int[] coins, int amount) {
        dfs(coins, amount, 0);
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private void dfs(int[] coins, int amount, int count) {
        if (amount < 0) return;
        else if (amount == 0) res = Math.min(res, count);
        else {
            for (int coin : coins) {
                dfs(coins, amount - coin, count + 1);
            }
        }

    }
}

//递归 缓存
class SolutionP322_2 {
    int[] memo;

    public int coinChange(int[] coins, int amount) {
        memo = new int[amount];
        return dfs(coins, amount);

    }

    private int dfs(int[] coins, int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        if (memo[amount - 1] != 0) return memo[amount - 1];
        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int res = dfs(coins, amount - coin);
            if (res >= 0 && res < min) min = res + 1;
        }
        return memo[amount - 1] = (min == Integer.MIN_VALUE ? -1 : min);

    }
}


//自底向上 动态规划
class SolutionP322_3 {
    public int coinChange(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        dp[0] = 0;
        Arrays.fill(dp, max);
        for (int i = 1; i <= amount; i++)
            for (int j = 0; j < coins.length; j++){
                dp[i] = Math.min(dp[i], dp[i - coins[j]]) + 1;
            }
        return dp[amount] > amount? -1 : dp[amount];
    }

}
//lee
//leetcode submit region end(Prohibit modification and deletion)
