
//给定一个数组 nums 和滑动窗口的大小 k，请找出所有滑动窗口里的最大值。 
//
// 示例: 
//
// 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
//输出: [3,3,5,5,6,7] 
//解释: 
//
//  滑动窗口的位置                最大值
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7 
//
// 
//
// 提示： 
//
// 你可以假设 k 总是有效的，在输入数组不为空的情况下，1 ≤ k ≤ 输入数组的大小。 
//
// 注意：本题与主站 239 题相同：https://leetcode-cn.com/problems/sliding-window-maximum/ 
// Related Topics 队列 滑动窗口 单调队列 堆（优先队列）


package leetcode.editor.cn;

import java.util.Deque;
import java.util.LinkedList;

//Java：滑动窗口的最大值
public class Offer59IHuaDongChuangKouDeZuiDaZhiLcof{
    public static void main(String[] args) {
        SolutionOfferP59 solution = new SolutionOfferP59();
       //TEST
        int[] nums = {1,-1};
        System.out.println(solution.maxSlidingWindow(nums,1));
    }
}
//leetcode submit region begin(Prohibit modification and deletion)
class SolutionOfferP59 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if(n == 0 || k == 0) return new int[0];
        //双向队列
        Deque<Integer> deque = new LinkedList<>();
        //窗口数量
        int[] res = new int[n - k + 1];
        //未形成窗口
        for (int i = 0; i < k; i++) {
            while(!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        res[0] = nums[deque.peekFirst()];
        //形成窗口后
        for (int i = k; i < n; i ++) {
            while(!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            while (deque.peekFirst() <= i -k)  deque.pollFirst();
            res[i - k + 1] = nums[deque.peekFirst()];
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
