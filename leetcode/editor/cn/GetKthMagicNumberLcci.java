
//有些数的素因子只有 3，5，7，请设计一个算法找出第 k 个数。注意，不是必须有这些素因子，而是必须不包含其他的素因子。例如，前几个数按顺序应该是 1，3，
//5，7，9，15，21。 
//
// 示例 1: 
//
// 输入: k = 5
//
//输出: 9
// 
// Related Topics 哈希表 数学 动态规划 堆（优先队列）


package leetcode.editor.cn;

import java.util.PriorityQueue;

//Java：第 k 个数
public class GetKthMagicNumberLcci{
    public static void main(String[] args) {
       //Solution solution = new Solution();
       //TEST
    }
}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution1709 {
    public int getKthMagicNumber(int k) {
        PriorityQueue<Long> minPQ = new PriorityQueue<>();
        minPQ.add(1L);
        int[] fectors = {3,5,7};
        Long res =0L;
        for (int i = 0; i< k; i++){
            res = minPQ.poll();
            for(int fector :fectors){
                Long temp = fector * res;
                if(!minPQ.contains(temp)) minPQ.offer(temp);
            }
        }
        return res.intValue();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
