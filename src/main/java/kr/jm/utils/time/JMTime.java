package kr.jm.utils.time;

import java.text.SimpleDateFormat;

/**
 * The type Jm time.
 */
public class JMTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    /**
     * Instantiates a new Jm time.
     *
     * @param timestamp the timestamp
     */
    public JMTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String[] timeStrings = sdf.format(timestamp).split("-");
        init(timeStrings[0], timeStrings[1], timeStrings[2], timeStrings[3],
                timeStrings[4], timeStrings[5]);
    }

    /**
     * Instantiates a new Jm time.
     *
     * @param year   the year
     * @param month  the month
     * @param day    the day
     * @param hour   the hour
     * @param minute the minute
     * @param second the second
     */
    public JMTime(String year, String month, String day, String hour,
            String minute, String second) {
        super();
        init(year, month, day, hour, minute, second);
    }

    private void init(String year, String month, String day, String hour,
            String minute, String second) {
        this.year = Integer.valueOf(year);
        this.month = Integer.valueOf(month);
        this.day = Integer.valueOf(day);
        this.hour = Integer.valueOf(hour);
        this.minute = Integer.valueOf(minute);
        this.second = Integer.valueOf(second);
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets month.
     *
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets day.
     *
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets hour.
     *
     * @return the hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gets minute.
     *
     * @return the minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Gets second.
     *
     * @return the second
     */
    public int getSecond() {
        return second;
    }

}
