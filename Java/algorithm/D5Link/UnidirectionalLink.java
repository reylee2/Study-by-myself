package com.methonLearning.algorithm.D5Link;

import java.util.Random;

/**
 * @author ReyLee
 */
public class UnidirectionalLink {
    private int data;
    private UnidirectionalLink next;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public UnidirectionalLink getNext() {
        return next;
    }

    public void setNext(UnidirectionalLink next) {
        this.next = next;
    }

    public UnidirectionalLink() {
    }

    public UnidirectionalLink(int data) {
        this.data = data;
        this.next = null;
    }
    public UnidirectionalLink initUnidirectionalLink(int count) {
        Random random = new Random();
        UnidirectionalLink link = new UnidirectionalLink(-1);
        UnidirectionalLink next = link;
        for (int i = 0; i < count; i++) {
            next.setNext(new UnidirectionalLink(random.nextInt(count)));
            next = next.getNext();
        }
        return link.getNext();
    }

}
