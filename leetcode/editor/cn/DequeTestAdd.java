package leetcode.editor.cn;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Author: baisiqi01
 * @DateTime: 8/7/21 11:30 PM
 * @Description: deque addFirst addLast
 */
public class DequeTestAdd {
    public static void main(String[] args) {
        Deque<String> deque = new LinkedList<String>();
        deque.addFirst("a");
        deque.addFirst("b");
        deque.offerFirst("c");
        deque.addLast("d");
        deque.addLast("e");
        deque.offerLast("f");
        //[c, b, a, d, e, f]
        String c = deque.getFirst();
        //[c, b, a, d, e, f]
         c = deque.removeFirst();
        //[ b, a, d, e, f]
        String f =deque.pollLast();
        System.out.println(deque);
    }

}
