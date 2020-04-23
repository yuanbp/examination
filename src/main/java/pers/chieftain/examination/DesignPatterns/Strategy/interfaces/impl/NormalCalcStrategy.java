package pers.chieftain.examination.DesignPatterns.Strategy.interfaces.impl;

import pers.chieftain.examination.DesignPatterns.Strategy.interfaces.ICalcFeeStrategy;
import org.springframework.stereotype.Service;

/**
 * @author chieftain
 * @date 2020/4/2 16:02
 */
@Service
public class NormalCalcStrategy implements ICalcFeeStrategy {
    @Override
    public String userType() {
        return "normal";
    }

    @Override
    public double calc(double fee) {
        return fee * 0.9;
    }
}
