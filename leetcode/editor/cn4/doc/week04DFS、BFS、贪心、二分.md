# week04  DFS、BFS、贪心、二分

## 1、DFS（deep first search）

### 1.1模板-使用递归

https://shimo.im/docs/UdY2UUKtliYXmk8t/read

```
#二叉树
def dfs(node)
	 if node in visited
	 #已经访问不再访问
	 retrun
	 visited.add(node)
	#process current node
	#。。。 #logic here
	dfs(node.left)
	dfs(node.right)
#多叉树模板
	visited = set()
	
	def dfs(node, visited);
		visited.add(node)
		#process current node heer
		...
		for next_node in node.children();
			if not next_node in visited:
				dfs(next_node, visited)
#也可以用栈模拟递归


```

### 1.2代码

#### [二叉树的层序遍历](https://leetcode-cn.com/problems/binary-tree-level-order-traversal/#/description)

```java
//BFS 使用队列
class Solution102 {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            //记录当前队列长度
            int queueLen = queue.size();
            //把当前层所有节点输出
            for (int i = 0; i < queueLen; i++) {
                TreeNode node = queue.poll();
                temp.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            res.add(temp);
        }
        return res;
    }
}

//DFS,使用level来标识哪一层，放入对应的list中，主要起始位置未0： dfs(root, 0);
class Solution102_2 {
    List<List<Integer>> res = new ArrayList<>();
    public List<List<Integer>> levelOrder(TreeNode root) {
        dfs(root, 0);
        return res;
    }

    private void dfs(TreeNode root, int level) {
        if (root == null) return;
        if (res.size() == level) res.add(new ArrayList<>());
        res.get(level).add(root.val);
        dfs(root.left,level + 1);
        dfs(root.right, level + 1);
    }
}
```



#### [最小基因变化](https://leetcode-cn.com/problems/minimum-genetic-mutation/#/description)

```java
//BFS
class Solution433{
    public int minMutation(String start, String end, String[] bank) {
        if (start.equals(end)) return 0;
        Set<String> visited = new HashSet<>();
        Deque<String> queue = new LinkedList<>();
        Set<String> bankSet = new HashSet<>();
        for (String s : bank) bankSet.add(s);
        if (!bankSet.contains(end)) return -1;
        visited.add(start);
        queue.offer(start);
        int level = 0;
        char[] charSet = new char[]{'A', 'C', 'G', 'T'};
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String curr = queue.poll();
                if (curr.equals(end)) return level;

                char[] currArray = curr.toCharArray();
                for (int i = 0; i < currArray.length; i++) {
                    char old = currArray[i];
                    for (char c : charSet) {
                        currArray[i] = c;
                        String next = new String(currArray);
                        if (!visited.contains(next) && bankSet.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }
                    }
                    currArray[i] = old;
                }
            }
            return level++;
        }
        return -1;

    }
}

//DFS待补充
```



#### [在每个树行中找最大值](https://leetcode-cn.com/problems/find-largest-value-in-each-tree-row/#/description)

```java
//BFS
class Solution {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int max = Integer.MIN_VALUE;
            for (int i = 0; i< size; i++){
                TreeNode node =queue.poll();
                if (node.val > max) max = node.val;
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            res.add(max); 
        }
        return res;
    }
}

class SolutionP515_1{
    //DFS
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        dfs(root,1, res);
        return res;

    }

    private void dfs(TreeNode node, int level, List<Integer> res) {
        if (node == null) return;
        if (res.size() + 1 ==level) res.add(node.val);
        else res.set(level -1,Math.max(node.val,res.get(level - 1) ));
        dfs(node.left,level + 1,res);
        dfs(node.right,level+1, res);
    }
}

```



## 2、BFS(Breadth-First-search)

### 2.1模板 -队列

https://shimo.im/docs/ZBghMEZWix0Lc2jQ/read

```
//1java对应deque
def BFS(graph, start, end)
	queue  = []
	queue.append([start])
	visited.add(start)
	
	while queue:
		node = queue.popleft()
		visited.add(node)
		
		process(node)
		nodes = generate_related_node(node)
		queue.push(nodes)
```

