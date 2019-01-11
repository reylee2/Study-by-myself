package com.methonLearning.algorithm.D5Link;

/**
 * 给定一个链表和一个值x，将链表划分成两部分，使得划分后小于x的结点在前，大于等于x的结点在后。在这两部分中要保持
 * 原链表中的出现顺序。
 * <p>
 * 如：给定链表1->4->3->2->5->2和x = 3，返回1->2->2->4->3->5。
 */
public class D5_2LinkSeparate {
    public static void main(String[] args) {
        UnidirectionalLink link = new UnidirectionalLink();
        link=link.initUnidirectionalLink(10);
        int point = 5;
        System.out.println(link);

        link = separateLink(link, point);
        System.out.println(link);
    }

    private static UnidirectionalLink separateLink(UnidirectionalLink link, int point) {
        UnidirectionalLink linkCopy = new UnidirectionalLink();

        UnidirectionalLink right = new UnidirectionalLink();
        UnidirectionalLink rightHead = right;
        UnidirectionalLink leftHead = linkCopy;
        while (link != null) {
            if (link.getData() >= point) {
                rightHead.setNext(link);
                rightHead = rightHead.getNext();
            } else {
                leftHead.setNext(link);
                leftHead = leftHead.getNext();
            }
            link = link.getNext();
        }
        rightHead.setNext(null);
        leftHead.setNext(right.getNext());

        return linkCopy.getNext();
    }
}
