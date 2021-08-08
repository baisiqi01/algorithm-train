
//给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
//输出：6
//解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 
// 
//
// 示例 2： 
//
// 
//输入：height = [4,2,0,3,2,5]
//输出：9
// 
//
// 
//
// 提示： 
//
// 
// n == height.length 
// 0 <= n <= 3 * 104 
// 0 <= height[i] <= 105 
// 
// Related Topics 栈 数组 双指针 动态规划 单调栈


package leetcode.editor.cn;

import java.util.Stack;

//Java：接雨水
public class P42TrappingRainWater{
    public static void main(String[] args) {
       Solution42 solution = new Solution42();
       //TEST
    }
}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution42 {
    public int trap(int[] height) {
        int sum = 0;
        Stack<Integer> stack = new Stack();
        for (int i = 1;i < height.length;i++) {
            while(!stack.isEmpty() && height[i] > height[stack.peek()]){
                int cur =stack.peek();
                stack.pop();
                if(stack.isEmpty()){
                    break;
                }
                int left =stack.peek();
                int right = i;
                int h =Math.min(height[left],height[right]) - height[cur];
                sum +=(right - left - 1) * h;

            }
            stack.push(i);
        }
        return sum;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
