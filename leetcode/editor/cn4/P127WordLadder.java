
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


package leetcode.editor.cn4;

import java.util.*;

//Java：单词接龙
public class P127WordLadder{
    public static void main(String[] args) {
        SolutionP127 solution = new SolutionP127();
       String[] s = {"hot","dot","dog","lot","log","cog"};
       solution.ladderLength("hit","cog",Arrays.asList(s));
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionP127 {
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
//leetcode submit region end(Prohibit modification and deletion)
