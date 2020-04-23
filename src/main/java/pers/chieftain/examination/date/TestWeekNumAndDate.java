package pers.chieftain.examination.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class TestWeekNumAndDate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//初始化，第一周至少四天
		WeekFields wfs= WeekFields.of(DayOfWeek.MONDAY, 4);
		//一年最后一天日期的LocalDate，如果该天获得的周数为1或52，那么该年就只有52周，否则就是53周
		//获取指定时间所在年的周数
		int num= LocalDate.of(2019, 12, 31).get(wfs.weekOfWeekBasedYear());
		num = num == 1 ? 52 : num;
		System.out.println("第" + num + "周，周一日期:" + getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.MONDAY));
		System.out.println("第" + num + "周，周二日期:" + getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.TUESDAY));
		System.out.println("第" + num + "周，周三日期:" + getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.WEDNESDAY));
		System.out.println("第" + num + "周，周四日期:" + getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.THURSDAY));
		System.out.println("第" + num + "周，周五日期:" + getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.FRIDAY));
		System.out.println("第" + num + "周，周六日期:" + getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.SATURDAY));
		System.out.println("第" + num + "周，周日日期:" + getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.SUNDAY));
		//该格式周日为一周第一天，周六为一周最后一天
		System.out.println(LocalDate.parse("2019-52-1", DateTimeFormatter.ofPattern("YYYY-ww-e", Locale.CHINA)));
	}
	
	private static LocalDate getDateByYearAndWeekNumAndDayOfWeek(Integer year, Integer num, DayOfWeek dayOfWeek) {
		//周数小于10在前面补个0
        String numStr = num < 10 ? "0" + String.valueOf(num) : String.valueOf(num);
        //2019-W01-01获取第一周的周一日期，2019-W02-07获取第二周的周日日期
        String weekDate = String.format("%s-W%s-%s", year, numStr, dayOfWeek.getValue());
        return LocalDate.parse(weekDate, DateTimeFormatter.ISO_WEEK_DATE);
    }

}
