
package kr.jm.utils.helper;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMPredicate.getIsEmpty;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.enums.OS;
import kr.jm.utils.exception.JMExceptionManager;

/**
 * The Class JMPath.
 */
public class JMPath {

	private static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(JMPath.class);

	private static final OS os = OS.getOS();

	/** The Constant FS. */
	public static final FileSystem FS = FileSystems.getDefault();

	/** The Constant DirectoryFilter. */
	public static final Predicate<Path> DirectoryFilter = Files::isDirectory;

	/** The Constant RegularFileFilter. */
	public static final Predicate<Path> RegularFileFilter =
			Files::isRegularFile;

	/** The Constant ExecutableFilter. */
	public static final Predicate<Path> ExecutableFilter = Files::isExecutable;

	/** The Constant ReadableFilter. */
	public static final Predicate<Path> ReadableFilter = Files::isReadable;

	/** The Constant WritableFilter. */
	public static final Predicate<Path> WritableFilter = Files::isWritable;

	/** The Constant SymbolicLinkFilter. */
	public static final Predicate<Path> SymbolicLinkFilter =
			Files::isSymbolicLink;

	/** The Constant HiddenFilter. */
	public static final Predicate<Path> HiddenFilter = JMPath::isHidden;

	/** The Constant NotExistFilter. */
	public static final Predicate<Path> NotExistFilter = Files::notExists;

	/** The Constant ExistFilter. */
	public static final Predicate<Path> ExistFilter = Files::exists;

	/** The Constant DirectoryAndNotSymbolicLinkFilter. */
	public static final Predicate<Path> DirectoryAndNotSymbolicLinkFilter =
			DirectoryFilter.and(SymbolicLinkFilter.negate());

	/** The Constant directoryFirstComparator. */
	public static final Comparator<Path> directoryFirstComparator =
			(p1, p2) -> isDirectory(p1) && !isDirectory(p2) ? -1
					: !isDirectory(p1) && isDirectory(p2) ? 1
							: p1.compareTo(p2);

	/**
	 * Checks if is directory.
	 *
	 * @param path
	 *            the path
	 * @return true, if is directory
	 */
	public static boolean isDirectory(Path path) {
		return DirectoryFilter.test(path);
	}

	/**
	 * Checks if is regular file.
	 *
	 * @param path
	 *            the path
	 * @return true, if is regular file
	 */
	public static boolean isRegularFile(Path path) {
		return RegularFileFilter.test(path);
	}

	/**
	 * Checks if is executable.
	 *
	 * @param path
	 *            the path
	 * @return true, if is executable
	 */
	public static boolean isExecutable(Path path) {
		return ExecutableFilter.test(path);
	}

	/**
	 * Checks if is readable.
	 *
	 * @param path
	 *            the path
	 * @return true, if is readable
	 */
	public static boolean isReadable(Path path) {
		return ReadableFilter.test(path);
	}

	/**
	 * Checks if is writable.
	 *
	 * @param path
	 *            the path
	 * @return true, if is writable
	 */
	public static boolean isWritable(Path path) {
		return WritableFilter.test(path);
	}

	/**
	 * Checks if is symbolic link.
	 *
	 * @param path
	 *            the path
	 * @return true, if is symbolic link
	 */
	public static boolean isSymbolicLink(Path path) {
		return SymbolicLinkFilter.test(path);
	}

	/**
	 * Not exists.
	 *
	 * @param path
	 *            the path
	 * @return true, if successful
	 */
	public static boolean notExists(Path path) {
		return NotExistFilter.test(path);
	}

	/**
	 * Exists.
	 *
	 * @param path
	 *            the path
	 * @return true, if successful
	 */
	public static boolean exists(Path path) {
		return ExistFilter.test(path);
	}

	/**
	 * Checks if is directory and not symbolic link.
	 *
	 * @param path
	 *            the path
	 * @return true, if is directory and not symbolic link
	 */
	public static boolean isDirectoryAndNotSymbolicLink(Path path) {
		return DirectoryAndNotSymbolicLinkFilter.test(path);
	}

	/**
	 * Gets the file store list.
	 *
	 * @return the file store list
	 */
	public static List<FileStore> getFileStoreList() {
		return JMCollections.buildList(FS.getFileStores());
	}

