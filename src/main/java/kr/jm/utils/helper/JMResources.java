package kr.jm.utils.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import kr.jm.utils.exception.JMExceptionManager;

public class JMResources {

	private static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(JMResources.class);

	public static void setSystemPropertyIfIsNull(String key, Object value) {
		if (!System.getProperties().containsKey(key))
			System.setProperty(key, value.toString());
	}

	public static String getSystemProperty(String key) {
		return System.getProperty(key);
	}

	public static URL getResourceURL(String pathInClassPath) {
		return ClassLoader.getSystemResource(pathInClassPath);
	}

	public static URI getResourceURI(String pathInClassPath) {
		try {
			return getResourceURL(pathInClassPath).toURI();
		} catch (URISyntaxException e) {
			JMExceptionManager.logException(log, e, "getResourceURI",
					pathInClassPath);
			return null;
		}
	}

	public static InputStream getResourceInputStream(String pathInClassPath) {
		return ClassLoader.getSystemResourceAsStream(pathInClassPath);
	}

	public static Properties getProperties(String pathInClassPath) {
		Properties properties = new Properties();
		InputStream is = getResourceInputStream(pathInClassPath);
		try {
			properties.load(is);
			is.close();
		} catch (IOException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"getProperties", pathInClassPath);
		}
		return properties;
	}

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

	public static String readString(String resourceClasspath) {
		return JMIO.toString(getResourceInputStream(resourceClasspath));
	}

	public static String readString(String resourceClasspath, String encoding) {
		return JMIO.toString(getResourceInputStream(resourceClasspath),
				encoding);
	}

	public static List<String> readLines(String resourceClasspath) {
		return JMIO.readLines(getResourceInputStream(resourceClasspath));
	}

	public static List<String> readLines(String resourceClasspath,
			String encoding) {
		return JMIO.readLines(getResourceInputStream(resourceClasspath),
				encoding);
	}

	public static String getStringFromClasspathOrFilePath(
			String resourceInClasspathOrFilePath) {
		InputStream resourceInputStream =
				getResourceInputStream(resourceInClasspathOrFilePath);
		return resourceInputStream != null ? JMIO.toString(resourceInputStream)
				: JMFile.readString(resourceInClasspathOrFilePath);
	}

	public static String getStringFromClasspathOrFilePath(
			String resourceInClasspathOrFilePath, String encoding) {
		InputStream resourceInputStream =
				getResourceInputStream(resourceInClasspathOrFilePath);
		return resourceInputStream != null
				? JMIO.toString(resourceInputStream, encoding)
				: JMFile.readString(resourceInClasspathOrFilePath, encoding);
	}

	public static List<String> readLinesfromClasspathOrFilePath(
			String resourceInClasspathOrFilePath) {
		InputStream resourceInputStream =
				getResourceInputStream(resourceInClasspathOrFilePath);
		return resourceInputStream != null ? JMIO.readLines(resourceInputStream)
				: JMFile.readLines(resourceInClasspathOrFilePath);
	}

	public static ResourceBundle getResourceBundle(String baseName) {
		return ResourceBundle.getBundle(baseName);
	}

	public static ResourceBundle getResourceBundle(String baseName,
			Locale targetLocale) {
		Locale.setDefault(targetLocale);
		return ResourceBundle.getBundle(baseName);
	}

}
