
//n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。 
//
// 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。 
//
// 
// 
// 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 4
//输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
//解释：如上图所示，4 皇后问题存在两个不同的解法。
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：[["Q"]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 9 
// 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。 
// 
// 
// 
// Related Topics 数组 回溯


package leetcode.editor.cn7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Java：N 皇后
public class P51NQueens {
    public static void main(String[] args) {
        SolutionP51 solution = new SolutionP51();
        System.out.println(solution.solveNQueens(4));
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionP51 {
    List<List<String>> res = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        char[][] chessBoard = new char[n][n];
        for (char[] chessRow : chessBoard) Arrays.fill(chessRow, '.');
        backTrace(n, 0, chessBoard);
        return res;

    }

    private void backTrace(int n, int row, char[][] chessBoard) {
        //终止条件
        if(row == n) {
            res.add(arraysToList(chessBoard));
            return;
        }
        for (int col = 0; col < n; col++) {
            if(isValid(row,col,n,chessBoard)) {
                chessBoard[row][col] = 'Q';
                //回溯
                backTrace(n,row + 1,chessBoard);
                //恢复棋盘状态
                chessBoard[row][col] = '.';
            }
        }
    }

    private List<String> arraysToList(char[][] chessBoard) {
        List<String> res = new ArrayList<>();
        for(char[] chessLine : chessBoard) {
            res.add(String.copyValueOf(chessLine));
        }
        return res;
    }

    private boolean isValid(int row, int col, int n, char[][] chessBoard) {
        //检查列
        for(int i = 0; i < row; i++){
            if(chessBoard[i][col] == 'Q') return false;
        }

        //检查左上角
        for (int i = row -1, j = col - 1; i >= 0 && j >= 0;i--, j--) {
            if(chessBoard[i][j] == 'Q') return false;
        }
        //检查右上角
        for(int i = row - 1, j = col + 1 ; i >= 0 && j < n; i--, j++) {
            if(chessBoard[i][j] == 'Q') return false;
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
