
//给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,1]
//输出：[[0,1],[1,0]]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1]
//输出：[[1]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 6 
// -10 <= nums[i] <= 10 
// nums 中的所有整数 互不相同 
// 
// Related Topics 数组 回溯


package leetcode.editor.cn3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Java：全排列
public class P46Permutations{
    public static void main(String[] args) {
//       Solution solution = new Solution();
//       int[] nums ={1,2,3};
//        System.out.println(solution.permute(nums));
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution46 {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> list = new LinkedList<>();
        return dfs(nums, res, list);

    }

    private List<List<Integer>> dfs(int[] nums, List<List<Integer>> res, LinkedList<Integer> list) {
        if(list.size() == nums.length) {
            res.add(new ArrayList<>(list));
            return res;
        }
        for (int i = 0; i < nums.length ;i++) {
            if (list.contains(nums[i])) continue;
            list.add(nums[i]);
            dfs(nums,res,list);
            //回溯
            list.removeLast();
        }

        return res;
    }
}


class Solution46_2 {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> list = new LinkedList<>();
        return dfs(nums, res, list);
    }

    private List<List<Integer>> dfs(int[] nums, List<List<Integer>> res, LinkedList<Integer> list) {
        if(list.size() == nums.length) {
            res.add(new ArrayList<>(list));
            return res;
        }
        for (int i = 0; i < nums.length; i++) {
            if(list.contains(nums[i])) continue;
            list.add(nums[i]);
            dfs(nums, res, list);
            list.removeLast();
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
