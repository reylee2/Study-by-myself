package com.methonLearning.algorithm.D5Link;

/**
 * LCA (lowest common ancestor) 最近公共祖先：
 * 指在有根树中，找出某两个节点u和v最近的公共祖先，对于有根树T的两个节点u和v，最近公共祖先LCA（T,u,v）表示一个节点x，
 * 满足x是u和v的祖先且x的深度尽可能大。
 * 如： 对于T=<V，E>   其中V={1,2,3,4,5}，E={(1,2),(1,3),(3,4),(3,5)}
 * 则有： LCA（T,5,2）= 1       LCA(T,3,4)=3     LCA(T,4,5)=3
 * <p>
 * 本身属于树结构，但是可以转化为单链的公共节点问题(即：由u和v为头节点依次遍历到树的根节点的两条单链的问题)
 * <p>
 * ！注意：单向链表的特性：若两个单向链表相交了，则相交后两链完全重合
 */
public class D5_4LCA {
    public static void main(String[] args) {
        System.out.println("单纯单向链表：");
        UnidirectionalLink linkCommon = new UnidirectionalLink();
        linkCommon = linkCommon.initUnidirectionalLink(5);
        System.out.println("linkCommon:" + linkCommon);

        UnidirectionalLink linkTmp;

        UnidirectionalLink link1 = new UnidirectionalLink();
        link1 = link1.initUnidirectionalLink(2);
        linkTmp = link1;
        while (linkTmp.getNext() != null) {
            linkTmp = linkTmp.getNext();
        }
        linkTmp.setNext(linkCommon);
        System.out.println("link1:" + link1);

        UnidirectionalLink link2 = new UnidirectionalLink();
        link2 = link2.initUnidirectionalLink(5);
        linkTmp = link2;
        while (linkTmp.getNext() != null) {
            linkTmp = linkTmp.getNext();
        }
        linkTmp.setNext(linkCommon);
        System.out.println("link2:" + link2);

        UnidirectionalLink result = linkedLCA(link1, link2);
        System.out.println("common part:" + result);


        System.out.println("单向链表中存在环的情况：");
        linkTmp = link1;
        while (linkTmp.getNext() != null) {
            linkTmp = linkTmp.getNext();
        }
        UnidirectionalLink start = link1;
        for (int i = 0; i < 2; i++) {
            start = start.getNext();
        }
        linkTmp.setNext(start);


        // 1. 判断是否为环
        boolean link1Boolean = decideCycle(link1);
        boolean link2boolean = decideCycle(link2);
        if ((link1Boolean && !link2boolean) || (!link1Boolean && link2boolean)) {
            System.out.println("不相交~");
        }
        System.out.println(link1Boolean + ";" + link2boolean);

        // 2. 计算入环节点
        int positionLink1 = calculateStartOfCycleLink(link1);
        int positionLink2 = calculateStartOfCycleLink(link2);
        System.out.println(positionLink1 + "; " + positionLink2);

        // 3. 计算两环是否相交
        // 判断入环节点是否相等，若不等，则一环绕环一周分别于另一环入环节点比较，若没有相等，则不相交

    }


    /**
     * 计算入环节点，下图为单链计算入环节点的图。假设第一次相遇时slow走的路程为S，入环前长度为a，slow进入环的长度为x，
     * 环的剩余长度为y，环长为r，（x+y=r）；fast每次两步走，slow每次一步走，直到第一次相遇fast转了n次环
     *                   _______y______
     *                  |             |
     *  ————a———————x————
     *                  S
     * 由上图可以得出： s+nr=2s  =》  s=nr=a+x  =》  a=nr-x=(n-1)r+y
     * 由 a=（n-1）r+y 公式可得：将fast指针重头开始每次一步走，slow依然继续，到入环节点的时候二者重新相遇
     *
     * @param link
     * @return
     */
    private static int calculateStartOfCycleLink(UnidirectionalLink link) {
        UnidirectionalLink fast = link.getNext().getNext();
        UnidirectionalLink slow = link.getNext();

        int count = 0;
        while (fast != null) {
            if (fast == slow) {
                fast = link;
                while (fast != slow) {
                    count++;
                    fast = fast.getNext();
                    slow = slow.getNext();
                }
                return count;
            } else {
                fast = fast.getNext().getNext();
                slow = slow.getNext();
            }
        }
        return -1;
    }

    private static UnidirectionalLink linkedLCA(UnidirectionalLink link1, UnidirectionalLink link2) {
        UnidirectionalLink link1Tmp = link1;
        UnidirectionalLink link2Tmp = link2;
        int link1Count = 0;
        int link2Count = 0;
        while (link1Tmp.getNext() != null) {
            link1Tmp = link1Tmp.getNext();
            link1Count++;
        }
        while (link2Tmp.getNext() != null) {
            link2Tmp = link2Tmp.getNext();
            link2Count++;
        }

        link1Tmp = link1;
        link2Tmp = link2;
        while (link1Count != 0) {
            if (link1Tmp.getNext() == link2Tmp.getNext()) {
                break;
            }
            if (link1Count != link2Count) {
                if (link1Count > link2Count) {
                    link1Count--;
                    link1Tmp = link1Tmp.getNext();
                } else {
                    link2Count--;
                    link2Tmp = link2Tmp.getNext();
                }
            } else {
                link1Count--;
                link2Count--;
                link1Tmp = link1Tmp.getNext();
                link2Tmp = link2Tmp.getNext();
            }
        }
        return link1Tmp.getNext();
    }


    /**
     * 利用快慢指针来判断该链是否有环
     * 从开头开始扫描，分快指针和慢指针,当快指针追上慢指针时，即二者相等时，说明该单链有环
     *
     * @param link
     * @return
     */
    private static boolean decideCycle(UnidirectionalLink link) {
        UnidirectionalLink fast = link.getNext().getNext();
        UnidirectionalLink slow = link.getNext();

        while (fast != null) {
            if (fast == slow) {
                return true;
            } else {
                fast = fast.getNext().getNext();
                slow = slow.getNext();
            }
        }
        return false;
    }


}
