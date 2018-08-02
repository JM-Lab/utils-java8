package kr.jm.utils.helper;

import kr.jm.utils.JMProgressiveManager;
import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.exception.JMExceptionManager;

import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMLog.debug;
import static kr.jm.utils.helper.JMPredicate.negate;

/**
 * The type Jm path operation.
 */
public class JMPathOperation {

    /**
     * The Log.
     */
    static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(JMPathOperation.class);

    /**
     * Copy path.
     *
     * @param sourcePath      the source path
     * @param destinationPath the destination path
     * @param options         the options
     * @return the path
     */
    public static Path copy(Path sourcePath, Path destinationPath,
            CopyOption... options) {
        return operate(sourcePath, destinationPath, "copy", finalPath -> {
            try {
                return Files.copy(sourcePath, finalPath, options);
            } catch (Exception e) {
                return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                        "copy", sourcePath, destinationPath, options);
            }
        });
    }

    /**
     * Copy dir recursively async optional.
     *
     * @param targetDirPath      the target dir path
     * @param destinationDirPath the destination dir path
     * @param options            the options
     * @return the optional
     */
    public static Optional<JMProgressiveManager<Path, Path>>
    copyDirRecursivelyAsync(Path targetDirPath, Path destinationDirPath,
            CopyOption... options) {
        Map<Boolean, List<Path>> directoryOrFilePathMap = JMLambda.groupBy(
                JMPath.getSubPathList(targetDirPath), JMPath::isDirectory);
        JMOptional.getOptional(directoryOrFilePathMap, true)
                .ifPresent(list -> list.stream()
                        .map(dirPath -> JMPath.buildRelativeDestinationPath(
                                destinationDirPath, targetDirPath, dirPath))
                        .forEach(JMPathOperation::createDirectories));
        return operateDir(targetDirPath, destinationDirPath,
                directoryOrFilePathMap.get(false),
                bulkPathList -> new JMProgressiveManager<>(bulkPathList,
                        path -> Optional.ofNullable(copy(path,
                                JMPath.buildRelativeDestinationPath(
                                        destinationDirPath, targetDirPath,
                                        path),
                                options))));
    }

    /**
     * Copy file path list async optional.
     *
     * @param filePathList       the file path list
     * @param destinationDirPath the destination dir path
     * @param options            the options
     * @return the optional
     */
    public static Optional<JMProgressiveManager<Path, Path>>
    copyFilePathListAsync(List<Path> filePathList,
            Path destinationDirPath, CopyOption... options) {
        return operateBulk(filePathList, destinationDirPath,
                sourcePath -> Optional.ofNullable(
                        copy(sourcePath, destinationDirPath, options)));
    }

    /**
     * Move path.
     *
     * @param sourcePath      the source path
     * @param destinationPath the destination path
     * @param options         the options
     * @return the path
     */
    public static Path move(Path sourcePath, Path destinationPath,
            CopyOption... options) {
        return operate(sourcePath, destinationPath, "move", finalPath -> {
            try {
                return Files.move(sourcePath, finalPath, options);
            } catch (Exception e) {
                return JMExceptionManager.handleExceptionAndReturnNull(log, e,
                        "move", sourcePath, destinationPath, options);
            }
        });
    }

    /**
     * Move dir optional.
     *
     * @param sourceDirPath      the source dir path
     * @param destinationDirPath the destination dir path
     * @param options            the options
     * @return the optional
     */
    public static Optional<Path> moveDir(Path sourceDirPath,
            Path destinationDirPath, CopyOption... options) {
        return operateDir(sourceDirPath, destinationDirPath, sourceDirPath,
                path -> move(path, destinationDirPath, options));
    }

    /**
     * Move path list async optional.
     *
     * @param pathList           the path list
     * @param destinationDirPath the destination dir path
     * @param options            the options
     * @return the optional
     */
    public static Optional<JMProgressiveManager<Path, Path>> movePathListAsync(
            List<Path> pathList, Path destinationDirPath,
            CopyOption... options) {
        return operateBulk(pathList, destinationDirPath,
                sourcePath -> JMPath.isDirectory(sourcePath)
                        ? moveDir(sourcePath, destinationDirPath, options)
                        : Optional.ofNullable(
                        move(sourcePath, destinationDirPath, options)));
    }

    private static Path operate(Path sourcePath, Path destinationPath,
            String method, Function<Path, Path> resultPathFunction) {
        debug(log, method, sourcePath, destinationPath);
        Path finalPath = JMPath.isDirectory(destinationPath)
                ? destinationPath.resolve(sourcePath.getFileName())
                : buildParentAndDestinationPath(destinationPath);
        if (sourcePath.equals(finalPath))
            throw new RuntimeException("Already Exist !!!");
        return resultPathFunction.apply(finalPath);
    }

    private static Path buildParentAndDestinationPath(Path destinationPath) {
        JMLambda.runIfTrue(!JMPath.exists(destinationPath.getParent()),
                () -> createDirectories(destinationPath.getParent()));
        return destinationPath;
    }

    private static <T, R> Optional<R> operateDir(Path sourceDirPath,
            Path destinationDirPath, T target,
            Function<T, R> operateDirFunction) {
        if (!JMPath.exists(destinationDirPath))
            createDirectories(destinationDirPath);
        return JMLambda.functionIfTrue(
                JMPath.isDirectory(sourceDirPath)
                        && JMPath.isDirectory(destinationDirPath),
                target, operateDirFunction);
    }

    private static Optional<JMProgressiveManager<Path, Path>> operateBulk(
            List<Path> bulkPathList, Path destinationDirPath,
            Function<Path, Optional<Path>> operationFunction) {
        JMLambda.runIfTrue(!JMPath.exists(destinationDirPath),
                () -> createDirectories(destinationDirPath));
        return JMLambda.supplierIfTrue(JMPath.isDirectory(destinationDirPath),
                () -> new JMProgressiveManager<>(bulkPathList,
                        operationFunction));
    }

    /**
     * Delete boolean.
     *
     * @param targetPath the target path
     * @return the boolean
     */
    public static boolean delete(Path targetPath) {
        debug(log, "delete", targetPath);
        try {
            Files.delete(targetPath);
            return true;
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturnFalse(log, e,
                    "delete", targetPath);
        }
    }

    /**
     * Delete all boolean.
     *
     * @param targetPath the target path
     * @return the boolean
     */
    public static boolean deleteAll(Path targetPath) {
        debug(log, "deleteAll", targetPath);
        return JMPath.isDirectory(targetPath) ? deleteDir(targetPath)
                : delete(targetPath);
    }

    /**
     * Delete dir boolean.
     *
     * @param targetDirPath the target dir path
     * @return the boolean
     */
    public static boolean deleteDir(Path targetDirPath) {
        debug(log, "deleteDir", targetDirPath);
        return deleteBulkThenFalseList(
                JMCollections.getReversed(JMPath.getSubPathList(targetDirPath)))
                .size() == 0 && delete(targetDirPath);
    }

    /**
     * Delete bulk then false list list.
     *
     * @param bulkPathList the bulk path list
     * @return the list
     */
    public static List<Path> deleteBulkThenFalseList(List<Path> bulkPathList) {
        debug(log, "deleteBulkThenFalseList", bulkPathList);
        return bulkPathList.stream().filter(negate(JMPathOperation::deleteAll))
                .collect(toList());
    }

    /**
     * Delete all async jm progressive manager.
     *
     * @param pathList the path list
     * @return the jm progressive manager
     */
    public static JMProgressiveManager<Path, Boolean>
    deleteAllAsync(List<Path> pathList) {
        debug(log, "deleteAllAsync", pathList);
        return new JMProgressiveManager<>(pathList,
                targetPath -> Optional.of(deleteAll(targetPath)));
    }

    /**
     * Delete on exit path.
     *
     * @param path the path
     * @return the path
     */
    public static Path deleteOnExit(Path path) {
        debug(log, "deleteOnExit", path);
        path.toFile().deleteOnExit();
        return path;
    }

    /**
     * Create temp file path as opt optional.
     *
     * @param path the path
     * @return the optional
     */
    public static Optional<Path> createTempFilePathAsOpt(Path path) {
        debug(log, "createTempFilePathAsOpt", path);
        String[] prefixSuffix = JMFiles.getPrefixSuffix(path.toFile());
        try {
            return Optional
                    .of(Files.createTempFile(prefixSuffix[0], prefixSuffix[1]))
                    .filter(JMPath.ExistFilter)
                    .map(JMPathOperation::deleteOnExit);
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturnEmptyOptional(log,
                    e, "createTempFilePathAsOpt", path);
        }
    }

    /**
     * Create temp dir path as opt optional.
     *
     * @param path the path
     * @return the optional
     */
    public static Optional<Path> createTempDirPathAsOpt(Path path) {
        debug(log, "createTempDirPathAsOpt", path);
        try {
            return Optional.of(Files.createTempDirectory(path.toString()))
                    .filter(JMPath.ExistFilter)
                    .map(JMPathOperation::deleteOnExit);
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturnEmptyOptional(log,
                    e, "createTempDirPathAsOpt", path);
        }
    }

    /**
     * Create file optional.
     *
     * @param path  the path
     * @param attrs the attrs
     * @return the optional
     */
    public static Optional<Path> createFile(Path path,
            FileAttribute<?>... attrs) {
        debug(log, "createFile", path, attrs);
        try {
            return Optional.of(Files.createFile(path, attrs));
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturnEmptyOptional(log,
                    e, "createFile", path);
        }
    }

    /**
     * Create directories optional.
     *
     * @param dirPath the dir path
     * @param attrs   the attrs
     * @return the optional
     */
    public static Optional<Path> createDirectories(Path dirPath,
            FileAttribute<?>... attrs) {
        debug(log, "createDirectories", dirPath, attrs);
        try {
            return Optional.of(Files.createDirectories(dirPath, attrs));
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturnEmptyOptional(log,
                    e, "createDirectories", dirPath);
        }
    }

    /**
     * Create directory optional.
     *
     * @param dirPath the dir path
     * @param attrs   the attrs
     * @return the optional
     */
    public static Optional<Path> createDirectory(Path dirPath,
            FileAttribute<?>... attrs) {
        debug(log, "createDirectory", dirPath, attrs);
        try {
            return Optional.of(Files.createDirectory(dirPath, attrs));
        } catch (Exception e) {
            return JMExceptionManager.handleExceptionAndReturnEmptyOptional(log,
                    e, "createDirectory", dirPath);
        }
    }

    /**
     * Create file with parent directories.
     *
     * @param path  the path
     * @param attrs the attrs
     */
    public static void createFileWithParentDirectories(Path path,
            FileAttribute<?>... attrs) {
        createDirectories(path.getParent(), attrs);
        createFile(path, attrs);
    }

    /**
     * Delete dir on exist boolean.
     *
     * @param dirPath the dir path
     * @return the boolean
     */
    public static boolean deleteDirOnExist(Path dirPath) {
        return JMPath.exists(dirPath) && deleteDir(dirPath);
    }
}
