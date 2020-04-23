package pers.chieftain.examination.DesignPatterns.Strategy.interfaces;

/**
 * @author chieftain
 * @date 2020/4/2 16:00
 */
public interface ICalcFeeStrategy {

    String userType();

    double calc(double fee);
}
