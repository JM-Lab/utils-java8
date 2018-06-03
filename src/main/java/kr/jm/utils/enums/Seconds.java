
package kr.jm.utils.enums;

/**
 * The enum Seconds.
 */
public enum Seconds {

    /**
     * Second seconds.
     */
    SECOND(1),
    /**
     * Minute seconds.
     */
    MINUTE(SECOND.getValue() * 60),
    /**
     * Hour seconds.
     */
    HOUR(
            MINUTE.getValue() * 60),
    /**
     * Day seconds.
     */
    DAY(
            HOUR.getValue() * 24),
    /**
     * Week seconds.
     */
    WEEK(DAY.getValue() * 7);

	private int seconds;

	Seconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
		return seconds;
	}

}
