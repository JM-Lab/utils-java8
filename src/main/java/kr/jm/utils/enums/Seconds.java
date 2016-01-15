
package kr.jm.utils.enums;

/**
 * The Enum Seconds.
 */
public enum Seconds {

	SECOND(1), MINUTE(SECOND.getValue() * 60), HOUR(
			MINUTE.getValue() * 60), DAY(HOUR.getValue() * 24), WEEK(
					DAY.getValue() * 7);

	private int seconds;

	private Seconds(int seconds) {
		this.seconds = seconds;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return seconds;
	}

}
