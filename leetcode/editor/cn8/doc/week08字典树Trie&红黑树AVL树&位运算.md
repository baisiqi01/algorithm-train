# 字典树Trie&红黑树AVL树&位运算

[TOC]



## 字典树Trie

### 1、字典树数据结构

字典树Trie，又称前缀树、单词查找数或键树。典型应用是用于统计和排序大量字符串，所以经常呗搜索引系统用于文本词频统计

优点：最大限度的减少无畏的字符串比较，查询效率比哈希表高

### 2、字典树的核心思想

1、Trie 树的核心思想是空间换时间。 --类似跳表

2、利用字符串的公共前缀来降低查询时间的开销以达到提高效率的目的。

### 3、字典树的基本性质

1. 结点本身不存完整单词;

2. 从根结点到某一结点，路径上经过的字符连接起来，为该结点对应的字符串;

3. 每个结点的所有子结点路径代表的字符都不相同。

- [实现 Trie](https://leetcode-cn.com/problems/implement-trie-prefix-tree/solution/)
- [ Tire 树代码模板](https://shimo.im/docs/DP53Y6rOwN8MTCQH)

```java
//Java

class Trie {

  private boolean isEnd;

  private Trie[] next;

  /** Initialize your data structure here. */

  public Trie() {

​    isEnd = false;

​    next = new Trie[26];

  }

  

  /** Inserts a word into the trie. */

  public void insert(String word) {

​    if (word == null || word.length() == 0) return;

​    Trie curr = this;

​    char[] words = word.toCharArray();

​    for (int i = 0;i < words.length;i++) {

​      int n = words[i] - 'a';

​      if (curr.next[n] == null) curr.next[n] = new Trie();

​      curr = curr.next[n];

​    }

​    curr.isEnd = true;

  }

  

  /** Returns if the word is in the trie. */

  public boolean search(String word) {

​    Trie node = searchPrefix(word);

​    return node != null && node.isEnd;

  }

  

  /** Returns if there is any word in the trie that starts with the given prefix. */

  public boolean startsWith(String prefix) {

​    Trie node = searchPrefix(prefix);

​    return node != null;

  }

  private Trie searchPrefix(String word) {

​    Trie node = this;

​    char[] words = word.toCharArray();

​    for (int i = 0;i < words.length;i++) {

​      node = node.next[words[i] - 'a'];

​      if (node == null) return null;

​    }

​    return node;

  }

}
```



### 4、字典树习题

1、[实现 Trie (前缀树) ](https://leetcode-cn.com/problems/implement-trie-prefix-tree/#/description)

```java
class Trie {
    private Trie[] children;
    private boolean isEnd;


    /**
     * Initialize your data structure here.
     */
    public Trie() {
        children = new Trie[26];
        isEnd = false;
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if (node.children[index] == null) node.children[index] = new Trie();
            node = node.children[index];
        }
        node.isEnd = true;
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEnd == true;
    }


    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    private Trie searchPrefix(String word) {
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if (node.children[index] == null) return null;
            node = node.children[index];
        }
        return node;


    }
}

```

2、[单词搜索 II ](https://leetcode-cn.com/problems/word-search-ii/)分析单词搜索 2 

        用 Tire 树方式实现的时间复杂度 O(m *n *)
        m = board.length;
        n = board[0].length;


```java
class SolutionP212 {
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
```



## 红黑树和AVL树

### 1、AVL树

1、发明者G.M.Adelson 和 Evgenii Landis，平衡二插搜索树

2、平衡因子 balanceFactor

是左子树高度减去柚子树的高度（有时候相反） 取值范围{-1,0,1}

3、通过旋转进行平衡（四种）

​	==1）左旋，右右子树==

​	==2）右旋，左左子树==

​	==3）左右旋，左右子树==

​	==4）有做旋，右左子树==

4、不足需要存储额外信息，切调整次数频繁

https://zhuanlan.zhihu.com/p/63272157

### 2、红黑树  :Red-black Tree tradeoff取舍

1、红黑树是益中近似平衡二叉树，它能够顾保证任何一个节点的左右子树的==高度差小于2倍==

==2==、特点（5个）：

1. 每个节点要么红色，要么黑色

2. 根节点是黑色

3. 每个叶子节点（NIL节点，空节点）是黑色的

4. 不能有相邻的两个红色节点

5. 从任意节点到期每个叶子的所有路径都包含相同数目的黑色节点

   

3、其他

• AVL trees provide faster lookups than Red Black Trees because they are more strictly balanced.

- Red Black Trees provide faster insertion and removal operations than AVL trees as fewer rotations are done due to relatively relaxed balancing.
- AVL trees store balance factors or heights with each node, thus requires storage for an integer per node whereas Red Black Tree requires only 1 bit of information per node.
- Red Black Trees are used in most of the language libraries
   like map, multimap, multisetin C++whereas AVL trees are used in databases where faster retrievals are required.



## 位运算

### 1、运算法

​	1）左右移 >>  <<

​	 2）或 |    与&  按位取反~  按位异或^ 

### 2、异或的特点

​    x^0 = x

​	x^1s = ~x //1s代表全1

​	x^(~x) = 1s

​	x^x =0

​	c=a^b => a^c=b,b^c=a //交换两个数

### 3、指定位置的位运算

\1. 将x最右边的n位清零:x&(~0<<n)
 \2. 获取x的第n位值(0或者1):(x>>n)&1
 \3. 获取x的第n位的幂值:x&(1<<n)
 \4. 仅将第n位置为1:x|(1<<n)
 \5. 仅将第n位置为0:x&(~(1<<n))
 \6. 将x最高位至第n位(含)清零:x&((1<<n)-1)

### 4、实战要点

- 判断奇偶:
   x%2 == 1     —>(x&1)==1 x%2 ==   0 —>(x&1)==0

- x>>1—>x/2.
   即: x=x/2; —> x=x>>1;

  mid=(left+right)/2; —> mid=(left+right)>>1;

- X=X&(X-1)清零最低位的1

- X&-X=>得到最低位的1

- X&~X=>0



### 5、实战练习

- [位 1 的个数](https://leetcode-cn.com/problems/number-of-1-bits/)

  ```java
  //循环次数从32次( :&1   >>)减少至存在1的个数   
  class Solution {
      // you need to treat n as an unsigned value
      public int hammingWeight(int n) {
          int count = 0;
          //注意负数
          while (n != 0) {
              n = n & (n - 1);
              count++;
          }
          return count;
      }
  }
  ```

  

- [ 2 的幂](https://leetcode-cn.com/problems/power-of-two/)

  ```java
  class SolutionP231 {
      public boolean isPowerOfTwo(int n) {
          return n > 0 && (n & (n -1)) == 0;
      }
  }
  ```

  

- [颠倒二进制位](https://leetcode-cn.com/problems/reverse-bits/)

  ```java
  public class Solution {
      // you need treat n as an unsigned value
      public int reverseBits(int n) {
          int reverse = 0;
          for ( int i = 0 ; i < 32; i++) {
              //注意&运算符优先级，如果无括号错误
              reverse |= (n & 1) << (31 - i);
              //逻辑右移，高位用符号填充
              n >>>= 1;
          }
          return reverse;
      }
  }
  ```

  

- [ N 皇后 ](https://leetcode-cn.com/problems/n-queens/description/) 待完成

- [ N 皇后 II ](https://leetcode-cn.com/problems/n-queens-ii/description/)待完成

  