package com.methonLearning.algorithm.D3Array;

/**
 * 输入一个数组A[0…N-1]和一个数字Sum，在数组中查找两个数Ai,Aj，使得Ai+Aj=Sum
 *
 * @author reylee
 */
public class D3_1SumOfTwoMembers {
    public static void main(String[] args) {
        int[] ints = {1, 5, 6, 8, 2, 6, 3, 10, 90, 26, 856, 18, 15, 20, 12};
        int sum = 20;

        int[] result = new int[2];
        result = bf(ints, sum);
        if (result != null) {
            System.out.println(result[0] + ":" + ints[result[0]] + " , " + result[1] + ":" + ints[result[1]]);
        }

        result = opt(ints, sum);
        if (result != null) {
            System.out.println(result[0] + ":" + ints[result[0]] + " , " + result[1] + ":" + ints[result[1]]);
        }

        result = hash(ints, sum);
        if (result != null) {
            System.out.println(result[0] + ":" + ints[result[0]] + " , " + result[1] + ":" + ints[result[1]]);
        }
    }


    /**
     * 暴力法：时间复杂度为O(n^2)
     *
     * @param ints
     * @param sum
     * @return
     */
    private static int[] bf(int[] ints, int sum) {
        for (int i = 0; i < ints.length - 1; i++) {
            for (int j = i + 1; j < ints.length; j++) {
                if (ints[i] + ints[j] == sum) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }


    /**
     * 优化算法，时间复杂度O(NlogN)
     * @param ints
     * @param sum
     * @return
     */
    private static int[] opt(int[] ints, int sum) {
        // 首先对原序列进行排序
        mergeSort(ints, 0, ints.length - 1);

        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i] + ",");
        }
        System.out.println();

        int start = 0;
        int end = ints.length - 1;
        while (start < end) {
            if (ints[start] + ints[end] == sum) {
                return new int[]{start, end};
            } else if (ints[start] + ints[end] > sum) {
                end--;
            } else {
                start++;
            }
        }

        return null;
    }

    public static void mergeSort(int[] ints, int start, int end) {
        if (start == end) {
            return;
        } else {
            int mid = (start + end) / 2;
            mergeSort(ints, start, mid);
            mergeSort(ints, mid + 1, end);
            merge(ints, start, mid, end);
        }
    }

    private static void merge(int[] ints, int start, int mid, int end) {
        int i = start;
        int j = mid + 1;
        int[] tmp = new int[end - start + 1];
        int k = 0;
        while (i <= mid && j <= end) {
            tmp[k++] = ints[i] < ints[j] ? ints[i++] : ints[j++];
        }
        while (i <= mid) {
            tmp[k++] = ints[i++];
        }
        while (j <= end) {
            tmp[k++] = ints[j++];
        }
        for (int l = end; l >= start; l--) {
            ints[l] = tmp[--k];
        }
    }


    /**
     * hash 算法：理论时间复杂度为O(N) ，空间复杂度为开辟的hash结构的大小
     *  1. 开辟空间存放数组元素，简历hash结构
     *  2. 遍历数组a[i],计算 hash（sum-a[i]）是否存在
     * 以空间换时间(注意需要解决hash冲突)
     *
     * @param ints
     * @param sum
     * @return
     */
    private static int[] hash(int[] ints, int sum) {
        int[] hashSpace=new int[65536];
        for (int i = 0; i < ints.length; i++) {
            hashSpace[sdbm(ints[i])] = ints[i];
        }
        return null;
    }

    private static int sdbm(int anInt) {
        int hash=0;
        hash = (hash << 6) + (hash << 16) - hash + anInt;
        return hash;
    }

}
