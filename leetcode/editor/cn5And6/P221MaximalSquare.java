
//在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。 
//
// 
//
// 示例 1： 
//
// 
//输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"]
//,["1","0","0","1","0"]]
//输出：4
// 
//
// 示例 2： 
//
// 
//输入：matrix = [["0","1"],["1","0"]]
//输出：1
// 
//
// 示例 3： 
//
// 
//输入：matrix = [["0"]]
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// m == matrix.length 
// n == matrix[i].length 
// 1 <= m, n <= 300 
// matrix[i][j] 为 '0' 或 '1' 
// 
// Related Topics 数组 动态规划 矩阵


package leetcode.editor.cn5And6;

//Java：最大正方形
public class P221MaximalSquare {
    public static void main(String[] args) {
        //Solution solution = new Solution();
        //TEST
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionP221 {
    public int maximalSquare(char[][] matrix) {
        int maxSide = 0;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return maxSide;
        int row = matrix.length, col = matrix[0].length;
        int[][] dp = new int[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) dp[i][j] = 1;
                    else dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                maxSide = Math.max(dp[i][j], maxSide);
                }
            }

        int maxRes = maxSide * maxSide;
            return maxRes;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
