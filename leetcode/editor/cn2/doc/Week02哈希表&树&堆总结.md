# week02哈希、树、堆总结



[TOC]

## 代码总结

### [242. 有效的字母异位词](https://leetcode-cn.com/problems/valid-anagram/)

1、排序比较 nlogn时间复杂度

通过数组排序后直接比较，排序以及比较用java.util工具类

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) {
            return false;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);
        return Arrays.equals(str1,str2);
    }
}
```



2、hashMap

利用数组下标代替hashMap唯一key，判断数组中个元素的值是否遍历结束后回归为0

```java
class Solution242 {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] word = new int[26];
        for (int i = 0; i < s.length(); i++) {
            word[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            word[t.charAt(i) - 'a']--;
            if(word[t.charAt(i) - 'a'] < 0){
                return false;
            }
        }
        return true;
    }
}
```



### [49.字母异位词分组](https://leetcode-cn.com/problems/group-anagrams/)

1、hashMap

遍历数组，先排序，使用排序后的字符串作为hash值入map，并以及key不断添加value值，最后使用ArrayList

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if(strs ==null || strs.length == 0) return new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = String.valueOf(chars);
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<>(map.values());
    }
}
```

2、使用字符串记录子串key值

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map =new HashMap<>();
        if(strs == null || strs.length == 0) return new ArrayList<>();
        for(String s : strs) {
            char[] nums = new char[26];
            for(char c : s.toCharArray()) nums[c - 'a']++;
            String key = String.valueOf(nums);
            if(!map.containsKey(key)) 
                map.put(key, new ArrayList<>());
            map.get(key).add(s);
        } 
        return new ArrayList<>(map.values());
    }
}

```



### [94.二叉树的中序遍历](https://leetcode-cn.com/problems/binary-tree-inorder-traversal/)

1、中序遍历递归方法（左跟右）

```java
//递归方法
class Solution94 {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorder(root,res);
        return res;
    }
    private void inorder(TreeNode root, List<Integer> res) {
        if(root ==  null) return;
        inorder(root.left,res);
        res.add(root.val);
        inorder(root.right,res);
    }
}
```



2、利用栈实现二叉树中序遍历，通过跟节点->左节点 再出站保证左根顺序 最后再添加右节点

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        Deque<TreeNode> stk = new LinkedList<TreeNode>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            root = stk.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }
}
```





### [144二叉树的前序遍历](https://leetcode-cn.com/problems/binary-tree-preorder-traversal/)

1、递归:前序遍历先加根后再加左右节点

```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if(root == null) return list;
        preorder(root,list);
        return list;
    }

    public void preorder(TreeNode root,List<Integer> list) {
                if(root == null) return;
                list.add(root.val);
                preorder(root.left,list);
                preorder(root.right,list);
    }
}
```

2、栈方式，先放入跟节点，同时结果中增加根节点，最后遍历右界定

```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()) {
            while( root != null){
                res.add(root.val);
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            root = root.right;
        }
        return res;
    }
}
```



### [ 590N 叉树的后序遍历](https://leetcode-cn.com/problems/n-ary-tree-postorder-traversal/)

1、递归，通过递归方式可简单进行N叉数遍历，最后要加入根节点

```java
class Solution {
    public List<Integer> postorder(Node root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        dfs(root, res);
        res.add(root.val);
        return res;
    }

    public void dfs(Node root, List<Integer> res) {
        for(Node child : root.children){
            dfs(child,res);
            res.add(child.val);
        }
    }
}

//同一方法递归
class Solution {
    private List<Integer> res = new ArrayList<>();
    public List<Integer> postorder(Node root) {
        if (root == null) return res;
        for(Node child :root.children) {
            postorder(child);
        }
        res.add(root.val);
        return res;
    }
}
```

2、结果根据跟入栈后，将子节点分别入栈（先左再右），去除时候先右再左，使得整体顺序为跟-右-左，再反转（假后序）

```java
class Solution {
    public List<Integer> postorder(Node root) {
        List<Integer> res = new ArrayList<>();
        if(root == null) return res;
        Deque<Node> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            res.add(root.val);
            for(Node child : root.children) {
                stack.push(child);
            }
        }
        Collections.reverse(res);
        return res;
    }
}
```



### [ 589N 叉树的前序遍历](https://leetcode-cn.com/problems/n-ary-tree-preorder-traversal/description/)

```java
//递归方法
class Solution589_1 {
    public List<Integer> preorder(Node root) {
        List<Integer> res = new ArrayList<>();
        dfs(root,res);
        return res;
    }

    public void dfs(Node root, List<Integer> res) {
        if(root == null) return;
        res.add(root.val);
        for(Node child : root.children)
            dfs(child,res);
    }
}
```

2、栈方法，入栈时候需要从右->左入栈，保障处理顺序相反

