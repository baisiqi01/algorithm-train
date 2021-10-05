
//给定一个字符串 s 和一个整数 k，从字符串开头算起，每计数至 2k 个字符，就反转这 2k 字符中的前 k 个字符。 
//
// 
// 如果剩余字符少于 k 个，则将剩余字符全部反转。 
// 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "abcdefg", k = 2
//输出："bacdfeg"
// 
//
// 示例 2： 
//
// 
//输入：s = "abcd", k = 2
//输出："bacd"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 104 
// s 仅由小写英文组成 
// 1 <= k <= 104 
// 
// Related Topics 双指针 字符串


package leetcode.editor.cn;
//Java：反转字符串 II
public class P541ReverseStringIi{
    public static void main(String[] args) {
       //Solution solution = new Solution();
       //TEST
    }
}
//leetcode submit region begin(Prohibit modification and deletion)
class SolutionP541 {
    public String reverseStr(String s, int k) {
        int len = s.length();
        char[] chars = s.toCharArray();
        for (int i = 0; i < len; i += 2 * k) {
            reverse(chars,i , Math.min(i + k , len) - 1);
        }
        return String.valueOf(chars);

    }

    private void reverse(char[] chars, int start, int end) {
        while(start < end) {
            char temp = chars[start];
            chars[start]  = chars[end];
            chars[end] = temp;
            start++;
            end--;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
