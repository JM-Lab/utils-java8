
package kr.jm.utils.exception;

/**
 * The Class ErrorMessageHistory.
 */
public class ErrorMessageHistory {

	private long timestamp;
	private String errorMessage;

	/**
	 * Instantiates a new error message history.
	 *
	 * @param timestamp
	 *            the timestamp
	 * @param errorMessage
	 *            the error message
	 */
	public ErrorMessageHistory(long timestamp, String errorMessage) {
		super();
		this.timestamp = timestamp;
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}
