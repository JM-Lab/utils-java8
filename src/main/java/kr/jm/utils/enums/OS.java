
package kr.jm.utils.enums;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMPredicate.negate;

import java.awt.Desktop;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;

import kr.jm.utils.AutoStringBuilder;
import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.exception.JMExceptionManager;
import kr.jm.utils.helper.JMLog;
import kr.jm.utils.helper.JMString;

/**
 * The Enum OS.
 */
public enum OS {

	WINDOWS, MAC, LINUX;

	private static FileView fileView;
	private static FileSystemView fileSystemView;

	private static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(OS.class);

	private static final String fileSeparator =
			System.getProperty("file.separator");

	private static Predicate<InetAddress> LoopbackFilter =
			negate(InetAddress::isLoopbackAddress);

	/**
	 * Gets the file separator.
	 *
	 * @return the file separator
	 */
	public static String getFileSeparator() {
		return fileSeparator;
	}

	/**
	 * Gets the line separator.
	 *
	 * @return the line separator
	 */
	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}

	/**
	 * Gets the os name.
	 *
	 * @return the os name
	 */
	public static String getOsName() {
		return System.getProperty("os.name");
	}

	/**
	 * Gets the os version.
	 *
	 * @return the os version
	 */
	public static String getOsVersion() {
		return System.getProperty("os.version");
	}

	/**
	 * Gets the user working dir.
	 *
	 * @return the user working dir
	 */
	public static String getUserWorkingDir() {
		return System.getProperty("user.dir");
	}

	/**
	 * Gets the user home dir.
	 *
	 * @return the user home dir
	 */
	public static String getUserHomeDir() {
		return System.getProperty("user.home");
	}

	/**
	 * Builds the path.
	 *
	 * @param strings
	 *            the strings
	 * @return the string
	 */
	public static String buildPath(String... strings) {
		AutoStringBuilder asb = new AutoStringBuilder(fileSeparator);
		for (String string : strings) {
			asb.append(string);
		}
		return asb.toString();
	}

	/**
	 * Gets the os.
	 *
	 * @return the os
	 */
	public static OS getOS() {
		String os = getOsName().toLowerCase();
		if (os.contains("windows"))
			return WINDOWS;
		else if (os.contains("mac"))
			return MAC;
		else
			return LINUX;
	}

	/**
	 * Open.
	 *
	 * @param file
	 *            the file
	 * @return true, if successful
	 */
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
			JMLog.info(log, "open", command);
			Runtime.getRuntime().exec(command);
			return true;
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"open", runCmd, file.getAbsolutePath());
		}
	}

	/**
	 * Gets the hostname.
	 *
	 * @return the hostname
	 */
	public static String getHostname() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"getHostname");
		}

	}

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public static String getIp() {
		return getIpInfo().getHostAddress();
	}

	public static List<String> getIpList() {
		return getIpInfoList().stream().map(InetAddress::getHostAddress)
				.collect(toList());
	}

	public static InetAddress getIpInfo() {
		return getDefaultInetAddressAsOpt().filter(OS::isIpv4Address)
				.orElseGet(getIpInfoList().stream().findFirst()::get);
	}

	public static List<InetAddress> getIpInfoList() {
		return getAllInetAddressInfoStream().filter(LoopbackFilter)
				.filter(OS::isIpv4Address).collect(toList());
	}

	private static boolean isIpv4Address(InetAddress inetAddress) {
		return inetAddress.getHostAddress().matches(JMString.ipv4Pattern);
	}

	private static Optional<InetAddress> getDefaultInetAddressAsOpt() {
		try {
			return Optional.ofNullable(InetAddress.getLocalHost())
					.filter(LoopbackFilter);
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndThrowRuntimeEx(log,
					new RuntimeException("There Is No IP Address !!!"),
					"getDefaultInetAddressAsOpt");
		}
	}

	public static List<InetAddress> getAllInetAddressInfoList() {
		return getAllInetAddressInfoStream().collect(toList());
	}

	public static Stream<InetAddress> getAllInetAddressInfoStream() {
		try {
			return Collections.list(NetworkInterface.getNetworkInterfaces())
					.stream().flatMap(nic -> Collections
							.list(nic.getInetAddresses()).stream());
		} catch (SocketException e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					"getAllInetAddressInfoStream");
		}
	}

	private static FileSystemView getFileSystemView() {
		return Optional.ofNullable(fileSystemView)
				.orElseGet(FileSystemView::getFileSystemView);
	}

	private static FileView getFileView() {
		return Optional.ofNullable(fileView).orElseGet(() -> {
			JFileChooser jFileChooser = new JFileChooser();
			return fileView = jFileChooser.getUI().getFileView(jFileChooser);
		});
	}

	/**
	 * Gets the icon.
	 *
	 * @param file
	 *            the file
	 * @return the icon
	 */
	public Icon getIcon(File file) {
		switch (this) {
		case MAC:
			return getFileView().getIcon(file);
		default:
			return getFileSystemView().getSystemIcon(file);
		}
	}

	/**
	 * Gets the file name.
	 *
	 * @param file
	 *            the file
	 * @return the file name
	 */
	public String getFileName(File file) {
		switch (this) {
		case MAC:
			return getFileView().getName(file);
		default:
			return getFileSystemView().getSystemDisplayName(file);
		}
	}

	/**
	 * Gets the file type description.
	 *
	 * @param file
	 *            the file
	 * @return the file type description
	 */
	public String getFileTypeDescription(File file) {
		switch (this) {
		case MAC:
			return getFileView().getTypeDescription(file);
		default:
			return getFileSystemView().getSystemTypeDescription(file);
		}
	}

	/**
	 * Gets the root file list.
	 *
	 * @return the root file list
	 */
	public List<File> getRootFileList() {
		return JMCollections.buildList(getFileSystemView().getRoots());
	}

	/**
	 * Gets the file description.
	 *
	 * @param file
	 *            the file
	 * @return the file description
	 */
	public static String getFileDescription(File file) {
		return getFileView().getDescription(file);
	}

	/**
	 * Gets the home dirctory file.
	 *
	 * @return the home dirctory file
	 */
	public static File getHomeDirctoryFile() {
		return getFileSystemView().getHomeDirectory();
	}

	/**
	 * Gets the default dirctory file.
	 *
	 * @return the default dirctory file
	 */
	public static File getDefaultDirctoryFile() {
		return getFileSystemView().getDefaultDirectory();
	}

}
