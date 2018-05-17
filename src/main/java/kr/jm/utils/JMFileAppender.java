package kr.jm.utils;

import kr.jm.utils.exception.JMExceptionManager;
import kr.jm.utils.helper.JMFiles;
import kr.jm.utils.helper.JMLog;
import kr.jm.utils.helper.JMPath;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The type Jm file appender.
 */
public class JMFileAppender implements AutoCloseable {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
            .getLogger(JMFileAppender.class);
    private Writer writer;
    private Path filePath;

    /**
     * Instantiates a new Jm file appender.
     *
     * @param filePath the file path
     */
    public JMFileAppender(String filePath) {
        this(JMPath.getPath(filePath));
    }

    /**
     * Instantiates a new Jm file appender.
     *
     * @param filePath the file path
     */
    public JMFileAppender(Path filePath) {
        this(filePath, UTF_8);
    }

    /**
     * Instantiates a new Jm file appender.
     *
     * @param filePath the file path
     * @param charset  the charset
     */
    public JMFileAppender(String filePath, Charset charset) {
        this(JMPath.getPath(filePath), charset);
    }

    /**
     * Instantiates a new Jm file appender.
     *
     * @param filePath the file path
     * @param charset  the charset
     */
    public JMFileAppender(Path filePath, Charset charset) {
        JMLog.info(log, "JMFileAppender.new", filePath, charset);
        this.writer =
                JMFiles.buildBufferedAppendWriter(filePath, charset);
        this.filePath = filePath;
    }

    /**
     * Append jm file appender.
     *
     * @param string the string
     * @return the jm file appender
     */
    public JMFileAppender append(String string) {
        JMFiles.append(this.writer, string);
        return this;
    }

    /**
     * Append line jm file appender.
     *
     * @param line the line
     * @return the jm file appender
     */
    public JMFileAppender appendLine(String line) {
        JMFiles.appendLine(this.writer, line);
        return this;
    }

    /**
     * Append and close path.
     *
     * @param filePath      the file path
     * @param charset       the charset
     * @param writingString the writing string
     * @return the path
     */
    public static Path appendAndClose(String filePath, Charset charset,
            String writingString) {
        return new JMFileAppender(filePath, charset).append(writingString)
                .closeAndGetFilePath();
    }

    /**
     * Close and get file path path.
     *
     * @return the path
     */
    public Path closeAndGetFilePath() {
        close();
        return filePath;
    }

    /**
     * Append lines and close path.
     *
     * @param filePath the file path
     * @param lines    the lines
     * @return the path
     */
    public static Path appendLinesAndClose(String filePath, String... lines) {
        return appendLinesAndClose(filePath, UTF_8, lines);
    }

    /**
     * Append lines and close path.
     *
     * @param filePath       the file path
     * @param lineCollection the line collection
     * @return the path
     */
    public static Path appendLinesAndClose(String filePath,
            Collection<String> lineCollection) {
        return appendLinesAndClose(filePath, UTF_8, lineCollection);
    }

    /**
     * Append lines and close path.
     *
     * @param filePath the file path
     * @param charset  the charset
     * @param lines    the lines
     * @return the path
     */
    public static Path appendLinesAndClose(String filePath, Charset charset,
            String... lines) {
        return appendLineStreamAndClose(filePath, charset,
                Arrays.stream(lines));
    }

    /**
     * Append lines and close path.
     *
     * @param filePath       the file path
     * @param charset        the charset
     * @param lineCollection the line collection
     * @return the path
     */
    public static Path appendLinesAndClose(String filePath, Charset charset,
            Collection<String> lineCollection) {
        return appendLineStreamAndClose(filePath, charset,
                lineCollection.stream());
    }

    private static Path appendLineStreamAndClose(String filePath,
            Charset charset, Stream<String> lineStream) {
        return appendLineStreamAndClose(new JMFileAppender(filePath, charset),
                lineStream);
    }

    private static Path appendLineStreamAndClose(JMFileAppender jmFileAppender,
            Stream<String> lineStream) {
        lineStream.forEach(jmFileAppender::appendLine);
        return jmFileAppender.closeAndGetFilePath();
    }

    /**
     * Gets file path.
     *
     * @return the file path
     */
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            JMExceptionManager.logException(log, e, "close");
        }
    }

}
