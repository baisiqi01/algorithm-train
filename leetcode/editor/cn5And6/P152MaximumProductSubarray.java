
//给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。 
//
// 
//
// 示例 1: 
//
// 输入: [2,3,-2,4]
//输出: 6
//解释: 子数组 [2,3] 有最大乘积 6。
// 
//
// 示例 2: 
//
// 输入: [-2,0,-1]
//输出: 0
//解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。 
// Related Topics 数组 动态规划


package leetcode.editor.cn5And6;

//Java：乘积最大子数组
public class P152MaximumProductSubarray {
    public static void main(String[] args) {
        SolutionP152 solution = new SolutionP152();
        System.out.println(solution.maxProduct(new int[]{2,3,-2,4}));
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionP152 {
    public int maxProduct(int[] nums) {
        int maxV = nums[0], minV = nums[0], length = nums.length, res = nums[0];
        for (int i = 1; i < length; i++) {
            int mx = maxV, mn = minV;
            maxV = Math.max(mx * nums[i], Math.max(nums[i], nums[i] * mn));
            minV = Math.min(mx * nums[i], Math.min(nums[i], nums[i] * mn));
            res = Math.max(res, maxV);
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
