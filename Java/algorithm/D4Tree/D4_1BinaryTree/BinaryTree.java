package com.methonLearning.algorithm.D4Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 根据前序遍历和中序遍历求得后序遍历
 * 思路：
 * 1. 根据前序遍历和中序遍历构造二叉树
 * 2. 通过构造的二叉树求得后序遍历
 *
 * @author jdli
 */
public class D4_1BinaryTree {

    public static void main(String[] args) {
        String pre = "GDAFEMHZ";
        String in = "ADEFGHMZ";
        String post = "AEFDHZMG";

        System.out.println("前序遍历和中序遍历构造二叉树：");
        Tree binaryTree = buildBinaryTreeViaPreIn(pre.toCharArray(), in.toCharArray());

        List<Character> postList = new ArrayList<>();
        postSequence(binaryTree, postList);
        System.out.println("后序遍历："+postList);

        postList = new ArrayList<>();
        preSequence(binaryTree, postList);
        System.out.println("前序遍历："+postList);

        postList = new ArrayList<>();
        inSequence(binaryTree, postList);
        System.out.println("中序遍历："+postList);


        System.out.println("后序遍历和中序遍历构造二叉树：");
        binaryTree = buildBinaryTreeViaInPost(post.toCharArray(), in.toCharArray());

        postList = new ArrayList<>();
        postSequence(binaryTree, postList);
        System.out.println("后序遍历："+postList);

        postList = new ArrayList<>();
        preSequence(binaryTree, postList);
        System.out.println("前序遍历："+postList);

        postList = new ArrayList<>();
        inSequence(binaryTree, postList);
        System.out.println("中序遍历："+postList);
    }

    /**
     * 获取中序遍历
     * @param binaryTree
     * @param postList
     */
    private static void inSequence(Tree binaryTree, List<Character> postList) {
        if (binaryTree == null) {
            return;
        }
        inSequence(binaryTree.getLTree(), postList);
        postList.add(binaryTree.getData());
        inSequence(binaryTree.getRTree(), postList);
    }

    /**
     * 获取前序遍历
     * @param binaryTree
     * @param postList
     */
    private static void preSequence(Tree binaryTree, List<Character> postList) {
        if (binaryTree == null) {
            return;
        }
        postList.add(binaryTree.getData());
        preSequence(binaryTree.getLTree(),postList);
        preSequence(binaryTree.getRTree(), postList);
    }

    /**
     * 获取后序遍历
     *
     * @param binaryTree
     * @param postList
     */
    private static void postSequence(Tree binaryTree, List<Character> postList) {
        if (binaryTree == null) {
            return;
        }
        postSequence(binaryTree.getLTree(), postList);
        postSequence(binaryTree.getRTree(), postList);
        postList.add(binaryTree.getData());
    }


    /**
     * 通过前序遍历和中序遍历构造二叉树
     *
     * @param preArray
     * @param inArray
     * @return
     */
    private static Tree buildBinaryTreeViaPreIn(char[] preArray, char[] inArray) {
        if (preArray.length == 0 || inArray.length == 0) {
            return null;
        }

        Tree binaryTree = new Tree(preArray[0]);
        int inIndex = inSearch(inArray, binaryTree.getData());

        binaryTree.setLTree(buildBinaryTreeViaPreIn(Arrays.copyOfRange(preArray, 1, inIndex + 1),
                Arrays.copyOfRange(inArray, 0, inIndex)));
        binaryTree.setRTree(buildBinaryTreeViaPreIn(Arrays.copyOfRange(preArray, inIndex + 1, preArray.length),
                Arrays.copyOfRange(inArray, inIndex + 1, inArray.length)));

        return binaryTree;
    }

    /**
     * 通过中序遍历和后序遍历构造二叉树
     * @param postArray
     * @param inArray
     * @return
     */
    private static Tree buildBinaryTreeViaInPost(char[] postArray, char[] inArray) {
        if (postArray.length == 0 || inArray.length == 0) {
            return null;
        }

        Tree binaryTree = new Tree(postArray[postArray.length - 1]);
        int inIndex = inSearch(inArray, binaryTree.getData());
        binaryTree.setLTree(buildBinaryTreeViaInPost(Arrays.copyOfRange(postArray, 0, inIndex),
                Arrays.copyOfRange(inArray, 0, inIndex)));
        binaryTree.setRTree(buildBinaryTreeViaInPost(Arrays.copyOfRange(postArray,inIndex,postArray.length-1),
                Arrays.copyOfRange(inArray,inIndex+1,inArray.length)));

        return binaryTree;
    }
    
    private static int inSearch(char[] inArray, char data) {
        for (int i = 0; i < inArray.length; i++) {
            if (inArray[i] == data) {
                return i;
            }
        }
        return -1;
    }


}
