package com.yzk.launcher.custom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 农历
 * Created by yzk on 2020/8/17
 */
public class Lunar {
    private static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    private static final String[] chineseNumber = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "腊"};
    private static final String[] chineseNumber1 = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    private static final long[] lunarInfo = {19416, 19168, 42352, 21717, 53856, 55632, 91476, 22176, 39632, 21970, 19168, 42422, 42192, 53840, 119381, 46400, 54944, 44450, 38320, 84343, 18800, 42160, 46261, 27216, 27968, 109396, 11104, 38256, 21234, 18800, 25958, 54432, 59984, 28309, 23248, 11104, 100067, 37600, 116951, 51536, 54432, 120998, 46416, 22176, 107956, 9680, 37584, 53938, 43344, 46423, 27808, 46416, 86869, 19872, 42448, 83315, 21200, 43432, 59728, 27296, 44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632, 23383, 22176, 38608, 19925, 19152, 42192, 54484, 53840, 54616, 46400, 46496, 103846, 38320, 18864, 43380, 42160, 45690, 27216, 27968, 44870, 43872, 38256, 19189, 18800, 25776, 29859, 59984, 27480, 21952, 43872, 38613, 37600, 51552, 55636, 54432, 55888, 30034, 22176, 43959, 9680, 37584, 51893, 43344, 46240, 47780, 44368, 21977, 19360, 42416, 86390, 21168, 43312, 31060, 27296, 44368, 23378, 19296, 42726, 42208, 53856, 60005, 54576, 23200, 30371, 38608, 19415, 19152, 42192, 118966, 53840, 54560, 56645, 46496, 22224, 21938, 18864, 42359, 42160, 43600, 111189, 27936, 44448};
    private int day;
    private boolean leap;
    private int month;
    private int year;

    private static int yearDays(int y) {
        int sum = 348;
        for (int i = 32768; i > 8; i >>= 1) {
            if ((lunarInfo[y - 1900] & ((long) i)) != 0) {
                sum++;
            }
        }
        return leapDays(y) + sum;
    }

    private static int leapDays(int y) {
        if (leapMonth(y) == 0) {
            return 0;
        }
        if ((lunarInfo[y - 1900] & 65536) != 0) {
            return 30;
        }
        return 29;
    }

    private static int leapMonth(int y) {
        return (int) (lunarInfo[y - 1900] & 15);
    }

    private static int monthDays(int y, int m) {
        if ((lunarInfo[y - 1900] & ((long) (65536 >> m))) == 0) {
            return 29;
        }
        return 30;
    }

    public final String animalsYear() {
        return new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"}[(this.year - 4) % 12];
    }

    private static String cyclicalm(int num) {
        return String.valueOf(new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"}[num % 10]) + new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"}[num % 12];
    }

    public final String cyclical() {
        return cyclicalm((this.year - 1900) + 36);
    }

    public Lunar(Calendar cal) {
        Date baseDate = null;
        try {
            baseDate = chineseDateFormat.parse("1900年1月31日");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int offset = (int) ((cal.getTime().getTime() - baseDate.getTime()) / 86400000);
        int i = offset + 40;
        int monCyl = 14;
        int daysOfYear = 0;
        int iYear = 1900;
        while (iYear < 2050 && offset > 0) {
            daysOfYear = yearDays(iYear);
            offset -= daysOfYear;
            monCyl += 12;
            iYear++;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
            monCyl -= 12;
        }
        this.year = iYear;
        int i2 = iYear - 1864;
        int leapMonth = leapMonth(iYear);
        this.leap = false;
        int daysOfMonth = 0;
        int iMonth = 1;
        while (iMonth < 13 && offset > 0) {
            if (leapMonth <= 0 || iMonth != leapMonth + 1 || this.leap) {
                daysOfMonth = monthDays(this.year, iMonth);
            } else {
                iMonth--;
                this.leap = true;
                daysOfMonth = leapDays(this.year);
            }
            offset -= daysOfMonth;
            if (this.leap && iMonth == leapMonth + 1) {
                this.leap = false;
            }
            if (!this.leap) {
                monCyl++;
            }
            iMonth++;
        }
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (this.leap) {
                this.leap = false;
            } else {
                this.leap = true;
                iMonth--;
                monCyl--;
            }
        }
        if (offset < 0) {
            offset += daysOfMonth;
            iMonth--;
            int monCyl2 = monCyl - 1;
        }
        this.month = iMonth;
        this.day = offset + 1;
    }

    public static String getChinaDayString(int day2) {
//        String[] chineseTen = {"初", "十", "廿", "卅"};
        String[] chineseTen = {"初", "十", "二十", "三十"};
        int n = day2 % 10 == 0 ? 9 : (day2 % 10) - 1;
        if (day2 > 30) {
            return "";
        }
        if (day2 == 10) {
            return "初十";
        }
        return String.valueOf(chineseTen[day2 / 10]) + chineseNumber1[n];
    }

    public String toString() {
        return String.valueOf(this.leap ? "闰" : "") + chineseNumber[this.month - 1] + "月" + getChinaDayString(this.day);
    }

    public String getChinaWeekdayString(String weekday) {
        if (weekday.equals("Mon")) {
            return "周一";
        }
        if (weekday.equals("Tue")) {
            return "周二";
        }
        if (weekday.equals("Wed")) {
            return "周三";
        }
        if (weekday.equals("Thu")) {
            return "周四";
        }
        if (weekday.equals("Fri")) {
            return "周五";
        }
        if (weekday.equals("Sat")) {
            return "周六";
        }
        if (weekday.equals("Sun")) {
            return "周日";
        }
        return "";
    }
}
