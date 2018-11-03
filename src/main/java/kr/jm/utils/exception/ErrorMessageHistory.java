
package kr.jm.utils.exception;

/**
 * The type Error message history.
 */
public class ErrorMessageHistory {

	private long timestamp;
	private String errorMessage;

    /**
     * Instantiates a new Error message history.
     *
     * @param timestamp    the timestamp
     * @param errorMessage the error message
     */
    public ErrorMessageHistory(long timestamp, String errorMessage) {
		super();
		this.timestamp = timestamp;
		this.errorMessage = errorMessage;
	}

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public long getTimestamp() {
		return timestamp;
	}

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
		return errorMessage;
	}

}
