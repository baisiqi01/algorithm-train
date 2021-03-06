
//给定一个三角形 triangle ，找出自顶向下的最小路径和。 
//
// 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。也就是说，如果
//正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。 
//
// 
//
// 示例 1： 
//
// 
//输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
//输出：11
//解释：如下面简图所示：
//   2
//  3 4
// 6 5 7
//4 1 8 3
//自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
// 
//
// 示例 2： 
//
// 
//输入：triangle = [[-10]]
//输出：-10
// 
//
// 
//
// 提示： 
//
// 
// 1 <= triangle.length <= 200 
// triangle[0].length == 1 
// triangle[i].length == triangle[i - 1].length + 1 
// -104 <= triangle[i][j] <= 104 
// 
//
// 
//
// 进阶： 
//
// 
// 你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题吗？ 
// 
// Related Topics 数组 动态规划


package leetcode.editor.cn5And6;

import java.util.List;

//Java：三角形最小路径和
public class P120Triangle{
    public static void main(String[] args) {
       //Solution solution = new Solution();
       //TEST
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
//动态规划 P120,根据动态方程dp[i][j] = Math.min(dp[i + 1][j],dp[i + 1][j + 1]) + triangle.get(i).get(j);
class SolutionP120_1 {
    public int minimumTotal(List<List<Integer>> triangle) {
        int size = triangle.size();
        //多声明一列用于减少初始0赋值
        int[][] dp = new int[size + 1][size + 1];
        //自底向上
        for (int i = size - 1; i >= 0; i--)
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.min(dp[i + 1][j],dp[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        return dp[0][0];

    }
}
//leetcode submit region end(Prohibit modification and deletion)

//暴力法-需要优化记录中间值
class SolutionP120_2 {
    Integer[][] memo;

    public int minimumTotal(List<List<Integer>> triangle) {
        memo = new Integer[triangle.size()][triangle.size()];
        return dfs(triangle,0,0);
    }

    private int dfs(List<List<Integer>> triangle, int i, int j) {
        if (i == triangle.size()) return 0;
        if (memo[i][j] != null) return memo[i][j];
        return memo[i][j] = Math.min(dfs(triangle,i +1,j),dfs(triangle,i + 1, j + 1)) + triangle.get(i).get(j);
    }
}