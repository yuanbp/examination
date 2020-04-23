package com.chieftain.examination;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-06-17
 * @time 09:56
 */
public class CompareTest4 {

    public static void main(String[] args) {
        System.out.println(new CompareTest4().compare("aaa"));
    }

    public boolean compare (String arg) {
        return GlobalArgs.getArgs().contains(arg);
    }
}
