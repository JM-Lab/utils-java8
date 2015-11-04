package kr.jm.utils.enums;

import java.awt.Desktop;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;

import kr.jm.utils.AutoStringBuilder;
import kr.jm.utils.exception.JMExceptionManager;
import kr.jm.utils.helper.JMLog;

public enum OS {

	WINDOWS, MAC, LINUX;

	private FileView fileView;
	private FileSystemView fileSystemView;

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(OS.class);

	private static final String fileSeparator = System
			.getProperty("file.separator");

	public static String getFileSeparator() {
		return fileSeparator;
	}

	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}

	public static String getOsName() {
		return System.getProperty("os.name");
	}

	public static String getOsVersion() {
		return System.getProperty("os.version");
	}

	public static String getUserWorkingDir() {
		return System.getProperty("user.dir");
	}

	public static String getUserHomeDir() {
		return System.getProperty("user.home");
	}

	public static String buildPath(String... strings) {
		AutoStringBuilder asb = new AutoStringBuilder(fileSeparator);
		for (String string : strings) {
			asb.append(string);
		}
		return asb.toString();
	}

	public static OS getOS() {
		String os = getOsName().toLowerCase();
		if (os.contains("windows")) {
			return WINDOWS;
		} else if (os.contains("mac")) {
			return MAC;
		} else {
			return LINUX;
		}
	}

	public boolean open(File file) {
		try {
			Desktop.getDesktop().open(file);
			return true;
		} catch (Exception e) {
			return openAlternatively(file);
		}
	}

	private boolean openAlternatively(File file) {
		switch (this) {
		case WINDOWS:
			return open("cmd /c ", file);
		case MAC:
			return open("open ", file);
		default:
			return open("open ", file);
		}
	}

	private boolean open(String runCmd, File file) {
		try {
			String command = runCmd + file.getAbsolutePath();
			JMLog.logMethodStartInfo(log, "open", command);
			Runtime.getRuntime().exec(command);
			return true;
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"open", runCmd, file.getAbsolutePath());
		}
	}

	public static String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"getIp");
		}

	}

	public static String getHostname() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"getHostname");
		}
	}

	public Icon getIconInOS(File file) {
		switch (this) {
		case MAC:
			return Optional.ofNullable(fileView).orElseGet(() -> {
				JFileChooser jFileChooser = new JFileChooser();
				fileView = jFileChooser.getUI().getFileView(jFileChooser);
				return fileView;
			}).getIcon(file);
		default:
			return Optional.ofNullable(fileSystemView).orElseGet(() -> {
				fileSystemView = FileSystemView.getFileSystemView();
				return fileSystemView;
			}).getSystemIcon(file);
		}
	}
}
