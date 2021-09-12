
//给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。 
//
// 
//
// 
// 
// 示例 1： 
//
// 
//输入：s = "(()"
//输出：2
//解释：最长有效括号子串是 "()"
// 
//
// 示例 2： 
//
// 
//输入：s = ")()())"
//输出：4
//解释：最长有效括号子串是 "()()"
// 
//
// 示例 3： 
//
// 
//输入：s = ""
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 3 * 104 
// s[i] 为 '(' 或 ')' 
// 
// 
// 
// Related Topics 栈 字符串 动态规划


package leetcode.editor.cn5And6;

import java.util.Deque;
import java.util.LinkedList;

//Java：最长有效括号
public class P32LongestValidParentheses{
    public static void main(String[] args) {
       //Solution solution = new Solution();
       //TEST
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionP32_Stack {
    //栈
    public int longestValidParentheses(String s) {
        Deque<Integer> stack = new LinkedList<>();
        int maxAns = 0 ;
        stack.push(-1);
        for(int i =0;  i < s.length(); i++) {
            if(s.charAt(i) == '(') stack.push(i);
            else {
                stack.pop();
                if(stack.isEmpty()) {
                    stack.push((i));
                } else {
                    maxAns = Math.max(maxAns, i - stack.peek());
                }
            }
        }
        return maxAns;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

class SolutionP32_DP {
    //DP
    public int longestValidParentheses(String s) {
        int maxAns = 0 ;
        int[] dp = new int[s.length()];
        for (int i = 1; i< s.length(); i++) {
            if(s.charAt(i) ==  ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0 )+ 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1)  == '(') {
                    dp[i] = dp[i - 1] + (i - dp[i -1] >= 2 ? dp[i - dp[i - 1] - 2]: 0) + 2;
                }
            }
            maxAns = Math.max(maxAns,dp[i]);
        }
        return maxAns;
    }
}