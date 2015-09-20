package kr.jm.utils.exception;

public class ErrorMessageHistory {

	private long timestamp;
	private String errorMessage;

	public ErrorMessageHistory(long timestamp, String errorMessage) {
		super();
		this.timestamp = timestamp;
		this.errorMessage = errorMessage;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
