package com.methonLearning.algorithm;

/**
 * 给定一个字符串S[0…N-1]，要求把S的前k个字符移动到S的尾部，如把字符串“abcdef”前面的2个字符‘a’、‘b’移动到字符串的尾
 * 部，得到新字符串“cdefab”：即字符串循环左移k。
 *  多说一句：循环左移k位等价于循环右移n-k位。
 *  算法要求：
 *  时间复杂度为 O(n)，空间复杂度为 O(1)。
 *
 * @author ReyLee
 */
public class D2_1StringMovement {
    public static void main(String[] args) {
        String string = "abcdefg";
        int move = 9;
        char[] before = string.toCharArray();
        char tmp;
        move = move % before.length;

        for (int i = 0,j=move-1; i < j; i++,j--) {
            tmp = before[i];
            before[i] = before[j];
            before[j] = tmp;
        }
        for (int i = move,j=before.length-1; i < j; i++,j--) {
            tmp = before[i];
            before[i] = before[j];
            before[j] = tmp;
        }
        System.out.println(String.copyValueOf(before));

        for (int i = 0,j=before.length-1; i < j; i++,j--) {
            tmp = before[i];
            before[i] = before[j];
            before[j] = tmp;
        }


        System.out.println(String.copyValueOf(before));
    }
}
