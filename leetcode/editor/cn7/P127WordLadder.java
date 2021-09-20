
//字典 wordList 中从单词 beginWord 和 endWord 的 转换序列 是一个按下述规格形成的序列： 
//
// 
// 序列中第一个单词是 beginWord 。 
// 序列中最后一个单词是 endWord 。 
// 每次转换只能改变一个字母。 
// 转换过程中的中间单词必须是字典 wordList 中的单词。 
// 
//
// 给你两个单词 beginWord 和 endWord 和一个字典 wordList ，找到从 beginWord 到 endWord 的 最短转换序列 中
//的 单词数目 。如果不存在这样的转换序列，返回 0。 
// 
//
// 示例 1： 
//
// 
//输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","lo
//g","cog"]
//输出：5
//解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。
// 
//
// 示例 2： 
//
// 
//输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","lo
//g"]
//输出：0
//解释：endWord "cog" 不在字典中，所以无法进行转换。 
//
// 
//
// 提示： 
//
// 
// 1 <= beginWord.length <= 10 
// endWord.length == beginWord.length 
// 1 <= wordList.length <= 5000 
// wordList[i].length == beginWord.length 
// beginWord、endWord 和 wordList[i] 由小写英文字母组成 
// beginWord != endWord 
// wordList 中的所有字符串 互不相同 
// 
// Related Topics 广度优先搜索 哈希表 字符串


package leetcode.editor.cn7;

import java.util.*;

//Java：单词接龙
public class P127WordLadder {
    public static void main(String[] args) {
//        输入
////        "hot"
////        "dog"
////                ["hot","dog"]
        SolutionP127_2 solution = new SolutionP127_2();
//        String[] s = {"hot", "dot", "dog", "lot", "log", "cog"};
        String[] s = {"hot", "dog"};
        System.out.println(solution.ladderLength("hot", "dog", Arrays.asList(s)));
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
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
//leetcode submit region end(Prohibit modification and deletion)
