package com.methonLearning.algorithm.D3Array;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.methonLearning.algorithm.D3Array.D3_1SumOfTwoMembers.mergeSort;

/**
 * 已知数组A[0…N-1]，给定某数值sum，找出数组中的若干个数，使得这些数的和为sum。
 * <p>
 * 布尔向量x[0…N-1]
 * x[i]=0表示不取A[i]，x[i]=1表示取A[i]
 * 假定数组中的元素都大于0：A[i]＞0
 * 这是个NP问题！
 * <p>
 * 思维模式：从特殊到一般，（从只考虑正数到包含负数）
 */
public class D3_3SumOfAnyMember {
    public static void main(String[] args) {
        int[] ints = {1, 4, 6, 8, 856, 6, 3, 10, 90, 26, 2, 18, 15, 0, 20, 12};
        int sum = 20;

        boolean[] x = new boolean[ints.length];

        System.out.println("暴力枚举法：");
        enumNumber(ints, sum, x, 0, 0);

        System.out.println("暴力法（优化）：");
        enumNumberOptimization(ints, sum, x);

        System.out.println("分支限定法：");
        branchNumber(ints, sum, x);


        ints = new int[]{1, 3, -2, 6, -3, 1, -1, -2, -5, 0, 8, 10};
        x = new boolean[ints.length];
        System.out.println("分支限定法(包含负数)：");
        branchAllNumber(ints, sum, x);
        System.out.println("暴力枚举法：");
        enumNumber(ints, sum, x, 0, 0);
    }


    /**
     * 暴力法：枚举   时间复杂度为O(2^n)
     *
     * @param array
     * @param sum
     * @param x
     * @param position
     * @param tmpSum
     * @return
     */
    private static void enumNumber(int[] array, int sum, boolean[] x, int position, int tmpSum) {
        if (position >= array.length) {
            return;
        }
        if (tmpSum + array[position] == sum) {
            x[position] = true;
            print(array, x);
            x[position] = false;
        }
        x[position] = true;
        enumNumber(array, sum, x, position + 1, tmpSum + array[position]);
        x[position] = false;
        enumNumber(array, sum, x, position + 1, tmpSum);
    }

    private static void print(int[] array, boolean[] x) {
        for (int i = 0; i < array.length; i++) {
            if (x[i]) {
                System.out.print(array[i] + ", ");
            }
        }
        System.out.println();
    }

    private static void print(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.println();
    }


    /**
     * 暴力枚举法的优化，
     * 思路：
     * 先排序，计算前i项和，若前i项和 + 第i+1项和大于sum，则后面部分全部舍弃
     *
     * @param ints
     * @param sum
     * @param x
     */
    private static void enumNumberOptimization(int[] ints, int sum, boolean[] x) {
        // 先排序
        int[] array = new int[ints.length];
        for (int i = 0; i < ints.length; i++) {
            array[i] = ints[i];
        }
        mergeSort(array, 0, ints.length - 1);
        print(array);

        // 枚举求解
        enumNumberOpt(array, sum, x, 0, 0);
    }

    private static void enumNumberOpt(int[] array, int sum, boolean[] x, int position, int tmpSum) {
        if (position >= array.length || tmpSum + array[position] > sum) {
            return;
        }
        if (tmpSum + array[position] == sum) {
            x[position] = true;
            print(array, x);
        }
        x[position] = true;
        enumNumberOpt(array, sum, x, position + 1, tmpSum + array[position]);
        x[position] = false;
        enumNumberOpt(array, sum, x, position + 1, tmpSum);
    }

    /**
     * 另外一种优化方案：分支限定法
     * 思路：
     * 1. 计算整个数组的和
     * 2. 判断x[i]是否为1,(假设i项前和为aboveSum，i项后和为restSum（包含i项），数组为array)
     * 若 aboveSum+array[i]<=sum && aboveSum+restSum>=sum 则 x[i] 可以为1；
     * 若 aboveSum+restSum-array[i]>=sum  , 则 x[i] 可以为0；
     * <p>
     * ！注意：此种方法仅适合于数组元素全部都为正数
     */
    private static void branchNumber(int[] array, int sum, boolean[] x) {
        // 求数组之和
        int rest = 0;
        for (int i = 0; i < array.length; i++) {
            rest += array[i];
        }

        // 判断x[i]
        branchShow(array, sum, x, 0, 0, rest);
    }

    private static void branchShow(int[] array, int sum, boolean[] x, int position, int aboveSum, int restSum) {
        if (position >= array.length) {
            return;
        }
        if (aboveSum + array[position] == sum) {
            x[position] = true;
            print(array, x);
        }
        if (aboveSum + array[position] <= sum && aboveSum + restSum >= sum) {
            x[position] = true;
            branchShow(array, sum, x, position + 1, aboveSum + array[position], restSum - array[position]);
        }
        x[position] = false;
        if (aboveSum + restSum - array[position] >= sum) {
            branchShow(array, sum, x, position + 1, aboveSum, restSum - array[position]);
        }

    }

    /**
     * 分支限定法：（数组中存在负数的情况）
     * branchAll 实现正负数存在于数组中的情况
     * 思路：
     * 1. 正负排序，使得负数都在左侧，正数都在右侧
     */
    private static void branchAllNumber(int[] array, int sum, boolean[] x) {
        // 正负排序
        int border = positiveNegativeSort(array);
        // 正数部分和负数部分分别的和
        int positiveSum = 0;
        for (int i = border; i < array.length; i++) {
            positiveSum += array[i];
        }

        // 判断x[i]
        // 去除负数部分中不满足条件的元素
        int[] tmp = omitNegative(array, sum, positiveSum);
        // 递归获取结果
        branchAll(tmp, sum, x, 0, 0, positiveSum);

    }

    private static void branchAll(int[] array, int sum, boolean[] x, int position, int aboveSum, int restSum) {
        if (position >= array.length) {
            return;
        }
        if (aboveSum + array[position] == sum) {
            x[position] = true;
            print(array, x);
        }
        if (array[position] >= 0) {
            if (aboveSum + array[position] <= sum && aboveSum + restSum >= sum) {
                x[position] = true;
                branchAll(array, sum, x, position + 1, aboveSum + array[position], restSum - array[position]);
            }
            x[position] = false;
            if (aboveSum + restSum - array[position] >= sum) {
                branchAll(array, sum, x, position + 1, aboveSum, restSum - array[position]);
            }
        } else {
            if (aboveSum + array[position] + restSum >= sum) {
                x[position] = true;
                branchAll(array, sum, x, position + 1, aboveSum + array[position], restSum);
            }
            x[position] = false;
            branchAll(array, sum, x, position + 1, aboveSum, restSum);
        }
    }

    private static int[] omitNegative(int[] array, int sum, int positiveSum) {
        List<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] + positiveSum >= sum) {
                tmp.add(array[i]);
            }
        }
        int[] result = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            result[i] = tmp.get(i);
        }
        return result;
    }

    private static int positiveNegativeSort(int[] array) {
        int start = 0, end = array.length - 1, tmp;
        while (start < end) {
            if (array[start] >= 0 && array[end] < 0) {
                tmp = array[start];
                array[start++] = array[end];
                array[end--] = tmp;
                continue;
            }
            if (array[start] < 0) {
                start++;
            }
            if (array[end] >= 0) {
                end--;
            }
        }
        return start;
    }
}
