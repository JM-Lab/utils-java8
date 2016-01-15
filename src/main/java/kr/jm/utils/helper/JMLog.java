
package kr.jm.utils.helper;

import java.util.Collection;

import org.slf4j.Logger;

import kr.jm.utils.AutoStringBuilder;

/**
 * The Class JMLog.
 */
public class JMLog {

	/**
	 * Log method start info.
	 *
	 * @param log
	 *            the log
	 * @param methodName
	 *            the method name
	 */
	static public void infoBeforeStart(Logger log, String methodName) {
		log.info(buildMethodLogString(methodName));
	}

	/**
	 * Log method start info.
	 *
	 * @param log
	 *            the log
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	static public void infoBeforeStart(Logger log, String methodName,
			Object... params) {
		log.info(buildMethodLogString(methodName, params));
	}

	/**
	 * Log method start info and debug.
	 *
	 * @param log
	 *            the log
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	static public void infoAndDebugBeforeStart(Logger log, String methodName,
			Object... params) {
		int length = params.length;
		Object[] newParams = new Object[length];
		boolean hasCollection = false;
		for (int i = 0; i < length; i++) {
			if (params[i] instanceof Collection) {
				newParams[i] = (((Collection<?>) params[i]).size());
				hasCollection = true;
			} else
				newParams[i] = params[i];
		}
		if (hasCollection)
			log.info(buildMethodLogString(methodName, newParams));
		log.debug(buildMethodLogString(methodName, params));
	}

	/**
	 * Log exception.
	 *
	 * @param log
	 *            the log
	 * @param exception
	 *            the exception
	 * @param methodName
	 *            the method name
	 */
	static public void errorForException(Logger log, Exception exception,
			String methodName) {
		log.error(buildMethodLogString(methodName), exception);
	}

	/**
	 * Log exception.
	 *
	 * @param log
	 *            the log
	 * @param exception
	 *            the exception
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	static public void errorForException(Logger log, Exception exception,
			String methodName, Object... params) {
		log.error(buildMethodLogString(methodName, params), exception);
	}

	private static String buildMethodLogString(String methodName) {
		return methodName + "()";
	}

	private static String buildMethodLogString(String methodName,
			Object[] params) {
		AutoStringBuilder logASB = new AutoStringBuilder(", ");
		logASB.getStringBuilder().append(methodName).append("(");
		for (Object param : params) {
			if (param == null)
				logASB.append("null");
			else
				logASB.append(param.toString());
		}
		String finalLogString = logASB.removeLastAutoAppendingString()
				.getStringBuilder().append(")").toString();
		return finalLogString;
	}

	/**
	 * Log method start debug.
	 *
	 * @param log
	 *            the log
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	public static void debugBeforeStart(Logger log, String methodName,
			Object... params) {
		log.debug(buildMethodLogString(methodName, params));
	}

	/**
	 * Log method start debug.
	 *
	 * @param log
	 *            the log
	 * @param methodName
	 *            the method name
	 */
	public static void debugBeforeStart(Logger log, String methodName) {
		log.debug(buildMethodLogString(methodName));
	}

	/**
	 * Log method start warn.
	 *
	 * @param log
	 *            the log
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	public static void warnBeforeStart(Logger log, String methodName,
			Object... params) {
		log.warn(buildMethodLogString(methodName, params));
	}

	/**
	 * Warn before start.
	 *
	 * @param log
	 *            the log
	 * @param methodName
	 *            the method name
	 */
	public static void warnBeforeStart(Logger log, String methodName) {
		log.warn(buildMethodLogString(methodName));
	}

}
