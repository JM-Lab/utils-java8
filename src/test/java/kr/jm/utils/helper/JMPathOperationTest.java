package kr.jm.utils.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import kr.jm.utils.ProgressiveManager;

public class JMPathOperationTest {

	@Test
	public void testDelete() throws Exception {
		Path startDirectoryPath = Paths.get("test");
		Path d1Path = startDirectoryPath.resolve("d1");
		Path d2Path = d1Path.resolve("d2");
		Files.createDirectories(d2Path);
		System.out.println(JMPath.getSubPathList(startDirectoryPath));
		assertTrue(!JMPathOperation.delete(startDirectoryPath));
		assertTrue(JMPath.exists(startDirectoryPath));
		assertEquals(0,
				JMPathOperation
						.deleteBulkThenFalseList(
								JMPath.getSubPathList(startDirectoryPath))
						.size());
		System.out.println(JMPath.getSubPathList(startDirectoryPath));
		assertTrue(JMPath.exists(startDirectoryPath));
		Files.createDirectories(d2Path);
		Files.createFile(startDirectoryPath.resolve("test.file"));
		Files.createFile(d1Path.resolve("test.file"));
		Files.createFile(d2Path.resolve("test.file"));
		System.out.println(JMPath.getSubPathList(startDirectoryPath));
		JMPathOperation.deleteDir(startDirectoryPath);
		assertTrue(!JMPath.exists(startDirectoryPath));
		System.out.println(JMPath.getSubPathList(startDirectoryPath));

		Files.createDirectories(d2Path);
		Files.createFile(startDirectoryPath.resolve("test.file"));
		Files.createFile(d1Path.resolve("test.file"));
		Files.createFile(d2Path.resolve("test.file"));
		assertFalse(JMPathOperation.delete(startDirectoryPath));
		assertTrue(JMPathOperation.deleteDir(startDirectoryPath));
	}

	@Test
	public void testCopy() throws Exception {
		Path startDirectoryPath = Paths.get("test");
		JMPathOperation.deleteDir(startDirectoryPath);
		Path d1Path = startDirectoryPath.resolve("d1");
		Path d2Path = d1Path.resolve("d2");
		Files.createDirectories(d2Path);
		Path rootFilePath = startDirectoryPath.resolve("test.file");
		Files.createFile(rootFilePath);
		Path d1FilePath = d1Path.resolve("test.file");
		Files.createFile(d1FilePath);
		Path d2FilePath = d2Path.resolve("test.file");
		Files.createFile(d2FilePath);
		List<Path> subPathList = JMPath.getSubPathList(startDirectoryPath);
		System.out.println(subPathList);
		assertTrue(JMPathOperation.copy(d1FilePath, d2Path) == null);
		JMPathOperation.copy(d1FilePath, d2Path.resolve("test2.file"));
		System.out.println(subPathList);
		int size = subPathList.size();
		List<Path> subPathList2 = JMPath.getSubPathList(d1Path);
		int size2 = subPathList2.size();
		System.out.println(JMPath.getSubPathList(startDirectoryPath));
		Path copyd1 = startDirectoryPath.resolve("copyd1");
		Optional<ProgressiveManager<Path, Path>> copyDirProgress =
				JMPathOperation.copyDir(d1Path, copyd1);
		copyDirProgress.get().getResultListSync();
		subPathList = JMPath.getSubPathList(startDirectoryPath);
		System.out.println(subPathList);
		assertEquals(subPathList.size(), size + size2 + 3);
		copyDirProgress = JMPathOperation.copyDir(copyd1, d2Path);
		assertEquals(3, copyDirProgress.get().getResultListSync().size());
		System.out.println(JMPath.getSubPathList(startDirectoryPath));
		JMPathOperation.deleteDir(startDirectoryPath);

	}

	@Test
	public void testMove() throws Exception {
		Path startDirectoryPath = Paths.get("test");
		JMPathOperation.deleteDir(startDirectoryPath);
		Path d1Path = startDirectoryPath.resolve("d1");
		Path d2Path = d1Path.resolve("d2");
		Files.createDirectories(d2Path);
		Path rootFilePath = startDirectoryPath.resolve("test.file");
		Files.createFile(rootFilePath);
		Path d1FilePath = d1Path.resolve("test.file");
		Files.createFile(d1FilePath);
		Path d2FilePath = d2Path.resolve("test.file");
		Files.createFile(d2FilePath);
		List<Path> subPathList = JMPath.getSubPathList(startDirectoryPath);
		System.out.println(subPathList);
		assertTrue(JMPathOperation.move(d1FilePath, d2Path) == null);
		JMPathOperation.move(d1FilePath, d2Path.resolve("test2.file"));
		assertTrue(!JMPath.exists(d1FilePath));
		System.out.println(subPathList);
		int size = subPathList.size();
		System.out.println(JMPath.getSubPathList(startDirectoryPath));
		Path copyd1 = startDirectoryPath.resolve("copyd1");
		JMPathOperation.moveDir(d1Path, copyd1);
		subPathList = JMPath.getSubPathList(startDirectoryPath);
		System.out.println(subPathList);
		assertEquals(subPathList.size(), size + 1);
		List<Path> subPathList2 =
				JMPath.getSubPathList(JMPathOperation.move(copyd1, d2Path));
		System.out.println(subPathList2);
		System.out.println(JMPath.getSubPathList(d1Path));
		assertEquals(5, JMPath.getSubPathList(d1Path).size());
		System.out.println(JMPath.getSubPathList(startDirectoryPath));
		JMPathOperation.deleteDir(startDirectoryPath);
	}
}
