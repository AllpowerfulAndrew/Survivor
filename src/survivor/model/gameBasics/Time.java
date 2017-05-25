package survivor.model.gameBasics;

import survivor.model.gameConstants.DayTimeStatus;

public abstract class Time implements DayTimeStatus {
    private static String dayTime = DAWN;

    private static int DAY = 1;
    private static int HOUR = 8;

    private static int ACTUAL_HOUR = 8;
    private static int ACTUAL_MINUTE = 0;

    private static final int STEP = 50;

    public static void increaseTime() {
        if (Game.isTimingOn) increaseMinute((int) ((Math.random() + 1) * (Game.difficulty * STEP) / 2 + ACTUAL_MINUTE));
    }

    private static void increaseMinute(int minute) {
        if (minute >= 60) {
            increaseHour();
            ACTUAL_MINUTE = minute % 60;
        } else ACTUAL_MINUTE = minute;
    }

    private static void increaseHour() {
        if (ACTUAL_HOUR + 1 > 23) ACTUAL_HOUR = 0;
        else ACTUAL_HOUR++;

        HOUR++;
        DAY = HOUR / 24 + 1;

        setDayStatus();
    }

    private static void setDayStatus() {
        if (ACTUAL_HOUR > 2 && ACTUAL_HOUR < 6) dayTime = EARLY_MORNING;
        if (ACTUAL_HOUR > 5 && ACTUAL_HOUR < 9) dayTime = DAWN;
        if (ACTUAL_HOUR > 8 && ACTUAL_HOUR < 12) dayTime = LATE_MORNING;
        if (ACTUAL_HOUR > 11 && ACTUAL_HOUR < 15) dayTime = NOON;
        if (ACTUAL_HOUR > 14 && ACTUAL_HOUR < 18) dayTime = EVENING;
        if (ACTUAL_HOUR > 17 && ACTUAL_HOUR < 21) dayTime = DUSK;
        if (ACTUAL_HOUR > 20 && ACTUAL_HOUR < 24) dayTime = NIGHT;
        if (ACTUAL_HOUR >= 0 && ACTUAL_HOUR < 3) dayTime = MIDNIGHT;
    }

    public static String getDayTime() {
        return dayTime;
    }

    public static String getTime() {
        String hour = String.valueOf(ACTUAL_HOUR);
        String minute = String.valueOf(ACTUAL_MINUTE);

        if (hour.length() == 1) hour = 0 + hour;
        if (minute.length() == 1) minute = 0 + minute;

        return hour + ":" + minute;
    }

    public static int getHour() {
        return ACTUAL_HOUR;
    }

    public static String getDay() {
        return String.valueOf(DAY);
    }

    public static int getAllHours() {
        return HOUR;
    }
}
