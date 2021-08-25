
//给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。 
//
// 
//
// 示例： 
//二叉树：[3,9,20,null,null,15,7], 
//
// 
//    3
//   / \
//  9  20
//    /  \
//   15   7
// 
//
// 返回其层序遍历结果： 
//
// 
//[
//  [3],
//  [9,20],
//  [15,7]
//]
// 
// Related Topics 树 广度优先搜索 二叉树


package leetcode.editor.cn4;

import leetcode.editor.util.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

//Java：二叉树的层序遍历
public class P102BinaryTreeLevelOrderTraversal {
    public static void main(String[] args) {
        String s = "[3,9,20,null,null,15,7]";
        Solution102_2 solution = new Solution102_2();
        solution.levelOrder(TreeNode.mkTree(s));
    }
}
//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
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
//leetcode submit region end(Prohibit modification and deletion)

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