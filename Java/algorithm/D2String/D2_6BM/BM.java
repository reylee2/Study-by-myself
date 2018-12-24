package com.methonLearning.algorithm;


/**
 * BM 算法 （字符串匹配算法）
 * 利用模式串的尾部开始依次向前比较
 *
 * @author jdli
 */
public class D2_6BM {
    public static void main(String[] args) {
        String text = "here is a simple example";
        String pattern = "example";

        System.out.println("BM算法：");
        int position = getPositionViaBM(text, pattern);
        System.out.println(text.substring(position, position + pattern.length()));

        System.out.println("BM算法（优化后）：");
        position = getPositionViaBMOptimization(text, pattern);
        System.out.println(text.substring(position, position + pattern.length()));
    }

    private static int getPositionViaBM(String text, String pattern) {
        char[] cText = text.toCharArray();
        char[] cPattern = pattern.toCharArray();

        int textPosition = cPattern.length - 1;
        int patternPosition = cPattern.length - 1;

        int compareCount = 0;
        while (textPosition < cText.length && patternPosition >= 0) {
            compareCount++;
            if (cText[textPosition] == cPattern[patternPosition]) {
                textPosition--;
                patternPosition--;
            } else {
                int rightPosition = getRightPosition(cText[textPosition], cPattern, patternPosition);
                textPosition += cPattern.length - 1 - rightPosition;
                patternPosition = cPattern.length - 1;
            }
        }
        System.out.println("比较次数：" + compareCount);
        if (patternPosition < 0) {
            return textPosition + 1;
        }
        return -1;
    }

    private static int getPositionViaBMOptimization(String text, String pattern) {
        char[] cText = text.toCharArray();
        char[] cPattern = pattern.toCharArray();
        int[] position = getPosition(cPattern);

        int textPosition = cPattern.length - 1;
        int patternPosition = cPattern.length - 1;

        int compareCount = 0;
        while (textPosition < cText.length && patternPosition >= 0) {
            compareCount++;
            if (cText[textPosition] == cPattern[patternPosition]) {
                textPosition--;
                patternPosition--;
            } else {
                int rightPosition = getRightPosition(cText[textPosition], cPattern, patternPosition);
                // 采用KMP算法，但是是利用后缀法
                if (rightPosition == -1 && patternPosition != cPattern.length - 1) {
                    int maxPosition = position.length - 1;
                    int maxLength = 0;
                    for (int i = position.length - 2; i >= 0; i--) {
                        if (maxLength < position[i]) {
                            maxLength = position[i];
                            maxPosition = i;
                        }
                    }
                    textPosition += cPattern.length - 1 - patternPosition + cPattern.length - maxLength;
                    patternPosition = cPattern.length - 1;
                    continue;
                }
                textPosition += cPattern.length - 1 - rightPosition;
                patternPosition = cPattern.length - 1;
            }
        }
        System.out.println("比较次数：" + compareCount);
        if (patternPosition < 0) {
            return textPosition + 1;
        }
        return -1;
    }

    private static int getRightPosition(char c, char[] cPattern, int patternPosition) {
        for (int i = patternPosition - 1; i >= 0; i--) {
            if (c == cPattern[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取与模式串开头相等的最长串位置信息
     *
     * @param chars
     * @return
     */
    private static int[] getPosition(char[] chars) {
        int[] position = new int[chars.length];

        int start = chars.length - 1;
        for (int i = chars.length - 2; i >= 0; i--) {
            if (chars[i] == chars[start]) {
                position[i] = position[i + 1] + 1;
                start++;
            } else {
                start = chars.length - 1;
                position[i] = chars[i] == chars[start] ? position[start] + 1 : position[i];
            }
        }
        return position;
    }
}
