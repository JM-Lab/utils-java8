package kr.jm.utils.helper;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.enums.OS;
import kr.jm.utils.exception.JMExceptionManager;

public class JMPath {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(JMPath.class);

	public static final FileSystem FS = FileSystems.getDefault();

	public static final Predicate<Path> DirectoryFilter = Files::isDirectory;
	public static final Predicate<Path> FileFilter = Files::isRegularFile;
	public static final Predicate<Path> ExecutableFilter = Files::isExecutable;
	public static final Predicate<Path> ReadableFilter = Files::isReadable;
	public static final Predicate<Path> WritableFilter = Files::isWritable;
	public static final Predicate<Path> SymbolicLinkFilter = Files::isSymbolicLink;
	public static final Predicate<Path> HiddenFilter = JMPath::isHidden;
	public static final Predicate<Path> NotExistFilter = Files::notExists;
	public static final Predicate<Path> ExistFilter = NotExistFilter.negate();
	public static final Predicate<Path> DirectoryAndNoSymbolicLinkFilter = DirectoryFilter
			.and(SymbolicLinkFilter.negate());

	public static List<FileStore> getFileStoreList() {
		return JMCollections.buildList(FS.getFileStores());
	}

	public static List<Path> getFileStorePathList() {
		return getFileStoreList()
				.stream()
				.map(Object::toString)
				.map(toString -> toString.substring(0,
						toString.indexOf(JMString.SPACE))).map(JMPath::getPath)
				.collect(Collectors.toList());
	}

	public static Path getRoot() {
		return Paths.get("/");
	}

	public static boolean isHidden(Path path) {
		try {
			return Files.isHidden(path);
		} catch (IOException e) {
			return true;
		}
	}

	public static List<Path> getRootDirectories() {
		return StreamSupport.stream(FS.getRootDirectories().spliterator(),
				false).collect(toList());
	}

	public static Path getPath(String path) {
		return FS.getPath(path).toAbsolutePath();
	}

	public static Path getCurrentPath() {
		return getPath("");
	}

	public static Path getUserHome() {
		return getPath(OS.getUserHomeDir());
	}

	public static Optional<Stream<Path>> getChildPathStreamAsOpt(Path path) {
		return Optional.ofNullable(path.toFile().listFiles()).map(
				listFiles -> Stream.of(listFiles).map(File::toPath));
	}

	public static Optional<Stream<Path>> getChildPathStreamAsOpt(Path path,
			Predicate<Path> filter) {
		return getChildPathStreamAsOpt(path).map(
				stream -> stream.filter(filter));
	}

	public static Optional<Stream<Path>> getChildDirectoryPathStreamAsOpt(
			Path path) {
		return getChildPathStreamAsOpt(path).map(
				stream -> stream.filter(DirectoryFilter));
	}

	public static Optional<Stream<Path>> getChildDirectoryPathStreamAsOpt(
			Path path, Predicate<Path> filter) {
		return getChildPathStreamAsOpt(path).map(
				stream -> stream.filter(DirectoryFilter.and(filter)));
	}

	public static Optional<Stream<Path>> getChildFilePathStreamAsOpt(Path path) {
		return getChildPathStreamAsOpt(path).map(
				stream -> stream.filter(FileFilter));
	}

	public static Optional<Stream<Path>> getChildFilePathStreamAsOpt(Path path,
			Predicate<Path> filter) {
		return getChildPathStreamAsOpt(path).map(
				stream -> stream.filter(FileFilter.and(filter)));
	}

	public static List<Path> getSubDirectoryPathList(Path startDirectoryPath) {
		return getSubPathList(startDirectoryPath, DirectoryFilter);
	}

	public static List<Path> getSubDirectoryPathList(Path startDirectoryPath,
			int maxDepth) {
		return getSubPathList(startDirectoryPath, maxDepth, DirectoryFilter);
	}

