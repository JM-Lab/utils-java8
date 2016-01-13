package kr.jm.utils.exception;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;

import kr.jm.utils.AutoStringBuilder;
import kr.jm.utils.helper.JMLog;
import kr.jm.utils.helper.JMResources;

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
		synchronized (errorMessageHistoryList) {
			errorMessageHistoryList.add(new ErrorMessageHistory(
					System.currentTimeMillis(), getStackTraceString(e)));
			if (errorMessageHistoryList.size() > maxQueueSize)
				errorMessageHistoryList.remove(0);
		}
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

	synchronized public static void removeErrorMessgeHistoryList() {
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
			String method, Object... params) {
		logException(log, e, method, params);
		return null;
	}

	public static boolean handleExceptionAndReturnFalse(Logger log, Exception e,
			String method, Object... params) {
		logException(log, e, method, params);
		return false;
	}

	public static <T> T handleExceptionAndReturn(Logger log, Exception e,
			String method, Supplier<T> returnSupplier, Object... params) {
		logException(log, e, method, params);
		return returnSupplier.get();
	}

	public static <T> T handleExceptionAndThrowRuntimeEx(Logger log,
			Exception e, String method, Object... params) {
		logException(log, e, method, params);
		throw new RuntimeException(e);
	}

	public static <T> Optional<T> handleExceptionAndReturnEmptyOptioal(
			Logger log, Exception e, String method, Object... params) {
		return Optional.<T> empty();
	}

	public static RuntimeException getDontSupportMethodRuntimeException() {
		return new RuntimeException("Don't Support Method !!!");
	}

}
