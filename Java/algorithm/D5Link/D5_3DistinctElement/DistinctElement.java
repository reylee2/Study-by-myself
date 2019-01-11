package com.methonLearning.algorithm.D5Link;

/**
 * 给定排序的链表，删除重复元素，只保留重复元素第一次出现的结点
 *
 */
public class D5_3DistinctElement {
    public static void main(String[] args) {
        UnidirectionalLink link = new UnidirectionalLink();
        link=link.initUnidirectionalLink(5);
        System.out.println(link);

        link = distinctElement(link);
        System.out.println(link);
    }

    private static UnidirectionalLink distinctElement(UnidirectionalLink link) {
        UnidirectionalLink tmpLink = new UnidirectionalLink();
        tmpLink.setNext(link);
        UnidirectionalLink head = tmpLink;
        UnidirectionalLink prev = head.getNext();
        UnidirectionalLink cur = prev.getNext();

        while (cur != null) {
            if (prev.getData() != cur.getData()) {
                if (prev.getNext() != cur) {
                    head.setNext(cur);
                } else {
                    head=head.getNext();
                }
                prev = cur;
            }
            cur = cur.getNext();
        }

        if (prev.getNext() != null) {
            head.setNext(null);
        }

        return tmpLink.getNext();
    }

}
