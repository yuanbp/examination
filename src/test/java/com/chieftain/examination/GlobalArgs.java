package com.chieftain.examination;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-06-17
 * @time 09:52
 */
public class GlobalArgs {

    private static String[] argArray = {"aaa","bbb","ccc","ddd","eee"};

    public static Set<String> args;

    public static Set<String> getArgs () {
        if (null == args) {
            synchronized (GlobalArgs.class) {
                if (null == args) {
                    args = new HashSet<>(Arrays.asList(argArray));
                }
            }
        }
        return args;
    }
}
