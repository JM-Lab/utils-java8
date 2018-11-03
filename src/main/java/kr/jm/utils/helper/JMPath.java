
package kr.jm.utils.helper;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.enums.OS;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMPredicate.getIsEmpty;
import static kr.jm.utils.helper.JMPredicate.peek;

/**
 * The type Jm path.
 */
public class JMPath {

	private static final OS os = OS.getOS();

    /**
     * The constant FS.
     */
    public static final FileSystem FS = FileSystems.getDefault();

    /**
     * The constant DirectoryFilter.
     */
    public static final Predicate<Path> DirectoryFilter = Files::isDirectory;

    /**
     * The constant RegularFileFilter.
     */
    public static final Predicate<Path> RegularFileFilter =
			Files::isRegularFile;

    /**
     * The constant ExecutableFilter.
     */
    public static final Predicate<Path> ExecutableFilter = Files::isExecutable;

    /**
     * The constant ReadableFilter.
     */
    public static final Predicate<Path> ReadableFilter = Files::isReadable;

    /**
     * The constant WritableFilter.
     */
    public static final Predicate<Path> WritableFilter = Files::isWritable;

    /**
     * The constant SymbolicLinkFilter.
     */
    public static final Predicate<Path> SymbolicLinkFilter =
			Files::isSymbolicLink;

    /**
     * The constant HiddenFilter.
     */
    public static final Predicate<Path> HiddenFilter = JMPath::isHidden;

    /**
     * The constant NotExistFilter.
     */
    public static final Predicate<Path> NotExistFilter = Files::notExists;

    /**
     * The constant ExistFilter.
     */
    public static final Predicate<Path> ExistFilter = Files::exists;

    /**
     * The constant DirectoryAndNotSymbolicLinkFilter.
     */
    public static final Predicate<Path> DirectoryAndNotSymbolicLinkFilter =
			DirectoryFilter.and(SymbolicLinkFilter.negate());

    /**
     * The constant directoryFirstComparator.
     */
    public static final Comparator<Path> directoryFirstComparator =
			(p1, p2) -> isDirectory(p1) && !isDirectory(p2) ? -1
					: !isDirectory(p1) && isDirectory(p2) ? 1
							: p1.compareTo(p2);

    /**
     * Is directory boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean isDirectory(Path path) {
		return DirectoryFilter.test(path);
	}

    /**
     * Is regular file boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean isRegularFile(Path path) {
		return RegularFileFilter.test(path);
	}

    /**
     * Is executable boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean isExecutable(Path path) {
		return ExecutableFilter.test(path);
	}

    /**
     * Is readable boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean isReadable(Path path) {
		return ReadableFilter.test(path);
	}

    /**
     * Is writable boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean isWritable(Path path) {
		return WritableFilter.test(path);
	}

    /**
     * Is symbolic link boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean isSymbolicLink(Path path) {
		return SymbolicLinkFilter.test(path);
	}

    /**
     * Not exists boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean notExists(Path path) {
		return NotExistFilter.test(path);
	}

    /**
     * Exists boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean exists(Path path) {
		return ExistFilter.test(path);
	}

    /**
     * Is directory and not symbolic link boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean isDirectoryAndNotSymbolicLink(Path path) {
		return DirectoryAndNotSymbolicLinkFilter.test(path);
	}

    /**
     * Gets file store list.
     *
     * @return the file store list
     */
    public static List<FileStore> getFileStoreList() {
		return JMCollections.buildList(FS.getFileStores());
	}

    /**
     * Gets file store path list.
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
     * Is hidden boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean isHidden(Path path) {
		try {
			return Files.isHidden(path);
		} catch (IOException e) {
			return true;
		}
	}

    /**
     * Gets parent as opt.
     *
     * @param path the path
     * @return the parent as opt
     */
    public static Optional<Path> getParentAsOpt(Path path) {
		return Optional.ofNullable(path.getParent()).filter(ExistFilter);
	}

