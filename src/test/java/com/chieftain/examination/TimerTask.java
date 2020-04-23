package com.chieftain.examination;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-06-10
 * @time 16:26
 */
public class TimerTask extends java.util.TimerTask {
    @Override
    public void run() {
        System.out.println("task runing...");
        if (1==1) {
            this.cancel();
        }
    }
}
