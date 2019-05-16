package kr.jm.utils.enums;

/**
 * The enum Date type.
 */
public enum DateType {

    /**
     * Week date type.
     */
    WEEK,
    /**
     * Day date type.
     */
    DAY,
    /**
     * Hour date type.
     */
    HOUR,
    /**
     * Minute date type.
     */
    MINUTE,
    /**
     * Second date type.
     */
    SECOND;

    /**
     * Gets milliseconds.
     *
     * @return the milliseconds
     */
    public long getMilliseconds() {
        switch (this) {
            case WEEK:
                return Milliseconds.WEEK.getValue();
            case DAY:
                return Milliseconds.DAY.getValue();
            case HOUR:
                return Milliseconds.HOUR.getValue();
            case MINUTE:
                return Milliseconds.MINUTE.getValue();
            case SECOND:
                return Milliseconds.SECOND.getValue();
            default:
                return 0;
        }
    }

    /**
     * Gets seconds.
     *
     * @return the seconds
     */
    public int getSeconds() {
        switch (this) {
            case WEEK:
                return Seconds.WEEK.getValue();
            case DAY:
                return Seconds.DAY.getValue();
            case HOUR:
                return Seconds.HOUR.getValue();
            case MINUTE:
                return Seconds.MINUTE.getValue();
            case SECOND:
                return Seconds.SECOND.getValue();
            default:
                return 0;
        }

    }
}
