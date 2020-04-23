package com.chieftain.examination;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * com.chieftain.jtestcodes [workset]
 * Created by chieftain on 2018/9/10
 *
 * @author chieftain on 2018/9/10
 */
public class JsonTest {

    public static void main(String[] args) throws Exception {
        String jsonStr = "{\n" +
                "\t\"appId\": \"tom\",\n" +
                "\t\"password\": \"123456\",\n" +
                "\t\"methodName\": \"login\",\n" +
                "\t\"userKey\": \"1\",\n" +
                "\t\"timestamp\": \"2018-09-10 15:44:00\"\n" +
                "}";
        InputStream is = new ByteArrayInputStream(jsonStr.getBytes("UTF-8"));
//        Object json = JSON.parse(jsonStr);
        Map<String, String> map = JSON.parseObject(is,Map.class);
        System.out.println(map);
    }
}
