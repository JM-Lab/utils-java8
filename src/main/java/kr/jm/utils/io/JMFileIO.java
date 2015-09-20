package kr.jm.utils.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import kr.jm.utils.exception.JMExceptionManager;

public class JMFileIO {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(JMFileIO.class);

	public static boolean writeString(String inputString, File targetfile) {
		if (!targetfile.exists()) {
			try {
				Files.write(targetfile.toPath(), inputString.getBytes());
			} catch (IOException e) {
				return JMExceptionManager.handleExceptionAndReturnFalse(log, e,
						"writeString", inputString, targetfile);
			}
		}
		return true;
	}

	public static String readString(File targetfile) {
		try {
			return new String(Files.readAllBytes(targetfile.toPath()));
		} catch (IOException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"readString", targetfile);
		}
	}

	public static String readString(File targetfile, String encoding) {
		try {
			return new String(Files.readAllBytes(targetfile.toPath()),
					Charset.forName(encoding));
		} catch (IOException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"readString", targetfile);
		}
	}

	public static String readString(String filePath) {
		return readString(getFile(filePath));
	}

	public static String readString(String filePath, String encoding) {
		return readString(getFile(filePath), encoding);
	}

	public static List<String> readLines(File targetfile) {
		try {
			return Files.readAllLines(targetfile.toPath());
		} catch (IOException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"readLines", targetfile);
		}
	}

	public static List<String> readLines(File targetfile, String encoding) {
		try {
			return Files.readAllLines(targetfile.toPath(),
					Charset.forName(encoding));
		} catch (IOException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"readLines", targetfile);
		}
	}

	public static List<String> readLines(String filePath) {
		return readLines(getFile(filePath));
	}

	public static List<String> readLines(String filePath, String encoding) {
		return readLines(getFile(filePath), encoding);
	}

	private static File getFile(String filePath) {
		return new File(filePath);
	}

}
