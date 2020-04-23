package pers.chieftain.examination.schedule;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler
{
    class SchedulerTimerTask extends TimerTask
    {
        private SchedulerTask schedulerTask = null;

        private ScheduleIterator scheduleIterator = null;


        public SchedulerTimerTask(SchedulerTask schedulerTask,
                                  ScheduleIterator scheduleIterator)
        {
            this.schedulerTask = schedulerTask;
            this.scheduleIterator = scheduleIterator;
        }


        public void run()
        {
            schedulerTask.run();
            reschedule(schedulerTask, scheduleIterator);
        }
    }

    private final Timer timer = new Timer();


    public Scheduler()
    {
    }


    public void cancel()
    {
        timer.cancel();
    }


    public void schedule(SchedulerTask schedulerTask,
                         ScheduleIterator scheduleIterator)
    {
        Date time = scheduleIterator.next();

        if (time == null)
        {
            schedulerTask.cancel();
        }
        else
        {
            synchronized (schedulerTask.lock)
            {
                if (schedulerTask.state != SchedulerTask.VIRGIN)
                {
                    throw new IllegalStateException("Task already scheduled or cancelled");
                }
                
                schedulerTask.state = SchedulerTask.SCHEDULED;

                schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask,
                                                                 scheduleIterator);

                timer.schedule(schedulerTask.timerTask, time);
            }
        }
    }


    private void reschedule(SchedulerTask schedulerTask,
                            ScheduleIterator scheduleIterator)
    {
        Date time = scheduleIterator.next();

        if (time == null)
        {
            schedulerTask.cancel();
        }
        else
        {
            synchronized (schedulerTask.lock)
            {
                if (schedulerTask.state != SchedulerTask.CANCELLED)
                {
                    schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask,
                                                                     scheduleIterator);

                    timer.schedule(schedulerTask.timerTask, time);
                }
            }
        }
    }
}

