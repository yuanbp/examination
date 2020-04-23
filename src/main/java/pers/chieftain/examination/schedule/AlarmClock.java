package pers.chieftain.examination.schedule;

public class AlarmClock
{
    private final Scheduler scheduler = new Scheduler();

    private final int hourofDay, minute, second;


    public AlarmClock(int hourOfDay, int minute, int second)
    {
        this.hourofDay = hourOfDay;
        this.minute = minute;
        this.second = second;
    }


    public void start()
    {
        scheduler.schedule(new SchedulerTask()
        {
            public void run()
            {
                System.out.println("时间到...");
            }
        }, new DailyIterator(hourofDay, minute, second));
    }


    public static void main(String[] args)
    {
        AlarmClock alarmClock = new AlarmClock(17, 58, 0);
        alarmClock.start();
    }
}

