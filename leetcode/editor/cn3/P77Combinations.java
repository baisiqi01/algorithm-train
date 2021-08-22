
//给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。 
//
// 你可以按 任何顺序 返回答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 4, k = 2
//输出：
//[
//  [2,4],
//  [3,4],
//  [2,3],
//  [1,2],
//  [1,3],
//  [1,4],
//] 
//
// 示例 2： 
//
// 
//输入：n = 1, k = 1
//输出：[[1]] 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 20 
// 1 <= k <= n 
// 
// Related Topics 数组 回溯


package leetcode.editor.cn3;

import java.util.ArrayList;
import java.util.List;

//Java：组合
public class P77Combinations {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        System.out.println(solution.combine(4, 2));

    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution77 {
    List<Integer> tempList = new ArrayList<>();
    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        dfs(1, n, k);
        return res;
    }


    private void dfs(int cur, int n, int k) {
        //terminal
        if (tempList.size() + (n - cur + 1) < k) return;
        if (tempList.size() == k) {
            res.add(new ArrayList<Integer>(tempList));
            return;
        }
        //process 选择当前位置
        tempList.add(cur);
        //drill down,add cur
        dfs(cur + 1, n, k);
        //处理跳过场景,先删除之前添加的再向下
        tempList.remove(tempList.size() - 1);
        //考虑不选择当前位置
        dfs(cur+1, n, k);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
