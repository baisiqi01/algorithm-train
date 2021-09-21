# week07高级搜索

## 1、初级搜索

1、朴素搜索  傻搜 重复

2、优化：不重复（换粗，fibonacci）  剪枝（括号数量）

3、搜索方向 DFS BFS



双向搜索、启发式搜索



## 2、模板

Java  DFS

```
//Java  DFS

  public List<List<Integer>> levelOrder(TreeNode root) {

​    List<List<Integer>> allResults = new ArrayList<>();

​    if(root==null){

​      return allResults;

​    }

​    travel(root,0,allResults);

​    return allResults;

  }

  private void travel(TreeNode root,int level,List<List<Integer>> results){

​    if(results.size()==level){

​      results.add(new ArrayList<>());

​    }

​    results.get(level).add(root.val);

​    if(root.left!=null){

​      travel(root.left,level+1,results);

​    }

​    if(root.right!=null){

​      travel(root.right,level+1,results);

​    }

  }
  
```

Java  BFS

```
//Java

public class TreeNode {

​    int val;

​    TreeNode left;

​    TreeNode right;

​    TreeNode(int x) {

​        val = x;

​    }

}

public List<List<Integer>> levelOrder(TreeNode root) {

​    List<List<Integer>> allResults = new ArrayList<>();

​    if (root == null) {

​        return allResults;

​    }

​    Queue<TreeNode> nodes = new LinkedList<>();

​    nodes.add(root);

​    while (!nodes.isEmpty()) {

​        int size = nodes.size();

​        List<Integer> results = new ArrayList<>();

​        for (int i = 0; i < size; i++) {

​            TreeNode node = nodes.poll();

​            results.add(node.val);

​            if (node.left != null) {

​                nodes.add(node.left);

​            }

​            if (node.right != null) {

​                nodes.add(node.right);

​            }

​        }

​        allResults.add(results);

​    }

​    return allResults;

}
```

棋类复杂度：https://en.wikipedia.org/wiki/Game_complexity

回溯三部曲

```

void backtracking(参数) {
    if (终止条件) {
        存放结果;
        return;
    }
    for (选择：本层集合中元素（树中节点孩子的数量就是集合的大小）) {
        处理节点;
        backtracking(路径，选择列表); // 递归
        回溯，撤销处理结果
    }
}



```



## 3、练习

### 3.1 数独类

