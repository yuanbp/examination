package pers.chieftain.examination.datastructure.bst;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * @author chieftain
 * @date 2020/5/11 10:17
 */
public class TreeNodeTest {

    public static void main(String[] args)
    {
        TreeNode<Integer> treeNode = create();

//        preOrderRecur(treeNode);
//        preOrder(treeNode);

//        InOrderRecur(treeNode);
//        InOrder(treeNode);

//        PosOrderRecur(treeNode);
//        PosOrderOne(treeNode);

        LevelOrder(treeNode);
    }

    /**
     *                     1
     *                 /      \
     *               2         3
     *             /  \       /  \
     *            4    5     6    7
     *           / \  / \   / \   / \
     *          8  9 10 11 12 13 14 15
     * @return
     */
    public static TreeNode<Integer> create()
    {
        TreeNode<Integer> root = new TreeNode<>(1, 2, 3);
        TreeNode<Integer> n1 = new TreeNode<>(2, 4, 5);
        TreeNode<Integer> n2 = new TreeNode<>(3, 6, 7);
//        TreeNode<Integer> n3 = new TreeNode<>(4, 8, 9);
//        TreeNode<Integer> n4 = new TreeNode<>(5, 10, 11);
//        TreeNode<Integer> n5 = new TreeNode<>(6, 12, 13);
//        TreeNode<Integer> n6 = new TreeNode<>(7, 14, 15);
        root.setLChild(n1);
        root.setRChild(n2);
//        n1.setLChild(n3);
//        n1.setRChild(n4);
//        n2.setLChild(n5);
//        n2.setRChild(n6);
        return root;
    }

    /**
     * 递归前序遍历
     * @param treeNode
     */
    public static void preOrderRecur(TreeNode<Integer> treeNode)
    {
        if (treeNode == null)
        {
            return;
        }
        System.out.println(treeNode.toString());
        preOrderRecur(treeNode.getLChild());
        preOrderRecur(treeNode.getRChild());
    }

    /**
     * 递归中序遍历
     * @param treeNode
     */
    public static void InOrderRecur(TreeNode<Integer> treeNode)
    {
        if (treeNode == null)
        {
            return;
        }
        InOrderRecur(treeNode.getLChild());
        System.out.println(treeNode.toString());
        InOrderRecur(treeNode.getRChild());
    }

    /**
     * 递归后续遍历
     * @param treeNode
     */
    public static void PosOrderRecur(TreeNode<Integer> treeNode)
    {
        if (treeNode == null)
        {
            return;
        }
        PosOrderRecur(treeNode.getLChild());
        PosOrderRecur(treeNode.getRChild());
        System.out.println(treeNode.toString());
    }

    /**
     * 通过栈实现前序遍历
     * @param head
     */
    public static void preOrder(TreeNode<Integer> head)
    {
        if (head == null)
        {
            return;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        stack.push(head);
        while (stack.size() != 0)
        {
            TreeNode<Integer> cur = stack.pop();
            System.out.println(cur.toString());

            if (cur.getRChild() != null)
            {
                stack.push(cur.getRChild());
            }
            if (cur.getLChild() != null)
            {
                stack.push(cur.getLChild());
            }
        }
    }

    /**
     * 栈实现中序遍历
     * @param treeNode
     */
    public static void InOrder(TreeNode<Integer> treeNode)
    {
        if (treeNode == null)
        {
            return;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();

        TreeNode<Integer> cur = treeNode;

        while (stack.size() != 0 || cur != null)
        {
            while (cur != null)
            {
                stack.push(cur);
                cur = cur.getLChild();
            }
            TreeNode<Integer> node = stack.pop();
            System.out.println(node.toString());
            cur = node.getRChild();
        }
    }

    public static void PosOrderOne(TreeNode<Integer> treeNode)
    {
        if (treeNode == null)
        {
            return;
        }

        Stack<TreeNode<Integer>> stack1 = new Stack<>();
        Stack<TreeNode<Integer>> stack2 = new Stack<>();

        stack1.push(treeNode);
        TreeNode<Integer> cur = treeNode;

        while (stack1.size() != 0)
        {
            cur = stack1.pop();
            if (cur.getLChild() != null)
            {
                stack1.push(cur.getLChild());
            }
            if (cur.getRChild() != null)
            {
                stack1.push(cur.getRChild());
            }
            stack2.push(cur);
        }

        while (stack2.size() != 0)
        {
            TreeNode<Integer> node = stack2.pop();
            System.out.println(node.toString());
        }
    }

    public static void LevelOrder(TreeNode<Integer> treeNode)
    {
        if(treeNode == null)
        {
            return;
        }
        Queue<TreeNode<Integer>> queue = new ArrayDeque<>();
        queue.offer(treeNode);

        while (queue.size() != 0)
        {
            TreeNode<Integer> node = queue.poll();
            System.out.println(node.toString());

            if (node.getLChild() != null)
            {
                queue.offer(node.getLChild());
            }

            if (node.getRChild() != null)
            {
                queue.offer(node.getRChild());
            }
        }
    }
}
