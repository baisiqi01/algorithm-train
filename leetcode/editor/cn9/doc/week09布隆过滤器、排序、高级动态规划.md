# week09布隆过滤器、排序、高级动态规划

## 1、布隆过滤器、LRU Cached

### 1.1定义

```
一个很长的二进制向量和一系列随机映射函数。布隆过滤器可以用于检索
一个元素是否在一个集合中。
原理与实现博客：https://www.cnblogs.com/cpselvis/p/6265825.html
```

### 1.2优缺点

```
优点是空间效率和查询时间都远远超过一般的算法，
缺点是有一定的误识别率和删除困难。
```

### 1.3实现

https://github.com/lovasoa/bloomfilter/blob/master/src/main/java/BloomFilter.java

```java
package com.github.lovasoa.bloomfilter;

import java.util.BitSet;
import java.util.Random;
import java.util.Iterator;

public class BloomFilter implements Cloneable {
  private BitSet hashes;
  private RandomInRange prng;
  private int k; // Number of hash functions
  private static final double LN2 = 0.6931471805599453; // ln(2)

  /**
   * Create a new bloom filter.
   * @param n Expected number of elements
   * @param m Desired size of the container in bits
   **/
  public BloomFilter(int n, int m) {
    k = (int) Math.round(LN2 * m / n);
    if (k <= 0) k = 1;
    this.hashes = new BitSet(m);
    this.prng = new RandomInRange(m, k);
  }

  /**
   * Create a bloom filter of 1Mib.
   * @param n Expected number of elements
   **/
  public BloomFilter(int n) {
    this(n, 1024*1024*8);
  }

  /**
  * Add an element to the container
  **/
  public void add(Object o) {
    prng.init(o);
    for (RandomInRange r : prng) hashes.set(r.value);
  }
  /** 
  * If the element is in the container, returns true.
  * If the element is not in the container, returns true with a probability ≈ e^(-ln(2)² * m/n), otherwise false.
  * So, when m is large enough, the return value can be interpreted as:
  *    - true  : the element is probably in the container
  *    - false : the element is definitely not in the container
  **/
  public boolean contains(Object o) {
    prng.init(o);
    for (RandomInRange r : prng)
      if (!hashes.get(r.value))
        return false;
    return true;
  }

  /**
   * Removes all of the elements from this filter.
   **/
  public void clear() {
    hashes.clear();
  }

  /**
   * Create a copy of the current filter
   **/
  public BloomFilter clone() throws CloneNotSupportedException {
    return (BloomFilter) super.clone();
  }

  /**
   * Generate a unique hash representing the filter
   **/
  public int hashCode() {
    return hashes.hashCode() ^ k;
  }

  /**
   * Test if the filters have equal bitsets.
   * WARNING: two filters may contain the same elements, but not be equal
   * (if the filters have different size for example).
   */
  public boolean equals(BloomFilter other) {
    return this.hashes.equals(other.hashes) && this.k == other.k;
  }

  /**
   * Merge another bloom filter into the current one.
   * After this operation, the current bloom filter contains all elements in
   * other.
   **/
  public void merge(BloomFilter other) {
    if (other.k != this.k || other.hashes.size() != this.hashes.size()) {
      throw new IllegalArgumentException("Incompatible bloom filters");
    }
    this.hashes.or(other.hashes);
  }

  private class RandomInRange
      implements Iterable<RandomInRange>, Iterator<RandomInRange> {

    private Random prng;
    private int max; // Maximum value returned + 1
    private int count; // Number of random elements to generate
    private int i = 0; // Number of elements generated
    public int value; // The current value

    RandomInRange(int maximum, int k) {
      max = maximum;
      count = k;
      prng = new Random();
    }
    public void init(Object o) {
      prng.setSeed(o.hashCode());
    }
    public Iterator<RandomInRange> iterator() {
      i = 0;
      return this;
    }
    public RandomInRange next() {
      i++;
      value = prng.nextInt() % max;
      if (value<0) value = -value;
      return this;
    }
    public boolean hasNext() {
      return i < count;
    }
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}

```

### 1.4课后作业

