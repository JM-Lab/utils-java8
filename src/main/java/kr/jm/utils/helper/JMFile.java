package kr.jm.utils.helper;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.enums.OS;

public class JMFile {

	public static final FileSystem FS = FileSystems.getDefault();

	public static final Predicate<? super Path> DirectoryFilter = Files::isDirectory;
	public static final Predicate<? super Path> FileFilter = Files::isRegularFile;
	public static final Predicate<? super Path> ExecutableFilter = Files::isExecutable;
	public static final Predicate<? super Path> ReadableFilter = Files::isReadable;
	public static final Predicate<? super Path> WritableFilter = Files::isWritable;
	public static final Predicate<? super Path> SymbolicLinkFilter = Files::isSymbolicLink;
	public static final Predicate<? super Path> HiddenFileFilter = JMFile::isHidden;

	public static Path getRoot() {
		return Paths.get("/");
	}

	public static List<FileStore> getFileStoreList() {
		return JMCollections.buildList(FS.getFileStores());
	}

	public static List<Path> getFileStorePathList() {
		return getFileStoreList()
				.stream()
				.map(Object::toString)
				.map(toString -> toString.substring(0,
						toString.indexOf(JMString.SPACE))).map(JMFile::getPath)
				.collect(Collectors.toList());
	}

	public static boolean isHidden(Path path) {
		try {
			return Files.isHidden(path);
		} catch (IOException e) {
			return true;
		}
	}

	public static List<Path> getRootDirectories() {
		List<Path> rootDirectoryList = new ArrayList<Path>();
		for (Path path : FS.getRootDirectories())
			rootDirectoryList.add(path);
		return rootDirectoryList;
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
		try {
			return Optional.of(Files.walk(path, 1).skip(1));
		} catch (IOException e) {
			return Optional.empty();
		}
	}

	public static Optional<Stream<Path>> getChildPathStreamAsOpt(Path path,
			Predicate<? super Path> filter) {
		return getChildPathStreamAsOpt(path).map(
				stream -> stream.filter(filter));
	}

	public static Optional<Stream<Path>> getChildDirectoryPathStreamAsOpt(
			Path path) {
		return getChildPathStreamAsOpt(path).map(
				stream -> stream.filter(DirectoryFilter));
	}

}
