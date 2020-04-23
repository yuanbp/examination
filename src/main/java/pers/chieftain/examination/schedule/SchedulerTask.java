package pers.chieftain.examination.schedule;

import java.util.TimerTask;
 
public abstract class SchedulerTask implements Runnable
{
    final Object lock = new Object();
 
    int state = VIRGIN;
 
    static final int VIRGIN = 0;
 
    static final int SCHEDULED = 1;
 
    static final int CANCELLED = 2;
 
    TimerTask timerTask = null;
 
 
    protected SchedulerTask()
    {
 
    }
 
 
    public abstract void run();
 
 
    public boolean cancel()
    {
        synchronized (lock)
        {
            if (timerTask != null)
            {
                timerTask.cancel();
            }
            boolean result = (state == SCHEDULED);
 
            state = CANCELLED;
 
            return result;
        }
    }
 
 
    public long scheduleExecutionTime()
    {
        synchronized (lock)
        {
            return timerTask == null ? 0 : timerTask.scheduledExecutionTime();
        }
    }
}