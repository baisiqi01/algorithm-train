
//给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b
//], nums[c], nums[d]] ： 
//
// 
// 0 <= a, b, c, d < n 
// a、b、c 和 d 互不相同 
// nums[a] + nums[b] + nums[c] + nums[d] == target 
// 
//
// 你可以按 任意顺序 返回答案 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,0,-1,0,-2,2], target = 0
//输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [2,2,2,2,2], target = 8
//输出：[[2,2,2,2]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 200 
// -109 <= nums[i] <= 109 
// -109 <= target <= 109 
// 
// Related Topics 数组 双指针 排序


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Java：四数之和
public class P18FourSum{
    public static void main(String[] args) {
       SolutionP18 solution = new SolutionP18();
       int[] test = {0,4,-5,2,-2,4,2,-1,4};
        System.out.println(solution.fourSum(test, 12));
    }
}
//leetcode submit region begin(Prohibit modification and deletion)
class SolutionP18 {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 4) return res;
        Arrays.sort(nums);
        int len = nums.length;
        for (int i = 0; i< len - 3;i++) {
            if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i +3] > target) break;
            if (nums[i] + nums[len -3] + nums[len -2] + nums[len -1] < target) continue;
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j< len -2; j++) {
                if(nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) continue;
                if(nums[i] + nums[j] + nums[len - 2] + nums[len -1] < target) continue;
                if(j > i + 1 && nums[j] == nums[j -1]) continue;
                int k = j + 1, z = len - 1;
                while (z > k) {
                    if (nums[i] + nums[j] + nums[k] + nums[z] == target) {
                        res.add(Arrays.asList(nums[i],nums[j],nums[k],nums[z]));
                        while(z > k && nums[k] == nums[k + 1]) k++;
                        while(z > k && nums[z] == nums[z - 1]) z--;
                        k++;z--;
                    } else if (nums[i] + nums[j] + nums[k] + nums[z] < target){
                        k++;
                    } else if (nums[i] + nums[j] + nums[k] +nums[z] > target) {
                        z--;
                    }
                }
            }
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
