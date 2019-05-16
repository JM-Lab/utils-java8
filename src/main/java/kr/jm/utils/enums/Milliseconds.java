package kr.jm.utils.enums;

/**
 * The enum Milliseconds.
 */
public enum Milliseconds {

    /**
     * Second milliseconds.
     */
    SECOND(1000L),
    /**
     * Minute milliseconds.
     */
    MINUTE(SECOND.getValue() * 60),
    /**
     * Hour milliseconds.
     */
    HOUR(
            MINUTE.getValue() * 60),
    /**
     * Day milliseconds.
     */
    DAY(
            HOUR.getValue() * 24),
    /**
     * Week milliseconds.
     */
    WEEK(DAY.getValue() * 7);

    private long milliseconds;

    Milliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public long getValue() {
        return milliseconds;
    }

}
