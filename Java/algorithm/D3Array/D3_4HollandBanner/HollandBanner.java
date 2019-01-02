package com.methonLearning.algorithm.D3Array;

/**
 * 荷兰旗问题：
 *      现有红、白、蓝三个不同颜色的小球，乱序排列在一起，请重新排列这些小球，使得红白蓝三色的同颜色的球在一起。这个问题之
 *  所以叫荷兰国旗，是因为我们可以将红白蓝三色小球想象成条状物，有序排列后正好组成荷兰国旗。
 *
 * 问题转换：
 *      将输入的乱序数组A[N]，包含0,1,2，重新排序，形成00..022..211..1的形式
 *
 */
public class D3_4HollandBanner {
    public static void main(String[] args) {
        int[] array = {0,2,1,1,2,2,0,0,2,1,0,1};
        System.out.println("排序前：");
        print(array);

        sort_1(array);
        System.out.println("排序后：");
        print(array);

        array = new int[]{0,2,1,1,2,2,0,0,2,1,0,1};
        sort_2(array);

        System.out.println("排序后：");
        print(array);
    }

    private static void sort_1(int[] array) {
        int start = 0;
        int end = array.length - 1;
        int current = 0;

        int count = 0;
        while (current <= end) {
            count++;
            switch (array[current]) {
                case 0:
                    if (current == start ) {
                        start++;
                        current++;
                    } else {
                        swap(array, current, start);
                        start++;
                    }
                    break;
                case 2:
                    current++;
                    break;
                case 1:
                    swap(array, current, end);
                    end--;
                    break;
            }
        }
        System.out.println("计数："+count);
    }

    /**
     * 对sort_1做一个简单的优化
     *  想法： 1. current位置之前的元素不存在2，因此A[begin]可能为0，可能为1
     *         2. 若begin!=current时，则A[begin]==1
     *              故，当A[current]==0,且current！=start时，直接交换，然后current++
     *
     * @param array
     */
    private static void sort_2(int[] array) {
        int start = 0;
        int end = array.length - 1;
        int current = 0;

        int count = 0;
        while (current <= end) {
            count++;
            switch (array[current]) {
                case 0:
                    /*if (current == start ) {
                        start++;
                        current++;
                    } else {
                        swap(array, current, start);
                        start++;
                        // 优化添加的代码
                        current++;
                    }*/
                    if (current != start) {
                        swap(array, current, start);
                    }
                    start++;
                    current++;
                    break;
                case 2:
                    current++;
                    break;
                case 1:
                    swap(array, current, end);
                    end--;
                    break;
            }
        }
        System.out.println("计数："+count);
    }

    private static void swap(int[] array, int first, int last) {
        int tmp;
        tmp = array[first];
        array[first] = array[last];
        array[last] = tmp;
    }


    private static void print(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+", ");
        }
        System.out.println();
    }

}
