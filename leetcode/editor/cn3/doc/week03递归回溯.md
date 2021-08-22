# week03递归、分治、回溯总结

[TOC]



## 1、递归

### 1）递归java模板

```java
public void recur(int level, int param) {  
  // terminator   
  if (level > MAX_LEVEL) {     
    // process result    
    return;   
  }  
  // process current logic   
  process(level, param);   
  // drill down   
  recur( level: level + 1, newParam);  
  // restore current status  
}
```

### 2）递归代码总结

#### [22括号生成](https://leetcode-cn.com/problems/generate-parentheses/) 

1、找相似性 根据模板terminal、process、drill down、restore实现

2、实现后优化 ->剪枝 时间复杂度不变减少减少多余或者无用循环

```java
//left right代表左右括号剩余个数，注意右括号剩余必须大于左括号剩余才可添加！减少一个传参：不用传数量n参数
class Solution22_1 {
    private List<String> res = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        helper2(n, n, "");
        return res;
    }

    private void helper2(int left, int right, String s) {
        if (left == 0 && right == 0) {
            res.add(s);
            return;
        }
        //左括号只要有剩余就可以添加
        if (left > 0) helper2(left - 1, right, s+'(');
        //右括号剩余必须大于左括号剩余才可添加！！
        if (right >0 && left < right) helper2(left, right - 1, s+')');
    }
}

//较Solution22_0 增加括号合法性拦截，即剪枝
class Solution {
    private List<String> res = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        _genertate(0, 0, n, "");
        return res;
    }

    private void _genertate(int left, int right, int n, String s) {
        //terminal
        if (left == n && right == n) {
            res.add(s);
            return;
        }
        //process//合并至drill down
        // String s1 = s + '(';
        // String s2 = s + ')';
        //drill down
      	//剪枝
        if (left < n) _genertate(left + 1, right, n, s + '(');
        if (right < left) _genertate(left, right + 1, n, s + ')');

        //restore，无
    }
}

//生成所有括号不考虑括号规则，初始递归代码，后续添加剪枝
class Solution22_0 {
    private List<String> res = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        helper(0, 2 * n, "");
        return res;
    }

    private void helper(int level, int max, String s) {
        //terminal
        if (level >= max) {
            res.add(s);
            return;
        }
        //process
        String s1 = s + '(';
        String s2 = s + ')';
        //drill down
        helper(level + 1, max, s1);
        helper(level + 1, max, s2);
        //restore null
    }

}

```

#### [验证二叉搜索树](https://leetcode-cn.com/problems/validate-binary-search-tree)

1、递归，每个元素判断一次即访问一次时间复杂度O(n)

```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        return validBST( Long.MIN_VALUE,Long.MAX_VALUE,root);

    }

    private boolean validBST(Long min, Long max, TreeNode node) {
        //terminal
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return validBST(min, (long) node.val, node.left) && validBST((long) node.val, max,node.right );
    }
}
```

2、中序遍历

```java
class Solution98_1 {
    Long temp = Long.MIN_VALUE;

    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        if (!isValidBST(root.left)) return false;
        if (root.val <= temp) return  false;
        temp  = (long)root.val ;
        return isValidBST(root.right);
    }
}
```

#### [二叉树的最近公共祖先](https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/)

1、递归方法,时间复杂度O(n)

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //terminal，当前节点
        if (root == null || root ==p || root == q) return  root;
        TreeNode left =lowestCommonAncestor(root.left,p,q);
        TreeNode right =lowestCommonAncestor(root.right,p,q);
        // 如果在左子树中 p和 q都找不到，都在右子树中
        if (left == null) return right;
        if (right == null) return left;
        //当 left和 right均不为空时，说明 p、q节点分别在root异侧, 最近公共祖先即为 root
        return root;
    }
}	
```

#### [二叉树的序列化与反序列化](https://leetcode-cn.com/problems/serialize-and-deserialize-binary-tree/)

```java
class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return rserializde(root, "");
    }

    private String rserializde(TreeNode root, String s) {
        //拼接字符字符串，null转化成字符串None
        if (root == null) {
            s += "None,";
        } else {
            //前序遍历
            s += root.val  + ",";
            s = rserializde(root.left,s);
            s = rserializde(root.right,s);
        }
        return s;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        // 字符串转换数组
        String[] dataArray = data.split(",");
        // 字符串数组转化成链表支持获取 和 删除
        List<String>  dataList = new LinkedList<>(Arrays.asList(dataArray));
        return rdeserialize(dataList);

    }

    private TreeNode rdeserialize(List<String> dataList) {
        //注意不能用==判断
        if (dataList.get(0).equals("None")) {
            dataList.remove(0);
            return null;
        }
        //根左右
        TreeNode root = new TreeNode(Integer.valueOf(dataList.get(0)));
        dataList.remove(0);
        root.left = rdeserialize(dataList);
        root.right = rdeserialize(dataList);
        return  root;

    }
}
```

#### [组合](https://leetcode-cn.com/problems/combinations/)

```
//递归方法，分别考虑处理当前位置还是跳过当前位置,进行递归
class Solution {
    List<Integer> tempList = new ArrayList<>();
    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        dfs(1, n, k);
        return res;
    }

    private void dfs(int cur, int n, int k) {
        //terminal
        //剪枝
        if (tempList.size() + (n - cur + 1) < k) return;
        if (tempList.size() == k) {
            res.add(new ArrayList<Integer>(tempList));
            return;
        }
        //process 选择当前位置
        tempList.add(cur);
        //drill down,add cur
        dfs(cur + 1, n, k);
        //处理跳过场景,先删除之前添加的再向下
        tempList.remove(tempList.size() - 1);
        //考虑不选择当前位置
        dfs(cur+1, n, k);
    }
}
```



## 2、分治&回溯

### 2.1分治代码模板

```java
    private static int divide_conquer(Problem problem,) {
        if (problem == NULL) {
            int res = process_last_result();
            return res;
        }
        subProblems = split_problem(problem)
        res0 = divide_conquer(subProblems[0]) 
        res1 = divide_conquer(subProblems[1])
        result = process_result(res0, res1);
        return result;
    }
