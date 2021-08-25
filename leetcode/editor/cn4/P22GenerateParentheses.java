
//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 
//
// 有效括号组合需满足：左括号必须以正确的顺序闭合。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3
//输出：["((()))","(()())","(())()","()(())","()()()"]
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：["()"]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 8 
// 
// Related Topics 字符串 动态规划 回溯


package leetcode.editor.cn4;

import java.util.ArrayList;
import java.util.List;

//Java：括号生成
public class P22GenerateParentheses {
    public static void main(String[] args) {
        Solution22 solution = new Solution22();
        System.out.println( solution.generateParenthesis(3));
    }
}


//leetcode submit region begin(Prohibit modification and deletion)
class Solution22 {
    private List<String> res = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        dfs(0,0, n, "");
        return res;
    }

    private void dfs(int left, int right, int n, String s) {
        if(left == n && right ==n) {
            res.add(s);
            return;
        }
        if (left < n) dfs(left + 1 ,right, n,s + "(");
        if (right < left) dfs(left, right +1, n, s +=")");
    }

}
//leetcode submit region end(Prohibit modification and deletion)


