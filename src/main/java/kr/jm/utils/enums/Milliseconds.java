
package kr.jm.utils.enums;

/**
 * The Enum Milliseconds.
 */
public enum Milliseconds {

	SECOND(1000l), MINUTE(SECOND.getValue() * 60), HOUR(
			MINUTE.getValue() * 60), DAY(HOUR.getValue() * 24), WEEK(
					DAY.getValue() * 7);

	private long milliseconds;

	private Milliseconds(long milliseconds) {
		this.milliseconds = milliseconds;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public long getValue() {
		return milliseconds;
	}

}
