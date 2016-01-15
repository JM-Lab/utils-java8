
package kr.jm.utils.enums;

/**
 * The Enum Period.
 */
public enum Period {
	WEEKLY, DAILY, HOURLY, MINUTELY, SECONDLY;

	private DateType getDateType() {
		switch (this) {
		case WEEKLY:
			return DateType.WEEK;
		case DAILY:
			return DateType.DAY;
		case HOURLY:
			return DateType.HOUR;
		case MINUTELY:
			return DateType.MINUTE;
		case SECONDLY:
			return DateType.SECOND;
		default:
			return null;
		}
	}

	/**
	 * Gets the period milliseconds.
	 *
	 * @return the period milliseconds
	 */
	public long getPeriodMilliseconds() {
		return this.getDateType().getMilliseconds();
	}

	/**
	 * Gets the period seconds.
	 *
	 * @return the period seconds
	 */
	public int getPeriodSeconds() {
		return this.getDateType().getSeconds();
	}

}
