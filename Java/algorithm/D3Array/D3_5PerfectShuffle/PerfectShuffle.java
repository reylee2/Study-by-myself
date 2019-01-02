package com.methonLearning.algorithm.D3Array;

import static java.lang.Math.pow;

/**
 * 完美洗牌算法：
 * 题目描述：
 * 有个长度为2n的数组 {a1, a2, a3, ..., an, b1, b2, b3, ..., bn} ，希望排序后 {a1, b1, a2, b2, ...., an, bn} ，
 * 要求时间复杂度 O(n)，空间复杂度 O(1) 的解法。
 */
public class D3_5PerfectShuffle {
    public static void main(String[] args) {
        String[] array = {"a1", "a2", "a3", "a4", "a5", "b1", "b2", "b3", "b4", "b5"};
        System.out.println("排序前：");
        print(array);

        method_1(array, array.length / 2);
        System.out.println("排序后：");
        print(array);

        array = new String[]{"a1", "a2", "a3", "a4", "a5", "b1", "b2", "b3", "b4", "b5"};
        perfectShuffle(array, 1, array.length - 2);
        System.out.println("排序后：");
        print(array);
    }


    /**
     * 中间交换法：       时间复杂度为O(N^2)
     * 类似于冒泡依次交换
     * 1. 交换中间的两个元素
     * "a1", "a2", "a3", "a4", "b1", "b2", "b3", "b4"
     * "a1", "a2", "a3", "b1", "a4", "b2", "b3", "b4"
     * 2. 交换中间两对元素
     * "a1", "a2", "a3", "b1", "a4", "b2", "b3", "b4"
     * "a1", "a2", "b1", "a3", "b2", "a4", "b3", "b4"
     * 3. 交换中间三对元素
     * "a1", "a2", "b1", "a3", "b2", "a4", "b3", "b4"
     * "a1", "b1", "a2", "b2", "a3", "b3", "a4", "b4"
     *
     * @param array
     * @param count
     */
    private static void method_1(String[] array, int count) {
        int start = count - 1, end = count;
        int number = 0;
        while (start > 0) {
            for (int i = 0; i < (end - start + 1) / 2; i++) {
                number++;
                swap(array, start + i * 2, start + i * 2 + 1);
            }
            start--;
            end++;
        }
        System.out.println("循环次数：" + number);
    }


    /**
     * 完美洗牌算法：
     * 由题可得：数组由  "a1", "a2", "a3", "a4", "b1", "b2", "b3", "b4"
     * 转化为： "a1", "b1", "a2", "b2", "a3", "b3", "a4", "b4"
     * 位置为：  0,    1,    2,    3,    4,    5,    6,    7
     * 可以得出首位元素是不参与排序的，故发生位置变换的只有中间部分的元素。
     * 假定位置为i，则只考虑从1到6之间的位置变换。假定位置变换后的新位置为I
     * 若i>0 && i<n : I=i*2   ==> I=(2*i)%(2(n-1)+1)
     * 若i>=n && i<2*n-1 : I=2(i-(n-1))-1  ==> I=2*i-2(n-1)-1 ==> I=2*i-(2(n-1)+1)  ==> I=(2*i)%(2(n-1)+1)
     * <p>
     * 转化关系为：
     * 1 -> 2
     * 2 -> 4
     * 3 -> 6
     * 4 -> 1
     * 5 -> 3
     * 6 -> 5
     * 形成两个环： 1 -> 2 -> 4 -> 1； 3 -> 6 -> 5 -> 3
     * <p>
     * 有一个重要的结论：如果满足等式 2*count=3^k-1 ; 则该数组由k个环，环首分别为 k^0, k^1, k^2 ...
     *                  若不满足该等式，则查找一个小于count的最大的一个M满足该等式, 左移或者右移数组元素，使得相关元素在一定区域内
     *                      如：1  2  3  11  12  13 
     *                      -》 1  2  11 12  3   13
     *
     * @param array
     * @param from
     * @param to
     */
    private static void perfectShuffle(String[] array, int from, int to) {
        int count = (to - from + 1) / 2;
        int number = calculateCycles(count);
        if (count > 1) {
            if ((pow(3, number) - 1) == (2 * count)) {
                for (int i = 0; i < number; i++) {
                    swapCycle(array, from, (int) pow(3, i), 2 * count + 1);
                }
            } else {
                // 左移
                move(array, from + (int) pow(3, number - 1) / 2, from + (int) pow(3, number - 1) / 2 + count - 1,
                        count - ((int) pow(3, number - 1) - 1) / 2);

                for (int i = 0; i < number - 1; i++) {
                    swapCycle(array, from, (int) pow(3, i), (int) pow(3, number - 1));
                }
                perfectShuffle(array, from + (int) pow(3, number - 1) - 1, to);
            }
        } else if (count == 1) {
            swap(array, from, to);
        }
    }

    private static void move(String[] array, int from, int to, int bits) {
        reverse(array, from, from + bits - 1);
        reverse(array, from + bits, to);
        reverse(array, from, to);
    }

    private static void reverse(String[] array, int from, int to) {
        for (int i = from, j = to; i < j; i++, j--) {
            swap(array, i, j);
        }
    }

    private static int calculateCycles(int count) {
        int number = 1;
        for (; pow(3, number) < (2 * count); number++) {
        }
        return number;
    }

    /**
     * 对环中的元素进行交换位置
     *
     * @param array
     * @param from
     * @param mod
     */
    private static void swapCycle(String[] array, int from, int relPosition, int mod) {
        for (int i = (2 * relPosition) % mod; i != relPosition; i = (2 * i) % mod) {
            swap(array, from + relPosition - 1, from + i - 1);
        }
    }


    private static void swap(String[] array, int start, int end) {
        String tmp = array[start];
        array[start] = array[end];
        array[end] = tmp;
    }

    private static void print(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.println();
    }
}

