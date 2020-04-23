package pers.chieftain.examination.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @author chieftain
 * @date 2020-02-03 12:01
 */
public class FastJsonTest {

    public static void main(String[] args) {
        String jsonArrStr = "[{\n" +
                "\t\"flightName\": \"航班名称\",\n" +
                "    \"StartingPlace\": \"起始地\",\n" +
                "    \"destination\": \"起始地\",\n" +
                "    \"departureTime\": \"起飞时间\",\n" +
                "    \"timeOfArrival\": \"到达时间\",\n" +
                "    \"airport\": \"机场\",\n" +
                "    \"aircraftType\": \"机型\",\n" +
                "    \"position\": \"仓位\"\n" +
                "},{\n" +
                "\t\"flightName\": \"航班名称\",\n" +
                "    \"StartingPlace\": \"起始地\",\n" +
                "    \"destination\": \"起始地\",\n" +
                "    \"departureTime\": \"起飞时间\",\n" +
                "    \"timeOfArrival\": \"到达时间\",\n" +
                "    \"airport\": \"机场\",\n" +
                "    \"aircraftType\": \"机型\",\n" +
                "    \"position\": \"仓位\"\n" +
                "}]";
        JSONArray jsonArray = JSON.parseArray(jsonArrStr);
        System.out.println(jsonArray.toJSONString());
    }
}
