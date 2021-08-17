
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


package leetcode.editor.cn3;

import java.util.ArrayList;
import java.util.List;

//Java：括号生成
public class P22GenerateParentheses {
    public static void main(String[] args) {
        Solution22 solution = new Solution22();
        System.out.println( solution.generateParenthesis(3));
        Solution22_1 solution0 = new Solution22_1();
        System.out.println(solution0.generateParenthesis(3));
    }
}

//生成所有括号不考虑括号规则
class Solution22_0 {
    private List<String> res = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        helper(0, 2 * n, "");
        return res;
    }

    private void helper(int level, int max, String s) {
        //terminal
        if (level >= max) {
            res.add(s);
            return;
        }
        //process
        String s1 = s + '(';
        String s2 = s + ')';
        //drill down
        helper(level + 1, max, s1);
        helper(level + 1, max, s2);
        //restore null
    }

}

//left right代表左右括号剩余个数，注意右括号剩余必须大于左括号剩余才可添加！减少一个传参：不用传数量n参数
class Solution22_1 {
    private List<String> res = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        helper2(n, n, "");
        return res;
    }

    private void helper2(int left, int right, String s) {
        if (left == 0 && right == 0) {
            res.add(s);
            return;
        }
        //左括号只要有剩余就可以添加
        if (left > 0) helper2(left - 1, right, s+'(');
        //右括号剩余必须大于左括号剩余才可添加！！
        if (right >0 && left < right) helper2(left, right - 1, s+')');
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution22 {
    private List<String> res = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        _genertate(0, 0, n, "");
        return res;
    }

    private void _genertate(int left, int right, int n, String s) {
        //terminal
        if (left == n && right == n) {
            res.add(s);
            return;
        }
        //process//
        // String s1 = s + '(';
//        String s2 = s + ')';
        //drill down
        if (left < n) _genertate(left + 1, right, n, s + '(');
        if (right < left) _genertate(left, right + 1, n, s + ')');

        //restore
    }
}
//leetcode submit region end(Prohibit modification and deletion)