- [ N 皇后](https://leetcode-cn.com/problems/n-queens/)

  ```java
  class SolutionP51 {
      List<List<String>> res = new ArrayList<>();
  
      public List<List<String>> solveNQueens(int n) {
          char[][] chessBoard = new char[n][n];
          for (char[] chessRow : chessBoard) Arrays.fill(chessRow, '.');
          backTrace(n, 0, chessBoard);
          return res;
  
      }
  
      private void backTrace(int n, int row, char[][] chessBoard) {
          //终止条件
          if(row == n) {
              res.add(arraysToList(chessBoard));
              return;
          }
          for (int col = 0; col < n; col++) {
              if(isValid(row,col,n,chessBoard)) {
                  chessBoard[row][col] = 'Q';
                  //回溯
                  backTrace(n,row + 1,chessBoard);
                  //恢复棋盘状态
                  chessBoard[row][col] = '.';
              }
          }
      }
  
      private List<String> arraysToList(char[][] chessBoard) {
          List<String> res = new ArrayList<>();
          for(char[] chessLine : chessBoard) {
              res.add(String.copyValueOf(chessLine));
          }
          return res;
      }
  
      private boolean isValid(int row, int col, int n, char[][] chessBoard) {
          //检查列
          for(int i = 0; i < row; i++){
              if(chessBoard[i][col] == 'Q') return false;
          }
  
          //检查左上角
          for (int i = row -1, j = col - 1; i >= 0 && j >= 0;i--, j--) {
              if(chessBoard[i][j] == 'Q') return false;
          }
          //检查右上角
          for(int i = row - 1, j = col + 1 ; i >= 0 && j < n; i--, j++) {
              if(chessBoard[i][j] == 'Q') return false;
          }
          return true;
      }
  }
  ```

  [有效的数独](https://leetcode-cn.com/problems/valid-sudoku/description/)

  ```java
     //时间空间都是O(1) 
     public boolean isValidSudoku(char[][] board) {
          for (int i = 0; i < 9; i++) {
              Set<Character> rows = new HashSet<>(), cols = new HashSet<>(), cube = new HashSet<>();
              for (int j = 0; j < 9; j++) {
                  if(board[i][j] != '.' && !rows.add(board[i][j])) return false;
                  //注意是j\i
                  if(board[j][i] != '.' && !cols.add(board[j][i])) return false;
                  //方块计算
                  int rowIndex = 3 * (i / 3);
                  int colIndex = 3 * (i % 3);
                  if(board[rowIndex + j/3][colIndex + j % 3] != '.' && !cube.add(board[rowIndex + j/3][colIndex + j % 3])) return false;
              }
          }
          return true;
      }
  
  ```

### 3.2 双向BFS

[单词接龙](https://leetcode-cn.com/problems/word-ladder/)

```java
//双向BFS用到两个set代替之前queue，并可根据set数量进行随时交换提高效率
class SolutionP127_2 {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(),
                beginSet = new HashSet<>(),
                endSet = new HashSet<>(),
                visited = new HashSet<>();
        for (String word : wordList) wordSet.add(word);
        if (!wordSet.contains(endWord)) return 0;
        int len = 1;
        beginSet.add(beginWord);
        endSet.add(endWord);

        while (!beginSet.isEmpty() && !endWord.isEmpty()) {
            //交换，使得beginSet数量小,从小的地方扩散
            if (beginSet.size() > endSet.size()) {
                Set<String> set = beginSet;
                beginSet = endSet;
                endSet = set;
            }

            Set<String> temp = new HashSet<>();
            for (String word : beginSet) {
                char[] ch = word.toCharArray();
                for (int i = 0; i < ch.length; i++)
                    for (char c = 'a'; c <= 'z'; c++) {
                        char origin = ch[i];
                        ch[i] = c;
                        String target = String.valueOf(ch);
                        if (endSet.contains(target)) return len + 1;
                        if (!visited.contains(target) && wordSet.contains(target)) {
                            visited.add(target);
                            temp.add(target);
                        }
                        ch[i] = origin;
                    }
            }
            len++;
            beginSet = temp;
        }
        return 0;

    }

}
```

[最小基因变化](https://leetcode-cn.com/problems/minimum-genetic-mutation/)

```java
//双向BFS，与单词接龙一个解法
class Solution433 {
    char[] fector = {'A', 'C', 'G', 'T'};

    public int minMutation(String start, String end, String[] bank) {
        Set<String> startSet = new HashSet<>(),
                endSet = new HashSet<>(),
                visited = new HashSet<>(),
                bankSet = new HashSet<>();
        int res = 0;
        //全部赋值至Set后续好判断是否存在
        Collections.addAll(bankSet, bank);
        if (!bankSet.contains(end)) return -1;
        startSet.add(start);
        endSet.add(end);
        while (!startSet.isEmpty() && !endSet.isEmpty()) {
            if (startSet.size() > endSet.size()) {
                Set<String> set = startSet;
                startSet = endSet;
                endSet = set;
            }
            Set<String> temp = new HashSet<>();
            for (String word : startSet) {
                char[] chs = word.toCharArray();
                for (int i = 0; i < 8; i++){
                    char origin = chs[i];
                    for (int j = 0; j < 4; j++) {
                        chs[i] = fector[j];
                        String target = String.valueOf(chs);
                        if(endSet.contains(target)) return res + 1;
                        if (!visited.contains(target) && bankSet.contains(target)) {
                            temp.add(target);
                            visited.add(target);
                        }
                        chs[i] = origin;
                    }
                }
            }
            res++;
            startSet = temp;
        }
        return -1;
    }
}

```



### 3.3 启发式搜索 heuristic

参考，智能搜索，使用PQ 

- [ A* 代码模板](https://shimo.im/docs/8CzMlrcvbWwFXA8r)
- [相似度测量方法](https://dataaspirant.com/2015/04/11/five-most-popular-similarity-measures-implementation-in-python/)
- [二进制矩阵中的最短路径的 A* 解法](https://leetcode.com/problems/shortest-path-in-binary-matrix/discuss/313347/A*-search-in-Python)
- [ 8 puzzles 解法比较](https://zxi.mytechroad.com/blog/searching/8-puzzles-bidirectional-astar-vs-bidirectional-bfs/)

