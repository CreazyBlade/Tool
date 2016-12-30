package com.qys.tools;

import java.util.Calendar;

/**
 * Created by Yishuai on 2016/6/22.
 */
public class TimeUtil {

    public static int getMonthDays(int year, int month) {
        int days = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                if ((year % 100 != 0) && (year % 4 == 0))
                    days = 29;
                else if (year % 400 == 0)
                    days = 29;
                else if (year % 3200 == 0 && year % 172800 == 0)
                    days = 29;
                else
                    days = 28;
                break;
            default:
                break;
        }
        return days;
    }

    public static int getFirstDayWeek(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
