package com.chieftain.examination;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-02-26
 * @time 19:50
 */
public class EnumMainTest {

    public static void main(String[] args) throws Exception {
        TestEnum testEnum = TestEnum.find(8);
        switch (testEnum) {
            case A:
                System.out.println("a");
                break;
            case B:
                System.out.println("b");
                break;
            default:
                System.out.println("c");
        }
    }
}
