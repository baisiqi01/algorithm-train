
//序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，采取相反方
//式重构得到原数据。 
//
// 请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串
//反序列化为原始的树结构。 
//
// 提示: 输入输出格式与 LeetCode 目前使用的方式一致，详情请参阅 LeetCode 序列化二叉树的格式。你并非必须采取这种方式，你也可以采用其他的
//方法解决这个问题。 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [1,2,3,null,null,4,5]
//输出：[1,2,3,null,null,4,5]
// 
//
// 示例 2： 
//
// 
//输入：root = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：root = [1]
//输出：[1]
// 
//
// 示例 4： 
//
// 
//输入：root = [1,2]
//输出：[1,2]
// 
//
// 
//
// 提示： 
//
// 
// 树中结点数在范围 [0, 104] 内 
// -1000 <= Node.val <= 1000 
// 
// Related Topics 树 深度优先搜索 广度优先搜索 设计 字符串 二叉树


package leetcode.editor.cn3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//Java：二叉树的序列化与反序列化
public class P297SerializeAndDeserializeBinaryTree {
    public static void main(String[] args) {
        //Solution solution = new Solution();
        //TEST
    }
}

//class TreeNode {
//    int val;
//    TreeNode left;
//    TreeNode right;
//
//    TreeNode(int x) {
//        val = x;
//    }
//}
//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for a binary tree node.
 */


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

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
//leetcode submit region end(Prohibit modification and deletion)
