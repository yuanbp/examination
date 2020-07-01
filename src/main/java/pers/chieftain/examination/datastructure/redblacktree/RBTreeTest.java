package pers.chieftain.examination.datastructure.redblacktree;

/**
 * Java 语言: 二叉查找树
 *
 * @author skywang
 * @date 2013/11/07
 */
public class RBTreeTest {

    private static final int[] A = {10, 40, 30, 60, 90, 70, 20, 50, 80};
    private static final boolean M_DEBUG_INSERT = true;    // "插入"动作的检测开关(false，关闭；true，打开)
    private static final boolean M_DEBUG_DELETE = true;    // "删除"动作的检测开关(false，关闭；true，打开)

    public static void main(String[] args) {
        int i, ilen = A.length;
        RBTree<Integer> tree=new RBTree<>();

        System.out.print("== 原始数据: ");
        for(i=0; i<ilen; i++) {
            System.out.printf("%d ", A[i]);
        }
        System.out.print("\n");

        for(i=0; i<ilen; i++) {
            tree.insert(A[i]);
            // 设置mDebugInsert=true,测试"添加函数"
            if (M_DEBUG_INSERT) {
                System.out.printf("== 添加节点: %d\n", A[i]);
                System.out.print("== 树的详细信息: \n");
                tree.print();
                System.out.print("\n");
            }
        }

        System.out.print("== 前序遍历: ");
        tree.preOrder();

        System.out.print("\n== 中序遍历: ");
        tree.inOrder();

        System.out.print("\n== 后序遍历: ");
        tree.postOrder();
        System.out.print("\n");

        System.out.printf("== 最小值: %s\n", tree.minimum());
        System.out.printf("== 最大值: %s\n", tree.maximum());
        System.out.print("== 树的详细信息: \n");
        tree.print();
        System.out.print("\n");

        // 设置mDebugDelete=true,测试"删除函数"
        if (M_DEBUG_DELETE) {
            for(i=0; i<ilen; i++)
            {
                tree.remove(A[i]);

                System.out.printf("== 删除节点: %d\n", A[i]);
                System.out.print("== 树的详细信息: \n");
                tree.print();
                System.out.print("\n");
            }
        }

        // 销毁二叉树
        tree.clear();
    }
}