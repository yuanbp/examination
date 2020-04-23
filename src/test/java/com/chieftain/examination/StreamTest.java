package com.chieftain.examination;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-04-03
 * @time 19:33
 */
public class StreamTest {

    @Test
    public void streamSum () {
        List<TestBo> list = new ArrayList<TestBo>() {{
            add(new TestBo("1", new BigDecimal(1000)));
            add(new TestBo("1", new BigDecimal(1000)));
            add(new TestBo("2", new BigDecimal(1000)));
            add(new TestBo("2", new BigDecimal(1000)));
            add(new TestBo("3", new BigDecimal(1000)));
        }};
        Map<String, TestBo> testBoMap = new HashMap<String, TestBo>() {{
            put("1", new TestBo("1", new BigDecimal(1000)));
            put("2", new TestBo("1", new BigDecimal(1000)));
            put("3", new TestBo("1", new BigDecimal(1000)));
            put("4", new TestBo("1", new BigDecimal(1000)));
            put("5", new TestBo("1", new BigDecimal(1000)));
        }};
        Map<String, BigDecimal> allotAmountMap = list.stream().collect(Collectors.groupingBy(TestBo::getId,
                Collectors.reducing(BigDecimal.ZERO, TestBo::getMoney, BigDecimal::add)));
        System.out.println(allotAmountMap.toString());

        for (Map.Entry<String, TestBo> entry : testBoMap.entrySet()) {
            System.out.println(entry.getValue().getMoney());
        }
        for (Map.Entry<String, TestBo> entry : testBoMap.entrySet()) {
            entry.getValue().setMoney(new BigDecimal(3000));
        }
        for (Map.Entry<String, TestBo> entry : testBoMap.entrySet()) {
            System.out.println(entry.getValue().getMoney());
        }
    }
}