[ LRU 缓存机制](https://leetcode-cn.com/problems/lru-cache/#/)

需要用到一个哈希表和一个双向链表，未实现，而是直接使用LinkedHashMap

```java
class LRUCache extends LinkedHashMap<Integer, Integer> {

    private int capacity;
    public LRUCache(int capacity) {
        super(capacity, 0.75f,true);
        this.capacity = capacity;

    }
    
    public int get(int key) {
        return super.getOrDefault(key,-1);
    }
    
    public void put(int key, int value) {
        super.put(key,value);
    }
}
```





## 2、排序

### 2.1 排序算法分类

1）比较类排序

通过决定元素间的相对次序，由于期时间复杂度不能突破O(logn),因此也成为非线性时间比较类排序

交换排序：冒泡 ==**快排**==

插入排序：简单插入排序 希尔排序

选择排序：简单选择排序  ==**堆排序**==

**==归并排序==**： 二路归并排序 多路归并排序

2）非比较类排序

不通过比较来决定元素见的相对次序他可以突破基于比较排序时间下界，以现行时间运行，因此也称为现行时间费比较类排序

计数排序 堆排序 基数排序

### 2.2参考链接

- [十大经典排序算法](https://www.cnblogs.com/onepixel/p/7674659.html)
- [快速排序代码示例](https://shimo.im/docs/TX9bDbSC7C0CR5XO)
- [归并排序代码示例](https://shimo.im/docs/sDXxjjiKf3gLVVAU)
- [堆排序代码示例](https://shimo.im/docs/M2xfacKvwzAykhz6)

- [ 9 种经典排序算法可视化动画](https://www.bilibili.com/video/av25136272)
- [ 6 分钟看完 15 种排序算法动画展示](https://www.bilibili.com/video/av63851336)

### 2.3初级排序 - O(n^2)

1. 选择排序(Selection Sort) 每次找最小值，然后放到待排序数组的起始位置。

2. 插入排序(Insertion Sort) 从前到后逐步构建有序序列;对于未排序数据，在已排序序列中从后 向前扫描，找到相应位置并插入。

3. 冒泡排序(Bubble Sort) 嵌套循环，每次查看相邻的元素如果逆序，则交换



### 2.4高级排序 - O(N*LogN)

#### • 2.4.1快速排序(Quick Sort)

数组取标杆 pivot，将小元素放 pivot左边，大元素放右侧，然后依 次对右边和右边的子数组继续快排;以达到整个序列有序		 		

```
 快排代码 - Java
public static void quickSort(int[] array, int begin, int end) { if (end <= begin) return;
int pivot = partition(array, begin, end);
quickSort(array, begin, pivot - 1);
quickSort(array, pivot + 1, end);
}
static int partition(int[] a, int begin, int end) { // pivot: 标杆位置，counter: 小于pivot的元素的个数 int pivot = end, counter = begin;
for (int i = begin; i < end; i++) {
if (a[i] < a[pivot]) {
int temp = a[counter]; a[counter] = a[i]; a[i] = temp; counter++;
} }
int temp = a[pivot]; a[pivot] = a[counter]; a[counter] = temp;
    return counter;
}
调用方式: quickSort(a, 0, a.length - 1)
```

#### 2.4.2 归并排序(Merge Sort)— 分治

1. 把长度为n的输入序列分成两个长度为n/2的子序列; 
2. 对这两个子序列分别采用归并排序;
3. 将两个排序好的子序列合并成一个最终的排序序列。

```
 归并排序代码 - Java
public static void mergeSort(int[] array, int left, int right) { if (right <= left) return;
    int mid = (left + right) >> 1; // (left + right) / 2
    mergeSort(array, left, mid);
    mergeSort(array, mid + 1, right);
    merge(array, left, mid, right);
}

public static void merge(int[] arr, int left, int mid, int right) { int[] temp = new int[right - left + 1]; // 中间数组
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        }
        while (i <= mid)   temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];
for (int p = 0; p < temp.length; p++) {
            arr[left + p] = temp[p];
}
// 也可以用 System.arraycopy(a, start1, b, start2, length) }
```

2.4.3高级排序 - O(N*LogN)

• 堆排序(Heap Sort) — 堆插入 O(logN)，取最大/小值 O(1)

1. 数组元素依次建立小顶堆 

2. 依次取堆顶元素，并删除

   不重要，直接用PQ

```
static void heapify(int[] array, int length, int i) { int left = 2 * i + 1, right = 2 * i + 2;
int largest = i;
    if (left < length && array[left] > array[largest]) {
        largest = leftChild;
    }
    if (right < length && array[right] > array[largest]) {
largest = right; }
    if (largest != i) {
        int temp = array[i]; array[i] = array[largest]; array[largest] = temp;
        heapify(array, length, largest);
} }
public static void heapSort(int[] array) {
    if (array.length == 0) return;
int length = array.length;
for (int i = length / 2-1; i >= 0; i-)
        heapify(array, length, i);
    for (int i = length - 1; i >= 0; i--) {
        int temp = array[0]; array[0] = array[i]; array[i] = temp;
        heapify(array, i, 0);
} }
```

### 2.4 课后作业

排序代码

```java
class SolutionSort {
    //bubbleSort 冒泡排序O(n2)
    public int[] bubbleSort(int[] array) {
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        return array;
    }

    //选择排序O(n2)
    public int[] selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[minIndex]) minIndex = j;
            }
            if (minIndex != i) {
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
            }
        }
        return array;
    }

    //插入排序O(n2)
    public int[] insertSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int current = array[i + 1];
            int index = i;
            while (index >= 0 && current < array[index]) {
                array[index + 1] = array[index];
                index--;
            }
            array[index + 1] = current;
        }
        return array;
    }


    public int[] merge_sort(int[] array) {
        mergeSort(array, 0, array.length - 1);
        return array;
    }

    //归并排序O(nlogn)
    public void mergeSort(int[] array, int left, int right) {
        if (right <= left) return;
        int mid = (left + right) >> 1;
        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);
        merge(array, left, mid, right);
    }

    private void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            temp[k++] = array[i] <= array[j] ? array[i++] : array[j++];
        }
        while (i <= mid) temp[k++] = array[i++];
        while (j <= right) temp[k++] = array[j++];
        for (int n = 0; n < temp.length; n++) {
            array[left + n] = temp[n];
        }
    }

    //快速排序O(nlogn)
    public void quickSort(int[] array, int left, int right) {
        if (right <=  left) return;
        int pivot = partition(array, left, right);
        quickSort(array, left, pivot - 1);
        quickSort(array, pivot + 1, right);

    }

    private int partition(int[] array, int left, int right) {
        //pivot标杆位置，counter小于标杆位置个数
        int pivot = right, counter = left;
        for (int i = left; i < right; i++) {
            if(array[i] < array[pivot]) {
                int temp = array[counter]; array[counter] = array[i]; array[i] = temp;
                counter++;
            }
        }
        int temp = array[pivot]; array[pivot] = array[counter]; array[counter] = temp;
        return  counter;
    }
}
```



- [数组的相对排序](https://leetcode-cn.com/problems/relative-sort-array/)（谷歌在半年内面试中考过）

- [有效的字母异位词](https://leetcode-cn.com/problems/valid-anagram/)（Facebook、亚马逊、谷歌在半年内面试中考过）

  ```java
  class Solution {
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

  

- [合并区间](https://leetcode-cn.com/problems/merge-intervals/)

  ```java
  class SolutionP56 {
      public int[][] merge(int[][] intervals) {
          if(intervals.length <= 1) return intervals;
          Arrays.sort(intervals,(i1,i2) -> Integer.compare(i1[0], i2[0]));
          List<int []> result = new ArrayList<>();
          int[] newIntervals = intervals[0];
          result.add(newIntervals);
          for (int [] interval : intervals) {
              if(interval[0] < newIntervals[1]) {
                  newIntervals[1] = Math.max(newIntervals[1], interval[1]);
              }else {
                  newIntervals =interval;
                  result.add(newIntervals);
              }
  
          }
          return result.toArray(new int[result.size()][2]);
  
      }
  }class SolutionP56 {
      public int[][] merge(int[][] intervals) {
          if(intervals.length <= 1) return intervals;
          Arrays.sort(intervals,(i1,i2) -> Integer.compare(i1[0], i2[0]));
          List<int []> result = new ArrayList<>();
          int[] newIntervals = intervals[0];
          result.add(newIntervals);
          for (int [] interval : intervals) {
              if(interval[0] < newIntervals[1]) {
                  newIntervals[1] = Math.max(newIntervals[1], interval[1]);
              }else {
                  newIntervals =interval;
                  result.add(newIntervals);
              }
  
          }
          return result.toArray(new int[result.size()][2]);
  
      }
  }
  ```

  

- [翻转对](https://leetcode-cn.com/problems/reverse-pairs/)

  遗留第9周

  

  ## 3、高级动态规划

### 3.1练习

- [使用最小花费爬楼梯](https://leetcode-cn.com/problems/min-cost-climbing-stairs/)

  时间O(n),可优化未滚动减少空间复杂度

  ```java
  
  class SolutionP746 {
      public int minCostClimbingStairs(int[] cost) {
          int n = cost.length;
          int[] dp = new int[n + 1];
          dp[0] = dp[1] = 0;
          for (int i = 2; i<= n ; i++) {
              dp[i] = Math.min(dp[i - 1] + cost[i -1], dp[i - 2] + cost[i - 2]);
          }
          return dp[n];
      }
  }
  ```

  

- [编辑距离](https://leetcode-cn.com/problems/edit-distance/)

  ```java
  class Solution {
      public int minDistance(String word1, String word2) {
          int m = word1.length(), n = word2.length();
          int[][] dp = new int[m + 1][n + 1];
          //边界
          for (int i = 1; i <= m; i++) dp[i][0] = dp[i - 1][0] + 1;
          for (int j = 1; j <= n; j++) dp[0][j] = dp[0][j - 1] + 1;
          for (int i = 1; i <= m; i++)
              for (int j = 1; j <= n; j++) {
                  if (word1.charAt(i - 1) == word2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                  else dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
              }
          return dp[m][n];
      }
  }
  ```

  - [最长上升子序列](https://leetcode-cn.com/problems/longest-increasing-subsequence/)

    ```java
    class SolutionP91 {
        public int numDecodings(String s) {
            int length = s.length();
            int[] dp = new int[length + 1];
            dp[0] = 1;
            for (int i = 1; i <= length; i++) {
                if (s.charAt(i - 1) != '0') dp[i] += dp[i - 1];
                if (i > 1 && s.charAt(i -  2) != '0' && (s.charAt(i - 2) - '0')* 10 + (s.charAt(i - 1) - '0') <= 26) {
                    dp[i] += dp[i -2];
                }
            }
            return dp[length];
        }
    }
    ```

    

  - 
