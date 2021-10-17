
//给定一个非空字符串 s，最多删除一个字符。判断是否能成为回文字符串。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "aba"
//输出: true
// 
//
// 示例 2: 
//
// 
//输入: s = "abca"
//输出: true
//解释: 你可以删除c字符。
// 
//
// 示例 3: 
//
// 
//输入: s = "abc"
//输出: false 
//
// 
//
// 提示: 
//
// 
// 1 <= s.length <= 105 
// s 由小写英文字母组成 
// 
// Related Topics 贪心 双指针 字符串


package leetcode.editor.cn;
//Java：验证回文字符串 Ⅱ
public class P680ValidPalindromeIi{
    public static void main(String[] args) {
       //Solution solution = new Solution();
       //TEST
    }
}
//leetcode submit region begin(Prohibit modification and deletion)
class SolutionP680 {
    public boolean validPalindrome(String s) {
        int left =0, right = s.length();
        if (right < 2) return true;
        while (left < right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++; right--;
            } else {
                return validPalindrome(s, left + 1, right) || validPalindrome(s, left, right -1);
            }
        }
        return true;
    }

    private boolean validPalindrome(String s, int left, int right) {
        for (int i = left, j = right; i < j; i++, j--) {
            if(s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
