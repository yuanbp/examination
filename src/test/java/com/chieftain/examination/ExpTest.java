package com.chieftain.examination;

import java.util.regex.Pattern;

/**
 * com.chieftain.jtestcodes [workset]
 * Created by chieftain on 2018/9/3
 *
 * @author chieftain on 2018/9/3
 */
public class ExpTest {

    public static void main(String[] args) {
        String content = "029-33333333";
        String pattern = "^\\d+(\\-\\d+)?";
        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println(isMatch);
    }
}
