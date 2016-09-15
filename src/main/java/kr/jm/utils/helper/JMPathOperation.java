package kr.jm.utils.helper;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMPredicate.negate;

import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.exception.JMExceptionManager;

/**
 * The Class JMPathOperation.
 */
public class JMPathOperation {

	static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(JMPathOperation.class);

	/**
	 * Copy.
	 *
	 * @param sourcePath
	 *            the source path
	 * @param destinationPath
	 *            the destination path
	 * @param options
	 *            the options
	 * @return the path
	 */
	public static Path copy(Path sourcePath, Path destinationPath,
			CopyOption... options) {
		return JMPathOperation.operate(sourcePath, destinationPath, "copy",
				finalPath -> {
					try {
						return Files.copy(sourcePath, finalPath, options);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
	}

	/**
	 * Copy dir.
	 *
	 * @param sourceDirPath
	 *            the source dir path
	 * @param destinationDirPath
	 *            the destination dir path
	 * @param options
	 *            the options
	 * @return the list
	 */
	public static List<Path> copyDir(Path sourceDirPath,
			Path destinationDirPath, CopyOption... options) {
		return JMPathOperation.operateDir(sourceDirPath, destinationDirPath,
				path -> copy(path,
						JMPathOperation.buildDestinationDirPath(path,
								JMPathOperation.buildSubPathIndex(
										sourceDirPath),
								destinationDirPath),
						options));
	}

	/**
	 * Copy dir.
	 *
	 * @param sourceDirPath
	 *            the source dir path
	 * @param destinationDirPath
	 *            the destination dir path
	 * @param callbackConsumer
	 *            the callback consumer
	 * @param options
	 *            the options
	 * @return the list
	 */
	public static List<Path> copyDir(Path sourceDirPath,
			Path destinationDirPath, Consumer<Path> callbackConsumer,
			CopyOption... options) {
		return JMPathOperation.operateDir(sourceDirPath, destinationDirPath,
				path -> copy(path,
						JMPathOperation.buildDestinationDirPath(path,
								JMPathOperation.buildSubPathIndex(
										sourceDirPath),
								destinationDirPath),
						options),
				callbackConsumer);
	}

	/**
	 * Copy bulk.
	 *
	 * @param bulkPathList
	 *            the bulk path list
	 * @param destinationDirPath
	 *            the destination dir path
	 * @param options
	 *            the options
	 * @return the list
	 */
	public static List<Path> copyBulk(List<Path> bulkPathList,
			Path destinationDirPath, CopyOption... options) {
		return JMPathOperation.operateBulk(bulkPathList, destinationDirPath,
				(sourcePath, dirPath) -> copy(sourcePath, dirPath, options));
	}

	/**
	 * Copy bulk.
	 *
	 * @param bulkPathList
	 *            the bulk path list
	 * @param destinationDirPath
	 *            the destination dir path
	 * @param callbackConsumer
	 *            the callback consumer
	 * @param options
	 *            the options
	 * @return the list
	 */
	public static List<Path> copyBulk(List<Path> bulkPathList,
			Path destinationDirPath, Consumer<Path> callbackConsumer,
			CopyOption... options) {
		return JMPathOperation.operateBulk(bulkPathList, destinationDirPath,
				(sourcePath, dirPath) -> copy(sourcePath, dirPath, options),
				callbackConsumer);
	}

	private static Path operate(Path sourcePath, Path destinationPath,
			String method, Function<Path, Path> resultPathFunction) {
		try {
			return resultPathFunction.apply(JMPath.isDirectory(destinationPath)
					? JMPathOperation.buildDistinationFilePath(destinationPath,
							sourcePath.getFileName())
					: JMPathOperation.buildDistinationFilePath(
							destinationPath.getParent(),
							destinationPath.getFileName()));
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					method, sourcePath, destinationPath);
		}
	}

	private static List<Path> operateDir(Path sourceDirPath,
			Path destinationDirPath, Function<Path, Path> transformFunction) {
		return JMPath.isDirectory(sourceDirPath)
				&& JMPath.isDirectory(destinationDirPath)
				|| !JMPath.exists(destinationDirPath)
						? JMCollections.transformList(
								JMPath.getSubFilePathList(sourceDirPath),
								transformFunction)
						: Collections.emptyList();
	}

	private static List<Path> operateDir(Path sourceDirPath,
			Path destinationDirPath, Function<Path, Path> transformFunction,
			Consumer<Path> callbackConsumer) {
		return JMPath.isDirectory(sourceDirPath)
				&& JMPath.isDirectory(destinationDirPath)
				|| !JMPath.exists(destinationDirPath)
						? JMCollections.transformList(
								JMPath.getSubFilePathList(sourceDirPath),
								transformFunction.andThen(path -> {
									callbackConsumer.accept(path);
									return path;
								}))
						: Collections.emptyList();
	}

	private static List<Path> operateBulk(List<Path> bulkPathList,
			Path destinationDirPath, BiFunction<Path, Path, Path> biFunction) {
		return JMPath.isDirectory(destinationDirPath)
				? bulkPathList.stream()
						.map(path -> biFunction.apply(path, destinationDirPath))
						.filter(path -> path != null).collect(toList())
				: Collections.emptyList();
	}

	private static List<Path> operateBulk(List<Path> bulkPathList,
			Path destinationDirPath, BiFunction<Path, Path, Path> biFunction,
			Consumer<Path> callbackConsumer) {
		return JMPath.isDirectory(destinationDirPath) ? bulkPathList.stream()
				.map(path -> biFunction.apply(path, destinationDirPath))
				.peek(callbackConsumer).filter(path -> path != null)
				.collect(toList()) : Collections.emptyList();
	}

	private static Path buildDestinationDirPath(Path path, int subPathIndex,
			Path destinationDirPath) {
		return destinationDirPath
				.resolve(path.toString().substring(subPathIndex));
	}

	private static Path buildDistinationFilePath(Path destinationDirPath,
			Path destinationFileNamePath) throws Exception {
		if (!JMPath.exists(destinationDirPath))
			Files.createDirectories(destinationDirPath);
		return destinationDirPath.resolve(destinationFileNamePath);
	}

	/**
	 * Move.
	 *
	 * @param sourcePath
	 *            the source path
	 * @param destinationPath
	 *            the destination path
	 * @param options
	 *            the options
	 * @return the path
	 */
	public static Path move(Path sourcePath, Path destinationPath,
			CopyOption... options) {
		return operate(sourcePath, destinationPath, "move", finalPath -> {
			try {
				return Files.move(sourcePath, finalPath, options);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	/**
	 * Move dir.
	 *
	 * @param sourceDirPath
	 *            the source dir path
	 * @param destinationDirPath
	 *            the destination dir path
	 * @param options
	 *            the options
	 * @return the list
	 */
	public static List<Path> moveDir(Path sourceDirPath,
			Path destinationDirPath, CopyOption... options) {
		List<Path> movedList =
				operateDir(sourceDirPath, destinationDirPath,
						path -> move(path, buildDestinationDirPath(path,
								JMPathOperation.buildSubPathIndex(
										sourceDirPath),
								destinationDirPath), options));
		if (JMPathOperation
				.deleteBulkWithFalseList(
						JMPath.getSubDirectoryPathList(sourceDirPath))
				.size() == 0)
			JMPathOperation.delete(sourceDirPath);
		return movedList;
	}

	/**
	 * Move dir.
	 *
	 * @param sourceDirPath
	 *            the source dir path
	 * @param destinationDirPath
	 *            the destination dir path
	 * @param callbackConsumer
	 *            the callback consumer
	 * @param options
	 *            the options
	 * @return the list
	 */
	public static List<Path> moveDir(Path sourceDirPath,
			Path destinationDirPath, Consumer<Path> callbackConsumer,
			CopyOption... options) {
		List<Path> movedList =
				operateDir(sourceDirPath, destinationDirPath,
						path -> move(path, buildDestinationDirPath(path,
								JMPathOperation.buildSubPathIndex(
										sourceDirPath),
								destinationDirPath), options),
						callbackConsumer);
		if (JMPathOperation
				.deleteBulkWithFalseList(
						JMPath.getSubDirectoryPathList(sourceDirPath))
				.size() == 0)
			JMPathOperation.delete(sourceDirPath);
		return movedList;
	}

	/**
	 * Move bulk.
	 *
	 * @param bulkPathList
	 *            the bulk path list
	 * @param destinationDirPath
	 *            the destination dir path
	 * @param options
	 *            the options
	 * @return the list
	 */
	public static List<Path> moveBulk(List<Path> bulkPathList,
			Path destinationDirPath, CopyOption... options) {
		return operateBulk(bulkPathList, destinationDirPath,
				(sourcePath, dirPath) -> move(sourcePath, dirPath, options));
	}

	/**
	 * Move bulk.
	 *
	 * @param bulkPathList
	 *            the bulk path list
	 * @param destinationDirPath
	 *            the destination dir path
	 * @param callbackConsumer
	 *            the callback consumer
	 * @param options
	 *            the options
	 * @return the list
	 */
	public static List<Path> moveBulk(List<Path> bulkPathList,
			Path destinationDirPath, Consumer<Path> callbackConsumer,
			CopyOption... options) {
		return operateBulk(bulkPathList, destinationDirPath,
				(sourcePath, dirPath) -> move(sourcePath, dirPath, options),
				callbackConsumer);
	}

	private static int buildSubPathIndex(Path sourceDirPath) {
		return sourceDirPath.toString().length() - 2;
	}

	/**
	 * Delete.
	 *
	 * @param targetPath
	 *            the target path
	 * @return true, if successful
	 */
	public static boolean delete(Path targetPath) {
		try {
			Files.delete(targetPath);
			return true;
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnFalse(log, e,
					"delete", targetPath);
		}
	}

	/**
	 * Delete dir.
	 *
	 * @param targetDirPath
	 *            the target dir path
	 * @return true, if successful
	 */
	public static boolean deleteDir(Path targetDirPath) {
		return JMPathOperation
				.deleteBulkWithFalseList(JMPath.getSubPathList(targetDirPath))
				.size() == 0 ? delete(targetDirPath) : false;
	}

	/**
	 * Delete dir.
	 *
	 * @param targetDirPath
	 *            the target dir path
	 * @param callbackConsumer
	 *            the callback consumer
	 * @return true, if successful
	 */
	public static boolean deleteDir(Path targetDirPath,
			Consumer<Path> callbackConsumer) {
		return JMPathOperation
				.deleteBulkWithFalseList(JMPath.getSubPathList(targetDirPath),
						callbackConsumer)
				.size() == 0 ? delete(targetDirPath) : false;
	}

	/**
	 * Delete bulk with false list.
	 *
	 * @param bulkPathList
	 *            the bulk path list
	 * @return the list
	 */
	public static List<Path> deleteBulkWithFalseList(List<Path> bulkPathList) {
		return JMCollections.getReversed(bulkPathList).stream()
				.filter(negate(JMPathOperation::delete)).collect(toList());
	}

	/**
	 * Delete bulk with false list.
	 *
	 * @param bulkPathList
	 *            the bulk path list
	 * @param callbackConsumer
	 *            the callback consumer
	 * @return the list
	 */
	public static List<Path> deleteBulkWithFalseList(List<Path> bulkPathList,
			Consumer<Path> callbackConsumer) {
		return JMCollections.getReversed(bulkPathList).stream()
				.filter(negate(path -> {
					boolean result = delete(path);
					callbackConsumer.accept(path);
					return result;
				})).collect(toList());
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
					.filter(JMPath.ExistFilter)
					.map(JMPathOperation::deleteOnExit);
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnEmptyOptional(log,
					e, "createTempFile", path);
		}
	}

	/**
	 * Creates the file.
	 *
	 * @param path
	 *            the path
	 * @param attrs
	 *            the attrs
	 * @return the optional
	 */
	public static Optional<Path> createFile(Path path,
			FileAttribute<?>... attrs) {
		try {
			return Optional.of(Files.createFile(path, attrs));
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnEmptyOptional(log,
					e, "createFile", path);
		}
	}

	/**
	 * Creates the directories.
	 *
	 * @param dirPath
	 *            the dir path
	 * @param attrs
	 *            the attrs
	 * @return the optional
	 */
	public static Optional<Path> createDirectories(Path dirPath,
			FileAttribute<?>... attrs) {
		try {
			return Optional.of(Files.createDirectories(dirPath, attrs));
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnEmptyOptional(log,
					e, "createDirectories", dirPath);
		}
	}

}
