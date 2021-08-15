
//输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。 
//
// 
//
// 示例 1： 
//
// 输入：arr = [3,2,1], k = 2
//输出：[1,2] 或者 [2,1]
// 
//
// 示例 2： 
//
// 输入：arr = [0,1,2,1], k = 1
//输出：[0] 
//
// 
//
// 限制： 
//
// 
// 0 <= k <= arr.length <= 10000 
// 0 <= arr[i] <= 10000 
// 
// Related Topics 数组 分治 快速选择 排序 堆（优先队列）


package leetcode.editor.cn2;

import java.util.PriorityQueue;

//Java：最小的k个数
public class Offer40ZuiXiaoDeKgeShuLcof {
    public static void main(String[] args) {
        //Solution solution = new Solution();
        //TEST
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionOffer40 {
    public int[] getLeastNumbers(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k==0) return new int[0];
        //大根堆
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((v1, v2) -> v2 - v1);
        for (int num : arr) {
            if (priorityQueue.size() < k) {
                priorityQueue.offer(num);
            } else if (num < priorityQueue.peek()) {
                //替换元素
                priorityQueue.poll();
                priorityQueue.offer(num);

            }
        }
        int[] res = new int[priorityQueue.size()];
        int j = 0;
        for (int num : priorityQueue) {
            res[j++] = num;
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
