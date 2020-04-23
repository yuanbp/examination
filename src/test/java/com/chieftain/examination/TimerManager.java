package com.chieftain.examination;

import java.util.Date;
import java.util.Timer;


/**
 * 定时任务管理器
 * @author dyh
 *
 */
public class TimerManager {
    
    /**
     * 单例模式
     */
    private static TimerManager timerManager = null;
    private TimerManager(){}
    public static TimerManager getInstance(){
        if(timerManager == null){
            timerManager = new TimerManager();
        }
        return timerManager;
    }
    
    /**
     * 定时器
     */
    private Timer timer = new Timer("homePageTimer");


    /**
     * 定时任务
     */
    private TimerTask timerTask = null;
    
    /**
     * 启动定时任务
     */
    public void startTimerTask(){
        if(timerTask==null){
            timerTask = new TimerTask();
        }
        timer.schedule(timerTask, new Date(), 5000);
    }
    
    /**
     * 定时任务取消
     */
    public void stopTimerTask(){
        timerTask.cancel();
        timer.purge();
    }

    public static void main(String[] args) {
        TimerManager manager = new TimerManager();
        for (int i = 0; i < 5; i++) {
            manager.startTimerTask();
        }
        manager.stopTimerTask();
        for (int i = 0; i < 10; i++) {
            manager.startTimerTask();
        }
        manager.stopTimerTask();
    }

}