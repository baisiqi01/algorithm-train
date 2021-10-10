package leetcode.editor.cn9;


import java.util.Arrays;

public class SortExercise {
    public static void main(String[] args) {
        int[] array = {1, 9, 2, 4,8,7};
        SolutionSort sort = new SolutionSort();
//        System.out.println(Arrays.toString(sort.sortbubbleSort(array)));
//        System.out.println(Arrays.toString(sort.selectionSort(array)));
//        System.out.println(Arrays.toString(sort.insertSort(array)));
//        System.out.println(Arrays.toString(sort.merge_sort(array)));
        sort.quickSort(array,0, array.length -1);
        System.out.println(Arrays.toString(array));
    }
}

/**
 * @Author: baisiqi01
 * @DateTime: 10/5/21 10:01 PM
 * @Description: 基础排序
 */


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