    /**
     * Gets root path stream.
     *
     * @return the root path stream
     */
    public static Stream<Path> getRootPathStream() {
		return os.getRootFileList().stream().map(File::toPath);
	}

    /**
     * Gets root directory stream.
     *
     * @return the root directory stream
     */
    public static Stream<Path> getRootDirectoryStream() {
		return getRootPathStream().filter(DirectoryFilter);
	}

    /**
     * Gets path.
     *
     * @param path the path
     * @return the path
     */
    public static Path getPath(String path) {
		return FS.getPath(path).toAbsolutePath();
	}

    /**
     * Gets current path.
     *
     * @return the current path
     */
    public static Path getCurrentPath() {
		return getPath(kr.jm.utils.enums.OS.getUserWorkingDir());
	}

    /**
     * Gets user home.
     *
     * @return the user home
     */
    public static Path getUserHome() {
		return getPath(kr.jm.utils.enums.OS.getUserHomeDir());
	}

    /**
     * Gets children path stream.
     *
     * @param path the path
     * @return the children path stream
     */
    public static Stream<Path> getChildrenPathStream(Path path) {
		return Optional.ofNullable(path.toFile().listFiles())
				.map(listFiles -> Stream.of(listFiles).map(File::toPath))
				.orElseGet(Stream::empty);
	}

    /**
     * Gets children path stream.
     *
     * @param path   the path
     * @param filter the filter
     * @return the children path stream
     */
    public static Stream<Path> getChildrenPathStream(Path path,
			Predicate<Path> filter) {
		return getChildrenPathStream(path).filter(filter);
	}

    /**
     * Gets child directory path stream.
     *
     * @param path the path
     * @return the child directory path stream
     */
    public static Stream<Path> getChildDirectoryPathStream(Path path) {
		return getChildrenPathStream(path)
				.filter(DirectoryAndNotSymbolicLinkFilter);
	}

    /**
     * Gets child directory path stream.
     *
     * @param path   the path
     * @param filter the filter
     * @return the child directory path stream
     */
    public static Stream<Path> getChildDirectoryPathStream(Path path,
			Predicate<Path> filter) {
		return getChildrenPathStream(path)
				.filter(DirectoryAndNotSymbolicLinkFilter.and(filter));
	}

    /**
     * Gets child file path stream.
     *
     * @param path the path
     * @return the child file path stream
     */
    public static Stream<Path> getChildFilePathStream(Path path) {
		return getChildrenPathStream(path).filter(RegularFileFilter);
	}

    /**
     * Gets child file path stream.
     *
     * @param path   the path
     * @param filter the filter
     * @return the child file path stream
     */
    public static Stream<Path> getChildFilePathStream(Path path,
			Predicate<Path> filter) {
		return getChildrenPathStream(path)
				.filter(RegularFileFilter.and(filter));
	}

    /**
     * Gets ancestor path list.
     *
     * @param startPath the start path
     * @return the ancestor path list
     */
    public static List<Path> getAncestorPathList(Path startPath) {
		List<Path> ancestorPathList = new ArrayList<>();
		buildPathListOfAncestorDirectory(ancestorPathList, startPath);
		Collections.reverse(ancestorPathList);
		return ancestorPathList;
	}

	private static void buildPathListOfAncestorDirectory(
			List<Path> pathListOfAncestorDirectory, Path path) {
		Optional.ofNullable(path.getParent())
				.filter(peek(pathListOfAncestorDirectory::add))
				.ifPresent(p -> buildPathListOfAncestorDirectory(
						pathListOfAncestorDirectory, p));
	}

    /**
     * Gets sub directory path list.
     *
     * @param startDirectoryPath the start directory path
     * @return the sub directory path list
     */
    public static List<Path> getSubDirectoryPathList(Path startDirectoryPath) {
		return getSubPathList(startDirectoryPath,
				DirectoryAndNotSymbolicLinkFilter);
	}

