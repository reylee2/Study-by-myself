package com.methonLearning.algorithm;


import static java.lang.Math.max;

/**
 * LCS (Longest common subsequence 最长公共子序列) 即两个或者多个序列删除任意字符构成一个公共的最长的序列，两个字符可以不连续
 * 区别最长公共子字符串（最长公共子字符串要求子串必须连续）
 * <p>
 * 解法思路：
 *
 * 动态规划：假设有序列X和Y，最长公共子序列为C（）
 * 1. 若X的第i位和Y的第j位值相等，即X[i]=Y[j]，则最长公共子序列为 C（i-1,j-1）+1
 * 2. 若X的第i位和Y的第j位值不相等，则最长公共子序列为 max{C(i-1,j), C(i,j-1)}
 *
 * 最长递增子序列问题：由动态规划可以得知，其实只有X[i]=Y[j]的时候，才有意义。故可以通过这一特性对算法进行优化
 *
 *
 * @author jdli
 */

public class D1_4LCS {
    public static void main(String[] args) {
        String x = "ABCBDAB";
        String y = "BDCABA";

        String result = new D1_4LCS().getLCS(x, y);
        System.out.println(result);
    }


    /**
     * 动态规划：时间复杂度O(N*M) <其中两个字符串长度分别为N和M>
     * @param string1
     * @param string2
     * @return
     */
    public String getLCS(String string1, String string2) {
        char[] chars1 = string1.toCharArray();
        char[] chars2 = string2.toCharArray();

        int[][] lcsLength = new int[chars1.length + 1][chars2.length + 1];

        // get max
        for (int i = 0; i < chars1.length; i++) {
            for (int j = 0; j < chars2.length; j++) {
                if (chars1[i] == chars2[j]) {
                    lcsLength[i + 1][j + 1] = lcsLength[i][j] + 1;
                } else {
                    lcsLength[i + 1][j + 1] = max(lcsLength[i][j + 1] , lcsLength[i + 1][j]);
                }
            }
        }

        for (int i = 0; i <= chars1.length; i++) {
            for (int j = 0; j <= chars2.length; j++) {
                System.out.print(lcsLength[i][j]);
            }
            System.out.print('\n');
        }

        String result = "";
        return show(chars1,lcsLength, chars1.length , chars2.length, result);
    }

    private String show(char[] chars1, int[][] chars, int x, int y, String result) {
        if (x == 0 || y == 0) {
            return result;
        }
        if (chars[x][y] == chars[x-1][y-1]+1) {
            result = chars1[x-1] + result;
            return show(chars1, chars, x - 1, y - 1, result);
        } else if (chars[x][y] == chars[x][y-1]) {
            return show(chars1, chars, x, y - 1, result);
        } else {
            return show(chars1, chars, x - 1, y, result);
        }
    }
}
