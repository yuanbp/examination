package com.chieftain.examination;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * com.chieftain.jtestcodes [workset]
 * Created by chieftain on 2018/10/10
 *
 * @author chieftain on 2018/10/10
 */
public class AnyTest {

    public static void main (String[] args) throws Exception {
//        new AnyTest().testDateFormat();
//        JSON.parseObject("{}");
//        int i = 1 / (1-1);

//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            AnyTest test = new AnyTest();
//            test.listTest(list);
//        }
//        list.forEach(s -> System.out.println(s));

//        streamDistinct();

//        String dateStr = "2019-03-11 17:02:00";
//        Date date = Date.from(LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant());
//        System.out.println(date);

        Map<String, String> map = new HashMap<>();
        String k = "key";
        String v = "value";
        map.put(k, v);
        v = "vvv";
        System.out.println(map.get(k));
    }

    static class TestBean {
        String field1;
        String field2;

        public TestBean(String f1, String f2) {
            this.field1 = f1;
            this.field2 = f2;
        }

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }
    }

    public static void streamDistinct () {
        List<TestBean> strs = new ArrayList<>();
        TestBean testBean1 = new TestBean("1","1");
        TestBean testBean2 = new TestBean("1","1");
        TestBean testBean3 = new TestBean("2","2");
        TestBean testBean4 = new TestBean("2","2");
        TestBean testBean5 = new TestBean("3","3");
        TestBean testBean6 = new TestBean("4","4");
        strs.add(testBean1);
        strs.add(testBean2);
        strs.add(testBean3);
        strs.add(testBean4);
        strs.add(testBean5);
        strs.add(testBean6);
        List<String> results = strs.stream().map(TestBean::getField1).distinct().collect(Collectors.toList());
        results.forEach(System.out::println);
    }

    public void listTest (List<String> list) {
        String s = this.getStringRandom(5);
        list.add(s);
    }

    // 生成随机数字和字母，
    public String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        // 参数 length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public void testStrFormat () {
        System.out.println(String.format("######收到不识别的回执: %s", "hello"));
    }

    public void testDateFormat () {
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        System.out.println(format.format(new Date()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        LocalDateTime now = LocalDateTime.now();
        String nowStr = formatter.format(now);
        System.out.println(nowStr);
    }

    public void testDevide () {
        int scala = 0;
        int pointsDataLimit = 1000;
        int size = 3000;
        int part = (new BigDecimal(size).divide(new BigDecimal(pointsDataLimit), scala, BigDecimal.ROUND_UP)).intValue();
        System.out.println(part);
    }

    public void listJoin () {
        List<String> strs = new ArrayList<>();
        strs.add("1");
        strs.add("1");
        strs.add("1");
        System.out.println(String.join("','", strs));
        System.out.println(StringUtils.join(strs, ","));
    }

    public void distinctlistLumbda () {
        String[] dumplicates = new String[5];
        dumplicates[0] = "1";
        dumplicates[1] = "1";
        dumplicates[2] = "1";
        dumplicates[3] = "3";
        dumplicates[4] = "4";
        List<String> unique = Arrays.asList(dumplicates).stream().distinct().collect(Collectors.toList());
        for (String s : unique) {
            System.out.println(s);
        }
    }

    public static void dateTest () {
        // JDK 8 有bug 没有任何符号的毫秒数解析失败
        DateTimeFormatter MICRODATEFORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
//        DateTimeFormatter MICRODATEFORMAT = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmssSSS").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();;
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.parse("20181224193930101", MICRODATEFORMAT);
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        Date date = Date.from(instant);
        System.out.println(date.toString());
        System.out.println(localDateTime.format(MICRODATEFORMAT));
    }

    public static String testException () {
        try {
            int i = 1 / 0;
            return String.valueOf(i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void oldTest () {
        //        fileTest();
//        String filePath = "/Users/chieftain/retainfiles/tmp/recv/4a0cf53dccb84ef4975a0fe0cfb72e00.xml";
//        File file = new File(filePath);
//        File tempFile = new File(file.getParent() + File.separator + "TEMP_" + file.getName());
//        while(file.renameTo(tempFile)) {
//            if(!tempFile.delete()) {
//                TimeUnit.MILLISECONDS.sleep(500);
//            }
//        }

//        String str = "a,b,c,,";
//        String[] ary = str.split(",", -1);
//        // 预期大于 3，结果是 3
//        System.out.println(ary.length);

//        Class<AnyTest> clazz = AnyTest.class;
//        AnyTest anyTest = clazz.newInstance();
//        String className = clazz.getName();
//        System.out.println(className);
//
//        LocalDateTime rightNow=LocalDateTime.now();
//        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("YYYYMMddHHmmssSSS");
//        System.out.println(rightNow.format(formatter));

//        Calendar calendar1 = Calendar.getInstance();
//        String datestr1 = "2018-11-16 00:00:00";
//        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datestr1);
//        calendar1.setTime(date1);
//
//        Calendar calendar2 = Calendar.getInstance();
//        String datestr2 = "2018-12-16 00:00:00";
//        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datestr2);
//        calendar2.setTime(date2);
//        while (calendar1.before(calendar2)) {
//            System.out.println("test");
//            calendar1.add(Calendar.MONTH, 1);
//        }

//        int partition = 1000;
//        for(int i=0; i < 5000; i++) {
//            int part = i / partition;
//            part += 1;
//            System.out.println(i + "---" + part);
//        }
    }

    public static void fileTest() throws Exception {
        String path = "/Users/chieftain/retainfiles/tmp/recv";
        File director = new File(path);
        if (director.exists()) {
            File[] files = director.listFiles();
            List<File> fileList = new ArrayList<>(Arrays.asList(files));
            splitFileList(fileList);
//            if (fileList.size() > 0) {
//                for (File file : fileList) {
//                    String content = FileNIOUtils.readFile(file);
//                    System.out.println(content);
//                    file.deleteOnExit();
//                }
//            }
        }
    }

    public static void splitFileList(List<File> dataList) {
        // 分批处理
        if (null != dataList && dataList.size() > 0) {
            int pointsDataLimit = 100;// 限制条数
            Integer size = dataList.size();
            // 判断是否有必要分批
            if (pointsDataLimit < size) {
                int part = size / pointsDataLimit;// 分批数
                System.out.println("共有 ：" + size + "条，！" + "分为 ：" + part + "批");
                for (int i = 0; i < part; i++) {
                    //1000 条
                    CopyOnWriteArrayList<File> listPage = new CopyOnWriteArrayList<>();
                    listPage.addAll(dataList.subList(0, pointsDataLimit));
                    BackFileTask backFileTask = new BackFileTask(listPage);
                    Thread thread = new Thread(backFileTask);
                    thread.start();
                    // 剔除
                    dataList.subList(0, pointsDataLimit).clear();
                }

                if (!dataList.isEmpty()) {
                    // 表示最后剩下的数据
                    CopyOnWriteArrayList<File> listPage = new CopyOnWriteArrayList<>();
                    listPage.addAll(dataList);
                    BackFileTask backFileTask = new BackFileTask(listPage);
                    Thread thread = new Thread(backFileTask);
                    thread.start();
                }
            } else {
                CopyOnWriteArrayList<File> listPage = new CopyOnWriteArrayList<>();
                listPage.addAll(dataList);
                BackFileTask backFileTask = new BackFileTask(listPage);
                Thread thread = new Thread(backFileTask);
                thread.start();
                System.out.println(dataList);
            }
        } else {
            System.out.println("没有数据!!!");
        }
    }

    public static void arrcyCopyTest() {
        String[] srcArray = new String[3];
        srcArray[0] = "0";
        srcArray[1] = "1";
        srcArray[2] = "2";
        String[] newArray = new String[srcArray.length + 1];
        System.arraycopy(srcArray, 0, newArray, 1, srcArray.length);
        newArray[0] = "1";
        System.out.println(newArray);
    }

    public static void theadTest() {
        String outStr = "hello";
        Executors.newCachedThreadPool().execute(new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String outStr = "1111";
                System.out.println(outStr);
            }
        });
    }
}
