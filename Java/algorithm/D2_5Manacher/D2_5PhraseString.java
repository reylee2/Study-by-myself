package com.methonLearning.algorithm;


import static java.lang.Math.min;

/**
 * 回文串定义：
 * “回文串”是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。
 * <p>
 * 回文子串的定义：
 * 给定字符串str，若s同时满足以下条件：
 *  s是str的子串
 *  s是回文串
 * 则，s是str的回文子串。
 * <p>
 * 该算法的要求，是求str中最长的那个回文子串
 *
 * @author jdli
 */
public class D2_5PhraseString {
    public static void main(String[] args) {

        String text = "abclevevella";

        System.out.println("Enum center method:");
        System.out.println(getPhraseString(text));
        text = "12212";
        System.out.println(getPhraseString(text));
        text = "122122";
        System.out.println(getPhraseString(text));
        text = "waabwswfd";
        System.out.println(getPhraseString(text));


        System.out.println("Manacher method:");
        text = "abclevevella";
        System.out.println(getManacher(text));
        text = "12212";
        System.out.println(getManacher(text));
        text = "122122";
        System.out.println(getManacher(text));
        text = "waabwswfd";
        System.out.println(getManacher(text));


        System.out.println("Optimization enum center method:");
        text = "abclevevella";
        System.out.println(getManacherOptimization(text));
        text = "12212";
        System.out.println(getManacherOptimization(text));
        text = "122122";
        System.out.println(getManacherOptimization(text));
        text = "waabwswfd";
        System.out.println(getManacherOptimization(text));

    }


    /**
     * 枚举中心位置法，时间复杂度为O(n^2)
     * @param text
     * @return
     */
    private static String getPhraseString(String text) {
        char[] chars = text.toCharArray();
        int start = 0, end = 0, maxLength = 1;
        for (int i = 1; i < text.length() - 1; i++) {
            int endFor = i;
            int startFor = i - 1;
            if (chars[startFor] == chars[endFor] || chars[startFor] == chars[++endFor]) {
                while (startFor >= 0 && endFor<chars.length) {
                    if (chars[startFor] != chars[endFor]){
                        break;
                    } else{
                        startFor--;
                        endFor++;
                    }
                }
                if (maxLength < endFor - startFor - 1) {
                    maxLength = endFor - startFor - 1;
                    start = startFor + 1;
                    end = endFor - 1;
                }
            }
        }
        return text.substring(start, end + 1);
    }


    /**
     * 使用Manacher方法实现了线性的求解时间复杂度达到O(N)
     * 思想：
     *      回文串的特性为：以中心为轴左右对称，中心轴（包含中心轴）左侧使用枚举中心轴方法，求出以该位置为中心轴对应的最大
     *  回文串长度，中心轴右侧的元素应与对称的左侧最大回文串长度或者该位置到此回文串末尾的距离（取最小值），然后以此位置为
     *  中心轴依次向两侧延伸求回文串长度，这样大程度的减少了比较次数。
     *
     * 思路：
     *      由上述枚举法可以得出回文串长度存在单数（奇数）和双数（偶数）的区别，
     *  若在每个字符中间插入一位填充位，即（bob --> #b#o#b#   noon --> #n#o#o#n#）,使回文串都为奇数
     *  1. 添加填充位
     *  2. 计算以每一位为中心的最大回文串, 使用 next（i）方法依次计算最大回文串长度
     *
     * @param text
     * @return
     */
    private static String getManacher(String text) {
        char[] cText = text.toCharArray();
        char[] afterString = new char[2 * cText.length + 1];

        // 添加填充位
        for (int i = 0; i < cText.length;i++) {
            afterString[2 * i] = '#';
            afterString[2 * i + 1] = cText[i];
        }
        afterString[afterString.length - 1] = '#';

        // 计算最大回文串
        int[] length = new int[afterString.length];
        length[0] = 1;
        int maxLength = 0;
        int maxPosition = 0;
        int mx = 1;
        int center=1;
        for (int i = 1; i < length.length; i++) {
            length[i] = mx > i ? min(length[2 * center - i], mx - i) : 1;
            while (i - length[i]>=0 && i + length[i]<afterString.length && afterString[i - length[i]] == afterString[i + length[i]]) {
                length[i]++;
            }
            if (mx < length[i] + i) {
                mx = length[i] + i;
                center = i;
            }
            if (maxLength < length[i]) {
                maxLength = length[i];
                maxPosition = i;
            }
        }

        return text.substring((maxPosition-maxLength+1)/2,(maxPosition+maxLength)/2);
    }


    /**
     * manacher 方法可改进的地方：
     *     length[2 * center - i] > mx-i : length[i]=mx-i
     *     length[2 * center - i] < mx-i : length[i]=length[2 * center - i]
     *     length[2 * center - i] = mx-i : length[i]>=length[2 * center - i]
     *  前两行为即不需要再次比较进行暴力枚举
     *
     * @param text
     * @return
     */
    private static String getManacherOptimization(String text) {
        char[] cText = text.toCharArray();
        char[] afterString = new char[2 * cText.length + 1];

        // 添加填充位
        for (int i = 0; i < cText.length;i++) {
            afterString[2 * i] = '#';
            afterString[2 * i + 1] = cText[i];
        }
        afterString[afterString.length - 1] = '#';

        // 计算最大回文串
        int[] length = new int[afterString.length];
        length[0] = 1;
        int maxLength = 0;
        int maxPosition = 0;
        int mx = 1;
        int center=1;
        for (int i = 1; i < length.length; i++) {

            /* 改进的地方*/
            if (mx > i) {
                if (length[2 * center - i] != mx - i) {
                    length[i] = min(mx - i, length[2 * center - i]);
                } else {
                    length[i] = mx - i;
                    while (i - length[i] >= 0 && i + length[i] < afterString.length && afterString[i - length[i]] == afterString[i + length[i]]) {
                        length[i]++;
                    }
                }
            } else {
                while (i - length[i]>=0 && i + length[i]<afterString.length && afterString[i - length[i]] == afterString[i + length[i]]) {
                    length[i]++;
                }
            }

            if (mx < length[i] + i) {
                mx = length[i] + i;
                center = i;
            }
            if (maxLength < length[i]) {
                maxLength = length[i];
                maxPosition = i;
            }
        }

        return text.substring((maxPosition-maxLength+1)/2,(maxPosition+maxLength)/2);
    }
}
