package kr.jm.utils.exception;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import kr.jm.utils.AutoStringBuilder;
import kr.jm.utils.helper.JMLog;
import kr.jm.utils.io.JMResources;

import org.slf4j.Logger;

public class JMExceptionManager {

	private static final String ERROR_HISTORY_SIZE = "error.history.size";

	static {
		JMResources.setSystemPropertyIfIsNull(ERROR_HISTORY_SIZE, 500);
	}

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	private static final int maxQueueSize = new Integer(
			JMResources.getSystemProperty(ERROR_HISTORY_SIZE));

	private static List<ErrorMessageHistory> errorMessageHistoryList = new LinkedList<ErrorMessageHistory>();

	private static long errorCount = 0;

	public static void logException(Logger log, Exception e, String methodName,
			Object... params) {
		if (params.length > 0)
			JMLog.logException(log, e, methodName, params);
		else
			JMLog.logException(log, e, methodName);
		increaseErrorCount();
		recordErrorMessageHistory(e);
	}

	private static void recordErrorMessageHistory(Exception e) {
		if (errorMessageHistoryList.size() >= maxQueueSize)
			errorMessageHistoryList.remove(0);
		errorMessageHistoryList.add(new ErrorMessageHistory(System
				.currentTimeMillis(), getStackTraceString(e)));
	}

	private static String getStackTraceString(Throwable throwable) {
		AutoStringBuilder stackTraceStringBuilder = new AutoStringBuilder(
				LINE_SEPARATOR);
		stackTraceStringBuilder.append(throwable.toString());
		for (StackTraceElement stackTraceElement : throwable.getStackTrace())
			stackTraceStringBuilder.append(stackTraceElement.toString());
		return stackTraceStringBuilder.autoToString();
	}

	public static List<ErrorMessageHistory> getErrorMessageHistoryList() {
		return errorMessageHistoryList;
	}

	public static void clearAll() {
		removeErrorMessgeHistoryList();
		resetErrorCount();
	}

	public static void removeErrorMessgeHistoryList() {
		errorMessageHistoryList.clear();
	}

	public static long getErrorCount() {
		return errorCount;
	}

	public static void resetErrorCount() {
		errorCount = 0;
	}

	public static void increaseErrorCount() {
		errorCount++;
	}

	public static <T> T handleExceptionAndReturnNull(Logger log, Exception e,
			String method, Object... sources) {
		logException(log, e, method, sources);
		return null;
	}

	public static boolean handleExceptionAndReturnFalse(Logger log,
			Exception e, String method, Object... sources) {
		logException(log, e, method, sources);
		return false;
	}

	public static <T> T handleExceptionAndReturn(Logger log, Exception e,
			String method, Supplier<T> returnSupplier, Object... sources) {
		logException(log, e, method, sources);
		return returnSupplier.get();
	}

}
