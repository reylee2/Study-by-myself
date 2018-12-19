package com.methonLearning.algorithm;


import java.util.Arrays;

/**
 * 字符串查找问题:
 * 给定文本串text和模式串pattern，从文本串text中找出模式串pattern第一次出现的位置。
 * <p>
 * 1.暴力求解法BF（Brute Force）：
 * 依次遍历
 * <p>
 * 2.KMP算法求解
 * 减少比较次数，同时减少文本串text的回溯
 *
 * @author ReyLee
 */
public class D2_3KMP {
    public static void main(String[] args) {
        String text = "ttababcababdebca";
        String pattern = "abcabad";
        char[] cText = text.toCharArray();
        char[] cPattern = pattern.toCharArray();

        System.out.println("BF:");
        int position = bf(cText, cPattern);
        System.out.println(position);

        System.out.println("KMP:");
        position = kmp(text, pattern);
        System.out.println(position);
    }


    /**
     * KMP求解法，理论上时间复杂度可以达到O(M+N)
     *
     * @param text
     * @param pattern
     * @return
     */
    private static int kmp(String text, String pattern) {
        int i;
        int j;
        char[] cText = text.toCharArray();
        char[] cPattern = pattern.toCharArray();
        // 获取模式串中与前缀相同的位置信息    时间复杂度为O(N)
        int[] position = getPosition(pattern);

        int compareCount = 0;
        int maxPosition = 0;
        for (i = 0, j = 0; i < text.length() - pattern.length() + j; ) {
            compareCount++;
            if (cText[i] == cPattern[j]) {
                if (j >= cPattern.length - 1) {
                    System.out.println("比较次数：" + compareCount);
                    return i - j;
                }
                j++;
                i++;
            } else {
                if (j == 0) {
                    i++;
                } else {
                    for (int k = 0; k < j; k++) {
                        maxPosition = position[maxPosition] < position[k] ? k : maxPosition;
                    }
                    if (maxPosition == 0) {
                        j = 0;
                        continue;
                    }
                    i = i - (j - maxPosition);
                    j = position[maxPosition] - 1;
                }
            }
        }

        System.out.println("比较次数：" + compareCount);
        return -1;
    }

    private static int[] getPosition(String pattern) {
        int[] tmp = new int[pattern.length()];
        int count = 0;
        char[] charPattern = pattern.toCharArray();

        int x = 0, y = 1;
        while (x < charPattern.length && y < charPattern.length) {
            if (charPattern[x] == charPattern[y]) {
                tmp[y] = x + 1;
                x++;
                y++;
            } else {
                if (x != 0) {
                    x = 0;
                } else {
                    y++;
                }
            }
        }
        return tmp;
    }


    /**
     * 暴力求解法，时间复杂度为O(M*N) 空间复杂度为O(1)
     *
     * @param text
     * @param pattern
     * @return
     */
    private static int bf(char[] text, char[] pattern) {
        int i = 0;
        int j = 0;
        int compareCount = 0;

        while (i < text.length - pattern.length && j < pattern.length) {
            compareCount++;
            if (text[i + j] == pattern[j]) {
                j++;
            } else {
                i++;
                j = 0;
            }
        }

        System.out.println("比较次数：" + compareCount);

        if (j < pattern.length) {
            return -1;
        } else {
            return i;
        }
    }


}
