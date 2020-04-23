package com.chieftain.examination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-05-05
 * @time 13:28
 */
public class FillArgsTest {

    private static final Pattern pattern = Pattern.compile("\\{(\\d)\\}");

    public static void main(String[] args) {
        String str="我是{0},我来自{1},今年{2}岁";
        String[] arr={"中国人","北京","22"};
        System.out.println(fillStringByArgs(str, arr));
    }

    private static String fillStringByArgs(String str,String[] arr){
        Matcher m= pattern.matcher(str);
        while(m.find()){
            str=str.replace(m.group(),arr[Integer.parseInt(m.group(1))]);
        }
        return str;
    }
}