	/**
	 * Gets the file store path list.
	 *
	 * @return the file store path list
	 */
	public static List<Path> getFileStorePathList() {
		return getFileStoreList().stream().map(Object::toString)
				.map(toString -> toString.substring(0,
						toString.indexOf(JMString.SPACE)))
				.map(JMPath::getPath).collect(toList());
	}

	/**
	 * Checks if is hidden.
	 *
	 * @param path
	 *            the path
	 * @return true, if is hidden
	 */
	public static boolean isHidden(Path path) {
		try {
			return Files.isHidden(path);
		} catch (IOException e) {
			return true;
		}
	}

	/**
	 * Gets the parent.
	 *
	 * @param path
	 *            the path
	 * @return the parent
	 */
	public static Path getParent(Path path) {
		return Optional.ofNullable(path.getParent()).orElse(path.getRoot());
	}

	/**
	 * Gets the root path stream.
	 *
	 * @return the root path stream
	 */
	public static Stream<Path> getRootPathStream() {
		return os.getRootFileList().stream().map(File::toPath);
	}

	/**
	 * Gets the root directory stream.
	 *
	 * @return the root directory stream
	 */
	public static Stream<Path> getRootDirectoryStream() {
		return getRootPathStream().filter(DirectoryFilter);
	}

	/**
	 * Gets the path.
	 *
	 * @param path
	 *            the path
	 * @return the path
	 */
	public static Path getPath(String path) {
		return FS.getPath(path).toAbsolutePath();
	}

	/**
	 * Gets the current path.
	 *
	 * @return the current path
	 */
	public static Path getCurrentPath() {
		return getPath(kr.jm.utils.enums.OS.getUserWorkingDir());
	}

	/**
	 * Gets the user home.
	 *
	 * @return the user home
	 */
	public static Path getUserHome() {
		return getPath(kr.jm.utils.enums.OS.getUserHomeDir());
	}

	/**
	 * Gets the children path stream.
	 *
	 * @param path
	 *            the path
	 * @return the children path stream
	 */
	public static Stream<Path> getChildrenPathStream(Path path) {
		return Optional.ofNullable(path.toFile().listFiles())
				.map(listFiles -> Stream.of(listFiles).map(File::toPath))
				.orElseGet(Stream::empty);
	}

	/**
	 * Gets the children path stream.
	 *
	 * @param path
	 *            the path
	 * @param filter
	 *            the filter
	 * @return the children path stream
	 */
	public static Stream<Path> getChildrenPathStream(Path path,
			Predicate<Path> filter) {
		return getChildrenPathStream(path).filter(filter);
	}

	/**
	 * Gets the child directory path stream.
	 *
	 * @param path
	 *            the path
	 * @return the child directory path stream
	 */
	public static Stream<Path> getChildDirectoryPathStream(Path path) {
		return getChildrenPathStream(path)
				.filter(DirectoryAndNotSymbolicLinkFilter);
	}

	/**
	 * Gets the child directory path stream.
	 *
	 * @param path
	 *            the path
	 * @param filter
	 *            the filter
	 * @return the child directory path stream
	 */
	public static Stream<Path> getChildDirectoryPathStream(Path path,
			Predicate<Path> filter) {
		return getChildrenPathStream(path)
				.filter(DirectoryAndNotSymbolicLinkFilter.and(filter));
	}

	/**
	 * Gets the child file path stream.
	 *
	 * @param path
	 *            the path
	 * @return the child file path stream
	 */
	public static Stream<Path> getChildFilePathStream(Path path) {
		return getChildrenPathStream(path).filter(RegularFileFilter);
	}

	/**
	 * Gets the child file path stream.
	 *
	 * @param path
	 *            the path
	 * @param filter
	 *            the filter
	 * @return the child file path stream
	 */
	public static Stream<Path> getChildFilePathStream(Path path,
			Predicate<Path> filter) {
		return getChildrenPathStream(path)
				.filter(RegularFileFilter.and(filter));
	}

	/**
	 * Gets the sub directory path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @return the sub directory path list
	 */
	public static List<Path> getSubDirectoryPathList(Path startDirectoryPath) {
		return getSubPathList(startDirectoryPath,
				DirectoryAndNotSymbolicLinkFilter);
	}

