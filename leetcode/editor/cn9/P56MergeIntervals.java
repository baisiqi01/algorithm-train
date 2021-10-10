
//以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返
//回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。 
//
// 
//
// 示例 1： 
//
// 
//输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
//输出：[[1,6],[8,10],[15,18]]
//解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
// 
//
// 示例 2： 
//
// 
//输入：intervals = [[1,4],[4,5]]
//输出：[[1,5]]
//解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。 
//
// 
//
// 提示： 
//
// 
// 1 <= intervals.length <= 104 
// intervals[i].length == 2 
// 0 <= starti <= endi <= 104 
// 
// Related Topics 数组 排序


package leetcode.editor.cn9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Java：合并区间
public class P56MergeIntervals{
    public static void main(String[] args) {
       //Solution solution = new Solution();
       //TEST
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionP56 {
    public int[][] merge(int[][] intervals) {
        if(intervals.length <= 1) return intervals;
        Arrays.sort(intervals,(i1,i2) -> Integer.compare(i1[0], i2[0]));
        List<int []> result = new ArrayList<>();
        int[] newIntervals = intervals[0];
        result.add(newIntervals);
        for (int [] interval : intervals) {
            if(interval[0] < newIntervals[1]) {
                newIntervals[1] = Math.max(newIntervals[1], interval[1]);
            }else {
                newIntervals =interval;
                result.add(newIntervals);
            }

        }
        return result.toArray(new int[result.size()][2]);

    }
}
//leetcode submit region end(Prohibit modification and deletion)
