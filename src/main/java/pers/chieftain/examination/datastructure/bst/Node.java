package pers.chieftain.examination.datastructure.bst;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chieftain
 * @date 2020/5/11 14:14
 */
@Getter
@Setter
public class Node {

    private int data;

    public Node left;

    public Node right;

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", left=" + (left == null ? "" : left.getData()) +
                ", right=" + (right == null ? "" : right.getData()) +
                '}';
    }

    public void display() {
        System.out.println(this.toString());
    }
}
