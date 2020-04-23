package pers.chieftain.examination.des;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chieftain
 * @date 2020-02-20 14:08
 */
public class Test {

    public static void main(String[] args) throws Exception {
        List<String> datas = new ArrayList<>();
        Map<String, String> dataMap = new HashMap<>();
//        dataMap.put("client_id", "trade_platform_web");
//        dataMap.put("app_code", "trade_platform_web");
//        dataMap.put("loginid", "bpyuan");
//        dataMap.put("password", "qwer1234");
        dataMap.put("client_id", "trade_platform_admin");
        dataMap.put("app_code", "trade_platform_admin");
        dataMap.put("loginid", "trade_admin");
        dataMap.put("password", "qwer1234");
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            System.out.println(entry.getKey() + "---" + entry.getValue());
            String result = DESUtils.des3EncodeCBC(entry.getValue());
            System.out.println(result);
            String urlResult = URLEncoder.encode(result, "UTF-8");
            System.out.println(urlResult);
        }
    }
}
