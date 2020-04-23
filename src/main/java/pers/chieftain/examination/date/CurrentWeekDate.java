package pers.chieftain.examination.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author chieftain
 * @date 2020/4/17 11:20
 */
public class CurrentWeekDate {

    public static void main(String[] args) {
        LocalDate currentWeekMonday = LocalDate.now().with(DayOfWeek.MONDAY);
        System.out.println(currentWeekMonday);
        String currentWeekTime = currentWeekMonday.atStartOfDay(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime currentWeekMondayTime = LocalDateTime.parse(currentWeekTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(currentWeekMondayTime));
    }
}
