
package kr.jm.utils.enums;

import kr.jm.utils.AutoStringBuilder;
import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.exception.JMExceptionManager;
import kr.jm.utils.helper.JMLog;
import kr.jm.utils.helper.JMString;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMPredicate.negate;

/**
 * The enum Os.
 */
public enum OS {

    /**
     * Windows os.
     */
    WINDOWS,
    /**
     * Mac os.
     */
    MAC,
    /**
     * Linux os.
     */
    LINUX;

	private static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(OS.class);
	private static final String fileSeparator =
			System.getProperty("file.separator");
	private static FileView fileView;
	private static FileSystemView fileSystemView;
	private static Predicate<InetAddress> LoopbackFilter =
			negate(InetAddress::isLoopbackAddress);

    /**
     * Gets file separator.
     *
     * @return the file separator
     */
    public static String getFileSeparator() {
        return fileSeparator;
    }

    /**
     * Gets line separator.
     *
     * @return the line separator
     */
    public static String getLineSeparator() {
		return System.getProperty("line.separator");
    }

    /**
     * Gets os name.
     *
     * @return the os name
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * Gets os version.
     *
     * @return the os version
     */
    public static String getOsVersion() {
		return System.getProperty("os.version");
    }

    /**
     * Gets user working dir.
     *
     * @return the user working dir
     */
    public static String getUserWorkingDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Gets user home dir.
     *
     * @return the user home dir
     */
    public static String getUserHomeDir() {
        return System.getProperty("user.home");
    }

    /**
     * Gets java io tmp dir.
     *
     * @return the java io tmp dir
     */
    public static String getJavaIoTmpDir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * Build path string.
     *
     * @param strings the strings
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
     * Gets os.
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
     * Add shutdown hook.
     *
     * @param runAfterShutdown the run after shutdown
     */
    public static void addShutdownHook(Runnable runAfterShutdown) {
		Runtime.getRuntime().addShutdownHook(new Thread(runAfterShutdown));
    }

    /**
     * Gets hostname.
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
     * Gets ip.
     *
     * @return the ip
     */
    public static String getIp() {
        return getIpInfo().getHostAddress();
    }

    /**
     * Gets ip list.
     *
     * @return the ip list
     */
    public static List<String> getIpList() {
		return getIpInfoList().stream().map(InetAddress::getHostAddress)
                .collect(toList());
    }

    /**
     * Gets ip info.
     *
     * @return the ip info
     */
    public static InetAddress getIpInfo() {
		return getDefaultInetAddressAsOpt().filter(OS::isIpv4Address)
                .orElseGet(getIpInfoList().stream().findFirst()::get);
    }

    /**
     * Gets ip info list.
     *
     * @return the ip info list
     */
    public static List<InetAddress> getIpInfoList() {
		return getAllInetAddressInfoStream().filter(LoopbackFilter)
				.filter(OS::isIpv4Address).collect(toList());
	}

	private static boolean isIpv4Address(InetAddress inetAddress) {
		return inetAddress.getHostAddress().matches(JMString.IPV4Pattern);
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

    /**
     * Gets all inet address info list.
     *
     * @return the all inet address info list
     */
    public static List<InetAddress> getAllInetAddressInfoList() {
        return getAllInetAddressInfoStream().collect(toList());
    }

    /**
     * Gets all inet address info stream.
     *
     * @return the all inet address info stream
     */
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
     * Gets file description.
     *
     * @param file the file
     * @return the file description
     */
    public static String getFileDescription(File file) {
        return getFileView().getDescription(file);
    }

    /**
     * Gets home directory file.
     *
     * @return the home directory file
     */
    public static File getHomeDirectoryFile() {
        return getFileSystemView().getHomeDirectory();
    }

    /**
     * Gets default directory file.
     *
     * @return the default directory file
     */
    public static File getDefaultDirectoryFile() {
        return getFileSystemView().getDefaultDirectory();
    }

    /**
     * Gets available local port.
     *
     * @return the available local port
     */
    public static int getAvailableLocalPort() {
		try (ServerSocket socket = new ServerSocket(0)) {
			socket.setReuseAddress(true);
			return socket.getLocalPort();
		} catch (IOException e) {
			return JMExceptionManager.handleExceptionAndReturn(log, e,
                    "getAvailableLocalPort", () -> -1);
        }
    }

    /**
     * Gets available processors.
     *
     * @return the available processors
     */
    public static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Open boolean.
     *
     * @param file the file
     * @return the boolean
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
			return JMExceptionManager.handleExceptionAndReturnFalse(log, e,
                    "open", runCmd, file.getAbsolutePath());
        }
    }

    /**
     * Gets icon.
     *
     * @param file the file
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
     * Gets file name.
     *
     * @param file the file
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
     * Gets file type description.
     *
     * @param file the file
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
     * Gets root file list.
     *
     * @return the root file list
     */
    public List<File> getRootFileList() {
		return JMCollections.buildList(getFileSystemView().getRoots());
	}

}
