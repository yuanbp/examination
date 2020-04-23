package com.chieftain.examination;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-05-17
 * @time 11:04
 */
public class StrTest {

    @Test
    public void streamIoTest () throws IOException {
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println(verifyCode);

        for (int i = 0; i <= 100; i++)
        {
//            String sources = "0123456789ABCDEFGHIJKLMNOPQRSTUVWSYZ"; // 加上一些字母，就可以生成pc站的验证码了
            String sourcesnum = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
            Random rand = new Random();
            StringBuffer flag = new StringBuffer();
            for (int j = 0; j < 6; j++)
            {
//                flag.append(sources.charAt(rand.nextInt(35)) + "");
                flag.append(sourcesnum.charAt(rand.nextInt(9)) + "");
            }
            System.out.println(flag.toString());
        }
        if ("".indexOf(".*") != -1) {
            "".substring(0, "".indexOf(".*"));
        }
    }

    private void extractBill() throws IOException {
        BufferedReader reader = null;
        try {
            String str = "交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,代金券或立减优惠金额,微信退款单号,商户退款单号,退款金额,代金券或立减优惠退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率\n" +
                    "`2014-11-10 16：33：45,`wx2421b1c4370ec43b,`10000100,`0,`1000,`1001690740201411100005734289,`1415640626,`085e9858e3ba5186aafcbaed1,`MICROPAY,`SUCCESS,`CFT,`CNY,`0.01,`0.0,`0,`0,`0,`0,`,`,`被扫支付测试,`订单额外描述,`0,`0.60%\n" +
                    "`2014-11-10 16：46：14,`wx2421b1c4370ec43b,`10000100,`0,`1000,`1002780740201411100005729794,`1415635270,`085e9858e90ca40c0b5aee463,`MICROPAY,`SUCCESS,`CFT,`CNY,`0.01,`0.0,`0,`0,`0,`0,`,`,`被扫支付测试,`订单额外描述,`0,`0.60%\n" +
                    "总交易单数,总交易额,总退款金额,总代金券或立减优惠退款金额,手续费总金额\n" +
                    "`2,`0.02,`0.0,`0.0,`0";

            String line;

            int detailLen = 25;
            int summaryLen = 6;
            int summaryTileLen = 1;

            AtomicBoolean skipLine = new AtomicBoolean(true);
            AtomicBoolean summarySkipLine = new AtomicBoolean(true);
            reader = new BufferedReader(new InputStreamReader(IOUtils.toInputStream(str, StandardCharsets.UTF_8)));
            while ((line = reader.readLine()) != null) {
                if (skipLine.get()) {
                    skipLine.compareAndSet(true, false);
                    continue;
                }
//            System.out.println(line);
                System.out.println("--------------------------------------------------");
                line = line.replaceAll(",`", "@separat@");
                line = line.replaceAll("`", "@separat@");
                String[] vals = line.split("@separat@");
                if (summaryTileLen == vals.length && summarySkipLine.get()) {
                    summarySkipLine.compareAndSet(true, false);
                    continue;
                }
                if (detailLen == vals.length) {
                    System.out.println("-----账单详情-----");
                    Arrays.stream(vals).forEach(System.out::println);
                } else {
                    System.out.println("-----账单汇总-----");
                    Arrays.stream(vals).forEach(System.out::println);
                }

                System.out.println("--------------------------------------------------");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                reader.close();
            }
        }
    }

    @Test
    public void strFormatTest () {
        System.out.println(String.format("%s%s%s", "1", File.separator, "3"));
        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void moveTest () {
        System.out.println(100 & 0x1);
    }
}