	public static List<Path> getSubDirectoryPathList(Path startDirectoryPath,
			Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, Integer.MAX_VALUE,
				DirectoryFilter.and(filter));
	}

	public static List<Path> getSubDirectoryPathList(Path startDirectoryPath,
			int maxDepth, Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, maxDepth,
				DirectoryFilter.and(filter));
	}

	public static List<Path> getSubFilePathList(Path startDirectoryPath) {
		return getSubPathList(startDirectoryPath, FileFilter);
	}

	public static List<Path> getSubFilePathList(Path startDirectoryPath,
			int maxDepth) {
		return getSubPathList(startDirectoryPath, maxDepth, FileFilter);
	}

	public static List<Path> getSubFilePathList(Path startDirectoryPath,
			Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, FileFilter.and(filter));
	}

	public static List<Path> getSubFilePathList(Path startDirectoryPath,
			int maxDepth, Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, maxDepth,
				FileFilter.and(filter));
	}

	public static List<Path> getSubPathList(Path startDirectoryPath) {
		return getSubPathList(startDirectoryPath, Integer.MAX_VALUE);
	}

	public static List<Path> getSubPathList(Path startDirectoryPath,
			int maxDepth) {
		return getSubPathList(startDirectoryPath, maxDepth,
				JMPredicate.getTrue());
	}

	public static List<Path> getSubPathList(Path startDirectoryPath,
			Predicate<Path> filter) {
		return getSubPathList(startDirectoryPath, Integer.MAX_VALUE, filter);
	}

	public static List<Path> getSubPathList(Path startDirectoryPath,
			int maxDepth, Predicate<Path> filter) {
		List<Path> pathList = Collections.synchronizedList(new ArrayList<>());
		getChildPathStreamAsOpt(startDirectoryPath).ifPresent(
				pathStream -> addAndGetDirectoryStream(pathList, pathStream,
						filter).parallel().forEach(
						getDrillDownFunction(maxDepth, filter, pathList)));
		return pathList;
	}

	private static Stream<Path> addAndGetDirectoryStream(List<Path> pathList,
			Stream<Path> pathStream, Predicate<Path> filter) {
		return pathStream
				.peek(path -> JMWithLambda.ifTureConsume(path, filter,
						pathList::add))
				.filter(DirectoryAndNoSymbolicLinkFilter);
	}

	private static void drillDown(Path directoryPath, int maxDepth,
			List<Path> pathList, Predicate<Path> filter) {
		if (maxDepth < 1)
			return;
		getChildPathStreamAsOpt(directoryPath).ifPresent(
				pathStream -> addAndGetDirectoryStream(pathList, pathStream,
						filter).forEach(
						getDrillDownFunction(maxDepth, filter, pathList)));
	}

	private static Consumer<Path> getDrillDownFunction(int maxDepth,
			Predicate<Path> filter, List<Path> pathList) {
		return path -> drillDown(path, maxDepth - 1, pathList, filter);
	}

	public static void walkSubFilePaths(Path startDirectoryPath, int maxDepth,
			Predicate<Path> filter, Consumer<Path> consumer) {
		if (maxDepth < 1)
			return;
		getChildPathStreamAsOpt(startDirectoryPath).ifPresent(
				pathStream -> pathStream
						.parallel()
						.peek(path -> JMWithLambda.ifTureConsume(path,
								FileFilter.and(filter), consumer))
						.filter(DirectoryAndNoSymbolicLinkFilter)
						.sequential()
						.forEach(
								path -> walkSubFilePaths(path, maxDepth - 1,
										filter, consumer)));
	}

	public static void walkSubFilePaths(Path startDirectoryPath,
			Consumer<Path> consumer) {
		walkSubFilePaths(startDirectoryPath, Integer.MAX_VALUE,
				JMPredicate.getTrue(), consumer);
	}

	public static void walkSubFilePaths(Path startDirectoryPath, int maxDepth,
			Consumer<Path> consumer) {
		walkSubFilePaths(startDirectoryPath, maxDepth, JMPredicate.getTrue(),
				consumer);
	}

	public static void walkSubFilePaths(Path startDirectoryPath,
			Predicate<Path> filter, Consumer<Path> consumer) {
		walkSubFilePaths(startDirectoryPath, Integer.MAX_VALUE, filter,
				consumer);
	}

	public static List<Path> walkSubFilePathsAndGetConsumedList(
			Path startDirectoryPath, int maxDepth, Predicate<Path> filter,
			Consumer<Path> consumer) {
		List<Path> consumedList = Collections
				.synchronizedList(new ArrayList<>());
		walkSubFilePaths(startDirectoryPath, maxDepth, filter,
				consumer.andThen(consumedList::add));
		return consumedList;
	}

	public static List<Path> walkSubFilePathsAndGetConsumedList(
			Path startDirectoryPath, Consumer<Path> consumer) {
		return walkSubFilePathsAndGetConsumedList(startDirectoryPath,
				Integer.MAX_VALUE, JMPredicate.getTrue(), consumer);
	}

	public static List<Path> walkSubFilePathsAndGetConsumedList(
			Path startDirectoryPath, int maxDepth, Consumer<Path> consumer) {
		return walkSubFilePathsAndGetConsumedList(startDirectoryPath, maxDepth,
				JMPredicate.getTrue(), consumer);
	}

	public static List<Path> walkSubFilePathsAndGetConsumedList(
			Path startDirectoryPath, Predicate<Path> filter,
			Consumer<Path> consumer) {
		return walkSubFilePathsAndGetConsumedList(startDirectoryPath,
				Integer.MAX_VALUE, filter, consumer);
	}

	public static Optional<String> getFilePathExtentionAsOpt(Path path) {
		return getPathExtentionAsOpt(path, FileFilter);
	}

	public static Optional<String> getPathExtentionAsOpt(Path path) {
		return getPathExtentionAsOpt(path, JMPredicate.getTrue());
	}

	private static Optional<String> getPathExtentionAsOpt(Path path,
			Predicate<Path> filter) {
		return JMOptional.getOptionalIfTrue(path, filter).map(Path::toString)
				.map(JMString::getExtention)
				.filter(JMPredicate.getEmpty().negate());
	}

	public static String getLastName(Path path) {
		return path.toFile().getName();
	}

	public static Optional<Path> createTempFilePathAsOpt(Path path) {
		String[] prefixSuffix = JMFile.getPrefixSuffix(path.toFile());
		try {
			Path tempFilePath = Files.createTempFile(prefixSuffix[0],
					prefixSuffix[1]);
			deleteOnExit(tempFilePath);
			return Optional.of(tempFilePath);
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnEmptyOptioal(log,
					e, "createTempFile", path);
		}
	}

	public static void deleteOnExit(Path path) {
		path.toFile().deleteOnExit();
	}
}
