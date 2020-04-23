package pers.chieftain.examination.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test
{
    private Calendar startSee = null; //开始看病时间


    public Test()
    {
        startSee = Calendar.getInstance();
        System.out.println("感冒了." + startSee.getTime().toLocaleString());
    }
    
    public Date getScheduleDate(int day)
    {
//        startSee.add(Calendar.DATE, 1);   //按天来计算
        startSee.add(Calendar.SECOND, day); //按秒来计算
        return startSee.getTime();
    }

    public void start(int day)
    {
       final Timer timer = new Timer();
       
        timer.schedule(new TimerTask()
        {
            public void run()
            {
                System.out.println("回访啦.." + new Date().toLocaleString());
                
                timer.cancel();
            }
        }, getScheduleDate(day));
    }
    
    public static void main(String[] args)
    {
        //由于timer调系统时间不太起作用,所以拿秒来做测试.
        Test test = new Test();
        test.start(10);  
        test.start(15);  
        test.start(20);  
        test.start(30);  
    }
}