```java
class Solution {
    public List<Integer> preorder(Node root) {
        List<Integer> res = new ArrayList<>();
        Deque<Node> stack = new LinkedList<>();
        if(root == null) return res;
        stack.push(root);
        while(!stack.isEmpty()) {
            root = stack.pop();
            res.add(root.val);
            for(int i = root.children.size()-1; i>=0;i--) {
                stack.push(root.children.get(i));
            }
        }
        return res;
    }
}
```



### [429_ N 叉树的层序遍历](https://leetcode-cn.com/problems/n-ary-tree-level-order-traversal/)

```java
//使用队列，先放入root、再根据队列层序不断加入队列
class Solution429_1 {
    public List<List<Integer>> levelOrder(Node root) {
        if(root == null) return new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            List<Integer> list =new ArrayList<>();
            int size = queue.size();
            for(int i = 0; i < size;i++ ) {
                Node node = queue.poll();
                list.add(node.val);
                queue.addAll(node.children);
            }
            res.add(list);
        }
        return res;
    }
}
```

```java
//递归方法遍历n叉树层序遍历
class Solution429_2 {
    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> levelOrder(Node root) {
        if (root == null) return res;
        trans(root, 0);
        return res;
    }

    public void trans(Node node, int level) {
        while (res.size() <= level) {
            res.add(new ArrayList<>());
        }
        res.get(level).add(node.val);
        for (Node child : node.children) {
            trans(child, level + 1);
        }
    }
}

//递归方法合并
class Solution {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> res = new ArrayList<>();
        levelOrderHelper(root , 0, res);
        return res;
    }

    private void levelOrderHelper(Node node, int level, List<List<Integer>> res){
        if (node == null) return;
        if (level == res.size()) {
            res.add(new ArrayList<Integer>());
        }
        res.get(level).add(node.val);
        for(Node child : node.children) {
            levelOrderHelper(child, level+1,res);
        }
    }
}
```





### [offer49丑数](https://leetcode-cn.com/problems/chou-shu-lcof/)

```java
//1.动态规划法
class Solution {
    public int nthUglyNumber(int n) {
        int a = 0, b = 0, c = 0;
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            int n2 = dp[a]*2, n3 =dp[b]*3, n5=dp[c]*5;
            dp[i] = Math.min(Math.min(n2, n3), n5);
            if (dp[i] == n2) a++;
            if (dp[i] == n3) b++;
            if (dp[i] == n5) c++;
        }  
        return dp[n-1];
    }
}
```

```java
//堆栈方法,注意使用long类型，使用int当达到1407时越界变为负数
class Solution {
    public int nthUglyNumber(int n) {
        PriorityQueue<Long> minMQ = new PriorityQueue<>();
        Long res = 0L;
        minMQ.add(1L);
        int[] nums = {2, 3, 5};
        for (int i = 0; i < n; i++) {
            res = minMQ.poll();
            for (int fector : nums) {
                Long temp = fector * res;
                if (!minMQ.contains(temp)) {
                    minMQ.offer(temp);
                }
            }
        }
        return res.intValue();
    }
}
```



## HashMap总结

#### 一，相关定义

1、哈希表（hash table），也叫散列表，根据关键码值（key  value）直接进行访问的数据结构

2、散列函数：映射函数（hash function），存放记录的数组叫做哈希表

3、HashMap  key-value，key不重复

#### 二UML图

![image-20210815232748001](/Users/baisiqi/Library/Application Support/typora-user-images/image-20210815232748001.png)

#### 二、 常用方法（put、get）

1、添加元素put

```java
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }
  
    static final int hash(Object key) {
        int h;
      //移位操作主要减少key耦合
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            //resize懒加载
            n = (tab = resize()).length;
      //hash值再哈希表中不存在，即不存在冲突情况
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
      //hash冲突  
      else {
            Node<K,V> e; K k;
        		//第一种冲突，插入的key value的hash、key都相等，则e=p相等
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
        		//第二种冲突，hash值不等，判断p是否属于红黑树
            else if (p instanceof TreeNode)
              	//为红黑树，则添加至红诶嘿数，如该节点已存在则返回该阶段
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        		//第三种不等于首节点，不为红黑树，则为链表
            else {
               //遍历，到尾部如没有元素在尾部天剑
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                      //是否转化红黑树
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //存在该至，则覆盖
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        //长度调整
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }


```



2、获取元素

```java
public V get(Object key) {
    Node<K,V> e;
    //也是调用getNode方法来完成的
    return (e = getNode(hash(key), key)) == null ? null : e.value;
}
 
final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (first = tab[(n - 1) & hash]) != null) {
        //如果是头结点，则直接返回
        if (first.hash == hash && 
            ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        //不是头结点
        if ((e = first.next) != null) {
            //判断是否是红黑树结构
            if (first instanceof TreeNode)
                //红黑树查找
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            do {
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    return null;
```

