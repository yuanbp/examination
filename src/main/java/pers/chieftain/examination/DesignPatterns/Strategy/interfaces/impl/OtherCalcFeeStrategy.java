package pers.chieftain.examination.DesignPatterns.Strategy.interfaces.impl;

import pers.chieftain.examination.DesignPatterns.Strategy.interfaces.ICalcFeeStrategy;
import org.springframework.stereotype.Service;

/**
 * @author chieftain
 * @date 2020/4/2 16:01
 */
@Service
public class OtherCalcFeeStrategy implements ICalcFeeStrategy {
    @Override
    public String userType() {
        return "other";
    }

    @Override
    public double calc(double fee) {
        return fee;
    }
}