	/**
	 * Gets the sub directory path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param maxDepth
	 *            the max depth
	 * @return the sub directory path list
	 */
	public static List<Path> getSubDirectoryPathList(Path startDirectoryPath,
			int maxDepth) {
		return getSubPathList(startDirectoryPath, maxDepth,
				DirectoryAndNotSymbolicLinkFilter);
	}

	/**
	 * Gets the sub directory path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param filter
	 *            the filter
	 * @return the sub directory path list
	 */
	public static List<Path> getSubDirectoryPathList(Path startDirectoryPath,
			Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, Integer.MAX_VALUE,
				DirectoryAndNotSymbolicLinkFilter.and(filter));
	}

	/**
	 * Gets the sub directory path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param maxDepth
	 *            the max depth
	 * @param filter
	 *            the filter
	 * @return the sub directory path list
	 */
	public static List<Path> getSubDirectoryPathList(Path startDirectoryPath,
			int maxDepth, Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, maxDepth,
				DirectoryAndNotSymbolicLinkFilter.and(filter));
	}

	/**
	 * Gets the sub file path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @return the sub file path list
	 */
	public static List<Path> getSubFilePathList(Path startDirectoryPath) {
		return getSubPathList(startDirectoryPath, RegularFileFilter);
	}

	/**
	 * Gets the sub file path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param maxDepth
	 *            the max depth
	 * @return the sub file path list
	 */
	public static List<Path> getSubFilePathList(Path startDirectoryPath,
			int maxDepth) {
		return getSubPathList(startDirectoryPath, maxDepth, RegularFileFilter);
	}

	/**
	 * Gets the sub file path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param filter
	 *            the filter
	 * @return the sub file path list
	 */
	public static List<Path> getSubFilePathList(Path startDirectoryPath,
			Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath,
				RegularFileFilter.and(filter));
	}

	/**
	 * Gets the sub file path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param maxDepth
	 *            the max depth
	 * @param filter
	 *            the filter
	 * @return the sub file path list
	 */
	public static List<Path> getSubFilePathList(Path startDirectoryPath,
			int maxDepth, Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, maxDepth,
				RegularFileFilter.and(filter));
	}

	/**
	 * Gets the sub path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @return the sub path list
	 */
	public static List<Path> getSubPathList(Path startDirectoryPath) {
		return getSubPathList(startDirectoryPath, Integer.MAX_VALUE);
	}

	/**
	 * Gets the sub path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param maxDepth
	 *            the max depth
	 * @return the sub path list
	 */
	public static List<Path> getSubPathList(Path startDirectoryPath,
			int maxDepth) {
		return getSubPathList(startDirectoryPath, maxDepth,
				JMPredicate.getTrue());
	}

	/**
	 * Gets the sub path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param filter
	 *            the filter
	 * @return the sub path list
	 */
	public static List<Path> getSubPathList(Path startDirectoryPath,
			Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, Integer.MAX_VALUE, filter);
	}

	/**
	 * Gets the sub path list.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param maxDepth
	 *            the max depth
	 * @param filter
	 *            the filter
	 * @return the sub path list
	 */
	public static List<Path> getSubPathList(Path startDirectoryPath,
			int maxDepth, Predicate<Path> filter) {
		List<Path> pathList = Collections.synchronizedList(new ArrayList<>());
		addAndGetDirectoryStream(pathList,
				getChildrenPathStream(startDirectoryPath), filter).parallel()
						.forEach(getDrillDownFunction(maxDepth, filter,
								pathList));
		return pathList;
	}

	private static Stream<Path> addAndGetDirectoryStream(List<Path> pathList,
			Stream<Path> pathStream, Predicate<Path> filter) {
		return pathStream
				.peek(path -> JMLambda.consumeIfTrue(path, filter,
						pathList::add))
				.filter(DirectoryAndNotSymbolicLinkFilter);
	}

	private static void drillDown(Path directoryPath, int maxDepth,
			List<Path> pathList, Predicate<Path> filter) {
		if (maxDepth < 1)
			return;
		addAndGetDirectoryStream(pathList, getChildrenPathStream(directoryPath),
				filter).forEach(
						getDrillDownFunction(maxDepth, filter, pathList));
	}

	private static Consumer<Path> getDrillDownFunction(int maxDepth,
			Predicate<Path> filter, List<Path> pathList) {
		return path -> drillDown(path, maxDepth - 1, pathList, filter);
	}

	/**
	 * Consume path list.
	 *
	 * @param pathList
	 *            the path list
	 * @param consumer
	 *            the consumer
	 * @param isParallel
	 *            the is parallel
	 */
	public static void consumePathList(List<Path> pathList,
			Consumer<Path> consumer, boolean isParallel) {
		JMLambda.getByBoolean(isParallel, () -> pathList.parallelStream(),
				() -> pathList.stream()).forEach(consumer);
	}

	/**
	 * Consume path list.
	 *
	 * @param pathList
	 *            the path list
	 * @param consumer
	 *            the consumer
	 */
	public static void consumePathList(List<Path> pathList,
			Consumer<Path> consumer) {
		pathList.parallelStream().forEach(consumer);
	}

	/**
	 * Consume sub file paths.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param maxDepth
	 *            the max depth
	 * @param filter
	 *            the filter
	 * @param consumer
	 *            the consumer
	 */
	public static void consumeSubFilePaths(Path startDirectoryPath,
			int maxDepth, Predicate<Path> filter, Consumer<Path> consumer) {
		consumePathList(
				getSubFilePathList(startDirectoryPath, maxDepth, filter),
				consumer);
	}

	/**
	 * Consume sub file paths.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param consumer
	 *            the consumer
	 */
	public static void consumeSubFilePaths(Path startDirectoryPath,
			Consumer<Path> consumer) {
		consumeSubFilePaths(startDirectoryPath, Integer.MAX_VALUE,
				JMPredicate.getTrue(), consumer);
	}

	/**
	 * Consume sub file paths.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param maxDepth
	 *            the max depth
	 * @param consumer
	 *            the consumer
	 */
	public static void consumeSubFilePaths(Path startDirectoryPath,
			int maxDepth, Consumer<Path> consumer) {
		consumeSubFilePaths(startDirectoryPath, maxDepth, JMPredicate.getTrue(),
				consumer);
	}

	/**
	 * Consume sub file paths.
	 *
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param filter
	 *            the filter
	 * @param consumer
	 *            the consumer
	 */
	public static void consumeSubFilePaths(Path startDirectoryPath,
			Predicate<Path> filter, Consumer<Path> consumer) {
		consumeSubFilePaths(startDirectoryPath, Integer.MAX_VALUE, filter,
				consumer);
	}

	/**
	 * Apply path list and get result list.
	 *
	 * @param <R>
	 *            the generic type
	 * @param pathList
	 *            the path list
	 * @param function
	 *            the function
	 * @param isParallel
	 *            the is parallel
	 * @return the list
	 */
	public static <R> List<R> applyPathListAndGetResultList(List<Path> pathList,
			Function<Path, R> function, boolean isParallel) {
		return JMLambda
				.getByBoolean(isParallel, () -> pathList.parallelStream(),
						() -> pathList.stream())
				.map(function).collect(toList());
	}

	/**
	 * Apply path list and get result list.
	 *
	 * @param <R>
	 *            the generic type
	 * @param pathList
	 *            the path list
	 * @param function
	 *            the function
	 * @return the list
	 */
	public static <R> List<R> applyPathListAndGetResultList(List<Path> pathList,
			Function<Path, R> function) {
		return pathList.parallelStream().map(function).collect(toList());
	}

	/**
	 * Apply sub file paths and get applied list.
	 *
	 * @param <R>
	 *            the generic type
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param maxDepth
	 *            the max depth
	 * @param filter
	 *            the filter
	 * @param function
	 *            the function
	 * @param isParallel
	 *            the is parallel
	 * @return the list
	 */
	public static <R> List<R> applySubFilePathsAndGetAppliedList(
			Path startDirectoryPath, int maxDepth, Predicate<Path> filter,
			Function<Path, R> function, boolean isParallel) {
		return applyPathListAndGetResultList(
				getSubFilePathList(startDirectoryPath, maxDepth, filter),
				function, isParallel);
	}

	/**
	 * Apply sub file paths and get applied list.
	 *
	 * @param <R>
	 *            the generic type
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param maxDepth
	 *            the max depth
	 * @param filter
	 *            the filter
	 * @param function
	 *            the function
	 * @return the list
	 */
	public static <R> List<R> applySubFilePathsAndGetAppliedList(
			Path startDirectoryPath, int maxDepth, Predicate<Path> filter,
			Function<Path, R> function) {
		return applySubFilePathsAndGetAppliedList(startDirectoryPath, maxDepth,
				filter, function, true);
	}

	/**
	 * Apply sub file paths and get applied list.
	 *
	 * @param <R>
	 *            the generic type
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param function
	 *            the function
	 * @return the list
	 */
	public static <R> List<R> applySubFilePathsAndGetAppliedList(
			Path startDirectoryPath, Function<Path, R> function) {
		return applySubFilePathsAndGetAppliedList(startDirectoryPath,
				Integer.MAX_VALUE, JMPredicate.getTrue(), function);
	}

	/**
	 * Apply sub file paths and get applied list.
	 *
	 * @param <R>
	 *            the generic type
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param maxDepth
	 *            the max depth
	 * @param function
	 *            the function
	 * @return the list
	 */
	public static <R> List<R> applySubFilePathsAndGetAppliedList(
			Path startDirectoryPath, int maxDepth, Function<Path, R> function) {
		return applySubFilePathsAndGetAppliedList(startDirectoryPath, maxDepth,
				JMPredicate.getTrue(), function);
	}

	/**
	 * Apply sub file paths and get applied list.
	 *
	 * @param <R>
	 *            the generic type
	 * @param startDirectoryPath
	 *            the start directory path
	 * @param filter
	 *            the filter
	 * @param function
	 *            the function
	 * @return the list
	 */
	public static <R> List<R> applySubFilePathsAndGetAppliedList(
			Path startDirectoryPath, Predicate<Path> filter,
			Function<Path, R> function) {
		return applySubFilePathsAndGetAppliedList(startDirectoryPath,
				Integer.MAX_VALUE, filter, function);
	}

	/**
	 * Gets the file path extension as opt.
	 *
	 * @param path
	 *            the path
	 * @return the file path extension as opt
	 */
	public static Optional<String> getFilePathExtensionAsOpt(Path path) {
		return getPathExtensionAsOpt(path, RegularFileFilter);
	}

	/**
	 * Gets the path extension as opt.
	 *
	 * @param path
	 *            the path
	 * @return the path extension as opt
	 */
	public static Optional<String> getPathExtensionAsOpt(Path path) {
		return getPathExtensionAsOpt(path, JMPredicate.getTrue());
	}

	private static Optional<String> getPathExtensionAsOpt(Path path,
			Predicate<Path> filter) {
		return JMOptional.getNullableAndFilteredOptional(path, filter)
				.map(JMPath::getLastName).map(JMString::getExtension)
				.filter(getIsEmpty().negate());
	}

	/**
	 * Creates the temp file path as opt.
	 *
	 * @param path
	 *            the path
	 * @return the optional
	 */
	public static Optional<Path> createTempFilePathAsOpt(Path path) {
		String[] prefixSuffix = JMFile.getPrefixSuffix(path.toFile());
		try {
			return Optional
					.of(Files.createTempFile(prefixSuffix[0], prefixSuffix[1]))
					.filter(ExistFilter).map(JMPath::deleteOnExit);
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnEmptyOptional(log,
					e, "createTempFile", path);
		}
	}

	/**
	 * Delete on exit.
	 *
	 * @param path
	 *            the path
	 * @return the path
	 */
	public static Path deleteOnExit(Path path) {
		path.toFile().deleteOnExit();
		return path;
	}

	/**
	 * Gets the last name.
	 *
	 * @param path
	 *            the path
	 * @return the last name
	 */
	public static String getLastName(Path path) {
		return path.toFile().getName();
	}

	/**
	 * Gets the path name in OS.
	 *
	 * @param path
	 *            the path
	 * @return the path name in OS
	 */
	public static String getPathNameInOS(Path path) {
		return os.getFileName(path.toFile());
	}

	/**
	 * Gets the last modified.
	 *
	 * @param path
	 *            the path
	 * @return the last modified
	 */
	public static long getLastModified(Path path) {
		return path.toFile().lastModified();
	}

	/**
	 * Gets the path type description.
	 *
	 * @param path
	 *            the path
	 * @return the path type description
	 */
	public static String getPathTypeDescription(Path path) {
		return os.getFileTypeDescription(path.toFile());
	}

	/**
	 * Gets the size.
	 *
	 * @param path
	 *            the path
	 * @return the size
	 */
	public static long getSize(Path path) {
		return path.toFile().length();
	}

}