    /**
     * Gets sub directory path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @return the sub directory path list
     */
    public static List<Path> getSubDirectoryPathList(Path startDirectoryPath,
			int maxDepth) {
		return getSubPathList(startDirectoryPath, maxDepth,
				DirectoryAndNotSymbolicLinkFilter);
	}

    /**
     * Gets sub directory path list.
     *
     * @param startDirectoryPath the start directory path
     * @param filter             the filter
     * @return the sub directory path list
     */
    public static List<Path> getSubDirectoryPathList(Path startDirectoryPath,
			Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, Integer.MAX_VALUE,
				DirectoryAndNotSymbolicLinkFilter.and(filter));
	}

    /**
     * Gets sub directory path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @return the sub directory path list
     */
    public static List<Path> getSubDirectoryPathList(Path startDirectoryPath,
			int maxDepth, Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, maxDepth,
				DirectoryAndNotSymbolicLinkFilter.and(filter));
	}

    /**
     * Gets sub file path list.
     *
     * @param startDirectoryPath the start directory path
     * @return the sub file path list
     */
    public static List<Path> getSubFilePathList(Path startDirectoryPath) {
		return getSubPathList(startDirectoryPath, RegularFileFilter);
	}

    /**
     * Gets sub file path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @return the sub file path list
     */
    public static List<Path> getSubFilePathList(Path startDirectoryPath,
			int maxDepth) {
		return getSubPathList(startDirectoryPath, maxDepth, RegularFileFilter);
	}

    /**
     * Gets sub file path list.
     *
     * @param startDirectoryPath the start directory path
     * @param filter             the filter
     * @return the sub file path list
     */
    public static List<Path> getSubFilePathList(Path startDirectoryPath,
			Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath,
				RegularFileFilter.and(filter));
	}

    /**
     * Gets sub file path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @return the sub file path list
     */
    public static List<Path> getSubFilePathList(Path startDirectoryPath,
			int maxDepth, Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, maxDepth,
				RegularFileFilter.and(filter));
	}

    /**
     * Gets sub path list.
     *
     * @param startDirectoryPath the start directory path
     * @return the sub path list
     */
    public static List<Path> getSubPathList(Path startDirectoryPath) {
		return getSubPathList(startDirectoryPath, Integer.MAX_VALUE);
	}

    /**
     * Gets sub path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @return the sub path list
     */
    public static List<Path> getSubPathList(Path startDirectoryPath,
			int maxDepth) {
		return getSubPathList(startDirectoryPath, maxDepth,
				JMPredicate.getTrue());
	}

    /**
     * Gets sub path list.
     *
     * @param startDirectoryPath the start directory path
     * @param filter             the filter
     * @return the sub path list
     */
    public static List<Path> getSubPathList(Path startDirectoryPath,
			Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, Integer.MAX_VALUE, filter);
	}

    /**
     * Gets sub path list.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @return the sub path list
     */
    public static List<Path> getSubPathList(Path startDirectoryPath,
			int maxDepth, Predicate<Path> filter) {
		List<Path> subPathList =
				Collections.synchronizedList(new ArrayList<>());
		if (isDirectory(startDirectoryPath))
			buildSubPath(startDirectoryPath, maxDepth, filter, subPathList);
		return subPathList;
	}

	private static void buildSubPath(Path startDirectoryPath, int maxDepth,
			Predicate<Path> filter, List<Path> pathList) {
		addAndGetDirectoryStream(pathList,
				getChildrenPathStream(startDirectoryPath), filter).parallel()
						.forEach(getDrillDownFunction(maxDepth, filter,
								pathList));
	}

	private static Stream<Path> addAndGetDirectoryStream(List<Path> pathList,
			Stream<Path> pathStream, Predicate<Path> filter) {
		return pathStream.peek(
				path -> JMLambda.consumeIfTrue(path, filter, pathList::add))
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
     * @param isParallel the is parallel
     * @param pathList   the path list
     * @param consumer   the consumer
     */
    public static void consumePathList(boolean isParallel, List<Path> pathList,
			Consumer<Path> consumer) {
		JMStream.buildStream(isParallel, pathList).forEach(consumer);
	}

    /**
     * Consume path list.
     *
     * @param pathList the path list
     * @param consumer the consumer
     */
    public static void consumePathList(List<Path> pathList,
			Consumer<Path> consumer) {
		for (Path path : pathList)
			consumer.accept(path);
	}

    /**
     * Consume sub file paths.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @param consumer           the consumer
     */
    public static void consumeSubFilePaths(Path startDirectoryPath,
			int maxDepth, Predicate<Path> filter, Consumer<Path> consumer) {
		consumePathList(true,
				getSubFilePathList(startDirectoryPath, maxDepth, filter),
				consumer);
	}

    /**
     * Consume sub file paths.
     *
     * @param startDirectoryPath the start directory path
     * @param consumer           the consumer
     */
    public static void consumeSubFilePaths(Path startDirectoryPath,
			Consumer<Path> consumer) {
		consumeSubFilePaths(startDirectoryPath, Integer.MAX_VALUE,
				JMPredicate.getTrue(), consumer);
	}

    /**
     * Consume sub file paths.
     *
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param consumer           the consumer
     */
    public static void consumeSubFilePaths(Path startDirectoryPath,
			int maxDepth, Consumer<Path> consumer) {
		consumeSubFilePaths(startDirectoryPath, maxDepth, JMPredicate.getTrue(),
				consumer);
	}

    /**
     * Consume sub file paths.
     *
     * @param startDirectoryPath the start directory path
     * @param filter             the filter
     * @param consumer           the consumer
     */
    public static void consumeSubFilePaths(Path startDirectoryPath,
			Predicate<Path> filter, Consumer<Path> consumer) {
		consumeSubFilePaths(startDirectoryPath, Integer.MAX_VALUE, filter,
				consumer);
	}

    /**
     * Apply path list and get result list list.
     *
     * @param <R>        the type parameter
     * @param isParallel the is parallel
     * @param pathList   the path list
     * @param function   the function
     * @return the list
     */
    public static <R> List<R> applyPathListAndGetResultList(boolean isParallel,
			List<Path> pathList, Function<Path, R> function) {
		return JMStream.buildStream(isParallel, pathList).map(function)
				.collect(toList());
	}

    /**
     * Apply path list and get result list list.
     *
     * @param <R>      the type parameter
     * @param pathList the path list
     * @param function the function
     * @return the list
     */
    public static <R> List<R> applyPathListAndGetResultList(List<Path> pathList,
			Function<Path, R> function) {
		return pathList.parallelStream().map(function).collect(toList());
	}

    /**
     * Apply sub file paths and get applied list list.
     *
     * @param <R>                the type parameter
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @param function           the function
     * @param isParallel         the is parallel
     * @return the list
     */
    public static <R> List<R> applySubFilePathsAndGetAppliedList(
			Path startDirectoryPath, int maxDepth, Predicate<Path> filter,
			Function<Path, R> function, boolean isParallel) {
		return applyPathListAndGetResultList(isParallel,
				getSubFilePathList(startDirectoryPath, maxDepth, filter),
				function);
	}

    /**
     * Apply sub file paths and get applied list list.
     *
     * @param <R>                the type parameter
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param filter             the filter
     * @param function           the function
     * @return the list
     */
    public static <R> List<R> applySubFilePathsAndGetAppliedList(
			Path startDirectoryPath, int maxDepth, Predicate<Path> filter,
			Function<Path, R> function) {
		return applySubFilePathsAndGetAppliedList(startDirectoryPath, maxDepth,
				filter, function, true);
	}

    /**
     * Apply sub file paths and get applied list list.
     *
     * @param <R>                the type parameter
     * @param startDirectoryPath the start directory path
     * @param function           the function
     * @return the list
     */
    public static <R> List<R> applySubFilePathsAndGetAppliedList(
			Path startDirectoryPath, Function<Path, R> function) {
		return applySubFilePathsAndGetAppliedList(startDirectoryPath,
				Integer.MAX_VALUE, JMPredicate.getTrue(), function);
	}

    /**
     * Apply sub file paths and get applied list list.
     *
     * @param <R>                the type parameter
     * @param startDirectoryPath the start directory path
     * @param maxDepth           the max depth
     * @param function           the function
     * @return the list
     */
    public static <R> List<R> applySubFilePathsAndGetAppliedList(
			Path startDirectoryPath, int maxDepth, Function<Path, R> function) {
		return applySubFilePathsAndGetAppliedList(startDirectoryPath, maxDepth,
				JMPredicate.getTrue(), function);
	}

    /**
     * Apply sub file paths and get applied list list.
     *
     * @param <R>                the type parameter
     * @param startDirectoryPath the start directory path
     * @param filter             the filter
     * @param function           the function
     * @return the list
     */
    public static <R> List<R> applySubFilePathsAndGetAppliedList(
			Path startDirectoryPath, Predicate<Path> filter,
			Function<Path, R> function) {
		return applySubFilePathsAndGetAppliedList(startDirectoryPath,
				Integer.MAX_VALUE, filter, function);
	}

    /**
     * Gets file path extension as opt.
     *
     * @param path the path
     * @return the file path extension as opt
     */
    public static Optional<String> getFilePathExtensionAsOpt(Path path) {
		return JMOptional
				.getNullableAndFilteredOptional(path, RegularFileFilter)
				.map(JMPath::getLastName).map(JMString::getExtension)
				.filter(getIsEmpty().negate());
	}

    /**
     * Gets last name.
     *
     * @param path the path
     * @return the last name
     */
    public static String getLastName(Path path) {
		return path.toFile().getName();
	}

    /**
     * Gets path name in os.
     *
     * @param path the path
     * @return the path name in os
     */
    public static String getPathNameInOS(Path path) {
		return os.getFileName(path.toFile());
	}

    /**
     * Gets last modified.
     *
     * @param path the path
     * @return the last modified
     */
    public static long getLastModified(Path path) {
		return path.toFile().lastModified();
	}

    /**
     * Gets path type description.
     *
     * @param path the path
     * @return the path type description
     */
    public static String getPathTypeDescription(Path path) {
		return os.getFileTypeDescription(path.toFile());
	}

    /**
     * Gets size.
     *
     * @param path the path
     * @return the size
     */
    public static long getSize(Path path) {
		return path.toFile().length();
	}

    /**
     * Gets sub files count.
     *
     * @param dirPath the dir path
     * @return the sub files count
     */
    public static int getSubFilesCount(Path dirPath) {
		return getSubFilePathList(dirPath).size();
	}

    /**
     * Gets sub directories count.
     *
     * @param dirPath the dir path
     * @return the sub directories count
     */
    public static int getSubDirectoriesCount(Path dirPath) {
		return getSubDirectoryPathList(dirPath).size();
	}

    /**
     * Gets sub paths count.
     *
     * @param dirPath the dir path
     * @return the sub paths count
     */
    public static int getSubPathsCount(Path dirPath) {
		return getSubPathList(dirPath).size();
	}

    /**
     * Extract sub path path.
     *
     * @param basePath   the base path
     * @param sourcePath the source path
     * @return the path
     */
    public static Path extractSubPath(Path basePath, Path sourcePath) {
		return sourcePath.subpath(basePath.getNameCount() - 1,
				sourcePath.getNameCount());
	}

    /**
     * Build relative destination path path.
     *
     * @param destinationDirPath the destination dir path
     * @param baseDirPath        the base dir path
     * @param sourcePath         the source path
     * @return the path
     */
    public static Path buildRelativeDestinationPath(Path destinationDirPath,
			Path baseDirPath, Path sourcePath) {
		Path extractSubPath = extractSubPath(baseDirPath, sourcePath);
		return destinationDirPath.resolve(extractSubPath);
	}
}
