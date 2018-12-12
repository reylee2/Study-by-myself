package com.methonLearning.algorithm;

/**
 * 给定字符串S[0…N-1]，设计算法，枚举S的全排列。
 *
 * @author ReyLee
 */
public class D2_2StringAllSequence {
    public static void main(String[] args) {
        String seq = "1232";
        char[] chars = seq.toCharArray();

//        showAllSeq(chars, 0, chars.length - 1);

        seq = "926520";
        chars = seq.toCharArray();
        showAllSeqNonRecursive(chars);
    }


    /**
     * 递归算法，时间复杂度为O（(n+1)!）
     * 空间换时间可以将时间复杂度转换为O(n!)
     * 如果是单字符，可以使用mark[256]
     * 如果是整数，可以遍历整数得到最大值max和最小值min，使用mark[max-min+1]
     * 如果是浮点数或者其他结构数据，用Hash(事实上，如果发现整数间变化太大，也应该考虑使用Hash；并且，可以认为整数情况是最朴素的Hash)
     * (思路使用mark标记该元素是否被使用过)
     *
     * @param chars
     * @param from
     * @param to
     */
    private static void showAllSeq(char[] chars, int from, int to) {
        if (from == to) {
            System.out.println(String.copyValueOf(chars));
            return;
        }

        for (int i = from; i <= to; i++) {
            if (!isSwap(chars, from, i)) {
                continue;
            }
            swap(chars, from, i);
            showAllSeq(chars, from + 1, to);
            swap(chars, from, i);
        }
    }

    // 检查是否存在重复
    private static boolean isSwap(char[] chars, int from, int to) {
        boolean flag = true;
        for (int j = from; j < to; j++) {
            if (chars[to] == chars[j]) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private static void swap(char[] chars, int i, int i1) {
        if (i == i1) {
            return;
        }
        char tmp = chars[i];
        chars[i] = chars[i1];
        chars[i1] = tmp;
    }


    /**
     * 非递归算法，利用字典序排列
     *  例如：
     *      起点：字典序最小的排列，例如12345
     *      终点：字典序最大的排列，例如54321
     *      过程：从当前排列生成字典序刚好比它大的下一个排列
     * 思路：从起点序列开始，交换位置元素使得序列为比源序列大的最小序列
     * 发现：交换发生先从序列尾部开始，发生交换以后，发生交换的最小位置后的所有元素为逆序排列，故需要将该位置之后的所有元素进行翻转
     */
    private static void showAllSeqNonRecursive(char[] chars) {
        // 归并排序 时间复杂度（O(nlogn)）
        mergeSort(chars, 0, chars.length - 1);
        System.out.println(String.copyValueOf(chars));

        // 交换使序列逐渐变大
        while (nextPermutation(chars)) {
            System.out.println(String.copyValueOf(chars));
        }
    }

    private static boolean nextPermutation(char[] chars) {
        if (chars.length == 0 || chars.length == 1) {
            return false;
        }
        // 第一次循環，寻找被替换元素位置
        for (int i = chars.length - 2; i >= 0; i--) {
            // 第二次循环，定位替换元素位置
            for (int j = chars.length - 1; j > i; j--) {
                if (chars[i] < chars[j]) {
                    swap(chars, i, j);
                    // 翻转被替换元素位置之后的子序列
                    reverse(chars, i+1, chars.length - 1);
                    return true;
                }
            }
        }
        return false;
    }

    private static void reverse(char[] chars, int from, int to) {
        for (int i = from, j = to; i < j; i++, j--) {
            swap(chars, i, j);
        }
    }

    private static void mergeSort(char[] chars, int from, int to) {
        int middle = (from + to) / 2;
        if (from < to) {
            mergeSort(chars, from, middle);
            mergeSort(chars, middle + 1, to);
            merge(chars, from, middle, to);
        }
    }

    private static void merge(char[] chars, int from, int middle, int to) {
        char[] tmp = new char[to - from + 1];
        int i = from;
        int j = middle + 1;
        int k = 0;
        while (i <= middle && j <= to) {
            if (chars[i] > chars[j]) {
                tmp[k++] = chars[j++];
            } else {
                tmp[k++] = chars[i++];
            }
        }
        while (i <= middle) {
            tmp[k++] = chars[i++];
        }
        while (j <= to) {
            tmp[k++] = chars[j++];
        }
        for (int l = from; l <= to; l++) {
            chars[l] = tmp[l - from];
        }
    }
}
