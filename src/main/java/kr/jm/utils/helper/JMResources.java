
package kr.jm.utils.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import kr.jm.utils.exception.JMExceptionManager;

/**
 * The Class JMResources.
 */
public class JMResources {

	private static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(JMResources.class);

	/**
	 * Sets the system property if is null.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public static void setSystemPropertyIfIsNull(String key, Object value) {
		if (!System.getProperties().containsKey(key))
			System.setProperty(key, value.toString());
	}

	/**
	 * Gets the system property.
	 *
	 * @param key
	 *            the key
	 * @return the system property
	 */
	public static String getSystemProperty(String key) {
		return System.getProperty(key);
	}

	/**
	 * Gets the resource URL.
	 *
	 * @param classpath
	 *            the classpath
	 * @return the resource URL
	 */
	public static URL getResourceURL(String classpath) {
		return ClassLoader.getSystemResource(classpath);
	}

	/**
	 * Gets the url.
	 *
	 * @param classpathOrFilePath
	 *            the classpath or file path
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
	 * @param classpath
	 *            the classpath
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
	 * @param classpathOrFilePath
	 *            the classpath or file path
	 * @return the uri
	 */
	public static URI getURI(String classpathOrFilePath) {
		return Optional.ofNullable(getResourceURI(classpathOrFilePath))
				.orElseGet(() -> new File(classpathOrFilePath).toURI());
	}

	/**
	 * Gets the resource input stream.
	 *
	 * @param classpath
	 *            the classpath
	 * @return the resource input stream
	 */
	public static InputStream getResourceInputStream(String classpath) {
		return ClassLoader.getSystemResourceAsStream(classpath);
	}

	/**
	 * Gets the properties.
	 *
	 * @param classpath
	 *            the classpath
	 * @return the properties
	 */
	public static Properties getProperties(String classpath) {
		Properties properties = new Properties();
		InputStream is = getResourceInputStream(classpath);
		try {
			properties.load(is);
			is.close();
		} catch (IOException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"getProperties", classpath);
		}
		return properties;
	}

	/**
	 * Gets the properties.
	 *
	 * @param propertiesFile
	 *            the properties file
	 * @return the properties
	 */
	public static Properties getProperties(File propertiesFile) {
		Properties properties = new Properties();
		try {
			BufferedReader reader =
					new BufferedReader(new FileReader(propertiesFile));
			properties.load(reader);
			reader.close();
		} catch (IOException e) {
			JMExceptionManager.logException(log, e, "getProperties",
					propertiesFile);
		}
		return properties;
	}

	/**
	 * Save properties.
	 *
	 * @param inProperties
	 *            the in properties
	 * @param saveFile
	 *            the save file
	 * @param comment
	 *            the comment
	 * @return true, if successful
	 */
	public static boolean saveProperties(Properties inProperties, File saveFile,
			String comment) {
		try {
			if (!saveFile.exists()) {
				saveFile.getParentFile().mkdirs();
				saveFile.createNewFile();
			}
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
	 * @param resourceClasspath
	 *            the resource classpath
	 * @return the string
	 */
	public static String readString(String resourceClasspath) {
		return JMIO.toString(getResourceInputStream(resourceClasspath));
	}

	/**
	 * Read string.
	 *
	 * @param resourceClasspath
	 *            the resource classpath
	 * @param encoding
	 *            the encoding
	 * @return the string
	 */
	public static String readString(String resourceClasspath, String encoding) {
		return JMIO.toString(getResourceInputStream(resourceClasspath),
				encoding);
	}

	/**
	 * Read lines.
	 *
	 * @param resourceClasspath
	 *            the resource classpath
	 * @return the list
	 */
	public static List<String> readLines(String resourceClasspath) {
		return JMIO.readLines(getResourceInputStream(resourceClasspath));
	}

	/**
	 * Read lines.
	 *
	 * @param resourceClasspath
	 *            the resource classpath
	 * @param encoding
	 *            the encoding
	 * @return the list
	 */
	public static List<String> readLines(String resourceClasspath,
			String encoding) {
		return JMIO.readLines(getResourceInputStream(resourceClasspath),
				encoding);
	}

	/**
	 * Gets the string from classpath or file path.
	 *
	 * @param classpathOrFilePath
	 *            the classpath or file path
	 * @return the string from classpath or file path
	 */
	public static String
			getStringFromClasspathOrFilePath(String classpathOrFilePath) {
		InputStream resourceInputStream =
				getResourceInputStream(classpathOrFilePath);
		return resourceInputStream != null ? JMIO.toString(resourceInputStream)
				: JMFile.readString(classpathOrFilePath);
	}

	/**
	 * Gets the string from classpath or file path.
	 *
	 * @param classpathOrFilePath
	 *            the classpath or file path
	 * @param encoding
	 *            the encoding
	 * @return the string from classpath or file path
	 */
	public static String getStringFromClasspathOrFilePath(
			String classpathOrFilePath, String encoding) {
		InputStream resourceInputStream =
				getResourceInputStream(classpathOrFilePath);
		return resourceInputStream != null
				? JMIO.toString(resourceInputStream, encoding)
				: JMFile.readString(classpathOrFilePath, encoding);
	}

	/**
	 * Read lines from classpath or file path.
	 *
	 * @param classpathOrFilePath
	 *            the classpath or file path
	 * @return the list
	 */
	public static List<String>
			readLinesFromClasspathOrFilePath(String classpathOrFilePath) {
		InputStream resourceInputStream =
				getResourceInputStream(classpathOrFilePath);
		return resourceInputStream != null ? JMIO.readLines(resourceInputStream)
				: JMFile.readLines(classpathOrFilePath);
	}

	/**
	 * Gets the resource bundle.
	 *
	 * @param baseName
	 *            the base name
	 * @return the resource bundle
	 */
	public static ResourceBundle getResourceBundle(String baseName) {
		return ResourceBundle.getBundle(baseName);
	}

	/**
	 * Gets the resource bundle.
	 *
	 * @param baseName
	 *            the base name
	 * @param targetLocale
	 *            the target locale
	 * @return the resource bundle
	 */
	public static ResourceBundle getResourceBundle(String baseName,
			Locale targetLocale) {
		Locale.setDefault(targetLocale);
		return ResourceBundle.getBundle(baseName);
	}

}
