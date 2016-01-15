
package kr.jm.utils.enums;

/**
 * The Enum Minutes.
 */
public enum Minutes {

	MINUTE(1), HOUR(MINUTE.getValue() * 60), DAY(HOUR.getValue() * 24), WEEK(
			DAY.getValue() * 7);

	private int minutes;

	private Minutes(int minutes) {
		this.minutes = minutes;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return minutes;
	}

}
