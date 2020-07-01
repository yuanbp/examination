package pers.chieftain.examination.datastructure.bst;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author chieftain
 * @date 2020/5/11 14:36
 */
@Getter
@Setter
public class BinarySearchTree {

    private Node root;

    public BinarySearchTree() {
        root = null;
    }

    /**
     * 插入
     *
     * @param i
     */
    public void insert(int i) {
        Node newNode = new Node();
        newNode.setData(i);
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parent;
        while (true) {
            parent = current;
            if (i < current.getData()) {
                current = current.getLeft();
                if (current == null) {
                    parent.setLeft(newNode);
                    break;
                }
            } else {
                current = current.getRight();
                if (current == null) {
                    parent.setRight(newNode);
                    break;
                }
            }
        }
    }

    /**
     * 层序遍历
     *
     * @param root
     */
    public void levelOrder(Node root) {
        if (root == null) {
            return;
        }
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (queue.size() != 0) {
            Node node = queue.poll();
            System.out.println(node.toString());

            if (node.getLeft() != null) {
                queue.offer(node.getLeft());
            }

            if (node.getRight() != null) {
                queue.offer(node.getRight());
            }
        }
    }

    public void preOrder(Node node) {
        if (node != null) {
            node.display();
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    /**
     * 中序遍历
     *
     * @param node
     */
    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.getLeft());
            node.display();
            inOrder(node.getRight());
        }
    }

    public void posOrder(Node node) {
        if (node != null) {
            posOrder(node.getLeft());
            posOrder(node.getRight());
            node.display();
        }
    }

    /**
     * 查找最小值
     *
     * @return
     */
    public int findMin() {
        Node current = root;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current.getData();
    }

    /**
     * 查找最大值
     *
     * @return
     */
    public int findMax() {
        Node current = root;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current.getData();
    }

    /**
     * 查找指定值
     *
     * @param n
     * @return
     */
    public Node find(int n) {
        Node cur = root;
        while (cur.getData() != n) {
            cur = n < cur.getData() ? cur.getLeft() : cur.getRight();
            if (cur == null) {
                break;
            }
        }
        return cur;
    }

    /// <summary>
    /// 二叉查找树删除节点
    /// </summary>
    /// <param name="key"></param>
    /// <returns></returns>
    public boolean delete(int key) {
        //要删除的当前结点
        Node current = root;
        //当前结点的父结点
        Node parent = root;
        //当前结点是否是左子树
        boolean isLeftChild = true;
        //先通过二分查找找出要删除的结点
        while (current.getData() != key) {
            parent = current;
            if (key < current.getData()) {
                isLeftChild = true;
                current = current.getLeft();
            } else {
                isLeftChild = false;
                current = current.getRight();
            }
            if (current == null) {
                return false;
            }
        }


        //要删除的结点是叶子结点的处理
        if (current.getLeft() == null && current.getRight() == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }

        //要删除的结点是带有一个子节点的节点的处理
        else if (current.getRight() == null) {
            if (current == root) {
                root = current.getLeft();
            } else if (isLeftChild) {
                parent.setLeft(current.getLeft());
            } else {
                parent.setRight(current.getLeft());
            }
        } else if (current.getLeft() == null) {
            if (current == root) {
                root = current.getRight();
            } else if (isLeftChild) {
                parent.setLeft(current.getRight());
            } else {
                parent.setRight(current.getRight());
            }
        }
        //要删除的结点是带有两个子节点的节点的处理
        else {
            Node successor = GetSuccessor(current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.setLeft(successor);
            } else {
                parent.setRight(successor);
            }
            //因为后继结点是要删除结点右子树的最左侧结点
            //所以后继结点的左子树肯定是要删除结点左子树
            successor.setLeft(current.getLeft());
        }
        return true;
    }

    /// <summary>
    /// 获取后继结点
    /// </summary>
    /// <param name="delNode">要删除的结点</param>
    /// <returns></returns>
    public Node GetSuccessor(Node delNode) {
        //后继节点的父节点
        Node successorParent = delNode;
        //后继节点
        Node successor = delNode.getRight();
        Node current = delNode.getRight().getLeft();
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.getLeft();
        }
        //如果后继结点不是要删除结点的右子结点，
        //则要将后继节点的子结点赋给后继节点父节点的左节点
        //删除结点的右子结点赋给后继结点作为 后继结点的后继结点
        if (successor != delNode.getRight()) {
            successorParent.setLeft(successor.getRight());
            successor.setRight(delNode.getRight());
        }
        return successor;
    }
}