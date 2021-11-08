
//给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位
//。 
//
// 返回滑动窗口中的最大值。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
//输出：[3,3,5,5,6,7]
//解释：
//滑动窗口的位置                最大值
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7
// 
//
// 示例 2： 
//
// 
//输入：nums = [1], k = 1
//输出：[1]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1,-1], k = 1
//输出：[1,-1]
// 
//
// 示例 4： 
//
// 
//输入：nums = [9,11], k = 2
//输出：[11]
// 
//
// 示例 5： 
//
// 
//输入：nums = [4,-2], k = 2
//输出：[4] 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 105 
// -104 <= nums[i] <= 104 
// 1 <= k <= nums.length 
// 
// Related Topics 队列 数组 滑动窗口 单调队列 堆（优先队列）


package leetcode.editor.cn2;

import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

//Java：滑动窗口最大值
public class P239SlidingWindowMaximum {
    public static void main(String[] args) {
        //Solution solution = new Solution();
        //TEST
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
//滑动窗口利用堆排序，提交时候超出时间限制
class Solution239_1 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1) return new int[0];
        int windowsNum = nums.length - k + 1;
        int[] res = new int[windowsNum];
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>((v1, v2) -> v2 - v1);
        for (int i = 0; i < nums.length; i++) {
            int start = i - k;
            if (start >= 0) {
                maxPQ.remove(nums[start]);
            }
            maxPQ.offer(nums[i]);
            if (maxPQ.size() == k) {
                res[i - k + 1] = maxPQ.peek();
            }
        }
        return res;
    }
}

//滑动窗口利用双端队列
class Solution239_2 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k <= 0) return new int[0];
        int len = nums.length;
        Deque<Integer> deque = new LinkedList<>();
        int[] res = new int[len - k + 1];//nums of window
        for (int i = 0; i < len; i++) {
           //删除越界元素
            while (!deque.isEmpty() && deque.peekFirst() < i-k+1) {
                deque.pollFirst();
            }
            //删除小于插入元素、
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            //添加元素
            deque.offerLast(i);
            if(i - k +1 >=0) {
                res[i - k + 1] = nums[deque.getFirst()];
            }
        }
        return res;
    }
}

class Solution239_3 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums == null || nums.length == 0) return new int[0];
        int len = nums.length;
        Deque<Integer> deque = new LinkedList<>();
        int[] res = new int[len - k + 1];
        for (int i = 0; i < len; i++) {
            while(!deque.isEmpty() && deque.peek() < i - k + 1) deque.pollFirst();
            while(!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) deque.pollLast();
            deque.offerLast(i);
            if(i - k + 1 >= 0) res[i - k + 1] = nums[deque.getFirst()];
        }
        return res;
    }
}


//leetcode submit region end(Prohibit modification and deletion)
