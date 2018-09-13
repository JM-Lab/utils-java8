
package kr.jm.utils.helper;

import kr.jm.utils.exception.JMExceptionManager;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

/**
 * The type Jm files.
 */
public class JMFiles {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(JMFiles.class);

    /**
     * Append writer.
     *
     * @param writer the writer
     * @param string the string
     * @return the writer
     */
    public static Writer append(Writer writer, String string) {
        try {
            writer.append(string);
            writer.flush();
            return writer;
        } catch (IOException e) {
            return JMExceptionManager.handleExceptionAndReturn(log, e,
                    "append", () -> writer, string);
        }
    }

    /**
     * Append line writer.
     *
     * @param writer the writer
     * @param line   the line
     * @return the writer
     */
    public static Writer appendLine(Writer writer, String line) {
        return append(writer, line + JMString.LINE_SEPARATOR);
    }

    /**
     * Build buffered append writer writer.
     *
     * @param path the path
     * @return the writer
     */
    public static Writer buildBufferedAppendWriter(Path path) {
        return buildBufferedAppendWriter(path, StandardCharsets.UTF_8);
    }

    /**
     * Build buffered append writer writer.
     *
     * @param path    the path
     * @param charset the charset
     * @return the writer
     */
    public static Writer buildBufferedAppendWriter(Path path, Charset charset) {
        try {
            if (JMPath.notExists(path))
                JMPathOperation.createFileWithParentDirectories(path);
            return Files.newBufferedWriter(path, charset, StandardOpenOption
                    .APPEND);
        } catch (IOException e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "buildBufferedAppendWriter", path, charset);
        }
    }

    /**
     * Write string boolean.
     *
     * @param inputString the input string
     * @param targetFile  the target file
     * @return the boolean
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
     * Read string string.
     *
     * @param targetFile the target file
     * @return the string
     */
    public static String readString(File targetFile) {
        return readString(targetFile.toPath());
    }

    /**
     * Read string string.
     *
     * @param targetPath the target path
     * @return the string
     */
    public static String readString(Path targetPath) {
        try {
            return new String(Files.readAllBytes(targetPath));
        } catch (IOException e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "readString", targetPath);
        }
    }

    /**
     * Read string string.
     *
     * @param targetFile  the target file
     * @param charsetName the charset name
     * @return the string
     */
    public static String readString(File targetFile, String charsetName) {
        return readString(targetFile.toPath(), charsetName);
    }

    /**
     * Read string string.
     *
     * @param targetPath  the target path
     * @param charsetName the charset name
     * @return the string
     */
    public static String readString(Path targetPath, String charsetName) {
        try {
            return new String(Files.readAllBytes(targetPath),
                    Charset.forName(charsetName));
        } catch (IOException e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "readString", charsetName);
        }
    }

    /**
     * Read string string.
     *
     * @param filePath the file path
     * @return the string
     */
    public static String readString(String filePath) {
        return readString(getPath(filePath));
    }

    /**
     * Read string string.
     *
     * @param filePath    the file path
     * @param charsetName the charset name
     * @return the string
     */
    public static String readString(String filePath, String charsetName) {
        return readString(getPath(filePath), charsetName);
    }

    /**
     * Read lines list.
     *
     * @param targetFile the target file
     * @return the list
     */
    public static List<String> readLines(File targetFile) {
        return readLines(targetFile.toPath());
    }

    /**
     * Read lines list.
     *
     * @param targetPath the target path
     * @return the list
     */
    public static List<String> readLines(Path targetPath) {
        try {
            return Files.readAllLines(targetPath);
        } catch (IOException e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "readLines", targetPath);
        }
    }

    /**
     * Read lines list.
     *
     * @param targetFile  the target file
     * @param charsetName the charset name
     * @return the list
     */
    public static List<String> readLines(File targetFile, String charsetName) {
        return readLines(targetFile.toPath(), charsetName);
    }

    /**
     * Read lines list.
     *
     * @param targetPath  the target path
     * @param charsetName the charset name
     * @return the list
     */
    public static List<String> readLines(Path targetPath, String charsetName) {
        try {
            return Files.readAllLines(targetPath, Charset.forName(charsetName));
        } catch (IOException e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "readLines", targetPath, charsetName);
        }
    }

    /**
     * Read lines list.
     *
     * @param filePath the file path
     * @return the list
     */
    public static List<String> readLines(String filePath) {
        return readLines(getPath(filePath));
    }

    /**
     * Read lines list.
     *
     * @param filePath    the file path
     * @param charsetName the charset name
     * @return the list
     */
    public static List<String> readLines(String filePath, String charsetName) {
        return readLines(getPath(filePath), charsetName);
    }

    /**
     * Gets path.
     *
     * @param filePath the file path
     * @return the path
     */
    public static Path getPath(String filePath) {
        return FileSystems.getDefault().getPath(filePath);
    }

    /**
     * Gets extension.
     *
     * @param file the file
     * @return the extension
     */
    public static String getExtension(File file) {
        return JMString.getExtension(file.getName());
    }

    /**
     * Gets prefix.
     *
     * @param file the file
     * @return the prefix
     */
    public static String getPrefix(File file) {
        return JMString.getPrefixOfFileName(file.getName());
    }

    /**
     * Get prefix suffix string [ ].
     *
     * @param file the file
     * @return the string [ ]
     */
    public static String[] getPrefixSuffix(File file) {
        return JMString.splitFileNameIntoPreSuffix(file.getName());
    }

    /**
     * Create temp file file.
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

    /**
     * Gets line stream.
     *
     * @param filePath the file path
     * @return the line stream
     */
    public static Stream<String> getLineStream(String filePath) {
        return getLineStream(getPath(filePath));
    }

    /**
     * Gets line stream.
     *
     * @param filePath the file path
     * @param charset  the charset
     * @return the line stream
     */
    public static Stream<String> getLineStream(String filePath, Charset
            charset) {
        return getLineStream(getPath(filePath), charset);
    }

    /**
     * Gets line stream.
     *
     * @param path the path
     * @return the line stream
     */
    public static Stream<String> getLineStream(Path path) {
        return getLineStream(path, null);
    }

    /**
     * Gets line stream.
     *
     * @param path    the path
     * @param charset the charset
     * @return the line stream
     */
    public static Stream<String> getLineStream(Path path, Charset charset) {
        try {
            return charset == null ? Files.lines(path) : Files
                    .lines(path, charset);
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturn(log, e,
                    "getLineStream", Stream::empty, path, charset);
        }
    }

    /**
     * Create empty file boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean createEmptyFile(File file) {
        try {
            file.getParentFile().mkdirs();
            return file.createNewFile();
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturnFalse(log, e,
                    "createEmptyFile", file);
        }

    }
}