```

### 2.2 代码总结

#### [ Pow(x, n) ](https://leetcode-cn.com/problems/powx-n/)

```java
//递归，相似性：计算想x^n 当n为偶数时候计算 x^(n/2) * x^(n/2),当n为基数 x^(n/2) * x^(n/2) * x，使得时间复杂度降到O(logn)
class Solution50_3 {
    //递归
    public double myPow(double x, int n) {
        if (n == Integer.MIN_VALUE) {
            x = x * x;
            n = n / 2;
        }
      //未判断最小值，-2147483648取相反值仍为最小值，需注意
        if (n < 0) {
            n = -n;
            x = 1 / x;
        }
        if (n == 0) return 1;
        double temp = myPow(x, n / 2);
      //使用？：运算更为简洁
        if (n % 2 == 0) {
            return temp * temp;
        } else {
            return temp * temp * x;
        }
    }
}


class Solution {
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


//暴力法，O(n)时间复杂度,github会提示超出时间复杂度
class Solution {
    private double res;
    //暴力法
    public double myPow(double x, int n) {
        int N = n;
        if ( n < 0 ){
            N = -n;
            x = 1/x;
        }
        double res = 1;
        for (int i = 0; i < N ; i++) {
            res = res * x;
        }
        return res;
    }
}
```

#### [二叉树的最大深度](https://leetcode-cn.com/problems/maximum-depth-of-binary-tree)

```java
class Solution {
    public int maxDepth(TreeNode root) {
       //terminal 
        if (root == null) return 0;
      	//分治，变为计算左右深度子问题
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        //处理结果
        return Math.max(left, right) + 1; 
    } 
}
```

#### [二叉树的最小深度](https://leetcode-cn.com/problems/minimum-depth-of-binary-tree)

```java
class Solution {
    public int minDepth(TreeNode root) {
        //1、根节点为空，返回0
        if (root == null) return 0;
       //2、子节点都为空，返回1
        if (root.left == null && root.right == null) return 1;
       //3、其中一个节点为空，根据示例需要计算另一个分支大小，同时需要记住要加1
        if (root.left == null) return minDepth(root.right)+1;
        if (root.right == null) return minDepth(root.left)+1;
        int left = minDepth(root.left);
        int right = minDepth(root.right);
      //4、当前节点中左右节点都存在则需比较取最小值
        return Math.min(left,right)+1;
    }
}
```

#### [子集](https://leetcode-cn.com/problems/subsets/)

```

```

#### [从前序与中序遍历序列构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)

```java
//先序遍历起点元素，根节点，并根据根节点和中序遍历可以区分左右子树，根据其相似性进行分治
class Solution105 {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int preLen = preorder.length;
        int inLen = inorder.length;
        if (preLen != inLen) {
            throw new RuntimeException("error input");
        }
        return buildTree(preorder, 0, preLen - 1, inorder, 0, inLen - 1);
    }

    private TreeNode buildTree(int[] preorder, int preLeft, int preRight, int[] inorder, int inLeft, int inRight) {
        if (preLeft > preRight || inLeft > inRight) return null;
        int pivot = preorder[preLeft];
        TreeNode root = new TreeNode(pivot);
        int pivotIndex = inLeft;
        while (inorder[pivotIndex] != pivot) {
            pivotIndex++;
        }
        root.left = buildTree(preorder, preLeft + 1, pivotIndex - inLeft + preLeft, inorder, inLeft, pivotIndex - 1);
        root.right = buildTree(preorder, pivotIndex - inLeft + preLeft + 1, preRight, inorder, pivotIndex + 1, inRight);
        return root;
    }
}
```

#### [全排列](https://leetcode-cn.com/problems/permutations/)

```
//回溯解决多解问题
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> list = new LinkedList<>();
        return dfs(nums, res, list);

    }

    private List<List<Integer>> dfs(int[] nums, List<List<Integer>> res, LinkedList<Integer> list) {
        if(list.size() == nums.length) {
            res.add(new ArrayList<>(list));
            return res;
        }
        for (int i = 0; i < nums.length ;i++) {
            if (list.contains(nums[i])) continue;
            list.add(nums[i]);
            dfs(nums,res,list);
            //回溯
            list.removeLast();
        }

        return res;
    }
}
```

