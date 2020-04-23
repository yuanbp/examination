package com.chieftain.examination;

import java.util.EnumSet;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-02-26
 * @time 19:48
 */
public enum TestEnum {

    A(1,"A"),
    B(2,"B"),
    C(3,"C");

    int code;
    String desc;

    TestEnum (int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc () {
        return desc;
    }

    public static TestEnum find(int code) {
        return EnumSet.allOf(TestEnum.class)
                .stream()
                .filter(e -> e.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported TestEnum %s.", code)));
//        return EnumSet.allOf(TestEnum.class)
//                .stream()
//                .filter(e -> e.getCode() == code)
//                .findFirst()
//                .orElse(null);
//        return Arrays.stream(TestEnum.values())
//                .filter(e -> e.getCode() == code).findFirst()
//                .orElseThrow(() -> {throw new IllegalStateException(String.format("Unsupported TestEnum %s.", code));});
    }
}
