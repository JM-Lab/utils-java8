
package kr.jm.utils.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import kr.jm.utils.exception.JMExceptionManager;

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
	 * @param inputStream
	 *            the input stream
	 * @return the string
	 */
	public static String toString(InputStream inputStream) {
		return toString(inputStream, UTF_8, new StringBuilder());
	}

	/**
	 * To string.
	 *
	 * @param inputStream
	 *            the input stream
	 * @param encoding
	 *            the encoding
	 * @return the string
	 */
	public static String toString(InputStream inputStream, String encoding) {
		return toString(inputStream, encoding, new StringBuilder());
	}

	private static String toString(InputStream inputStream, String encoding,
			StringBuilder stringBuilder) {
		try {
			consumeInputStream(inputStream, encoding,
					line -> appendLine(stringBuilder, line));
			return stringBuilder.toString();
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"toString", inputStream, encoding);
		}

	}

	private static void appendLine(StringBuilder stringBuilder, String line) {
		stringBuilder.append(line);
		stringBuilder.append(JMString.LINE_SEPERATOR);
	}

	/**
	 * Read lines.
	 *
	 * @param inputStream
	 *            the input stream
	 * @param encoding
	 *            the encoding
	 * @return the list
	 */
	public static List<String> readLines(InputStream inputStream,
			String encoding) {
		return readLines(inputStream, encoding, new ArrayList<String>());
	}

	/**
	 * Read lines.
	 *
	 * @param inputStream
	 *            the input stream
	 * @return the list
	 */
	public static List<String> readLines(InputStream inputStream) {
		return readLines(inputStream, UTF_8, new ArrayList<String>());
	}

	private static List<String> readLines(InputStream inputStream,
			String encoding, List<String> stringList) {
		try {
			consumeInputStream(inputStream, encoding,
					line -> stringList.add(line));
			return stringList;
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"readLines", inputStream, encoding);
		}

	}

	/**
	 * Consume input stream.
	 *
	 * @param inputStream
	 *            the input stream
	 * @param encoding
	 *            the encoding
	 * @param consumer
	 *            the consumer
	 */
	public static void consumeInputStream(InputStream inputStream,
			String encoding, Consumer<String> consumer) {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(inputStream, encoding))) {
			for (String line = br.readLine(); line != null; line =
					br.readLine())
				consumer.accept(line);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
