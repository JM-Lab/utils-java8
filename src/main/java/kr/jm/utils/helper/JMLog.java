
package kr.jm.utils.helper;

import kr.jm.utils.AutoStringBuilder;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Collection;

/**
 * The type Jm log.
 */
public class JMLog {

    /**
     * Info.
     *
     * @param log        the log
     * @param methodName the method name
     */
    public static void info(Logger log, String methodName) {
        if (log.isInfoEnabled())
            log.info(buildMethodLogString(methodName));
    }

    /**
     * Info.
     *
     * @param log        the log
     * @param methodName the method name
     * @param params     the params
     */
    public static void info(Logger log, String methodName, Object... params) {
        if (log.isInfoEnabled())
            log.info(buildMethodLogString(methodName, params));
    }

    /**
     * Info and debug.
     *
     * @param log        the log
     * @param methodName the method name
     * @param params     the params
     */
    public static void infoAndDebug(Logger log, String methodName,
            Object... params) {
        if (!log.isInfoEnabled())
            return;
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
     * Error.
     *
     * @param log        the log
     * @param methodName the method name
     */
    public static void error(Logger log, String methodName) {
        log.error(buildMethodLogString(methodName));
    }

    /**
     * Error.
     *
     * @param log        the log
     * @param methodName the method name
     * @param params     the params
     */
    public static void error(Logger log, String methodName, Object... params) {
        log.error(buildMethodLogString(methodName, params));
    }

    /**
     * Error for exception.
     *
     * @param log        the log
     * @param throwable  the throwable
     * @param methodName the method name
     */
    public static void errorForException(Logger log, Throwable throwable,
            String methodName) {
        log.error(buildMethodLogString(methodName), throwable);
    }

    /**
     * Error for exception.
     *
     * @param log        the log
     * @param throwable  the throwable
     * @param methodName the method name
     * @param params     the params
     */
    public static void errorForException(Logger log, Throwable throwable,
            String methodName, Object... params) {
        log.error(buildMethodLogString(methodName, params), throwable);
    }

    private static String buildMethodLogString(String methodName) {
        return methodName + "()";
    }

    private static String buildMethodLogString(String methodName,
            Object[] params) {
        AutoStringBuilder loggerASB = new AutoStringBuilder(", ");
        loggerASB.getStringBuilder().append(methodName).append("(");
        for (Object param : params)
            loggerASB.append(param == null ? "null" : param.getClass()
                    .isArray() ? Arrays.toString((Object[]) param) : param
                    .toString());
        return loggerASB.removeLastAutoAppendingString()
                .getStringBuilder().append(")").toString();
    }

    /**
     * Debug.
     *
     * @param log        the log
     * @param methodName the method name
     * @param params     the params
     */
    public static void debug(Logger log, String methodName, Object... params) {
        if (log.isDebugEnabled())
            log.debug(buildMethodLogString(methodName, params));
    }

    /**
     * Debug.
     *
     * @param log        the log
     * @param methodName the method name
     */
    public static void debug(Logger log, String methodName) {
        if (log.isDebugEnabled())
            log.debug(buildMethodLogString(methodName));
    }

    /**
     * Warn.
     *
     * @param log        the log
     * @param methodName the method name
     * @param params     the params
     */
    public static void warn(Logger log, String methodName, Object... params) {
        if (log.isWarnEnabled())
            log.warn(buildMethodLogString(methodName, params));
    }

    /**
     * Warn.
     *
     * @param log        the log
     * @param methodName the method name
     */
    public static void warn(Logger log, String methodName) {
        if (log.isWarnEnabled())
            log.warn(buildMethodLogString(methodName));
    }

}
