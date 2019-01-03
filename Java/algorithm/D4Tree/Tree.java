package com.methonLearning.algorithm.D4Tree;

import lombok.Data;

/**
 * Tree structure
 *
 * @author jdli
 */
@Data
public class Tree {
    char data;
    Tree lTree;
    Tree rTree;

    public Tree(char data) {
        this.data = data;
        this.lTree = null;
        this.rTree = null;
    }
}
