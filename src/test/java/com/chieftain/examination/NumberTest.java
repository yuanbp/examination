package com.chieftain.examination;

/**
 * com.chieftain.jtestcodes [workset]
 * Created by chieftain on 2018/9/3
 *
 * @author chieftain on 2018/9/3
 */
public class NumberTest {

    public static void main(String[] args) {
//        double num = 256.0;

        Double mainWastage = Double.parseDouble("256.1");
        //主材损耗费取整
        if (mainWastage.intValue() - mainWastage == 0) {//判断是否符合取整条件
            System.out.println(String.valueOf(mainWastage.intValue()));
        } else {
            System.out.println(String.valueOf(mainWastage));
        }
    }


    public static String doubleTrans(double num) {
        if (Math.round(num) - num == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

    public static String doubleTrans(String numStr) {
        double num = Double.parseDouble(numStr);
        if (Math.round(num) - num == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

    public static String doubleTrans2(Double mainWastage){
        if (mainWastage.intValue() - mainWastage == 0) {
            return String.valueOf(mainWastage.intValue());
        } else {
            return String.valueOf(mainWastage);
        }
    }

    public static String doubleTrans2(String numStr){
        Double mainWastage = Double.parseDouble(numStr);
        if (mainWastage.intValue() - mainWastage == 0) {
            return String.valueOf(mainWastage.intValue());
        } else {
            return String.valueOf(mainWastage);
        }
    }
}
