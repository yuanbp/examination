package pers.chieftain.examination.encryptionanddecryption;

import cn.hutool.core.codec.Base64Decoder;

import java.nio.charset.StandardCharsets;

/**
 * @author chieftain
 * @date 2020/6/10 17:44
 */
public class Base64Test {

    public static void main(String[] args) {
        String s = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8\n" +
                "Q0JFQ01FU1NBR0U+CiAgICA8TUVTU0FHRUhFQUQ+CiAgICAgICAgPE1FU1NBR0VJRD5lNmU3OGQ5\n" +
                "MC1kZjFlLTRjMzMtOWEwMC1mZWI3YTI5M2Q4NDM8L01FU1NBR0VJRD4KICAgICAgICA8TUVTU0FH\n" +
                "RVRZUEU+QTAwMTM8L01FU1NBR0VUWVBFPgogICAgICAgIDxNRVNTQUdFVElNRT4yMDIwLTA2LTA5\n" +
                "IDE1OjQ3OjAwPC9NRVNTQUdFVElNRT4KICAgICAgICA8U0VORENPREU+U0VORDwvU0VORENPREU+\n" +
                "CiAgICAgICAgPFJFQ0lQVENPREU+UkVDSVBUPC9SRUNJUFRDT0RFPgogICAgICAgIDxUWVBFPjwv\n" +
                "VFlQRT4KICAgIDwvTUVTU0FHRUhFQUQ+CiAgICA8TUVTU0FHRUJPRFk+CiAgICAgICAgPE51Y2xl\n" +
                "YXJIZWFkPgogICAgICAgICAgICA8aWQ+NzU2MTI2OGQxMGFlNDhlN2JhMjgwYTg0YjExNmU0NGY8\n" +
                "L2lkPgogICAgICAgICAgICA8cGVycE5vPjIwMjAwNjA5MDAwMTA1PC9wZXJwTm8+CiAgICAgICAg\n" +
                "ICAgIDxwYXNzcG9ydE5vPkZLMjAyMDA2MDlJMDA0OTwvcGFzc3BvcnRObz4KICAgICAgICAgICAg\n" +
                "PGlzRW1wdHk+MDE8L2lzRW1wdHk+CiAgICAgICAgICAgIDxjdXNMb2NrPjwvY3VzTG9jaz4KICAg\n" +
                "ICAgICAgICAgPG1haW5DdXM+NDcyNTwvbWFpbkN1cz4KICAgICAgICAgICAgPHZlaGljTm8+6YSC\n" +
                "QTZFMjhYPC92ZWhpY05vPgogICAgICAgICAgICA8dmVoaWNXdD4yODA1PC92ZWhpY1d0PgogICAg\n" +
                "ICAgICAgICA8ZHJpdk5hbWU+PC9kcml2TmFtZT4KICAgICAgICAgICAgPGRyaXZQaG9uZT48L2Ry\n" +
                "aXZQaG9uZT4KICAgICAgICAgICAgPHJlbWFya3M+PC9yZW1hcmtzPgogICAgICAgICAgICA8ZGVj\n" +
                "bENvcENvZGU+NDI1MDY2SzAxNDwvZGVjbENvcENvZGU+CiAgICAgICAgICAgIDxkZWNsQ29wTmFt\n" +
                "ZT7mrabmsYnkuZ3nsbPpgJrlm73pmYXotKfov5Dku6PnkIbmnInpmZDlhazlj7g8L2RlY2xDb3BO\n" +
                "YW1lPgogICAgICAgICAgICA8c3JjQ3NzYT4wMjwvc3JjQ3NzYT4KICAgICAgICAgICAgPGRzdENz\n" +
                "c2E+MDE8L2RzdENzc2E+CiAgICAgICAgICAgIDxidXNzVHlwZT4wNTwvYnVzc1R5cGU+CiAgICAg\n" +
                "ICAgICAgIDxnb29kc1R5cGU+PC9nb29kc1R5cGU+CiAgICAgICAgICAgIDxkZWNsVGltZT4yMDIw\n" +
                "LTA2LTA5VDE1OjQ3OjAwKzA4OjAwPC9kZWNsVGltZT4KICAgICAgICAgICAgPHN0YXR1cz4xPC9z\n" +
                "dGF0dXM+CiAgICAgICAgICAgIDx0cmFkZUNvZGU+NDI1MDY2SzAxNDwvdHJhZGVDb2RlPgogICAg\n" +
                "ICAgICAgICA8dHJhZGVOYW1lPuatpuaxieS5neexs+mAmuWbvemZhei0p+i/kOS7o+eQhuaciemZ\n" +
                "kOWFrOWPuDwvdHJhZGVOYW1lPgogICAgICAgICAgICA8d21zQ29kZT48L3dtc0NvZGU+CiAgICAg\n" +
                "ICAgICAgIDx3bXNOYW1lPjwvd21zTmFtZT4KICAgICAgICAgICAgPGJvb2tObz48L2Jvb2tObz4K\n" +
                "ICAgICAgICAgICAgPGRlY2xUeXBlPjwvZGVjbFR5cGU+CiAgICAgICAgICAgIDx0cnNwTW9kZWNk\n" +
                "PjwvdHJzcE1vZGVjZD4KICAgICAgICAgICAgPGluT3V0VHlwZT5JPC9pbk91dFR5cGU+CiAgICAg\n" +
                "ICAgPC9OdWNsZWFySGVhZD4KICAgIDwvTUVTU0FHRUJPRFk+CjwvQ0JFQ01FU1NBR0U+Cg==";
        System.out.println(Base64Decoder.decodeStr(s, StandardCharsets.UTF_8));
    }
}
