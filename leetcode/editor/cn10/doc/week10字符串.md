# week10字符串

## 1、字符串相关练习

[字符串转换整数 (atoi) ](https://leetcode-cn.com/problems/string-to-integer-atoi/)

```java
class SolutionP8 {
    public int myAtoi(String s) {
        int signal = 1, index = 0, total =0;
        if(s.length() == 0) return 0;
        //丢弃空格
        while (index < s.length() && s.charAt(index) == ' ' ) index++;
        //判断负号
        if(index < s.length() && (s.charAt(index) == '+' || s.charAt(index) == '-') ) {
            signal = s.charAt(index) == '+' ? 1 : -1;
            index++;
        }
        //计算total
        while (index < s.length()) {
            int digit = s.charAt(index) - '0';
            if(digit > 9 || digit < 0) break;
            //判断越界情况
            if (Integer.MAX_VALUE/10 < total || Integer.MAX_VALUE/10 == total && Integer.MAX_VALUE %10 < digit) return signal ==1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            total = total * 10 + digit;
            index++;
        }
        //返回结果
        return signal * total;

    }
}
```

回文子串https://leetcode-cn.com/problems/longest-palindromic-substring/

```java
class SolutionP5 {
    //动态规划
    public String longestPalindrome(String s) {
        if (s.length() < 2) return s;
        String res = "";
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i;j < len ; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i + 1][j -1]);
                if(dp[i][j] &&  j - i + 1 >res.length()) {
                    res = s.substring(i, j + 1);
                }
            }

        }
        return res;
    }

    //中心扩散
    public String longestPalindrome2(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

}
```

P72编辑距离

```java
class SolutionP72 {
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        //边界
        for (int i = 1; i <= m; i++) dp[i][0] = dp[i - 1][0] + 1;
        for (int j = 1; j <= n; j++) dp[0][j] = dp[0][j - 1] + 1;
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
            }
        return dp[m][n];
    }
}
```

P1143最长公共子序列

```java
class SolutionP1143 {
    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length(), len2 = text2.length();
        if (len1 == 0 || len2 == 0) return 0;
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 1; i <= len1; i++)
            for (int j = 1; j <= len2; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        return dp[len1][len2];
    }
}
```



## 2、参考链接

- [ Boyer-Moore 算法](https://www.ruanyifeng.com/blog/2013/05/boyer-moore_string_search_algorithm.html)

- [ Sunday 算法](https://blog.csdn.net/u012505432/article/details/52210975)

  扩展

- [字符串匹配暴力法代码示例](https://shimo.im/docs/8G0aJqNL86wWrPUE)

  基础

- [ Rabin-Karp 代码示例](https://shimo.im/docs/1wnsM7eaZ6Ab9j9M) 

  预先判断-hash

- [ KMP 字符串匹配算法视频](https://www.bilibili.com/video/av11866460?from=search&seid=17425875345653862171)

  前缀匹配，加速遍历过程

- [字符串匹配的 KMP 算法](http://www.ruanyifeng.com/blog/2013/05/Knuth–Morris–Pratt_algorithm.html)
