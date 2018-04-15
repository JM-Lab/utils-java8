package kr.jm.utils.helper;

import kr.jm.utils.exception.JMExceptionManager;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * The Class JMResources.
 */
public class JMResources {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(JMResources.class);
    /**
     * The constant UTF_8_CharsetString.
     */
    public static final String UTF_8_CharsetString = "UTF-8";

    /**
     * Sets the system property if is null.
     *
     * @param key   the key
     * @param value the value
     */
    public static void setSystemPropertyIfIsNull(String key, Object value) {
        if (!System.getProperties().containsKey(key))
            System.setProperty(key, value.toString());
    }

    /**
     * Gets the system property.
     *
     * @param key the key
     * @return the system property
     */
    public static String getSystemProperty(String key) {
        return System.getProperty(key);
    }

    /**
     * Gets the resource URL.
     *
     * @param classpath the classpath
     * @return the resource URL
     */
    public static URL getResourceURL(String classpath) {
        return ClassLoader.getSystemResource(classpath);
    }

    /**
     * Gets the url.
     *
     * @param classpathOrFilePath the classpath or file path
     * @return the url
     */
    public static URL getURL(String classpathOrFilePath) {
        try {
            return getURI(classpathOrFilePath).toURL();
        } catch (MalformedURLException e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "getURL", classpathOrFilePath);
        }
    }

    /**
     * Gets the resource URI.
     *
     * @param classpath the classpath
     * @return the resource URI
     */
    public static URI getResourceURI(String classpath) {
        try {
            return getResourceURL(classpath).toURI();
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "getResourceURI", classpath);
        }
    }

    /**
     * Gets the uri.
     *
     * @param classpathOrFilePath the classpath or file path
     * @return the uri
     */
    public static URI getURI(String classpathOrFilePath) {
        return Optional.ofNullable(getResourceURI(classpathOrFilePath))
                .orElseGet(() -> new File(classpathOrFilePath).toURI());
    }

    /**
     * Gets the resource input stream.
     *
     * @param classpath the classpath
     * @return the resource input stream
     */
    public static InputStream getResourceInputStream(String classpath) {
        return ClassLoader.getSystemResourceAsStream(classpath);
    }

    /**
     * Gets the properties.
     *
     * @param classpath the classpath
     * @return the properties
     */
    public static Properties getProperties(String classpath) {
        Properties properties = new Properties();
        try (InputStream is = getResourceInputStream(classpath)) {
            properties.load(is);
            return properties;
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                    "getProperties", classpath);
        }
    }

    /**
     * Gets the properties.
     *
     * @param propertiesFile the properties file
     * @return the properties
     */
    public static Properties getProperties(File propertiesFile) {
        Properties properties = new Properties();
        try (BufferedReader reader =
                new BufferedReader(new FileReader(propertiesFile))) {
            properties.load(reader);
            return properties;
        } catch (Exception e) {
            return JMExceptionManager
                    .handleExceptionAndReturnNull(log, e, "getProperties",
                            propertiesFile);
        }
    }

    /**
     * Save properties.
     *
     * @param inProperties the in properties
     * @param saveFile     the save file
     * @param comment      the comment
     * @return true, if successful
     */
    public static boolean saveProperties(Properties inProperties, File saveFile,
            String comment) {
        try {
            if (!saveFile.exists())
                JMFiles.createEmptyFile(saveFile);
            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(saveFile));
            inProperties.store(writer, comment);
            writer.close();
            return true;
        } catch (IOException e) {
            return JMExceptionManager.handleExceptionAndReturnFalse(log, e,
                    "saveProperties", inProperties, saveFile, comment);
        }

    }

    /**
     * Read string.
     *
     * @param resourceClasspath the resource classpath
     * @return the string
     */
    public static String readString(String resourceClasspath) {
        return JMInputStream
                .toString(getResourceInputStream(resourceClasspath));
    }

    /**
     * Read string.
     *
     * @param resourceClasspath the resource classpath
     * @param charsetName       the charsetName
     * @return the string
     */
    public static String readString(String resourceClasspath,
            String charsetName) {
        return JMInputStream.toString(getResourceInputStream(resourceClasspath),
                charsetName);
    }

    /**
     * Read lines.
     *
     * @param resourceClasspath the resource classpath
     * @return the list
     */
    public static List<String> readLines(String resourceClasspath) {
        return JMInputStream
                .readLines(getResourceInputStream(resourceClasspath));
    }

    /**
     * Read lines.
     *
     * @param resourceClasspath the resource classpath
     * @param charsetName       the charsetName
     * @return the list
     */
    public static List<String> readLines(String resourceClasspath,
            String charsetName) {
        return JMInputStream
                .readLines(getResourceInputStream(resourceClasspath),
                        charsetName);
    }

    /**
     * Gets the string from classpath or file path.
     *
     * @param classpathOrFilePath the classpath or file path
     * @return the string from classpath or file path
     */
    public static String getStringWithClasspathOrFilePath(
            String classpathOrFilePath) {
        return getStringWithClasspathOrFilePath(classpathOrFilePath,
                UTF_8_CharsetString);
    }

    /**
     * Gets the string from classpath or file path.
     *
     * @param classpathOrFilePath the classpath or file path
     * @param charsetName         the charsetName
     * @return the string from classpath or file path
     */
    public static String getStringWithClasspathOrFilePath(
            String classpathOrFilePath, String charsetName) {
        return getStringAsOptWithClasspath(classpathOrFilePath, charsetName)
                .orElseGet(() -> getStringAsOptWithFilePath
                        (classpathOrFilePath, charsetName).orElse(null));
    }

    /**
     * Read lines from classpath or file path.
     *
     * @param classpathOrFilePath the classpath or file path
     * @return the list
     */
    public static List<String> readLinesWithClasspathOrFilePath(
            String classpathOrFilePath) {
        return getStringListAsOptWithClasspath(classpathOrFilePath)
                .filter(JMPredicate.getGreaterSize(0))
                .orElseGet(() -> JMFiles.readLines(classpathOrFilePath));
    }

    /**
     * Gets string list as opt with classpath.
     *
     * @param classpathOrFilePath the classpath or file path
     * @return the string list as opt with classpath
     */
    public static Optional<List<String>> getStringListAsOptWithClasspath(
            String classpathOrFilePath) {
        return getResourceInputStreamAsOpt(classpathOrFilePath)
                .map(JMInputStream::readLines);
    }

    /**
     * Read lines with file path or classpath list.
     *
     * @param filePathOrClasspath the file path or classpath
     * @return the list
     */
    public static List<String> readLinesWithFilePathOrClasspath(
            String filePathOrClasspath) {
        return JMOptional.getOptional(JMFiles.readLines(filePathOrClasspath))
                .orElseGet(() -> getStringListAsOptWithClasspath(
                        filePathOrClasspath).orElseGet(Collections::emptyList));
    }

    /**
     * Gets string with file path or classpath.
     *
     * @param filePathOrClasspath the file path or classpath
     * @param charsetName         the charset name
     * @return the string with file path or classpath
     */
    public static String getStringWithFilePathOrClasspath(
            String filePathOrClasspath, String charsetName) {
        return getStringAsOptWithFilePath(filePathOrClasspath, charsetName)
                .orElseGet(
                        () -> getStringAsOptWithClasspath(filePathOrClasspath,
                                charsetName).orElse(null));
    }

    /**
     * Gets string with file path or classpath.
     *
     * @param filePathOrClasspath the file path or classpath
     * @return the string with file path or classpath
     */
    public static String getStringWithFilePathOrClasspath(
            String filePathOrClasspath) {
        return getStringWithFilePathOrClasspath(filePathOrClasspath,
                UTF_8_CharsetString);
    }

    /**
     * Gets string as opt with file path.
     *
     * @param filePath    the file path
     * @param charsetName the charset name
     * @return the string as opt with file path
     */
    public static Optional<String> getStringAsOptWithFilePath(
            String filePath, String charsetName) {
        return JMOptional
                .getOptional(JMFiles.readString(filePath, charsetName));
    }

    /**
     * Gets string as opt with file path.
     *
     * @param filePath the file path
     * @return the string as opt with file path
     */
    public static Optional<String> getStringAsOptWithFilePath(String filePath) {
        return getStringAsOptWithFilePath(filePath, UTF_8_CharsetString);
    }

    /**
     * Gets string as opt with classpath.
     *
     * @param classpath   the classpath
     * @param charsetName the charset name
     * @return the string as opt with classpath
     */
    public static Optional<String> getStringAsOptWithClasspath(
            String classpath, String charsetName) {
        return getResourceInputStreamAsOpt(classpath)
                .map(resourceInputStream -> JMInputStream
                        .toString(resourceInputStream, charsetName));
    }

    /**
     * Gets string as opt with classpath.
     *
     * @param classpath the classpath
     * @return the string as opt with classpath
     */
    public static Optional<String> getStringAsOptWithClasspath(
            String classpath) {
        return getStringAsOptWithClasspath(classpath, UTF_8_CharsetString);
    }

    /**
     * Gets resource input stream as opt.
     *
     * @param classpath the classpath
     * @return the resource input stream as opt
     */
    public static Optional<InputStream> getResourceInputStreamAsOpt(
            String classpath) {
        return Optional.ofNullable(getResourceInputStream(classpath));
    }

    /**
     * Gets the resource bundle.
     *
     * @param baseName the base name
     * @return the resource bundle
     */
    public static ResourceBundle getResourceBundle(String baseName) {
        return ResourceBundle.getBundle(baseName);
    }

    /**
     * Gets the resource bundle.
     *
     * @param baseName     the base name
     * @param targetLocale the target locale
     * @return the resource bundle
     */
    public static ResourceBundle getResourceBundle(String baseName,
            Locale targetLocale) {
        Locale.setDefault(targetLocale);
        return ResourceBundle.getBundle(baseName);
    }
}