### 2.2代码示例

#### [最小基因变化](https://leetcode-cn.com/problems/minimum-genetic-mutation/#/description)

使用队列存储子节点，暂分别尝试

```java
class Solution {
    public int minMutation(String start, String end, String[] bank) {
        if (start.equals(end)) return 0;
        Set<String> visited = new HashSet<>();
        Deque<String> queue = new LinkedList<>();
        Set<String> bankSet = new HashSet<>();
        for (String s : bank) bankSet.add(s);
        if (!bankSet.contains(end)) return -1;
        visited.add(start);
        queue.offer(start);
        int level = 0;
        char[] charSet = new char[]{'A', 'C', 'G', 'T'};
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String curr = queue.poll();
                if (curr.equals(end)) return level;

                char[] currArray = curr.toCharArray();
                for (int i = 0; i < currArray.length; i++) {
                    char old = currArray[i];
                    for (char c : charSet) {
                        currArray[i] = c;
                        String next = new String(currArray);
                        if (!visited.contains(next) && bankSet.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }
                    }
                    currArray[i] = old;
                }
            }
            level++;
        }
        return -1;

    }
}
```

#### [单词接龙](https://leetcode-cn.com/problems/word-ladder/description/)

```java
//单向BFS，可以后续尝试双向BFS，减少时间复杂度
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        //wordSet存储中间字符串，方便判断
        Set<String>  wordSet = new HashSet<>();
        if(wordSet.size() != 0 ||!wordList.contains(endWord)) return 0;
        for(String s : wordList) wordSet.add(s);
        //广度优先需要声明queue 和 visitedList
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        //开始广度，其实步骤1
        queue.offer(beginWord);
        visited.add(beginWord);
        wordSet.remove(beginWord);
        int step = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();
                if(changeWordEveryOneLetter(currentWord,beginWord,endWord,queue,visited,wordSet)) {
                    return step + 1 ;
                }
            }
            step++;
        }
        return 0;
    }

    private boolean changeWordEveryOneLetter(String currentWord, String beginWord, String endWord, Queue<String> queue, Set<String> visited, Set<String> wordSet) {
        char[] curChar = currentWord.toCharArray();
        for (int i = 0; i < endWord.length(); i++) {
            char originChar =curChar[i];
          //优化时间复杂度，解决如果wordSet过大时超时问题
            for (char j = 'a'; j<= 'z'; j++) {
                if(j == originChar) continue;
                curChar[i] = j;
                String nextWord = String.valueOf(curChar);
                if(wordSet.contains(nextWord)) {
                    if(endWord.equals(nextWord)) {
                        return true;
                    }
                    if(!visited.contains(nextWord)) {
                        queue.offer(nextWord);
                        visited.add(nextWord);
                    }
                }
            }
            curChar[i] = originChar;
        }
        return false;
    }
}
```

#### [扫雷游戏](https://leetcode-cn.com/problems/minesweeper/description/)

