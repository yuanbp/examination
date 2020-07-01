package pers.chieftain.examination.datastructure.bst;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chieftain
 * @date 2020/5/11 09:57
 */
@Getter
@Setter
public class TreeNode<T> {

    private T data;

    private TreeNode<T> lChild;

    private TreeNode<T> rChild;

    public TreeNode(T val, TreeNode<T> lp, TreeNode<T> rp)
    {
        data = val;
        lChild = lp;
        rChild = rp;
    }

    public TreeNode(T val, T lVal, T rVal)
    {
        data = val;
        TreeNode<T> lNode = new TreeNode<>(lVal);
        TreeNode<T> rNode = new TreeNode<>(rVal);
        this.lChild = lNode;
        this.rChild = rNode;
    }

    public TreeNode(TreeNode<T> lp, TreeNode<T> rp)
    {
        this.lChild = lp;
        this.rChild = rp;
    }

    public TreeNode(T val)
    {
        data = val;
        this.lChild = null;
        this.rChild = null;
    }

    public TreeNode()
    {
        this.lChild = null;
        this.rChild = null;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "data=" + data +
                ", lChild.data=" + (lChild == null ? "" : lChild.getData()) +
                ", rChild.data=" + (rChild == null ? "" : rChild.getData()) +
                '}';
    }
}
