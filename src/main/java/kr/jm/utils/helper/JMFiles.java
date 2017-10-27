
package kr.jm.utils.helper;

import kr.jm.utils.exception.JMExceptionManager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * The Class JMFiles.
 */
public class JMFiles {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(JMFiles.class);

    /**
     * Write string.
     *
     * @param inputString the input string
     * @param targetFile  the target file
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
     * @param targetFile the target file
     * @return the string
     */
    public static String readString(File targetFile) {
        return readString(targetFile);
    }

    private static String readString(Path targetPath) {
        try {
            return new String(Files.readAllBytes(targetPath));
        } catch (IOException e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "readString", targetPath);
        }
    }

    /**
     * Read string.
     *
     * @param targetFile the target file
     * @param encoding   the encoding
     * @return the string
     */
    public static String readString(File targetFile, String encoding) {
        return readString(targetFile, encoding);
    }

    private static String readString(Path targetPath, String encoding) {
        try {
            return new String(Files.readAllBytes(targetPath),
                    Charset.forName(encoding));
        } catch (IOException e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "readString", encoding);
        }
    }

    /**
     * Read string.
     *
     * @param filePath the file path
     * @return the string
     */
    public static String readString(String filePath) {
        return readString(getPath(filePath));
    }

    /**
     * Read string.
     *
     * @param filePath the file path
     * @param encoding the encoding
     * @return the string
     */
    public static String readString(String filePath, String encoding) {
        return readString(getPath(filePath), encoding);
    }

    /**
     * Read lines.
     *
     * @param targetFile the target file
     * @return the list
     */
    public static List<String> readLines(File targetFile) {
        return readLines(targetFile);
    }

    private static List<String> readLines(Path targetPath) {
        try {
            return Files.readAllLines(targetPath);
        } catch (IOException e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "readLines", targetPath);
        }
    }

    /**
     * Read lines.
     *
     * @param targetFile the target file
     * @param encoding   the encoding
     * @return the list
     */
    public static List<String> readLines(File targetFile, String encoding) {
        return readLines(targetFile.toPath(), encoding);
    }

    public static List<String> readLines(Path targetPath, String encoding) {
        try {
            return Files.readAllLines(targetPath, Charset.forName(encoding));
        } catch (IOException e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "readLines", targetPath, encoding);
        }
    }

    /**
     * Read lines.
     *
     * @param filePath the file path
     * @return the list
     */
    public static List<String> readLines(String filePath) {
        return readLines(getPath(filePath));
    }

    /**
     * Read lines.
     *
     * @param filePath the file path
     * @param encoding the encoding
     * @return the list
     */
    public static List<String> readLines(String filePath, String encoding) {
        return readLines(getPath(filePath), encoding);
    }

    private static Path getPath(String filePath) {
        return FileSystems.getDefault().getPath(filePath);
    }

    /**
     * Gets the extension.
     *
     * @param file the file
     * @return the extension
     */
    public static String getExtension(File file) {
        return JMString.getExtension(file.getName());
    }

    /**
     * Gets the prefix.
     *
     * @param file the file
     * @return the prefix
     */
    public static String getPrefix(File file) {
        return JMString.getPrefixOfFileName(file.getName());
    }

    /**
     * Gets the prefix suffix.
     *
     * @param file the file
     * @return the prefix suffix
     */
    public static String[] getPrefixSuffix(File file) {
        return JMString.splitFileNameIntoPreSuffix(file.getName());
    }

    /**
     * Creates the temp file.
     *
     * @param file the file
     * @return the file
     */
    public static File createTempFile(File file) {
        String[] prefixSuffix = getPrefixSuffix(file);
        try {
            File tempFile =
                    File.createTempFile(prefixSuffix[0], prefixSuffix[1]);
            tempFile.deleteOnExit();
            return tempFile;
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "createTempFile", file);
        }
    }

    public static Stream<String> getLineStream(String filePath) {
        return getLineStream(getPath(filePath));
    }

    public static Stream<String> getLineStream(String filePath, Charset
            charset) {
        return getLineStream(getPath(filePath), charset);
    }

    public static Stream<String> getLineStream(Path path) {
        return getLineStream(path, null);
    }

    public static Stream<String> getLineStream(Path path, Charset charset) {
        try {
            return charset == null ? Files.lines(path) : Files
                    .lines(path, charset);
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturn(log, e,
                    "getLineStream", Stream::empty, path, charset);
        }
    }

}
