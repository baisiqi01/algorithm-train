
//给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words，找出所有同时在二维网格和字典中出现的单词。 
//
// 单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使
//用。 
//
// 
//
// 示例 1： 
//
// 
//输入：board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l"
//,"v"]], words = ["oath","pea","eat","rain"]
//输出：["eat","oath"]
// 
//
// 示例 2： 
//
// 
//输入：board = [["a","b"],["c","d"]], words = ["abcb"]
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// m == board.length 
// n == board[i].length 
// 1 <= m, n <= 12 
// board[i][j] 是一个小写英文字母 
// 1 <= words.length <= 3 * 104 
// 1 <= words[i].length <= 10 
// words[i] 由小写英文字母组成 
// words 中的所有字符串互不相同 
// 
// Related Topics 字典树 数组 字符串 回溯 矩阵


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;

//Java：单词搜索 II
public class P212WordSearchIi{
    public static void main(String[] args) {
       //Solution solution = new Solution();
       //TEST
    }
}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    class TrieNode{
        TrieNode[] next = new TrieNode[26];
        String word;
    }

    int[] dx = {-1, 0, 1, 0};
    int[] dy = {0, 1, 0, -1};
    int m,n;
    List<String> res = new ArrayList<>();

    public List<String> findWords(char[][] board, String[] words) {
        m = board.length;
        n = board[0].length;
        TrieNode root = buildTrie(words);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, i, j, root);
            }
        }return res;

    }

    private void dfs(char[][] board, int i, int j, TrieNode p) {
        if (i < 0 || i >= m || j < 0 || j >= n) return;
        char c = board[i][j];
        //当前的字符被搜索过（设置成#）|| 没有可供备选的单词了
        if (c == '#' || p.next[c - 'a'] == null) return;
        p = p.next[c - 'a'];//当前的节点存在，选word不是null的加入到结果集
        if (p.word != null) {
            res.add(p.word);
            //去重，如[["o","a","b","n"],["o","t","a","e"],["a","h","k","r"],["a","f","l","v"]]
            //["oa","oaa"]
            //输出["oa","oa","oaa"] 其中有"oa"重复了
            p.word = null;
        }
        //进入下一层
        board[i][j] = '#';
        for (int d = 0; d < dx.length; d++) {
            dfs(board, i + dx[d], j + dy[d], p);
        }
        board[i][j] = c;

    }

    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word :words) {
            TrieNode cur= root;
            for (char c : word.toCharArray()) {
                if(cur.next[c - 'a'] == null) cur.next[c - 'a'] = new TrieNode();
                cur = cur.next[c - 'a'];
            }
            cur.word = word;
        }
        return root;
    }
}

//leetcode submit region end(Prohibit modification and deletion)
