package com.common.jdk.redblack;

import lombok.Data;

/**
 * @author zhoucg
 * @date 2021-01-28 11:31
 */
@Data
public class RedBlackNode<E> {

    private E data;
    private RedBlackNode<E> left;
    private RedBlackNode<E> right;
    private Boolean isRed;

    public RedBlackNode(E data)
    {
        this.data = data;
        this.left = null;
        this.right = null;
        this.isRed = true;
    }



}
