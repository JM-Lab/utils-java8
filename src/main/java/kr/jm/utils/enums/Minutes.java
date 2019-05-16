package kr.jm.utils.enums;

/**
 * The enum Minutes.
 */
public enum Minutes {

    /**
     * Minute minutes.
     */
    MINUTE(1),
    /**
     * Hour minutes.
     */
    HOUR(MINUTE.getValue() * 60),
    /**
     * Day minutes.
     */
    DAY(HOUR.getValue() * 24),
    /**
     * Week minutes.
     */
    WEEK(
            DAY.getValue() * 7);

    private int minutes;

    Minutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return minutes;
    }

}
