package com.methonLearning.algorithm.D3Array;

/**
 * 简单介绍几种hash算法
 *      ！注：需要解决hash冲突
 *
 * @author jdli
 */
public class D3_2Hash {
    public static void main(String[] args) {
        System.out.println(djb2("4"));
        System.out.println(sdbm("4"));
    }

    /**
     * djb2算法：
     * 这个算法(k=33)是多年前首先由dan bernstein 在comp.lang.c中提出的，这个算法的另外一个版本（bernstein的贡献）是使用
     * 异或方式：hash(i) = hash(i - 1) * 33 ^ str[i];魔数33尚未得到足够的解释（为何它能够比其他很多素数的效果更好）
     *
     * @param string
     * @return
     */
    public static long djb2(String string) {
        long hash = 5381;       // 5381为魔数
        char[] tmp = string.toCharArray();
        int c;
        for (int i = 0; i < tmp.length; i++) {
            c = tmp[i];
            hash = ((hash << 5) + hash) + c;        // hash * 33 + c
        }

        return hash;
    }

    /**
     * sdbm算法
     * djb2的变形， 将 乘魔数33 变为 --》 乘一个素数65599
     *
     * This algorithm was created for sdbm (a public-domain reimplementation of ndbm) database library. it was
     * found to do well in scrambling bits, causing better distribution of the keys and fewer splits.
     *
     * It also happens to be a good general hashing function with good distribution. the actual function is
     * hash(i) = hash(i - 1) * 65599 + str[i]; which is the faster version used in gawk. The magic constant
     * 65599 was picked out of thin air while experimenting with different constants, and turns out to be a
     * prime. this is one of the algorithms used in berkeley db and elsewhere.
     *
     * @param string
     * @return
     */
    public static long sdbm(String string) {
        long hash = 0;
        char[] tmp = string.toCharArray();
        int c;
        for (int i = 0; i < tmp.length; i++) {
            c = tmp[i];
            hash = ((hash << 6) + (hash << 16)-hash) + c;        // hash * 33 + c
        }

        return hash;
    }
}
