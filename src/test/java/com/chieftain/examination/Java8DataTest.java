package com.chieftain.examination;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-04-03
 * @time 15:25
 */
public class Java8DataTest {

    @Test
    public void PeriodTest () {
        LocalDate today = LocalDate.now();
        System.out.println("Today : " + today);
        LocalDate birthDate = LocalDate.of(2019, 4, 2);
        System.out.println("BirthDate : " + birthDate);

        Period p = Period.between(birthDate, today);
        System.out.printf("年龄 : %d 年 %d 月 %d 日", p.getYears(), p.getMonths(), p.getDays());
    }

    @Test
    public void diffDaysTest () {
        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.of(2018, 4, 3);
        long diffDays = start.until(now, ChronoUnit.DAYS);
        System.out.println(diffDays);
        start = start.plusDays(diffDays);
        System.out.println(start);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    @Test
    public void chronoUnitTest () {
        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.of(2018, 4, 3);
        long monthDiff = ChronoUnit.MONTHS.between(start, now);
        System.out.println(monthDiff);
        long packed1 = (start.getYear() * 12L + start.getMonthValue() - 1) * 32L + start.getDayOfMonth();
        long packed2 = (now.getYear() * 12L + now.getMonthValue() - 1) * 32L + now.getDayOfMonth();
        monthDiff = new BigDecimal(packed2 -packed1).divide(new BigDecimal(32), 9, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_UP).longValue();
        System.out.println(monthDiff);
    }

    @Test
    public void periodTest () {
//        LocalDateTime start = LocalDateTime.of(2019, 3,1,0,0,0);
//        LocalDateTime end = LocalDateTime.of(2019, 4,30,23,59,59);
//        LocalDateTime end = LocalDateTime.of(2019, 5,1,0,0,0);
//        Period period = Period.between(start.toLocalDate(), end.toLocalDate());
        LocalDate start = LocalDate.of(2019, 4, 1);
        LocalDate end = LocalDate.of(2019, 5, 31);
        Period period = Period.between(start, end);
        System.out.println(period.getYears() + "-" + period.getMonths() + "-" + period.getDays());
    }

    @Test
    public void timeNowTest () {
        System.out.println(System.currentTimeMillis() / 1000);
        System.out.println(String.valueOf(LocalDateTime.now().toEpochSecond(ZoneId.systemDefault().getRules().getOffset(Instant.now()))));
    }
}
