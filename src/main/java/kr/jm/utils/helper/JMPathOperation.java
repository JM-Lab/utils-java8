package kr.jm.utils.helper;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMPredicate.negate;

import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import kr.jm.utils.ProgressiveManager;
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
		return operate(sourcePath, destinationPath, "copy", finalPath -> {
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
	 * @return the optional
	 */
	public static Optional<ProgressiveManager<Path, Path>> copyDir(
			Path sourceDirPath, Path destinationDirPath,
			CopyOption... options) {
		return operateDir(sourceDirPath, destinationDirPath,
				JMPath.getSubFilePathList(sourceDirPath),
				bulkPathList -> new ProgressiveManager<Path, Path>(bulkPathList)
						.start(path -> copy(path,
								buildCopyDestinationPath(path,
										destinationDirPath, sourceDirPath),
								options)));
	}

	private static Path buildCopyDestinationPath(Path path,
			Path destinationDirPath, Path sourceDirPath) {
		return destinationDirPath.resolve(
				path.toString().substring(buildSubPathIndex(sourceDirPath)));
	}

	/**
	 * Copy bulk.
	 *
	 * @param bulkFilePathList
	 *            the bulk file path list
	 * @param destinationDirPath
	 *            the destination dir path
	 * @param options
	 *            the options
	 * @return the optional
	 */
	public static Optional<ProgressiveManager<Path, Path>> copyBulk(
			List<Path> bulkFilePathList, Path destinationDirPath,
			CopyOption... options) {
		return operateBulk(bulkFilePathList, destinationDirPath,
				sourcePath -> copy(sourcePath, destinationDirPath, options));
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
	 * @return the optional
	 */
	public static Optional<Path> moveDir(Path sourceDirPath,
			Path destinationDirPath, CopyOption... options) {
		return operateDir(sourceDirPath, destinationDirPath, sourceDirPath,
				path -> move(path, destinationDirPath, options));
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
	 * @return the optional
	 */
	public static Optional<ProgressiveManager<Path, Path>> moveBulk(
			List<Path> bulkPathList, Path destinationDirPath,
			CopyOption... options) {
		return operateBulk(bulkPathList, destinationDirPath,
				sourcePath -> move(sourcePath, destinationDirPath, options));
	}

	private static Path operate(Path sourcePath, Path destinationPath,
			String method, Function<Path, Path> resultPathFunction) {
		try {
			return resultPathFunction.apply(JMPath.isDirectory(destinationPath)
					? destinationPath.resolve(sourcePath.getFileName())
					: buildParentAndDistinationPath(destinationPath));
		} catch (Exception e) {
			return JMExceptionManager.handleExceptionAndReturnNull(log, e,
					method, sourcePath, destinationPath);
		}
	}

	private static Path buildParentAndDistinationPath(Path destinationPath) {
		JMLambda.runIfTrue(!JMPath.exists(destinationPath.getParent()),
				() -> createDirectories(destinationPath.getParent()));
		return destinationPath;
	}

	private static <T, R> Optional<R> operateDir(Path sourceDirPath,
			Path destinationDirPath, T target,
			Function<T, R> operateDirFunction) {
		if (!JMPath.exists(destinationDirPath)) {
			log.warn(
					"DestinationDirPath Doesn't Exist !!! - Create Directory = {}",
					destinationDirPath);
			createDirectories(destinationDirPath);
		}
		return JMLambda.functionIfTrue(
				JMPath.isDirectory(sourceDirPath)
						&& JMPath.isDirectory(destinationDirPath),
				target, operateDirFunction);
	}

	private static Optional<ProgressiveManager<Path, Path>> operateBulk(
			List<Path> bulkPathList, Path destinationDirPath,
			Function<Path, Path> operationFunction) {
		JMLambda.runIfTrue(!JMPath.exists(destinationDirPath),
				() -> createDirectories(destinationDirPath));
		return JMLambda.supplierIfTrue(JMPath.isDirectory(destinationDirPath),
				() -> new ProgressiveManager<Path, Path>(bulkPathList)
						.start(path -> operationFunction.apply(path)));
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
		return deleteBulkThenFalseList(JMPath.getSubPathList(targetDirPath))
				.size() == 0 ? delete(targetDirPath) : false;
	}

	/**
	 * Delete bulk then false list.
	 *
	 * @param bulkPathList
	 *            the bulk path list
	 * @return the list
	 */
	public static List<Path> deleteBulkThenFalseList(List<Path> bulkPathList) {
		return JMCollections.getReversed(bulkPathList).stream()
				.filter(negate(JMPathOperation::delete)).collect(toList());
	}

	/**
	 * Delete bulk then false list.
	 *
	 * @param bulkPathList
	 *            the bulk path list
	 * @param callbackConsumer
	 *            the callback consumer
	 * @return the progressive manager
	 */
	public static ProgressiveManager<Path, Path> deleteBulkThenFalseList(
			List<Path> bulkPathList, Consumer<Path> callbackConsumer) {
		ProgressiveManager<Path, Path> progressiveManager =
				new ProgressiveManager<>(
						JMCollections.getReversed(bulkPathList));
		progressiveManager.start(path -> delete(path) ? null : path);
		return progressiveManager;
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
