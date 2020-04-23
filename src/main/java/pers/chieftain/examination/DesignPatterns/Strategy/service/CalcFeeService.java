package pers.chieftain.examination.DesignPatterns.Strategy.service;

import pers.chieftain.examination.DesignPatterns.Strategy.interfaces.ICalcFeeStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chieftain
 * @date 2020/4/2 15:57
 */
@Service
public class CalcFeeService {

    private Map<String, ICalcFeeStrategy> strategyMap = new ConcurrentHashMap<>();

    public CalcFeeService(List<ICalcFeeStrategy> calcFeeStrategies) {
        calcFeeStrategies.forEach(e -> {
            strategyMap.put(e.userType(), e);
        });
    }

    public void totalFee(String userType, double fee) {
        System.out.println(userType + " 的价格是 " + strategyMap.get(userType).calc(fee));
    }
}
