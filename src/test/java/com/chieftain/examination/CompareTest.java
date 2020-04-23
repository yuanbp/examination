package com.chieftain.examination;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-06-17
 * @time 09:29
 */
public class CompareTest {

    String[] args = {"aaa","bbb","ccc","ddd","eee"};

    Map<String, String> argMap;

    public CompareTest() {
        init();
    }

    public void init () {
        argMap = new HashMap<>();
        for (String arg : args) {
            argMap.put(arg, arg);
        }
    }

    public static void main(String[] args) {
        CompareTest ct = new CompareTest();
        String arg = "ccc";
        System.out.println(ct.compare(arg));
    }

    public boolean compare (String arg) {
        boolean result = false;
        if (this.argMap.containsKey(arg)) {
            result = true;
        }
        return result;
    }
}
