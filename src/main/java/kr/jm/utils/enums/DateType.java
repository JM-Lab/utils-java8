
package kr.jm.utils.enums;

/**
 * The Enum DateType.
 */
public enum DateType {

    /**
     * Week date type.
     */
    WEEK, /**
     * Day date type.
     */
    DAY, /**
     * Hour date type.
     */
    HOUR, /**
     * Minute date type.
     */
    MINUTE, /**
     * Second date type.
     */
    SECOND;

    /**
     * Gets the milliseconds.
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
     * Gets the seconds.
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
