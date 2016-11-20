
package kr.jm.utils.exception;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;

import kr.jm.utils.AutoStringBuilder;
import kr.jm.utils.helper.JMLog;
import kr.jm.utils.helper.JMResources;

/**
 * The Class JMExceptionManager.
 */
public class JMExceptionManager {

	private static final String ERROR_HISTORY_SIZE = "error.history.size";

	static {
		JMResources.setSystemPropertyIfIsNull(ERROR_HISTORY_SIZE, 500);
	}

	private static final String LINE_SEPARATOR =
			System.getProperty("line.separator");
	private static final int maxQueueSize =
			new Integer(JMResources.getSystemProperty(ERROR_HISTORY_SIZE));

	private static List<ErrorMessageHistory> errorMessageHistoryList =
			new LinkedList<ErrorMessageHistory>();

	private static long errorCount = 0;

	/**
	 * Log exception.
	 *
	 * @param log
	 *            the log
	 * @param throwable
	 *            the throwable
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	public static void logException(Logger log, Throwable throwable,
			String methodName, Object... params) {
		if (params.length > 0)
			JMLog.errorForException(log, throwable, methodName, params);
		else
			JMLog.errorForException(log, throwable, methodName);
		increaseErrorCount();
		recordErrorMessageHistory(throwable);
	}

	private static void recordErrorMessageHistory(Throwable throwable) {
		synchronized (errorMessageHistoryList) {
			errorMessageHistoryList
					.add(new ErrorMessageHistory(System.currentTimeMillis(),
							getStackTraceString(throwable)));
			if (errorMessageHistoryList.size() > maxQueueSize)
				errorMessageHistoryList.remove(0);
		}
	}

	private static String getStackTraceString(Throwable throwable) {
		AutoStringBuilder stackTraceStringBuilder =
				new AutoStringBuilder(LINE_SEPARATOR);
		stackTraceStringBuilder.append(throwable.toString());
		for (StackTraceElement stackTraceElement : throwable.getStackTrace())
			stackTraceStringBuilder.append(stackTraceElement.toString());
		return stackTraceStringBuilder.autoToString();
	}

	/**
	 * Gets the error message history list.
	 *
	 * @return the error message history list
	 */
	public static List<ErrorMessageHistory> getErrorMessageHistoryList() {
		return errorMessageHistoryList;
	}

	/**
	 * Clear all.
	 */
	public static void clearAll() {
		removeErrorMessgeHistoryList();
		resetErrorCount();
	}

	/**
	 * Removes the error messge history list.
	 */
	synchronized public static void removeErrorMessgeHistoryList() {
		errorMessageHistoryList.clear();
	}

	/**
	 * Gets the error count.
	 *
	 * @return the error count
	 */
	public static long getErrorCount() {
		return errorCount;
	}

	/**
	 * Reset error count.
	 */
	public static void resetErrorCount() {
		errorCount = 0;
	}

	/**
	 * Increase error count.
	 */
	public static void increaseErrorCount() {
		errorCount++;
	}

	/**
	 * Handle exception and return null.
	 *
	 * @param <T>
	 *            the generic type
	 * @param log
	 *            the log
	 * @param throwable
	 *            the throwable
	 * @param method
	 *            the method
	 * @param params
	 *            the params
	 * @return the t
	 */
	public static <T> T handleExceptionAndReturnNull(Logger log,
			Throwable throwable, String method, Object... params) {
		logException(log, throwable, method, params);
		return null;
	}

	/**
	 * Handle exception and return false.
	 *
	 * @param log
	 *            the log
	 * @param throwable
	 *            the throwable
	 * @param method
	 *            the method
	 * @param params
	 *            the params
	 * @return true, if successful
	 */
	public static boolean handleExceptionAndReturnFalse(Logger log,
			Throwable throwable, String method, Object... params) {
		logException(log, throwable, method, params);
		return false;
	}

	/**
	 * Handle exception and return.
	 *
	 * @param <T>
	 *            the generic type
	 * @param log
	 *            the log
	 * @param throwable
	 *            the throwable
	 * @param method
	 *            the method
	 * @param returnSupplier
	 *            the return supplier
	 * @param params
	 *            the params
	 * @return the t
	 */
	public static <T> T handleExceptionAndReturn(Logger log,
			Throwable throwable, String method, Supplier<T> returnSupplier,
			Object... params) {
		logException(log, throwable, method, params);
		return returnSupplier.get();
	}

	/**
	 * Handle exception and throw runtime ex.
	 *
	 * @param <T>
	 *            the generic type
	 * @param log
	 *            the log
	 * @param throwable
	 *            the throwable
	 * @param method
	 *            the method
	 * @param params
	 *            the params
	 * @return the t
	 */
	public static <T> T handleExceptionAndThrowRuntimeEx(Logger log,
			Throwable throwable, String method, Object... params) {
		logException(log, throwable, method, params);
		throw new RuntimeException(throwable);
	}

	/**
	 * Handle exception and return runtime ex.
	 *
	 * @param log
	 *            the log
	 * @param throwable
	 *            the throwable
	 * @param method
	 *            the method
	 * @param params
	 *            the params
	 * @return the runtime exception
	 */
	public static RuntimeException handleExceptionAndReturnRuntimeEx(Logger log,
			Throwable throwable, String method, Object... params) {
		logException(log, throwable, method, params);
		return new RuntimeException(throwable);
	}

	/**
	 * Handle exception and return empty optional.
	 *
	 * @param <T>
	 *            the generic type
	 * @param log
	 *            the log
	 * @param throwable
	 *            the throwable
	 * @param method
	 *            the method
	 * @param params
	 *            the params
	 * @return the optional
	 */
	public static <T> Optional<T> handleExceptionAndReturnEmptyOptional(
			Logger log, Throwable throwable, String method, Object... params) {
		return Optional.<T> empty();
	}

	/**
	 * Gets the dont support method runtime exception.
	 *
	 * @return the dont support method runtime exception
	 */
	public static RuntimeException getDontSupportMethodRuntimeException() {
		return new RuntimeException("Don't Support Method !!!");
	}

}
