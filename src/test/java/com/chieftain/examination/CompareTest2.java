package com.chieftain.examination;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-06-17
 * @time 09:37
 */
public class CompareTest2 {

    Map<String, String> argMap = new HashMap<String, String>() {{
        put("aaa", "aaa");
        put("bbb", "bbb");
        put("ccc", "ccc");
        put("ddd", "ddd");
        put("eee", "eee");
    }};

    public static void main(String[] args) {
        CompareTest2 ct = new CompareTest2();
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
