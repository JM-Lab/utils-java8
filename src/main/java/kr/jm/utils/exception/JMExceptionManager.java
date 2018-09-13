package kr.jm.utils.exception;

import kr.jm.utils.AutoStringBuilder;
import kr.jm.utils.helper.JMLog;
import kr.jm.utils.helper.JMResources;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static kr.jm.utils.helper.JMString.LINE_SEPARATOR;

/**
 * The type Jm exception manager.
 */
public class JMExceptionManager {

    private static final String ERROR_HISTORY_SIZE = "error.history.size";
    private static final int maxQueueSize =
            new Integer(Optional.ofNullable(JMResources.getSystemProperty
                    (ERROR_HISTORY_SIZE)).orElse("500"));
    private static final List<ErrorMessageHistory> errorMessageHistoryList =
            new LinkedList<>();
    private static long errorCount = 0;

    /**
     * Log exception.
     *
     * @param log        the log
     * @param throwable  the throwable
     * @param methodName the method name
     * @param params     the params
     */
    public static void handleException(Logger log, Throwable throwable,
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
     * Gets error message history list.
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
        removeErrorMessageHistoryList();
        resetErrorCount();
    }

    /**
     * Remove error message history list.
     */
    synchronized public static void removeErrorMessageHistoryList() {
        errorMessageHistoryList.clear();
    }

    /**
     * Gets error count.
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
     * Handle exception and return null t.
     *
     * @param <T>       the type parameter
     * @param log       the log
     * @param throwable the throwable
     * @param method    the method
     * @param params    the params
     * @return the t
     */
    public static <T> T handleExceptionAndReturnNull(Logger log,
            Throwable throwable, String method, Object... params) {
        handleException(log, throwable, method, params);
        return null;
    }

    /**
     * Handle exception and return false boolean.
     *
     * @param log       the log
     * @param throwable the throwable
     * @param method    the method
     * @param params    the params
     * @return the boolean
     */
    public static boolean handleExceptionAndReturnFalse(Logger log,
            Throwable throwable, String method, Object... params) {
        handleException(log, throwable, method, params);
        return false;
    }

    /**
     * Handle exception and return t.
     *
     * @param <T>            the type parameter
     * @param log            the log
     * @param throwable      the throwable
     * @param method         the method
     * @param returnSupplier the return supplier
     * @param params         the params
     * @return the t
     */
    public static <T> T handleExceptionAndReturn(Logger log,
            Throwable throwable, String method, Supplier<T> returnSupplier,
            Object... params) {
        handleException(log, throwable, method, params);
        return returnSupplier.get();
    }

    /**
     * Handle exception and throw runtime ex t.
     *
     * @param <T>       the type parameter
     * @param log       the log
     * @param throwable the throwable
     * @param method    the method
     * @param params    the params
     * @return the t
     */
    public static <T> T handleExceptionAndThrowRuntimeEx(Logger log,
            Throwable throwable, String method, Object... params) {
        handleException(log, throwable, method, params);
        throw new RuntimeException(throwable);
    }

    /**
     * Handle exception and return runtime ex runtime exception.
     *
     * @param log       the log
     * @param throwable the throwable
     * @param method    the method
     * @param params    the params
     * @return the runtime exception
     */
    public static RuntimeException handleExceptionAndReturnRuntimeEx(Logger log,
            Throwable throwable, String method, Object... params) {
        handleException(log, throwable, method, params);
        return new RuntimeException(throwable);
    }

    /**
     * Handle exception and return empty optional optional.
     *
     * @param <T>       the type parameter
     * @param log       the log
     * @param throwable the throwable
     * @param method    the method
     * @param params    the params
     * @return the optional
     */
    public static <T> Optional<T> handleExceptionAndReturnEmptyOptional(
            Logger log, Throwable throwable, String method, Object... params) {
        handleException(log, throwable, method, params);
        return Optional.empty();
    }

    /**
     * Gets dont support method runtime exception.
     *
     * @param methodName the method name
     * @return the dont support method runtime exception
     */
    public static RuntimeException getDontSupportMethodRuntimeException(
            String methodName) {
        return newRunTimeException(
                "Don't Support " + methodName + " Method !!!");
    }

    /**
     * Log runtime exception.
     *
     * @param log              the log
     * @param exceptionMessage the exception message
     * @param method           the method
     * @param params           the params
     */
    public static void logRuntimeException(Logger log, String exceptionMessage,
            String method, Object... params) {
        handleException(log, newRunTimeException(exceptionMessage), method,
                params);
    }

    /**
     * New run time exception runtime exception.
     *
     * @param exceptionMessage the exception message
     * @return the runtime exception
     */
    public static RuntimeException newRunTimeException(
            String exceptionMessage) {
        return new RuntimeException(exceptionMessage);
    }

    /**
     * Throw run time exception t.
     *
     * @param <T>              the type parameter
     * @param exceptionMessage the exception message
     * @return the t
     */
    public static <T> T throwRunTimeException(String exceptionMessage) {
        throw newRunTimeException(exceptionMessage);
    }
}
