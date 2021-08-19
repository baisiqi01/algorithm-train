
//实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，xn）。 
//
// 
//
// 示例 1： 
//
// 
//输入：x = 2.00000, n = 10
//输出：1024.00000
// 
//
// 示例 2： 
//
// 
//输入：x = 2.10000, n = 3
//输出：9.26100
// 
//
// 示例 3： 
//
// 
//输入：x = 2.00000, n = -2
//输出：0.25000
//解释：2-2 = 1/22 = 1/4 = 0.25
// 
//
// 
//
// 提示： 
//
// 
// -100.0 < x < 100.0 
// -231 <= n <= 231-1 
// -104 <= xn <= 104 
// 
// Related Topics 递归 数学


package leetcode.editor.cn3;

//Java：Pow(x, n)
public class P50PowxN {
    public static void main(String[] args) {
        Solution50_3 solution = new Solution50_3();
        System.out.println(solution.myPow(1.00000, -2147483648));
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution50_1 {
    //递归
    public double myPow(double x, int n) {
        int N = n;
        return n > 0 ? helper(x, N) : 1 / helper(x, -N);
    }

    private double helper(double x, int N) {
        //terminal
        if (N == 0) return 1;
        double temp = helper(x, N / 2);
        if (N % 2 == 0) {
            //偶数
            return temp * temp;
        } else {
            //奇数数
            return temp * temp * x;
        }
    }
}

class Solution50_3 {
    //递归
    public double myPow(double x, int n) {
        if (n == Integer.MIN_VALUE) {
            x = x * x;
            n = n / 2;
        }
        if (n < 0) {
            n = -n;
            x = 1 / x;
        }
        if (n == 0) return 1;
        double temp = myPow(x, n / 2);
        if (n % 2 == 0) {
            return temp * temp;
        } else {
            return temp * temp * x;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

//暴力法，O(n)时间复杂度,github会提示超出时间复杂度
class Solution50_2 {
    private double res;

    //暴力法
    public double myPow(double x, int n) {
        int N = n;
        if (n < 0) {
            N = -n;
            x = 1 / x;
        }
        double res = 1;
        for (int i = 0; i < N; i++) {
            res = res * x;
        }
        return res;
    }
}