```java
//BFS
class Solution {
    //坐标
    int[] dirX = {-1, -1, -1, 0, 1, 1, 1, 0};
    int[] dirY = {-1, 0, 1, 1, 1, 0, -1, -1};

    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0];
        int y = click[1];
        if (board[x][y] == 'M') board[x][y] = 'X';
        else bfs(x, y, board);
        return board;
    }

    private void bfs(int x, int y, char[][] board) {
        //BFS需要队列和visited
        Queue<int[]> queue = new LinkedList<>();
        int xLen = board.length;
        int yLen = board[0].length;
        boolean[][] visited = new boolean[xLen][yLen];
        visited[x][y] = true;
        queue.offer(new int[]{x, y});
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int rx = temp[0], ry = temp[1];
            int count = 0;
            for (int i = 0; i < 8; i++) {
                int xx = rx + dirX[i];
                int yy = ry + dirY[i];
                //判断是否在面板 
                if (xx < 0 || yy < 0 || xx >= xLen || yy >= yLen) continue;
                if (board[xx][yy] == 'M') count++;
            }
            if (count > 0) {
                board[rx][ry] = (char) ('0' + count);
            } else {
                board[rx][ry] = 'B';
                for (int i = 0; i < 8; i++) {
                    int xx = rx + dirX[i];
                    int yy = ry + dirY[i];
                    //判断是否在面板,以及是否访问过、是否为E 
                    if (xx < 0 || yy < 0 || xx >= xLen || yy >= yLen || board[xx][yy] != 'E' || visited[xx][yy])
                        continue;
                    //等于E的情况，并且没有处理过的点
                    queue.offer(new int[]{xx, yy});
                    visited[xx][yy] = true;

                }
            }
        }
    }
}
//DFS
class Solution {
    //坐标
    int[] dirX = {-1, -1, -1, 0, 1, 1, 1, 0};
    int[] dirY = {-1, 0, 1, 1, 1, 0, -1, -1};

    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0];
        int y = click[1];
        //如果是地雷结束
        if (board[x][y] == 'M') board[x][y] = 'X';
        else {
            dfs(x, y, board);
        }
        return board;
    }

    private void dfs(int x, int y, char[][] board) {
        int count = 0;
        int yLen = board[0].length;
        int xLen = board.length;
        for (int i = 0; i < 8; i++) {
            int xx = x + dirX[i];
            int yy = y + dirY[i];
            if (xx < 0 || yy < 0 || xx >= xLen || yy >= yLen) continue;
            if (board[xx][yy] == 'M') count++;
        }
        if (count > 0) board[x][y] = (char) ('0' + count);
            //规则2
        else {
            board[x][y] = 'B';
            for (int i = 0; i < 8; i++) {
                int xx = x + dirX[i];
                int yy = y + dirY[i];
                //B已经判断过了
                if (xx < 0 || yy < 0 || xx >= xLen || yy >= yLen || board[xx][yy] != 'E') continue;
                dfs(xx, yy, board);
            }
        }
    }
}
```



### 3、贪心

找出局部最优解，实际全局不一定最优-接近

使用最大、最小等情况时可以考虑

#### [买卖股票的最佳时机 II ](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/description/)

```java
//贪心，只能计算最大利润实际
class Solution122 {
    public int maxProfit(int[] prices) {
        int res = 0;
        if (prices.length < 1) return res;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) res += prices[i] - prices[i - 1];
        }
        return res;
    }
}
```



#### [柠檬水找零](https://leetcode-cn.com/problems/lemonade-change/description/)

```java
//正常流量,贪心：优先10 无法10再用5
class Solution {
    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        for (int i = 0; i < bills.length; i++) {
            if (bills[i] == 5) {
                five++;
            } else if (bills[i] == 10) {
                if (five < 1) return false;
                five--;
                ten++;
            } else {
                if (ten > 0 && five > 0) {
                    ten--;
                    five--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
```



#### [分发饼干](https://leetcode-cn.com/problems/assign-cookies/description/)

```java
//贪心 优先给最少需求孩子满足
class Solution {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int count = 0;
        for (int i = 0, j=0; i<g.length&&j<s.length ;i++) {
            while (j<s.length && g[i] > s[j]  ) j++;
            if (j<s.length){
                j++;
                count++;
            }
        }
        return  count;
    }
}
```



#### [跳跃游戏](https://leetcode-cn.com/problems/jump-game/) 

```java
//贪心，从后往前
class SolutionP55_Greed {
    public boolean canJump(int[] nums) {
        //贪心，从后往前，只记录最前者的值
        if (nums == null) return false;
        int len = nums.length - 1;
        int canReachable = len;
        for (int i = len; i >= 0; i--) {
            if (nums[i] + i >= canReachable) canReachable = i;
        }
        return canReachable == 0;
    }
}

//还可以中间数组O(n^2)
class Solution {
    public boolean canJump(int[] nums) {
        if (nums == null) return false;
        int len = nums.length;
        boolean[] canReachable = new boolean[len];
        canReachable[0] = true;
        for (int i = 0; i < len; i++) {
            if (canReachable[i]) {
                for (int j = 1; j <= nums[i] && i + j < len; j++){
                    canReachable[i + j] = true;
                }
            }
        }
        return canReachable[len -1];
    }
}
```



