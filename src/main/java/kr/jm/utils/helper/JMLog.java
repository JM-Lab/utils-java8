
package kr.jm.utils.helper;

import java.util.Collection;

import org.slf4j.Logger;

import kr.jm.utils.AutoStringBuilder;

/**
 * The Class JMLog.
 */
public class JMLog {

	/**
	 * Info.
	 *
	 * @param logger
	 *            the logger
	 * @param methodName
	 *            the method name
	 */
	static public void info(Logger logger, String methodName) {
		logger.info(buildMethodLogString(methodName));
	}

	/**
	 * Info.
	 *
	 * @param logger
	 *            the logger
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	static public void info(Logger logger, String methodName,
			Object... params) {
		logger.info(buildMethodLogString(methodName, params));
	}

	/**
	 * Info and debug.
	 *
	 * @param logger
	 *            the logger
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	static public void infoAndDebug(Logger logger, String methodName,
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
			logger.info(buildMethodLogString(methodName, newParams));
		logger.debug(buildMethodLogString(methodName, params));
	}

	/**
	 * Error.
	 *
	 * @param logger
	 *            the logger
	 * @param methodName
	 *            the method name
	 */
	static public void error(Logger logger, String methodName) {
		logger.error(buildMethodLogString(methodName));
	}

	/**
	 * Error.
	 *
	 * @param logger
	 *            the logger
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	static public void error(Logger logger, String methodName,
			Object... params) {
		logger.error(buildMethodLogString(methodName, params));
	}

	/**
	 * Error for exception.
	 *
	 * @param logger
	 *            the logger
	 * @param throwable
	 *            the throwable
	 * @param methodName
	 *            the method name
	 */
	static public void errorForException(Logger logger, Throwable throwable,
			String methodName) {
		logger.error(buildMethodLogString(methodName), throwable);
	}

	/**
	 * Error for exception.
	 *
	 * @param logger
	 *            the logger
	 * @param throwable
	 *            the throwable
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	static public void errorForException(Logger logger, Throwable throwable,
			String methodName, Object... params) {
		logger.error(buildMethodLogString(methodName, params), throwable);
	}

	private static String buildMethodLogString(String methodName) {
		return methodName + "()";
	}

	private static String buildMethodLogString(String methodName,
			Object[] params) {
		AutoStringBuilder loggerASB = new AutoStringBuilder(", ");
		loggerASB.getStringBuilder().append(methodName).append("(");
		for (Object param : params) {
			if (param == null)
				loggerASB.append("null");
			else
				loggerASB.append(param.toString());
		}
		String finalLogString = loggerASB.removeLastAutoAppendingString()
				.getStringBuilder().append(")").toString();
		return finalLogString;
	}

	/**
	 * Debug.
	 *
	 * @param logger
	 *            the logger
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	public static void debug(Logger logger, String methodName,
			Object... params) {
		logger.debug(buildMethodLogString(methodName, params));
	}

	/**
	 * Debug.
	 *
	 * @param logger
	 *            the logger
	 * @param methodName
	 *            the method name
	 */
	public static void debug(Logger logger, String methodName) {
		logger.debug(buildMethodLogString(methodName));
	}

	/**
	 * Warn.
	 *
	 * @param logger
	 *            the logger
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 */
	public static void warn(Logger logger, String methodName,
			Object... params) {
		logger.warn(buildMethodLogString(methodName, params));
	}

	/**
	 * Warn.
	 *
	 * @param logger
	 *            the logger
	 * @param methodName
	 *            the method name
	 */
	public static void warn(Logger logger, String methodName) {
		logger.warn(buildMethodLogString(methodName));
	}

}
