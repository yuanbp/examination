package pers.chieftain.examination.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chieftain
 * @date 2020/6/9 13:44
 */
public class RegexTest {

    private static final String REG_EMAIL = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
    private static final Pattern EMAIL_P = Pattern.compile(REG_EMAIL);

    public static void main(String[] args) {
        String mail = "1@qq.ccom";
        Matcher m = EMAIL_P.matcher(mail);
        System.out.println(m.matches());
    }
}
