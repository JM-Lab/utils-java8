
package kr.jm.utils.helper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import kr.jm.utils.exception.JMExceptionManager;

/**
 * The Class JMFile.
 */
public class JMFile {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(JMFile.class);

	/**
	 * Write string.
	 *
	 * @param inputString
	 *            the input string
	 * @param targetFile
	 *            the target file
	 * @return true, if successful
	 */
	public static boolean writeString(String inputString, File targetFile) {
		if (!targetFile.exists()) {
			try {
				Files.write(targetFile.toPath(), inputString.getBytes());
			} catch (IOException e) {
				return JMExceptionManager.handleExceptionAndReturnFalse(log, e,
						"writeString", inputString, targetFile);
			}
		}
		return true;
	}

	/**
	 * Read string.
	 *
	 * @param targetFile
	 *            the target file
	 * @return the string
	 */
	public static String readString(File targetFile) {
		try {
			return new String(Files.readAllBytes(targetFile.toPath()));
		} catch (IOException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"readString", targetFile);
		}
	}

	/**
	 * Read string.
	 *
	 * @param targetFile
	 *            the target file
	 * @param encoding
	 *            the encoding
	 * @return the string
	 */
	public static String readString(File targetFile, String encoding) {
		try {
			return new String(Files.readAllBytes(targetFile.toPath()),
					Charset.forName(encoding));
		} catch (IOException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"readString", targetFile);
		}
	}

	/**
	 * Read string.
	 *
	 * @param filePath
	 *            the file path
	 * @return the string
	 */
	public static String readString(String filePath) {
		return readString(getFile(filePath));
	}

	/**
	 * Read string.
	 *
	 * @param filePath
	 *            the file path
	 * @param encoding
	 *            the encoding
	 * @return the string
	 */
	public static String readString(String filePath, String encoding) {
		return readString(getFile(filePath), encoding);
	}

	/**
	 * Read lines.
	 *
	 * @param targetFile
	 *            the target file
	 * @return the list
	 */
	public static List<String> readLines(File targetFile) {
		try {
			return Files.readAllLines(targetFile.toPath());
		} catch (IOException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"readLines", targetFile);
		}
	}

	/**
	 * Read lines.
	 *
	 * @param targetFile
	 *            the target file
	 * @param encoding
	 *            the encoding
	 * @return the list
	 */
	public static List<String> readLines(File targetFile, String encoding) {
		try {
			return Files.readAllLines(targetFile.toPath(),
					Charset.forName(encoding));
		} catch (IOException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"readLines", targetFile);
		}
	}

	/**
	 * Read lines.
	 *
	 * @param filePath
	 *            the file path
	 * @return the list
	 */
	public static List<String> readLines(String filePath) {
		return readLines(getFile(filePath));
	}

	/**
	 * Read lines.
	 *
	 * @param filePath
	 *            the file path
	 * @param encoding
	 *            the encoding
	 * @return the list
	 */
	public static List<String> readLines(String filePath, String encoding) {
		return readLines(getFile(filePath), encoding);
	}

	private static File getFile(String filePath) {
		return new File(filePath);
	}

	/**
	 * Gets the extension.
	 *
	 * @param file
	 *            the file
	 * @return the extension
	 */
	public static String getExtension(File file) {
		return JMString.getExtension(file.getName());
	}

	/**
	 * Gets the prefix.
	 *
	 * @param file
	 *            the file
	 * @return the prefix
	 */
	public static String getPrefix(File file) {
		return JMString.getPrefixOfFileName(file.getName());
	}

	/**
	 * Gets the prefix suffix.
	 *
	 * @param file
	 *            the file
	 * @return the prefix suffix
	 */
	public static String[] getPrefixSuffix(File file) {
		return JMString.splitFileNameIntoPreSuffix(file.getName());
	}

	/**
	 * Creates the temp file.
	 *
	 * @param file
	 *            the file
	 * @return the file
	 */
	public static File createTempFile(File file) {
		String[] prefixSuffix = getPrefixSuffix(file);
		try {
			File tempFile = File.createTempFile(prefixSuffix[0],
					prefixSuffix[1]);
			tempFile.deleteOnExit();
			return tempFile;
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"createTempFile", file);
		}
	}

}
