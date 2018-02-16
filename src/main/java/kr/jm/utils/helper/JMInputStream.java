package kr.jm.utils.helper;

import kr.jm.utils.exception.JMExceptionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * The Class JMInputStream.
 */
public class JMInputStream {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(JMInputStream.class);

    private static final String UTF_8 = "UTF-8";

    /**
     * To string.
     *
     * @param inputStream the input stream
     * @return the string
     */
    public static String toString(InputStream inputStream) {
        return toString(inputStream, UTF_8, new StringBuilder());
    }

    /**
     * To string.
     *
     * @param inputStream the input stream
     * @param charsetName the charsetName
     * @return the string
     */
    public static String toString(InputStream inputStream, String charsetName) {
        return toString(inputStream, charsetName, new StringBuilder());
    }

    private static String toString(InputStream inputStream, String charsetName,
            StringBuilder stringBuilder) {
        try {
            consumeInputStream(inputStream, charsetName,
                    line -> appendLine(stringBuilder, line));
            return stringBuilder.toString();
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "toString", inputStream, charsetName);
        }

    }

    private static void appendLine(StringBuilder stringBuilder, String line) {
        stringBuilder.append(line);
        stringBuilder.append(JMString.LINE_SEPARATOR);
    }

    /**
     * Read lines.
     *
     * @param inputStream the input stream
     * @param charsetName the charsetName
     * @return the list
     */
    public static List<String> readLines(InputStream inputStream,
            String charsetName) {
        List<String> stringList = new ArrayList<>();
        try {
            consumeInputStream(inputStream, charsetName, stringList::add);
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturn(log, e,
                    "readLines", Collections::emptyList, inputStream, charsetName);
        }
        return stringList;
    }

    /**
     * Read lines.
     *
     * @param inputStream the input stream
     * @return the list
     */
    public static List<String> readLines(InputStream inputStream) {
        return readLines(inputStream, UTF_8);
    }


    /**
     * Consume input stream.
     *
     * @param inputStream the input stream
     * @param charsetName the charsetName
     * @param consumer    the consumer
     */
    public static void consumeInputStream(InputStream inputStream,
            String charsetName, Consumer<String> consumer) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream, charsetName))) {
            for (String line = br.readLine(); line != null;
                    line = br.readLine())
                consumer.accept(line);
        } catch (IOException e) {
            JMExceptionManager.handleExceptionAndThrowRuntimeEx(log, e,
                    "consumeInputStream", inputStream, charsetName, consumer);
        }
    }
}
