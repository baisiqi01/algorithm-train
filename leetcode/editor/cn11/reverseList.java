package leetcode.editor.cn11;

import javax.xml.soap.Node;

/**
 * @Author: baisiqi01
 * @DateTime: 11/8/21 1:45 PM
 * @Description: TODO
 */
public class reverseList {
    public static void main(String[] args) {
        NodeTest nodeTest = new NodeTest(1);
        nodeTest.next = new NodeTest(2);
        nodeTest.next.next = new NodeTest(3);
        NodeTest reverse = reverseList(nodeTest);
        while (reverse != null) {
            System.out.print(reverse.val + " ");
            reverse = reverse.next;
        }

    }

    public static NodeTest reverseList(NodeTest node) {
        if (node == null) return node;
        NodeTest cur = node;
        NodeTest pre = null;
        while (cur != null) {
            NodeTest next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;

    }
}

class NodeTest {
    int val;
    NodeTest next;

    public NodeTest(int val) {
        this.val = val;
    }

    public NodeTest(int val, NodeTest next) {
        this.val = val;
        this.next = next;
    }

}
