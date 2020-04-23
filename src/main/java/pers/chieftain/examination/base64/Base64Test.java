package pers.chieftain.examination.base64;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author chieftain
 * @date 2020-02-03 12:29
 */
public class Base64Test {

    public static void main(String[] args) {
        String jsonStr = "[{\n" +
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

        String encodeStr = Base64.getEncoder().encodeToString(jsonStr.getBytes(StandardCharsets.UTF_8));
        System.out.println(encodeStr);
        String decodeStr = new String(Base64.getDecoder().decode(encodeStr.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        System.out.println(decodeStr);
    }
}
