package com.chieftain.examination;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-05-05
 * @time 13:34
 */
public class FillArgsTest2 {

    private static final Set<Pattern> PATTERNS = new HashSet<Pattern>() {{
        add(Pattern.compile("\\{(YYYY|YY)\\}"));
        add(Pattern.compile("\\{(MM)\\}"));
        add(Pattern.compile("\\{(DD)\\}"));
        add(Pattern.compile("\\{(ss)\\}"));
        add(Pattern.compile("\\{(UCODE)\\}"));
        add(Pattern.compile("\\{(COMCODE)\\}"));
    }};

    private static final Pattern SERIALNUM_P = Pattern.compile("\\{(\\d)\\}");

    @Test
    public void fillDateTest () {
//        final String template = "EXM-{YYYY}-{MM}-{DD}-{COMCODE}-{UCODE}-{9}";
        final String template = "EXM-{9}";
        Map<String, String> fillDatas = this.prepareData();
        AtomicReference<String> result = new AtomicReference<>(template);
        PATTERNS.forEach(e -> {
            Matcher matcher = e.matcher(result.get());
//            System.out.println("groupCount is -->" + matcher.groupCount());
            while(matcher.find()){
                result.set(result.get().replace(matcher.group(), fillDatas.get(matcher.group(1))));
            }
        });
        Matcher matcher = SERIALNUM_P.matcher(result.get());
        while(matcher.find()){
            result.set(result.get().replace(matcher.group(), String.valueOf(genNum(Integer.parseInt(matcher.group(1)), 1))));
        }
        System.out.println(result.get());
        System.out.println("05e463a6d54f4595b6cec226b5cb7737".length());
    }

    public Map<String, String> prepareData () {
        return new HashMap<String, String>(){{
            put("YYYY", DateTimeFormatter.ofPattern("yyyy").format(LocalDate.now()));
            put("YY", DateTimeFormatter.ofPattern("yy").format(LocalDate.now()));
            put("MM", DateTimeFormatter.ofPattern("MM").format(LocalDate.now()));
            put("DD", DateTimeFormatter.ofPattern("dd").format(LocalDate.now()));
            put("UCODE", "U0001");
            put("COMCODE", "COM00001");
        }};
    }

    public String genNum (int len, int begin) {
        return String.format("%0" + len + "d", begin);
    }

    private long generateRandomNumber(int n){
        if(n<1){
            throw new IllegalArgumentException("随机数位数必须大于0");
        }
        return (long)(Math.random()*9*Math.pow(10,n-1)) + (long)Math.pow(10,n-1);
    }

    @Test
    public void fillZero () {
        System.out.println(String.format("%06d",12));
    }
}
