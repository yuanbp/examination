package com.chieftain.examination;

import java.util.Arrays;
import java.util.List;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-06-17
 * @time 09:44
 */
public class CompareTest3 {

    String[] args = {"aaa","bbb","ccc","ddd","eee"};

    public static void main(String[] args) {
        System.out.println(new CompareTest3().compare("zzz"));
    }

    public boolean compare (String arg) {
        List<String> list = Arrays.asList(args);
        return list.contains(arg);
    }
}
