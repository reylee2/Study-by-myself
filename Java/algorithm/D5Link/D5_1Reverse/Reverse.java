package com.methonLearning.algorithm.D5Link;

/**
 * 给定一个链表，翻转该链表从m到n的位置。要求直接翻转而非申请新空间。
 * 如：给定1->2->3->4->5，m=2，n=4，返回1->4->3->2->5。假定给出的参数满足：1≤m≤n≤链表长度。
 *
 * 思路：
 * 从第一个翻转位置开始依次向后一位置内容翻转（注意：每次翻转过后的翻转部分链当做一个整体和后一位置内容进行翻转）
 */
public class D5_1Link {

    public static void main(String[] args) {
        UnidirectionalLink link = new UnidirectionalLink();
        link = link.initUnidirectionalLink(8);
        System.out.println(link);

        link=reverse(link, 2, 8);
        System.out.println(link);
    }

    /**
     * 翻转指定范围内的部分链内容
     * @param link
     * @param from
     * @param to
     * @return
     */
    private static UnidirectionalLink reverse(UnidirectionalLink link, int from, int to) {
        UnidirectionalLink link1 = new UnidirectionalLink();
        link1.setNext(link);

        UnidirectionalLink head = link1;

        for (int i = 0; i < from - 1; i++) {
            head=head.getNext();
        }

        UnidirectionalLink prev = head.getNext();
        UnidirectionalLink cur = prev.getNext();
        for (int i = 0; i < to - from; i++) {
            prev.setNext(cur.getNext());
            cur.setNext(head.getNext());
            head.setNext(cur);
            cur = prev.getNext();
        }
        return link1.getNext();
    }


}
