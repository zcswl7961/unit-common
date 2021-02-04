package com.common.jdk.redblack;

import java.util.Comparator;

/**
 * 红黑树
 * @author zhoucg
 * @date 2021-01-28 11:32
 */
public class RedBlackTree<E> {

    private RedBlackNode<E> root;

    /**
     * 比较器
     */
    private Comparator<E> comp;


    public RedBlackTree(Comparator<E> comp){
        this.root = null;
        this.comp = comp;
    }

    public void insert(E data) {
        if(data == null) throw new NullPointerException("data should not be null");
        this.root = insert(data, root);
        this.root.setIsRed(false);
    }


    private RedBlackNode<E> insert(E data, RedBlackNode<E> h){
        if(h == null) return new RedBlackNode<>(data);
        int c = comp.compare(data, h.getData());
        if(c < 0){
            h.setLeft(insert(data, h.getLeft()));
        }
        else if(c > 0){
            h.setRight(insert(data, h.getRight()));
        }
        else{
            h.setData(data);
        }

        //Now for the rotating
        if(isRed(h.getRight()) && !isRed(h.getLeft()))
        {
            h = rotateLeft(h);
        }

        if(isRed(h.getLeft()) && isRed(h.getLeft().getLeft()))
        {
            h = rotateRight(h);
        }

        if(isRed(h.getLeft()) && isRed(h.getRight()))
        {
            flipColors(h);
        }
        return h;
    }

    private boolean isRed(RedBlackNode<E> node){
        if(node == null) return false;
        return node.getIsRed();
    }

    private RedBlackNode<E> rotateLeft(RedBlackNode<E> h){
        RedBlackNode<E> tmp = h.getRight();
        h.setRight(tmp.getLeft());
        tmp.setLeft(h);
        tmp.setIsRed(h.getIsRed());
        h.setIsRed(true);
        return tmp;
    }


    private RedBlackNode<E> rotateRight(RedBlackNode<E> h){
        RedBlackNode<E> tmp = h.getLeft();
        h.setLeft(tmp.getRight());
        tmp.setRight(h);
        tmp.setIsRed(h.getIsRed());
        h.setIsRed(true);
        return tmp;
    }

    private void flipColors(RedBlackNode<E> h)
    {
        h.getLeft().setIsRed(false);
        h.getRight().setIsRed(false);
        h.setIsRed(true);
    }


}
