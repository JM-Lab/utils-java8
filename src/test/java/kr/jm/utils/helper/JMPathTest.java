package kr.jm.utils.helper;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.enums.OS;

public class JMPathTest {

	@Test
	public void testGetRootPaths() throws Exception {
		System.out.println(JMPath.getRootPathStream());
		System.out.println(getRoot().getFileSystem().getRootDirectories());
	}

	private Path getRoot() {
		return JMPath.getPath("/");
	}

	@Test
	public void testGetPath() throws Exception {
		System.out.println(JMPath
				.getChildDirectoryPathStream(JMPath.getPath("[/]")).count());
		System.out.println(JMPath.getPath("~/"));
		System.out.println(Paths.get("~"));
		System.out.println(JMPath.getPath(OS.getUserHomeDir()));
	}

	@Test
	public void testGetCurrentPath() throws Exception {
		System.out.println(JMPath.getCurrentPath());
	}

	@Test
	public void testGetUserHome() throws Exception {
		System.out.println(JMPath.getUserHome());
	}

	@Test
	public void testGetRootDirectories() throws Exception {
		JMPath.getRootPathStream()
				.forEach(path -> JMPath.getChildrenPathStream(path)
						.forEach(p -> System.out.println(p)));
	}

	@Test
	public void testGetFileStoreList() throws Exception {
		System.out.println(JMPath.getFileStoreList());
		JMPath.getFileStoreList().stream()
				.forEach(fs -> System.out.print(fs.name() + ", "));
		System.out.println();
		JMPath.getFileStoreList().stream()
				.forEach(fs -> System.out.print(fs.type() + ", "));
		System.out.println();
		JMPath.getFileStoreList().stream()
				.forEach(fs -> System.out.print(fs.toString() + ", "));
		System.out.println();
		JMPath.getFileStoreList().stream().forEach(
				fs -> System.out.print(JMPath.getPath(fs.name()) + "," + " "));
		System.out.println();
		JMPath.getFileStorePathList().forEach(System.out::println);
	}

	@Test
	public void testGetFileStorePathList() throws Exception {
		System.out.println(JMPath.getFileStorePathList());
		JMPath.getFileStorePathList().stream()
				.map(JMPath::getChildrenPathStream)
				.forEach(System.out::println);
	}

	@Test
	public void testGetChildFilePathStreamAsOptPath() throws Exception {
		System.out.println(JMPath.getRootDirectoryStream());
		JMPath.getRootDirectoryStream()
				.forEach(path -> JMPath.getChildFilePathStream(path)
						.forEach(p -> System.out.print(p + " ")));
		System.out.println();
		JMPath.getRootDirectoryStream()
				.forEach(path -> JMPath
						.getChildFilePathStream(path,
								JMPath.HiddenFilter.negate())
						.forEach(p -> System.out.print(p + " ")));
	}

	@Test
	public void testGetChildDirectoryPathStreamAsOpt() throws Exception {
		System.out.println(JMPath.getRootDirectoryStream());
		JMPath.getRootDirectoryStream()
				.forEach(path -> JMPath.getChildDirectoryPathStream(path)
						.forEach(p -> System.out.print(p + " ")));
		System.out.println();
		JMPath.getRootDirectoryStream()
				.forEach(path -> JMPath
						.getChildDirectoryPathStream(path,
								JMPath.HiddenFilter.negate())
						.forEach(p -> System.out.print(p + " ")));
	}

	@Test
	public void testGetChildrenPathStreamAsOptPath() throws Exception {
		System.out.println(JMPath.getRootDirectoryStream());
		List<String> rootChildPaths = JMPath.getRootDirectoryStream()
				.flatMap(path -> JMPath.getChildrenPathStream(path)
						.filter(JMPath.DirectoryAndNoSymbolicLinkFilter
								.or(JMPath.FileFilter)))
				.map(Path::toAbsolutePath).map(Path::toString)
				.collect(toList());
		System.out.println(rootChildPaths);
		List<String> rootChildFilePaths = JMPath.getRootDirectoryStream()
				.flatMap(path -> JMPath.getChildFilePathStream(path))
				.map(Path::toAbsolutePath).map(Path::toString)
				.collect(toList());
		System.out.println(rootChildFilePaths);
		List<String> rootchildDirPaths =
				JMPath.getRootDirectoryStream()
						.flatMap(path -> JMPath
								.getChildDirectoryPathStream(path))
				.map(Path::toAbsolutePath).map(Path::toString)
				.collect(toList());

		System.out.println(rootchildDirPaths);

		assertEquals(rootChildPaths.size(),
				rootchildDirPaths.size() + rootChildFilePaths.size());
		assertEquals(JMCollections.sort(rootChildPaths).toString(),
				Stream.concat(rootchildDirPaths.stream(),
						rootChildFilePaths.stream()).sorted().collect(toList())
				.toString());

	}

