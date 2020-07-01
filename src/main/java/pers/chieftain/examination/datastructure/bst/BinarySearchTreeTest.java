package pers.chieftain.examination.datastructure.bst;

/**
 * @author chieftain
 * @date 2020/5/11 14:46
 */
public class BinarySearchTreeTest {

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(50);
        bst.insert(100);
        bst.insert(23);
        bst.insert(45);
        bst.insert(16);
        bst.insert(37);
        bst.insert(3);
        bst.insert(99);
        bst.insert(22);

//        bst.levelOrder(bst.getRoot());
//        bst.preOrder(bst.getRoot());
//        bst.inOrder(bst.getRoot());
        bst.posOrder(bst.getRoot());
        System.out.println(bst.findMin());
        System.out.println(bst.findMax());
        Node findNode = bst.find(23);
        if(findNode == null) {
            System.out.println("没有匹配项");
        } else {
            System.out.println(findNode.toString());
        }
    }
}
