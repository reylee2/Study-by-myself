package com.methonLearning.algorithm;

/**
 * KMP应用：求字符串的最小周期子串：
 * 给定一个长度为n的字符串S，如果存在一个字符串T，重复若干次T能够得到S，那么，S叫做周期串，T叫做S的一个周期。
 *  如：字符串abababab是周期串，abab、ab都是它的周期，其中，ab是它的最小周期。
 *
 *  设计一个线性算法，计算S的最小周期。如果S不存在周期，返回空串。
 *
 * @author ReyLee
 */
public class D2_4KMP_PowerString {
    public static void main(String[] args) {
        String text = "abababaa";
        String result = getPowerString(text);

        System.out.println(result);

    }

    private static String getPowerString(String text) {
        // 字符串个数为单数时，即不可能为周期串
        if (text.length() % 2 != 0) {
            return "";
        }

        // 获取位置信息
        // 思路最小子串后面的序列应该完全匹配前面的所有字符序列，故后面序列应该为一个递增序列且为前面最小子串序列个数的整数倍
        int[] postion = getPosition(text);

        int tmp = postion[postion.length - 1];
        // 根据位置信息判断是否为周期串
        for (int i = postion.length-2; i >= 0; i--) {
            if (--tmp != postion[i]) {
                if (postion[i + 1] == 1) {
                    return postion.length % (i + 1) == 0 ? text.substring(0, i+1) : "";
                } else {
                    return "";
                }
            }
        }
        return null;
    }

    private static int[] getPosition(String text) {
        int[] tmp = new int[text.length()];
        char[] chars = text.toCharArray();

        tmp[0] = 1;

        int x = 0, y = 1;
        while (y < text.length()) {
            if (chars[x] == chars[y]) {
                tmp[y] = x + 1;
                x++;
                y++;
            } else {
                if (x != 0) {
                    x = 0;
                } else {
                    tmp[y] = tmp[x] + 1;
                    y++;
                }
            }
        }

        return tmp;
    }

}