## 二分查找

1、概念

​      1）要求：有序

​      2）上下界

​	  3） 可以索引访问的 （单链表不可） 

1、代码模板



```

    // Java

    public int binarySearch(int[] array, int target) {

        int left = 0, right = array.length - 1, mid;

        while (left <= right) {

            mid = (right - left) / 2 + left;

            if (array[mid] == target) {

                return mid;

            } else if (array[mid] > target) {

                right = mid - 1;

            } else {

                left = mid + 1;

            }

        }

        return -1;

    }
```

### 代码总结

- #### [ x 的平方根](https://leetcode-cn.com/problems/sqrtx/)

  ```java
  //y=x^2 单调递增，且有上下界，则可以二分查找
  class Solution {
      public int mySqrt(int x) {
          if (x <= 1) return x;
          long left = 1, right = x;
          while (left <= right) {
              //防止越界
              long mid = left + (right - left)/2;
              if (mid * mid > x ) {
                  right =  mid - 1;
              }else {
                  left = mid + 1;
              }
          } 
          return (int)right;
      }
  }
  
  ```

  

- #### [有效的完全平方数](https://leetcode-cn.com/problems/valid-perfect-square/)（亚马逊在半年内面试中考过）

```java
class Solution {
    public boolean isPerfectSquare(int num) {
        if (num < 2) return true;
        long left = 1 , right = num;
        while (left <= right) {
            long mid = left + (right -left)/2;
            if (mid * mid == num) return true;
            else if (mid * mid < num) left = mid + 1;
            else right = mid -1;
        }
        return false;
    }
}
```

- [搜索旋转排序数组](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/)（Facebook、字节跳动、亚马逊在半年内面试常考）

  ```java
  //如果一半是有序的也可以使用二分查找，需要增加一个判断有序的条件
  class Solution {
      public int search(int[] nums, int target) {
          int numsLen = nums.length;
          int left = 0, right = numsLen -1;
          while (left <= right) {
              int mid = left + (right -left)/2;
              if (nums[mid] == target) return  mid;
              //判断左右哪个有序
              if (nums[mid] >= nums[0]) {
                  //左有序，判断target是否在起范围内
                  if (target >= nums[left] && target < nums[mid]) {
                      right = mid -1;
                  } else {
                      left = mid + 1;
                  }
              } else {
                  if (target > nums[mid] && target <= nums[right]) {
                      left = mid + 1;
                  } else  {
                      right = mid -1;
                  }
              }
          }
          return -1;
      }
  }
  ```

  

- [搜索二维矩阵](https://leetcode-cn.com/problems/search-a-2d-matrix/)（亚马逊、微软、Facebook 在半年内面试中考过）

  ```java
  //O(logmn),采用降维做法，难点在于temp取值，也可两次二分
  class Solution {
      public boolean searchMatrix(int[][] matrix, int target) {
          int r = matrix.length;
          int c = matrix[0].length;
          int left = 0,  right = c * r - 1;
          while (left <= right) {
              int mid = left + (right -left)/2;
              int temp = matrix[mid/c][mid%c];
              if (target > temp) {
                  left = mid + 1;
              } else if (target < temp) {
                  right = mid -1;
              } else {
                  return true;
              }
          }
          return false;
  
      }
  }
  ```

  

- [寻找旋转排序数组中的最小值](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/)（亚马逊、微软、字节跳动在半年内面试中考过）

  ```java
  class Solution {
      public int findMin(int[] nums) {
          int left = 0;
          int right = nums.length - 1;
          while (left < right) {
              int mid = left + (right - left) / 2;
              if (nums[mid] >= nums[right]) {
                  left = mid + 1;
              } else {
                  right = mid;
              }
          }
          return nums[left];
      }
  }
  ```

  
