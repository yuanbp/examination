package pers.chieftain.examination.schedule;

import java.util.Calendar;
import java.util.Date;

public interface ScheduleIterator
{
    public Date next();
}


class DailyIterator implements ScheduleIterator
{

    private final Calendar calendar = Calendar.getInstance();

    public DailyIterator(int hourOfDay, int minute, int second, Date date)
    {
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        
        if (!calendar.getTime().before(date)) {
            calendar.add(Calendar.DATE, -1);
        }
    }
    
    public DailyIterator(int hourOfDay, int minute, int second)
    {
        Date date = new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        
        if (!calendar.getTime().before(date)) {
            calendar.add(Calendar.DATE, -1);
        }
    }
    
    @Override
    public Date next() {
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
}