	@Test
	public void testGetSubPathsStreamAsOptPath() throws Exception {
		Path startDirectoryPath = getRoot();
		long orElse = JMPath.getChildrenPathStream(startDirectoryPath).count();
		long count = Files.walk(startDirectoryPath, 1).skip(1).count();
		int size = JMPath.getSubPathList(startDirectoryPath, 1).size();
		System.out.println(orElse + " " + count + " " + size);
		assertEquals(count, orElse);
		assertEquals(count, size);
		List<Path> list = JMPath.getSubFilePathList(startDirectoryPath, 3);
		System.out.println(list.size());
	}

	@Ignore
	@Test
	public void testConsumeSubFilePaths() throws Exception {
		Path startDirectoryPath = JMPath.getUserHome();
		List<Path> list = Collections.synchronizedList(new ArrayList<>());
		JMPath.consumeSubFilePaths(startDirectoryPath,
				path -> path.toString().contains(".png"), list::add);
	}

	@Test
	public void testConsumeSubFilePathsAndGetAppliedList() throws Exception {
		Path startDirectoryPath = getRoot();
		List<Path> consumedList = JMPath.applySubFilePathsAndGetAppliedList(
				startDirectoryPath, 3, path -> path.toString().contains(".png"),
				JMLambda::getSelf);
		System.out.println(consumedList);
	}

	@Test
	public void testConsumeSubFilePathsAndGetAppliedList2() throws Exception {
		Path startDirectoryPath = JMPath.getUserHome();
		System.out
				.println(JMPath
						.applySubFilePathsAndGetAppliedList(startDirectoryPath,
								JMPredicate.getTrue(), JMLambda::getSelf)
						.size());
	}

	@Test
	public void testGetPathExtentionAsOpt() throws Exception {
		Path path =
				JMPath.getPath("/09A0F357-E206-444C-83CA-0475947F8718/Data/7/3"
						+ "/Attachments/37857/2/Mail 첨부 파일.png");
		System.out.println(JMPath.getPathExtentionAsOpt(path));
		assertEquals(".png", JMPath.getPathExtentionAsOpt(path).get());

		path = JMPath.getPath("Data/7/3/Attachments/37857/2/Mail 첨부 파일");
		System.out.println(JMPath.getPathExtentionAsOpt(path));
		assertEquals(Optional.empty(), JMPath.getFilePathExtentionAsOpt(path));

		JMPath.getPath(
				"/09A0F357-E206-444C-83CA-0475947F8718/Data/7/3/Attachments/37857/2/");
		System.out.println(JMPath.getPathExtentionAsOpt(path));
		assertEquals(Optional.empty(), JMPath.getFilePathExtentionAsOpt(path));

		path = JMPath.getPath("Mail 첨부 파일");
		System.out.println(JMPath.getPathExtentionAsOpt(path));
		assertEquals(Optional.empty(), JMPath.getFilePathExtentionAsOpt(path));

		path = JMPath.getPath("Mail. 첨.부 파일.png");
		System.out.println(JMPath.getPathExtentionAsOpt(path));
		assertEquals(".png", JMPath.getPathExtentionAsOpt(path).get());

	}

	@Test
	public void testGetLastName() throws Exception {
		Path path = JMPath.getPath(
				"/09A0F357-E206-444C-83CA-0475947F8718/Data/7/3/Attachments/37857/2/Mail 첨부 파일.png");
		assertEquals("Mail 첨부 파일.png", JMPath.getLastName(path));
	}

	@Test
	public void testPathInfo() throws Exception {
		Path path = JMPath.getPath("/dev");
		System.out.println(JMPath.DirectoryFilter.test(path));
		System.out.println(JMPath.ExistFilter.test(path));
		// System.out.println(Files.is);

	}
}
